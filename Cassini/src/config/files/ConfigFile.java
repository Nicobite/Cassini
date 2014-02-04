/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.files;

import config.data.DataType;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import main.GlobalErrorHandler;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
/**
 *
 * @author Sylvain
 */

// TODO [someday] ensure that linked files are all set (for that the DataType.getLinkedFilesTypes() method can be uses)
// TODO [someday] ensure that a parent file is set if needed
// TODO [someday] maybe this class can be made abstract, and one subclass could be created per data type. This would allow better control of the types of the ConfigFile objects passed to data-type-specific functions)

public class ConfigFile {
	/****************************** TYPES ******************************/
	
	// indicates if the data inherits some other data (DIFFDATA), or not (FULLDATA)
	public enum DataFormat {FULLDATA, DIFFDATA};
	
	// exception thrown when on deletion of a file which has children
	public class HasDependantFilesException extends Exception {
		public HasDependantFilesException (String msg) {super (msg);}
	}
	
	
	/****************************** STATIC MEMBERS ******************************/
	
	private static final String XML_ROOT_NAME = "cassini";
	public static String globalConfigDirectoryWithoutSubdir = null;
	
	
	/****************************** ATTRIBUTES ******************************/
	
	// ABOUT THE FILE
	private File file;		// the file from filesystem
	private String canonicalPath = null;	// stores file.getCanonicalPath() to avoid dealing with IOException each time it's needed
	
	// ABOUT THE PARENT/CHILDREN FILES, IF ANY
	private ConfigFile parentFile = null;	// the file from which data is inherited, if any
	private ArrayList<ConfigFile> children = new ArrayList<>();	// the files that inherit their data from this file
	private String parentFileAsStoredInConfig = null;					// the parent file as stored in the configuration ()
	private String parentFileAsStoredInConfigCanonical = null;	// the same but canonical (unicity is needed for comparison, in order to match a ConfigFile)
	private String globalConfigDir;	// this is used to replace the %globaldir% in pathes stored in the configuration file (peut être null si et seulement si la notion de configuration globale n'a pas de sens pour ce type de fichier)
	
	// ABOUT THE LINKED FILES
	private HashMap<DataType, ConfigFile> linkedFiles = new HashMap<>();	// stores the linked ConfigFile as set by the ConfigFilesLinker class
	private HashMap<DataType, String> linkedFilesAsStoredInConfig = new HashMap<>();	// stores the linked files pathes as written in this file
	private HashMap<DataType, String> linkedFilesAsStoredInConfigCanonical = new HashMap<>();	// stores the linked files pathes as written in this file
	private ArrayList<ConfigFile> linkerFiles = new ArrayList<>();	// remembers the files which depends from this one
	
	// ABOUT THE DATA DESCRIPTION
	private DataType dataType = null;		// the data type
	private DataFormat dataFormat = null;	// indicates if the data inherits some other data
	private boolean isGlobal;				// indicates if the file is available to all maps, or if it's map-local
	
	// ABOUT THE DATA
	private Element xmlRoot = null;		// the XML root node
	private Element xmlDataNode = null;	// the XML node containing (i.e. right above) the data
	private Document xmlDoc = null;		// the XML document
	private boolean modified = false;	// remembers whether the file has been modified since its last saving
	
	
	/****************************** CONSTRUCTORS ******************************/
	
	// a constructor that do not check the data type (protected for robustess)
	protected ConfigFile (File file, boolean isGlobal, String globalConfigDir) throws NoSuchFileException, AccessDeniedException, JDOMException, IOException, IncorrectFormatException {
		try {init (file, isGlobal, globalConfigDir, null);}
		catch (BadDataTypeException e) {GlobalErrorHandler.notifyUnexpectedException(e);}	// that should never happen
	}
	
	// constructor
	public ConfigFile (File file, boolean isGlobal, String globalConfigDir, DataType expectedDataType) throws NoSuchFileException, AccessDeniedException, JDOMException, IOException, IncorrectFormatException, BadDataTypeException {
		init (file, isGlobal, globalConfigDir, expectedDataType);
	}
	
	
	/****************************** ACCESSORS AND ASSIMILATED ******************************/
	
