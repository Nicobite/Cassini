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
package org.insa.core.roadnetwork;

import java.util.ArrayList;
import java.util.Objects;
import org.insa.core.driving.Vehicle;
import org.insa.view.graphicmodel.GraphicLane;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Lane
 * A lane is a container of vehicles and is located inside a section.
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */

public class Lane {
    protected GraphicLane gLane;
    
    /**
     * list of vehicle in this lane
     */
    private ArrayList<Vehicle> vehicles;
    
    
    /**
     * Constructor
     * @param gLane Reference to graphic lane
     */
    public Lane(GraphicLane gLane) {
        this.gLane = gLane;
        this.vehicles = new ArrayList<>();
        
    }

    public Lane() {
        this.vehicles = new ArrayList<>();
    }
    
    
    /**
     * Add a vehicle to the vehicles list
     * @param v Vehicle to add
     */
    public void addVehicle(Vehicle v){
        this.vehicles.add(v);
    }
    
    /**
     * Remove a vehicle from the vehicle list
     * @param v Vehicle to remove
     */
    public void removeVehicle(Vehicle v){
        this.vehicles.remove(v);
    }
    
    /**
     * Search a vehicle into the vehicles list
     * @param v vehicle to search
     * @return true if the vehicle exists, false otherwise
     */
    public boolean containsVehicle(Vehicle v){
        return this.vehicles.contains(v);
    }
 
    
    /**
     * Get vehicles
     * @return Vehicles list
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    
    
    
    /**
     * Get graphic lane
     * @return Graphic lane
     */
    public GraphicLane getGraphicLane() {
        return gLane;
    }

    
    
    /**
     * Set vehicles
     * @param vehicles New vehicles list 
     */
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Lane other = (Lane) obj;
        if (!Objects.equals(this.gLane, other.gLane)) {
            return false;
        }
        return true;
    }
    
}
