/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <The Simulation Team> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer (or a Mojito for Julie)
 * in return.
 * Guillaume Blanc & Gabriel Charlemagne & Jonathan Fernandez & Julie Marti
 * ----------------------------------------------------------------------------
 */
package old.project2012.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import old.project2012.model.Coordinates;
import old.project2012.model.GlobalModel;
import old.project2012.model.junction.AccelerationLane;
import old.project2012.model.junction.CrossRoad;
import old.project2012.model.junction.DecelerationLane;
import old.project2012.model.junction.InterfaceJunctionTwoRoad;
import old.project2012.model.junction.Junction;
import old.project2012.model.junction.RoadSectionJunction;
import old.project2012.model.junction.RoadSign;
import old.project2012.model.junction.RoadWayChange;
import old.project2012.model.junction.TraficLight;
import old.project2012.model.road.Road;
import old.project2012.model.road.RoadSection;
import old.project2012.model.road.RoadWay;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author gabriel
 */
public class ParserLoadingMap extends DefaultHandler{
    
    String tempVal,tempVal2,tempVal3,tempVal4;
    String currentElement;
    
    GlobalModel globalModel;
    
    Road road;
    RoadSection roadSection;
    ArrayList<Coordinates> coordinates;
    Coordinates coordinate;
    ArrayList<RoadWay> roadWays;
    RoadWay roadWay;
    Junction junction;
    RoadSign roadSign;
    
    int x, y;
    boolean direction;
    int speedLimit;
    int width;
    int tempId;
    
    public ParserLoadingMap(GlobalModel globalModel){
        this.globalModel = globalModel;
    }
    
    public void parseFile(String file){
        try {
            globalModel.clearMap();
            globalModel.clearVehicules();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(file, this);
            
            /*for(Junction j:globalModel.getJunctions()){
                if(j instanceof CrossRoad && !(j instanceof AccelerationLane) && !(j instanceof DecelerationLane)){
                    j.sortRoadSection();
                }
            }*/
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
                else if (qName.equalsIgnoreCase("road")) {
                    road = new Road();
                }
                else if (qName.equalsIgnoreCase("roadSection")) {
                    roadSection = new RoadSection();
                }
                else if (qName.equalsIgnoreCase("coordinates")) {
                    coordinates = new ArrayList<Coordinates>();
                }
                else if (qName.equalsIgnoreCase("roadWays")) {
                    roadWays = new ArrayList<RoadWay>();
                }
                else if (qName.equalsIgnoreCase("roadWayChange")) {
                    junction = new RoadWayChange();
                }
                else if (qName.equalsIgnoreCase("roadSectionJunction")) {
                    junction = new RoadSectionJunction();
                    System.out.println("lala");
                }
                else if (qName.equalsIgnoreCase("crossRoad")) {
                    junction = new CrossRoad();
                }
                else if (qName.equalsIgnoreCase("accelerationLane")) {
                    junction = new AccelerationLane();
                }
                else if (qName.equalsIgnoreCase("decelerationLane")) {
                    junction = new DecelerationLane();
                }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch,start,length);
    }



