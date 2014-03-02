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
package org.insa.controller;

import org.insa.model.ControlUnitsModel;
import org.insa.model.DrivingModel;
import org.insa.model.VehiclesModel;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui &  Thomas Thiebaud
 * Class TrafficController
 * Manage trafic : priority, trafic lights, and vehicle movement
 */
public class TrafficController {
    /**
     * vehicles in the model
     */
    private VehiclesModel vehicles;
    
    /**
     * vehicle in the traffic
     */
    private DrivingModel drivings;
    
    /**
     * traffic control unit : traffic light management, priority management,
     * incidents dectection (accident, congestion,...)
     */
    private ControlUnitsModel controlUnits;
    
    /**
     * simulation step
     */
    private int simulationStep;
    
    /**
     * constructor
     */
    public TrafficController() {
        super();
    }
    
    public void setDriving(DrivingModel drivingModel) {
        this.drivings = drivingModel;
    }
    
    public DrivingModel getDriving() {
        return drivings;
    }
    
    public void setSimulationStep(int simulationStep) {
        this.simulationStep = simulationStep;
    }
    
    public int getSimulationStep() {
        return simulationStep;
    }
    
    public void setVehicles(VehiclesModel vehiclesModel) {
        this.vehicles = vehiclesModel;
    }
    
    public VehiclesModel getVehicles() {
        return vehicles;
    }

    public ControlUnitsModel getControlUnits() {
        return controlUnits;
    }

    public void setControlUnits(ControlUnitsModel controlUnits) {
        this.controlUnits = controlUnits;
    }
    
    
    
    
}
