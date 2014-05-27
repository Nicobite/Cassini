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
package org.insa.core.trafficcontrol;

import java.util.ArrayList;
import java.util.Objects;
import org.insa.core.driving.Vehicle;
import org.insa.core.roadnetwork.Lane;
import org.insa.view.graphicmodel.GraphicCongestion;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
    
    private final GraphicCongestion gCongestion;

    /**
     * Default constructor
     */
    public Congestion() {
        gCongestion = new GraphicCongestion(this);
        this.vehicles = new ArrayList<>();
        this.lanes = new ArrayList<>();
    }
    
    /**
     * Add a vehicle
     * @param v Vehicle to add
     */
    public void addVehicle(Vehicle v) {
        this.vehicles.add(v);
    }
    
    /**
     * Add a lane
     * @param l Lane to add
     */
    public void addLane(Lane l){
        this.lanes.add(l);
    }
    
    /**
     * Get graphic congestion
     * @return Graphic congestion
     */
    public GraphicCongestion getGraphicCongestion() {
        return gCongestion;
    }

    /**
     * Get lanes
     * @return Lanes list
     */
    public ArrayList<Lane> getLanes() {
        return lanes;
    }

    /**
     * Get time
     * @return Time
     */
    public int getTime() {
        return time;
    }

    /**
     * Get vehicles
     * @return Vehicles list
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    
    /**
     * Is viewed
     * @return true if viewed, false otherwise
     */
    public boolean isViewed() {
        return viewed;
    }
    
    /**
     * Set lanes
     * @param lanes New lanes list
     */
    public void setLanes(ArrayList<Lane> lanes) {
        this.lanes = lanes;
    }

    /**
     * Set vehicles
     * @param vehicles New vehicles list 
     */
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Set viewed
     * @param viewed New viewed boolean 
     */
    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Congestion other = (Congestion) obj;
        if (!Objects.equals(this.lanes, other.lanes)) {
            return false;
        }
        if (this.time != other.time) {
            return false;
        }
        return true;
    }
}