	// GETTING FILE INFO
	public File getFile () {return file;}
	public String getCanonicalPath () {return canonicalPath;}
	
	// MANIPULATING PARENTHOOD THINGS
	public ConfigFile getParent () {return parentFile;}	// return the parent
	// functions below are used by a ConfigFileExplorer to associate a parent ConfigFile from the parentFileFromConfig (String) stored in the configuration
	public String getParentAsStoredInConfigCanonical() {return parentFileAsStoredInConfigCanonical;}
	public boolean isParent (ConfigFile potentialParent) {
		return potentialParent.getCanonicalPath().equals(parentFileAsStoredInConfigCanonical);
	}
	public void addChild(ConfigFile child) {children.add(child);}
	public void setParent(ConfigFile parent) throws IllegalStateException {
		if (parentFile != null) throw new IllegalStateException("Le fichier de configuration a déjà un parent (fichier : " + file.getPath() + ")");
		parentFile = parent;
	}
	
	// MANIPULATING LINKED FILES
	// returns a linked file
	public ConfigFile getLinkedFile(DataType dataType) {
		ConfigFile f = linkedFiles.get(dataType);
		if (f == null) throw new IllegalStateException("A linked file is null and shouldn't be");
		return f;
	}
	// functions below are used by a ConfigFileLinker to set linked files from configuration String pathes
	public String getLinkedFileAsStoredInConfigCanonical(DataType dataType) {
		String path = linkedFilesAsStoredInConfigCanonical.get(dataType);
		if (path == null) throw new IllegalStateException("A linked file path is null and shouldn't be");
		return path;
	}
	public void setLinkedFile(DataType dataType, ConfigFile cf) {linkedFiles.put(dataType, cf);}
	public void addLinkerFile(ConfigFile file) {linkerFiles.add(file);}
	
	// GETTING DATA DESCRIPTION
	public DataType getDataType () {return dataType;}
	public DataFormat getDataFormat () {return dataFormat;}
	public boolean isGlobal () {return isGlobal;}
	
	// MANIPULATING DATA
	public Element getXMLDataNode () {return xmlDataNode;}
	public void setModified() {modified = true;}
	
	// A CONVERSION TO HUMAN READABLE NAME (to be displayed in the GUI's trees)
	public String toString() {return file.getName();}
	
	
	/****************************** PUBLIC OPERATIONS ******************************/
	
	public static ConfigFile createFile (File newFile, String globalConfigDir, String xmlParentPath, HashMap<DataType, String> xmlLinkedFiles, boolean isGlobal, DataType dataType, DataFormat dataFormat) throws FileAlreadyExistsException, IOException {
		// well, does it already exists ?
		if (newFile.exists())
			throw new FileAlreadyExistsException("Un fichier avec le nom \"" + newFile.getName() + "\" existe déjà");
		
		// creating XML structure
		Element rootNode = new Element(XML_ROOT_NAME);
		Element subroot = null;
		if (dataFormat == DataFormat.FULLDATA)
			subroot = new Element("fulldata");
		else if (dataFormat == DataFormat.DIFFDATA) {
			subroot = new Element("fulldata");
			subroot.setAttribute("path", xmlParentPath);
			rootNode.addContent(subroot);
			subroot = new Element("diffdata");
		}
		rootNode.addContent(subroot);
		
		// adding linked files
		Iterator<Map.Entry<DataType, String>> iterator = xmlLinkedFiles.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<DataType, String> entry = iterator.next();
			Element e = new Element(entry.getKey().getXMLDataNode());
			e.setAttribute("path", entry.getValue());
			subroot.addContent(e);
		}
		
		// adding parent file
		subroot.addContent(new Element(dataType.getXMLDataNode()));
		
		// writing the file
		Document doc = new Document (rootNode);
		XMLOutputter xmlOutputter = new XMLOutputter (Format.getPrettyFormat());
		FileOutputStream fos = new FileOutputStream (newFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		xmlOutputter.output(doc, bos);
		fos.close();
		
		// now creating the ConfigFile object
		ConfigFile cf = null;
		try {cf = new ConfigFile(newFile, isGlobal, globalConfigDir);}
		catch (JDOMException | IncorrectFormatException ex) {GlobalErrorHandler.notifyUnexpectedException(ex);}	// that should never happen cause we made the XML structure ourselves
		
		return cf;
	}
	
