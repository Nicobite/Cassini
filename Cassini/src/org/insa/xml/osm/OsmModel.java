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
import org.insa.model.RoadsModel;
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
    private ArrayList<OsmNode> nodes;
    
    @ElementList(inline = true)
    private ArrayList<OsmWay> ways;
    /**
     * construct map data from osm
     */
    private RoadsModel mapModel;
    private HashMap<Integer, OsmNode> osmNodes;
    
    public ArrayList<OsmWay> getWays() {
        return ways;
    }

    public void setWays(ArrayList<OsmWay> ways) {
        this.ways = ways;
    }

    public ArrayList<OsmNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<OsmNode> nodes) {
        this.nodes = nodes;
    }

    public HashMap<Integer, OsmNode> getOsmNodes() {
        return osmNodes;
    }
    
    @Commit
    public void commit(){
        osmNodes = new HashMap<>();
        for(OsmNode node : nodes){
            osmNodes.put(node.getId(), node);
        }
        nodes = null;
    }
    
}
