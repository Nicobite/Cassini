/*
 * Copyright 2014 Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.view.menuelement.ToolBarButton;

/**
 *
 * @author Thiebaud Thomas
 */
public class EditorToolBar extends CustomToolBar {
    private final ToolBarButton newButton = new ToolBarButton("new",this);
    private final ToolBarButton openButton = new ToolBarButton("open",this);
    private final ToolBarButton saveButton = new ToolBarButton("save",this);
    private final ToolBarButton resizeButton = new ToolBarButton("resize",this);
    
    /**
     * Default constructor
     */
    public EditorToolBar() {
        super(Orientation.HORIZONTAL, "top-tool-bar");
        
        newButton.setTooltip(new Tooltip("Créer une nouvelle carte"));
        openButton.setTooltip(new Tooltip("Ouvrir une carte au format .osm ou .map.xml"));
        saveButton.setTooltip(new Tooltip("Sauvegarder une carte au format .map.xml"));
        resizeButton.setTooltip(new Tooltip("Redimensionner une carte"));
        
        this.add(newButton);
        this.add(openButton);
        this.add(saveButton);
        this.add(resizeButton);
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
        }
    }
}
