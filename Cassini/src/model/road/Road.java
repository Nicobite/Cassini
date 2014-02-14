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
package model.road;

import java.util.ArrayList;

/**
 *
 * @author gabriel
 */
public class Road {
    
    private String name;
    private ArrayList<RoadSection> roadSections;
    
    public Road(){
        roadSections = new ArrayList<RoadSection>();
    }
    
    public Road(String name){
        this();
        this.name=name;
        
        
    }
    
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public ArrayList<RoadSection> getRoadSections() {
        return roadSections;
    }

    public RoadSection getRoadSection(int index){
        return roadSections.get(index);
    }

    public int getNumberOfSections(){
        return roadSections.size();
    }

    public void setRoadSections(ArrayList<RoadSection> roadSections) {
        this.roadSections = roadSections;
    }
}
