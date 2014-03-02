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
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.PersistenceException;
import org.simpleframework.xml.core.Validate;

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
    private ArrayList<OsmNodeRef> nodes;
    
    @ElementList(inline = true, required = false)
    private ArrayList<OsmTag> tags;
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public ArrayList<OsmTag> getTags() {
        return tags;
    }
    
    public void setTags(ArrayList<OsmTag> tags) {
        this.tags = tags;
    }
    
    public ArrayList<OsmNodeRef> getNodes() {
        return nodes;
    }
    
    public void setNodes(ArrayList<OsmNodeRef> nodes) {
        this.nodes = nodes;
    }
    public boolean isHighway(){
        boolean contains = false;
        for(OsmTag t : tags){
            contains |= t.getKey().equals("highway");  
        }
        return contains;
    }
    
}
