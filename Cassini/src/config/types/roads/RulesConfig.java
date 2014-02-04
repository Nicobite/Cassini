/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.types.roads;

import config.data.ConfigObjectManipulator;
import config.data.XMLNode;
import config.types.vehicles.DriverConfig;
import org.jdom2.Element;
import simulation.road.Rule;

/**
 *
 * @author Sylvain
 */
public class RulesConfig implements XMLNode {
	
	private final static ConfigObjectManipulator.StandardAttribute standardAttributes[] = {
		new ConfigObjectManipulator.StandardAttribute ("Limite de vitesse", "speedlimit", Integer.class) {
			protected Object get(Object o) {return ((RulesConfig)o) .speedLimit;}
			protected void set(Object o, Object value) { ((RulesConfig)o) .speedLimit = (int)value ;}
		},
		new ConfigObjectManipulator.StandardAttribute ("Danger", "danger", Boolean.class) {
			protected Object get(Object o) {return ((RulesConfig)o) .danger;}
			protected void set(Object o, Object value) { ((RulesConfig)o) .danger = (boolean)value ;}
		}
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (RulesConfig.class, standardAttributes, "rules");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
	
	// some attributes required by interfaces
	private Element xmlNode = null;
	
	// Defines the limit of speed on the roads
    private int speedLimit = 50;
    // Specifies if there is a roadsign indicating a danger on the road
    private boolean danger = false;

	public RulesConfig() {
		
	}
	
	public Element getXMLNode() {return xmlNode;}
	public void setXMLNode(Element xmlNode) {this.xmlNode = xmlNode;}
	
	public Rule generateRule () {
		return new Rule(speedLimit, danger);
	}
}
