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
 * Class OsmBound
 * See http://wiki.openstreetmap.org/wiki/.osm for details
 */
@Root(name="bounds", strict = false)
public class OsmBound {
     /**
     * bounds of the road network : minimum longitude
     */
    @Attribute
    private float minLon;
    /**
     * bounds of the road network : minimum latitude
     */
    @Attribute
    private float minLat;
    /**
     * bounds of the road network : maximum longitude
     */
    @Attribute
    private float maxLon;
    /**
     * bounds of the road network : maximum latitude
     */
    @Attribute
    private float maxLat;

    /**
     * Default constructor
     */
    public OsmBound(){
        //Empty for the moment
    }
    
    /**
     * Constructor
     * @param minLat Minimum longitude
     * @param minLon Minimum latitude
     * @param maxLat Maximum longitude
     * @param maxLon Maximum latitude
     */
    public OsmBound(float minLat, float minLon, float maxLat, float maxLon){
        this.minLat = minLat;
        this.minLon = minLon;
        this.maxLat = maxLat;
        this.maxLon = maxLon;
    }
    
    /**
     * Get minimum longitude
     * @return Minimum longitude
     */
    public float getMinLon() {
        return minLon;
    }

    /**
     * Get minimum latitude
     * @return Minimum latitud
     */
    public float getMinLat() {
        return minLat;
    }

    /**
     * Get maximum longitude
     * @return Maximum longitude
     */
    public float getMaxLon() {
        return maxLon;
    }

    /**
     * get maximum latitude
     * @return Maximum latitude
     */
    public float getMaxLat() {
        return maxLat;
    }

    /**
     * Set minimum longitude
     * @param minLon New minimum longitude
     */
    public void setMinLon(float minLon) {
        this.minLon = minLon;
    }

    /**
     * Set minimum latitude
     * @param minLat New minimum latitude
     */
    public void setMinLat(float minLat) {
        this.minLat = minLat;
    }

    /**
     * Set maximum longitude
     * @param maxLon New maximum longitude
     */
    public void setMaxLon(float maxLon) {
        this.maxLon = maxLon;
    }

    /**
     * Set maximum latitude
     * @param maxLat New maximum latitude
     */
    public void setMaxLat(float maxLat) {
        this.maxLat = maxLat;
    }
}