	public void rename (String name) throws FileAlreadyExistsException, IOException {
		// a descriptor for the new file
		File newFile = new File (file.getParentFile(), name);
		
		// checking
		if (newFile.exists())
			throw new FileAlreadyExistsException("Un fichier avec le nom \"" + name + "\" existe déjà");
		// renaming
		if (! file.renameTo(newFile))
			throw new IOException("Impossible de renommer le fichier, raison inconnue");
		
		file = newFile;
		canonicalPath = file.getCanonicalPath();
		
		ArrayList<ConfigFile> corruptedFiles = new ArrayList<>();
		
		// renaming parent in the children config
		for (ConfigFile child : children) {
			try {child.renameParentFile (name);}
			catch (IOException e) {corruptedFiles.add(child);}
		}
		
		// renaming linked file in the linkers config
		for (ConfigFile linker : linkerFiles) {
			try {linker.renameLinkedFile (dataType, name);}
			catch (IOException e) {corruptedFiles.add(linker);}
		}
		
		// problem handling (while manipulating children)
		if (! corruptedFiles.isEmpty()) {
			StringBuilder s = new StringBuilder();
			for (ConfigFile child : corruptedFiles)
				s.append("\n\t" + child.file.getPath());
			throw new IOException("Impossible de reporter le nouveau nom dans les fichiers dépendants (raison inconnue), une partie d'entre eux comporte encore l'ancien nom. Fichiers corrumpus :" + s.toString());
		}
	}
	
	public void delete () throws IOException, HasDependantFilesException {
		if (! children.isEmpty() || ! linkerFiles.isEmpty())
			throw new HasDependantFilesException("Le fichier \"" + file.getName() + "\" ne peut être supprimé car des fichiers dépendent de lui");
		
		Files.delete(file.toPath());
		
		// removing references to this file from parent file
		if (parentFile != null) parentFile.children.remove(this);
		
		// removing references to this file from linked files
		Iterator<ConfigFile> iterator = linkedFiles.values().iterator();
		while (iterator.hasNext()) {
			ConfigFile linkedFile = iterator.next();
			linkedFile.linkerFiles.remove(this);
		}
	}
	
