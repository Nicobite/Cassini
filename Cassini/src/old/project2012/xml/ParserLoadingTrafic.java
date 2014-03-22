/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package old.project2012.xml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import old.project2012.model.GlobalModel;
import old.project2012.model.road.RoadPosition;
import old.project2012.model.road.RoadSection;
import old.project2012.model.vehicule.Car;
import old.project2012.model.vehicule.Motorbike;
import old.project2012.model.vehicule.Truck;
import old.project2012.model.vehicule.Vehicule;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author gabriel
 */
public class ParserLoadingTrafic extends DefaultHandler{
    
    GlobalModel globalModel;
    
    String tempVal,context="";
    File file;
    
    String typeVehicule;
    RoadSection roadSection;
    int roadWay,distance, nbVehicules, gap;
    
    
        
    
    public ParserLoadingTrafic(GlobalModel globalModel){
        this.globalModel = globalModel;
    }
    
    public void parseFile(String file){
        try {
            globalModel.clearVehicules();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            this.file = new File(file);
            saxParser.parse(file, this);
            
            for(Vehicule v:globalModel.getVehicules()){
                for(int i=0;i<100;i++)
                    v.updatePosition(10);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ParserLoadingMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ParserLoadingMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ParserLoadingMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("map")) {
            globalModel.clearMap();
        }
        else if (qName.equalsIgnoreCase("positioning")) {
            context = "positioning";
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch,start,length);
    }
    
    @Override
    public void endElement(String uri, String localName,
		String qName) throws SAXException {
        if (qName.equalsIgnoreCase("map")) {
            ParserLoadingMap parser = new ParserLoadingMap(globalModel);
            parser.parseFile(file.getParent()+"/"+tempVal);
        }
        
        if (context.equalsIgnoreCase("positioning")) {
            endElementPositioning(qName);
        }
    }

    private void endElementPositioning(String qName) {
        if (qName.equalsIgnoreCase("typeVehicule")) {
            typeVehicule=tempVal;
        }
        else if (qName.equalsIgnoreCase("idRoadSection")) {
            roadSection= globalModel.getRoadSectionById(Integer.parseInt(tempVal));
        }
        else if (qName.equalsIgnoreCase("roadWay")) {
            roadWay= Integer.parseInt(tempVal);
        }
        else if (qName.equalsIgnoreCase("distance")) {
            distance= Integer.parseInt(tempVal);
        }
        else if (qName.equalsIgnoreCase("nbVehicules")) {
            nbVehicules= Integer.parseInt(tempVal);
        }
        else if (qName.equalsIgnoreCase("gap")) {
            gap= Integer.parseInt(tempVal);
        }
        else if (qName.equalsIgnoreCase("positioning")) {
            context="";
            for(int i=0; i<nbVehicules; i++){
                Vehicule v;
                if(typeVehicule.equalsIgnoreCase("car"))
                    v = new Car();
                else if(typeVehicule.equalsIgnoreCase("truck"))
                    v = new Truck();
                else if(typeVehicule.equalsIgnoreCase("motorbike"))
                    v = new Motorbike();
                else{
                    System.out.println("Type de véhicule non pris en comptes : " +typeVehicule);
                    v = new Vehicule();
                }
                RoadPosition roadPos = new RoadPosition(roadSection, roadWay, 0, distance + i * gap, 400);
                v.setPosition(roadPos);
                v.computeNextDestination(); 
                
                //v.setSpeed(20);
                globalModel.getVehicules().add(v);
                
            }
            
        }
        
    }
}
