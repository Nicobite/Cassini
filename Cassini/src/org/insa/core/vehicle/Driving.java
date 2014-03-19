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
package org.insa.core.vehicle;

import org.insa.core.enums.Action;
import org.simpleframework.xml.Element;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Driving
 * Vehicle movement
 */
public class Driving {
    /**
     * vehicle speed
     */
    private int speed;
    
    /**
     * acceleration
     */
    private int acceleration;
    
    /**
     * distance between current vehicle and it's leader
     */
    private float distanceToLeader;
    
    /**
     * vehicle position in the current lane
     */
    private VehiclePosition position;
    
    /**
     * Action of the vehicle (run, stop, change lane, ...)
     */
    private Action action;
    
    /**
     * vehicle's driving behavior
     */
    @Element
    private Behavior behavior;
    
    public Driving() {
    }
    
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public int getAcceleration() {
        return acceleration;
    }
    
    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }
    
    public float getDistanceToLeader() {
        return distanceToLeader;
    }
    
    public void setDistanceToLeader(float distanceToLeader) {
        this.distanceToLeader = distanceToLeader;
    }
    
    public VehiclePosition getPosition() {
        return position;
    }
    
    public void setPosition(VehiclePosition position) {
        this.position = position;
    }
    
    public Action getAction() {
        return action;
    }
    
    public void setAction(Action action) {
        this.action = action;
    }
    
    public Behavior getBehavior() {
        return behavior;
    }
    
    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }
    
}
