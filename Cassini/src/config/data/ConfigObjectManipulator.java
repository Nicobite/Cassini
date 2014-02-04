/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.data;

import config.files.IncorrectFormatException;
import java.util.List;
import org.jdom2.Element;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Sylvain
 */


// handle a data element from a ConfigFile, independently from its type

// a data element is made of several attributes (each is represented by a StandardAttribute, a private inner class)
// the interface Heritable has nothing to do with Java inheritance : 
//		it allows data elements to inherit data from a given element (which is called "parent")
// diffFromXml() get the data from a Heritable element
//		(it gets data from the child element, or from the parent for attributes which does not occur in the child)

public class ConfigObjectManipulator {
	
	private Class objectClass;
	private StandardAttribute attributes[];
	private String xmlNodeName;
	
	public ConfigObjectManipulator (Class objectClass, StandardAttribute attributes[], String xmlNodeName) {
		this.objectClass = objectClass;
		this.attributes = attributes;
		this.xmlNodeName = xmlNodeName;
	}

	public Class getObjectClass () {return objectClass;}
	public String getXMLNodeName () {return xmlNodeName;}
	
	public int getAttributesCount () {return attributes.length;}
	public Class getAttributeClass(int attributeIndex) {return attributes[attributeIndex].getAttributeClass();}
	public String getReadableName(int attributeIndex) {return attributes[attributeIndex].getReadableName();}
	public Element getXMLNode(Object manipulatedObject) throws ClassCastException {
		Element e = null;
		try {e = ((XMLNode)manipulatedObject).getXMLNode();}
		catch (ClassCastException ex) {throw new ClassCastException ("Trying to get an XML node from an object which does not implement the XMLNode interface (" + ex.getMessage() + ")");}
		return e;
	}
	
	public Object get(Object manipulatedObject, int attributeIndex) throws ClassCastException {
		checkClass(manipulatedObject);
		return attributes[attributeIndex].getAttribute(manipulatedObject);
	}

	public void set(Object manipulatedObject, int attributeIndex, Object attributeValue) throws IllegalArgumentException {
		checkClass(manipulatedObject);
		attributes[attributeIndex].setAttribute(manipulatedObject, attributeValue);
	}
	
	public Object fromXML(Element xmlNode) throws IncorrectFormatException, InstantiationException {
		Object manipulatedObject = null;
		try {manipulatedObject = objectClass.newInstance();}
		catch (IllegalAccessException | InstantiationException e) {throw new InstantiationException("Impossible d'instancier un nouvel objet");}
		
		processFromXML(manipulatedObject, xmlNode, true);
		return manipulatedObject;
	}
	
	public Object diffFromXML(Element xmlNode, ArrayList<Object> parentObjects) throws IncorrectFormatException, InstantiationException {	
		List<Element> idNodes = xmlNode.getChildren("id");
		if (idNodes.size() > 1)
			throw new IncorrectFormatException ("Plusieurs champs \"id\"");
		else if (idNodes.isEmpty())
			throw new IncorrectFormatException ("Pas de champ \"id\"");
		long id = Long.parseLong (idNodes.get(0).getTextTrim());
		
			Object parentObject = null;
			for (Object o : parentObjects)
				try {
					if (((Heritable)o).getId() == id) {
						parentObject = o;
						break;
					}
				} catch (ClassCastException e) {throw new ClassCastException ("Trying to get inherited data for a parent object which does not implement the Heritable interface (" + e.getMessage() + ")");}
			checkClass(parentObject);
			parentObjects.remove(parentObject);
			
		
		Object manipulatedObject;
		if (parentObject == null)
			manipulatedObject = fromXML(xmlNode);
		else {
			manipulatedObject = ((Heritable)parentObject).getNewSon();
			processFromXML(manipulatedObject, xmlNode, false);
		}
		return manipulatedObject;
	}
	
