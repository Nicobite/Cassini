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

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import org.insa.controller.MainController;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.model.items.RoadsModel;

/**
 *
 * @author Thomas Thiebaud
 */
public class RoadDrawingPanel extends DrawingPanel {

    private RoadsModel roads = MainController.getInstance().getModel().getRoadModel();
    
    /**
     * Constructor
     * @param width Panel width
     * @param height Panel height
     */
    public RoadDrawingPanel(int width, int height) {
        super(width, height);     
        
        initialMinLong = roads.getMinLon();
        initialMaxLong = roads.getMaxLon();
        initialMinLat = roads.getMinLat();
        initialMaxLat = roads.getMaxLat();
        
        minLong = initialMinLong;
        maxLong = initialMaxLong;
        minLat = initialMinLat;
        maxLat = initialMaxLat;
        
        this.paint();
    }
    
    /**
     * Draw a road, including all sections and lanes that composed the road
     * @param road Road to draw
     */
    public void drawRoad(Road road) {
        graphic.setLineCap(StrokeLineCap.BUTT);
        
        int size = road.getSections().size();
        
        if(size > 1) {
            for(int i=0; i<size; i++) {
                Section section = road.getSections().get(i);
                double lineWidth = section.getForwardLanes().size() * scale;
                
                int x1 = longToX(section.getSourceNode().getLongitude()) ;
                int x2 = longToX(section.getTargetNode().getLongitude()) ;
                int y1 = latToY(section.getSourceNode().getLatitude()) ;
                int y2 = latToY(section.getTargetNode().getLatitude()) ;
                
                graphic.setStroke(Color.GRAY);
                graphic.setLineWidth(lineWidth);
                graphic.strokeLine(x1, y1, x2, y2) ;
                graphic.fillOval(x1 - (lineWidth / 2), y1 - (lineWidth / 2), lineWidth, lineWidth);
                graphic.fillOval(x2 - (lineWidth / 2), y2 - (lineWidth / 2), lineWidth, lineWidth);
                
                graphic.setStroke(Color.ORANGE);
                graphic.setFill(Color.GRAY);
                graphic.setLineWidth(1);
                graphic.strokeOval(x1 - (lineWidth / 2), y1 - (lineWidth / 2), lineWidth, lineWidth);
                graphic.strokeOval(x2 - (lineWidth / 2), y2 - (lineWidth / 2), lineWidth, lineWidth);
            }
        }    
    }

    @Override
    public void paint() {
        for(Road r : roads.getRoads()) {
            drawRoad(r);
        }
    }

    @Override
    public void repaint() {
        graphic.clearRect(0, 0, this.getWidth(), this.getHeight());
        this.paint();
    }
}
