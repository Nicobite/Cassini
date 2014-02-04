/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.files;

/**
 *
 * @author Sylvain
 */

// an exception to be thrown when a ConfigFile does not respect its format
public class IncorrectFormatException extends Exception {
	public IncorrectFormatException (String msg) {super (msg);}
}
