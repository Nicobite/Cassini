/*
* Copyright 2014 Juste Abel Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
package org.insa.model.items;

import java.util.ArrayList;
import org.insa.core.enums.IncidentType;
import org.insa.controller.MainController;
import org.insa.core.trafficcontrol.Collision;
import org.insa.core.trafficcontrol.Congestion;
import org.insa.core.trafficcontrol.Incident;
import org.insa.core.trafficcontrol.TrafficLight;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class ControlUnitsModel
 * trafic controller and regulator (rafic lights management, incident dectection and mangement,...)
 */
public class ControlUnitsModel{
    /**
     * All the incidents occured in the simulation
     */
    private ArrayList<Incident> incidents;
    
    /**
     * All the accidents
     */
    private ArrayList<Collision> collisions;
    /**
     * All the congestions
     */
    private ArrayList<Congestion> congestions;
    
    /**
     * All the traffic lights
     */
    private ArrayList<TrafficLight> trafficLights;
    
    /**
     * Default constructor
     */
    public ControlUnitsModel() {
        incidents = new ArrayList<>();
        collisions = new ArrayList<>();
        trafficLights = new ArrayList<>();
        congestions = new ArrayList<>();
    }
    
    /**
     * Get traffic light number
     * @return Number of traffic lights
     */
    public int getTrafficLightNumber() {
        return trafficLights.size();
    }
    
    /**
     * Add a collision
     * @param c Collision to add
     */
    public void addCollision(Collision c){
        this.collisions.add(c);
        MainController.getInstance().performAddCollision(c);
    }
    
    /**
     * Remove a collision
     * @param c Collision to remove
     */
    public void removeCollision(Collision c){
        this.collisions.remove(c);
    }
    
    /**
     * Add an incident
     * @param i Incident to remove
     */
    public void addIncident(Incident i){
        this.incidents.add(i);
        MainController.getInstance().performAddIncident(i);
    }
    
    /**
     * Remove an incident
     * @param i Incident to remove
     */
    public void removeIncident(Incident i){
        this.incidents.remove(i);
    }
    
    /**
     * Add a traffic ligth
     * @param t Traffic light to add
     */
    public void addTrafficLight(TrafficLight t){
        this.trafficLights.add(t);
    }
    
    /**
     * Remove a traffic light
     * @param t Traffic light to remove
     */
    public void removeTrafficLight(TrafficLight t){
        this.trafficLights.remove(t);
    }
    
    /**
     * Add a congestion
     * @param c Congestion to add
     */
    public void addCongestion(Congestion c){
        this.congestions.add(c);
        MainController.getInstance().performAddCongestion(c);
    }
    
    /**
     * Remove a congestion 
     * @param c Congestion to remove
     */
    public void removeCongestion(Congestion c){
        this.congestions.remove(c);
    }
    
    /**
     * Get direction incidents
     * @return Direction incidents
     */
    public ArrayList<Incident> getDirectionIncidents() {
        ArrayList<Incident> result = new ArrayList<>();
        for(Incident i : this.incidents){
            if(i.getIncidentType() == IncidentType.WRONG_DIRECTION){
                result.add(i);
            }
        }
        return result;
    }
    
    /**
     * Get speed limit incidents
     * @return Speed limit incidents
     */
    public ArrayList<Incident> getSpeedLimitIncidents() {
        ArrayList<Incident> result = new ArrayList<>();
        for(Incident i : this.incidents){
            if(i.getIncidentType() == IncidentType.WRONG_SPEED_LIMIT){
                result.add(i);
            }
        }
        return result;
    }
    
    /**
     * Get priority incidents
     * @return Priority incidents
     */
    public ArrayList<Incident> getPriorityIncidents() {
        ArrayList<Incident> result = new ArrayList<>();
        for(Incident i : this.incidents){
            if(i.getIncidentType() == IncidentType.WRONG_PRIORITY){
                result.add(i);
            }
        }
        return result;
    }
    
    /**
     * Get stop incidents
     * @return Stop incidents
     */
    public ArrayList<Incident> getStopIncidents() {
        ArrayList<Incident> result = new ArrayList<>();
        for(Incident i : this.incidents){
            if(i.getIncidentType() == IncidentType.WRONG_STOP){
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Get incidents
     * @return Incidents
     */
    public ArrayList<Incident> getIncidents() {
        return incidents;
    }

    /**
     * Get collisions
     * @return Collisions
     */
    public ArrayList<Collision> getCollisions() {
        return collisions;
    }

    /**
     * Get congestions
     * @return Congestions 
     */
    public ArrayList<Congestion> getCongestions() {
        return congestions;
    }

    /**
     * Get traffic lights
     * @return Traffic lights
     */
    public ArrayList<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    /**
     * Set incidents
     * @param incidents New incidents 
     */
    public void setIncidents(ArrayList<Incident> incidents) {
        this.incidents = incidents;
    }

    /**
     * Set collisions
     * @param collisions New collisions 
     */
    public void setCollisions(ArrayList<Collision> collisions) {
        this.collisions = collisions;
    }

    /**
     * Set congestions
     * @param congestions New congestions 
     */
    public void setCongestions(ArrayList<Congestion> congestions) {
        this.congestions = congestions;
    }

    /**
     * Set traffic lights
     * @param trafficLights New traffic lights 
     */
    public void setTrafficLights(ArrayList<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
    }
}
