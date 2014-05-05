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
package org.insa.view.menuelement;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.insa.view.menu.CustomToolBar;

/**
 *
 * @author Thiebaud Thomas
 */
public class ToolBarButton extends Button {
    private String imageName = "";
    
    /**
     * Constructor
     * @param imageName Icon name in the /image directory
     * @param name Button string
     * @param toolBar Reference to tool bar
     */
    public ToolBarButton(String imageName, String name, CustomToolBar toolBar) {
        super(name);
        this.imageName = imageName;
        this.setGraphic(new ImageView(new Image("/org/insa/view/image/" + imageName + ".png")));
        if(toolBar.getOrientation() == Orientation.HORIZONTAL) {
            this.setPrefHeight(50);
        }
        if(toolBar.getOrientation() == Orientation.VERTICAL) {
            this.setPrefWidth(50);
        }
        this.setMinWidth(50);
        this.setMinHeight(50);
        this.setOnMouseClicked(toolBar);
        //this.getStyleClass().clear();
        this.getStyleClass().add("tool-bar-button");
    }
    
    /**
     * Constructor
     * @param imageName Icon name in the /image directory
     * @param toolBar Reference to tool bar
     */
    public ToolBarButton(String imageName, CustomToolBar toolBar) {
        this(imageName,"",toolBar); 
    }
    
    /**
     * Constructor
     * @param imageName Icon name in the /image directory
     * @param toolBar Reference to tool bar
     * @param cssClass Css class name
     */
    public ToolBarButton(String imageName, CustomToolBar toolBar, String cssClass) {
        this(imageName,"",toolBar); 
        this.getStyleClass().clear();
        this.getStyleClass().add(cssClass);
    }

    /**
     * Get image name
     * @return 
     */
    public String getImageName() {
        return imageName;
    }
}
