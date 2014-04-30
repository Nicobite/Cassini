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
import java.util.Objects;
import org.insa.core.roadnetwork.Node;
import org.insa.core.roadnetwork.Section;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicSection {
    protected Section section;
    protected GraphicNode sourceNode;
    protected GraphicNode targetNode;
    protected ArrayList<GraphicLane>forwardLanes = new ArrayList<GraphicLane>();
    protected ArrayList<GraphicLane>backwardLanes = new ArrayList<GraphicLane>();
    
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

    @Override
    public String toString() {
        return "GraphicSection{" + "section=" + section + ", sourceNode=" + sourceNode + ", targetNode=" + targetNode + ", forwardLanes=" + forwardLanes + ", backwardLanes=" + backwardLanes + '}';
    }

    @Override
    public boolean equals(Object obj) {
        final GraphicSection other = (GraphicSection) obj;
        if (!Objects.equals(this.section, other.section)) {
            return false;
        }
        return true;
    }
}
