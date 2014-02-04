/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.vehicles;

import config.data.ConfigObjectManipulator;
import config.data.Heritable;
import config.data.XMLNode;
import org.jdom2.Element;
import simulation.data.Person;

/**
 *
 * @author Sylvain
 */
public class DriverConfig implements XMLNode, Heritable {
	
	// ALL ATTRIBUTES IN THE LIST BELOW MUST BE INITIALIZED, BEFORE THE END OF THE CONSTRUCTOR, TO ALLOW THE OBJECT TO BE DISPLAYABLE IMMEDIATELY
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .id;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Nom", "name", String.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .name;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .name = (String)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Prénom", "surname", String.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .surname;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .surname = (String)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Age", "age", Integer.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .age;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .age = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Attention", "awareness", Integer.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .awareness;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .awareness = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Convivialité", "kindness", Integer.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .kindness;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .kindness = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Agressivité", "aggressivity", Integer.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .aggressivity;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .aggressivity = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Irritabilité", "irritability", Integer.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .irritability;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .irritability = (Integer)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Niveau de conduite", "levelOfDriving", Integer.class) {
			protected Object get(Object o) {return ((DriverConfig)o) .levelOfDriving;}
			protected void set(Object o, Object value) { ((DriverConfig)o) .levelOfDriving = (Integer)value ;}
		},
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (DriverConfig.class, standardAttributes, "driver");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private int id = 0;
	private Element xmlNode = null;
	private DriverConfig parent = null;
	
	
	private String name = "";
	private String surname = "";
	private int age = 42;
    // for the following attributes : squale on 100, avereage at 50
	private int awareness = 50;
	private int kindness = 50;
	private int aggressivity = 50;
	private int irritability = 50;
	private int levelOfDriving = 50;
	
	public DriverConfig() {
		
	}
	
	public DriverConfig getParent() {return parent;}
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public DriverConfig getNewSon() {
		DriverConfig d = new DriverConfig();
		
		d.name = name;
        d.surname = surname;
        d.age = age;
        d.awareness = awareness;
        d.kindness = kindness;
        d.aggressivity = aggressivity;
        d.irritability = irritability;
        d.levelOfDriving = levelOfDriving;
		
		d.id = id;
		d.parent = this;
		
		return d;
	}
	
	public Person generatePerson() {
		return new Person (name, surname, age, awareness, kindness, aggressivity, irritability, levelOfDriving);
	}
}
