/*
* Copyright 2014 Abel Juste Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     thtp://www.apache.org/licenses/LICENSE-2.0
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.insa.controller.MainController;
import org.insa.core.roadnetwork.Road;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicLane;
import org.insa.view.graphicmodel.GraphicPoint;
import org.insa.view.graphicmodel.GraphicRoad;
import org.insa.view.graphicmodel.GraphicSection;

/**
 *
 * @author Thomas Thiebaud
 */
public class RoadDrawingPanel extends Pane {
    
    private final DrawingPanel drawingPanel;
    private final RoadsModel roads = MainController.getInstance().getModel().getRoadModel();
    
    private double forwardPreviousX = 0;
    private double forwardPreviousY = 0;
    private double forwardCurrentX = 0;
    private double forwardCurrentY = 0;
    private double backwardPreviousX = 0;
    private double backwardPreviousY = 0;
    private double backwardCurrentX = 0;
    private double backwardCurrentY = 0;

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
                double deltaX = this.getDeltaX(angle, drawingPanel.getLaneSize() / 2, x1, y1, x2, y2);
                double deltaY = this.getDeltaY(angle, drawingPanel.getLaneSize() / 2, x1, y1, x2, y2);
                
                gSection.setDeltaX(drawingPanel.xToLong(x1 + deltaX) - drawingPanel.xToLong(x1));
                gSection.setDeltaY(drawingPanel.yToLat(y1 + deltaY) - drawingPanel.yToLat(y1));
                    
                gSection.setLongLatPoints(getPoints(gSection));
                 
                for(int i=0; i< gSection.getForwardLanes().size(); i++) {
                    GraphicLane lane = gSection.getForwardLanes().get(i);
                    lane.setSourcePoint(new GraphicPoint(drawingPanel.xToLong(x1) + 2 * i * gSection.getDeltaX(), drawingPanel.yToLat(y1) + 2 * i * gSection.getDeltaY()));
                    lane.setTargetPoint(new GraphicPoint(drawingPanel.xToLong(x2) + 2 * i * gSection.getDeltaX(), drawingPanel.yToLat(y2) + 2 * i * gSection.getDeltaY()));
                }
                
