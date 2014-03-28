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

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Thomas Thiebaud
 */
public abstract class DrawingPanel extends Canvas implements EventHandler<MouseEvent> {
    
    protected float initialMinLong = 0;
    protected float initialMaxLong = 0;
    protected float initialMinLat = 0;
    protected float initialMaxLat = 0;
    
    protected float minLong = 0;
    protected float maxLong = 0;
    protected float minLat = 0;
    protected float maxLat = 0;
        
    int scale = 1;
    int height;
    int width;
    
    protected GraphicsContext graphic = this.getGraphicsContext2D();
    
    /**
     * Constructor
     * @param width Panel width
     * @param height Panel height
     */
    public DrawingPanel(int width, int height) {
        super(width,height);
        this.height = height;
        this.width = width;
        
        this.setOnMouseClicked(this);
    }
    
    @Override
    public void handle(MouseEvent t) {
        double x = t.getSceneX();
        double y = t.getSceneY();
        
        double deltaLong = maxLong - minLong;
        double deltaLat = maxLat - minLat;
        
        double newLong = x * ( maxLong - minLong) / width + minLong;
        double newLat = ( 1 - ( y / height )) * (maxLat - minLat) + minLat;
        
        if(t.getButton() == MouseButton.PRIMARY) {
            scale ++;
            minLong = (float) (newLong - deltaLong /4);
            maxLong = (float) (newLong + deltaLong / 4);
            minLat = (float) (newLat - deltaLat /4);
            maxLat = (float) (newLat + deltaLat / 4);
        }
        else if(t.getButton() == MouseButton.SECONDARY && scale > 1) {
            scale --;
            minLong = (float) (newLong - deltaLong);
            maxLong = (float) (newLong + deltaLong);
            minLat = (float) (newLat - deltaLat);
            maxLat = (float) (newLat + deltaLat);
            if(minLong < initialMinLong || maxLong > initialMaxLong || minLat < initialMinLat || maxLat > initialMaxLat) {
                scale = 1;
                minLong = initialMinLong;
                maxLong = initialMaxLong;
                minLat = initialMinLat;
                maxLat = initialMaxLat;
            }
        }
        
        if(scale < 1)
            scale = 1;
        
        this.repaint();
    }
    
    /**
     * Convert a longitude into a X coordinate
     * @param lon longitude to convert
     * @return X coordinate
     */
    public int longToX(float lon) {
        return  (int)( width * (lon - this.minLong) / (this.maxLong - this.minLong));
    }
    
    /**
     * Convert a latitude into a Y coordinate
     * @param lat latitude to convert
     * @return Y coordinate
     */
    public int latToY(float lat) {
        return (int)(height * (1 - (lat - this.minLat) / (this.maxLat - this.minLat)));
    }
    
    /**
     * Get absolute angle between two points
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return Absolute angle
     */
    public double angle(int x1, int y1, int x2, int y2) {
        return Math.atan2(y2 - y1, x2 - x1);
    }
    
    /**
     * Paint all components into panel
     */
    public abstract void paint();
    
    /**
     * Repaint the panel by clearing it and calling the pain method
     */
    public abstract void repaint();
}
