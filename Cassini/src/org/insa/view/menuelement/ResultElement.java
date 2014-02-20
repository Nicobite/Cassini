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

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.insa.controller.MainController;

/**
 *
 * @author Thomas Thiebaud
 */
public class ResultElement extends MenuElement {
private String imageName = "default";
     
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     * @param name Name of the entry into the menu
     */
    public ResultElement(String imageName, String name) {
        super(name,40,150,Font.font("Arial", FontWeight.BLACK, 15));
        this.imageName = imageName;

        this.setGraphic(new ImageView(new Image("/org/insa/view/image/" + imageName + ".png")));
        this.setAlignment(Pos.CENTER);
    }
    
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     */
    public ResultElement(String imageName) {
        this(imageName, "");
    }
    
    @Override
    public void performAddAction() {
        switch(imageName) {
            case "note" :
                MainController.getInstance().performDisplayNoteResultPanel();
                break;
            case "graph" : 
                MainController.getInstance().performDisplayGraphResultPanelPanel();
                break;
        }
    }
    
}
