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
package org.insa.view.form;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

/**
 *
 * @author Thiebaud Thomas
 */
public class NumberBox extends BorderPane {
    
    private final Label title;
    private final Label number;
    
    /**
     * Constructor
     * @param title Title of the number box
     * @param number Value of the number box
     */
    public NumberBox(String title, int number) {
        this.title = new Label(title);
        this.number = new Label(""+number);
        
        this.title.setFont(Font.font(18));
        this.number.setFont(Font.font(50));

        this.getStyleClass().add("tool-bar");
        this.getStyleClass().add("left-tool-bar");
        this.getStyleClass().add("default-padding");
        
        this.setTop(this.title);
        this.setCenter(this.number);
        this.setPrefWidth(1000);
        
        this.setAlignment(this.getTop(), Pos.CENTER);
        this.setAlignment(this.getCenter(), Pos.CENTER);
    }
}
