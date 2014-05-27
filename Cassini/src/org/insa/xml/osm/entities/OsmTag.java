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
package org.insa.xml.osm.entities;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo
 * Class OsmTag
 * See http://wiki.openstreetmap.org/wiki/Tag for details
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root(name="tag")
public class OsmTag {
    @Attribute(name = "k")
    private String key;
    
    @Attribute(name="v")
    private String value;
    
    /**
     * Default constructor
     */
    public OsmTag() {
        //Empty for the moment
    }
    
    /**
     * Get key
     * @return Key 
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Get value
     * @return Value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Set key
     * @param key New key 
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * Set value
     * @param value New value 
     */
    public void setValue(String value) {
        this.value = value;
    }
}
