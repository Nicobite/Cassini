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
package org.insa.view.utils;

import org.insa.view.graphicmodel.GraphicBounds;
import javafx.scene.shape.Circle;
import org.insa.view.graphicmodel.GraphicPoint;

/**
 *
 * @author Thiebaud Thomas
 */
public class DrawingUtils {
    
    protected int height;
    protected int width;
    
    protected GraphicBounds initialBounds;
    protected GraphicBounds currentBounds;
    
    protected final double rayon_terre = 6378137.0 ;
    
    protected double laneSize;
    
    /**
     * Constructor
     * @param height Height
     * @param width Width
     */
    public DrawingUtils(int height, int width) {
        this.height = height;
        this.width = width;
    } 
    
    /**
     * Initialize bounds
     * @param minLong Map minimum longitude
     * @param maxLong Map maximum longitude
     * @param minLat Map minimum latitude
     * @param maxLat Map maximum latitude
     */
    public void initializeBounds(float minLong, float maxLong, float minLat, float maxLat) {
        initialBounds = new GraphicBounds(minLong, maxLong, minLat, maxLat);
        currentBounds = new GraphicBounds(initialBounds);
        laneSize = 2 * height / distance(minLong,minLat,maxLong,maxLat);
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
    	return rayon_terre * Math.acos(Math.sin(Math.toRadians(lat1))*Math.sin(Math.toRadians(lat2))+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.cos(Math.toRadians(long2-long1)));
    }
    
    /**
     * Get the intersecion point of two lines
     * @param x1 first line x cordinate of source point
     * @param y1 first line y cordinate of source point 
     * @param x2 first line x cordinate of target point
     * @param y2 first line y cordinate of target point
     * @param x3 second line x cordinate of source point
     * @param y3 second line y cordinate of source point
     * @param x4 second line x cordinate of target point
     * @param y4 second line y cordinate of target point
     * @return Intersection point of two lines
     */
    public GraphicPoint intersection(double x1,double y1,double x2,double y2,double x3, double y3, double x4,double y4) {
        double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
        if (d == 0)
            return null;
        
        double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
        double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
        
        GraphicPoint p = new GraphicPoint(xi,yi);
        
        return p;
    }
    
    /**
     * Convert a longitude into a X coordinate
     * @param lon longitude to convert
     * @return X coordinate
     */
    public double longToX(double lon) {
        return ( width * (lon - currentBounds.getMinLong()) / (currentBounds.getMaxLong() - currentBounds.getMinLong()));
    }
    
    /**
     * Convert a latitude into a Y coordinate
     * @param lat latitude to convert
     * @return Y coordinate
     */
    public double latToY(double lat) {
        return (height * (1 - (lat - currentBounds.getMinLat()) / (currentBounds.getMaxLat() - currentBounds.getMinLat())));
    }
    
    /**
     * Convert a X coordiante to a longitude
     * @param x X to convert
     * @return longitude
     */
    public double xToLong(double x) {
        return x * (currentBounds.getMaxLong() - currentBounds.getMinLong()) / width + currentBounds.getMinLong();
    }
    
    /**
     * Convert a Y coordinate to a latitude
     * @param y Y coordinate
     * @return latitude
     */
    public double yToLat(double y) {
        return (height - y) * (currentBounds.getMaxLat() - currentBounds.getMinLat()) / height + currentBounds.getMinLat();
    }
    
    /**
     * Get absolute angle between two points
     * @param x1 X coordinate of first point
     * @param y1 Y coordinate of first point
     * @param x2 X coordinate of second point
     * @param y2 Y coordinate of second point
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
        Circle circle = new Circle(x , y , 0.9 * r);
        return circle;
    }
    
    /**
     * Zoom into the panel after clicking to the (x,y) point
     * @param x X coordinate of click
     * @param y Y coordinate of click
     */
    public void zoom(double x, double y) {
        double deltaLong = currentBounds.getMaxLong() - currentBounds.getMinLong();
        double deltaLat = currentBounds.getMaxLat() - currentBounds.getMinLat();
        
        double newLong = xToLong(x);
        double newLat = yToLat(y);
        
        currentBounds.setMinLong((float) (newLong - deltaLong /4));
        currentBounds.setMaxLong((float) (newLong + deltaLong / 4));
        currentBounds.setMinLat((float) (newLat - deltaLat /4));
        currentBounds.setMaxLat((float) (newLat + deltaLat / 4));
    }
    
    /**
     * Dezoom into the panel after clicking to the (x,y) point
     * @param x X coordinate of click
     * @param y Y coordinate of click
     */
    public void dezoom(double x, double y) {
        double deltaLong = currentBounds.getMaxLong() - currentBounds.getMinLong();
        double deltaLat = currentBounds.getMaxLat() - currentBounds.getMinLat();
        
        double newLong = xToLong(x);
        double newLat = yToLat(y);
        
        boolean minLong = currentBounds.getMinLong() <= initialBounds.getMinLong();
        boolean maxLong = currentBounds.getMaxLong() <= initialBounds.getMaxLong();
        boolean minLat = currentBounds.getMinLat() <= initialBounds.getMinLat();
        boolean maxLat = currentBounds.getMaxLat() <= initialBounds.getMaxLat();
        
        
        if(minLong || maxLong || minLat || maxLat) {
            currentBounds.setMinLong(initialBounds.getMinLong());
            currentBounds.setMaxLong(initialBounds.getMaxLong());
            currentBounds.setMinLat(initialBounds.getMinLat());
            currentBounds.setMaxLat(initialBounds.getMaxLat());
        } else {
            currentBounds.setMinLong((float) (newLong - deltaLong));
            currentBounds.setMaxLong((float) (newLong + deltaLong));
            currentBounds.setMinLat((float) (newLat - deltaLat));
            currentBounds.setMaxLat((float) (newLat + deltaLat));
        }
    }

    /**
     * Get lane size
     * @return Lane size
     */
    public double getLaneSize() {
        return laneSize;
    }

    /**
     * Get initial bounds
     * @return Initial bounds
     */
    public GraphicBounds getInitialBounds() {
        return initialBounds;
    }

    /**
     * Get current bounds
     * @return Current bounds
     */
    public GraphicBounds getCurrentBounds() {
        return currentBounds;
    } 
}
