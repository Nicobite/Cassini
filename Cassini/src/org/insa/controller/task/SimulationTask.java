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
package org.insa.controller.task;

import java.util.TimerTask;
import org.insa.core.vehicle.Vehicle;
import org.insa.model.Model;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class SimulationTask
 * Execute simulation logic
 */
public class SimulationTask extends TimerTask {
    /**
     * reference of the simulation model
     */
    private final Model model;
    
    /**
     * Constructor
     * @param model Link to general model 
     */
    public SimulationTask(Model model){
        this.model = model;
    }
    
    @Override
    public void run() {
        updateTrafficLights();
        updateDrivings();
        updateView();
        System.out.println("done!");
    }
    
    /**
     * update vehicles position in traffic
     */
    private void updateDrivings(){
        for(Vehicle vehicle : model.getVehiclesModel().getVehicles()){
            //do stuff
        }
    }
    
    /**
     * update view
     */
    private void updateView(){
        
    }
    
    /**
     * update traffic lights
     */
    private void updateTrafficLights(){
        
    }
}
