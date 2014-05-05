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
import org.insa.core.enums.TrafficSignaling;
import org.insa.core.roadnetwork.Node;
import org.insa.core.roadnetwork.Road;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo
 * Class OsmNode
 * this class maps an osm xml node element
 * <node id="1" lat="1.0" lon="1.0" .....>
 *      <tag k="highway" v="roundabout"></tag>
 * </node>
 * See http://wiki.openstreetmap.org/wiki/Node for details
 */
@Root(name="node", strict = false)
public class OsmNode {
    /**
     * Node id
     */
    @Attribute
    private long id;
    /**
     * latitude attribute
     */
    @Attribute
    private float lat;
    
    /**
     * longitude attribute
     */
    @Attribute
    private float lon;
    /**
     * list of tags representing features like highway, ...
     */
    @ElementMap(entry="tag", key="k", value = "v", attribute=true, inline=true, required = false)
    private HashMap<String, String> tags;
    
    /**
     * set of roads (road id) containing this node 
     */
    private ArrayList<Road> roads;
    
    public long getId() {
        return id;
    }

    public OsmNode() {
        roads = new ArrayList<>();
    }

    
    /**
     * check wether this node is a rounabout\n
     * @return  true if roundabout false otherwise
     */
    private boolean isRoundabout() {
        return  tags!=null && tags.containsKey("highway")
                && tags.get("highway").equalsIgnoreCase("mini_roundabout") ;
    }
    /**
     * check wether this node is a traffic light
     * @return true if traffic light and false otherwise
     */
    private boolean isTrafficLight(){
        return  tags!=null && tags.containsKey("highway")
                && tags.get("highway").equalsIgnoreCase("traffic_signals") ;
    }
    /**
     * create a node from this osm node
     * @return the node created
     */
    public Node createNode(){
        Node node = new Node(lon, lat);
        node.setId(id);
        node.setSignaling(getSignaling());
        return node;
    }
    /**
     * Add a road to the roads list
     * @param road
     */
    public void addRoad(Road road){
        if(!this.roads.contains(road))
            this.roads.add(road);
    }
    /**
     * retrieve traffic signaling attributes from osm node
     * @return 
     */
    private TrafficSignaling getSignaling(){
        TrafficSignaling res = TrafficSignaling.NONE;
        if(tags!=null && tags.containsKey("highway")){
            switch(tags.get("highway")){
                case "mini_roundabout" :
                    res = TrafficSignaling.ROUNDABOUT;
                    break;
                case "traffic_signals" :
                    res= TrafficSignaling.TRAFFIC_LIGHT;
                    break;
                case "turning_circle" :
                    res = TrafficSignaling.TURN_LOOP;
                    break;
                case "turning_loop" :
                    res = TrafficSignaling.TURN_LOOP;
                    break;
                case "stop" :
                    res= TrafficSignaling.STOP;
                    break;
            }
        }
        return res;
    }
    
    /* getters and setters */
    
    public void setId(long id) {
        this.id = id;
    }
    
    public float getLat() {
        return lat;
    }
    
    public void setLat(float lat) {
        this.lat = lat;
    }
    
    public float getLon() {
        return lon;
    }
    
    public void setLon(float lon) {
        this.lon = lon;
    }
    
    public HashMap<String, String> getTags() {
        return tags;
    }
    
    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

 
}
