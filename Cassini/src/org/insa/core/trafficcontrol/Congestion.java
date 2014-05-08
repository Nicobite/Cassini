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
package org.insa.core.trafficcontrol;

import java.util.ArrayList;
import org.insa.core.driving.Vehicle;
import org.insa.core.roadnetwork.Lane;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Congestion
 */
public class Congestion {
    /**
     * Set of vehicle stuck in the congestion
     */
    private ArrayList<Vehicle> vehicles;
    /**
     * set of congested lanes 
     */
    private ArrayList<Lane> lanes;
    /**
     * time when the congestion took place
     */
    private int time;
    /**
     * viewed or not
     */
    private boolean viewed;

    public Congestion() {
        this.vehicles = new ArrayList<>();
        this.lanes = new ArrayList<>();
    }


    public ArrayList<Lane> getLanes() {
        return lanes;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    
    public void setLanes(ArrayList<Lane> lanes) {
        this.lanes = lanes;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isViewed() {
        return viewed;
    }
    
    public void addVehicle(Vehicle v){
        this.vehicles.add(v);
    }
    public void addLane(Lane l){
        this.lanes.add(l);
    }
}
