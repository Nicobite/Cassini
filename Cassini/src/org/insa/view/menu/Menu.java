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
package org.insa.view.menu;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.insa.view.menuelement.MenuElement;

/**
 *
 * @author Thomas Thiebaud
 */
public abstract class Menu extends BorderPane {
    
    protected Pane layout = null;
    protected ToggleGroup group = new ToggleGroup();
    protected ArrayList<MenuElement> elements = new ArrayList<MenuElement>();
    
    /**
     * Add an element into the menu
     * @param menuElement Element to add
     */
    public void addMenuElement(MenuElement menuElement) {
        layout.getChildren().add(menuElement);
        this.initElement(menuElement);
    }
    
    /**
     * Add an element into the menu to a given position
     * @param menuElement Element to add
     * @param position Given position
     */
    public void addMenuElement(MenuElement menuElement, Position position) {
        switch(position) {
            case TOP:
                this.setTop(menuElement);
                break;
            case RIGHT:
                this.setRight(menuElement);
                break;
            case BOTTOM:
                this.setBottom(menuElement);
                break;
            case LEFT:
                this.setLeft(menuElement);
                break;
        }
        this.initElement(menuElement);
    }
    
    /**
     * Init new menu elment menu by adding action listener
     * @param menuElement Menu elment to initialize
     */
    public void initElement(final MenuElement menuElement) {
        if(menuElement.isToggleButton())
            menuElement.setToggleGroup(group);
        this.elements.add(menuElement);
        
        menuElement.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent me){
                MenuElement menuElement = (MenuElement)(me.getSource());
                if(menuElement.isToggleButton()) {
                    if(!menuElement.isSelected())
                        menuElement.setSelected(true);
                    else
                        menuElement.performAddAction();
                } else {
                    menuElement.setSelected(false);
                    menuElement.performAddAction();
                } 
            }
        });
    }
}
