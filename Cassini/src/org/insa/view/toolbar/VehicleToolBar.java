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
import org.insa.view.menuelement.ToolBarButton;

/**
 *
 * @author Thiebaud Thomas
 */
public class VehicleToolBar extends CustomToolBar {
    private final ToolBarButton resetButton = new ToolBarButton("reset",this);
    private final ToolBarButton openButton = new ToolBarButton("open",this);
    private final ToolBarButton saveButton = new ToolBarButton("save",this);
    
    /**
     * Default constructor
     */
    public VehicleToolBar() {
        super(Orientation.HORIZONTAL, "top-tool-bar");
        
        resetButton.setTooltip(new Tooltip("Réinitialiser la liste des véhicules"));
        openButton.setTooltip(new Tooltip("Ouvrir une liste de véhicules au format .vhc.xml"));
        saveButton.setTooltip(new Tooltip("Sauvegarder la liste des véhicules au format .vhc.xml"));
        
        this.add(resetButton);
        this.add(openButton);
        this.add(saveButton);
    }
    
    @Override
    public void handle(MouseEvent t) {
        ToolBarButton button = (ToolBarButton)t.getSource();
        switch(button.getImageName()) {
            case "reset":
                MainController.getInstance().performResetVehiclesModel();
                break;
            case "open" :
                MainController.getInstance().performOpenVehicles();
                break;
            case "save" :
                MainController.getInstance().performSaveVehicles();
                break;
        }
    }
}
