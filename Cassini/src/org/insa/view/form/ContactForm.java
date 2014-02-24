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

import org.insa.controller.validator.ContactValidator;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.insa.controller.MainController;

/**
 *
 * @author Thomas Thiebaud
 */
public class ContactForm extends FormPanel {
    private final TextField recipient = new TextField();
    private final TextField subject = new TextField();
    private final TextArea message = new TextArea();
    private final SubmitButton submit = new SubmitButton("Envoyer", this);
    
    public ContactForm() {
        this.formValidator = new ContactValidator(this);
        this.setAlignment(Pos.CENTER);
        this.setVgap(10);
        this.setHgap(10);
        
        this.addFormField(new FormField("Email", recipient));
        this.addFormField(new FormField("Sujet", subject));
        this.addFormField(new FormField("Message", message));
        this.addFormField(new FormField("", submit));      
    }
    
    /**
     * Clear fields by remove all informations into the form
     */
    public void clearFields() {
        recipient.setText("");
        subject.setText("");
        message.setText("");
    }

    @Override
    public void performSubmitAction() {
        if(formValidator.validate()) {
            MainController.getInstance().performSendEmail(recipient.getText(), subject.getText(), message.getText());
            informationLabel.setText(formValidator.getSuccess());
        }
        else {
            informationLabel.setText(formValidator.getError());
        }
            
    }

    /**
     * Get recipient TextField
     * @return Recipient TextField
     */
    public TextField getRecipient() {
        return recipient;
    }

    /**
     * Get subject TextField
     * @return Subject TextField
     */
    public TextField getSubject() {
        return subject;
    }

    /**
     * Get message TextArea
     * @return Message TextArea
     */
    public TextArea getMessage() {
        return message;
    }  
}
