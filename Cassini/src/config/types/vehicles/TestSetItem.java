package config.types.vehicles;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import config.data.ConfigObjectManipulator;
import config.data.XMLNode;
import org.jdom2.Element;

/**
 *
 * @author Sylvain
 */
public class TestSetItem implements XMLNode {
	
	// ALL ATTRIBUTES IN THE LIST BELOW MUST BE INITIALIZED, BEFORE THE END OF THE CONSTRUCTOR, TO ALLOW THE OBJECT TO BE DISPLAYABLE IMMEDIATELY
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("ID", "id", Integer.class) {
			protected Object get(Object o) {return ((TestSetItem)o) .id;}
			protected void set(Object o, Object value) { ((TestSetItem)o) .id = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("ID véhicule", "vehicle", Integer.class) {
			protected Object get(Object o) {return ((TestSetItem)o) .vid;}
			protected void set(Object o, Object value) { ((TestSetItem)o) .vid = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("ID conducteur", "driver", Integer.class) {
			protected Object get(Object o) {return ((TestSetItem)o) .did;}
			protected void set(Object o, Object value) { ((TestSetItem)o) .did = (int)value ;}
		},
	};
	
	public final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (TestSetItem.class, standardAttributes, "item");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private int id = 0;
	private Element xmlNode = null;
	
	private int vid = 0;
	private int did = 0;
	
	public TestSetItem() {
		
	}
	
	public TestSetItem(int id, int vid, int did) {
		this.id = id;
		this.vid = vid;
		this.did = did;
	}

	public int getVid() {return vid;}
	public int getDid() {return did;}
	public void setVid(int vid) {this.vid = vid;}
	public void setDid(int did) {this.did = did;}
	
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
}
