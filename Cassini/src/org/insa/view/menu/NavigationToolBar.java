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
package org.insa.view.menu;

import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.view.menuelement.ToolBarToggleButton;

/**
 *
 * @author Thiebaud Thomas
 */
public class NavigationToolBar extends CustomToolBar {
    
    /**
     * Default constructor
     */
    public NavigationToolBar() {
        super(Orientation.VERTICAL, "navigation-menu");
        this.addCssClass("left-tool-bar");
        this.add(new ToolBarToggleButton("draw",this,"navigation-element"));
        this.add(new ToolBarToggleButton("map",this,"navigation-element"));
        this.add(new ToolBarToggleButton("car",this,"navigation-element"));
        this.add(new ToolBarToggleButton("simulation",this,"navigation-element"));
        this.add(new ToolBarToggleButton("result",this,"navigation-element"));
    }

    @Override
    public void handle(MouseEvent t) {
        ToolBarToggleButton button = (ToolBarToggleButton)t.getSource();
        switch(button.getImageName()) {
            case "draw" :
                MainController.getInstance().performDisplayMapEditor();
                break;
            case "map" :
                MainController.getInstance().performDisplayMapPanel();
                MainController.getInstance().performOpenMap();
                break;
            case "car" :
                MainController.getInstance().performDisplayVehiclesPanel();
                break;
            case "simulation" :
                MainController.getInstance().performDisplaySimulationPanel();
                break;
            case "result" :
                MainController.getInstance().performDisplayResultPanel();
                break;
        }
    }
}
