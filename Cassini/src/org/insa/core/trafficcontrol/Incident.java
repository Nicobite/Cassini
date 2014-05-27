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

import java.util.Objects;
import org.insa.core.driving.Vehicle;
import org.insa.core.driving.VehiclePosition;
import org.insa.core.enums.IncidentType;
import org.insa.view.graphicmodel.GraphicIncident;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
     * time when the incident take place
     */
    private int time;
    /**
     * viewed or not 
     */
    private boolean viewed;

    private final GraphicIncident gIncident;
    
    /**
     * Constructor
     * @param vhc Vehicle concerned by the incident
     * @param type Incident type
     */
    public Incident(Vehicle vhc, IncidentType type) {
        incident = type;
        this.position = new VehiclePosition(vhc.getDriving().getPosition());
        this.vehicle = vhc;
        this.viewed = false; 
        gIncident = new GraphicIncident(this);
    }

    /**
     * Get incident type
     * @return incident type
     */
    public IncidentType getIncidentType() {
        return incident;
    }

    /**
     * Get vehicle
     * @return Vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Get position
     * @return Position
     */
    public VehiclePosition getPosition() {
        return position;
    }

    /**
     * Get time
     * @return Time
     */
    public int getTime() {
        return time;
    }

    /**
     * Is viewed
     * @return true if viewed, false otherwise
     */
    public boolean isViewed() {
        return viewed;
    }

    /**
     * Get graphic incident
     * @return Graphic incident
     */
    public GraphicIncident getGraphicIncident() {
        return gIncident;
    }

    /**
     * Set incident type
     * @param incident New incident type
     */
    public void setIncidentType(IncidentType incident) {
        this.incident = incident;
    }

    /**
     * Set vehicle
     * @param vehicle New vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Set position
     * @param position New position 
     */
    public void setPosition(VehiclePosition position) {
        this.position = position;
    }

    /**
     * Set time
     * @param time New time
     */
    public void setTime(int time) {
        this.time = time;
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
        final Incident other = (Incident) obj;
        if (this.incident != other.incident) {
            return false;
        }
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (this.time != other.time) {
            return false;
        }
        return true;
    }
}
