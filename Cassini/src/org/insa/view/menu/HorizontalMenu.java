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

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.insa.view.menuelement.MenuElement;

/**
 *
 * @author Thomas Thiebaud
 */
public abstract class HorizontalMenu extends HBox{
    
    private ArrayList<MenuElement> elements = new ArrayList<MenuElement>();
    
    private String backgroundColor = "#FFFFFF";
    private String clickedElementColor = "#EFEFEF";
    private String clickedBorderColor = "#B6B6B6";
    private String topBorderColor = "transparent";
    private String rightBorderColor = "transparent";
    private String bottomBorderColor = "transparent";
    private String leftBorderColor = "transparent";
    private Pos alignment = Pos.CENTER_LEFT;
    
    /**
     * Constructor
     * @param backgroundColor Menu background color
     */
    public HorizontalMenu(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        updateVerticalMenuView();
    }
    
    /**
     * Constructor
     * @param backgroundColor Menu background color
     * @param alignment Menu alignment
     */
    public HorizontalMenu(String backgroundColor, Pos alignment) {
        this.backgroundColor = backgroundColor;
        this.alignment = alignment;
        updateVerticalMenuView();
    }
    
    /**
     * Constructor
     * @param backgroundColor Menu background color
     * @param clickedElementColor Selected element color
     * @param clickedBorderColor Selected element border color
     */
    public HorizontalMenu(String backgroundColor, Pos alignment, String clickedElementColor, String clickedBorderColor) {
        this(backgroundColor, alignment);
        this.clickedElementColor = clickedElementColor;
        this.clickedBorderColor = clickedBorderColor;
        updateVerticalMenuView();
    }
    
    /**
     * Constructor
     * @param backgroundColor Menu background color
     * @param clickedElementColor Selected element color
     * @param clickedBorderColor Selected element border color
     * @param topBorderColor Color of the top border
     * @param rightBorderColor Color of the right border
     * @param bottomBorderColor Color of the bottom border
     * @param leftBorderColor Color of the left border
     */
    public HorizontalMenu(String backgroundColor, Pos alignment, String clickedElementColor, String clickedBorderColor, String topBorderColor, String rightBorderColor, String bottomBorderColor, String leftBorderColor) {
        this(backgroundColor, alignment, clickedElementColor, clickedBorderColor);
        this.topBorderColor = topBorderColor;
        this.rightBorderColor = rightBorderColor;
        this.bottomBorderColor = bottomBorderColor;
        this.leftBorderColor = leftBorderColor;
        updateVerticalMenuView();
    }
    
    /**
     * Set menu element geometry and view using the MenuElement class attributes
     */
    protected final void updateVerticalMenuView() {
        this.setAlignment(alignment);
        this.setStyle("-fx-background-color: " + backgroundColor + ";"
                + "-fx-border-color: " + topBorderColor + " " + rightBorderColor + " " + bottomBorderColor + " " + leftBorderColor + ";");
    }
    
    /**
     * Add an element into the menu
     * @param menuElement Element to add
     */
    public void addMenuElement(final MenuElement menuElement) {
        menuElement.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                menuElement.performAddAction();
                for(MenuElement e : elements) {
                    e.setStyle("-fx-background-color: " + backgroundColor + ";");
                    e.setEffect(null);
                }
                menuElement.setStyle("-fx-background-color: " + clickedElementColor + ";"
                        + "-fx-effect:innershadow(gaussian, " + clickedBorderColor + ", 0,0,0,1)");
            }
        });
        
        elements.add(menuElement);
        this.getChildren().add(menuElement);
    }
}
