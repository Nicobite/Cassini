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
package org.insa.view.panel;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.insa.controller.MainController;
import org.insa.core.vehicle.Vehicle;
import org.insa.view.form.VehiclesForm;

/**
 *
 * @author Thomas Thiebaud
 */
public class VehiclesPanel extends BorderPane {
    
    private TableView<Vehicle> table = new TableView<Vehicle>();
    
    /**
     * Default constructor
     */
    public VehiclesPanel() {
        this.setTop(new VehiclesForm());

        TableColumn maxSpeed = new TableColumn("Vitesse maximale");
        maxSpeed.setCellValueFactory(new PropertyValueFactory<Vehicle,Integer>("maxSpeed"));
        maxSpeed.prefWidthProperty().bind(table.widthProperty().divide(4));
        
        TableColumn maxAcceleration = new TableColumn("Accélération maximale");
        maxAcceleration.setCellValueFactory(new PropertyValueFactory<Vehicle,Integer>("maxAcceleration"));
        maxAcceleration.prefWidthProperty().bind(table.widthProperty().divide(4));
        
        TableColumn maxDeceleration = new TableColumn("Décélération maximale");
        maxDeceleration.setCellValueFactory(new PropertyValueFactory<Vehicle,String>("maxDeceleration"));
        maxDeceleration.prefWidthProperty().bind(table.widthProperty().divide(4));
        
        TableColumn length = new TableColumn("Longueur");
        length.setCellValueFactory(new PropertyValueFactory<Vehicle,String>("length"));
        length.prefWidthProperty().bind(table.widthProperty().divide(4));
        
        table.setItems(FXCollections.observableList(MainController.getInstance().getModel().getVehiclesModel().getVehicles()));
        table.getColumns().addAll(maxSpeed,maxAcceleration, maxDeceleration, length);

        this.setCenter(table);
    }
    
    /**
     * Update table data by asking the controller for new data
     */
    public void performUpdateData(ArrayList<Vehicle> vehicles) {
        table.setItems(FXCollections.observableList(vehicles));
    }
}
