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

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Thomas Thiebaud
 */
public class DefaultPanel extends VBox{
    
    private final InnerShadow effect = new InnerShadow();
    private final Label title = new Label("CASSINI");
    private final Label subTitle = new Label("SIMULATION DE TRAFIC ROUTIER");
    
    /**
     * Default contructor
     */
    public DefaultPanel() {
        effect.setOffsetX(4f);
        effect.setOffsetY(4f);
        
        //title.setGraphic(new ImageView(new Image("/cassini.png")));
        title.setFont(Font.font("HelveticaNeue", FontWeight.BLACK, FontPosture.ITALIC , 100));
        title.setEffect(effect);
        
        subTitle.setFont(Font.font("HelveticaNeue", FontWeight.BLACK, FontPosture.ITALIC , 25));
        subTitle.setEffect(effect);
        
        this.setStyle("-fx-background-color: #E2E5E6;");
        
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(title);
        this.getChildren().add(subTitle);
    }
}
