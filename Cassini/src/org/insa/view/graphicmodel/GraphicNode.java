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

import java.util.Objects;
import org.insa.core.roadnetwork.Node;
import org.simpleframework.xml.Element;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicNode {
    protected Node node;
    
    @Element
    protected GraphicPoint point;
    
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
     * Get point
     * @return Graphic point
     */
    public GraphicPoint getPoint() {
        return point;
    }
    
    /**
     * Get longitude
     * @return longitude
     */
    public double getLongitude() {
        return point.getX();
    }
    
    /**
     * Get latitude
     * @return latitude
     */
    public double getLatitude() {
        return point.getY();
    }

    /**
     * Set point
     * @param point New point 
     */
    public void setPoint(GraphicPoint point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "GraphicNode{" + "point=" + point + '}';
    }

    public Node getNode() {
        return node;
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
        if (!Objects.equals(this.point, other.point)) {
            return false;
        }
        return true;
    }
}
