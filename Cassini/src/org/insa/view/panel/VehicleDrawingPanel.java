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
import org.insa.core.driving.Vehicle;
import org.insa.core.enums.Direction;
import org.insa.model.items.RoadsModel;
import org.insa.model.items.VehiclesModel;
import org.insa.view.utils.DrawingUtils;
import org.insa.view.graphicmodel.GraphicLane;
import org.insa.view.graphicmodel.GraphicSection;

/**
 *
 * @author Thiebaud Thomas
 */
public class VehicleDrawingPanel extends Pane {

    protected DrawingUtils drawingUtils;
    private RoadsModel roads = MainController.getInstance().getModel().getRoadModel();
    private VehiclesModel vehicles;

    /**
     * Constructor
     * @param drawingUtils Reference to drawing utils 
     */
    public VehicleDrawingPanel(DrawingUtils drawingUtils) {
        this.drawingUtils = drawingUtils;
        this.paint();
    }
    
    /**
     * Paint vehicles into panel
     */
    public void paint() {
        vehicles = MainController.getInstance().getModel().getDrivingVehiclesModel();
        for(Vehicle v : vehicles.getVehicles()) {
            if(v.getDriving().getPosition() != null) {
                double point[] = this.getPoint(v.getDriving().getPosition().getLane().getGraphicLane(), v.getDriving().getPosition().getOffset());
                this.getChildren().add(drawingUtils.drawCircle(point[0], point[1], point[2]));
            }
        }
    }
    
    /**
     * Get vehicle point in order to draw it
     * @param lane Lane that contains the vehicle
     * @param offset position of the vehicle into the lane
     * @return 
     */
    public double[] getPoint(GraphicLane lane, double offset) {
        double point[] = new double[3];
        
        GraphicSection section = lane.getSection();
        
        double widthX = drawingUtils.longToX(lane.getSourcePoint().getX() + section.getSourceDeltaX()) - drawingUtils.longToX(lane.getSourcePoint().getX());
        double widthY = drawingUtils.latToY(lane.getSourcePoint().getY() + section.getSourceDeltaY()) - drawingUtils.latToY(lane.getSourcePoint().getY());
        double width = Math.sqrt(Math.pow(widthX, 2) + Math.pow(widthY, 2));
                
        double ratio = offset / lane.getSection().getLength(); 

        double deltaX;
        double deltaY;
        
        if(lane.getDirection() == Direction.FORWARD) {
            deltaX = drawingUtils.longToX(lane.getTargetPoint().getX()) - drawingUtils.longToX(lane.getSourcePoint().getX());
            deltaY = drawingUtils.latToY(lane.getTargetPoint().getY()) - drawingUtils.latToY(lane.getSourcePoint().getY());
            point[0] = drawingUtils.longToX(lane.getSourcePoint().getX() + section.getSourceDeltaX()) + ratio * deltaX;
            point[1] = drawingUtils.latToY(lane.getSourcePoint().getY() + section.getSourceDeltaY()) + ratio * deltaY;
        } else if (lane.getDirection() == Direction.BACKWARD) {
            deltaX = drawingUtils.longToX(lane.getSourcePoint().getX()) - drawingUtils.longToX(lane.getTargetPoint().getX());
            deltaY = drawingUtils.latToY(lane.getSourcePoint().getY()) - drawingUtils.latToY(lane.getTargetPoint().getY());
            point[0] = drawingUtils.longToX(lane.getTargetPoint().getX() + section.getTargetDeltaX()) + ratio * deltaX;
            point[1] = drawingUtils.latToY(lane.getTargetPoint().getY() + section.getTargetDeltaY()) + ratio * deltaY;
        }
        point[2] = width;
                
        return point;
    }
    
    /**
     * Repaint all vehicle by clearing the old ones and painting new ones
     */
    public void repaint() {
        this.getChildren().clear();
        this.paint();
    }
}
