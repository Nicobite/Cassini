/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.data;

import org.jdom2.Element;

/**
 *
 * @author Sylvain
 */

// an interface for all types that can occur in ConfigFiles

public interface XMLNode {
	Element getXMLNode();
	void setXMLNode(Element e);
}
