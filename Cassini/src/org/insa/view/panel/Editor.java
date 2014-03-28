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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 *
 * @author thomas
 */
public class Editor extends Pane implements EventHandler<MouseEvent> {
    
    private GraphicsContext graphic = null;
    private double x[] = new double[4];
    private double y[] = new double[4];
    private int width = 20;
    
    
    
    public Editor() {
    }
    
    public Editor(int width, int height) {
        super();
        
        x[0] = -1;
        y[0] = -1;
        
        this.setOnMouseClicked(this);
    }
    
    @Override
    public void handle(MouseEvent t) {        
        x[1] = t.getSceneX() - 50;
        y[1] = t.getSceneY() - 50;
        
        if(x[0] != -1 && y[0] != -1) {
            double angle = Math.atan2(y[0] - y[1], x[0] - x[1]);
            double deltaX = Math.sin(angle) * width;
            double deltaY = Math.cos(angle) * width;
            
            if(deltaX < 0)
                deltaX *= -1;
            if(deltaY < 0)
                deltaY *= -1;
            
            if(x[0] < x[1] && y[0] < y[1]) {
                deltaX *= -1;
            }
            else if(x[0] < x[1] && y[0] > y[1]) {
                
            }
            else if(x[0] > x[1] && y[0] > y[1]) {
                deltaY *= -1;
                //deltaX *= -1;
            }
            else if(x[0] > x[1] && y[0] < y[1]) {
                deltaY *= -1;
                deltaX *= -1;
            }
            else if (x[0] == x[1]) {
                deltaY = 0;
            }
            else if(y[0] == y[1]) {
                deltaX = 0;
                if(x[0] > x[1])
                    deltaY *= -1;
            }
            
            double coinX[] = new double[3];
            double coinY[] = new double[3];
            
            if(x[3] != 0 && y[3] != 0) {
                coinX[0] = x[0];
                coinX[1] = x[0] + deltaX;
                coinX[2] = x[3];
                
                coinY[0] = y[0];
                coinY[1] = y[0] + deltaY;
                coinY[2] = y[3];
                
                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(new Double[]{
                coinX[0], coinY[0],
                coinX[1], coinY[1],
                coinX[2], coinY[2]});
                
                this.getChildren().add(polygon);
            }
            
            x[2] = x[1] + deltaX;
            x[3] = x[0] + deltaX;
            
            y[2] = y[1] + deltaY;
            y[3] = y[0] + deltaY;
                        
            final Polygon polygon = new Polygon();
            polygon.getPoints().addAll(new Double[]{
                x[0], y[0],
                x[1], y[1],
                x[2], y[2],
                x[3], y[3]});
            
            polygon.setFill(Color.GRAY);
            
            this.getChildren().add(polygon);
            
            Line line1 = new Line(x[0], y[0], x[1], y[1]);
            line1.setStrokeWidth(3);
            line1.setStroke(Color.WHITE);
            Line line2 = new Line(x[2], y[2], x[3], y[3]);
            line2.setStrokeWidth(3);
            line2.setStroke(Color.WHITE);
            Line line3 = new Line(x[0] + deltaX / 2, y[0] + deltaY / 2, x[1] + deltaX / 2, y[1] + deltaY / 2);
            line3.setStrokeWidth(3);
            line3.setStroke(Color.WHITE);
            
            this.getChildren().addAll(line1,line2,line3);
        }
        
        x[0] = x[1];
        x[3] = x[2];
        
        y[0] = y[1];
        y[3] = y[2];
    }
}
