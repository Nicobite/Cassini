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
package org.insa.core.graphicmodel;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 *
 * @author Thomas Thiebaud
 */
public class GraphicTarget extends Circle {
    
    /**
     * Constructor
     * @param x center x of the circle
     * @param y center y of the circle
     */
    public GraphicTarget(int x, int y) {
        super(x, y, 25);
        this.setFill(new ImagePattern(new Image("/org/insa/view/image/target_low.png")));
    }
    
    /**
     * Constructor
     * @param x center x of the circle
     * @param y center y of the circle
     * @param imageName Name of the image
     */
    public GraphicTarget(int x, int y,String imageName) {
        super(x, y, 25);
        this.setFill(new ImagePattern(new Image("/org/insa/view/image/" + imageName + ".png")));
    }
}