                for(int i=0; i< gSection.getBackwardLanes().size(); i++) {
                    GraphicLane lane = gSection.getBackwardLanes().get(i);
                    lane.setSourcePoint(new GraphicPoint(drawingPanel.xToLong(x1) - (2*i) * gSection.getDeltaX(), drawingPanel.yToLat(y1) - (2*i) * gSection.getDeltaY()));
                    lane.setTargetPoint(new GraphicPoint(drawingPanel.xToLong(x2) - (2*i) * gSection.getDeltaX(), drawingPanel.yToLat(y2) - (2*i) * gSection.getDeltaY()));
                }
            }
        }
    }
    /*
    public void drawRoad(GraphicRoad road) {
       
        for(int i=0; i<road.getSections().size(); i++) {
            GraphicSection currentSection = null;
            GraphicSection nextSection = null;
            
            currentSection = road.getSections().get(i);
            if(i<road.getSections().size()-1)
                nextSection = road.getSections().get(i+1);
            
            int forwardRoadWidth = currentSection.getForwardLanes().size();
            int backwardRoadWidth = currentSection.getForwardLanes().size();
            
            double currentX1 = drawingPanel.longToX(currentSection.getSourceNode().getLongitude()) ;
            double currentX2 = drawingPanel.longToX(currentSection.getTargetNode().getLongitude()) ;
            double currentY1 = drawingPanel.latToY(currentSection.getSourceNode().getLatitude()) ;
            double currentY2 = drawingPanel.latToY(currentSection.getTargetNode().getLatitude()) ;
            
            double currentAngle = drawingPanel.angle(currentX1, currentY1, currentX2, currentY2);
            double currentDeltaX =  this.getDeltaX(currentAngle, drawingPanel.getLaneSize() / 2, currentX1, currentY1, currentX2, currentY2);
            double currentDeltaY =  this.getDeltaY(currentAngle, drawingPanel.getLaneSize() / 2, currentX1, currentY1, currentX2, currentY2);
            
            
            if(road.getSections().size() == 1) {
                currentSection.setDeltaX(drawingPanel.xToLong(currentX1 + currentDeltaX) - drawingPanel.xToLong(currentX1));
                currentSection.setDeltaY(drawingPanel.yToLat(currentY1 + currentDeltaY) - drawingPanel.yToLat(currentY1));
                
                forwardPreviousX = 0;
                forwardPreviousY = 0;
                forwardCurrentX = 0;
                forwardCurrentY = 0;
                return;
            }
            
            if(nextSection != null) {
                double nextX1 = drawingPanel.longToX(nextSection.getSourceNode().getLongitude()) ;
                double nextX2 = drawingPanel.longToX(nextSection.getTargetNode().getLongitude()) ;
                double nextY1 = drawingPanel.latToY(nextSection.getSourceNode().getLatitude()) ;
                double nextY2 = drawingPanel.latToY(nextSection.getTargetNode().getLatitude()) ;
                
                double nextAngle = drawingPanel.angle(nextX1, nextY1, nextX2, nextY2);
                double nextDeltaX =  this.getDeltaX(nextAngle, drawingPanel.getLaneSize() / 2, nextX1, nextY1, nextX2, nextY2);
                double nextDeltaY =  this.getDeltaY(nextAngle, drawingPanel.getLaneSize() / 2, nextX1, nextY1, nextX2, nextY2);
                
                if(forwardPreviousX == 0 && forwardPreviousY == 0) {
                    forwardPreviousX = currentX1 + currentDeltaX;
                    forwardPreviousY = currentY1 + currentDeltaY;
                }
                
                GraphicPoint p = drawingPanel.intersection(currentX1 + currentDeltaX, currentY1 + currentDeltaY, currentX2 + currentDeltaX, currentY2 + currentDeltaY, nextX1 + nextDeltaX, nextY1 + nextDeltaY, nextX2 + nextDeltaX, nextY2 + nextDeltaY);
                if(p != null) {
                    forwardCurrentX = p.getX();
                    forwardCurrentY = p.getY();
                } else {
                    forwardCurrentX = currentX1 + currentDeltaX;
                    forwardCurrentY = currentY1 + currentDeltaY;
                }
            } else {
                forwardCurrentX = currentX2 + currentDeltaX;
                forwardCurrentY = currentY2 + currentDeltaY;
            }
            
            this.drawPolygon(currentX1, currentY1, currentX2, currentY2, (int)forwardCurrentX, (int)forwardCurrentY, (int)forwardPreviousX, (int)forwardPreviousY);
            
            forwardPreviousX = forwardCurrentX;
            forwardPreviousY = forwardCurrentY;
        }
        
        forwardPreviousX = 0;
        forwardPreviousY = 0;
        forwardCurrentX = 0;
        forwardCurrentY = 0;
    }
    */
    /**
     * Get graphic lane points
     * @param section
     * @return 
     */
    public ArrayList<Double> getPoints(GraphicSection section) {
        ArrayList<Double> points = new ArrayList<>();
        int forwardSize = section.getForwardLanes().size();
        int backwardSize = section.getBackwardLanes().size();
                
        points.add(0, section.getSourceNode().getLongitude() + 2 * section.getDeltaX() * forwardSize) ;
        points.add(1, section.getSourceNode().getLatitude() + 2 * section.getDeltaY() * forwardSize) ;
        points.add(2, section.getTargetNode().getLongitude() + 2 * section.getDeltaX() * forwardSize) ;
        points.add(3, section.getTargetNode().getLatitude() + 2 * section.getDeltaY() * forwardSize) ;
        points.add(4, section.getTargetNode().getLongitude() - 2 * section.getDeltaX() * backwardSize) ;
        points.add(5, section.getTargetNode().getLatitude() - 2 * section.getDeltaY() * backwardSize) ;
        points.add(6, section.getSourceNode().getLongitude() - 2 * section.getDeltaX() * backwardSize) ;
        points.add(7, section.getSourceNode().getLatitude() - 2 * section.getDeltaY() * backwardSize) ;

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
     * Transform the graphic section by convertising each point from latitude/longitude to X/Y coordinates
     * @param section Section to transform
     */
    public void transform(GraphicSection section) {
        double res ;
        section.getPoints().clear();
        for(int j=0; j<section.getLongLatPoints().size(); j++) {
            if(j%2 == 0)
                res = drawingPanel.longToX(section.getLongLatPoints().get(j));
            else
                res = drawingPanel.latToY(section.getLongLatPoints().get(j));
            section.getPoints().add(res);
        }
    }
    
    /**
     * Paint roads into panel
     */
    public final void paint() {
        for(Road r : roads.getRoads()) {
            for(GraphicSection gSection : r.getGraphicRoad().getSections()) { 
                transform(gSection);
                this.getChildren().add(gSection);
                for(GraphicLane lane : gSection.getForwardLanes()) {
                    Line line = new Line(drawingPanel.longToX(lane.getSourcePoint().getX()), drawingPanel.latToY(lane.getSourcePoint().getY()), drawingPanel.longToX(lane.getTargetPoint().getX()), drawingPanel.latToY(lane.getTargetPoint().getY()));
                    line.setStroke(Color.WHITE);
                    this.getChildren().add(line);
                }
                for(GraphicLane lane : gSection.getBackwardLanes() ) {
                    Line line = new Line(drawingPanel.longToX(lane.getSourcePoint().getX()), drawingPanel.latToY(lane.getSourcePoint().getY()), drawingPanel.longToX(lane.getTargetPoint().getX()), drawingPanel.latToY(lane.getTargetPoint().getY()));
                    line.setStroke(Color.WHITE);
                    this.getChildren().add(line);
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
