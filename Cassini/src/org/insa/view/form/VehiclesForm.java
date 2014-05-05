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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import org.insa.controller.MainController;
import org.insa.controller.validator.VehiclesValidator;
import org.insa.core.driving.Vehicle;

/**
 *
 * @author Thomas Thiebaud
 */
public class VehiclesForm extends FormPanel {
    
    private final TextField maxSpeed = new TextField();
    private final TextField maxAcceleration = new TextField();
    private final TextField maxDeceleration = new TextField();
    private final TextField length = new TextField();
    private final TextField quantity = new TextField("1");
    private final SubmitButton submit = new SubmitButton("Ajouter", this);
    
    /**
     * Default constructor
     */
    public VehiclesForm() {
        this.formValidator = new VehiclesValidator(this);
        this.setAlignment(Pos.CENTER);
        this.setVgap(10);
        this.setHgap(10);
        
        this.addFormField(new FormField("Vitesse maximale", maxSpeed));
        this.addFormField(new FormField("Accélération maximale", maxAcceleration));
        this.addFormField(new FormField("Décélération maximale", maxDeceleration));
        this.addFormField(new FormField("Longueur", length));
        this.addFormField(new FormField("Quantité", quantity));
        this.addFormField(new FormField("", submit));
        
        this.setPadding(new Insets(10));
    }
    
    @Override
    public void performSubmitAction() {
        if(formValidator.validate()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setMaxSpeed(Integer.valueOf(maxSpeed.getText()));
            vehicle.setMaxAcceleration(Integer.valueOf(maxAcceleration.getText()));
            vehicle.setMaxDeceleration(Integer.valueOf(maxDeceleration.getText()));
            vehicle.setLength(Integer.valueOf(length.getText()));
            
            MainController.getInstance().performAddVehicle(vehicle, Integer.valueOf(quantity.getText()).intValue());
            informationLabel.setText(formValidator.getSuccess());
        } else {
            informationLabel.setText(formValidator.getError());
        }
    }
    
    /**
     * Get max speed field
     * @return Max speed field
     */
    public TextField getMaxSpeed() {
        return maxSpeed;
    }
    
    /**
     * Get max acceleration field
     * @return Max acceleration field
     */
    public TextField getMaxAcceleration() {
        return maxAcceleration;
    }
    
    /**
     * Get max deceleration field
     * @return Max deceleration field
     */
    public TextField getMaxDeceleration() {
        return maxDeceleration;
    }
    
    /**
     * Get vehicle length
     * @return Vehicle length
     */
    public TextField getLength() {
        return length;
    }
    
    /**
     * Get vehicle quantity
     * @return Vehicle quantity
     */
    public TextField getQuantity() {
        return quantity;
    }
}
