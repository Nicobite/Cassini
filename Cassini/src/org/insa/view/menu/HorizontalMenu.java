/*
* Copyright 2014 Thomas Thiebaud
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.insa.view.menu;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.insa.view.menuelement.MenuElement;

/**
 *
 * @author Thomas Thiebaud
 */
public abstract class HorizontalMenu extends Menu {
    
    private ArrayList<MenuElement> elements = new ArrayList<MenuElement>();
    private ToggleGroup group = new ToggleGroup();
    
    /**
     * Default constructor
     */
    public HorizontalMenu() {
        layout = new HBox();
        ((HBox)layout).setAlignment(Pos.CENTER);
        this.setCenter(layout);
    }
    
}
