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
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.insa.controller.MainController;
import org.insa.core.roadnetwork.Node;
import org.simpleframework.xml.Element;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicNode extends Circle {
    protected Node node;
    
    protected ArrayList<GraphicSection> gSections = new ArrayList<GraphicSection>();
    
    @Element
    protected GraphicPoint point;
    
    /**
     * Default constructor
     */
    public GraphicNode() {
        this.node = new Node(this);
        this.setFill(Color.ORANGE);
        this.setRadius(4);
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                MainController.getInstance().performEndGetNode((GraphicNode)event.getSource());
                event.consume();
            }
        
        });
    }
    
    /**
     * Constructor
     * @param longitude
     * @param latitude 
     */
    public GraphicNode(float longitude, float latitude) {
        this();
        point = new GraphicPoint(longitude, latitude);
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
     * Get node
     * @return Node
     */
    public Node getNode() {
        return node;
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

    /*
    public GraphicSection getgSection() {
        return gSection;
    }

    public void setgSection(GraphicSection gSection) {
        this.gSection = gSection;
    }
    */
    
    public void addgSection(GraphicSection section) {
        this.gSections.add(section);
    }

    public ArrayList<GraphicSection> getgSections() {
        return gSections;
    }
    
    
    
}
