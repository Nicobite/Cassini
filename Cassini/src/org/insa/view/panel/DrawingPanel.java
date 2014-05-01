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

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.insa.controller.MainController;
import org.insa.model.items.RoadsModel;
import org.insa.model.items.VehiclesModel;

/**
 *
 * @author Thomas Thiebaud
 */
public class DrawingPanel extends StackPane implements EventHandler<MouseEvent> {
    protected RoadsModel roads = MainController.getInstance().getModel().getRoadModel();
    protected VehiclesModel vehicles = MainController.getInstance().getModel().getVehiclesModel();
    
    protected RoadDrawingPanel roadDrawingPanel;
    protected VehicleDrawingPanel vehicleDrawingPanel;
    
    protected float initialMinLong = 0;
    protected float initialMaxLong = 0;
    protected float initialMinLat = 0;
    protected float initialMaxLat = 0;
    
    protected float minLong = 0;
    protected float maxLong = 0;
    protected float minLat = 0;
    protected float maxLat = 0;   
    
    private final double rayon_terre = 6378137.0 ;
    double laneSize; 
    
    int height;
    int width;
        
    /**
     * Constructor
     * @param width Panel width
     * @param height Panel height
     */
    public DrawingPanel(int width, int height) {
        this.height = height;
        this.width = width;        
        
        initialMinLong = roads.getMinLon();
        initialMaxLong = roads.getMaxLon();
        initialMinLat = roads.getMinLat();
        initialMaxLat = roads.getMaxLat();
        
        minLong = initialMinLong;
        maxLong = initialMaxLong;
        minLat = initialMinLat;
        maxLat = initialMaxLat;
        
        laneSize = height / distance(initialMaxLong, initialMinLat, initialMaxLong, initialMaxLat);
        
        roadDrawingPanel = new RoadDrawingPanel(this);
        vehicleDrawingPanel = new VehicleDrawingPanel(this);
        
        roadDrawingPanel.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                 roadDrawingPanel.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
            }

        });
        
        roadDrawingPanel.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                 roadDrawingPanel.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
            }

        });
        
        this.getChildren().add(roadDrawingPanel);
        this.getChildren().add(vehicleDrawingPanel);
        
        this.setOnMouseClicked(this);
    }
    
    /** 
     *  Get the distance between two points
     *  @param long1 longitude of the first point
     *  @param lat1 latitude of the first point
     *  @param long2 longitude of the second point
     *  @param lat2 latitude of the second point
     *  @return Distance between the two points in meters
     */
    public double distance(double long1, double lat1, double long2, double lat2) {
    	return rayon_terre*Math.acos(Math.sin(Math.toRadians(lat1))*Math.sin(Math.toRadians(lat2))+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.cos(Math.toRadians(long2-long1)));
    }
    
    @Override
    public void handle(MouseEvent t) {
        
        double x = t.getX();
        double y = t.getY();
        
        double deltaLong = maxLong - minLong;
        double deltaLat = maxLat - minLat;
        
        double newLong = xToLong(x);
        double newLat = yToLat(y);
        
        if(t.getButton() == MouseButton.PRIMARY) {
            minLong = (float) (newLong - deltaLong /4);
            maxLong = (float) (newLong + deltaLong / 4);
            minLat = (float) (newLat - deltaLat /4);
            maxLat = (float) (newLat + deltaLat / 4);
        }
        else if(t.getButton() == MouseButton.SECONDARY) {
            minLong = (float) (newLong - deltaLong);
            maxLong = (float) (newLong + deltaLong);
            minLat = (float) (newLat - deltaLat);
            maxLat = (float) (newLat + deltaLat);
            if(minLong < initialMinLong || maxLong > initialMaxLong || minLat < initialMinLat || maxLat > initialMaxLat) {
                minLong = initialMinLong;
                maxLong = initialMaxLong;
                minLat = initialMinLat;
                maxLat = initialMaxLat;
            }
        }
        
        this.repaint();
    }
    
    /**
     * Convert a longitude into a X coordinate
     * @param lon longitude to convert
     * @return X coordinate
     */
    public double longToX(double lon) {
        return ( width * (lon - this.minLong) / (this.maxLong - this.minLong));
    }
    
    /**
     * Convert a latitude into a Y coordinate
     * @param lat latitude to convert
     * @return Y coordinate
     */
    public double latToY(double lat) {
        return (height * (1 - (lat - this.minLat) / (this.maxLat - this.minLat)));
    }
    
    /**
     * Convert a X coordiante to a longitude
     * @param x X to convert
     * @return longitude
     */
    public double xToLong(double x) {
        return x * (maxLong - minLong) / width + minLong;
    }
    
    /**
     * Convert a Y coordinate to a latitude
     * @param y Y coordinate
     * @return latitude
     */
    public double yToLat(double y) {
        return (height - y) * (maxLat - minLat) / height + minLat;
    }
    
    /**
     * Get absolute angle between two points
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return Absolute angle
     */
    public double angle(double x1, double y1, double x2, double y2) {
        return Math.atan2(y2 - y1, x2 - x1);
    }
    
    /**
     * Draw an oval center on the (x,y) point with the given height and width
     * @param x X coordinate of the center
     * @param y Y coordinate of the center
     * @param r Rayon
     * @return Circle 
     */
    public Circle drawCircle(double x, double y, double r) {
        Circle circle = new Circle(x - (r / 2), y - (r / 2), r);
        return circle;
    }
    
    /**
     * Paint all components into panel
     */
    public void paint() {
        roadDrawingPanel.paint();
        vehicleDrawingPanel.paint();
    }
    
    /**
     * Repaint the panel by clearing it and calling the paint method
     */
    public void repaint() {
        this.repaintRoads();
        this.repaintVehicles();
    }
    
    /**
     * Fet lane size
     * @return Lane size
     */
    public double getLaneSize() {
        return laneSize;
    }
    
    /**
     * Repaint all vehicles
     */
    public void repaintVehicles() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vehicleDrawingPanel.repaint();
            }
        });
    }

    /**
     * Repaint all roads
     */
    public void repaintRoads() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                roadDrawingPanel.repaint();
            }
        });
    }
}

