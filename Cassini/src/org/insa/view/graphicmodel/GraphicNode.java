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
package org.insa.view.graphicmodel;

import org.insa.core.roadnetwork.Node;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicNode {
    protected Node node;
    
    @Attribute(name="lon")
    protected float longitude;

    @Attribute(name="lat")
    protected float latitude;
    
    /**
     * Default constructor
     */
    public GraphicNode() {
        this.node = null;
    }
    
    /**
     * Constructor
     * @param node Refernce to node
     */
    public GraphicNode(Node node) {
        this.node = node;
    }

    /**
     * Get longitude
     * @return Longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Get latitude
     * @return Latitude
     */
    public float getLatitude() {
        return latitude;
    }
    
    /**
     * Set longitude
     * @param longitude New longitude 
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Set latitude
     * @param latitude New latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Node: lon = "+longitude+",lat="+latitude;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphicNode other = (GraphicNode) obj;
        if (Float.floatToIntBits(this.longitude) != Float.floatToIntBits(other.longitude)) {
            return false;
        }
        if (Float.floatToIntBits(this.latitude) != Float.floatToIntBits(other.latitude)) {
            return false;
        }
        return true;
    }
}
