/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.data;

import config.types.roads.*;
import config.types.vehicles.*;

/**
 *
 * @author Sylvain
 */


// describes all existing types (that occur in configfiles)

// each type is associated to a class (in config.types.*)

// for example of those classes, look in config.types.*
// they must implement XMLNode, and eventually Heritable
// they must also contain something like :

/*
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
		}
	};
	
	private final static ConfigObjectManipulator configObjectManipulator = new ConfigObjectManipulator (DriverConfig.class, standardAttributes, "driver");
	public static ConfigObjectManipulator getObjectManipulator() {return configObjectManipulator;}
*/

public enum DataType {
	DRIVERS (DriverConfig.class, DriverConfig.getObjectManipulator(), "drivers", "drivers", "de conducteurs"),
	VEHICLES (VehicleConfig.class, VehicleConfig.getObjectManipulator(), "vehicles", "vehicles", "de véhicules"),
	DRIVER_VEHICLE_ASSOCIATIONS (DriverVehicleAssociation.class, DriverVehicleAssociation.getObjectManipulator(), "proportions", "associations", "d'associations véhicules-conducteurs", DRIVERS, VEHICLES),
	TESTSET (TestSetItem.class, TestSetItem.getObjectManipulator(), "testsets", "testset", "de jeu de test", DRIVER_VEHICLE_ASSOCIATIONS),
	MAP(null, null, "../", "map", "de carte"),
	ROADS(RoadConfig.class, RoadConfig.getObjectManipulator(), null, "roads", null),
	ROADWAYS(RoadWayConfig.class, RoadWayConfig.getObjectManipulator(), null, null, null),
	COORDINATES(CoordinatesConfig.class, CoordinatesConfig.getObjectManipulator(), null, null, null),
	RULES(RulesConfig.class, RulesConfig.getObjectManipulator(), null, null, null),
	JUNCTIONS(JunctionConfig.class, JunctionConfig.getObjectManipulator(), null, "junctions", null),
	JUNCTION_ROADEND(JunctionRoadendConfig.class, JunctionRoadendConfig.getObjectManipulator(), null, null, null),
	TRAFFIC_LIGHTS(TrafficLightConfig.class, TrafficLightConfig.getObjectManipulator(), null, null, null);
	
	
	private Class<?> associatedClass;
	private ConfigObjectManipulator standardObjectManipulator;
	private String configSubdir;
	private String xmlDataNode;
	private String fileHumanReadableNameWithOfArticle;
	private DataType[] linkedFilesTypes;

	private DataType(Class<?> associatedClass, ConfigObjectManipulator standardObjectManipulator, String configSubdir, String xmlDataNode, String humanReadableNameWithOfArticle, DataType... linkedFilesTypes) {
		this.associatedClass = associatedClass;
		this.standardObjectManipulator = standardObjectManipulator;
		this.configSubdir = configSubdir;
		this.xmlDataNode = xmlDataNode;
		this.fileHumanReadableNameWithOfArticle = humanReadableNameWithOfArticle;
		this.linkedFilesTypes = linkedFilesTypes;
	}
	
	public boolean hasAssociatedClass() {
		if (associatedClass == null) return false;
		return true;
	}
	public Class<?> getAssociatedClass() throws IllegalStateException {
		if (associatedClass == null)
			throw new IllegalStateException ("There is no class (null) associated to this DataType, which means it represents only files but not data (i.e. the file contains several data types, all data are declared as other types)");
		return associatedClass;
	}
	public ConfigObjectManipulator getObjectManipulator () throws IllegalStateException  {
		if (standardObjectManipulator == null) throw new IllegalStateException ("There is no object manipulator (null) associated to this DataType, which means it represents only files but not data");
		return standardObjectManipulator;
	}
	public String getConfigSubdir () {
		if (configSubdir == null) throw new IllegalStateException ("There is no config subdir (null) associated to this DataType, which means it represents only data but not file (i.e. the file contains several data types, the file is declared as another type)");
		return configSubdir;
	}
	public boolean hasXMLDataNode () {
		if (xmlDataNode == null) return false;
		return true;
	}
	public String getXMLDataNode () {
		if (xmlDataNode == null) throw new IllegalStateException ("There is no XML data node (null) associated to this DataType, which means it is directly contained in the nodes of another type");
		return xmlDataNode;
	}
	
	public String getFileHumanReadableNameWithOfArticle() {
		if (fileHumanReadableNameWithOfArticle == null) throw new IllegalStateException ("There is no config subdir (null) associated to this DataType, which means it represents only data but not file (i.e. the file contains several data types, the file is declared as another type)");
		return fileHumanReadableNameWithOfArticle;
	}
	public DataType[] getLinkedFilesTypes () {return linkedFilesTypes;}
	public static DataType getDataTypeFromClass (Class<?> classToMatch) throws IllegalStateException {
		for (DataType dt : DataType.class.getEnumConstants())
			if (dt.hasAssociatedClass())
				if (dt.getAssociatedClass().equals(classToMatch))
					return dt;
		return null;
	}
}
