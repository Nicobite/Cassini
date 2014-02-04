
package GUI;

import config.files.ConfigFile;
import config.files.ConfigFilesExplorer;
import config.data.DataType;
import GUI.misc.StatusBarItem;
import GUI.roads.RoadsDisplayPanel;
import config.data.ConfigData;
import config.data.ConfigFileTableModel;
import config.files.ConfigFilesLinker;
import config.files.IncorrectFormatException;
import config.files.MapConfigFile;
import config.types.roads.JunctionConfig;
import config.types.roads.RoadConfig;
import config.types.vehicles.DriverVehicleAssociation;
import config.types.vehicles.TestSetItem;
import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import main.GlobalErrorHandler;
import main.MainController;
import org.jdom2.JDOMException;

/**
 *
 * @author Laure
 */
public class GUICore {
    private MainController mainController;
	private GUI gui;
	private MapConfigFile currentMap = null;
	private File currentMapConfigDir = null;
	private File globalConfigDir = new File ("./globalconfig/");
	private String currentMapConfigDirCanonical = null;
	private String globalConfigDirCanonical = null;
	
	private HashMap<DataType, ConfigFilesExplorer> configFilesExplorers = new HashMap<>();
	private ConfigFilesExplorer driversFilesExplorer;
	private ConfigFilesExplorer vehiclesFilesExplorer;
	private ConfigFilesExplorer proportionsFilesExplorer;
	private ConfigFilesExplorer testsetsFilesExplorer;
	
    public GUICore (MainController mainController, GUI gui) throws IOException {
		this.mainController = mainController;
		this.gui = gui;
		
		checkDir(globalConfigDir);
		globalConfigDirCanonical = globalConfigDir.getCanonicalPath();
    }
	
