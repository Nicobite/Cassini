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
import org.insa.core.enums.Severity;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Collision
 * Collision between 2 vehicles 
 */
public class Collision {
   /**
    * vehicule who is responsible for the accident
    */ 
    private Vehicle guilty;
    /**
     * victim of the collision
     */
    private Vehicle victim;
    /**
     * severity of the accident
     */
    private Severity severity;
    /**
     * position of the accident
     */
    private VehiclePosition position;
    /**
     * time of the accident
     */
    private int time;
    /**
     * viewed or not
     */
    private boolean viewed;

    public Collision() {
    }

    public Vehicle getGuilty() {
        return guilty;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Vehicle getVictim() {
        return victim;
    }

    public boolean isViewed() {
        return viewed;
    }

    public VehiclePosition getPosition() {
        return position;
    }
    
    public void setGuilty(Vehicle guilty) {
        this.guilty = guilty;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public void setVictim(Vehicle victim) {
        this.victim = victim;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
    

    public void setPosition(VehiclePosition position) {
        this.position = position;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    
}
