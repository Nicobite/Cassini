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

import org.insa.core.driving.Vehicle;
import org.insa.core.driving.VehiclePosition;
import org.insa.core.enums.IncidentType;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Incident
 */
public class Incident {
    /**
     * incident type
     */
    private IncidentType incident;
    /**
     * vehicle responsible for the incident
     */
    private Vehicle vehicle;
    /**
     * place of the incident
     */
    private VehiclePosition position;
    /**
     * viewed or not 
     */
    private boolean viewed;

    public Incident(Vehicle vhc) {
        this.position = vhc.getDriving().getPosition();
        this.vehicle = vhc;
        this.viewed = false;
    }

    //-----------Getters and setters ---------
    public IncidentType getIncident() {
        return incident;
    }

    public void setIncident(IncidentType incident) {
        this.incident = incident;
    }

    public VehiclePosition getPosition() {
        return position;
    }

    public void setPosition(VehiclePosition position) {
        this.position = position;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    
}
