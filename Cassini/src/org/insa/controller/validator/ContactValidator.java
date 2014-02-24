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
package org.insa.controller.validator;

import org.insa.view.form.ContactForm;

/**
 *
 * @author Thomas Thiebaud
 */
public class ContactValidator extends FormValidator{

    /**
     * Constructor
     * @param contactForm Form which need to be validated
     */
    public ContactValidator(ContactForm contactForm) {
        super(contactForm);
    }

    @Override
    public boolean validate() {
        ContactForm contactForm = (ContactForm) formPanel;
        if(!contactForm.getRecipient().getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            this.error = "Format de l'adresse mail incorect";
            return false;
        }
        if(contactForm.getSubject().getText().isEmpty()) {
            this.error = "Un sujet doit être précisé";
            return false;
        }
        if(contactForm.getMessage().getText().isEmpty()) {
            this.error = "Le message ne peut pas être vide";
            return false;
        }
        this.success = "Message bien envoyé";
        return true;
    }
    
}
