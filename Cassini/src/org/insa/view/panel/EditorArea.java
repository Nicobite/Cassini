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
package org.insa.view.panel;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.insa.core.enums.Direction;
import org.insa.core.roadnetwork.NextSection;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.view.dock.EditorToolsDock;
import org.insa.view.graphicmodel.GraphicNode;
import org.insa.view.graphicmodel.GraphicSection;
import org.insa.view.utils.DrawingUtils;

/**
 *
 * @author Thiebaud Thomas
 */
public class EditorArea extends RoadDrawingPanel implements EventHandler<MouseEvent> {
    private final Pane boundPane = new Pane();
    private final NodeDrawingPanel nodeDrawingPanel;
    private final Pane currentRoadPane = new Pane();
    
    private EditorToolsDock editorToolsDock;
    
    private Line minLong = new Line();
    private Line maxLong = new Line();
    private Line minLat = new Line();
    private Line maxLat = new Line();
    
    private boolean isWaitingSize = false;
    private boolean isDrawingRoad = false;
    
    private Road currentDrawingRoad = null;
    
    private GraphicNode sourceNode = null;
    private GraphicNode targetNode = null;
    
    /**
     * Constructor
     * @param drawingUtils Reference to drawing utils
     */
    public EditorArea(DrawingUtils drawingUtils) {
        super(drawingUtils);
        this.init();
        this.paint();
        nodeDrawingPanel = new NodeDrawingPanel(drawingUtils);
        this.getChildren().add(boundPane);
        this.getChildren().add(currentRoadPane);
        this.getChildren().add(nodeDrawingPanel);
        this.setOnMouseClicked(this);
    }
    
    /**
     * Draw a vertical line which represent longitude bound (min or max)
     * @param longitude Longitude bound
     * @param isMin true if the bound is the minimum longitude, false otherwise
     */
    public void drawVerticalBound(float longitude, boolean isMin) {
        double x = drawingUtils.longToX(longitude);
        
        if(isMin)
            minLong = new Line(x, 0, x, this.getHeight());
        else
            maxLong = new Line(x, 0, x, this.getHeight());
        
        this.repaintBoundLines();
    }
    
    /**
     * Draw a horizontal line which represent latitude bound (min or max)
     * @param latitude Latitude bound
     * @param isMin true if the bound is the minimum latitude, false otherwise
     */
    public void drawHorizontalBound(float latitude, boolean isMin) {
        double y = drawingUtils.latToY(latitude);
        
        if(isMin)
            minLat = new Line(0,y,this.getWidth(),y);
        else
            maxLat = new Line(0,y,this.getWidth(),y);
        
        this.repaintBoundLines();
    }
    
    /**
     * Repaint bounds
     */
    private void repaintBoundLines() {
        boundPane.getChildren().clear();
        
        minLong.setStroke(Color.RED);
        maxLong.setStroke(Color.RED);
        minLat.setStroke(Color.RED);
        maxLat.setStroke(Color.RED);
        
        boundPane.getChildren().add(minLong);
        boundPane.getChildren().add(maxLong);
        boundPane.getChildren().add(minLat);
        boundPane.getChildren().add(maxLat);
    }
    
    /**
     * repain all elements into editor area
     */
    public void repaint() {
        super.repaint();
        boundPane.getChildren().clear();
    }
    
