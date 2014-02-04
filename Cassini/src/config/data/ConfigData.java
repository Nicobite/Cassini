/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.data;

import config.files.ConfigFile;
import config.files.IncorrectFormatException;
import java.util.ArrayList;
import org.jdom2.Element;
import org.jdom2.JDOMException;

/**
 *
 * @author Sylvain
 */

// a class that provides easy access to the data contained in a ConfigFile

public class ConfigData {
	private ConfigFile file;
	private ConfigObjectManipulator objectManipulator;
	private Element xmlDataNode;
	private DataType subnodeType;
	
	public ConfigData(ConfigFile file) {
		this.file = file;
		objectManipulator = file.getDataType().getObjectManipulator();
		xmlDataNode = file.getXMLDataNode();
		subnodeType = null;
	}
	
	public ConfigData(ConfigFile file, DataType subnodeType) {
		this.file = file;
		if (subnodeType != null) {
			objectManipulator = subnodeType.getObjectManipulator();
			xmlDataNode = file.getXMLDataNode().getChild(subnodeType.getXMLDataNode());
		}
		else {
			objectManipulator = file.getDataType().getObjectManipulator();
			xmlDataNode = file.getXMLDataNode();
		}
		this.subnodeType = subnodeType;
	}
	
	public ArrayList<Object> getObjects() throws IncorrectFormatException, InstantiationException {
		ArrayList<Object> objects = new ArrayList<>();
		
		if (file.getDataFormat() == ConfigFile.DataFormat.DIFFDATA) {
			ConfigData cd = new ConfigData(file.getParent(), subnodeType);
			ArrayList<Object> parentObjects = cd.getObjects();
			
			for (Element e : xmlDataNode.getChildren())
				objects.add(objectManipulator.diffFromXML(e, parentObjects));
			for (Object o : parentObjects) {
				objects.add(((Heritable)o).getNewSon());
			}
		}
		else {
			for (Element e : xmlDataNode.getChildren())
				objects.add(objectManipulator.fromXML(e));
		}
		
		return objects;
	}
	
	public void updateObject(Object o) {
		objectManipulator.toXML(o);
		file.setModified();
	}
	
	public void addObject(Object o) throws IllegalArgumentException{
		Element newNode = objectManipulator.createXMLNode(o);
		objectManipulator.toXML(o);
		xmlDataNode.addContent(newNode);
		file.setModified();
	}
	
	public void removeObject(Object o) throws IllegalArgumentException, JDOMException {
		if (! xmlDataNode.removeContent(objectManipulator.getXMLNode(o)))
			throw new JDOMException("Removal failed (perhaps because the data is already removed from the file)");
		file.setModified();
	}
}
