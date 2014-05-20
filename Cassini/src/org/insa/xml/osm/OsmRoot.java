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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.insa.core.enums.Direction;
import org.insa.core.roadnetwork.NextSection;
import org.insa.core.roadnetwork.Node;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.core.trafficcontrol.TrafficLight;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicSection;
import org.insa.xml.osm.entities.OsmBound;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui &  Thomas Thiebaud
 * Class OsmRoot
 * Maps Osm xml document root
 * <osm ...>
 *   <bounds ....>      </bounds>
 *   <node.......> .... </node>
 *   .....................
 *   <way .......>.......</way>
 *   ..........................
 *   <relation ..>......</relation>
 *   .......................
 * </osm>
 * See http://wiki.openstreetmap.org/wiki/.osm for details
 */
@Root(strict = false, name = "osm") //Osm document root (osm relations are not processed)
public class OsmRoot {
    /**
     * osm map bounds
     */
    @Element(required = false)
    private OsmBound bounds;
    
    /**
     * osm nodes
     */
    @ElementList(inline = true)
    private ArrayList<OsmNode> osmNodes;
    
    /**
     * osm ways
     */
    @ElementList(inline = true)
    private ArrayList<OsmWay> osmWays;
    
    /**
     * build road model from osm data
     * @return the road network
     */
    public RoadsModel buildRoadModel(){
        //if no bounds in osm map
        if(bounds == null)
            bounds = buildBounds(osmNodes);
        
        //get all osm nodes in hasmap format
        HashMap<Long, OsmNode> nodes;
        nodes = toHashMap(osmNodes);
        
        //create road from osm ways
        RoadsModel roadsModel = new RoadsModel();
        
        //set map bounds
        roadsModel.setMaxLat(bounds.getMaxlat());
        roadsModel.setMaxLon(bounds.getMaxlon());
        roadsModel.setMinLat(bounds.getMinlat());
        roadsModel.setMinLon(bounds.getMinlon());
        
        //build the road network from Osm xml
        for(OsmWay way : osmWays){
            if(way.isHighway()){
                Road road = way.buildRoad(nodes);
                if(road.getGraphicRoad().getSections().size()>0)
                    roadsModel.addRoad(road);
            }
        }
        iniJunctions(roadsModel, nodes);
        addConnections(roadsModel);
        //splitRoadsByJunctions(roadsModel);
        return roadsModel;
    }
    public void splitRoadsByJunctions(RoadsModel roadModel){
        ArrayList <Node> nodes = roadModel.getNodes();
        System.out.println(nodes.size());
        int index;
        Section section;
        ArrayList<Road> toAdd = new ArrayList<>() ;
        Road newRoad = new Road();
        ArrayList<GraphicSection> list1, list2;
        boolean isBeginJunction, isEndJunction;
        for(Node node : nodes){
            if(node.getRoads().size()>1)
                for(Road road : node.getRoads()){
                    //split roads
                    isBeginJunction =
                            road.getFirstSection().getSourceNode().equals(node.getGraphicNode());
                    isEndJunction =
                            road.getLastSection().getTargetNode().equals(node.getGraphicNode());
                    if(!(isBeginJunction || isEndJunction) && road.size()>1){
                        //section =  road.findSectionBySourceNode(node.getGraphicNode().getNode());
                        index = road.getNodes().indexOf(node);
                        if(index >0){
                            list1 = new ArrayList<>(road.getGraphicRoad().getSections().subList(0, index));
                            System.out.println("road size : "+road.size()+";"+index);
                            list2 = new ArrayList<>(road.getGraphicRoad().getSections().subList(index,road.size()));
                            newRoad.getGraphicRoad().setSections(list2);
                            road.getGraphicRoad().setSections(list1);
                            newRoad.setId(hashCode());
                            newRoad.setType(road.getType()); newRoad.setOneway(road.isOneway());
                            roadModel.addRoad(newRoad);
                            
                            toAdd.add(newRoad);
                            
                            for(Node n : nodes){
                                n.updateRoad(index, road, newRoad);
                            }
                            /* for(Node n : road.getNodes()){
                            n.updateRoad(newRoad, road);
                            }
                            /*
                            System.out.println("Road 1 : ");
                            for(GraphicSection s : road.getGraphicRoad().getSections()){
                            System.out.println(s.getSection().getId());
                            }
                            
                            System.out.println("Road 2 : ");
                            for(GraphicSection s : newRoad.getGraphicRoad().getSections()){
                            System.out.println(s.getSection().getId());
                            s.getSection().setRoad(newRoad);
                            }*/
                        }
                    }
                }
            node.getRoads().addAll(toAdd);
        }
    }
    /**
     * Get all the traffic lights from the roads network
     * @return
     */
    public ArrayList<TrafficLight> getTrafficLightFromRoads(){
        ArrayList<TrafficLight> result = new ArrayList<>();
        for(OsmNode n : this.osmNodes){
            if(n.isTrafficLight()){
                result.add(new TrafficLight(n.getId(), n.getGraphicNode().getNode()));
            }
        }
        return result;
    }
    /**
     * Adds connections between roads at network-wide
     * @param roadModel
     * @param osmNodes
     */
    public void addConnections(RoadsModel roadModel){
        Long id;
        Section section;
        ArrayList<Road> others ;
        ArrayList <Node> nodes = roadModel.getNodes();
        for(Node node : nodes){
            // get the roads containing this node and build connections
            for(Road road : node.getRoads()){
                others = new ArrayList<>(node.getRoads());
                others.remove(road);
                if(others.size()>0)
                    connectRoads(node.getGraphicNode().getNode(), road, others);
                
            }
        }
        
    }
    
