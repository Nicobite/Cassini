/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.vehicles;

import config.data.ConfigObjectManipulator;
import config.data.Heritable;
import config.data.XMLNode;
import org.jdom2.Element;
import simulation.data.Vehicle;

/**
 *
 * @author Sylvain
 */
public class VehicleConfig implements XMLNode, Heritable {
	
	// ALL ATTRIBUTES IN THE LIST BELOW MUST BE INITIALIZED, BEFORE THE END OF THE CONSTRUCTOR, TO ALLOW THE OBJECT TO BE DISPLAYABLE IMMEDIATELY
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .id;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Constructeur", "constructor", String.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .constructor;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .constructor = (String)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Modèle", "model", String.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .model;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .model = (String)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Largeur", "width", Integer.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .width;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .width = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Longeur", "length", Integer.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .length;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .length = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Hauteur", "height", Integer.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .height;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .height = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Poids", "weight", Integer.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .weight;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .weight = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Vitesse max", "maxspeed", Integer.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .maxspeed;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .maxspeed = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Accélération max", "maxacc", Float.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .maxacc;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .maxacc = (Float)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Décélération max", "maxdec", Float.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .maxdec;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .maxdec = (Float)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Age", "age", Integer.class) {
			protected Object get(Object o) {return ((VehicleConfig)o) .age;}
			protected void set(Object o, Object value) { ((VehicleConfig)o) .age = (Integer)value ;}
		},
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (VehicleConfig.class, standardAttributes, "vehicle");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private int id = 0;
	private Element xmlNode = null;
	private VehicleConfig parent = null;
	
	
	private String constructor = "";
	private String model = "";
    // In cm
	private int width = 100;
	private int length = 100;
	private int height = 100;
    // In kg
	private int weight = 100;
	// In km/h
	private int maxspeed = 100;
	// In m/s²
	private float maxacc = 1.0f;
	private float maxdec = 1.0f;
    // In month
	private int age = 1;
	
	
	
	public VehicleConfig() {
		
	}
	
	public VehicleConfig getParent() {return parent;}
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public VehicleConfig getNewSon() {
		VehicleConfig v = new VehicleConfig();
		
		v.constructor = new String (constructor);
        v.model = new String (model);
        v.width = width;
        v.length = length;
        v.height = height;
        v.weight = weight;
        v.maxspeed = maxspeed;
        v.maxacc = maxacc;
        v.maxdec = maxdec;
        v.age = age;
		
		v.id = id;
		v.parent = this;
		
		return v;
	}
	
	public Vehicle generateVehicle() {
		return new Vehicle(constructor, model, width, length, height, weight, maxspeed, maxacc, maxdec, age);
	}
}
