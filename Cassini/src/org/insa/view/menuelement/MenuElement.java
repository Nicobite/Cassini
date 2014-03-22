/*
* Copyright 2014 Thomas Thiebaud
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.insa.view.menuelement;

import javafx.scene.control.ToggleButton;

/**
 *
 * @author Thomas Thiebaud
 */
public abstract class MenuElement extends ToggleButton implements MenuElementListener {
    
    protected int width = 200;
    protected int height = 200;
    
    /**
     * Default constructor
     */
    public MenuElement() {
        super();
    }
    
    /**
     * Constructor
     * @param name Name of the entry into the menu
     * @param height Height of the entry into the menu
     * @param width Width of the entry into a menu
     */
    public MenuElement(String name, int height, int width) {
        super(name);
        this.height = height;
        this.width = width;
        this.setPrefHeight(height);
        this.setPrefWidth(width);
    }
}
