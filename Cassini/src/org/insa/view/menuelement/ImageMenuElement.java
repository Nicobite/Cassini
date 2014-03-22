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

/**
 *
 * @author Thomas Thiebaud
 */
public abstract class ImageMenuElement extends MenuElement{
    
    protected String imageName = "default";
    
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     * @param name Name of the entry into the menu
     * @param height MenuElement height
     * @param width MenuElement width
     */
    public ImageMenuElement(String imageName, String name, int height, int width) {
        super(name,height,width);
        this.imageName = imageName;
        
        this.setGraphic(new ImageView(new Image("/org/insa/view/image/" + imageName + ".png")));
        this.setAlignment(Pos.CENTER);
    }
    
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     * @param name Name of the entry into the menu
     */
    public ImageMenuElement(String imageName, String name) {
        this(imageName, name, 40, 150);
    }
    
    /**
     * Constructor
     * @param imageName Name of the picture located in org.insa.view.image package
     */
    public ImageMenuElement(String imageName) {
        this(imageName, "");
    }
}
