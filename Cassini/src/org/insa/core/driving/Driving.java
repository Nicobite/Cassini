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
package org.insa.core.driving;

import org.insa.core.enums.Decision;
import org.simpleframework.xml.Element;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Driving
 * Vehicle movement
 */
public class Driving {
    /**
     * vehicle speed
     */
    private float speed;
    
    /**
     * acceleration
     */
    private int acceleration;
    
    /**
     * distance between current vehicle and it's leader
     */
    private float distanceToLeader;
    
    /**
     * time spent in driving (from the begining or after a STOP)
     * Number of simulation clock
     */
    private int time;
    
    /**
     * vehicle position in the current lane
     */
    private VehiclePosition position;
    
    /**
     * Decision of the vehicle (run, stop, change lane, ...)
     */
    private Decision decision;
    
    /**
     * vehicle's driving behavior
     */
    @Element
    private Behavior behavior;
    
    public Driving() {
        acceleration = 0;
        distanceToLeader = Float.MAX_VALUE;
        speed = 0;
    }
    
        
    /**
     * when vehicle is driving
     */
    public void updateDriving(){
        
    }
    /**
     * when vehicle starts driving
     */
    public void startDriving(){
        
    }
    
    /**
     * when vehicle stops driving
     */
    public void stopDriving(){
        
    }
    /**
     * when vehicles decides whether to accelerate/decelerate/change lane...
     * @param action the decision taken
     */
    public void makeDecision(Decision action){
        
    }
    
    /* getters and setters */
    
    public float getSpeed() {
        return speed;
    }
    
    public void setSpeed(float speed) {
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
    
    public Decision getDecision() {
        return decision;
    }
    
    public void setDecision(Decision decision) {
        this.decision = decision;
    }
    
    public Behavior getBehavior() {
        return behavior;
    }
    
    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    
    
}
