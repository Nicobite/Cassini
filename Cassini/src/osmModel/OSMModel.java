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
import model.Coordinates;
import model.GlobalModel;
import model.junction.CrossRoad;
import model.junction.RoadSign;
import model.road.Road;
import model.road.RoadSection;
import model.road.RoadWay;

/**
 *
 * @author gabriel
 */
public class OSMModel {
    
    private float minLat,minLon,maxLat,maxLon;
    
    private ArrayList<Node> nodes;
    private ArrayList<Way> ways;
    
    public OSMModel(){
        nodes = new ArrayList<Node>();
        ways = new ArrayList<Way>();
    }

    public void clear() {
        nodes.clear();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
    
    @Override
    public String toString(){
        String s= new String();
        
        s+="Nodes : \n";
        for(Node n:nodes){
            s+="\t"+n.toString()+"\n";
        }
        
        s+="Ways : \n";
        for(Way w:ways){
            s+="\t"+w.toString()+"\n";
        }
        
        return s;
        
    }
    
    public Node findNodeById(int id){
        for(Node n:nodes){
            if(n.getId()==id){
                return n;
            }
        }
        
        return null;
    }

    public ArrayList<Way> getWays() {
        return ways;
    }

    public void setWays(ArrayList<Way> ways) {
        this.ways = ways;
    }
    
    private int inferRoadWayWidth(String s){
        if (s.equalsIgnoreCase("motorway")) {
            return 3500;
        }else if (s.equalsIgnoreCase("motorway_link")) {
            return 3500;
        }else if(s.equalsIgnoreCase("primary")) {
            return 3500;
        }else if(s.equalsIgnoreCase("primary_link")) {
            return 3500;
        }else if(s.equalsIgnoreCase("secondary")) {
            return 3000;
        }else if(s.equalsIgnoreCase("tertiary")) {
            return 2500;
        }else if (s.equalsIgnoreCase("unclassified")) {
            return 2250;
        }else if (s.equalsIgnoreCase("residential")
                || s.equalsIgnoreCase("service")) {
            return 2250;
        } else if (s.equalsIgnoreCase("path")) {
            return 2000;
        } else if (s.equalsIgnoreCase("track")) {
            return 1750;
        }else if (s.equalsIgnoreCase("cycleway")) {
            return 1000;
        }
        else if (s.equalsIgnoreCase("footway")) {
            return 500;
        }else {
            System.out.println("Type de voie non pris en compte : " + s);
            return 0;
        }
    }
    
    private ArrayList<RoadWay> inferRoadWays(Way w) {
        ArrayList<RoadWay> roadWays = new ArrayList<RoadWay>();
        
        for(int i=0;i<w.getLanes();i++){
            roadWays.add(new RoadWay(inferRoadWayWidth(w.getType()), true, w.getMaxSpeed())); 
        }
        
        if (!w.isOneWay()) {
            for(int i=0;i<w.getLanes();i++){
                roadWays.add(new RoadWay(inferRoadWayWidth(w.getType()), false, w.getMaxSpeed())); 
            }
        }
        
        return roadWays;
    }
    
    
    public void toGlobalModel(GlobalModel globalModel){
        globalModel.clearMap();
             
        for(Way w:ways){
            if(w.isCarRoad()){
                Road road = new Road();
                if (w.getName() != null) {
                    road.setName(w.getName());
                }


                RoadSection roadSection = new RoadSection();
                roadSection.setRoadWays(inferRoadWays(w));
                roadSection.addCoordinate(toCoordinates(w.getNodes().get(0)));
                if(w.getNodes().get(0).getNbWay()>1){
                    w.getNodes().get(0).addRoadSection(roadSection, true);
                }


                for(int i=1;i<w.getNodes().size()-1;i++){
                    Node n = w.getNodes().get(i);
                    roadSection.addCoordinate(toCoordinates(n));

                    if(n.getNbWay()>1){
                        road.getRoadSections().add(roadSection);
                        n.addRoadSection(roadSection, false);
                        roadSection = new RoadSection();
                        roadSection.setRoadWays(inferRoadWays(w));
                        roadSection.addCoordinate(toCoordinates(n));
                        n.addRoadSection(roadSection, true);
                    }
                    //System.out.println(toCoordinates(n));

                }
                roadSection.addCoordinate(toCoordinates(w.getNodes().get(w.getNodes().size()-1))); 
                if(w.getNodes().get(w.getNodes().size()-1).getNbWay()>1){
                    w.getNodes().get(w.getNodes().size()-1).addRoadSection(roadSection, false);
                }
                road.getRoadSections().add(roadSection);

                globalModel.getRoads().add(road);
            }
        }
        
        
        for(Road road:globalModel.getRoads()){
            for(RoadSection roadSection:road.getRoadSections()){
                roadSection.centralToNormalCoordinates();
                roadSection.reduceExtremity(true, 10000);
                roadSection.reduceExtremity(false, 10000);
            }
        }
        
        for(Node n:nodes){
            if(n.getNbWay()>1 && n.NbRoadSections()>1){
                System.out.println(n.getNbWay()+" "+n.NbRoadSections());
                CrossRoad c = new CrossRoad();
                for(int i=0;i<n.NbRoadSections();i++){
                    c.addRoadSection(n.getRoadSection(i), n.isBeginOfRoadSection(i),new RoadSign("none"));
                }
                c.sortRoadSection();
                globalModel.getJunctions().add(c);
            }
            
        }
    }
    
    public Coordinates toCoordinates(Node node){
        return new Coordinates((int)(111600000f*(node.getLongitude()-getMinLon())),(int)(111600000f*(node.getLatitude()-getMinLat())));
    }

    public float getMinLat() {
        return minLat;
    }

    public void setMinLat(float minLat) {
        this.minLat = minLat;
    }

    public float getMinLon() {
        return minLon;
    }

    public void setMinLon(float minLon) {
        this.minLon = minLon;
    }

    public float getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(float maxLat) {
        this.maxLat = maxLat;
    }

    public float getMaxLon() {
        return maxLon;
    }

    public void setMaxLon(float maxLon) {
        this.maxLon = maxLon;
    }

    

}