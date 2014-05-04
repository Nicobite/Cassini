/*
* Copyright 2014 Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
package org.insa.core.roadnetwork;

import java.util.Objects;
import org.insa.core.enums.TrafficSignaling;
import org.insa.view.graphicmodel.GraphicNode;
import org.insa.view.graphicmodel.GraphicPoint;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Node
 * Represent a graph node
 * The simulation model is based on graph
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root(strict = false)
public class Node {
    protected GraphicNode gNode;
    /**
     * node id (node number)
     */
    @Attribute(required = false)
    private long id;
    
    @Attribute
    private TrafficSignaling signaling;
    
    /**
     * Construction of graph nodes
     * @param longitude
     * @param latitude
     */
    public Node(float longitude, float latitude) {
        gNode = new GraphicNode(this);
        gNode.setPoint(new GraphicPoint(longitude, latitude));
    }
    
    /**
     *  Default constructor
     */
    public Node() {
        super();
        gNode = new GraphicNode(this);
    }
    /*
    * getters et setters
    */
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public TrafficSignaling getSignaling() {
        return signaling;
    }

    public void setSignaling(TrafficSignaling signaling) {
        this.signaling = signaling;
    }
    
    
    @Override
    public String toString(){
        return gNode.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.gNode, other.gNode)) {
            return false;
        }
        return true;
    }
    
    public GraphicNode getGraphicNode() {
        return gNode;
    }
}
