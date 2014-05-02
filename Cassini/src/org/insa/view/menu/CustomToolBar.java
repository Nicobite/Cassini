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
package org.insa.view.menu;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Thiebaud Thomas
 */
public abstract class CustomToolBar extends ToolBar implements EventHandler<MouseEvent> {
    
    private ToggleGroup group = new ToggleGroup();
    
    /**
     * Default constructor
     */
    public CustomToolBar() {
        super();
    }
    
    /**
     * Constructor
     * @param orientation Orientation of the tool bar
     */
    public CustomToolBar(Orientation orientation) {
        super();
        if(orientation == Orientation.VERTICAL)
            this.setPrefWidth(50);
        if(orientation == Orientation.HORIZONTAL)
            this.setPrefHeight(50);
        this.setOrientation(orientation);
    }
    
    /**
     * Constructor
     * @param orientation Orientation of the tool bar
     * @param cssClass Css class name
     */
    public CustomToolBar(Orientation orientation, String cssClass) {
        this(orientation);
        this.getStyleClass().clear();
        this.getStyleClass().add(cssClass);
    }
    
    /**
     * Add a button to the tool bar. A toggle button will be added to the toggle group linked to the tool bar
     * @param element Tool bar element to add
     */
    public void add(ButtonBase element) {
        if(element instanceof ToggleButton)
            ((ToggleButton)element).setToggleGroup(group);
        this.getItems().add(element);
    }
}
