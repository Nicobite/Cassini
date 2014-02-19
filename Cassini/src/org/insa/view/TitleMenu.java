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

package org.insa.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Thomas Thiebaud
 */
public class TitleMenu extends BorderPane{
    
    private final Label helpLabel = new Label();
    private final Label contactLabel = new Label();
    private final VBox layout = new VBox();
    
    /**
     * Default constructor
     */
    public TitleMenu() {
        this.setStyle("-fx-background-color: #272A2D;");
        
        helpLabel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/help.png"))));
        helpLabel.setPrefWidth(50);
        helpLabel.setPrefHeight(50);
        helpLabel.setAlignment(Pos.CENTER);
        
        contactLabel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/contact.png"))));
        contactLabel.setPrefWidth(50);
        contactLabel.setPrefHeight(50);
        contactLabel.setAlignment(Pos.CENTER);
        
        layout.getChildren().add(helpLabel);
        layout.getChildren().add(contactLabel);
        
        this.setBottom(layout);
    }
}