    /**
     * Add connections between a road and it's sucessors roads
     * @param road
     * @param otherRoads
     * @param node
     */
    public void connectRoads(Node node, Road road, ArrayList<Road>otherRoads){
        Section target = road.findSectionByTargetNode(node);
        Section source = road.findSectionBySourceNode(node);
        Section otherSource = null;
        Section otherTarget = null;
        for(Road r : otherRoads){
            otherSource = r.findSectionBySourceNode(node);
            if(otherSource != null){
                if(target!=null){
                    //add connections between lanes
                    target.getGraphicSection().addConnections(target.getGraphicSection().getForwardLanes(),
                            otherSource.getGraphicSection().getForwardLanes());
                    target.addSuccessor(new NextSection(otherSource, Direction.FORWARD));
                    //System.err.println("f-f"+road.getId()+","+r.getId());
                }
                if(source!=null && !road.isOneway()){
                    source.getGraphicSection().addConnections(source.getGraphicSection().getBackwardLanes(),
                            otherSource.getGraphicSection().getForwardLanes());
                    source.addSuccessor(new NextSection(otherSource, Direction.BACKWARD));
                    // System.err.println("b-f"+road.getId()+","+r.getId());
                }
            }
            
            otherTarget = r.findSectionByTargetNode(node);
            if(otherTarget != null){
                if(target!=null && !r.isOneway()){
                    target.getGraphicSection().addConnections(target.getGraphicSection().getForwardLanes(),
                            otherTarget.getGraphicSection().getBackwardLanes());
                    target.addSuccessor(new NextSection(otherTarget, Direction.FORWARD));
                    //System.err.println("f-b"+road.getId()+","+r.getId());
                }
                
                if(source!=null && !road.isOneway()&& !r.isOneway()){
                    source.getGraphicSection().addConnections(source.getGraphicSection().getBackwardLanes(),
                            otherTarget.getGraphicSection().getBackwardLanes());
                    source.addSuccessor(new NextSection(otherTarget, Direction.BACKWARD));
                    // System.err.println("b-b"+road.getId()+","+r.getId());
                }
            }
        }
        
    }
    
    
    /**
     *  put osm nodes in an hashmap for easy access (referenced by id)
     */
    private HashMap<Long, OsmNode> toHashMap(ArrayList<OsmNode> list){
        HashMap<Long, OsmNode> map = new HashMap<>();
        for(OsmNode o : list){
            map.put(o.getId(), o);
        }
        return map;
    }
    private void iniJunctions(RoadsModel model, HashMap<Long, OsmNode> list){
        ArrayList<Node> nodes = model.getNodes();
        for(Node n : nodes){
            n.setRoads(list.get(n.getId()).getRoads());
        }
    }
    /**
     * infer the road network bounds (min latitude and longitude,\n
     * and max longitude and latitude
     * @param nodes the list of osm nodes
     * @return the boundary of the road network
     */
    public OsmBound buildBounds(ArrayList<OsmNode> nodes){
        OsmBound bound = new OsmBound(1000,1000,0,0);
        for(OsmNode node : nodes){
            if(node.getLat()> bound.getMaxlat()){
                bound.setMaxlat(node.getLat());
            }
            if(node.getLat()< bound.getMinlat()){
                bound.setMinlat(node.getLat());
            }
            if(node.getLon()> bound.getMaxlon()){
                bound.setMaxlon(node.getLon());
            }
            if(node.getLon()< bound.getMinlon()){
                bound.setMinlon(node.getLon());
            }
            
        }
        return bound;
    }
    
    /*
    * getters and setters
    */
    
    public OsmBound getBounds() {
        return bounds;
    }
    
    public void setBounds(OsmBound bounds) {
        this.bounds = bounds;
    }
    
    public ArrayList<OsmNode> getOsmNodes() {
        return osmNodes;
    }
    
    public void setOsmNodes(ArrayList<OsmNode> osmNodes) {
        this.osmNodes = osmNodes;
    }
    
    public ArrayList<OsmWay> getOsmWays() {
        return osmWays;
    }
    
    public void setOsmWays(ArrayList<OsmWay> osmWays) {
        this.osmWays = osmWays;
    }
    
}
