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

import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import org.insa.core.roadnetwork.Node;
import org.insa.core.roadnetwork.Section;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicSection extends Polygon {
    protected Section section;
    
    protected double sourceDeltaX;
    protected double sourceDeltaY;
    protected double targetDeltaX;
    protected double targetDeltaY;
    
    @ElementList
    protected ArrayList<Double> longLatPoints = new ArrayList<>();
    
    @Element(name="from")
    protected GraphicNode sourceNode;
    
    @Element(name="to")
    protected GraphicNode targetNode;
    
    @ElementList
    protected ArrayList<GraphicLane>forwardLanes = new ArrayList<>();
    
    @ElementList
    protected ArrayList<GraphicLane>backwardLanes = new ArrayList<>();
    
    /**
     * Default constructor
     */
    public GraphicSection() {
        this.section = null;
        this.setStrokeLineCap(StrokeLineCap.BUTT);
        this.setFill(Color.GRAY);
    }
    
    /**
     * Constructor
     * @param section Reference to section
     * @param from Source node
     * @param to Target node
     */
    public GraphicSection(Section section, Node from, Node to) {
        this.section = section;
        sourceNode = from.getGraphicNode();
        targetNode = to.getGraphicNode();
        this.setStrokeLineCap(StrokeLineCap.BUTT);
        this.setFill(Color.GRAY);
    }
    
    /**
     * Get section
     * @return Section
     */
    public Section getSection() {
        return section;
    }

    /**
     * Get source node
     * @return Source node
     */
    public GraphicNode getSourceNode() {
        return sourceNode;
    }

    /**
     * Get target node
     * @return Target node
     */
    public GraphicNode getTargetNode() {
        return targetNode;
    }

    /**
     * Get forward lanes
     * @return Forward lanes list
     */
    public ArrayList<GraphicLane> getForwardLanes() {
        return forwardLanes;
    }

    /**
     * Get backward lanes
     * @return Backward lanes list
     */
    public ArrayList<GraphicLane> getBackwardLanes() {
        return backwardLanes;
    }
    
    /**
     * Get source delta x
     * @return Source delta x
     */
    public double getSourceDeltaX() {
        return sourceDeltaX;
    }

    /**
     * Get source delta y
     * @return Source delta y
     */
    public double getSourceDeltaY() {
        return sourceDeltaY;
    }

    /**
     * Get target delta x
     * @return 
     */
    public double getTargetDeltaX() {
        return targetDeltaX;
    }

    /**
     * Get target delta y
     * @return Target delta y
     */
    public double getTargetDeltaY() {
        return targetDeltaY;
    }
    
    /**
     * Get longitude and latitude points list
     * @return Points list
     */
    public ArrayList<Double> getLongLatPoints() {
        return longLatPoints;
    }

    /**
     * Set section
     * @param section New section
     */
    public void setSection(Section section) {
        this.section = section;
    }

    /**
     * Set source node
     * @param sourceNode New source node 
     */
    public void setSourceNode(GraphicNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    /**
     * Set target node
     * @param targetNode New target node 
     */
    public void setTargetNode(GraphicNode targetNode) {
        this.targetNode = targetNode;
    }

    /**
     * Set forward lanes
     * @param forwardLanes New forward lanes list 
     */
    public void setForwardLanes(ArrayList<GraphicLane> forwardLanes) {
        this.forwardLanes = forwardLanes;
    }

    /**
     * Set backward lanes
     * @param backwardLanes New backward lanes list 
     */
    public void setBackwardLanes(ArrayList<GraphicLane> backwardLanes) {
        this.backwardLanes = backwardLanes;
    }
   
    /**
     * Set source delta x
     * @param sourceDeltaX New source delta x 
     */
    public void setSourceDeltaX(double sourceDeltaX) {
        this.sourceDeltaX = sourceDeltaX;
    }

    /**
     * Set source delta y
     * @param sourceDeltaY New source delta y 
     */
    public void setSourceDeltaY(double sourceDeltaY) {
        this.sourceDeltaY = sourceDeltaY;
    }

    /**
     * Set target delta x
     * @param targetDeltaX New source delta x 
     */
    public void setTargetDeltaX(double targetDeltaX) {
        this.targetDeltaX = targetDeltaX;
    }

    /**
     * Set target delta y
     * @param targetDeltaY New source delta y
     */
    public void setTargetDeltaY(double targetDeltaY) {
        this.targetDeltaY = targetDeltaY;
    }
    
    /**
     * Set longitude and latitude points list
     * @param longLatPoints New points list
     */
    public void setLongLatPoints(ArrayList<Double> longLatPoints) {
        this.longLatPoints = longLatPoints;
    }

    @Override
    public String toString() {
        return "GraphicSection{" + "section=" + section + ", sourceNode=" + sourceNode + ", targetNode=" + targetNode + ", forwardLanes=" + forwardLanes + ", backwardLanes=" + backwardLanes + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphicSection other = (GraphicSection) obj;
        if (!Objects.equals(this.section, other.section)) {
            return false;
        }
        return true;
    }    
}
