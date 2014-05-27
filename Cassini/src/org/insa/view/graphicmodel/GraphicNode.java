/*
* Copyright 2014 Juste Abel Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
import java.util.ArrayList;
import java.util.Objects;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.insa.controller.MainController;
import org.insa.core.roadnetwork.Node;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.core.Commit;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicNode extends Circle implements EventHandler<MouseEvent>, Serializable {
    @Element
    protected transient Node node;
    
    protected transient ArrayList<GraphicSection> gSections = new ArrayList<>();
    
    @Element
    protected GraphicPoint point;
    
    /**
     * Default constructor
     */
    public GraphicNode() {
        this.node = new Node(this);
        this.setFill(Color.ORANGE);
        this.setRadius(4);

        this.setOnMouseClicked(this);
        this.setOnMouseEntered(this);
        this.setOnMouseExited(this);
        this.setOnDragDetected(this);
    }
    
    /**
     * Constructor
     * @param longitude Longitude or X
     * @param latitude Latitude or Y
     */
    public GraphicNode(float longitude, float latitude) {
        this();
        point = new GraphicPoint(longitude, latitude);
    }
    
    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType() == MouseEvent.DRAG_DETECTED) {
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            MainController.getInstance().performSetMovingNode(this);
            content.putString("GraphicNode");
            db.setContent(content);
        }
        if(event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MainController.getInstance().performGetNode((GraphicNode)event.getSource());
        }
        if(event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            this.setFill(Color.RED);
        }
        if(event.getEventType() == MouseEvent.MOUSE_EXITED) {
            this.setFill(Color.ORANGE);
        }
        event.consume();
    }
    
    /**
     * Add a graphic section
     * @param section Graphic section to add
     */
    public void addGraphicSection(GraphicSection section) {
        this.gSections.add(section);
    }
    
    /**
     * Get graphic section list
     * @return Graphic section list
     */
    public ArrayList<GraphicSection> getGraphicSections() {
        return gSections;
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
        return (this.getLatitude()==other.getLatitude()&& this.getLongitude()==other.getLongitude());
    }
    
    /**
     * called when deserializing this object
     */
    @Commit
    private void build(){
        this.node.setGraphicNode(this);
    }
}
