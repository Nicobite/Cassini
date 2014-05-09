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
package org.insa.controller.task;

import java.util.Random;
import java.util.TimerTask;
import org.insa.controller.MainController;
import org.insa.core.driving.Driving;
import org.insa.core.driving.Vehicle;
import org.insa.core.driving.VehiclePosition;
import org.insa.core.enums.Decision;
import org.insa.core.enums.IncidentType;
import org.insa.core.enums.MissionStatus;
import org.insa.core.roadnetwork.Lane;
import org.insa.core.trafficcontrol.Incident;
import org.insa.model.Model;
import org.insa.view.graphicmodel.GraphicSection;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class SimulationTask
 * Execute simulation logic
 */
public class SimulationTask extends TimerTask {
    /**
     * reference of the simulation model
     */
    private final Model model;
    
    /**
     * simulation step (for vehicle position and speed updates)
     */
    private final int simuStep;
    
    private final boolean debug;
    
    /**
     * Constructor
     * @param model Link to general model
     * @param simuStep
     * @param debug
     */
    public SimulationTask(Model model, int simuStep, boolean debug){
        this.model = model;
        this.simuStep = simuStep;
        this.debug = debug;
    }
    
    
    @Override
    public void run() {
        //put vehicles into traffic until all vehicles are driving
        putVehiclesInDriving();
        
        //update the traffic lights
        updateTrafficLights();
        
        //update vehicle driving (speed, position, decisions, ...)
        updateDrivings();
        
        //report incidents
        reportIncidents();
        
        //update the GUI in consequence
        updateView();
        
    }
    
    /**
     * put vehicles in driving (one by one)
     */
    public void putVehiclesInDriving(){
        if(!model.getVehiclesModel().getVehicles().isEmpty()){
            GraphicSection section; Lane lane;
            //pick the first vehicle
            Vehicle veh = model.getVehiclesModel().getVehicles().remove(0);
            veh.getDriving().setDecision(Decision.ACCELERATE);
            
            //vehicle chooses a random road from the road network
            if(veh.hasMission()){
                lane = veh.getMission().getInitialLane();
                 
            }
            else{
                int indice = new Random().nextInt(model.getRoadModel().getRoads().size());
                section = model.getRoadModel().getRoads().get(indice).getFirstSection();
                   lane = section.getForwardLanes().get(section.getForwardLanes().size()-1).getLane();
            }
            veh.getDriving().setPosition(new VehiclePosition(lane, 0));
            model.getDrivingVehiclesModel().addVehicle(veh);
            if(debug)
                System.out.println("nb vehicles currently driving :"+model.getNbDrivingVehicles());
        }
    }
    
    /**
     * update vehicles position in traffic
     */
    private void updateDrivings(){
        Vehicle vehicle;
        for(int i = 0; i< model.getNbDrivingVehicles(); i++){
            vehicle = model.getDrivingVehiclesModel().getVehicles().get(i);
            // Make decision
            vehicle.makeDecision();
            
            // Execute decision
            vehicle.executeDecision();
            
            // Update speed
            vehicle.updateSpeed(simuStep);
            
            // Update position
            vehicle.updatePosition(simuStep);
            
            // Remove vehicles which reached their destination from the simulation
            if(vehicle.getDriving().getDecision() == Decision.OFF){
                model.getDrivingVehiclesModel().removeVehicle(vehicle);
                if(vehicle.hasMission()){
                    vehicle.getMission().setStatus(MissionStatus.COMPLETED);
                    System.out.println("Mission successful");
                }
                else{
                    model.getVehiclesModel().addVehicle(vehicle);
                }
            }
            
            if(debug)
                System.out.println("Vehicle numero "+i+": acc : "+vehicle.getDriving().getAcceleration()+
                        ",vit = "+vehicle.getDriving().getSpeed()+
                        ",position "+vehicle.getDriving().getPosition());
        }
    }
    
    /**
     * update view
     */
    private void updateView(){
        MainController.getInstance().performRepaintVehicles();
    }
    
    /**
     * update traffic lights
     */
    private void updateTrafficLights(){
        
    }
    
    /**
     * report driving
     */
    private void reportIncidents() {
        Driving driving;
        Incident incident;
        for(Vehicle vhc : model.getDrivingVehiclesModel().getVehicles()){
            driving = vhc.getDriving();
            //speed limit
            if(driving.getSpeed() > driving.getPosition().getLane().getGraphicLane().getSection().getSection().getMaxSpeed()){
                incident = new Incident(vhc);
                incident.setIncident(IncidentType.WRONG_SPEED_LIMIT);
                model.getControlUnitsModel().addIncident(incident);
            }  
        }
    }
}
