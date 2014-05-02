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

import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.view.menuelement.ToolBarButton;

/**
 *
 * @author Thiebaud Thomas
 */
public class VehicleToolBar extends CustomToolBar {

    /**
     * Default constructor
     */
    public VehicleToolBar() {
        this.add(new ToolBarButton("reset",this));
        this.add(new ToolBarButton("open", this));
        this.add(new ToolBarButton("save", this));
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
