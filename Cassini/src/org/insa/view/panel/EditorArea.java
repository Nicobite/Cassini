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

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.insa.view.utils.DrawingUtils;

/**
 *
 * @author Thiebaud Thomas
 */
public class EditorArea extends RoadDrawingPanel {
    private final Pane boundPane = new Pane();
    
    private Line minLong = new Line();
    private Line maxLong = new Line();
    private Line minLat = new Line();
    private Line maxLat = new Line();
    
    /**
     * Constructor
     * @param drawingUtils Reference to drawing utils 
     */
    public EditorArea(DrawingUtils drawingUtils) {
        super(drawingUtils);
        this.init();
        this.paint();
        this.getChildren().add(boundPane);
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
        this.getChildren().add(boundPane);
    }
    
    /**
     * Reload editor area by changing bounds and repainting elements
     */
    public void reload() {
        drawingUtils.initializeBounds(roads.getMinLon(), roads.getMaxLon(), roads.getMinLat(), roads.getMaxLat());
        this.repaint();
    }
}
