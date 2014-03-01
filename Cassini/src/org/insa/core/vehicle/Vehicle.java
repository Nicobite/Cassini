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

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Vehicle
 */
@Root
public class Vehicle {
    /**
     * vehicle max speed
     */
    @Attribute
    private int maxSpeed;
    
    /**
     * max acceleration
     */
    @Attribute(name="maxAcc")
    private int maxAcceleration;
    
    /**
     * max deceleration (braking)
     */
    @Attribute(name="maxDec")
    private int maxDeceleration;
    
    /**
     * vehicle length
     */
    @Element(required = false)
    private int length;
    
    public Vehicle(){
      super();  
    }
    public void setMaxAcceleration(int maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }
    
    public int getMaxAcceleration() {
        return maxAcceleration;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public int getLength() {
        return length;
    }
    
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public int getMaxSpeed() {
        return maxSpeed;
    }
    
    public void setMaxDeceleration(int maxDeceleration) {
        this.maxDeceleration = maxDeceleration;
    }
    
    public int getMaxDeceleration() {
        return maxDeceleration;
    }
    
    
}