    public void openMap() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new MapFileFilter());
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		int returnVal = chooser.showOpenDialog(gui);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File map = chooser.getSelectedFile();
			
			try {
				currentMap = new MapConfigFile (map);
				currentMapConfigDir = new File (map.getParentFile(), "config");
				checkFile(map);
				checkDir(currentMapConfigDir);
				currentMapConfigDirCanonical = currentMapConfigDir.getCanonicalPath();
				loadMap();
				loadConfigFiles();
			}
			catch (IOException | JDOMException | IncorrectFormatException | InstantiationException e) {
				GlobalErrorHandler.notifyNonCriticalException(e);
				currentMap = null;
				currentMapConfigDir = null;
				currentMapConfigDirCanonical = null;
			}
		}

		
    }
    
    public void saveMap() {
        // TODO save the map
		saveConfigFiles();	// save config files
    }
	
	public void renameConfigFile (final JTree tree) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		final ConfigFile cf = (ConfigFile) node.getUserObject();
		
		String name = cf.getFile().getName();
		Object o = JOptionPane.showInputDialog(gui, "Veuillez entrer le nouveau nom :", "Renommer", JOptionPane.PLAIN_MESSAGE, null, null, name).toString();
		if (o == null) return;
		final String newName = o.toString();
		
		if (name != null) {
			if (!name.isEmpty()) {

				SwingWorker worker = new SwingWorker<Void, Void>() {
					public Void doInBackground() {
						try {
							cf.rename(newName);
							((DefaultTreeModel)tree.getModel()).nodeChanged(node);
						} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
						return null;
					}
					public void done() {
						gui.updateAllFilesComponents(tree);
					}
				};
				worker.execute();
			}
		}
	}
	
	public void deleteConfigFile(final JTree tree) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		final ConfigFile cf = (ConfigFile) node.getUserObject();
		
		int confirm = JOptionPane.showConfirmDialog(gui, "Cette action est irréversible. Continuer ?", "Supprimer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		if (confirm == JOptionPane.OK_OPTION) {

			SwingWorker worker = new SwingWorker<Void, Void>() {
				public Void doInBackground() {
					try {
						cf.delete();
						((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
					} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
					return null;
				}
			};
			worker.execute();
		}
	}
	
	public void addConfigFile (final JTree tree, final DataType dataType) {
		// get parent node from tree
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		ConfigFile parentCf = null;
		if (parentNode != null) parentCf = (ConfigFile) parentNode.getUserObject();
		
		// IMPORTANT NOTE :
		// the new file will be stored in the same directory as its parent, or in the globalConfigDir or mapConfigDir if there's no parent
		// the directory of the parent should be the globalConfigDir or mapConfigDir...
		// ...but we'll process that as same-directory-as-the-parent instead of global/mapConfigDir (for robustess)
		
		// GET NAME FROM USER INPUT
		
		String newName;
		do {
			Object o = JOptionPane.showInputDialog(gui, "Veuillez entrer le nom du nouveau fichier :", "Nouveau", JOptionPane.PLAIN_MESSAGE, null, null, ".xml");
			if (o == null) return;
			newName = o.toString();
		} while (newName.isEmpty());
		
		// GET DATA FORMAT from user input but constrained by parent file
		ConfigFile.DataFormat dataFormat;
		
		// testsets don't have parents
		if (dataType == DataType.TESTSET) {
			dataFormat = ConfigFile.DataFormat.FULLDATA;
			parentNode = null;
			parentCf = null;
		}
		// no parent, no parent
		else if (parentCf == null)
			dataFormat = ConfigFile.DataFormat.FULLDATA;
		// else asking user
		else {
			String msg = new String ("Voulez-vous associer le nouveau fichier à celui sélectionné :\n" + parentCf.getFile().getPath() + "\n\nSi oui, les données du fichier sélectionné seront héritées par le nouveau fichier");
			int answer = JOptionPane.showConfirmDialog(gui, msg, "Nouveau", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
			if (answer == JOptionPane.YES_OPTION)
				dataFormat = ConfigFile.DataFormat.DIFFDATA;
			else if (answer == JOptionPane.NO_OPTION) {
				dataFormat = ConfigFile.DataFormat.FULLDATA;
				
				// don't want a parent file ? okay, no parent file
				parentNode = null;
				parentCf = null;
			}
			else return;	// closed and unknown case : just get the hell out of here
		}
		
		// GET GLOBAL/MAP-LOCAL PARAMETER from user input (and constrained by parent file)
		boolean global = false;
		
		// testsets cannot be global
		if (dataType == DataType.TESTSET)
			global = false;
		// if there's a map-local parent, cannot be global
		else if (parentCf != null && ! parentCf.isGlobal())
			global = false;
		// no parent or global parent case : user is free to choose
		else {
			String msg = "Voulez-vous que le fichier soit disponible pour toutes les cartes ?";
			int answer = JOptionPane.showConfirmDialog(gui, msg, "Nouveau", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (answer == JOptionPane.YES_OPTION)
				global = true;
			else if (answer == JOptionPane.NO_OPTION)
				global = false;
			else return;	// closed and unknown case : just get the hell out of here
		}
		
		// CREATING THE FILE OBJECT from newName, parent and global
		File newFile;
		
		// no parent ? config directory
		if (parentCf == null) {
			if (global)
				newFile = new File (globalConfigDir, dataType.getConfigSubdir());	// concatenating directories
			else
				newFile = new File (currentMapConfigDir, dataType.getConfigSubdir());	// concatenating directories
			
			newFile = new File (newFile, newName);	// concatenating filename
		}
		// if there's a parent, we'll use same directory
		// this directory should be the same as config directory, but for robustness purpose we'll consider that's not the case
		else {
			// if same directory as parent (actually if parent has the same globality parameter)
			if ((parentCf.isGlobal() && global) || (! parentCf.isGlobal() && !global))
				newFile = new File (parentCf.getFile().getParent(), newName);
			// if not, that means that parent is global and the new file is local, so let's put it in mapConfigDir
			else {
				newFile = new File (currentMapConfigDir, dataType.getConfigSubdir());	// concatenating directories
				newFile = new File (newFile, newName);	// concatenating filename
			}
		}
		
		// CREATING THE PARENT PATH TO BE SET IN THE XML CONFIG
		
		// parent config file path is from the program current dir
		// we need it from the new file dir, and eventually with the %globaldir% alias
		String xmlParentPath = null;
		
		// no parent, no parent (so we test it before doin anything)
		if (dataFormat == ConfigFile.DataFormat.DIFFDATA) {
			xmlParentPath = new String();
			// if parent and new file are in the same directory, path is "./parentfile.xml"
			if ((parentCf.isGlobal() && global) || (! parentCf.isGlobal() && !global))
				xmlParentPath = xmlParentPath.concat("./" + parentCf.getFile().getName());
			// else, parent is global, and new file is map-local : path is "%globaldir%/parentfile.xml"
			else xmlParentPath = xmlParentPath.concat("%globaldir%/" + parentCf.getFile().getName());
		}
		
		// ASKING FOR LINKED FILES
		
		HashMap<DataType, String> xmlLinkedFiles = new HashMap<>();
		DataType[] linkedTypes = dataType.getLinkedFilesTypes();
		
		for (DataType type : linkedTypes) {
			boolean done = false;
			String linkedFile;
			do {
				// asking the user
				String msg = "Veuillez entrer un fichier " + type.getFileHumanReadableNameWithOfArticle();
				ConfigFile linkedCf = ConfigFileChooser.showConfigFileChooser(gui, configFilesExplorers.get(type).getTreeModel(), msg);
				if (linkedCf == null) return;	// canceled case
				linkedFile = linkedCf.getCanonicalPath();
				
				// checking globality coherency
				if (global && ! linkedCf.isGlobal()) {
					JOptionPane.showMessageDialog(gui, "Le fichier sélectionné est propre à une carte, alors que le fichier créé est global\nVeuillez choisir à nouveau", "Nouveau", JOptionPane.WARNING_MESSAGE);
					continue;
				}
				
				// comparing the beginning of the path to the config directories to find the right one
				boolean linkedFileGlobal;
				if (linkedFile.startsWith(globalConfigDirCanonical)) {
					linkedFile = linkedFile.substring(globalConfigDirCanonical.length());
					linkedFileGlobal = true;
				} else if (linkedFile.startsWith(currentMapConfigDirCanonical)) {
					linkedFile = linkedFile.substring(globalConfigDirCanonical.length());
					linkedFileGlobal = false;
				} else throw new IllegalStateException("The to-be-linked file is not in the global config directory, nor in the map-local one (file : " + linkedCf.getFile().getPath() + ")");
				
				// TODO [someday] as long as a correctly configured ConfigFileChooser is used, this check is useless (correctly configured means that it has a coherent TreeModel)
				
				// removing separator
				if (linkedFile.startsWith(File.separator))
					linkedFile = linkedFile.substring(File.separator.length());
				// testing the subdir
				if (! linkedFile.startsWith(type.getConfigSubdir())) throw new IllegalStateException("The to-be-linked file is not in the expected global config sub-directory (file : " + linkedCf.getFile().getPath() + ")");
				// adding a separator at the beginning
				if (! linkedFile.startsWith(File.separator))
					linkedFile = File.separator + linkedFile;
				
				// adding the beginning of the XML-stored path
				if (linkedFileGlobal)
					linkedFile = "%globaldir%" + linkedFile;
				else
					linkedFile = "../" + type.getConfigSubdir() + "/" + linkedFile;
				
				// TODO [delete] now that we use a ConfigFileChooser, that should be useless
				// verifying the file
				if (! linkedFile.isEmpty()) {
					File f;
					if (linkedFile.startsWith("%globaldir%")) {
						String tempLink = linkedFile.substring(11);
						tempLink = globalConfigDir.getPath().concat(tempLink);
						f = new File (tempLink);
					}
					else f = new File (newFile.getParentFile(), linkedFile);
					
					if (f.exists()) done = true;
					else JOptionPane.showMessageDialog(gui, "Aucun fichier à ce nom : " + linkedFile, "Fichier introuvable", JOptionPane.ERROR_MESSAGE);
				}
			} while (!done);
			
			// adding the file to the list
			xmlLinkedFiles.put(type, linkedFile);
		}
		
		
		// FINALIZING THE PARAMETERS cause we need all parameters final, because of the SwingWorker
		final DefaultMutableTreeNode f_parentNode = parentNode;	// needed to notify tree event
		final File f_newFile = newFile;							// the new File object
		final File f_globalConfigDir = globalConfigDir;			// the global config dir, to let resolve "%globaldir%"
		final String f_xmlParentPath = xmlParentPath;			// the parent XML relative path, null if no parent
		final boolean f_global = global;						// the isGlobal parameter
		final ConfigFile.DataFormat f_dataFormat = dataFormat;	// the dataformat (it's a bit redundant with parent nullity, but smarter)
		final HashMap<DataType, String> f_xmlLinkedFiles = xmlLinkedFiles;	// links to otherfiles
		
		SwingWorker worker = new SwingWorker<Void, Void>() {
			public Void doInBackground() {
				try {
					// check if a full reload of the JTree display will be needed
					// this will happen if the tree is empty before creation of the new file
					boolean fullReloadNeeded = ! ((DefaultMutableTreeNode)tree.getModel().getRoot()).children().hasMoreElements();
					
					// creating the file
					ConfigFile cf = ConfigFile.createFile(f_newFile, f_globalConfigDir.getPath(), f_xmlParentPath, f_xmlLinkedFiles, f_global, dataType, f_dataFormat);
					
					// linking the file to its parent
					DefaultMutableTreeNode node = configFilesExplorers.get(dataType).addFile(cf);
					
					// setting links to other files if needed
					for (DataType linkedType : dataType.getLinkedFilesTypes())
						ConfigFilesLinker.linkOneOfThoseFilesToAnotherOne(configFilesExplorers.get(linkedType).getFiles(), cf, linkedType);
					
					// notify the event to the GUI (the JTree component)
					
					// if the tree was empty, perform a full reload
					if (fullReloadNeeded) ((DefaultTreeModel)tree.getModel()).reload();
					// else we only notify about the inserted node
					else {
						// retrieving the index of the inserted node, needed to notify the event
						int[] indexes = {node.getParent().getIndex(node)};
						// notify
						((DefaultTreeModel)tree.getModel()).nodesWereInserted(node.getParent(), indexes);
					}
				} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
				return null;
			}
		};
		worker.execute();
	}
	
	public void generateTestItemSet (final ConfigFile testsetFile, final int nb, final ConfigFileTableModel model) {
		SwingWorker worker = new SwingWorker<Void, Void>() {
			public Void doInBackground() {
				try {
					ConfigData cd = new ConfigData(testsetFile.getLinkedFile(DataType.DRIVER_VEHICLE_ASSOCIATIONS));
					ArrayList<Object> associations = cd.getObjects();

					int currentId = 1;
					for (Object o : associations) {
						DriverVehicleAssociation association = (DriverVehicleAssociation) o;
						for (int i = 0 ; i < association.getProportion()*nb ; i++) {
							TestSetItem item = association.generateTestSetItem();
							item.setId(currentId);
							model.insertRow(item);
							currentId ++;
						}	
					}
				} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
				return null;
			}
		};
		worker.execute();
	}
	
	
	// BELOW ARE PRIVATE FUNCTIONS
	
	private void checkFile(File file) throws NoSuchFileException, AccessDeniedException {
		if (! file.exists())
			throw new NoSuchFileException ("Fichier introuvable : " + file.getPath());
		if (! file.isFile())
			throw new NoSuchFileException (file.getPath() + "n'est pas un fichier");
		if (! file.canRead())
			throw new AccessDeniedException ("Fichier inaccessible en lecture : " + file.getPath());
		if (! file.canWrite())
			throw new AccessDeniedException ("Fichier inaccessible en écriture : " + file.getPath());
	}
	
	private void checkDir(File file) throws NoSuchFileException, AccessDeniedException {
		if (! file.exists())
			throw new NoSuchFileException ("Répertoire introuvable : " + file.getPath());
		if (! file.isDirectory())
			throw new NoSuchFileException (file.getPath() + "n'est pas un répertoire");
		if (! file.canRead())
			throw new AccessDeniedException ("Répertoire inaccessible en lecture : " + file.getPath());
		if (! file.canWrite())
			throw new AccessDeniedException ("Répertoire inaccessible en écriture : " + file.getPath());
	}
	
	private void loadMap() throws IncorrectFormatException, InstantiationException {
		// TODO this is some experiemental code to load a map, but it is not complete (not even tested or functional)
		
		ConfigData roadsData = new ConfigData(currentMap, DataType.ROADS);
		ConfigData junctionsData = new ConfigData(currentMap, DataType.JUNCTIONS);
		ArrayList<?> roads =  roadsData.getObjects();
		ArrayList<?> junctions =  junctionsData.getObjects();
		RoadsDisplayPanel panel = new RoadsDisplayPanel((ArrayList<RoadConfig>) roads, (ArrayList<JunctionConfig>)junctions);
		gui.setRoadsDisplayPanel(panel);
	}
	
	private void loadConfigFiles() {
		final StatusBarItem item = new StatusBarItem("Parcours des fichiers de configuration");
		gui.addStatusBarItem(item);
		
		SwingWorker worker = new SwingWorker<Void, Integer>() {
			Exception e = null;
			
			public Void doInBackground() {
				File global;
				File map;
				
				try {
					ConfigFile.globalConfigDirectoryWithoutSubdir = globalConfigDirCanonical;
					
					global = new File (globalConfigDir,  DataType.DRIVERS.getConfigSubdir());
					map = new File (currentMapConfigDir, DataType.DRIVERS.getConfigSubdir());
					checkDir(global);
					checkDir(map);
					driversFilesExplorer = new ConfigFilesExplorer(global, map, DataType.DRIVERS);
					configFilesExplorers.put(DataType.DRIVERS, driversFilesExplorer);
					
					publish(20);
					
					global = new File (globalConfigDir,  DataType.VEHICLES.getConfigSubdir());
					map = new File (currentMapConfigDir, DataType.VEHICLES.getConfigSubdir());
					checkDir(global);
					checkDir(map);
					vehiclesFilesExplorer = new ConfigFilesExplorer(global, map, DataType.VEHICLES);
					configFilesExplorers.put(DataType.VEHICLES, vehiclesFilesExplorer);
					
					publish(40);
					
					global = new File (globalConfigDir,  DataType.DRIVER_VEHICLE_ASSOCIATIONS.getConfigSubdir());
					map = new File (currentMapConfigDir, DataType.DRIVER_VEHICLE_ASSOCIATIONS.getConfigSubdir());
					checkDir(global);
					checkDir(map);
					proportionsFilesExplorer = new ConfigFilesExplorer(global, map, DataType.DRIVER_VEHICLE_ASSOCIATIONS);
					configFilesExplorers.put(DataType.DRIVER_VEHICLE_ASSOCIATIONS, proportionsFilesExplorer);
					
					publish(60);
					
					map = new File (currentMapConfigDir, DataType.TESTSET.getConfigSubdir());
					checkDir(global);
					checkDir(map);
					testsetsFilesExplorer = new ConfigFilesExplorer(null, map, DataType.TESTSET);
					configFilesExplorers.put(DataType.TESTSET, testsetsFilesExplorer);
					
					publish(80);
					
					for (DataType type : DataType.class.getEnumConstants())
						for (DataType linkedType : type.getLinkedFilesTypes())
							ConfigFilesLinker.linkThoseFilesToOtherOnes(configFilesExplorers.get(linkedType).getFiles(), configFilesExplorers.get(type).getFiles(), linkedType);
					
					publish(95);
				}
				catch (NoSuchFileException | AccessDeniedException e) {
					GlobalErrorHandler.notifyNonCriticalException(e);
					publish(-1);
				} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
				
				return null;
			}
			protected void process(List<Integer> i) {
				int value = i.get(i.size() - 1);
				if (value == -1)
					item.setFailed();
				else
					item.setProgression(value);
			}
			public void done() {
				if (driversFilesExplorer != null) gui.setDriversTreeModel(driversFilesExplorer.getTreeModel());
				if (vehiclesFilesExplorer != null) gui.setVehiclesTreeModel(vehiclesFilesExplorer.getTreeModel());
				if (proportionsFilesExplorer != null) gui.setProportionsTreeModel(proportionsFilesExplorer.getTreeModel());
				if (testsetsFilesExplorer != null) gui.setTestsetsTreeModel(testsetsFilesExplorer.getTreeModel());
				
				gui.enableVehiclesAndDrivers(true);
				item.setProgression(100);
			}
		};
		
		worker.execute();
	}
	
	private void saveConfigFiles() {
		final StatusBarItem item = new StatusBarItem("Sauvegarde des fichiers de configuration");
		gui.addStatusBarItem(item);
		
		SwingWorker worker = new SwingWorker<Void, Integer>() {
			public Void doInBackground() {
				try  {
					JTree trees[] = gui.getVehiclesAndDriversTrees();
					int processedCount = 0;

					for (JTree tree : trees) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
						if (node != null) {
							ConfigFile file = (ConfigFile) node.getUserObject();
							try {file.save();} catch (IOException ex) {GlobalErrorHandler.notifyNonCriticalException(ex);}
						}
						processedCount ++;
						publish(processedCount/trees.length);
					}
					publish(100);
				} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
				return null;
			}
			protected void process(List<Integer> i) {
				item.setProgression(i.get(i.size() - 1));
			}
		};
		
		worker.execute();
	}
	
	private class MapFileFilter extends FileFilter {
		public boolean accept(File f) {
			if (f.isFile())
				return f.getPath().endsWith(".map.xml");
			return true;
		}
		
		public String getDescription() {
			return ("Cartes");
		}
	}
}
