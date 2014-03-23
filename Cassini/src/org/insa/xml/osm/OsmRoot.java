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
import org.insa.model.items.RoadsModel;
import org.insa.xml.osm.entities.OsmBound;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui &  Thomas Thiebaud
 Class OsmRoot
 */
@Root(strict = false, name = "osm") //Osm document root (osm relations are not processed)
public class OsmRoot {
    /**
     * osm map bounds
     */
    @Element
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
        //get all osm nodes in hasmap format
        HashMap<Long, OsmNode> nodes;
        nodes = putOsmNodesInHashmap();
        
        //create road from osm ways
        RoadsModel roadsModel = new RoadsModel();
        
        //set map bounds
        roadsModel.setMaxLat(this.bounds.getMaxlat());
        roadsModel.setMaxLon(this.bounds.getMaxlon());
        roadsModel.setMinLat(this.bounds.getMinlat());
        roadsModel.setMinLon(this.bounds.getMinlon());
        
        for(OsmWay way : osmWays){
            if(way.isHighway()){
                roadsModel.addRoad(way.buildRoad(nodes));
            }
        }
        return roadsModel;
    }
    /**
     *  put osm nodes in an hashmap for easy access
     */
    private HashMap<Long, OsmNode> putOsmNodesInHashmap(){
        HashMap<Long, OsmNode> nodes = new HashMap<>();
        for(OsmNode n : osmNodes){
            nodes.put(n.getId(), n);
        }
        osmNodes = null;
        return nodes;
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
