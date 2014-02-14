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
package xml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Coordinates;
import model.GlobalModel;
import model.junction.AccelerationLane;
import model.junction.CrossRoad;
import model.junction.InterfaceJunctionTwoRoad;
import model.junction.Junction;
import model.junction.RoadSectionJunction;
import model.junction.RoadWayChange;
import model.road.Road;
import model.road.RoadSection;
import model.road.RoadWay;

/**
 *
 * @author gabriel
 */
public class WriterXML {
    
    GlobalModel globalModel;
    int tab=0;
    ArrayList<String> balises= new ArrayList<String>();
    FileWriter file;
    BufferedWriter output;
    boolean beginOfTheline = true;
    
    public WriterXML(GlobalModel globalModel){
        this.globalModel = globalModel;
        
    }
    
    public void saveGlobalModel(String filePath){
        try {
            file = new FileWriter(filePath); 
            output = new BufferedWriter(file);
            
            writeBeginBalise("map");
            for(Road road:globalModel.getRoads()){
                writeBeginBalise("road");
                for(RoadSection roadSection:road.getRoadSections()){
                    writeBeginBalise("roadSection");
                    
                    writeBeginBalise("id");
                    output.write(String.valueOf(roadSection.getId()));
                    writeEndBalise();
                    
                    writeBeginBalise("coordinates");
                    for(Coordinates c:roadSection.getCoordinates()){
                        writeBeginBalise("coordinate");
                        
                        writeBeginBalise("x");
                        output.write(String.valueOf(c.getX()));
                        writeEndBalise();
                        
                        writeBeginBalise("y");
                        output.write(String.valueOf(c.getY()));
                        writeEndBalise();
                        
                        writeEndBalise();
                    }
                    writeEndBalise();
                    
                    writeBeginBalise("roadWays");
                    for(RoadWay rw:roadSection.getRoadWays()){
                        writeBeginBalise("roadWay");
                          
                        writeBeginBalise("width");
                        output.write(String.valueOf(rw.getWidth()));    
                        writeEndBalise();
                        
                        writeBeginBalise("direction");
                        output.write(String.valueOf(rw.getDirection()));    
                        writeEndBalise();
                        
                        writeBeginBalise("speedLimit");
                        output.write(String.valueOf(rw.getSpeedLimit()));    
                        writeEndBalise();
                        
                        writeEndBalise();  
                    }
                    writeEndBalise();
                    
                    writeEndBalise();
                }
                writeEndBalise();
            }
            
            
            writeBeginBalise("junctions");
            for(Junction j:globalModel.getJunctions()){
                
                if(j instanceof RoadSectionJunction){
                    RoadSectionJunction rsj = (RoadSectionJunction)j;
                    writeBeginBalise("roadSectionJunction");
                    
                    writeJunctionTwoRoad(rsj);
                    
                    writeEndBalise();
                }
                else if(j instanceof AccelerationLane){
                    System.out.println("Enregistrement AccelerationLane non implementé");
                    writeBeginBalise("accelerationLane");
                    writeEndBalise();
                }
                else if(j instanceof RoadWayChange){
                    RoadWayChange rwc = (RoadWayChange)j;
                    writeBeginBalise("roadWayChange");
                    
                    writeJunctionTwoRoad(rwc);
                    
                    writeEndBalise();
                }
                else if(j instanceof CrossRoad){
                    CrossRoad c = (CrossRoad)j;
                    writeBeginBalise("crossRoad");
                    for(int i=0;i<c.getRoadSections().size();i++){
                        writeBeginBalise("roadSectionCR");
                        
                        writeBeginBalise("idRoadSectionCrossRoad");
                        output.write(String.valueOf(c.getRoadSection(i).getId()));  
                        writeEndBalise();
                        
                        writeBeginBalise("beginOfFirstCrossRoadSection");
                        output.write(String.valueOf(c.getBeginOfRoadSection(i)));  
                        writeEndBalise();
                        
                        writeBeginBalise("roadSign");
                        output.write(c.getRoadSign(i).toXMLData());
                        writeEndBalise();
                        
                        writeEndBalise();
                    }
                    writeEndBalise();
                    
                }
            }
            writeEndBalise();
            
            writeEndBalise();
            
            output.flush(); 
            output.close(); 
            
        } catch (IOException ex) {
            Logger.getLogger(WriterXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void writeJunctionTwoRoad(InterfaceJunctionTwoRoad j) throws IOException{
        writeBeginBalise("firstRoadSectionId");
        output.write(String.valueOf(j.getFirstRoadSection().getId()));
        writeEndBalise();
        writeBeginBalise("beginOfFirstRoadSection");
        output.write(String.valueOf(j.isBeginOfFirstRoadSection()));
        writeEndBalise();
        writeBeginBalise("secondRoadSectionId");
        output.write(String.valueOf(j.getSecondRoadSection().getId()));
        writeEndBalise();
        writeBeginBalise("beginOfFirstRoadSection");
        output.write(String.valueOf(j.isBeginOfSecondRoadSection()));
        writeEndBalise();
    }
    
    void writeBeginBalise(String balise){
        try {
            if(!beginOfTheline)
                output.write("\n");
            writeTab(tab);
            output.write("<"+balise+">");
            balises.add(balise);
            tab++;
            beginOfTheline = false;
        } catch (IOException ex) {
            Logger.getLogger(WriterXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void writeEndBalise(){
        try {
            tab--;
            if(beginOfTheline)
                writeTab(tab);
            output.write("</"+balises.remove(tab) +">\n");
            beginOfTheline = true;
            
        } catch (IOException ex) {
            Logger.getLogger(WriterXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void writeTab(int n){
        for(int i=0;i<n;i++){
            try {
                output.write("\t");
            } catch (IOException ex) {
                Logger.getLogger(WriterXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
