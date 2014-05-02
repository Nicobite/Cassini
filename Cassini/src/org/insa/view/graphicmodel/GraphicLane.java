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

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import org.insa.core.roadnetwork.Lane;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicLane extends Polygon {
    protected Lane lane;
    
    @ElementList
    protected ArrayList<Double> longLatPoints = new ArrayList<>();
    
    /**
     * Default constructor
     */
    public GraphicLane() {
        this.lane = null;
        this.setStrokeLineCap(StrokeLineCap.BUTT);
        this.setFill(Color.GRAY);
    }
    
    /**
     * Constructor
     * @param lane Reference to lane
     * @param points Points of the polygon wich represents the lane
     */
    public GraphicLane(Lane lane, double... points) {
        super(points);
        this.lane = lane;
        this.setStrokeLineCap(StrokeLineCap.BUTT);
        this.setFill(Color.GRAY);
    }
 
    /**
     * Get lane
     * @return Lane
     */
    public Lane getLane() {
        return lane;
    }
    
    /**
     * Get longitude and latitude points list
     * @return Points list
     */
    public ArrayList<Double> getLongLatPoints() {
        return longLatPoints;
    }

    /**
     * Set longitude and latitude points list
     * @param longLatPoints New points list
     */
    public void setLongLatPoints(ArrayList<Double> longLatPoints) {
        this.longLatPoints = longLatPoints;
    }
}
