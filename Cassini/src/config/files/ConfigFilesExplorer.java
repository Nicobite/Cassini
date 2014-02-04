/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.files;

import config.data.DataType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import main.GlobalErrorHandler;
import org.jdom2.JDOMException;

/**
 *
 * @author Sylvain
 */
public class ConfigFilesExplorer {
	
	// when a parent file is not in the configuration directories (whether it exists or not outside those directories is not checked)
	public class NotInDirectoryException extends Exception {
		public NotInDirectoryException (String msg) {super(msg);}
	}
	
	// when no file is of format "fulldata", i.e. when all files inherits their data and there's no "root" file in the hierarchy
	public class NoFullDataException extends Exception {
		public NoFullDataException (String msg) {super(msg);}
	}
	
	private ArrayList<ConfigFile> discoveredFiles = new ArrayList<>();	// where files are stored during exploration
	private ArrayList<ConfigFile> processedFiles = new ArrayList<>();	// where files are moved after exploration and during (parent-to-son) linking
	private DataType expectedDataType;			// the datatype to be checked
	private DefaultTreeModel treeModel = null;	// the treemodel that can be retrieved after files are explored (i.e. after this object is constructed)
	private HashMap<ConfigFile, DefaultMutableTreeNode> nodes = new HashMap<>();
	File globalFilesLocation;	// path of global files
	File mapFilesLocation;		// path of map-local files
	ArrayList<Throwable> nonCriticalExceptions = new ArrayList<>();	// stores encountered exceptions
	
	// some accessors
	public DefaultTreeModel getTreeModel() {return treeModel;}
	public ArrayList<ConfigFile> getFiles() {return processedFiles;}
	
	// the constructor : explores and link files (this is a time-consuming task, so be sure to run it in a worker thread)
	// files linking is about a parentship link (i.e. one file inherits data of another file), but there's no mix between files types (use ConfigFilesLinker for that)
	
	// note that locations can be null
	public ConfigFilesExplorer (File globalFilesLocation, File mapFilesLocation, DataType expectedDataType) throws NoFullDataException, NotInDirectoryException {
		this.expectedDataType = expectedDataType;
		this.globalFilesLocation = globalFilesLocation;
		this.mapFilesLocation = mapFilesLocation;
		
		// explore
		if (globalFilesLocation != null) exploreDir(globalFilesLocation, true);
		if (mapFilesLocation != null) exploreDir(mapFilesLocation, false);
		
		// link
		makeATree();
	}
	
	// a recursive function that browse a directory and all its subdirs
	// it assumes that all files in that directory are of the same type (expectedDataType)
	private void exploreDir (File dir, boolean isGlobal) {
		File[] files = dir.listFiles();
		
		// browsing the directory
		for (File f : files) {
			// browse any subdir
			if (f.isDirectory())
				exploreDir (f, isGlobal);
			// add file if no exception
			else if (f.isFile()) {
				ConfigFile cf = null;
				
				try {
					if (globalFilesLocation != null) cf = new ConfigFile(f, isGlobal, globalFilesLocation.getPath(), expectedDataType);
					else cf = new ConfigFile(f, isGlobal, null, expectedDataType);
				}
				catch (IOException | JDOMException | IncorrectFormatException | BadDataTypeException e) {
					GlobalErrorHandler.notifyNonCriticalException(e);
				}
				
				if (cf != null) discoveredFiles.add(cf);
			}
		}
	}
	
	private void makeATree() throws NoFullDataException, NotInDirectoryException {
		// some clones to allow modifying the ArrayList while iterating them
		ArrayList<ConfigFile> discoveredFilesClone;
		
		// tree model basics
		DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode();
		treeModel = new DefaultTreeModel(treeRoot);
		
		// first add root files (i.e. fulldata)
		discoveredFilesClone = (ArrayList<ConfigFile>)discoveredFiles.clone();
		
		for (ConfigFile f : discoveredFilesClone) {
			if (f.getDataFormat() == ConfigFile.DataFormat.FULLDATA) {
				discoveredFiles.remove(f);
				processedFiles.add(f);
				addFileToTree(f, null);
			}
		}
		
		// checking emptyness
		if (processedFiles.isEmpty() && ! discoveredFiles.isEmpty())
			throw new NoFullDataException ("Aucun des dossiers de configuration de contient de fichier de données brutes (fulldata)");
		
		// the loop below tries to associate files with already matched files
		// at first loop will be added the first-level-childhood files
		// at second loop, second-level-childhood ones
		// etc.
		
		// while it has some usefulness (i.e. while it remains links between processed and non-processed files)
		int remainingCount = discoveredFiles.size();
		int previousCount = -1;
		
		while (remainingCount != previousCount) {
			// updating the clones arrays
			discoveredFilesClone = (ArrayList<ConfigFile>)discoveredFiles.clone();
			
			// for each non-linked file
			for (ConfigFile fileToLink : discoveredFilesClone)
				if (linkFileToParent(fileToLink))
					discoveredFiles.remove(fileToLink);
			
			// updating counters
			previousCount = remainingCount;
			remainingCount = discoveredFiles.size();
		}
		
		// if it remains files, they have no parent in the configuration directory
		if (remainingCount != 0) throw new NotInDirectoryException ("Le fichier " + discoveredFiles.get(0).getCanonicalPath() + " pointe vers un fichier qui n'est pas dans les dossier de configuration (" + discoveredFiles.get(0).getParentAsStoredInConfigCanonical() + ") qui n'est pas dans les dossiers de configuration");
	}
	
	private void addFileToTree(ConfigFile f, ConfigFile parent) {
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(f);
		
		nodes.put(f, treeNode);
		
		if (parent != null) nodes.get(parent).add(treeNode);
		else ((DefaultMutableTreeNode)treeModel.getRoot()).add(treeNode);
	}
	
	public boolean linkFileToParent(ConfigFile fileToLink) {
		boolean matched = false;
		
		// browse every file to check paternity
		for (ConfigFile potentialParent : processedFiles)
			if (fileToLink.isParent(potentialParent)) {
				potentialParent.addChild(fileToLink);
				fileToLink.setParent(potentialParent);
				addFileToTree(fileToLink, potentialParent);
				matched = true;
			}
		
		if (matched)
			processedFiles.add(fileToLink);
		
		return matched;
	}
	
	public DefaultMutableTreeNode addFile(ConfigFile file) {
		if (file.getParentAsStoredInConfigCanonical() == null) {
			addFileToTree(file, null);
			processedFiles.add(file);
		}
		else linkFileToParent(file);
		
		return nodes.get(file);
	}
}

