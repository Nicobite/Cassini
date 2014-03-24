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
 * @author thomas
 */
public class NavigationElement extends ImageMenuElement {
    
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     * @param name Name of the entry into the menu
     */
    public NavigationElement(String imageName, String name) {
        super(imageName, name, 50, 50, true);
        this.getStyleClass().add("navigation-element");
    }
    
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     */
    public NavigationElement(String imageName) {
        this(imageName, "");
    }
    
    @Override
    public void performAddAction() {
        switch(imageName) {
            case "map" :
                MainController.getInstance().performDisplayMapPanel();
                break;
            case "configuration" :
                MainController.getInstance().performDisplayConfigurationPanel();
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
