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

import org.insa.controller.MainController;

/**
 *
 * @author Thomas Thiebaud
 */
public class VehicleTool extends ImageMenuElement {
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     * @param name Name of the entry into the menu
     */
    public VehicleTool(String imageName, String name) {
        super(imageName, name);
        this.getStyleClass().add("tool");
    }
    
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     * @param name Name of the entry into the menu
     * @param isToogleButton true if the button is a toogle button, false otherwise
     */
    public VehicleTool(String imageName, String name, boolean isToogleButton) {
        super(imageName,name,50,150, isToogleButton);
        this.getStyleClass().add("tool");
    }
    
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     */
    public VehicleTool(String imageName) {
        this(imageName, "");
    }
    
    @Override
    public void performAddAction() {
        switch(imageName) {
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