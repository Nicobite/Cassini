/*
* Copyright 2014 Abel Juste Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.insa.xml.osm;

import java.util.ArrayList;
import java.util.HashMap;
import org.insa.core.enums.Direction;
import org.insa.core.enums.RoadType;
import org.insa.core.roadnetwork.NextSection;
import org.insa.core.roadnetwork.Road;
import org.insa.view.graphicmodel.GraphicSection;
import org.insa.xml.osm.entities.OsmNodeRef;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo
 * Class OsmWay
 * OpenstreetMap xml element
 * See http://wiki.openstreetmap.org/wiki/Ways for details
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 *
 */
@Root(name="way", strict = false)
public class OsmWay {
    /** identifier of the way */
    @Attribute
    private long id;
    
    /** references of osm xml nodes that make the road */
    @ElementList(inline = true)
    private ArrayList<OsmNodeRef> nodesRef;
    
    /** list of tags of this way */
    @ElementMap(entry="tag", key="k", value = "v", attribute=true, inline=true, required = false)
    private HashMap<String, String> tags;
    
    public OsmWay() {
        this.nodesRef = new ArrayList<>();
        this.tags = new HashMap<>();
    }
    
    /**
     *  get the number of lanes of this way\n
     * @return the number of way if defined, default numbers otherwise
     */
    private int getForwardNbLanes(){
        int nbLanes = this.tags.containsKey("lanes:forward")?
                Integer.parseInt(tags.get("lanes:forward")) : 2;
        return nbLanes;
    }
    
    @Override
    public String toString(){
        String str = this.id+" oneway = "+isOneWay()+ " maxspeed = "+getMaxSpeed()+" nbLanes ="+getNbLanes()+"\n"
                +"roundabout = "+isRoundabout();
        return str;
    }
    
    /**
     * creates a Road from the current Osm way
     * @param osmNodes the list of all the osm nodes
     * @return the built road
     */
    public Road buildRoad(HashMap<Long, OsmNode> osmNodes){
        OsmNode src, dest;
        int i = 0;
        long refSrc, refDest;
        Road road = new Road();
        setRoadType(road);
        road.setId(this.id);
        road.setOneway(isOneWay());
        while(i<this.nodesRef.size()-1){
            refSrc  = this.nodesRef.get(i).getRef();
            refDest = this.nodesRef.get(i+1).getRef();
            if(osmNodes.containsKey(refSrc) && osmNodes.containsKey(refDest)) {
                src = osmNodes.get(refSrc);
                dest = osmNodes.get(refDest);
                //Add the current road to the nodes roads membership
                src.addRoad(road);
                dest.addRoad(road);
                this.createSections(road, src, dest);
            }
            i++;
        }
        //add connection between backward lanes and forward lanes at the end of the road
        if(isOneWay()){
            GraphicSection first = road.getFirstSection();
            GraphicSection last = road.getLastSection();
            first.addConnections(first.getBackwardLanes(),first.getForwardLanes());
            last.addConnections(last.getForwardLanes(),last.getBackwardLanes());
        }
        //if roundabout connect first section to last section
        if(isRoundabout()){
            GraphicSection first = road.getFirstSection();
            GraphicSection last = road.getLastSection();
            last.addConnections(last.getForwardLanes(),first.getForwardLanes());
            last.addSuccessor(new NextSection(first.getSection(), Direction.BACKWARD));
        }
        return road;
    }
    
    /**
     * create and add road section linking 2 nodes to a given road
     * @param road the road at which the section will be added
     * @param src
     * @param dest
     */
    private void createSections(Road road, OsmNode src, OsmNode dest) {
        GraphicSection gSection = new GraphicSection(src.getGraphicNode(), dest.getGraphicNode());
        gSection.getSection().setMaxSpeed(this.getMaxSpeed());
        int nbForwardLanes = this.getNbLanes();
        int nbBackwardLanes = 0;
        if(!this.isOneWay()){
            nbForwardLanes = this.getForwardNbLanes();
            nbBackwardLanes = getNbLanes() - getForwardNbLanes();
        }
        gSection.addLanes(nbForwardLanes, Direction.FORWARD, road.getLastSection());
        gSection.addLanes(nbBackwardLanes, Direction.BACKWARD, road.getLastSection());
        road.addSection(gSection.getSection());
    }
    
    /**
     * checks whether this osm way is a highway (osm way can be building, river,...)
     * @return
     */
    public boolean isHighway(){
        return this.tags.containsKey("highway");
    }
    
    /**
     * checks whether this osm way is one-way
     * @return
     */
    private boolean isOneWay() {
        return this.tags.containsKey("oneway") || this.isRoundabout();
    }
    
    /**
     * check whether this osm way is a roundabout
     * @return
     */
    private boolean isRoundabout() {
        return  (   tags.containsKey("junction")
                && tags.get("junction").equalsIgnoreCase("roundabout"))
                ||  tags.get("highway").equalsIgnoreCase("mini_roundabout") ;
    }
    
    /**
     * get the max speed of this way
     * @return
     */
    private float getMaxSpeed() {
        float maxspeed = 50;
        if(this.tags.containsKey("maxspeed")){
            try{
                
                maxspeed = Float.parseFloat(this.tags.get("maxspeed"));
                
            }
            catch(NumberFormatException e){
                
            }
        }
        return maxspeed;
    }
    
    /**
     * get the number of lanes of this way\n
     * @return the number of way if defined, default numbers otherwise
     */
    private int getNbLanes() {
        int def = this.isOneWay() ? 2 : 4;
        int nbLanes = this.tags.containsKey("lanes")?
                Integer.parseInt(tags.get("lanes")) : def;
        return nbLanes;
    }
    
    /**
     * Sets the type of the road and its priority
     * @param road
     * @return the propority of this road
     */
    private int setRoadType(Road road){
        int priority;
        String type = tags.get("highway");
        switch(type){
            case "motorway":
                priority = 14;
                road.setType(RoadType.MOTORWAY);
                break;
            case "trunk":
                priority = 13;
                road.setType(RoadType.TRUNK);
                break;
            case "primary" :
                priority = 12;
                road.setType(RoadType.PRIMARY);
                break;
            case "secondary":
                priority = 11;
                road.setType(RoadType.SECONDARY);
                break;
                /*case "motorway_link":
                priority = 10;
                break;
                case "trunk_link":
                priority = 9;
                break;
                case "primary_link":
                priority = 8;
                break;
                case "secondary_link":
                priority = 7;
                break;
                case "tertiary" :
                priority = 6;
                break;
                case "residential":
                priority = 5;
                break;
                case "unclassified":
                priority = 4;
                break;
                case "road":
                priority = 3;
                break;
                case "living_street":
                priority = 2;
                break;
                case "service":
                priority = 1;
                break;*/
            default:
                priority = 0;
                road.setType(RoadType.OTHER);
        }
        if(isRoundabout()) road.setType(RoadType.ROUNDABOUT);
        return priority;
    }
    
    /* getters and setters */
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public HashMap<String, String> getTags() {
        return tags;
    }
    
    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }
    
    public ArrayList<OsmNodeRef> getNodesRef() {
        return nodesRef;
    }
    
    public void setNodesRef(ArrayList<OsmNodeRef> nodesRef) {
        this.nodesRef = nodesRef;
    }
}