	public void toXML(Object manipulatedObject) throws NullPointerException, IllegalArgumentException, ClassCastException {
		checkClass(manipulatedObject);
		
		Element xmlNode = getXMLNode(manipulatedObject);
		if (xmlNode == null) throw new NullPointerException("Pointeur vers l'élément XML null");
			
		
		for (StandardAttribute a : attributes) {
			xmlNode.removeChild(a.getXMLName());
			Iterator<Object> iterator;
			
			if (a.getCanBeMultiple()) {
				if (a.get(manipulatedObject) instanceof ArrayList)
					iterator = ((ArrayList<Object>) a.get(manipulatedObject)).iterator();
				else if (a.get(manipulatedObject) instanceof HashMap)
					iterator = ((HashMap<?, Object>) a.get(manipulatedObject)).values().iterator();
				else
					throw new ClassCastException ("Un argument multiple doit être sous la forme d'une ArrayList ou d'une HashMap");
				
				while (iterator.hasNext()) {
					Object manipulatedAttributeValue = iterator.next();
					if (a.doesImplementInterface(XMLNode.class)) {
						DataType dt = DataType.getDataTypeFromClass(a.getAttributeClass());
						ConfigObjectManipulator com = dt.getObjectManipulator();
						com.toXML(manipulatedAttributeValue);
					}
					else {
						Element e = new Element(a.getXMLName());
						e.setText(manipulatedAttributeValue.toString());
						xmlNode.addContent(e);
					}
				}
			}
			
			if (a.doesImplementInterface(XMLNode.class)) {
				DataType dt = DataType.getDataTypeFromClass(a.getAttributeClass());
				ConfigObjectManipulator com = dt.getObjectManipulator();
				com.toXML(a.get(manipulatedObject));
			}
			else {
				Element e = new Element(a.getXMLName());
				e.setText(a.get(manipulatedObject).toString());
				xmlNode.addContent(e);
			}
		}
	}
	
	public Element createXMLNode(Object manipulatedObject) throws IllegalStateException, IllegalArgumentException, ClassCastException {
		checkClass(manipulatedObject);
		
		Element xmlNode = getXMLNode(manipulatedObject);
		if (xmlNode != null) throw new IllegalStateException("Un pointeur vers un élément XML existe déjà");
		
		xmlNode = new Element (getXMLNodeName());
		((XMLNode)manipulatedObject).setXMLNode(xmlNode);
		
		return xmlNode;
	}
	
	public boolean isSameAsParent(Object manipulatedObject) {
		Object parent;
		try {parent = ((Heritable)manipulatedObject).getParent();}
		catch (ClassCastException e) {throw new ClassCastException ("Trying to compare an object to its parent but the object does not implement the Heritable interface (" + e.getMessage() + ")");}
		
		for (int i = 0 ; i < attributes.length ; i++)
			if (get(manipulatedObject, i) != get(parent, i))
				return false;
		return true;
	}
	
	public void resetToParent(Object manipulatedObject) {
		Object parent;
		try {parent = ((Heritable)manipulatedObject).getParent();}
		catch (ClassCastException e) {throw new ClassCastException ("Trying to reset an object to its parent but the object does not implement the Heritable interface (" + e.getMessage() + ")");}
		
		if (parent != null)
			for (int i = 0 ; i < attributes.length ; i++)
				set(manipulatedObject, i, get(parent, i));
	}
	
	// a class to contain the properties/attributes of a data element
	public static abstract class StandardAttribute {
		private String readableName;
		private String xmlName;
		private Class attributeClass;
		private boolean canBeMultiple;
		
		public StandardAttribute(String columnName, String xmlName, Class attributeClass) {
			this.readableName = columnName;
			this.xmlName = xmlName;
			this.attributeClass = attributeClass;
			this.canBeMultiple = false;
		}
		
		public StandardAttribute(String columnName, String xmlName, Class attributeClass, boolean canBeMultiple) {
			this.readableName = columnName;
			this.xmlName = xmlName;
			this.attributeClass = attributeClass;
			this.canBeMultiple = canBeMultiple;
		}
		
		public final String getReadableName() {return readableName;}
		public final String getXMLName() {return xmlName;}
		public final Class getAttributeClass() {return attributeClass;}
		public final boolean getCanBeMultiple() {return canBeMultiple;}
		public final boolean doesImplementInterface(Class implementedInterface) {
			for (Class c : attributeClass.getInterfaces())
				if (c.equals(implementedInterface))
					return true;
			return false;
		}
		
		// get/set with type check
		public final Object getAttribute (Object manipulatedObject) {return get(manipulatedObject);}
		
