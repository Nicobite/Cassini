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

import java.util.Objects;
import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
import org.insa.controller.MainController;
import org.insa.core.enums.Decision;
import org.insa.core.enums.StateTrafficLight;
import org.insa.core.enums.TrafficSignaling;
import org.insa.core.roadnetwork.NextLane;
import org.insa.core.roadnetwork.Node;
import org.insa.core.trafficcontrol.TrafficLight;
import org.insa.mission.Mission;
import org.insa.view.graphicmodel.GraphicLane;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Vehicle
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */

public class Vehicle {
    /**
     * vehicle max speed (km/h)
     */
    @Attribute
    private SimpleIntegerProperty maxSpeed;
    
    /**
     * max acceleration in km/(h*h)
     */
    @Attribute(name="maxacc")
    private SimpleIntegerProperty maxAcceleration;
    
    /**
     * max deceleration in km/(h*h)
     */
    @Attribute(name="maxdec")
    private SimpleIntegerProperty maxDeceleration;
    
    /**
     * vehicle length
     */
    @Attribute
    private SimpleIntegerProperty length;
    
    /**
     * driving attribute (dynamic of the movement)
     */
    @Element(required = false)
    private Driving driving;
    
    //-----------Mission ---------------
    /**
     * mission assigned to this vehicle
     */
    private Mission mission;
    
    /**
     * whether this vehicle has a trajectory to follow (a mission)
     */
    private boolean hasMission;
    
    //------------------------------------
    
    public Vehicle(){
        super();
        driving = new Driving();
    }
    
    
    /* -----------getters and setters ----------------*/
    
    public void setMaxAcceleration(int maxAcceleration) {
        this.maxAcceleration = new SimpleIntegerProperty(maxAcceleration);
    }
    
    public int getMaxAcceleration() {
        return maxAcceleration.get();
    }
    
    public void setLength(int length) {
        this.length = new SimpleIntegerProperty(length);
    }
    
