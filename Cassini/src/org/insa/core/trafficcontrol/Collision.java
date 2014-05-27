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
import org.insa.core.enums.Severity;
import org.insa.view.graphicmodel.GraphicCollision;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
    
    private final GraphicCollision gCollision;

    /**
     * Constructor
     * @param guilty Guilty vehicle
     * @param victim Victim vehicle
     * @param severity Severity
     */
    public Collision(Vehicle guilty, Vehicle victim, Severity severity) {
        this.guilty = guilty;
        this.victim = victim;
        this.severity = severity;
        this.position = new VehiclePosition(victim.getDriving().getPosition());
        gCollision = new GraphicCollision(this);
    }

    /**
     * Get guilty
     * @return Guilty vehicle
     */
    public Vehicle getGuilty() {
        return guilty;
    }

    /**
     * Get victim
     * @return Victim vehicle
     */
    public Vehicle getVictim() {
        return victim;
    }

    /**
     * Get severity
     * @return Severity
     */
    public Severity getSeverity() {
        return severity;
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
     * Get graphic collision
     * @return Graphic collision
     */
    public GraphicCollision getGraphicCollision() {
        return gCollision;
    }

    /**
     * Set guilty
     * @param guilty New guilty 
     */
    public void setGuilty(Vehicle guilty) {
        this.guilty = guilty;
    }

    /**
     * Set victim
     * @param victim New vicim 
     */
    public void setVictim(Vehicle victim) {
        this.victim = victim;
    }

    /**
     * Set severity
     * @param severity New sevirity 
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
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
        final Collision other = (Collision) obj;
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (this.time != other.time) {
            return false;
        }
        return true;
    }
}