		public final void setAttribute(Object manipulatedObject, Object attributeValue) throws ClassCastException {
			if (! attributeClass.isInstance(attributeValue))
				throw new ClassCastException ("L'attribut n'est pas du type attendu (trouvé : " + attributeValue.getClass().toString() + ", attendu : " + attributeClass.toString() + ")");
			set (manipulatedObject, attributeValue);
		}
		
		// basic get/set without any check
		protected abstract Object get(Object manipulatedObject);
		protected abstract void set(Object manipulatedObject, Object attributeValue);
	}
	
	// check that the data does match the type given upon construction
	private void checkClass(Object object) throws ClassCastException {
		if (! objectClass.isInstance(object))
			throw new ClassCastException ("L'objet n'est pas du type attendu (trouvé : " + object.getClass().toString() + ", attendu : " + objectClass.toString() + ")");
	}
	
	// a private function to extract data from XML
	private void processFromXML(Object manipulatedObject, Element xmlNode, boolean warnIfNotExhaustive) throws IncorrectFormatException, InstantiationException {
		try {((XMLNode)manipulatedObject).setXMLNode(xmlNode);}
		catch (ClassCastException ex) {throw new ClassCastException ("Trying to get an object from XML node, but the object does not implement the XMLNode interface (" + ex.getMessage() + ")");}
		
		List<Element> elements = xmlNode.getChildren();
		boolean onceMatched[] = new boolean[attributes.length];
		
		for (boolean b : onceMatched)
			b = false;
		
		for (Element e : elements) {
			boolean foundAMatchingAttribute = false;
			for (int i = 0 ; i < attributes.length ; i++) {
				StandardAttribute att = attributes[i];
				
				if (att.getXMLName().equals(e.getName())) {
					Object value = null;
					
					// TODO [someday] note that second (or plus) XML sublevel can only be processed without inheritance handling (cause this feature is currently useless)
					if (att.doesImplementInterface(XMLNode.class)) {
						DataType dt = DataType.getDataTypeFromClass(att.getAttributeClass());
						ConfigObjectManipulator com = dt.getObjectManipulator();
						value = com.fromXML(e);
						if (! att.getCanBeMultiple() && onceMatched[i])
							throw new IncorrectFormatException("Donnée \"" + e.getName() + "\" en double (la configuration de ce champ l'interdit)");
					}
					else {
						if (onceMatched[i])
							throw new IncorrectFormatException("Donnée \"" + e.getName() + "\" en double (les champs de type primitifs ou enum ne peuvent pas être doubles car il ne peuvent pas comporter d'ID)");
						
						String text = e.getTextTrim();

						if (att.getAttributeClass().equals(Byte.class))
							value = Byte.parseByte(text);
						else if (att.getAttributeClass().equals(Short.class))
							value = Short.parseShort(text);
						else if (att.getAttributeClass().equals(Integer.class))
							value = Integer.parseInt(text);
						else if (att.getAttributeClass().equals(Long.class))
							value = Long.parseLong(text);
						else if (att.getAttributeClass().equals(Float.class))
							value = Float.parseFloat(text);
						else if (att.getAttributeClass().equals(Double.class))
							value = Double.parseDouble(text);
						else if (att.getAttributeClass().equals(Boolean.class))
							value = Boolean.parseBoolean(text);
						else if (att.getAttributeClass().equals(Character.class))
							value = text.charAt(0);
						else if (att.getAttributeClass().equals(String.class))
							value = text;
						else if (att.getAttributeClass().isEnum())
							value = Enum.valueOf(att.getAttributeClass(), text);
						else
							throw new IllegalArgumentException ("Le type de l'attribut n'est ni un type primitif, ni une chaine de caractère. Conversion depuis le XML impossible.");
						
						if (! e.getChildren().isEmpty())
							throw new IncorrectFormatException("La donnée XML \"" + e.getName() + "\" ne devrait pas avoir de noeud enfant");
					}
					att.setAttribute(manipulatedObject, value);
					onceMatched[i] = true;
					
					foundAMatchingAttribute = true;
					break;
				}
			}
			if (! foundAMatchingAttribute)
				throw new IncorrectFormatException("Balise \"" + e.getName() + "\" inconnue");
		}
		
		if (warnIfNotExhaustive)
			for (boolean b : onceMatched)
				if (!b) throw new IncorrectFormatException("Paramètre manquant dans le fichier XML");
	}
}