    public int getLength() {
        return length.get();
    }
    
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = new SimpleIntegerProperty(maxSpeed);
    }
    public int getMaxSpeed() {
        return maxSpeed.get();
    }
    
    public void setMaxDeceleration(int maxDeceleration) {
        this.maxDeceleration = new SimpleIntegerProperty(maxDeceleration);
    }
    
    public int getMaxDeceleration() {
        return maxDeceleration.get();
    }
    
    public Driving getDriving() {
        return driving;
    }
    
    public void setDriving(Driving driving) {
        this.driving = driving;
    }
    
    public void setMission(Mission mission) {
        this.mission = mission;
        this.hasMission = true;
    }
    
    public Mission getMission() {
        return mission;
    }
    
    public void setHasMission(boolean hasMission) {
        this.hasMission = hasMission;
    }
    
    public boolean hasMission(){
        return this.hasMission;
    }
    /*--------------------- driving logic ---------------------*/
    
    /**
     * Compare the speed of the vehicle with the max speed of the road
     * or of the vehicle and set the decision.
     */
    private void testSpeed(){
        float maxSpeedSection = this.getDriving().getPosition().getLane().getGraphicLane().getSection().getSection().getMaxSpeed() ;
        if (maxSpeedSection < this.getMaxSpeed()){
            if (this.getDriving().getSpeed() > maxSpeedSection-5){
                this.getDriving().setSpeed(maxSpeedSection);
                this.getDriving().setDecision(Decision.GO_STRAIGHT);
            }
        }else
        {
            if (this.getDriving().getSpeed() > this.getMaxSpeed()-0.5){
                this.getDriving().setDecision(Decision.GO_STRAIGHT);
            }            
        } 
    }
    /**
     * Return the distance between the vehicle and the next node
     * @param node
     * @return 
     */
    private float distanceToNextNode(Node node){
        float distance ;        
        distance = this.getDriving().getPosition().getLane().getGraphicLane().getSection().getLength() - this.getDriving().getPosition().getOffset() ;        
        return distance ;
    }
    /**
     * Compute the distance needed for the vehicle to stop
     * @return 
     */
    private float distanceToStop(){
        float distance ;
        float speed = this.getDriving().getSpeed() ; 
        float decceleration = this.getMaxDeceleration();
        distance = (speed * speed) / ( 2.0f * decceleration) ;
        return distance ;
    }
    /**
     * Change the decision of the vehicle stop to the next node if he is close enough
     * In the other case the vehicle continues to drive
     * @param node 
     */
    private void stopAtNextNode(Node node){
        if (this.distanceToNextNode(node) < distanceToStop()+15.0f){
            this.getDriving().setDecision(Decision.STOP);
        }
    }
    /**
     * behavior for the vehicle in front of a TrafficLight
     * The vehicle continu until it arrives at the end of the lane where it has to stop
     * @param targetNode 
     */
    private void behaviorAtTrafficLight(Node targetNode){
        TrafficLight light = TrafficLight.fromNode(targetNode) ;
        if (light == null){
            System.out.println("Error TrafficLight null.");
        }else
        {
            switch(light.getState()){
                case RED :
                    stopAtNextNode(targetNode) ; 
                    break ;
                case ORANGE :                                 
                    break ;
                case GREEN : 
                    if (this.getDriving().getSpeed()==0){
                        this.getDriving().setDecision(Decision.ACCELERATE);
                    }else
                    {
                        this.testSpeed();
                    }
                    break ; 
                default : 
                    break ;
            } 
        }
    }
    /**
     * Check if there is a vehicle in front of the considered one
     * @return 
     */
    private Vehicle VehicleInFront(){
        Vehicle vInFront = null ;
        float myOffset = this.getDriving().getPosition().getOffset() ;
        float vOffset ; 
        for (Vehicle v: this.getDriving().getPosition().getLane().getVehicles()){
            vOffset = v.getDriving().getPosition().getOffset() ;
            if (vOffset > myOffset){
                vInFront = v ; 
                break ; 
            }
        }
        return vInFront ; 
    }
    /**
     * Set the decision of the vehicle depending to the speed it is aiming
     */
    private void setDecisionToTargetSpeed(){
        float mySpeed = this.getDriving().getSpeed() ;
        float targetSpeed = this.getDriving().getBehavior().getTargetSpeed() ; 
        if (targetSpeed == 0){
            this.getDriving().setDecision(Decision.STOP);
        } else if (mySpeed > targetSpeed) {
            this.getDriving().setDecision(Decision.DECELARATE);
        } else if (mySpeed < targetSpeed){
            this.getDriving().setDecision(Decision.ACCELERATE);
        } else
        {
            this.getDriving().setDecision(Decision.GO_STRAIGHT) ;
        }
    }        
    
    /**
     * ************************************************************************
     * make driving decision
     */
    public void makeDecision(){
        
        GraphicLane currentLane = this.getDriving().getPosition().getLane().getGraphicLane(); 
        Vehicle vInFront = this.VehicleInFront() ;
        if (vInFront != null){            
            //this.getDriving().getBehavior().setTargetSpeed(vInFront.getDriving().getSpeed());
            //this.setDecisionToTargetSpeed(); 
            testSpeed();
        }
        else
        {
            if (currentLane.hasTransition()){
                NextLane nextLane = this.getDriving().getPosition().getLane().getGraphicLane().getNextLanes().get(0) ;             
                Node targetNode = currentLane.getSection().getSection().getGraphicSection().getTargetNode().getNode();

                switch (targetNode.getSignaling()){
                    case TRAFFIC_LIGHT:
                        this.behaviorAtTrafficLight(targetNode);
                        break ; 
                    case STOP : 
                        stopAtNextNode(targetNode) ;
                        /**
                         * TODO :
                         * Implement the check if there is vehicles on the lane the vehicle is going to
                         * here it this wait for the vehicle to stop and then go ahead 
                         */
                        if (this.getDriving().getSpeed() == 0){
                            this.getDriving().setDecision(Decision.ACCELERATE);
                        }
                    case CROSSING:
                        this.testSpeed();
                        break ;
                    case ROUNDABOUT: 
                        this.testSpeed();
                        break ; 
                    case TURN_LOOP:
                        this.testSpeed();
                        break ;
                    default :
                        this.testSpeed();
                        break;                    
                }
            }
        }
    }
    
    /**
     * execute driving decision
     */
    public void executeDecision(){
        switch(this.driving.getDecision()){
            case STOP :
                if (this.getDriving().getSpeed() == 0.0){
                    this.getDriving().setAcceleration(0);
                }
                else{
                    this.getDriving().setAcceleration(-this.getMaxDeceleration());
                }
                break;
                
            case ACCELERATE :
                this.driving.setAcceleration(this.getMaxAcceleration());
                break;
                
            case DECELARATE :
                this.driving.setAcceleration(-this.getMaxDeceleration());
                break;
                
            case GO_STRAIGHT :
                this.driving.setAcceleration(0);
                break;
            default:
                
        }
    }
    
    /**
     * Update speed
     * @param simuStep
     */
    public void updateSpeed(int simuStep){
        float speed ;
        float scale = 0.50f;
        speed = this.driving.getSpeed() + scale*this.driving.getAcceleration()*simuStep/1000;
        speed = Math.min(speed, this.getMaxSpeed());
        speed = Math.max(speed, 0);
        this.getDriving().setSpeed(speed);
    }
    
    /**
     * Update position
     * @param simuStep
     */
    public void updatePosition(int simuStep){
        float scale =0.50f;
        if(this.driving.getSpeed() != 0) {
            VehiclePosition position = this.getDriving().getPosition() ;
            //System.out.println(this.driving.getSpeed()*(float)simuStep/1000f) ;
            float distance = position.getOffset() + scale*this.driving.getSpeed()*(float)simuStep/1000f;
            
            //check whether we reach the end of the current section
            float laneLength = position.getLane().getGraphicLane().getSection().getLength();
            if(distance < laneLength){
                position.setOffset(distance);
            }
            //go to the next section
            else {
                // compute new offset on the next lane (in the next section)
                position.setOffset(distance - laneLength);
                GraphicLane previousLane = position.getLane().getGraphicLane() ;
                
                boolean destinationReached = this.hasMission() && previousLane.getSection().getSection().isEqualTo(this.mission.getPath().getLastSection().getSection());
                if(!previousLane.hasTransition() || destinationReached ){
                    this.driving.setDecision(Decision.OFF);
                    this.driving.setSpeed(0);
                    this.driving.setAcceleration(0);
                }
                else{
                    int indice=0;
                    //change section : if mission follow the path
                    GraphicLane nextLane;
                    if(this.hasMission){
                        this.mission.updateCurrentSection();
                        nextLane = this.mission.getNextLane(previousLane).getTargetLane();
                    }
                    else{
                        indice = new Random().nextInt(previousLane.getNextLanes().size());
                        nextLane = previousLane.getNextLanes().get(indice).getTargetLane();
                    }
                    
                    
                    // remove the vehicle from the previous lane
                    previousLane.getLane().getVehicles().remove(this) ;
                    
                    // add it to the new lane
                    nextLane.getLane().getVehicles().add(this) ;
                    
                    //in that case choose the lane (first in the array for now)
                    position.setLane(nextLane.getLane());
                    
                }
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vehicle other = (Vehicle) obj;
        if (!Objects.equals(this.maxSpeed, other.maxSpeed)) {
            return false;
        }
        if (!Objects.equals(this.maxAcceleration, other.maxAcceleration)) {
            return false;
        }
        if (!Objects.equals(this.maxDeceleration, other.maxDeceleration)) {
            return false;
        }
        if (!Objects.equals(this.length, other.length)) {
            return false;
        }
        return true;
    }
    
    
}



