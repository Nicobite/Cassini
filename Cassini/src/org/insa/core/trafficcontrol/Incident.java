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

import java.util.Objects;
import org.insa.core.driving.Vehicle;
import org.insa.core.driving.VehiclePosition;
import org.insa.core.enums.IncidentType;
import org.insa.core.graphicmodel.GraphicIncident;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
    
    public Incident(Vehicle vhc, IncidentType type) {
        incident = type;
        this.position = new VehiclePosition(vhc.getDriving().getPosition());
        this.vehicle = vhc;
        this.viewed = false; 
        gIncident = new GraphicIncident(this);
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public GraphicIncident getGraphicIncident() {
        return gIncident;
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
