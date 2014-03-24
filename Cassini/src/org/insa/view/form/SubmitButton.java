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
package org.insa.view.form;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Thomas Thiebaud
 */
public class SubmitButton extends Button{
    
    private FormPanel formPanel = null;
    
    /**
     * Constructor
     * @param name Button text
     * @param formPanel Form concerned by the submit button
     */
    public SubmitButton(String name, final FormPanel formPanel) {
        super(name);
        this.formPanel = formPanel;
        this.getStyleClass().add("submit-button");
        this.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                formPanel.performSubmitAction();
            }
        });
    }
}
