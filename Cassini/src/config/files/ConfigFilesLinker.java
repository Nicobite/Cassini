/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.files;

import config.data.DataType;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

/**
 *
 * @author Sylvain
 */
public class ConfigFilesLinker {
	
	public static void linkThoseFilesToOtherOnes(ArrayList<ConfigFile> potentialLinkedFiles, ArrayList<ConfigFile> linkers, DataType linkedFileType) throws NoSuchFileException {
		for (ConfigFile linker : linkers)
			linkOneOfThoseFilesToAnotherOne(potentialLinkedFiles, linker, linkedFileType);
	}
	
	public static void linkOneOfThoseFilesToAnotherOne(ArrayList<ConfigFile> potentialLinkedFiles, ConfigFile linker, DataType linkedFileType) throws NoSuchFileException {
		String linkedFilePath = linker.getLinkedFileAsStoredInConfigCanonical(linkedFileType);
		
		for (ConfigFile potentialLinkedFile : potentialLinkedFiles) {
			if (potentialLinkedFile.getCanonicalPath().equals(linkedFilePath)) {
				linker.setLinkedFile(linkedFileType, potentialLinkedFile);
				potentialLinkedFile.addLinkerFile(linker);
				break;
			}
		}
		
		try {linker.getLinkedFile(linkedFileType);}
		catch (IllegalStateException e) {throw new NoSuchFileException("Could not resolve the link to file \"" + linkedFilePath + "\" referenced by file \"" + linker.getCanonicalPath() + "\"");}
	}
}
