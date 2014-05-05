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

import java.util.ArrayList;
import org.insa.core.enums.TurnRestriction;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class NextSection
 * This class is used to add connections between section\n
 * so that the roads become connected to each other\n
 * This is used to build link between lanes (via NextLane)\n
 * and compute a path between 2 nodes. 
 * 
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

    public NextSection() {
        
    }
    
    /* getters and setters */

    public TurnRestriction getRestriction() {
        return restriction;
    }

    public void setRestriction(TurnRestriction restriction) {
        this.restriction = restriction;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Section getSection() {
        return section;
    }

   
    
}
