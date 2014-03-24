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

import javafx.beans.property.SimpleIntegerProperty;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Vehicle
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class Vehicle {
    /**
     * vehicle max speed
     */
    @Attribute()
    private SimpleIntegerProperty maxSpeed;
    
    /**
     * max acceleration
     */
    @Attribute(name="maxacc")
    private SimpleIntegerProperty maxAcceleration;
    
    /**
     * max deceleration (braking)
     */
    @Attribute(name="maxdec")
    private SimpleIntegerProperty maxDeceleration;
    
    /**
     * vehicle length
     */
    @Attribute
    private SimpleIntegerProperty length;
    
    /**
     * driving attributes
     */
    @Element(required = false)
    private Driving driving;
    
    public Vehicle(){
      super();  
    }
    
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
    
    
}
