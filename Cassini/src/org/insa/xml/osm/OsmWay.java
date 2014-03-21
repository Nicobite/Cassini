/*
* Copyright 2014 Abel Juste Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
import org.insa.core.roadnetwork.Node;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class OsmWay
 * Maps Openstreetmpa xml elements.
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 *
 */
@Root(name="way", strict = false)
public class OsmWay {
    
    @Attribute
    private long id;
    
    @ElementList(inline = true)
    private ArrayList<OsmNodeRef> nodesRef;
    
    @ElementMap(entry="tag", key="k", value = "v", attribute=true, inline=true, required = false)
    private HashMap<String, String> tags;
    
    /**
     * outward road
     */
    private Road road;
    
    public OsmWay(){
        this.nodesRef = new ArrayList<>();
        this.tags = new HashMap<>();
    }
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
    public boolean isHighway(){
        return this.tags.containsKey("highway");
    }
    
    public boolean isOneWay() {
        return this.tags.containsKey("oneway");
    }
    
    public boolean isRoundabout() {
        return tags.get("junction").equalsIgnoreCase("roundabout")
                ||tags.get("highway").equalsIgnoreCase("mini_roundabout") ;
    }
    
    public float getMaxSpeed() {
        float maxspeed = this.tags.containsKey("maxspeed")?
                Float.parseFloat(this.tags.get("maxspeed")) : 50;
        return maxspeed;
    }
    
    public int getNbLanes() {
        int def = this.isOneWay() ? 2 : 4;
        int nbLanes = this.tags.containsKey("lanes")?
                Integer.parseInt(tags.get("lanes")) : def;
        return nbLanes;
    }
    
    public int getForwardNbLanes(){
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
    
    public Road getRoad() {
        return road;
    }
    
    public void createRoad(HashMap<Long, OsmNode> osmNode){
        Node src, dest;
        this.road = new Road();
        if(this.nodesRef.size()>1){
            for(int i = 0;i<this.nodesRef.size()-1;i++){
                src = osmNode.get(this.nodesRef.get(i).getRef()).createNode();
                dest = osmNode.get(this.nodesRef.get(i+1).getRef()).createNode();
                this.createSections(src, dest);
            }
        }
        
    }
    
    private void createSections(Node src, Node dest){
        Section sect = new Section(src, dest);
        sect.setMaxSpeed(this.getMaxSpeed());
        int forwardLanes = this.getNbLanes();
        int backwardLanes = 0;
        if(!this.isOneWay()){
            forwardLanes = this.getForwardNbLanes();
            backwardLanes = getNbLanes() - getForwardNbLanes();
        }
        sect.addForwardLanes(forwardLanes);
        sect.addBackwardLanes(backwardLanes);
        road.addSection(sect);

    }
    private int getPriority(String type){
        int priority;
        switch(type){
            case "motorway":
                priority = 14;
                break;
            case "trunk":
                priority = 13;
                break;
            case "primary" :
                priority = 12;
                break;
            case "secondary":
                priority = 11;
                break;
            case "motorway_link":
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
                break;
            default:
                priority = 0;
        }
        return priority;
    }
    
}
