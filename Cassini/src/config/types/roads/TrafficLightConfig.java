/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.roads;

import config.data.ConfigObjectManipulator;
import config.data.XMLNode;
import config.types.vehicles.DriverConfig;
import java.util.HashMap;
import org.jdom2.Element;
import simulation.road.TrafficLight;
import simulation.road.TrafficLight.Status;

/**
 *
 * @author Sylvain
 */
public class TrafficLightConfig implements XMLNode {
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("Heure d'allumage", "starttime", Integer.class) {
			protected Object get(Object o) {return ((TrafficLightConfig)o) .startTime;}
			protected void set(Object o, Object value) { ((TrafficLightConfig)o) .startTime = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Heure d'extinction", "endtime", Integer.class) {
			protected Object get(Object o) {return ((TrafficLightConfig)o) .endTime;}
			protected void set(Object o, Object value) { ((TrafficLightConfig)o) .endTime = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Durée au vert", "greenduration", Integer.class) {
			protected Object get(Object o) {return ((TrafficLightConfig)o) .greenDuration;}
			protected void set(Object o, Object value) { ((TrafficLightConfig)o) .greenDuration = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Durée à l'orange", "orangeduration", Integer.class) {
			protected Object get(Object o) {return ((TrafficLightConfig)o) .orangeDuration;}
			protected void set(Object o, Object value) { ((TrafficLightConfig)o) .orangeDuration = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Durée au rouge", "redduration", Integer.class) {
			protected Object get(Object o) {return ((TrafficLightConfig)o) .redDuration;}
			protected void set(Object o, Object value) { ((TrafficLightConfig)o) .redDuration = (int)value ;}
		}
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (TrafficLightConfig.class, standardAttributes, "trafficlight");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private Element xmlNode = null;
	
	// Time of the beginning of the traffic light
    private int startTime = 0;
    // Time of the end of the traffic light
    private int endTime = 0;
    // Duration of the different status
	private int greenDuration = 20;
	private int orangeDuration = 5;
	private int redDuration = 25;
	
	public TrafficLightConfig() {
		
	}
	
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}

	public TrafficLight generateTrafficLight() {
		HashMap<Status, Integer> statusDuration = new HashMap<>();
		statusDuration.put(Status.Green, greenDuration);
		statusDuration.put(Status.Orange, orangeDuration);
		statusDuration.put(Status.Red, redDuration);
		return new TrafficLight(startTime, endTime, statusDuration);
	}
}
