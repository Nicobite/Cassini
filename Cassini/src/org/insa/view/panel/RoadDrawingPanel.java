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
        graphic.setFill(Color.GRAY);
           
        for(Section section : road.getSections()) {
            int forwardLineWidth = section.getForwardLanes().size() * scale;
            int backwardLineWidth = section.getForwardLanes().size() * scale;
            
            int x1 = longToX(section.getSourceNode().getLongitude()) ;
            int x2 = longToX(section.getTargetNode().getLongitude()) ;
            int y1 = latToY(section.getSourceNode().getLatitude()) ;
            int y2 = latToY(section.getTargetNode().getLatitude()) ;
            
            double angle = angle(x1, y1, x2, y2);
            double deltaX =  this.getDeltaX(angle, forwardLineWidth, x1, y1, x2, y2);
            double deltaY =  this.getDeltaY(angle, forwardLineWidth, x1, y1, x2, y2);
            
            this.drawPolygon(x1, y1, x2, y2, deltaX, deltaY);

            if(!section.getBackwardLanes().isEmpty()) {
                deltaX = this.getDeltaX(angle, backwardLineWidth, x1, y1, x2, y2);
                deltaY = this.getDeltaY(angle, backwardLineWidth, x1, y1, x2, y2);
                this.drawPolygon(x1, y1, x2, y2, - deltaX, - deltaY);
            }
            
            graphic.setLineWidth(1);
            graphic.setStroke(Color.ORANGE);
            this.drawOval(x1, y1, forwardLineWidth, forwardLineWidth);
            this.drawOval(x2, y2, forwardLineWidth, forwardLineWidth);
        }
    }
    
    /**
     * Draw an oval center on the (x,y) point with the given height and width
     * @param x X coordinate of the center
     * @param y Y coordinate of the center
     * @param width Oval width
     * @param height Oval height
     */
    public void drawOval(int x, int y, int width, int height) {
        graphic.strokeOval(x - (width / 2), y - (height / 2), width, height);
    }
    
    /**
     * Draw a polygon 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param deltaX
     * @param deltaY 
     */
    public void drawPolygon(int x1, int y1, int x2, int y2, double deltaX, double deltaY) {
        double x[] = new double[4];
        double y[] = new double[4];
        
        x[0] = x1 ;
        x[1] = x2 ;
        x[2] = x2 + deltaX;
        x[3] = x1 + deltaX;
        
        y[0] = y1 ;
        y[1] = y2 ;
        y[2] = y2 + deltaY;
        y[3] = y1 + deltaY;
        
        graphic.fillPolygon(x, y, 4);
    }
    
    /**
     * Get deltaX depending the (x1,y1) and (x2,y2) points
     * @param angle
     * @param width
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return DeltaX
     */
    public double getDeltaX(double angle, int width, int x1, int y1 ,int x2, int y2) {
        double deltaX =  Math.abs(Math.sin(angle) * width);
        
        if(x1 < x2 && y1 < y2) {
            deltaX *= -1;
        }
        else if(x1 > x2 && y1 < y2) {
            deltaX *= -1;
        }
        else if (x1 == x2) {
            if(y1 > y2)
                deltaX = width;
            else
                deltaX = -width;
        }
        else if(y1 == y2) {
            deltaX = 0;
        }
        return deltaX;
    }
    
    /**
     * Get deltaY depending the (x1,y1) and (x2,y2) points
     * @param angle
     * @param width
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return DeltaY
     */
    public double getDeltaY(double angle, int width, int x1, int y1 ,int x2, int y2) {
        double deltaY =  Math.abs(Math.cos(angle) * width);
        
        if(x1 > x2 && y1 > y2) {
            deltaY *= -1;
        }
        else if(x1 > x2 && y1 < y2) {
            deltaY *= -1;
        }
        else if (x1 == x2) {
            deltaY = 0;
        }
        else if(y1 == y2) {
            if(x1 < x2)
                deltaY = width;
            else
                deltaY = -width;
        }
        return deltaY;
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
