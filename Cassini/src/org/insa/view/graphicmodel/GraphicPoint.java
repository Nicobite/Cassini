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
package org.insa.view.graphicmodel;

import java.io.Serializable;
import org.simpleframework.xml.Attribute;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicPoint implements Serializable {
    @Attribute(name="lon")
    protected double x;
    
    @Attribute(name="lat")
    protected double y;
    
    /**
     * Default constructor
     */
    public GraphicPoint() {
        //Empty for the moment
    }
    
    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public GraphicPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x or longitude
     * @return x or longitude
     */
    public double getX() {
        return x;
    }

    /**
     * Get y or latitude
     * @return y or latitude
     */
    public double getY() {
        return y;
    }

    /**
     * Set x or longitude
     * @param x new x or longitude
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Set y or latitude
     * @param y new y or latitude 
     */
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphicPoint other = (GraphicPoint) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }
}
