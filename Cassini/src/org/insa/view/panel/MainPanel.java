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

package org.insa.view.panel;

import javafx.scene.layout.BorderPane;
/**
 *
 * @author Thomas Thiebaud
 */
public class MainPanel extends BorderPane{
    
    private final BorderPane layout = new BorderPane();
    private final DefaultPanel defaultPanel = new DefaultPanel();
    
    /**
     * Default constructor
     */
    public MainPanel() {        
        layout.setLeft(new NavigationPanel());
        
        this.setLeft(layout);
        this.setCenter(defaultPanel);
    }
}
