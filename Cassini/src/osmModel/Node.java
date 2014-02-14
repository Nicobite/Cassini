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
package osmModel;

import java.util.ArrayList;
import model.road.RoadSection;

/**
 *
 * @author gabriel
 */
public class Node {
    
    private int id;
    private float latitude;
    private float longitude;
    private int nbWay=0;
    
    private ArrayList<RoadSection> roadSections;
    private ArrayList<Boolean> isBeginOfRoadSections;
    
    public Node(){
    }
    
    public Node(int id, float altitude, float longitude){
        this.id=id;
        this.latitude=altitude;
        this.longitude=longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    
    public String toString(){
        return "Node : id = "+id+", lat="+latitude+", lon="+longitude;
    }

    public int getNbWay() {
        return nbWay;
    }

    public void setNbWay(int nbWay) {
        this.nbWay = nbWay;
    }
    
    public void addRoadSection(RoadSection roadSection, boolean isBegin){
        if(roadSections==null){
            roadSections= new ArrayList<RoadSection>();
            isBeginOfRoadSections= new ArrayList<Boolean>();
        }
        
        roadSections.add(roadSection);
        isBeginOfRoadSections.add(isBegin);
    }
    
    public int NbRoadSections(){
        if(roadSections==null){
            return 0;
        }
        return roadSections.size();
    }
    public RoadSection getRoadSection(int i){
        return roadSections.get(i);
    }
    
    public boolean isBeginOfRoadSection(int i){
        return isBeginOfRoadSections.get(i);
    }
    
    
    
}
