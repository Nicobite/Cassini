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
import org.simpleframework.xml.core.Commit;

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
    private int id;
    
    @ElementList(inline = true)
    private ArrayList<OsmNodeRef> nodesRef;
    
    @ElementMap(entry="tag", key="k", value = "v", attribute=true, inline=true, required = false)
    private HashMap<String, String> tags;
    
    private boolean oneWay;
    private boolean highway;
    private float maxSpeed;
    private int nbLanes;
    private boolean roundabout;
    /**
     * outward road
     */
    private Road road;
    /**
     * return road if not one-way
     */
    private Road returnRoad;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
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
        return highway;
    }
    
    public boolean isOneWay() {
        return oneWay;
    }
    
    public boolean isRoundabout() {
        return roundabout;
    }
    
    public float getMaxSpeed() {
        return maxSpeed;
    }
    
    public int getNbLanes() {
        return nbLanes;
    }
    
    @Override
    public String toString(){
        return this.id+" oneway = "+oneWay+ " maxspeed = "+maxSpeed+" nbLanes ="+nbLanes+"\n";
    }

    public Road getRoad() {
        road.setDirection(false);
        return road;
    }

    public Road getReturnRoad() {
        returnRoad.setDirection(true);
        return returnRoad;
    }
    
    @Commit
    private void build(){
        if(this.tags!=null){
            this.highway = this.tags.containsKey("highway");
            this.oneWay = this.tags.containsKey("oneway") && tags.get("oneway").equals("yes");
            this.maxSpeed = this.tags.containsKey("maxspeed")?
                    Float.parseFloat(this.tags.get("maxspeed")): 30;
            if(this.highway)inferNbLanes(this.tags.get("highway"));
        }
    }
    
    public void createRoad(HashMap<Integer, OsmNode> osmNode){
        Node src, via, target;
        this.road = new Road();
        this.returnRoad = new Road();
       switch(this.nodesRef.size()){
           case 1 :
               break;
           case 2 :
                src = osmNode.get(this.nodesRef.get(0).getRef()).createNode();
                target = osmNode.get(this.nodesRef.get(1).getRef()).createNode();
                Section s = new Section(src, target);
                s.setMaxSpeed(maxSpeed);
                s.addLanes(nbLanes);
                road.addSection(s);
               break;
           default:
               for(int i = 0;i<this.nodesRef.size()-2;i++){
                   src = osmNode.get(this.nodesRef.get(i).getRef()).createNode();
                   via = osmNode.get(this.nodesRef.get(i+1).getRef()).createNode();
                   target = osmNode.get(this.nodesRef.get(i+2).getRef()).createNode();
                   
                   createSections(road, src, via, target);
                   if(!this.isOneWay())
                       createSections(returnRoad, target, via, src);
               }
       }
        
    }
    
    private void createSections(Road road, Node src, Node via, Node dest){
        Section source = new Section(src, via);
        Section target = new Section(via, dest);
        source.setMaxSpeed(this.maxSpeed);
        target.setMaxSpeed(this.maxSpeed);
        source.addLanes(this.nbLanes);
        target.addLanes(this.nbLanes);
        road.addSection(source);
        road.addSection(target);
    }
    private void inferNbLanes(String type){
        switch(type){
            case "motorway":
                nbLanes = 3;
                break;
            case "trunk":
                nbLanes = 3;
                break;
            case "primary" :
                nbLanes = 3;
                break;
            case "secondary":
                nbLanes = 2;
                break;
            case "motorway_link":
                nbLanes = 1;
                break;
            case "trunk_link":
                nbLanes = 1;
                break;
            case "primary_link":
                nbLanes = 1;
                break;
            case "secondary_link":
                nbLanes = 1;
                break;
            case "tertiary" :
                nbLanes = 2;
                break;
            case "residential":
                nbLanes = 2;
                break;
            case "unclassified":
                nbLanes = 1;
                break;
            case "road":
                nbLanes = 2;
                break;
            case "living_street":
                nbLanes = 1;
                break;
            case "service":
                nbLanes = 1;
                break;
            case "roundabout":
                roundabout = true;
                break;
            default:
                nbLanes = 1;
        }
    }
    
}
