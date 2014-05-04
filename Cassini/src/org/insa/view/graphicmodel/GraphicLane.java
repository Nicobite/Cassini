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

import org.insa.core.roadnetwork.Lane;
import org.simpleframework.xml.Element;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicLane {
    protected Lane lane;
    
    @Element
    protected GraphicPoint sourcePoint;
    
    @Element
    protected GraphicPoint targetPoint;
    
    /**
     * Default constructor
     */
    public GraphicLane() {
        this.lane = null;
    }
    
    /**
     * Constructor
     * @param lane Reference to lane
     */
    public GraphicLane(Lane lane) {
        this.lane = lane;
    }
 
    /**
     * Get lane
     * @return Lane
     */
    public Lane getLane() {
        return lane;
    }

    /**
     * Get source point
     * @return Source point
     */
    public GraphicPoint getSourcePoint() {
        return sourcePoint;
    }

    /**
     * Get target point
     * @return Target point
     */
    public GraphicPoint getTargetPoint() {
        return targetPoint;
    }

    /**
     * Set source point
     * @param sourcePoint New source point 
     */
    public void setSourcePoint(GraphicPoint sourcePoint) {
        this.sourcePoint = sourcePoint;
    }

    /**
     * Set target point
     * @param targetPoint New target point 
     */
    public void setTargetPoint(GraphicPoint targetPoint) {
        this.targetPoint = targetPoint;
    }
}
