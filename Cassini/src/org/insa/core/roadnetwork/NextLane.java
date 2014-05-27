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
package org.insa.core.roadnetwork;

import org.insa.core.enums.TrafficSignaling;
import org.insa.core.enums.TurnRestriction;
import org.insa.core.enums.TurningIndication;
import org.insa.view.graphicmodel.GraphicLane;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class NextLane
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class NextLane {
    /**
     * target lane
     * This is a lane accessible from current lane
     */
    
    private GraphicLane targetLane;
    
    /**
     * turn restrictions (banned turns,...)
     */
    @Attribute
    private TurnRestriction restriction;
    
    /**
     * turning indication in lane
     */
    @Attribute
    private TurningIndication indication;
    
    /**
     * traffic signaling(traffic light, stop sign, ...)
     */
    @Attribute
    private TrafficSignaling signal;
    
    @Attribute
    private String ref;
    
    /**
     * Default constructor
     */
    public NextLane(){
        super();
        this.restriction = TurnRestriction.NONE;
        this.indication = TurningIndication.NONE;
        this.signal = TrafficSignaling.NONE;
    }
    
    /**
     * gfet target lane
     * @return Target lane
     */
    public GraphicLane getTargetLane() {
        return targetLane;
    }
    
    /**
     * Get signal
     * @return Signal
     */
    public TrafficSignaling getSignal() {
        return signal;
    }
    
    /**
     * Get indication
     * @return Indication
     */
    public TurningIndication getIndication() {
        return indication;
    }
    
    /**
     * Get restriction
     * @return restriction
     */
    public TurnRestriction getRestriction() {
        return restriction;
    }
    
    /**
     * Get ref
     * @return Ref 
     */
    public String getRef() {
        return ref;
    }
    
    /**
     * Set target lane
     * @param targetLane New target lane 
     */
    public void setTargetLane(GraphicLane targetLane) {
        this.targetLane = targetLane;
        if(ref==null) 
            ref = targetLane.getId();
    }
    
    /**
     * Set signal
     * @param signal New signal 
     */
    public void setSignal(TrafficSignaling signal) {
        this.signal = signal;
    }
    
    /**
     * Set indication
     * @param indication New indication 
     */
    public void setIndication(TurningIndication indication) {
        this.indication = indication;
    }
    
    /**
     * Set restriction
     * @param restriction New restriction
     */
    public void setRestriction(TurnRestriction restriction) {
        this.restriction = restriction;
    }

    /**
     * Set ref
     * @param ref New ref 
     */
    public void setRef(String ref) {
        this.ref = ref;
    }
}
