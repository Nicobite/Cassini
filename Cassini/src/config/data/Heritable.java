/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.data;

/**
 *
 * @author Sylvain
 */

// the interface Heritable has nothing to do with Java inheritance : 
//		it allows data elements to inherit data from a given element (which is called "parent")

public interface Heritable {
	public Heritable getParent();
	public Heritable getNewSon();
	public int getId();
}
