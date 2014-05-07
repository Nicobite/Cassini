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
package org.insa.model.items;

import java.util.ArrayList;
import java.util.HashMap;
import org.insa.core.roadnetwork.Lane;
import org.insa.core.roadnetwork.NextLane;
import org.insa.core.roadnetwork.NextSection;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.view.graphicmodel.GraphicLane;
import org.insa.view.graphicmodel.GraphicSection;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui &    Thomas Thiebaud
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
    
    public RoadsModel(){
        this.roads = new ArrayList<>();
    }
    
    public ArrayList<Road> getRoads() {
        return roads;
    }
    
    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }
    public void addRoad(Road road){
        this.roads.add(road);
    }
    public void removeRoad(Road road){
        this.roads.remove(road);
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
    
    @Override
    public String toString(){
        return roads.toString();
    }
    @Commit
    public void commit(){
        HashMap<String, Section> sectionsMap = new HashMap();
        HashMap<String, GraphicLane> lanesMap = new HashMap();
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
                for(NextSection next : sect.getSection().getSuccessors()){
                    next.setSection(sectionsMap.get(next.getRef()).getGraphicSection());
                }
                for(GraphicLane lane : sect.getForwardLanes()){
                    for(NextLane next : lane.getNextLanes()){
                        next.setTargetLane(lanesMap.get(next.getRef()));
                    }
                }
                for(GraphicLane lane : sect.getBackwardLanes()){
                    for(NextLane next : lane.getNextLanes()){
                        next.setTargetLane(lanesMap.get(next.getRef()));
                    }
                }
            }
        }
        lanesMap=null; sectionsMap = null;
    }
    
}
