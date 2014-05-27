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

import javafx.beans.property.SimpleFloatProperty;
import org.simpleframework.xml.Attribute;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class Behavior
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */

public class Behavior {
    /**
     * safe distance gap
     */
    @Attribute
    private SimpleFloatProperty safetyDistance;
    
    private float targetSpeed ; 
    
    /**
     * Default constructor
     */
    public Behavior(){
        //Empty for the moment
    }

    /**
     * Get safety distance
     * @return Safety distance
     */
    public float getSafetyDistance() {
        return safetyDistance.get();
    }

    /**
     * Get target speed
     * @return Target speed
     */
    public float getTargetSpeed() {
        return targetSpeed;
    }
    
    /**
     * Set safety distance
     * @param safetyDistance New safety distance 
     */
    public void setSafetyDistance(float safetyDistance) {
        this.safetyDistance.set(safetyDistance);
    }
    
    /**
     * Set target speed
     * @param targetSpeed New target speed 
     */
    public void setTargetSpeed(float targetSpeed) {
        this.targetSpeed = targetSpeed;
    }
}
