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
package model.junction;

import java.util.ArrayList;
import model.Maths;
import model.road.RoadSection;
import model.vehicule.Vehicule;
import model.vehicule.VehiculeEnvironment;

/**
 *
 * @author gabriel
 */
public abstract class Junction {
    protected ArrayList<RoadSection> roadSections;
    protected ArrayList<Boolean>  beginOfRoadSections;
    protected ArrayList<Vehicule> vehicules;
    
    public Junction(){
        roadSections = new ArrayList<RoadSection>();
        beginOfRoadSections = new ArrayList<Boolean>();
        vehicules = new ArrayList<Vehicule>();
    }
    
    public void sortRoadSection(){
        ArrayList<Float> angles = new ArrayList<Float>(roadSections.size());
        ArrayList<RoadSection> newRS = new ArrayList<RoadSection>(roadSections.size());
        ArrayList<Boolean>  newBeginRS = new ArrayList<Boolean>(roadSections.size());

        for(int i=0;i<roadSections.size();i++){
            if(beginOfRoadSections.get(i)==true){
                angles.add(roadSections.get(i).getSegmentAngle(0));
            }else{
                angles.add(roadSections.get(i).getSegmentAngle(roadSections.get(i).getNumberOfCoordinates()-2)+Maths.PI);
            }
        }
        
        while(angles.size()>0){
            int indexMax=0;
            float max=angles.get(0);
            for(int i=1;i<angles.size();i++){
                if(angles.get(i)>max){
                    indexMax=i;
                    max=angles.get(i);
                }      
            }
            
            newRS.add(roadSections.get(indexMax));
            newBeginRS.add(beginOfRoadSections.get(indexMax));
            System.out.println(angles.get(indexMax));
            angles.remove(indexMax);
            roadSections.remove(indexMax);
            beginOfRoadSections.remove(indexMax);  
        }
        System.out.println();
        roadSections = newRS;
        beginOfRoadSections = newBeginRS;

    }

    public void addRoadSection (RoadSection road, boolean begin) {
        roadSections.add(road);
        beginOfRoadSections.add(begin);
        if (begin) {
            road.setBeginJunction(this);
        }
        else{
            road.setEndJunction(this);
        }
    }


    public int getNumberRoadSections(){
        return roadSections.size();
    }

    public ArrayList<Boolean> getBeginOfRoadSections() {
        return beginOfRoadSections;
    }

    public ArrayList<RoadSection> getRoadSections() {
        return roadSections;
    }

     public Boolean getBeginOfRoadSection(int index) {
        return beginOfRoadSections.get(index);
    }

    public RoadSection getRoadSection(int index) {
        return roadSections.get(index);
    }

    public abstract void vehiculeEntry(Vehicule v, int roadSection, int wayNumber, int distance, int lateralOffset);

    public abstract void fillFrontEnvironment(VehiculeEnvironment environment, int vehiculeIndex, int beginRoadSection, int beginWayNumber, int endRoadSection, int endWayNumber, int distanceToJunction, int nbVehiculeToFind);
    public abstract void fillFrontEnvironment(VehiculeEnvironment environment, int beginRoadSection, int beginWayNumber, int endRoadSection, int endWayNumber, int distanceToJunction, int nbVehiculeToFind);
    public abstract void fillBackEnvironment(VehiculeEnvironment environment, int RoadSection, int WayNumber, int distanceToJunction, int nbVehiculeToFind);

    public int getIndexRoadSection(RoadSection roadSection, boolean direction) {
        for(int i=0;i<roadSections.size();i++){
            //System.out.println(roadSections.size());
            //System.out.println(beginOfRoadSections.size());
            //System.out.println(i);
            if(roadSections.get(i) == roadSection){
                if(beginOfRoadSections.get(i) != direction){
                    return i;
                }
                
            }
        }
        return -1;
    }

    public ArrayList<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(ArrayList<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }
 
    public Vehicule getVehicule(int index){
        return vehicules.get(index);
    }
    
    public void addVehicule(Vehicule v){
        vehicules.add(v);
    }
    
    public void deleteVehicule(Vehicule v){
        vehicules.remove(v);
    }

    public int findVehiculeIndex(Vehicule vehicule) {
        for(int i=0;i<vehicules.size();i++){
            if(vehicule == vehicules.get(i)){
                return i;
            }
        }
        
        return -1;
    }


    
}
