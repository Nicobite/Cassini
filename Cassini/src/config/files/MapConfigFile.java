/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import org.jdom2.JDOMException;

/**
 *
 * @author Sylvain
 */
public class MapConfigFile extends ConfigFile {
	public MapConfigFile (File file) throws NoSuchFileException, AccessDeniedException, JDOMException, IOException, IncorrectFormatException {
		super (file, false, null);
	}
}
