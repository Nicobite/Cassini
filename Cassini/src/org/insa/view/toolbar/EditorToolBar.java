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
package org.insa.view.toolbar;

import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.view.menuelement.ToolBarButton;

/**
 *
 * @author Thiebaud Thomas
 */
public class EditorToolBar extends CustomToolBar {

    /**
     * Default constructor
     */
    public EditorToolBar() {
        super(Orientation.HORIZONTAL, "top-tool-bar");
        this.add(new ToolBarButton("new",this));
        this.add(new ToolBarButton("open",this));
        this.add(new ToolBarButton("save",this));
        this.add(new ToolBarButton("resize",this));
        this.add(new ToolBarButton("move",this));
    }
    
    @Override
    public void handle(MouseEvent t) {
        ToolBarButton button = (ToolBarButton)t.getSource();
        switch(button.getImageName()) {
            case "new" :
                MainController.getInstance().performCreateNewMap();
                break;
            case "open" :
                MainController.getInstance().performOpenMap(true);
                break;
            case "save" :
                MainController.getInstance().performSaveMap();
                break;
            case "resize" :
                MainController.getInstance().performDisplayResizeMapDock();
                break;
            case "move" :
                break;
        }
    }
}
