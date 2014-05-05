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

import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
import org.insa.core.enums.Decision;
import org.insa.core.roadnetwork.Lane;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Vehicle
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class Vehicle {
    /**
     * vehicle max speed (km/h)
     */
    @Attribute()
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
    
    /*--------------------- driving logic ---------------------*/
    
    /**
     * make driving decision
     */
    public void makeDecision(){
        if (this.getDriving().getSpeed() > this.getMaxSpeed()){
            this.getDriving().setDecision(Decision.GO_STRAIGHT);
        }
        /*
        if (myPosition close to next vehicle (inf to safetyDistance)){
            this.getDriving().setDecesion(Decision.DECELERATE) ; 
        }
        */
    }
    
    /**
     * execute driving decision
     */
    public void executeDecision(){
        switch(this.driving.getDecision()){
            case STOP :
                // if the decision of the vehicle is to stop, we dont have to change the decision but 
                // the acceleration. it's not the same decision
                //this.driving.setDecision(Decision.DECELARATE);
                
                // here the vehicle will decelerate and we have to change the action
                // in the other function that make the vehicle stops when it reaches 
                // 0km/h
                if (this.getDriving().getSpeed() == 0.0){
                    this.getDriving().setAcceleration(0);
                }
                else
                {
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
      * update speed
     * @param simuStep
      */
      public void updateSpeed(int simuStep){
          float speed ;
          speed = this.driving.getSpeed() + this.driving.getAcceleration()*simuStep/1000;
          speed = min(speed, this.getMaxSpeed());
          speed = max(speed, 0);
          this.getDriving().setSpeed(speed);
      }
      
      public void updatePosition(int simuStep){
          if(this.driving.getSpeed() != 0){
                VehiclePosition position = this.getDriving().getPosition() ;
                System.out.println(this.driving.getSpeed()*(float)simuStep/1000f) ;
                float distance = position.getOffset() + this.driving.getSpeed()*(float)simuStep/1000f;
                
                //check whether we reach the end of the current section
                float laneLength = position.getLane().getSection().getLength();
                if(distance < laneLength){
                    position.setOffset(distance);
                }
                else {
                    // compute new offset on the next lane (in the next section)
                    position.setOffset(distance - laneLength);
                    //go to the next section
                    Lane previousLane = position.getLane() ;
                    
                    if(!previousLane.hasTransition()){
                        this.driving.setDecision(Decision.OFF); 
                    }
                    else{
                        int indice = new Random().nextInt(previousLane.getNextLanes().size());
                        Lane nextLane = previousLane.getNextLanes().get(indice).getTargetLane(); 
                        System.err.println("indice = "+indice);
                        // remove the vehicle from the previous lane
                        previousLane.getVehicles().remove(this) ;
                        
                        // add it to the new lane
                        nextLane.getVehicles().add(this) ;
                        
                        //in that case choose the lane (first in the array for now)
                        position.setLane(nextLane);   
                        
                    }
                }
            }
      }
      
      private float max(float a, float b){
          return (a>b?a:b);
      }
      private float min(float a, float b){
          return a<b?a:b;
      }
      
}
