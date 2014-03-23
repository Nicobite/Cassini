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
package org.insa.xml.osm.entities;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class OsmBound
 */
@Root(name="bounds", strict = false)
public class OsmBound {
     /**
     * bounds of the road network : minimum longitude
     */
    @Attribute
    private float minlon;
    /**
     * bounds of the road network : minimum latitude
     */
    @Attribute
    private float minlat;
    /**
     * bounds of the road network : maximum longitude
     */
    @Attribute
    private float maxlon;
    /**
     * bounds of the road network : maximum latitude
     */
    @Attribute
    private float maxlat;

    public float getMaxlat() {
        return maxlat;
    }

    public float getMaxlon() {
        return maxlon;
    }

    public float getMinlat() {
        return minlat;
    }

    public float getMinlon() {
        return minlon;
    }

    public void setMaxlat(float maxlat) {
        this.maxlat = maxlat;
    }

    public void setMaxlon(float maxlon) {
        this.maxlon = maxlon;
    }

    public void setMinlat(float minlat) {
        this.minlat = minlat;
    }

    public void setMinlon(float minlon) {
        this.minlon = minlon;
    }
    
    
}
