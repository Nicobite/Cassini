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
package org.insa.core.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Transition
 * models movement possibility from current lane
 * section1         section2
 * --------------+------------
 * L1            +   L4        
 * --------------+------------
 *               +
 * L2            +   L5
 *               +
 * --------------+------------
 * L3            +   L6
 * --------------+------------
 * 
 * Transistions of L2 : L2->L1 (go to left lane)
 *                      L2->L3 (go to right lane)
 *                      L2->L5 (go straight)
 */
@Root
public class Transition {
    /**
     * target lane
     * This is a lane accessible from current lane
     */
    @Element
    private Lane targetLane;
    
    //TODO 
    /*
    private Constraint constraint
    */
    public Transition(){
        super();
    }

    public void setTargetLane(Lane targetLane) {
        this.targetLane = targetLane;
    }

    public Lane getTargetLane() {
        return targetLane;
    }
    
}