    /**
     * Reload editor area by changing bounds and repainting elements
     */
    public void reload() {
        drawingUtils.initializeBounds(roads.getMinLon(), roads.getMaxLon(), roads.getMinLat(), roads.getMaxLat());
        this.repaint();
    }
    
    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType() == MouseEvent.MOUSE_CLICKED)
            mouseClicked(event);
        
        event.consume();
    }
    
    /**
     * Method called when an user clicks into the editor area
     * @param event Mouse event
     */
    public void mouseClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        GraphicNode currentNode = new GraphicNode((float)drawingUtils.xToLong(x), (float)drawingUtils.yToLat(y));
        
        switch(event.getButton()) {
            case PRIMARY:
                this.drawRoad(currentNode);
                break;
            case SECONDARY:
                if(isDrawingRoad)
                    this.finishDrawRoad(currentNode);
                break;
        }
    }
    
    /**
     * Draw a road by adding a new section to the current one or creeating a new road
     * @param node Node to add into a new section
     */
    public void drawRoad(GraphicNode node) {
        if(isDrawingRoad)
            continueDrawRoad(node);
        else
            beginDrawRoad(node);
    }
    
    /**
     * Begin to draw a road
     * @param node First node of the road
     */
    public void beginDrawRoad(GraphicNode node) {
        currentDrawingRoad = new Road();
        sourceNode = node;
        isDrawingRoad = true;
        roads.addRoad(currentDrawingRoad);
    }
    
    /**
     * Continue to draw the current road by adding a node
     * @param node Node to add to the current road
     */
    public void continueDrawRoad(GraphicNode node) {
        targetNode = node;
        GraphicSection section = new GraphicSection(sourceNode, targetNode);
        
        section.addLanes(editorToolsDock.getForwardLaneSizeValue(), Direction.FORWARD, currentDrawingRoad.getLastSection());
        section.addLanes(editorToolsDock.getBackwardLaneSizeValue(), Direction.BACKWARD, currentDrawingRoad.getLastSection());
        section.getSection().setMaxSpeed(editorToolsDock.getMaxSpeedValue());
        
        currentDrawingRoad.getGraphicRoad().addSection(section);
        this.getChildren().add(section);
        this.init(currentDrawingRoad);
        this.repaint(currentRoadPane,currentDrawingRoad);
        
        sourceNode = targetNode;
        targetNode = null;
        nodeDrawingPanel.paintFirst(currentDrawingRoad);
    }
    
    /**
     * Finish to draw a road
     * @param node Last node of the road
     */
    public void finishDrawRoad(GraphicNode node) {
        targetNode = node;
        GraphicSection section = new GraphicSection(sourceNode, targetNode);
        
        section.addLanes(editorToolsDock.getForwardLaneSizeValue(), Direction.FORWARD, currentDrawingRoad.getLastSection());
        section.addLanes(editorToolsDock.getBackwardLaneSizeValue(), Direction.BACKWARD, currentDrawingRoad.getLastSection());
        currentDrawingRoad.getGraphicRoad().addSection(section);
        isDrawingRoad = false;
        
        this.init(currentDrawingRoad);
        this.repaint(currentRoadPane,currentDrawingRoad);
        this.migrateRoad();
        
        nodeDrawingPanel.paintFirstAndLast(currentDrawingRoad);
    }
    
    public void migrateRoad() {
        final ObservableList<Node> nodes = currentRoadPane.getChildren();
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                roadPane.getChildren().addAll(nodes);
                currentRoadPane.getChildren().clear();
            }
        });
    }
    
    /**
     * Add a connection between roads if clicking on first source node or last target node
     * @param graphicNode Node to add and liked to other roads
     */
    public void addConnectionBetweenRoads(GraphicNode graphicNode) {
        this.drawRoad(graphicNode);
        
        if(isDrawingRoad && currentDrawingRoad.size() > 0) {
            isDrawingRoad = false;
            this.migrateRoad();
        }
        
        //will add connection between road
        ArrayList<GraphicSection> sections = graphicNode.getGraphicSections();
        Section current = currentDrawingRoad.getGraphicRoad().getLastSection();
        
        ArrayList<GraphicSection> others = new ArrayList<>(sections);
        others.remove(current.getGraphicSection());
        
        for(GraphicSection s : others){
            if(s.equals(s.getSection().getRoad().getLastSection())){
                if(!s.getBackwardLanes().isEmpty()){
                    current.addSuccessor(new NextSection(s.getSection(), Direction.FORWARD));
                    current.getGraphicSection().addConnections(current.getGraphicSection().getForwardLanes(),
                            s.getBackwardLanes());
                }
                if(!current.getGraphicSection().getBackwardLanes().isEmpty()){
                    s.addSuccessor(new NextSection(current, Direction.FORWARD));
                    s.addConnections(s.getForwardLanes(), current.getGraphicSection().getBackwardLanes());
                }
            }
            else if(s.equals(s.getSection().getRoad().getFirstSection())){
                current.addSuccessor(new NextSection(s.getSection(), Direction.FORWARD));
                current.getGraphicSection().addConnections(current.getGraphicSection().getForwardLanes(),
                        s.getForwardLanes());
                if(!s.getBackwardLanes().isEmpty() &&
                        !current.getGraphicSection().getBackwardLanes().isEmpty()){
                    s.addSuccessor(new NextSection(current, Direction.BACKWARD));
                    s.addConnections(s.getBackwardLanes(), current.getGraphicSection().getBackwardLanes());
                }  
            }
        }
    }
    
    /**
     * Get is wainting size
     * @return true if editor area is waiting for some size, false otherwise
     */
    public boolean isWaitingSize() {
        return isWaitingSize;
    }
    
    /**
     * get is drawing road
     * @return true if a road is already drawing a road
     */
    public boolean isDrawingRoad() {
        return isDrawingRoad;
    }
    
    /**
     * Set is waiting size
     * @param isWaitingSize New is waiting size boolean
     */
    public void setIsWaitingSize(boolean isWaitingSize) {
        this.isWaitingSize = isWaitingSize;
    }
    
    /**
     * Set is editor tools dock
     * @param editorToolsDock Reference to editor tools
     */
    public void setEditorToolsDock(EditorToolsDock editorToolsDock) {
        this.editorToolsDock = editorToolsDock;
    }
}
