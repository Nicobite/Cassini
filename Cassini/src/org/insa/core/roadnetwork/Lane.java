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
import org.insa.core.enums.Direction;
import org.insa.core.driving.Vehicle;
import org.insa.view.graphicmodel.GraphicLane;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Lane
 * A lane is a container of vehicles and is located inside a section.
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class Lane {
    protected GraphicLane gLane;
    
    /**
     * direction of this lane
     * FORWARD if same direction as the road and
     * BACKWARD otherwise
     */
    @Attribute
    private Direction direction;
    
    /**
     * list of vehicle in this lane
     */
    private ArrayList<Vehicle> vehicles;
    
    /**
     * possible movement from current lane
     * i.e All lanes visibles from current lane
     */
    @ElementList(required = false)
    private ArrayList<NextLane> nextLanes;
    
    /**
     * Constructor
     * @param gLane Reference to graphic lane
     */
    public Lane(GraphicLane gLane) {
        this.gLane = gLane;
        this.vehicles = new ArrayList<>();
        this.nextLanes = new ArrayList<>();
    }
    
    /**
     * Add a transition to the transitions list
     * @param t Transition to add
     */
    public void addTransition(NextLane t){
        this.nextLanes.add(t);
    }
    
    /**
     * Remove a transition from the transition list
     * @param t Transition to remove
     */
    public void removeTransition(NextLane t){
        this.nextLanes.remove(t);
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
     * Check if the transitions list is not empty
     * @return true if the transitions list is not empty, false otherwise
     */
    public boolean hasTransition(){
        return !this.getNextLanes().isEmpty();
    }
    
    /**
     * Get vehicles
     * @return Vehicles list
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    
    /**
     * Get direction
     * @return Direction
     */
    public Direction getDirection() {
        return direction;
    }
    
    /**
     * Get next lane
     * @return Next lane
     */
    public ArrayList<NextLane> getNextLanes() {
        return nextLanes;
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
    
    /**
     * Set direction
     * @param direction New direction 
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    /**
     * Set transitions
     * @param transitions New transitions list 
     */
    public void setTransition(ArrayList<NextLane> transitions) {
        this.nextLanes = transitions;
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
        if (this.direction != other.direction) {
            return false;
        }
        return true;
    }
    
    /**
     * find the transition to the next lane of a given section
     * @param section
     * @return 
     */
    public NextLane findNextLaneBySection(Section section){
        NextLane result = null;
        for(NextLane transition : this.getNextLanes()){
            if(transition.getTargetLane().getGraphicLane().getSection().getSection().isEqualTo(section)){
               result = transition; 
            }
        }
        return result;
    }
}
