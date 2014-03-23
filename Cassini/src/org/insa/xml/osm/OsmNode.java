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

import java.util.HashMap;
import org.insa.core.roadnetwork.Node;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class OsmNode
 */
@Root(name="node", strict = false)
public class OsmNode {
    
    @Attribute
    private long id;
    
    @Attribute
    private float lat;
    
    @Attribute
    private float lon;
    
    @ElementMap(entry="tag", key="k", value = "v", attribute=true, inline=true, required = false)
    private HashMap<String, String> tags;
    
    public long getId() {
        return id;
    }

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

   private boolean isRoundabout() {
        return  tags!=null && tags.containsKey("highway")
                && tags.get("highway").equalsIgnoreCase("mini_roundabout") ;
    }
    
   private boolean isTrafficLight(){
         return  tags!=null && tags.containsKey("highway")
                && tags.get("highway").equalsIgnoreCase("traffic_signals") ; 
   }
    public Node createNode(){
        Node node = new Node(lon, lat);
        node.setId(id);
        node.setTrafficLight(this.isTrafficLight());
        return node;
    }
}
