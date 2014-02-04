/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.roads;

import config.data.ConfigObjectManipulator;
import config.data.XMLNode;
import config.types.vehicles.DriverConfig;
import simulation.Coordinates;
import org.jdom2.Element;

/**
 *
 * @author Sylvain
 */
public class CoordinatesConfig implements XMLNode {
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((CoordinatesConfig)o) .id;}
			protected void set(Object o, Object value) { ((CoordinatesConfig)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("x", "x", Integer.class) {
			protected Object get(Object o) {return ((CoordinatesConfig)o) .x;}
			protected void set(Object o, Object value) { ((CoordinatesConfig)o) .x = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("y", "y", Integer.class) {
			protected Object get(Object o) {return ((CoordinatesConfig)o) .y;}
			protected void set(Object o, Object value) { ((CoordinatesConfig)o) .y = (int)value ;}
		}
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (CoordinatesConfig.class, standardAttributes, "coordinates");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private Element xmlNode = null;
	
	private int id = 0;
	
	private int x;
    private int y;

	public CoordinatesConfig() {
		
	}
	
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}

	public int getId() {
		return id;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	
	public Coordinates generateCoordinates() {
		return new Coordinates(x, y);
	}
}
