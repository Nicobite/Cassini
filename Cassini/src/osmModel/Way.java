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

/**
 *
 * @author gabriel
 */
public class Way {
    
    private int id;
    private ArrayList<Node> nodes;
    private String type;
    private String name;
    private String ref;
    private boolean oneWay;
    private int lanes;
    private int maxSpeed;
    
    
    public Way(){
        nodes = new ArrayList<Node>();
        oneWay = false;
        lanes=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        if(type.equalsIgnoreCase("motorway")){
            setOneWay(true);
            lanes=2;
            maxSpeed=130;
        }else if(type.equalsIgnoreCase("motorway_link")
                ||type.equalsIgnoreCase("motorway_junction")){
            lanes=1;
            setOneWay(true);
            maxSpeed=90;
        }else if(type.equalsIgnoreCase("trunk")){
            lanes=2;
            maxSpeed=110;
        }else if(type.equalsIgnoreCase("trunk_link")){
            lanes=1;
            setOneWay(true);
            maxSpeed=90;
        }else if(type.equalsIgnoreCase("primary")
                || type.equalsIgnoreCase("secondary")
                || type.equalsIgnoreCase("tertiary")
                || type.equalsIgnoreCase("unclassified")
                || type.equalsIgnoreCase("residential")){
            lanes=1;
            maxSpeed=90;
        }else if(type.equalsIgnoreCase("primary_link")){
            lanes=1;
            setOneWay(true);
            maxSpeed=90;
        }else if(type.equalsIgnoreCase("service")){
            lanes=1;
            maxSpeed=50;
        }else if(type.equalsIgnoreCase("trunk")){
            lanes=1;
            maxSpeed=30;
        }else if(type.equalsIgnoreCase("footway")
                || type.equalsIgnoreCase("path")
                || type.equalsIgnoreCase("steps")
                || type.equalsIgnoreCase("cycleway")
                || type.equalsIgnoreCase("pedestrian")
                || type.equalsIgnoreCase("track")){
            lanes=1;
            maxSpeed=20;
        }else if(type.equalsIgnoreCase("construction")){
            lanes=1;
            maxSpeed=0;
        }else{
            lanes=1;
            maxSpeed=0;
            System.out.println("Way : Type non reconnu : "+type);
        }
    }
    
    public boolean isCarRoad(){
        if(type.equalsIgnoreCase("motorway")
            || type.equalsIgnoreCase("motorway_link")
            || type.equalsIgnoreCase("motorway_junction")
            || type.equalsIgnoreCase("trunk")
            || type.equalsIgnoreCase("trunk_link")
            || type.equalsIgnoreCase("primary")
            || type.equalsIgnoreCase("secondary")
            || type.equalsIgnoreCase("tertiary")
            || type.equalsIgnoreCase("unclassified")
            || type.equalsIgnoreCase("residential")
            || type.equalsIgnoreCase("primary_link")
            || type.equalsIgnoreCase("service")
            || type.equalsIgnoreCase("trunk")){
            return true;
        }else{
            return false;
        }
               
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    
    @Override
    public String toString(){
        return "Way : id="+id+", type="+type+", nom="+name+", ref="+ref+", nbNodes="+nodes.size();
    }

    public int getLanes() {
        return lanes;
    }

    public void setLanes(int lanes) {
        System.out.println("setLane="+lanes);
        this.lanes = lanes;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    
}
