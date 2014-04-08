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
package org.insa.core.driving;

import org.insa.core.roadnetwork.Lane;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui &  Thomas Thiebaud
 Class VehiclePosition
 vehicle lane in the road represented by a lane 
 and an offset in that lane
 -------------------------------
       ___
      |___|  
      
 -----+--------------------------
 0    offset
 */
public class VehiclePosition {
    /**
     * current lane
     */
    private Lane lane;
    
    /**
     * offset in the lane
     */
    private float offset;

    public VehiclePosition(Lane lane, float distance){
        this.lane = lane;
        this.offset = distance;
    }
    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }
}
