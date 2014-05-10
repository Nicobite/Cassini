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
package org.insa.core.roadnetwork;

import org.insa.core.enums.Direction;
import org.insa.core.enums.TurnRestriction;
import org.simpleframework.xml.Attribute;

/**
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class NextSection
 * This class is used to add connections between section\n
 * so that the roads become connected to each other\n
 * This is used to build link between lanes (via NextLane)\n
 * and compute a path between 2 nodes.
 */
public class NextSection {
    /**
     * the next sections that can be rached from the current one
     */
    private Section section;
    /**
     * check whether this section cannot be reached from the current one (banned road,..)
     */
    private TurnRestriction restriction;
    
    @Attribute
    private String ref;
        
    @Attribute
    private Direction direction;
    
    /**
     * Constructor
     * @param gSection reference to graphic section
     * @param direction
     */
    public NextSection(Section gSection, Direction direction) {
        this.section = gSection;
        this.direction = direction;
        if(ref==null) ref = section.getId();
    }
    public NextSection(){
        
    }
    /**
     * Get restriction
     * @return Restriction
     */
    public TurnRestriction getRestriction() {
        return restriction;
    }
    
    /**
     * Get section
     * @return Section
     */
    public Section getSection() {
        return section;
    }  
    
    /**
     * Set restriction
     * @param restriction New restriction 
     */
    public void setRestriction(TurnRestriction restriction) {
        this.restriction = restriction;
    }
    
    /**
     * Set section
     * @param section New section 
     */
    public void setSection(Section section) {
        this.section = section;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    
}
