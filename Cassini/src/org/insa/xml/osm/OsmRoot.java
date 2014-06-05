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
package org.insa.xml.osm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class OsmRoot
 * Maps Osm xml document root
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
     * Default constructor
     */
    public OsmRoot() {
        //Empty for the moment
    }
    
    /**
     * Build road model from osm data
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
        roadsModel.setMaxLat(bounds.getMaxLat());
        roadsModel.setMaxLon(bounds.getMaxLon());
        roadsModel.setMinLat(bounds.getMinLat());
        roadsModel.setMinLon(bounds.getMinLon());
        
        //build the road network from Osm xml
        for(OsmWay way : osmWays){
            if(way.isHighway()){
                Road road = way.buildRoad(nodes);
                if(road.getGraphicRoad().getSections().size()>0)
                    roadsModel.addRoad(road);
            }
        }
        addConnections(roadsModel, nodes);
        return roadsModel;
    }
    
    /**
     * Split roads in order to create junctions
     * @param roadModel Road model
     */
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
    public void addConnections(RoadsModel roadModel, HashMap<Long, OsmNode> osmNodes ){
        Iterator<Long> iter = osmNodes.keySet().iterator();
        Long id;
        OsmNode osmNode;
        ArrayList<Road> others = null;
        while(iter.hasNext()){
            id = iter.next();
            // get osm node
            osmNode = osmNodes.get(id);
            // get the roads at containing this node and build connections
            for(Road road : osmNode.getRoads()){
                others = new ArrayList<>(osmNode.getRoads());
                others.remove(road);
                if(others.size()>0)
                    connectRoads(osmNode.getGraphicNode().getNode(), road, others);
            }
                
            }
        }
    /**
     * Add connections between a road and it's sucessors roads
     * @param road Road to connect
     * @param otherRoads Other roads
     * @param node Connexion node
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
    
    /**
     * infer the road network bounds (min latitude and longitude,\n
     * and max longitude and latitude
     * @param nodes the list of osm nodes
     * @return the boundary of the road network
     */
    public OsmBound buildBounds(ArrayList<OsmNode> nodes){
        OsmBound bound = new OsmBound(1000,1000,0,0);
        for(OsmNode node : nodes){
            if(node.getLat()> bound.getMaxLat()){
                bound.setMaxLat(node.getLat());
            }
            if(node.getLat()< bound.getMinLat()){
                bound.setMinLat(node.getLat());
            }
            if(node.getLon()> bound.getMaxLon()){
                bound.setMaxLon(node.getLon());
            }
            if(node.getLon()< bound.getMinLon()){
                bound.setMinLon(node.getLon());
            }
            
        }
        return bound;
    }

    /**
     * Get bounds
     * @return bounds
     */
    public OsmBound getBounds() {
        return bounds;
    }

    /**
     * Get osm nodes
     * @return Osm nodes
     */
    public ArrayList<OsmNode> getOsmNodes() {
        return osmNodes;
    }

    /**
     * Get osm ways
     * @return Osm ways
     */
    public ArrayList<OsmWay> getOsmWays() {
        return osmWays;
    }

    /**
     * Set bounds
     * @param bounds New bounds 
     */
    public void setBounds(OsmBound bounds) {
        this.bounds = bounds;
    }

    /**
     * Set osm nodes
     * @param osmNodes New osm nodes 
     */
    public void setOsmNodes(ArrayList<OsmNode> osmNodes) {
        this.osmNodes = osmNodes;
    }

    /**
     * Set osm ways
     * @param osmWays New osm ways 
     */
    public void setOsmWays(ArrayList<OsmWay> osmWays) {
        this.osmWays = osmWays;
    }
}
