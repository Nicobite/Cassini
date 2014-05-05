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
package org.insa.view.menuelement;

import javafx.geometry.Orientation;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.insa.view.menu.CustomToolBar;

/**
 *
 * @author Thebaud Thomas
 */
public class ToolBarToggleButton extends ToggleButton {
    private String imageName = "";
    
    /**
     * Constructor
     * @param imageName Icon name in the /image directory
     * @param name Button string
     * @param toolBar Reference to tool bar
     */
    public ToolBarToggleButton(String imageName, String name, CustomToolBar toolBar, String cssClass) {
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
        if(cssClass.isEmpty())
            this.getStyleClass().add("tool-bar-button");
        else 
            this.getStyleClass().add(cssClass);
    }
    
    /**
     * Constructor
     * @param imageName Icon name in the /image directory
     * @param toolBar Reference to tool bar
     */
    public ToolBarToggleButton(String imageName, CustomToolBar toolBar) {
        this(imageName,"",toolBar,""); 
    }
    
    /**
     * Constructor
     * @param imageName Icon name in the /image directory
     * @param toolBar Reference to tool bar
     * @param cssClass Css class name
     */
    public ToolBarToggleButton(String imageName, CustomToolBar toolBar, String cssClass) {
        this(imageName,"",toolBar,cssClass); 
    } 

    /**
     * Get image name
     * @return 
     */
    public String getImageName() {
        return imageName;
    }
}
