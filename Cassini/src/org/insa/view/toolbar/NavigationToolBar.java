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
package org.insa.view.toolbar;

import javafx.geometry.Orientation;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.view.menuelement.ToolBarToggleButton;

/**
 *
 * @author Thiebaud Thomas
 */
public class NavigationToolBar extends CustomToolBar {
    private final ToolBarToggleButton drawButton = new ToolBarToggleButton("draw",this,"navigation-element");
    private final ToolBarToggleButton mapButton = new ToolBarToggleButton("map",this,"navigation-element");
    private final ToolBarToggleButton carButton = new ToolBarToggleButton("car",this,"navigation-element");
    private final ToolBarToggleButton simulationButton = new ToolBarToggleButton("simulation",this,"navigation-element");
    private final ToolBarToggleButton resultButton = new ToolBarToggleButton("result",this,"navigation-element");
    
    /**
     * Default constructor
     */
    public NavigationToolBar() {
        super(Orientation.VERTICAL, "navigation-menu");
        this.addCssClass("left-tool-bar");
        
        drawButton.setTooltip(new Tooltip("Ouvrir l'éditeur de cartes"));
        mapButton.setTooltip(new Tooltip("Charger la carte pour la simulation"));
        carButton.setTooltip(new Tooltip("Charger des véhicules pour la simulation"));
        simulationButton.setTooltip(new Tooltip("Ouvrir la simulation"));
        resultButton.setTooltip(new Tooltip("Afficher les résultats de la simulation"));
        
        this.add(drawButton);
        this.add(mapButton);
        this.add(carButton);
        this.add(simulationButton);
        this.add(resultButton);
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
                MainController.getInstance().performOpenMap(false);
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
