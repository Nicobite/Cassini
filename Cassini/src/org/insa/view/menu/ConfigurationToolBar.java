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

import org.insa.view.menuelement.ConfigurationTool;

/**
 *
 * @author Thomas Thiebaud
 */
public class ConfigurationToolBar extends HorizontalMenu{
    
    /**
     * Default constructor
     */
    public ConfigurationToolBar() {
        this.getStyleClass().add("tool-bar");
        this.getStyleClass().add("top-tool-bar");
        
        this.addMenuElement(new ConfigurationTool("car","Véhicules"));
        this.addMenuElement(new ConfigurationTool("reset", "Reset"));
        this.addMenuElement(new ConfigurationTool("open", "Importer"));
        this.addMenuElement(new ConfigurationTool("save", "Exporter"));
    }
}
