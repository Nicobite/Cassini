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

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Thomas Thiebaud
 */
public abstract class MenuElement extends Label implements MenuElementListener{
    
    private int height = 50;
    private int width = 200;
    private Font font = Font.font("Vernada", FontWeight.BOLD, USE_PREF_SIZE);
    private String topBorderColor = "transparent";
    private String rightBorderColor = "transparent";
    private String bottomBorderColor = "transparent";
    private String leftBorderColor = "transparent";
    
    
    /**
     * Default constructor
     */
    public MenuElement() {
        updateMenuElementView();
    }
    
    /**
     * Constructor
     * @param name Name of the entry into the menu
     */
    public MenuElement(String name) {
        super(name);
        updateMenuElementView();
    }
    
    /**
     * Constructor
     * @param name Name of the entry into the menu
     * @param height Height of the entry into the menu
     * @param width Width of the entry into a menu
     */
    public MenuElement(String name, int height, int width) {
        this(name);
        this.height = height;
        this.width = width;
        updateMenuElementView();
    }
    
    /**
     * Constructor
     * @param name Name of the entry into the menu
     * @param height Height of the entry into the menu
     * @param width Width of the entry into a menu
     * @param font New font for the text
     */
    public MenuElement(String name, int height, int width, Font font) {
        this(name, height, width);
        this.font = font;
        updateMenuElementView();
    }
    
    /**
     * Constructor
     * @param name Name of the entry into the menu
     * @param height Height of the entry into the menu
     * @param width Width of the entry into a menu
     * @param font New font for the text
     * @param topBorderColor Color of the top border
     * @param rightBorderColor Color of the right border
     * @param bottomBorderColor Color of the bottom border
     * @param leftBorderColor Color of the left border
     */
    public MenuElement(String name, int height, int width, Font font, String topBorderColor, String rightBorderColor, String bottomBorderColor, String leftBorderColor) {
        this(name, height, width, font);
        this.topBorderColor = topBorderColor;
        this.rightBorderColor = rightBorderColor;
        this.bottomBorderColor = bottomBorderColor;
        this.leftBorderColor = leftBorderColor;
        updateMenuElementView();
    }
    
    /**
     * Set menu element geometry and view using the MenuElement class attributes
     */
    protected final void updateMenuElementView() {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setFont(font);
        this.setStyle("-fx-border-color: " + topBorderColor + " " + rightBorderColor + " " + bottomBorderColor + " " + leftBorderColor + ";");
    }
}