	public void save () throws IOException {
		if (modified) {
			XMLOutputter xmlOutputter = new XMLOutputter (Format.getPrettyFormat());
			FileOutputStream fos = new FileOutputStream (file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			xmlOutputter.output(xmlDoc, bos);
			fos.close();
		}
		modified = false;
	}
	
	
	/****************************** PRIVATE OPERATIONS ******************************/
	
	private void init (File file, boolean isGlobal, String globalConfigDir, DataType expectedDataType) throws NoSuchFileException, AccessDeniedException, JDOMException, IOException, IncorrectFormatException, BadDataTypeException {
		this.file = file;
		this.canonicalPath = file.getCanonicalPath();
		this.isGlobal = isGlobal;
		this.globalConfigDir = globalConfigDir;
		
		// verifying the file existence and permissions
		checkFile();
		
		// reading the file as XML
		SAXBuilder xmlBuilder = new SAXBuilder();
		xmlDoc = xmlBuilder.build(file);
		xmlRoot = xmlDoc.getRootElement();
		
		// parse file to extract data
		parseMainElements();
		
		// check that parent file is not absolute, and computes its canonical path
		if (parentFileAsStoredInConfig != null)
			computeParentCanonicalPathFromConfigPath();
		
		// compute absolute pathes of linked files
		computeLinkedFilesCanonicalPathFromConfigPath();
		
		// check that data type is the expected one
		if (expectedDataType != null)
			if (expectedDataType != dataType)
				throw new BadDataTypeException ("Le fichier contient des données de " + dataType.toString().toLowerCase() + " alors que " + expectedDataType.toString().toLowerCase() + " est attendu (fichier :  " + file.getPath() + ")");
		
		performTypeSpecificChecks();
	}
	
	private void performTypeSpecificChecks() throws IncorrectFormatException {
		// check that any testset file is not diffdata
		if (dataType == DataType.TESTSET) {
			if (dataFormat == DataFormat.DIFFDATA)
				throw new IncorrectFormatException ("Un fichier de jeu de test ne peut pas avoir de parent (fichier : " + file.getPath() + ")");
			if (isGlobal)
				throw new IncorrectFormatException ("Un fichier de jeu de test ne peut pas être global (fichier : " + file.getPath() + ")");
		}
	}
				
	private void checkFile () throws NoSuchFileException, AccessDeniedException {
		if (! file.exists())
			throw new NoSuchFileException ("Fichier introuvable : " + file.getPath());
		if (! file.isFile())
			throw new NoSuchFileException (file.getPath() + "n'est pas un fichier");
		if (! file.canRead())
			throw new AccessDeniedException ("Fichier inaccessible en lecture : " + file.getPath());
		if (! file.canWrite())
			throw new AccessDeniedException ("Fichier inaccessible en écriture : " + file.getPath());
	}
	
	private void renameParentFile (String name) throws IOException {
		parentFileAsStoredInConfig = getDirectoryOfSomeFileAsStoredInConfig(parentFileAsStoredInConfig).concat(name);
		
		// modify the XML data
		Element e = xmlRoot.getChild("fulldata");
		e.setAttribute ("path", parentFileAsStoredInConfig);
		
		setModified();
		save();
		
		computeParentCanonicalPathFromConfigPath();
	}
	
	private void renameLinkedFile (DataType type, String name) throws IOException {
		String linkedFileFromConfig = linkedFilesAsStoredInConfig.get(type);
		linkedFileFromConfig = getDirectoryOfSomeFileAsStoredInConfig(linkedFileFromConfig);
		linkedFileFromConfig = linkedFileFromConfig.concat(name);
		linkedFilesAsStoredInConfig.put(type, linkedFileFromConfig);
		
		// modify the XML data
		Element e = xmlRoot.getChild("fulldata").getChild(type.getXMLDataNode());
		e.setAttribute ("path", linkedFileFromConfig);
		
		setModified();
		save();
		
		computeLinkedFilesCanonicalPathFromConfigPath();
	}
	
	private void parseMainElements () throws IncorrectFormatException {
		String rootDataPath = null;
		boolean hasRootData = false;
		boolean hasDiffData = false;
		
		// checking root name
		if (! xmlRoot.getName().equals(XML_ROOT_NAME)) throw new IncorrectFormatException ("Racine XML invalide (fichier : " + file.getPath() + ")");
		
		List<Element> elements = xmlRoot.getChildren();
		
		// checking elements that are right under root : diffdata & fulldata
		for (Element e : elements) {
			// fulldata processing
			if (e.getName().equals("fulldata")) {
				// unicité
				if (hasRootData || rootDataPath != null) throw new IncorrectFormatException ("L'élément \"fulldata\" doit être unique (fichier : " + file.getPath() + ")");
				
				// attributes retrieval
				List<Attribute> atts = e.getAttributes();
				
				// if some attributes are present, we're in the "diffdata" case (if everything goes fine)
				if (! atts.isEmpty()) {
					for (Attribute a : atts) {
						// checking the non-mixing of diffdata and fulldata
						if (! e.getChildren().isEmpty()) throw new IncorrectFormatException ("L'élément \"fulldata\" ne doit pas contenir à la fois des éléments et des attributs (fichier : " + file.getPath() + ")");
						// cheking unkonwn attribute
						if (! a.getName().equals("path")) throw new IncorrectFormatException ("Attribut \""+a.getName()+"\"inconnu pour l'élément \"fulldata\" (fichier : " + file.getPath() + ")");
						// unicity of the "path" attribute
						if (rootDataPath != null) throw new IncorrectFormatException ("Plusieurs attributs \"path\" pour l'élément \"fulldata\" (fichier : " + file.getPath() + ")");
						
						// data retrieval
						rootDataPath = a.getValue();
						
						// if there are several attributes (what should not happen), they are detected as either unknown, or duplicate of the "path" attribute
					}
				}
				// if no attributes : case "fulldata"
				else hasRootData = true;
			}
			// diffdata processing
			else if (e.getName().equals("diffdata")) {
				// unicity
				if (hasDiffData) throw new IncorrectFormatException ("L'élément \"diffdata\" doit être unique (fichier : " + file.getPath() + ")");
				
				// checking there are no attributes
				List<Attribute> atts = e.getAttributes();
				if (! atts.isEmpty()) throw new IncorrectFormatException ("L'élément \"diffdata\" ne doit pas contenir d'attributs (fichier : " + file.getPath() + ")");
				
				// data retrieval
				hasDiffData = true;
			}
			// checking unknown element
			else throw new IncorrectFormatException ("Elément \""+e.getName()+"\" inconnu (fichier : " + file.getPath() + ")");
			
			// checking the non-mix of diffdata and fulldata
			if (hasDiffData && hasRootData) throw new IncorrectFormatException ("Un même fichier ne doit pas contenir des données dans les éléments \"fulldata\" et \"diffdata\" (fichier : " + file.getPath() + ")");
			// case diffdata only : checking the presence of a link to fulldata
			if (hasDiffData && rootDataPath == null) throw new IncorrectFormatException ("Un fichier avec des données \"diffdata\" doit contenir un lien vers un ficher \"fulldata\" (fichier : " + file.getPath() + ")");
		}
		
		// setting the file properties
		if (hasDiffData) {
			dataFormat = DataFormat.DIFFDATA;
			parentFileAsStoredInConfig = rootDataPath;
		}
		else dataFormat = DataFormat.FULLDATA;
		
		// if there are data to parse
		// getting again the right-under-the-root node (<diffdata> or <fulldata>), cause' we gonna parse it
		Element subroot;
		if (dataFormat == DataFormat.FULLDATA) subroot = xmlRoot.getChild("fulldata");
		else subroot = xmlRoot.getChild("diffdata");
		
		// let's go for a loop
		elements = subroot.getChildren();
		// parse XML nodes
		for (Element e : elements) {
			boolean matched = false;
			
			// compare to each DataType
			for (DataType dt : DataType.class.getEnumConstants()) {
				if (dt.hasXMLDataNode()) {
					if (dt.getXMLDataNode().equals(e.getName())) {
						matched = true;
						List<Attribute> attributes = e.getAttributes();

						// no attributes mean that it's the data node (whenever there's children or not)
						if (attributes.isEmpty()) {
							// checking unicity and lonelyness
							if (dataType != null) throw new IncorrectFormatException ("Un fichier ne doit contenir qu'un seul type de données (fichier : " + file.getPath() + ")");
							dataType = dt;
							xmlDataNode = e;
						}
						// attributes mean it's a link node (so no children nodes must be present)
						else if (e.getChildren().isEmpty()) {
							if (attributes.size() > 1) throw new IncorrectFormatException ("L'élément \""+e.getName()+"\" ne peut contenir qu'un seul attribut à la fois (fichier : " + file.getPath() + ")");
							Attribute a = attributes.get(0);
							if (! a.getName().equals("path")) throw new IncorrectFormatException ("L'attribut de l'élément \""+e.getName()+"\" n'a pas pour nom \"path\" (fichier : " + file.getPath() + ")");
							linkedFilesAsStoredInConfig.put(dt, a.getValue().trim());
						}
						// else mean both children and attributes are present
						else throw new IncorrectFormatException ("L'élément \""+e.getName()+"\" ne peut contenir à la fois des attributs et d'autres éléments (fichier : " + file.getPath() + ")");
					}
				}
			}
			if (!matched) throw new IncorrectFormatException ("Elément \""+e.getName()+"\" inconnu (fichier : " + file.getPath() + ")");
			
			// the file should contain only one node right under <diffdata> or <fulldata>
			// any bonus node will match as unknown (if it is) or at the beginning of the second loop
			// (actually the loop right after the one that matched a known element)
		}
	}
	/* TODO [remove] old version
	private void setParentCanonicalPathFromConfig () throws IOException {
		File parentFileFile;
		
		// when using the alias %globaldir% we need to replace it
		if (parentFileFromConfig.startsWith("%globaldir%")) {
			if (globalConfigDir == null) throw new NullPointerException("Le répertoire de configuration globale n'est pas défini (null) et aurait du l'être");
			String parentFilePath = parentFileFromConfig.substring(11);
			parentFileFile = new File(globalConfigDir, parentFilePath);
		}
		// otherwise
		else {
			parentFileFile = new File (file.getParentFile(), parentFileFromConfig);
		}
		
		// removing any . or .. and affecting to attribute
		parentFileCanonicalPathFromConfig = parentFileFile.getCanonicalPath();
	}
	
	private void setLinkedFilesCanonicalPathesFromConfig () throws IOException {
		Iterator<Map.Entry<DataType, String>> iterator = linkedFilesFromConfig.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<DataType, String> entry = iterator.next();
			File linkedFileFile;
			String linkedFileFromConfig = entry.getValue();
			
			// when using the alias %globaldir% we need to replace it
			if (linkedFileFromConfig.startsWith("%globaldir%")) {
				if (globalConfigDirectoryWithoutSubdir == null) throw new NullPointerException("Le répertoire de configuration globale n'est pas défini (null) et aurait du l'être");
				String linkedFilePath = linkedFileFromConfig.substring(11);
				linkedFileFile = new File(globalConfigDirectoryWithoutSubdir, linkedFilePath);
			}
			// otherwise
			else {
				linkedFileFile = new File (file.getParentFile(), linkedFileFromConfig);
			}
			
			// removing any . or .. and affecting to attribute
			linkedFilesCanonicalPathesFromConfig.put(entry.getKey(), linkedFileFile.getCanonicalPath());
		}
	}*/
	
	private void computeParentCanonicalPathFromConfigPath () throws IOException {
		parentFileAsStoredInConfigCanonical = computeCanonicalPathFromConfigPath(parentFileAsStoredInConfig, globalConfigDir);
	}

	private void computeLinkedFilesCanonicalPathFromConfigPath () throws IOException {
		Iterator<Map.Entry<DataType, String>> iterator = linkedFilesAsStoredInConfig.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<DataType, String> entry = iterator.next();
			String linkedFileFromConfig = entry.getValue();
			linkedFilesAsStoredInConfigCanonical.put(entry.getKey(), computeCanonicalPathFromConfigPath(linkedFileFromConfig, globalConfigDirectoryWithoutSubdir));
		}
	}

