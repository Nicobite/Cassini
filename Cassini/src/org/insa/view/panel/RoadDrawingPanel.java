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
import javafx.scene.layout.Pane;
import org.insa.controller.MainController;
import org.insa.core.roadnetwork.Road;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicLane;
import org.insa.view.graphicmodel.GraphicSection;

/**
 *
 * @author Thomas Thiebaud
 */
public class RoadDrawingPanel extends Pane {
    
    private DrawingPanel drawingPanel;
    private RoadsModel roads = MainController.getInstance().getModel().getRoadModel();

    /**
     * Constructor
     * @param panel Reference to drawing panel
     */
    public RoadDrawingPanel(DrawingPanel panel) {
        this.drawingPanel = panel;
        this.init();
        this.paint();
    }
    
    /**
     * Initialize by calculting all the graphic lanes
     */
    private void init() {
        for(Road r : roads.getRoads()) {
            for(GraphicSection gSection : r.getGraphicRoad().getSections()) {   
                double x1 = drawingPanel.longToX(gSection.getSourceNode().getLongitude()) ;
                double x2 = drawingPanel.longToX(gSection.getTargetNode().getLongitude()) ;
                double y1 = drawingPanel.latToY(gSection.getSourceNode().getLatitude()) ;
                double y2 = drawingPanel.latToY(gSection.getTargetNode().getLatitude()) ;

                double angle = drawingPanel.angle(x1, y1, x2, y2);
                double deltaX = this.getDeltaX(angle, drawingPanel.getLaneSize(), x1, y1, x2, y2);
                double deltaY = this.getDeltaY(angle, drawingPanel.getLaneSize(), x1, y1, x2, y2);
                
                for(int i=0; i< gSection.getForwardLanes().size(); i++) {
                    GraphicLane lane = gSection.getForwardLanes().get(i);
                    lane.setLongLatPoints((getPoints(x1 + i*deltaX, y1 + i*deltaY, x2 + i*deltaX, y2 + i*deltaY, deltaX, deltaY)));
                }
                   
                for(int i=0; i< gSection.getBackwardLanes().size(); i++) {
                    GraphicLane lane = gSection.getBackwardLanes().get(i);
                    lane.setLongLatPoints(getPoints(x1 - i*deltaX, y1 - i*deltaY, x2 - i*deltaX, y2 - i*deltaY, -deltaX, -deltaY));
                }
            }
        }
    }
    
    /**
     * Get graphic lane points
     * @param x1 x coordinate of first known point
     * @param y1 y coordiante of first known point
     * @param x2 x coordinate of second known point 
     * @param y2 x coordinate of second known point
     * @param deltaX delta x
     * @param deltaY delta y
     * @return 
     */
    public ArrayList<Double> getPoints(double x1, double y1, double x2, double y2, double deltaX, double deltaY) {
        ArrayList<Double> points = new ArrayList<>();
        
        points.add(0, drawingPanel.xToLong(x1)) ;
        points.add(1, drawingPanel.yToLat(y1)) ;
        points.add(2, drawingPanel.xToLong(x2)) ;
        points.add(3, drawingPanel.yToLat(y2)) ;
        points.add(4, drawingPanel.xToLong(x2 + deltaX)) ;
        points.add(5, drawingPanel.yToLat(y2 + deltaY)) ;
        points.add(6, drawingPanel.xToLong(x1 + deltaX)) ;
        points.add(7, drawingPanel.yToLat(y1 + deltaY)) ;
        
        return points;
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
    public double getDeltaX(double angle, double width, double x1, double y1 ,double x2, double y2) {
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
    public double getDeltaY(double angle, double width, double x1, double y1 ,double x2, double y2) {
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
    
    /**
     * Transform the graphic lane by convertising each point into the latitude and longitude point list to X/Y coordinates
     * @param lane Lane to transform
     */
    public void transform(GraphicLane lane) {
        double res ;
        for(int j=0; j<lane.getLongLatPoints().size(); j++) {
            if(j%2 == 0)
                res = drawingPanel.longToX(lane.getLongLatPoints().get(j));
            else
                res = drawingPanel.latToY(lane.getLongLatPoints().get(j));
            
            lane.getPoints().add(res);
        }
    }
    
    /**
     * Paint roads into panel
     */
    public void paint() {
        for(Road r : roads.getRoads()) {
            for(GraphicSection gSection : r.getGraphicRoad().getSections()) { 
                for(int i=0; i< gSection.getForwardLanes().size(); i++) {
                    GraphicLane lane = gSection.getForwardLanes().get(i);
                    lane.getPoints().clear();
                    transform(lane);
                    this.getChildren().add(lane);
                }
                for(int i=0; i< gSection.getBackwardLanes().size(); i++) {
                    GraphicLane lane = gSection.getBackwardLanes().get(i);
                    lane.getPoints().clear();
                    transform(lane);
                    this.getChildren().add(lane);
                }
            }
        }
    }
    
    /**
     * Repaint all road by clearing the old ones and painting new ones
     */
    public void repaint() {
        this.getChildren().clear();
        this.paint();
    }
}
