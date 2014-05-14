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
package org.insa.core.graphicmodel;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicBounds {
    protected float minLong = 0;
    protected float maxLong = 0;
    protected float minLat = 0;
    protected float maxLat = 0; 

    /**
     * Constructor
     * @param minLong Minimum longitude
     * @param maxLong Maximum longitude
     * @param minLat Minimum latitude
     * @param maxLat Maximum latitude
     */
    public GraphicBounds(float minLong, float maxLong, float minLat, float maxLat) {
        this.minLong = minLong;
        this.maxLong = maxLong;
        this.minLat = minLat;
        this.maxLat = maxLat;
    }
    
    /**
     * Constructor
     * @param bounds Another bounds in order to copy values 
     */
    public GraphicBounds(GraphicBounds bounds) {
        this.minLong = bounds.getMinLong();
        this.maxLong = bounds.getMaxLong();
        this.minLat = bounds.getMinLat();
        this.maxLat = bounds.getMaxLat();
    }

    /**
     * Get minimum longitude
     * @return Minimum longitude
     */
    public float getMinLong() {
        return minLong;
    }

    /**
     * Get maximum longitude
     * @return Maximum longitude
     */
    public float getMaxLong() {
        return maxLong;
    }

    /**
     * Get minimum latitude
     * @return Minimum latitude
     */
    public float getMinLat() {
        return minLat;
    }

    /**
     * Get maximum latitude
     * @return 
     */
    public float getMaxLat() {
        return maxLat;
    }

    /**
     * Set minimum longitude
     * @param minLong New minimum longitude
     */
    public void setMinLong(float minLong) {
        this.minLong = minLong;
    }

    /**
     * Set maximum longitude
     * @param maxLong New maximum longitude
     */
    public void setMaxLong(float maxLong) {
        this.maxLong = maxLong;
    }

    /**
     * Set minimum latitude
     * @param minLat New minimum latitude
     */
    public void setMinLat(float minLat) {
        this.minLat = minLat;
    }

    /**
     * Set maximum latitude
     * @param maxLat New maximum latitude
     */
    public void setMaxLat(float maxLat) {
        this.maxLat = maxLat;
    }
}
