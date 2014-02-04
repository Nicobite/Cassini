/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.roads;

import config.data.ConfigObjectManipulator;
import config.data.XMLNode;
import config.files.IncorrectFormatException;
import config.types.vehicles.DriverConfig;
import java.util.ArrayList;
import java.util.HashMap;
import org.jdom2.Element;
import simulation.road.Junction;
import simulation.road.Junction.CrossroadsIndication;
import simulation.road.Road;
import simulation.road.TrafficLight;

/**
 *
 * @author Sylvain
 */
public class JunctionConfig implements XMLNode {
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((JunctionConfig)o) .id;}
			protected void set(Object o, Object value) { ((JunctionConfig)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Nom", "road", JunctionRoadendConfig.class, true) {
			protected Object get(Object o) {return ((JunctionConfig)o) .roadsEnds;}
			protected void set(Object o, Object value) { ((JunctionConfig)o) .roadsEnds.put(((JunctionRoadendConfig)value).getId(), (JunctionRoadendConfig)value);}
		}
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (JunctionConfig.class, standardAttributes, "junction");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private Element xmlNode = null;
	
	private int id = 0;
	
	// Roads crossing on the junction
    private HashMap<Integer, JunctionRoadendConfig> roadsEnds = new HashMap();
	
	public JunctionConfig() {
		
	}
    
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}
	
	public Junction generateJunction(ArrayList<Road> knownRoads) throws IncorrectFormatException {
		ArrayList<Road> roads = new ArrayList<>();
		ArrayList<CrossroadsIndication> indications = new ArrayList<>();
		ArrayList<TrafficLight> trafficLights = new ArrayList<>();
		
		for (int i = 0 ; i < roads.size() ; i++) {
			JunctionRoadendConfig jrc = roadsEnds.get(i);
			if (jrc == null) throw new IncorrectFormatException("The IDs for the roads joining at a junction are not contiguous or do not start at zero");
			roads.add(knownRoads.get(jrc.getRoadId()));
			indications.add(jrc.getIndication());
			trafficLights.add(jrc.getTrafficLight().generateTrafficLight());
		}
		return new Junction (roads, indications, trafficLights);
	}
}
