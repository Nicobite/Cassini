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
package org.insa.view.panel;

import javafx.scene.layout.Pane;
import org.insa.controller.MainController;
import org.insa.core.roadnetwork.Road;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicNode;
import org.insa.view.graphicmodel.GraphicSection;
import org.insa.view.utils.DrawingUtils;

/**
 *
 * @author Thiebaud Thomas
 */
public class NodeDrawingPanel extends Pane {
    private final DrawingUtils drawingUtils;
    private RoadsModel roads = MainController.getInstance().getModel().getRoadModel();
    private boolean isSourceNode = false;

    /**
     * Constructor
     * @param drawingUtils Reference to drawing utils 
     */
    NodeDrawingPanel(DrawingUtils drawingUtils) {
        this.drawingUtils = drawingUtils;
        drawingUtils.initializeBounds(roads.getMinLon(), roads.getMaxLon(), roads.getMinLat(), roads.getMaxLat());
    }
    
    /**
     * Paint all nodes
     */
    public void paint() {
        if(isSourceNode)
            this.paintSourceNode();
        else
            this.paintTargetNode();
    }
    
    /**
     * Paint all source nodes
     */
    public void paintSourceNode() {
        isSourceNode = true;
        for(Road r : roads.getRoads()) { 
            for(GraphicSection gSection : r.getGraphicRoad().getSections()) { 
                GraphicNode sourceNode = gSection.getSourceNode();
                sourceNode.setCenterX(drawingUtils.longToX(sourceNode.getLongitude()));
                sourceNode.setCenterY(drawingUtils.latToY(sourceNode.getLatitude()));
                this.getChildren().add(sourceNode);
            }
        }
    }
    
    /**
     * Paint all target nodes
     */
    public void paintTargetNode() {
        isSourceNode = false;
        for(Road r : roads.getRoads()) { 
            for(GraphicSection gSection : r.getGraphicRoad().getSections()) { 
                GraphicNode targetNode = gSection.getTargetNode();
                targetNode.setCenterX(drawingUtils.longToX(targetNode.getLongitude()));
                targetNode.setCenterY(drawingUtils.latToY(targetNode.getLatitude()));
                this.getChildren().add(targetNode);
            }
        }
    }
    
    /**
     * Paint all first source node and all last target node
     */
    public void paintFirstAndLast() {
        this.getChildren().clear();
        for(Road r : roads.getRoads()) {
            this.paintFirstAndLast(r);
        }
    }
    
    /**
     * Paint all first source node and all last target node of the given road
     * @param r Given road
     */
    public void paintFirstAndLast(Road r) {
        GraphicNode node = null;
        
        if((node = r.getLastSection().getTargetNode()) != null) {
            node.setCenterX(drawingUtils.longToX(node.getLongitude()));
            node.setCenterY(drawingUtils.latToY(node.getLatitude()));
            if(!this.getChildren().contains(node))
                this.getChildren().add(node);
        }

        if((node = r.getFirstSection().getSourceNode()) != null) {
            node.setCenterX(drawingUtils.longToX(node.getLongitude()));
            node.setCenterY(drawingUtils.latToY(node.getLatitude()));
            if(!this.getChildren().contains(node))
                this.getChildren().add(node);
        }
        
    }
    
    /**
     * Paint all first source node
     */
    void paintFirst() {
        for(Road r : roads.getRoads()) {
            this.paintFirst(r);
        }
    }
    
    /**
     * Paint all first source node of the given road
     * @param r Given road
     */
    void paintFirst(Road r) {
        GraphicNode node = null;

        if((node = r.getFirstSection().getSourceNode()) != null) {
            node.setCenterX(drawingUtils.longToX(node.getLongitude()));
            node.setCenterY(drawingUtils.latToY(node.getLatitude()));
            if(!this.getChildren().contains(node))
                this.getChildren().add(node);
        }
    }

    /**
     * Remove all nodes from pane and paint them again
     */
    public void repaint() {
        this.getChildren().clear();
        this.paint();
    }
}
