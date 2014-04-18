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
import org.insa.core.driving.Vehicle;
import org.insa.core.driving.VehiclePosition;
import org.insa.core.enums.Action;
import org.insa.core.roadnetwork.Lane;
import org.insa.model.Model;
import org.insa.controller.* ;

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
        System.out.println("Init");
        putVehiclesInDriving();
        System.out.println(MainController.getInstance().getModel().getNbDrivingVehicles());
        updateTrafficLights();
        updateDrivings();
        updateView();
        //System.out.println("done!");
    }
    
    /**
     * put vehicles in driving (one by one)
     */
    public void putVehiclesInDriving(){
        if(model.getNbDrivingVehicles()<model.getNbVehicles()){
            Vehicle veh = model.getVehiclesModel().getVehicles().get(model.getNbDrivingVehicles());
            veh.getDriving().setAction(Action.ACCELERATE);
            //first lane of first section of first road of the road network
            Lane lane = model.getRoadModel().getRoads().get(0).getSections().get(0).getForwardLanes().get(0);
            veh.getDriving().setPosition(new VehiclePosition(lane, 0));
            model.getDrivingVehiclesModel().addVehicle(veh);
            System.err.println(model.getNbDrivingVehicles());
        }
    }
    /**
     * update vehicles position in traffic
     */
    private void updateDrivings(){
        int simuStep = 5 ;
        //int simuStep = MainController.getInstance().getSimulationController().getSimulationStep();
        int acceleration ;
        int speed ;
        int distanceDriven ; 
        float tempOffset ; 
        VehiclePosition position ; 
        Lane previousLane, nextLane ;
        
        for(Vehicle vehicle : model.getDrivingVehiclesModel().getVehicles()){
            /**
             * getDriving
            */
            acceleration = 0 ;
            speed = 0 ; 
            if (vehicle.getDriving().getAction() == Action.STOP){
                vehicle.getDriving().setAction(Action.ACCELERATE);
            }
            if (vehicle.getDriving().getSpeed() > vehicle.getMaxSpeed()){
                vehicle.getDriving().setAction(Action.GO_STRAIGHT); // RUN ?
            }
            
            // Influence of the decision on the acceleration
            if (vehicle.getDriving().getAction() == Action.ACCELERATE){
                acceleration = vehicle.getMaxAcceleration() ;
                vehicle.getDriving().setAcceleration(acceleration) ; 
            }
            
            if (vehicle.getDriving().getAction() == Action.GO_STRAIGHT){
                acceleration = 0 ;
                vehicle.getDriving().setAcceleration(acceleration); 
            }
            
            // Update speed
            speed = acceleration * simuStep ;  // check unité
            vehicle.getDriving().setSpeed(speed) ; 
            
            // Update position
            if (speed != 0 ){
                distanceDriven = speed * simuStep ; // check unités
                
                position = vehicle.getDriving().getPosition() ;
                if (position == null){
                    System.out.println("Position null error."); 
                }else if(position.getLane().getSection()== null ){
                    System.out.println("Erreur section.");   
                }
                /*
                 * convertir ?? tout en float ?? tout en int ???
                 * Décision à prendre !!!
                 */
                tempOffset = position.getOffset() + (float)distanceDriven ;
                
                
                
                if (tempOffset > position.getLane().getSection().getLength()){
                    // compute new offset on the next lane 
                    previousLane = position.getLane() ; 
                    nextLane = previousLane.getTransitions().
                                                    get(0).getTargetLane() ;
                    tempOffset = tempOffset - 
                            previousLane.getSection().getLength() ;
                    
                    // remove the vehicle from the previous lane
                    previousLane.getVehicles().remove(vehicle) ;
                    
                    // add it to the new lane 
                    nextLane.getVehicles().add(vehicle) ; 
                    
                    //in that case choose the lane (first in the array for now)
                    position.setLane(nextLane);
                    

                }
                position.setOffset(tempOffset);
            }
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
