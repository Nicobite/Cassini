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
import org.insa.core.vehicle.Vehicle;
import org.simpleframework.xml.Element;
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
     * max vehicles in this lane
     */
    private int capacity;
    /**
     * list of vehicle in this lane
     */
    private ArrayList<Vehicle> vehicles;
    /**
     * section of this lane
     */
    private Section section;
    
    /**
     * possible movement from current lane
     * i.e All lanes visibles from current lane
     */
    @Element
    private Transition transition;
    
    
    public Lane(){
        this.vehicles = new ArrayList<>();
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
    
    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }
    
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
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
}