	private String computeCanonicalPathFromConfigPath(String fileFromConfig, String globalDir) throws IOException {
		File fileObject;
		
		// when using the alias %globaldir% we need to replace it
		if (fileFromConfig.startsWith("%globaldir%")) {
			if (globalDir == null) throw new NullPointerException("Le répertoire de configuration globale n'est pas défini (null) et aurait du l'être");
			String filePath = fileFromConfig.substring(11);
			fileObject = new File(globalDir, filePath);
		}
		// otherwise
		else {
			fileObject = new File (file.getParentFile(), fileFromConfig);
		}

		// removing any . or .. and affecting to attribute
		return fileObject.getCanonicalPath();
	}
	
	private String getDirectoryOfSomeFileAsStoredInConfig(String configDirectory) {
		int separatorIndex1, separatorIndex2;
		
		// getting the last '/' separator position
		if (configDirectory.endsWith("/"))
			separatorIndex1 = configDirectory.lastIndexOf('/', configDirectory.length()-2);
		else
			separatorIndex1 = configDirectory.lastIndexOf('/');
		
		// getting the last '\' separator position
		if (configDirectory.endsWith("\\"))
			separatorIndex2 = configDirectory.lastIndexOf('\\', configDirectory.length()-2);
		else
			separatorIndex2 = configDirectory.lastIndexOf('\\');
		
		// truncating at the right-most separator position
		if (separatorIndex1 > 0 || separatorIndex2 > 0)
			configDirectory = configDirectory.substring(0, Math.max(separatorIndex1, separatorIndex2) + 1);
		
		return configDirectory;
	}
}
