/*
* Copyright 2014 Juste Abel Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
package org.insa.model.items;

import java.util.ArrayList;
import java.util.HashMap;
import org.insa.core.enums.TrafficSignaling;
import org.insa.core.roadnetwork.NextLane;
import org.insa.core.roadnetwork.NextSection;
import org.insa.core.roadnetwork.Node;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.core.trafficcontrol.TrafficLight;
import org.insa.view.graphicmodel.GraphicLane;
import org.insa.view.graphicmodel.GraphicSection;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class RoadsModel
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class RoadsModel {
    /**
     * roads
     */
    @ElementList
    private ArrayList<Road>roads;
    
    /**
     * bounds of the road network : minimum longitude
     */
    @Attribute
    private float minLon;
    /**
     * bounds of the road network : minimum latitude
     */
    @Attribute
    private float minLat;
    /**
     * bounds of the road network : maximum longitude
     */
    @Attribute
    private float maxLon;
    /**
     * bounds of the road network : maximum latitude
     */
    @Attribute
    private float maxLat;
    
    /**
     * Default constructor
     */
    public RoadsModel(){
        this.roads = new ArrayList<>();
    }
    
    /**
     * Get number of roads
     * @return Number of roads
     */
    public int getRoadNumber() {
        return roads.size();
    }
    
    /**
     * Get number of sections
     * @return Number of sections
     */
    public int getSectionNumber() {
        int res = 0;
        for(Road r : roads) {
            res += r.getGraphicRoad().getSections().size();
        }
        return res;
    }
    
    /**
     * Clear road model
     */
    public void clear() {
        roads.clear();
    }

    /**
     * Add a road
     * @param road Road to add
     */
    public void addRoad(Road road){
        this.roads.add(road);
    }
    
    /**
     * Remove a road
     * @param road Road to remove
     */
    public void removeRoad(Road road){
        this.roads.remove(road);
    }
    
    /**
     * Get all nodes
     * @return Nodes
     */
    public ArrayList<Node> getNodes(){
        ArrayList<Node> res = new ArrayList<>();
        for(Road r : roads){
            for(Node n : r.getNodes()){
                if(!res.contains(n))
                    res.add(n);
            }
        }
        return res;
    }
    
    /**
     * Get junction
     */
    public void getJunctions(){
        Node node;
        for(Road r : roads){
            for(GraphicSection s : r.getGraphicRoad().getSections()){
                for(NextSection next : s.getSection().getSuccessors()){
                    node = next.getSection().getGraphicSection().getSourceNode().getNode();
                    node.addRoad(next.getSection().getRoad());
                    node = next.getSection().getGraphicSection().getTargetNode().getNode();
                    node.addRoad(next.getSection().getRoad());
                }
            }
            r.getFirstSection().getTargetNode().getNode().addRoad(r);
            r.getFirstSection().getSourceNode().getNode().addRoad(r);
        }
    }
    
    /**
     * Get all the traffic lights from the roads network
     * @return Traffic lights
     */
    public ArrayList<TrafficLight> getTrafficLightFromRoads(){
        ArrayList<TrafficLight> result = new ArrayList<>();
        HashMap<Long, Node> nodes = new HashMap();
        for(Road road : this.roads){
            for(GraphicSection s : road.getGraphicRoad().getSections()){
                nodes.put(s.getSourceNode().getNode().getId(), s.getSourceNode().getNode());
                nodes.put(s.getTargetNode().getNode().getId(), s.getTargetNode().getNode());
            }
        }
        for (Node n : nodes.values()) {
            if(n.getSignaling() == TrafficSignaling.TRAFFIC_LIGHT){
                result.add(new TrafficLight(n.getId(), n.getGraphicNode().getNode()));
            }
        }
        
        return result;
    }
    
    @Override
    public String toString(){
        return roads.toString();
    }
    
    @Commit
    public void commit(){
        HashMap<String, Section> sectionsMap = new HashMap();
        HashMap<String, GraphicLane> lanesMap = new HashMap();
        /* Put all the lanes and sections in a map for easy access */
        for(Road road : this.roads){
            for(GraphicSection sect : road.getGraphicRoad().getSections()){
                sectionsMap.put(sect.getSection().getId(),sect.getSection());
                for(GraphicLane lane : sect.getForwardLanes() ){
                    lanesMap.put(lane.getId(), lane);
                }
                for(GraphicLane lane : sect.getBackwardLanes() ){
                    lanesMap.put(lane.getId(), lane);
                }
            }
        }
        for(Road road : this.roads){
            for(GraphicSection sect : road.getGraphicRoad().getSections()){
                /* Retrieve sections */
                for(NextSection next : sect.getSection().getSuccessors()){
                    next.setSection(sectionsMap.get(next.getRef()));
                }
                /* Retrieve forward lanes */
                for(GraphicLane lane : sect.getForwardLanes()){
                    for(NextLane next : lane.getNextLanes()){
                        next.setTargetLane(lanesMap.get(next.getRef()));
                    }
                }
                /* Retrieve backward lanes */
                for(GraphicLane lane : sect.getBackwardLanes()){
                    for(NextLane next : lane.getNextLanes()){
                        next.setTargetLane(lanesMap.get(next.getRef()));
                    }
                }
            }
        }
        //getJunctions();
    }

    /**
     * Get roads
     * @return Roads
     */
    public ArrayList<Road> getRoads() {
        return roads;
    }

    /**
     * Get minimum longitude
     * @return Minimum longitude
     */
    public float getMinLon() {
        return minLon;
    }

    /**
     * Get minimum latitude
     * @return Minimum latitud
     */
    public float getMinLat() {
        return minLat;
    }

    /**
     * Get maximum longitude
     * @return Maximum longitude
     */
    public float getMaxLon() {
        return maxLon;
    }

    /**
     * get maximum latitude
     * @return Maximum latitude
     */
    public float getMaxLat() {
        return maxLat;
    }

    /**
     * Set roads
     * @param roads New roads 
     */
    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    /**
     * Set minimum longitude
     * @param minLon New minimum longitude
     */
    public void setMinLon(float minLon) {
        this.minLon = minLon;
    }

    /**
     * Set minimum latitude
     * @param minLat New minimum latitude
     */
    public void setMinLat(float minLat) {
        this.minLat = minLat;
    }

    /**
     * Set maximum longitude
     * @param maxLon New maximum longitude
     */
    public void setMaxLon(float maxLon) {
        this.maxLon = maxLon;
    }

    /**
     * Set maximum latitude
     * @param maxLat New maximum latitude
     */
    public void setMaxLat(float maxLat) {
        this.maxLat = maxLat;
    }
}
