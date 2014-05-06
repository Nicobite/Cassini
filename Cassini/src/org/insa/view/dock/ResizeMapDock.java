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
package org.insa.view.dock;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.insa.view.graphicmodel.GraphicBounds;

/**
 *
 * @author Thiebaud Thomas
 */
public class ResizeMapDock extends AbstractDock {
    
    private TextField minLong = new TextField();
    private TextField maxLong = new TextField();
    private TextField minLat = new TextField();
    private TextField maxLat = new TextField();
    
    private final Button submit = new Button("Redimensionner");
    
    private final VBox layout = new VBox();
    
    /**
     * Defaul constructor
     */
    public ResizeMapDock() {
        super();
        this.init();
    }

    /**
     * Constructor
     * @param initialBounds Bounds of the current map 
     */
    public ResizeMapDock(GraphicBounds initialBounds) {
        minLong = new TextField(String.valueOf(initialBounds.getMinLong()));
        maxLong = new TextField(String.valueOf(initialBounds.getMaxLong()));
        minLat = new TextField(String.valueOf(initialBounds.getMinLat()));
        maxLat = new TextField(String.valueOf(initialBounds.getMaxLat()));
        this.init();
    }
    
    /**
     * Initialize resize map dock' components
     */
    private void init() {
        minLong.setPrefWidth(WIDTH);
        maxLong.setPrefWidth(WIDTH);
        minLat.setPrefWidth(WIDTH);
        maxLat.setPrefWidth(WIDTH);

        submit.setPrefWidth(WIDTH);
        submit.getStyleClass().add("submit-button");
        submit.getStyleClass().add("bottom-tool-bar");
        
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(3);
        layout.getChildren().add(new Text("Longitude minimale : "));
        layout.getChildren().add(minLong);
        layout.getChildren().add(new Text("Longitude maximale : "));
        layout.getChildren().add(maxLong);
        layout.getChildren().add(new Text("Latitude minimale : "));
        layout.getChildren().add(minLat);
        layout.getChildren().add(new Text("Latitude maximale : "));
        layout.getChildren().add(maxLat);
        
        this.setCenter(layout);
        this.setBottom(submit);
    }
}
