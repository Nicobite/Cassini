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
package org.insa.view.form;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Thiebaud Thomas
 */
public class FloatSpinner extends HBox {
    
    private TextField field;
    private final Button up = new Button();
    private final Button down = new Button();
    private final VBox layout = new VBox();
    
    /**
     * Constructor
     * @param value Initial value 
     */
    public FloatSpinner(float value) {
        field = new TextField(String.valueOf(value));
        field.setEditable(false);
        field.setPrefWidth(500);
        field.setMaxWidth(Double.MAX_VALUE);
         
        up.setMaxSize(13,13);
        up.setMinSize(13,13);
        up.setPrefSize(13,13);
        
        down.setMaxSize(13,13);
        down.setMinSize(13,13);
        down.setPrefSize(13,13);
        
        up.setGraphic(new ImageView(new Image("/org/insa/view/image/up.png",10,10,false,true)));
        down.setGraphic(new ImageView(new Image("/org/insa/view/image/down.png",10,10,false,true)));
        
        up.getStyleClass().add("submit-button");
        down.getStyleClass().add("submit-button");
        
        up.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                float tmp = Float.valueOf(field.getText());
                tmp += 0.001;
                
                field.setText(String.valueOf(tmp));
                
            }
        
        });
        
        down.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                float tmp = Float.valueOf(field.getText());
                tmp -= 0.001;
                field.setText(String.valueOf(tmp));
            }
        
        });
        
        layout.getChildren().add(up);
        layout.getChildren().add(down);
          
        this.getChildren().add(field);
        this.getChildren().add(layout);
    } 

    /**
     * Get float value from field
     * @return Float value from float spinner
     */
    public float getValue() {
        return Float.valueOf(field.getText());
    }
    
    /**
     * Get field
     * @return Field
     */
    public TextField getField() {
        return field;
    }

    /**
     * Get up
     * @return Up button 
     */
    public Button getUp() {
        return up;
    }

    /**
     * Get down
     * @return Down button
     */
    public Button getDown() {
        return down;
    }
}
