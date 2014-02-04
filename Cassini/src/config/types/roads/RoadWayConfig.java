/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.roads;

import config.data.ConfigObjectManipulator;
import config.data.XMLNode;
import config.types.vehicles.DriverConfig;
import org.jdom2.Element;
import simulation.road.Road;
import simulation.road.RoadWay;

/**
 *
 * @author Sylvain
 */
public class RoadWayConfig implements XMLNode {
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((RoadWayConfig)o) .id;}
			protected void set(Object o, Object value) { ((RoadWayConfig)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Largeur", "width", Integer.class) {
			protected Object get(Object o) {return ((RoadWayConfig)o) .width;}
			protected void set(Object o, Object value) { ((RoadWayConfig)o) .width = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Direction", "direction", Integer.class) {
			protected Object get(Object o) {return ((RoadWayConfig)o) .direction;}
			protected void set(Object o, Object value) { ((RoadWayConfig)o) .direction = (int)value ;}
		},
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (RoadWayConfig.class, standardAttributes, "roadway");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private Element xmlNode = null;
	
	// Identifier of the RoadWay (left from junctionBegin of the road is number 1)
    private int id;
    // Width of the RoadWay
    private int width;
    // Direction of the RoadWay
    private int direction;

	public RoadWayConfig() {
		
	}
	
	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getDirection() {
		return direction;
	}
	
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}
	
	public RoadWay generateRoadWay(Road road) {
		return new RoadWay(id, width, direction, road);
	}
}
