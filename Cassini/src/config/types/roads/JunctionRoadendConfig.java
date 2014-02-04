/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.roads;

import config.data.ConfigObjectManipulator;
import config.data.XMLNode;
import config.types.vehicles.DriverConfig;
import org.jdom2.Element;
import simulation.road.Junction;
import simulation.road.Junction.CrossroadsIndication;

/**
 *
 * @author Sylvain
 */
public class JunctionRoadendConfig implements XMLNode {
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((JunctionRoadendConfig)o) .id;}
			protected void set(Object o, Object value) { ((JunctionRoadendConfig)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("ID Route", "road", Integer.class) {
			protected Object get(Object o) {return ((JunctionRoadendConfig)o) .roadId;}
			protected void set(Object o, Object value) { ((JunctionRoadendConfig)o) .roadId = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Signalisation", "indication", CrossroadsIndication.class) {
			protected Object get(Object o) {return ((JunctionRoadendConfig)o) .indication;}
			protected void set(Object o, Object value) { ((JunctionRoadendConfig)o) .indication = (CrossroadsIndication)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Feu tricolore", "trafficlight", TrafficLightConfig.class) {
			protected Object get(Object o) {return ((JunctionRoadendConfig)o) .trafficLight;}
			protected void set(Object o, Object value) { ((JunctionRoadendConfig)o) .trafficLight = (TrafficLightConfig)value ;}
		}
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (JunctionRoadendConfig.class, standardAttributes, "road");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private Element xmlNode = null;
	
	private int id = 0;
	private int roadId;
	private CrossroadsIndication indication;
	private TrafficLightConfig trafficLight;

	public JunctionRoadendConfig() {
		
	}
	
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}

	public int getId() {
		return id;
	}

	public int getRoadId() {
		return roadId;
	}

	public Junction.CrossroadsIndication getIndication() {
		return indication;
	}

	public TrafficLightConfig getTrafficLight() {
		return trafficLight;
	}
}
