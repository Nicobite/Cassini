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
package org.insa.model.items;

import java.util.ArrayList;
import org.insa.core.enums.IncidentType;
import org.insa.core.trafficcontrol.Collision;
import org.insa.core.trafficcontrol.Congestion;
import org.insa.core.trafficcontrol.Incident;
import org.insa.core.trafficcontrol.TrafficLight;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class ControlUnitsModel
 * trafic controller and regulator (rafic lights management, incident dectection and mangement,...)
 */
public class ControlUnitsModel{
    /**
     * All the incidents occured in the simulation
     */
    private ArrayList<Incident>incidents;
    
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
    
    public ControlUnitsModel(){
        incidents = new ArrayList<>();
        collisions = new ArrayList<>();
        trafficLights = new ArrayList<>();
        congestions = new ArrayList<>();
    }
    
    //-----------Getters and setters-----------------
    public ArrayList<Collision> getCollisions() {
        return collisions;
    }
    
    public void setCollisions(ArrayList<Collision> collisions) {
        this.collisions = collisions;
    }
    
    public ArrayList<Incident> getAllIncidents() {
        return incidents;
    }
    
    public ArrayList<Incident> getDirectionIncidents() {
        ArrayList<Incident> result = new ArrayList<>();
        for(Incident i : this.incidents){
            if(i.getIncident() == IncidentType.WRONG_DIRECTION){
                result.add(i);
            }
        }
        return result;
    }
    public ArrayList<Incident> getSpeedLimitIncidents() {
        ArrayList<Incident> result = new ArrayList<>();
        for(Incident i : this.incidents){
            if(i.getIncident() == IncidentType.WRONG_SPEED_LIMIT){
                result.add(i);
            }
        }
        return result;
    }
    public ArrayList<Incident> getPriorityIncidents() {
        ArrayList<Incident> result = new ArrayList<>();
        for(Incident i : this.incidents){
            if(i.getIncident() == IncidentType.WRONG_PRIORITY){
                result.add(i);
            }
        }
        return result;
    }
    public ArrayList<Incident> getStopIncidents() {
        ArrayList<Incident> result = new ArrayList<>();
        for(Incident i : this.incidents){
            if(i.getIncident() == IncidentType.WRONG_STOP){
                result.add(i);
            }
        }
        return result;
    }
    public void setIncidents(ArrayList<Incident> incidents) {
        this.incidents = incidents;
    }
    
    public ArrayList<TrafficLight> getTrafficLights() {
        return trafficLights;
    }
    
    public void setTrafficLights(ArrayList<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
    }
    
    public ArrayList<Congestion> getCongestions() {
        return congestions;
    }
    
    public void setCongestions(ArrayList<Congestion> congestions) {
        this.congestions = congestions;
    }
    
    
    //-----------Add and remove elements-----------------
    public void addCollision(Collision c){
        this.collisions.add(c);
    }
    public void removeCollision(Collision c){
        this.collisions.remove(c);
    }
    
    public void addIncident(Incident i){
        this.incidents.add(i);
    }
    public void removeIncident(Incident i){
        this.incidents.remove(i);
    }
    
    public void addTrafficLight(TrafficLight t){
        this.trafficLights.add(t);
    }
    public void removeTrafficLight(TrafficLight t){
        this.trafficLights.remove(t);
    }
    
    public void addCongestion(Congestion c){
        this.congestions.add(c);
    }
    public void removeCongestion(Congestion c){
        this.congestions.remove(c);
    }
}
