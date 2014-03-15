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
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class OsmModel
 */
@Root(strict = false, name = "osm")
public class OsmModel {
    @ElementList(inline = true)
    private ArrayList<OsmNode> osmNodes;
    
    @ElementList(inline = true)
    private ArrayList<OsmWay> osmWays;
    /**
     * construct map data from osm
     */
    private RoadsModel roadsModel;
    
    public ArrayList<OsmNode> getOsmNodes() {
        return osmNodes;
    }
    
    public ArrayList<OsmWay> getOsmWays() {
        return osmWays;
    }
    
    public RoadsModel getRoadsModel() {
        return roadsModel;
    }
    
    public void setOsmNodes(ArrayList<OsmNode> osmNodes) {
        this.osmNodes = osmNodes;
    }
    
    public void setOsmWays(ArrayList<OsmWay> osmWays) {
        this.osmWays = osmWays;
    }
    
    @Commit
    private void build(){
        //get all osm nodes in hasmap format
        HashMap<Integer, OsmNode> nodes;
        nodes = createNodesMap();
        //create road from osm ways
        roadsModel = new RoadsModel();
        for(OsmWay way : osmWays){
            if(way.isHighway()){
                way.createRoad(nodes);
                roadsModel.addRoad(way.getRoad());
                if(!way.isOneWay())
                    roadsModel.addRoad(way.getReturnRoad());
            }
        }
        
    }
    /**
     *  put osm nodes in an hashmap for easy accessing
     */
    private HashMap<Integer, OsmNode> createNodesMap(){
        HashMap<Integer, OsmNode> nodes = new HashMap<>();
        for(OsmNode n : osmNodes){
            nodes.put(n.getId(), n);
        }
        //free osmNodes
        osmNodes = null;
        return nodes;
    }
    
    
}
