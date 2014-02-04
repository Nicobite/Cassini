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
import simulation.Coordinates;
import org.jdom2.Element;
import simulation.environment.ZoneEnvironment;
import simulation.road.Junction;
import simulation.road.Road;
import simulation.road.RoadWay;
import simulation.road.Rule;

/**
 *
 * @author Sylvain
 */
public class RoadConfig implements XMLNode {
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((RoadConfig)o) .id;}
			protected void set(Object o, Object value) { ((RoadConfig)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Nom", "name", String.class) {
			protected Object get(Object o) {return ((RoadConfig)o) .name;}
			protected void set(Object o, Object value) { ((RoadConfig)o) .name = (String)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Voies", "roadway", RoadWayConfig.class, true) {
			protected Object get(Object o) {return ((RoadConfig)o) .roadWaysConfig;}
			protected void set(Object o, Object value) { ((RoadConfig)o) .roadWaysConfig.put(((RoadWayConfig)value).getId(), (RoadWayConfig)value);}
		},
		new ConfigObjectManipulator.StandardAttribute ("Coordonnées", "coordinates", CoordinatesConfig.class, true) {
			protected Object get(Object o) {return ((RoadConfig)o) .coordinatesConfig;}
			protected void set(Object o, Object value) { ((RoadConfig)o) .coordinatesConfig.put(((CoordinatesConfig)value).getId(), (CoordinatesConfig)value);}
		},
		new ConfigObjectManipulator.StandardAttribute ("Longueur", "length", Float.class) {
			protected Object get(Object o) {return ((RoadConfig)o) .length;}
			protected void set(Object o, Object value) { ((RoadConfig)o) .length = (Float)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("ID jonction début", "junctionbegin", Integer.class) {
			protected Object get(Object o) {return ((RoadConfig)o) .junctionBeginId;}
			protected void set(Object o, Object value) { ((RoadConfig)o) .junctionBeginId = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("ID jonction fin", "junctionend", Integer.class) {
			protected Object get(Object o) {return ((RoadConfig)o) .junctionEndId;}
			protected void set(Object o, Object value) { ((RoadConfig)o) .junctionEndId = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Règles", "rules", RulesConfig.class) {
			protected Object get(Object o) {return ((RoadConfig)o) .rules;}
			protected void set(Object o, Object value) { ((RoadConfig)o) .rules = (RulesConfig)value ;}
		}
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (RoadConfig.class, standardAttributes, "road");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private Element xmlNode = null;
	
	private int id = 0;
	
	// Name of the road :
    private String name;
    // Roadways of the road :
    private HashMap<Integer, RoadWayConfig> roadWaysConfig = new HashMap();
    // Coordinates of the points of the road :
    private HashMap<Integer, CoordinatesConfig> coordinatesConfig = new HashMap();
    // Length of the road
    private float length;
    // Junction indicating the beginning of the road
    private int junctionBeginId;
    // Junction indicating the end of the road
    private int junctionEndId;
    // Rules currently applied to the road
    private RulesConfig rules;

	public RoadConfig() {
		
	}
	
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}
	
	public HashMap<Integer, CoordinatesConfig> getCoordinates() {return coordinatesConfig;}
	public HashMap<Integer, RoadWayConfig> getRoadWays() {return roadWaysConfig;}
	
	public Road generateRoad(ArrayList<Junction> junctions) throws IncorrectFormatException {
		ArrayList<RoadWay> roadWays = new ArrayList<>();
		ArrayList<Coordinates> coordinates = new ArrayList<>();
		
		for (int i = 0 ; i < coordinates.size() ; i++) {
			CoordinatesConfig cc = coordinatesConfig.get(i);
			if (cc == null) throw new IncorrectFormatException("The IDs for the coordinates of a road are not contiguous or do not start at zero");
			coordinates.add(cc.generateCoordinates());
		}
		
		Road road = new Road(name, roadWays, coordinates, junctions.get(junctionBeginId), junctions.get(junctionEndId), rules.generateRule(), new ArrayList<ZoneEnvironment>());
		
		for (int i = 0 ; i < roadWaysConfig.size() ; i++) {
			RoadWayConfig rwc = roadWaysConfig.get(i);
			if (rwc == null) throw new IncorrectFormatException("The IDs for the ways of a road are not contiguous or do not start at zero");
			roadWays.add(rwc.generateRoadWay(road));
		}
		
		return road;
	}
}
