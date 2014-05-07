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
package org.insa.view.form;

import org.insa.controller.validator.FormValidator;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 *
 * @author Thomas Thiebaud
 */
public abstract class FormPanel extends GridPane implements SubmitFormListener{
    private final ArrayList<FormField> formFields = new ArrayList<FormField>();
    protected FormValidator formValidator = null;
    protected Label informationLabel = new Label();
    
    /**
     * Default constructor
     */
    public FormPanel() {
        informationLabel.setFont(new Font(15));
        informationLabel.setAlignment(Pos.CENTER);
        this.add(informationLabel, 0, 0, 2, 1);
    }
    
    /**
     * Add a FormField into FormPanel
     * @param formField FormField to add
     */
    public void addFormField(FormField formField) {
        formFields.add(formField);
        this.add(new Label(formField.getLegend()), 0, formFields.size()+1);
        this.add(formField.getControl(), 1, formFields.size()+1);
    }

    /**
     * Set informationLabel text
     * @param information New text
     */
    public void setInformationText(String information) {
        informationLabel.setText(information);
    }
}
