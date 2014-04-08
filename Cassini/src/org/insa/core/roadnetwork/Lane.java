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
package org.insa.core.roadnetwork;

import java.util.ArrayList;
import org.insa.core.enums.Direction;
import org.insa.core.driving.Vehicle;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Lane
 * A lane is a container of vehicles and is located inside a section.
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class Lane {
    /**
     * section of this lane
     */
    private Section section;
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
    private ArrayList<Transition> transitions;
    
    
    public Lane(){
        this.vehicles = new ArrayList<>();
        this.transitions = new ArrayList<>();
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
    
    public ArrayList<Transition>  getTransitions() {
        return transitions;
    }

    public void setTransition(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }
    
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    public void addTransition(Transition t){
        this.transitions.add(t);
    }
    public void removeTransition(Transition t){
        this.transitions.remove(t);
    }
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    public void addVehicle(Vehicle v){
        this.vehicles.add(v);
    }
    public void removeVehicle(Vehicle v){
        this.vehicles.remove(v);
    }
    public boolean containsVehicle(Vehicle v){
        return this.vehicles.contains(v);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
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
}
