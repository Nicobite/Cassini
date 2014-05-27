/*
* Copyright 2014 Juste Abel Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
package org.insa.view.panel;

import javafx.scene.layout.BorderPane;
import org.insa.view.toolbar.VehicleToolBar;

/**
 *
 * @author Thomas Thiebaud
 */
public class VehiclesPanel extends BorderPane {
    
    private VehicleDataPanel vehicleDataPanel = new VehicleDataPanel();
    
    /**
     * Default constructor
     */
    public VehiclesPanel() {
        this.setTop(new VehicleToolBar());
        this.setCenter(vehicleDataPanel);
    }

    /**
     * Get vehicles data panel
     * @return Vehicles data panel
     */
    public VehicleDataPanel getVehicleDataPanel() {
        return vehicleDataPanel;
    }
}
