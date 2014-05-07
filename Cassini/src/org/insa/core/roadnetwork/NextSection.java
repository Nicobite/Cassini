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

import org.insa.core.enums.TurnRestriction;
import org.insa.view.graphicmodel.GraphicSection;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

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
    private GraphicSection gSection;
    /**
     * check whether this section cannot be reached from the current one (banned road,..)
     */
    private TurnRestriction restriction;
    
    @Attribute
    private String ref;
    
    /**
     * Constructor
     * @param gSection reference to graphic section
     */
    public NextSection(GraphicSection gSection) {
        this.gSection = gSection;
        if(ref==null) ref = gSection.getSection().getId();
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
    public GraphicSection getSection() {
        return gSection;
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
    public void setSection(GraphicSection section) {
        this.gSection = section;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    
}
