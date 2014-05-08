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
    protected final DrawingUtils drawingUtils;
    protected RoadsModel roads = MainController.getInstance().getModel().getRoadModel();
    boolean isSourceNode = false;

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
     * Remove all nodes from pane and paint them again
     */
    public void repaint() {
        this.getChildren().clear();
        this.paint();
    }
}
