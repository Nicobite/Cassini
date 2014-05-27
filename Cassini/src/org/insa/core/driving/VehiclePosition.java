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

import java.util.Objects;
import org.insa.core.roadnetwork.Lane;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class VehiclePosition
 * vehicle lane in the road represented by a lane
 * and an offset in that lane
 * -------------------------------
 * ___
 * |___|
 * 
 * -----+--------------------------
 * 0    offset
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
    
    /**
     * Constructor
     * @param lane Reference to lane
     * @param distance Distance
     */
    public VehiclePosition(Lane lane, float distance){
        this.lane = lane;
        this.offset = distance;
    }
    
    /**
     * Constructor
     * @param position Refernece to another vehicle position in order to copy it
     */
    public VehiclePosition(VehiclePosition position) {
        this.lane = position.getLane();
        this.offset = position.getOffset();
    }
    
    /**
     * Get offset
     * @return Offset
     */
    public float getOffset() {
        return offset;
    }
    
    /**
     * Get lane
     * @return Lane
     */
    public Lane getLane() {
        return lane;
    }
    
    /**
     * Set offset
     * @param offset New offset
     */
    public void setOffset(float offset) {
        this.offset = offset;
    }
    
    /**
     * Set lane
     * @param lane New lane 
     */
    public void setLane(Lane lane) {
        this.lane = lane;
    }
    
    @Override
    public String toString(){
        String str =""+offset+" lane length = "+lane.getGraphicLane().getSection().getLength();
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VehiclePosition other = (VehiclePosition) obj;
        if (!Objects.equals(this.lane, other.lane)) {
            return false;
        }
        if (Float.floatToIntBits(this.offset) != Float.floatToIntBits(other.offset)) {
            return false;
        }
        return true;
    }
}
