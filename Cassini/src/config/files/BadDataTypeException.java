/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.files;

/**
 *
 * @author Sylvain
 */

// exception to be thrown when a ConfigFile does not contain the expected type of data
public class BadDataTypeException extends Exception {
	public BadDataTypeException (String msg) {super (msg);}
}