    @Override
    public void endElement(String uri, String localName,
		String qName) throws SAXException {

                if (qName.equalsIgnoreCase("name")) {
                    road.setName(tempVal);
                }
                else if (qName.equalsIgnoreCase("id")) {
                    roadSection.setId(Integer.parseInt(tempVal));
                }
                else if (qName.equalsIgnoreCase("x")) {
                    x=Integer.parseInt(tempVal);
                }
                else if (qName.equalsIgnoreCase("y")) {
                    y=Integer.parseInt(tempVal);  
                }
                else if(qName.equalsIgnoreCase("coordinate")){
                    coordinates.add(new Coordinates(x,y));
                }
                else if(qName.equalsIgnoreCase("coordinates")){
                    roadSection.setCoordinates(coordinates);
                }
                else if(qName.equalsIgnoreCase("width")){
                    width = Integer.parseInt(tempVal);
                }
                else if(qName.equalsIgnoreCase("direction")){
                    if(tempVal.equalsIgnoreCase("true")){
                       direction = true; 
                    }
                    else{
                        direction = false; 
                    }   
                }
                else if(qName.equalsIgnoreCase("speedLimit")){
                    speedLimit = Integer.parseInt(tempVal);
                }
                else if(qName.equalsIgnoreCase("roadWay")){
                    roadWays.add(new RoadWay(width, direction, speedLimit));
                }
                else if(qName.equalsIgnoreCase("roadWays")){
                    roadSection.setRoadWays(roadWays);
                }
                else if(qName.equalsIgnoreCase("roadSection")){
                    road.getRoadSections().add(roadSection);
                }
                else if(qName.equalsIgnoreCase("road")){
                    globalModel.getRoads().add(road);
                }
                else if(qName.equalsIgnoreCase("firstRoadSectionId")){
                    tempVal2 = tempVal.toString();
                }
                else if(qName.equalsIgnoreCase("beginOfFirstRoadSection")){
                    if(tempVal.equalsIgnoreCase("true")){
                       ((InterfaceJunctionTwoRoad)junction).setFirstRoadSection(globalModel.getRoadSectionById(Integer.parseInt(tempVal2)), true);
                    }
                    else{
                        ((InterfaceJunctionTwoRoad)junction).setFirstRoadSection(globalModel.getRoadSectionById(Integer.parseInt(tempVal2)), false);
                    }
                    
                }
                else if(qName.equalsIgnoreCase("secondRoadSectionId")){
                    tempVal2 = tempVal.toString();
                }
                else if(qName.equalsIgnoreCase("beginOfSecondRoadSection")){
                    if(tempVal.equalsIgnoreCase("true")){
                       ((InterfaceJunctionTwoRoad)junction).setSecondRoadSection(globalModel.getRoadSectionById(Integer.parseInt(tempVal2)), true);
                    }
                    else{
                        ((InterfaceJunctionTwoRoad)junction).setSecondRoadSection(globalModel.getRoadSectionById(Integer.parseInt(tempVal2)), false);
                    }
                }
                else if(qName.equalsIgnoreCase("roadWayChange")){
                    globalModel.getJunctions().add(junction);                       
                }
                else if (qName.equalsIgnoreCase("roadSectionJunction")) {
                    globalModel.getJunctions().add(junction);
                    System.out.println("lolo");
                }
                else if(qName.equalsIgnoreCase("idRoadSectionCrossRoad")){
                    tempVal2 = tempVal.toString();
                }
                else if(qName.equalsIgnoreCase("beginOfFirstCrossRoadSection")){
                   //System.out.println("init "+globalModel.getRoadSectionById(Integer.parseInt(tempVal2)));
                    if(tempVal.equalsIgnoreCase("true")){
                        tempVal3 = "true";  
                   }
                   else{
                        tempVal3 = "false";
                   }   
                }
                else if(qName.equalsIgnoreCase("roadSign"))
                {
                   tempVal4 = tempVal.toString();
                   if (tempVal4.startsWith("traficLight")){
                       roadSign = new TraficLight(tempVal4.substring(12, 13), Integer.parseInt(tempVal4.substring(14)));
                       globalModel.getTraficLights().add((TraficLight) roadSign); ((TraficLight)roadSign).pause();
                   }
                   else
                       roadSign = new RoadSign(tempVal4);
                }
                else if(qName.equalsIgnoreCase("roadSectionCR")){
                    if(tempVal3.equalsIgnoreCase("true")){
                        ((CrossRoad)junction).addRoadSection(globalModel.getRoadSectionById(Integer.parseInt(tempVal2)), true, roadSign);
                    }
                    else{
                        ((CrossRoad)junction).addRoadSection(globalModel.getRoadSectionById(Integer.parseInt(tempVal2)), false, roadSign);
                    }
                }
                else if(qName.equalsIgnoreCase("crossRoad")){
                    globalModel.getJunctions().add(junction);
                     for(int i=0; i< junction.getRoadSections().size();i++)
                     {
                         if(junction.getBeginOfRoadSection(i)==true)
                            junction.getRoadSection(i).setBeginJunction(junction);
                         else
                            junction.getRoadSection(i).setEndJunction(junction);
                     }
                }
                //Acceleration lane
                else if(qName.equalsIgnoreCase("idRoadSectionAL")){
                     tempVal2 = tempVal.toString();  
                     roadSection = globalModel.getRoadSectionById(Integer.parseInt(tempVal2));
                }
                else if(qName.equalsIgnoreCase("beginOfRoadSectionAL")){
                    if(tempVal.equalsIgnoreCase("true")){
                       direction = true; 
                    }
                    else{
                        direction = false; 
                    }  
                }
                else if(qName.equalsIgnoreCase("AL")){
                    ((AccelerationLane)junction).addRoadSection(roadSection, direction);
                }
                 else if(qName.equalsIgnoreCase("accelerationLane")){
                     globalModel.getJunctions().add(junction);
                }
                 //Deceleration lane
                else if(qName.equalsIgnoreCase("idRoadSectionDL")){
                     tempVal2 = tempVal.toString();  
                     roadSection = globalModel.getRoadSectionById(Integer.parseInt(tempVal2));
                }
                else if(qName.equalsIgnoreCase("beginOfRoadSectionDL")){
                    if(tempVal.equalsIgnoreCase("true")){
                       direction = true; 
                    }
                    else{
                        direction = false; 
                    }  
                }
                else if(qName.equalsIgnoreCase("DL")){
                    ((DecelerationLane)junction).addRoadSection(roadSection, direction);
                }
                 else if(qName.equalsIgnoreCase("decelerationLane")){
                     globalModel.getJunctions().add(junction);
                }
                //other
                else {
                    System.out.println("Lecture non traité :"+ tempVal.toString());
                }
                
	}
}
