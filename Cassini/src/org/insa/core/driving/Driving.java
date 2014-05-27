/*
* Copyright 2014 Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
    @Element(required = false)
    private Behavior behavior;
    
    /**
     * default constructor
     */
    public Driving() {
        acceleration = 0;
        distanceToLeader = Float.MAX_VALUE;
        speed = 0;
    }
  
    /**
     * Called when vehicle is driving
     */
    public void updateDriving(){
        
    }
    /**
     * Called when vehicle starts driving
     */
    public void startDriving(){
        
    }
    
    /**
     * Called when vehicle stops driving
     */
    public void stopDriving(){
        
    }
    
    /**
     * Called when vehicles decides whether to accelerate/decelerate/change lane...
     * @param action the decision taken
     */
    public void makeDecision(Decision action){
        
    }

    /**
     * Get speed
     * @return Speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Get acceleration
     * @return Acceleration
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     * Get distance to leader
     * @return Distance to leader
     */
    public float getDistanceToLeader() {
        return distanceToLeader;
    }

    /**
     * Get time
     * @return Time
     */
    public int getTime() {
        return time;
    }

    /**
     * Get position
     * @return Position
     */
    public VehiclePosition getPosition() {
        return position;
    }

    /**
     * Get decision
     * @return Descision
     */
    public Decision getDecision() {
        return decision;
    }

    /**
     * Get behavior
     * @return Behavior
     */
    public Behavior getBehavior() {
        return behavior;
    }

    /**
     * Set speed
     * @param speed New speed 
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Set acceleration
     * @param acceleration New acceleration 
     */
    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Set distance to leader
     * @param distanceToLeader New distance to leader 
     */
    public void setDistanceToLeader(float distanceToLeader) {
        this.distanceToLeader = distanceToLeader;
    }

    /**
     * Set time
     * @param time New time 
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Set position
     * @param position New position
     */
    public void setPosition(VehiclePosition position) {
        this.position = position;
    }

    /**
     * Set decision
     * @param decision New decision 
     */
    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    /**
     * Set behavior
     * @param behavior New behavior 
     */
    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }
}
