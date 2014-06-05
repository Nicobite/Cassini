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
package org.insa.view.panel;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.insa.controller.MainController;
import org.insa.core.driving.Vehicle;
import org.insa.core.enums.Direction;
import org.insa.core.trafficcontrol.Collision;
import org.insa.core.trafficcontrol.Incident;
import org.insa.model.items.RoadsModel;
import org.insa.model.items.VehiclesModel;
import org.insa.view.graphicmodel.GraphicCollision;
import org.insa.view.graphicmodel.GraphicIncident;
import org.insa.view.utils.DrawingUtils;
import org.insa.view.graphicmodel.GraphicLane;
import org.insa.view.graphicmodel.GraphicSection;
import org.insa.view.graphicmodel.GraphicTarget;

/**
 *
 * @author Thiebaud Thomas
 */
public class VehicleDrawingPanel extends StackPane {

    private DrawingUtils drawingUtils;
    private RoadsModel roads = MainController.getInstance().getModel().getRoadModel();
    private VehiclesModel vehicles;
    
    private Pane vehiclePane = new Pane();
    private Pane incidentPane = new Pane();
    private Pane collisionPane = new Pane();
    private double width = 0;
    private GraphicLane initialLane = null;

    /**
     * Constructor
     * @param drawingUtils Reference to drawing utils 
     */
    public VehicleDrawingPanel(DrawingUtils drawingUtils) {
        this.drawingUtils = drawingUtils;
        this.paint();
        this.getChildren().add(vehiclePane);
        this.getChildren().add(incidentPane);
        this.getChildren().add(collisionPane);
    }
    
    /**
     * Paint vehicles into panel
     */
    public void paint() {
        vehicles = MainController.getInstance().getModel().getDrivingVehiclesModel();
        for(Vehicle v : vehicles.getVehicles()) {
            if(v.getDriving().getPosition() != null) {
                double point[] = this.getPoint(v.getDriving().getPosition().getLane().getGraphicLane(), v.getDriving().getPosition().getOffset());
                vehiclePane.getChildren().add(drawingUtils.drawCircle(point[0], point[1], width));
            }
        }
    }
    
    public void calculateWidth(GraphicLane lane) {
        if(initialLane == null)
            initialLane = lane;
        
        GraphicSection section = initialLane.getSection();

        double widthX = drawingUtils.longToX(initialLane.getSourcePoint().getX() + section.getSourceDeltaX()) - drawingUtils.longToX(initialLane.getSourcePoint().getX());
        double widthY = drawingUtils.latToY(initialLane.getSourcePoint().getY() + section.getSourceDeltaY()) - drawingUtils.latToY(initialLane.getSourcePoint().getY());

        width = Math.sqrt(Math.pow(widthX, 2) + Math.pow(widthY, 2));
    }
    
    /**
     * Get vehicle point in order to draw it
     * @param lane Lane that contains the vehicle
     * @param offset position of the vehicle into the lane
     * @return Points
     */
    public double[] getPoint(GraphicLane lane, double offset) {
        double point[] = new double[2];
        
        GraphicSection section = lane.getSection();
        
        calculateWidth(lane);
        
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
            point[0] = drawingUtils.longToX(lane.getTargetPoint().getX() - section.getTargetDeltaX()) + ratio * deltaX;
            point[1] = drawingUtils.latToY(lane.getTargetPoint().getY() - section.getTargetDeltaY()) + ratio * deltaY;
        }
                
        return point;
    }
    
    /**
     * Display an incident into the incident pane
     * @param incident Incident to display
     */
    public void displayIncident(Incident incident) {
        double point[] = this.getPoint(incident.getPosition().getLane().getGraphicLane(), incident.getPosition().getOffset());

        GraphicTarget target = new GraphicTarget((int)point[0], (int)point[1]);
        incident.getGraphicIncident().setGraphicTarget(target);
        
        incidentPane.getChildren().add(target);
    }
    
    public void displayCollision(Collision collision) {
        double point[] = this.getPoint(collision.getPosition().getLane().getGraphicLane(), collision.getPosition().getOffset());

        GraphicTarget target;
        switch(collision.getSeverity()) {
            case CRITICAL :
                target = new GraphicTarget((int)point[0], (int)point[1],"target_critical");
                break;
            case HIGH :
                target = new GraphicTarget((int)point[0], (int)point[1],"target_hight");
                break;
            case MEDIUM :
                target = new GraphicTarget((int)point[0], (int)point[1],"target_medium");
                break;
            default :
                target = new GraphicTarget((int)point[0], (int)point[1]);
                break;
        }
        
        collision.getGraphicCollision().setGraphicTarget(target);
        
        collisionPane.getChildren().add(target);
    }
    
    /**
     * Hide an incident from the incident pane
     * @param gIncident Incident to hide
     */
    public void hideIncident(GraphicIncident gIncident) {
        incidentPane.getChildren().remove(gIncident.getGraphicTarget());
    }
    
    /**
     * Hide a collision from the collision pane
     * @param gCollision Collision to hide
     */
    public void hideCollision(GraphicCollision gCollision) {
        collisionPane.getChildren().remove(gCollision.getGraphicTarget());
    }
    
    /**
     * Repaint all vehicle by clearing the old ones and painting new ones
     */
    public void repaint() {
        vehiclePane.getChildren().clear();
        this.paint();
    } 
}
