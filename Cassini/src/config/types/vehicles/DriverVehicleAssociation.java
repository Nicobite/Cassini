/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.vehicles;

import config.data.ConfigObjectManipulator;
import config.data.Heritable;
import config.data.XMLNode;
import org.jdom2.Element;

/**
 *
 * @author Sylvain
 */
public class DriverVehicleAssociation implements XMLNode, Heritable {
	
	// ALL ATTRIBUTES IN THE LIST BELOW MUST BE INITIALIZED, BEFORE THE END OF THE CONSTRUCTOR, TO ALLOW THE OBJECT TO BE DISPLAYABLE IMMEDIATELY
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((DriverVehicleAssociation)o) .id;}
			protected void set(Object o, Object value) { ((DriverVehicleAssociation)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("ID véhicule", "vehicle", Integer.class) {
			protected Object get(Object o) {return ((DriverVehicleAssociation)o) .vid;}
			protected void set(Object o, Object value) { ((DriverVehicleAssociation)o) .vid = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("ID conducteur", "driver", Integer.class) {
			protected Object get(Object o) {return ((DriverVehicleAssociation)o) .did;}
			protected void set(Object o, Object value) { ((DriverVehicleAssociation)o) .did = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Proportion", "proportion", Float.class) {
			protected Object get(Object o) {return ((DriverVehicleAssociation)o) .proportion;}
			protected void set(Object o, Object value) { ((DriverVehicleAssociation)o) .proportion = (float)value ;}
		},
	};
	
	public final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (DriverVehicleAssociation.class, standardAttributes, "association");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private int id = 0;
	private Element xmlNode = null;
	private DriverVehicleAssociation parent = null;
	
	
	private int vid = 0;
	private int did = 0;
	private float proportion = 0;
	
	public DriverVehicleAssociation() {
		
	}
	
	public DriverVehicleAssociation getParent() {return parent;}
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public float getProportion() {return proportion;}
	
	public DriverVehicleAssociation getNewSon() {
		DriverVehicleAssociation d = new DriverVehicleAssociation();
		
		d.vid = vid;
		d.did = did;
		d.proportion = proportion;
		
		d.id = id;
		d.parent = this;
		
		return d;
	}
	
	public TestSetItem generateTestSetItem() {
		return new TestSetItem (id, vid, did);
	}
}
