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

package org.insa.view;

/**
 *
 * @author Thomas Thiebaud
 */
public class MainMenu extends VerticalMenu{
    
    /**
     * Default constructor
     */
    public MainMenu() {
        super("#FFFFFF", "#E2E5E6", "#B6B6B6", "transparent", "#B3B2B3", "transparent", "#272A2D");
        
        this.addMenuElement(new MainMenuElement("Carte"));
        this.addMenuElement(new MainMenuElement("Configuration"));
        this.addMenuElement(new MainMenuElement("Simulation"));
    }
    
}
