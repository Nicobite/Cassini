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
public class MainElement extends MenuElement {
    
    /*
    private final int height = 50;
    private final int width = 200;
    private final String fontName = "Arial";
    private final FontWeight fontWeight = FontWeight.BLACK;
    private final int fontSize = 15;
    */
    
    /**
     * Constructor
     * @param name Name of the entry into the menu
     */
    public MainElement(String name) {
        super(name,50,200);
    }
    
    @Override
    public void performAddAction() {
        switch(this.getText()) {
            case "Carte":
                MainController.getInstance().performDisplayMapPanel();
                break;
            case "Configuration" :
                MainController.getInstance().performDisplayConfigurationPanel();
                break;
            case "Simulation":
                MainController.getInstance().performDisplaySimulationPanel();
                break;
            case "Résultat":
                MainController.getInstance().performDisplayResultPanel();
                break;
            default :
                MainController.getInstance().performDisplayDefaultPanel();
                break;
        }
    }
}
