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
package org.insa.view.graphicmodel;

import java.util.ArrayList;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.simpleframework.xml.ElementList;

/** 
 *
 * @author Thiebaud Thomas
 */
public class GraphicRoad {
    protected Road road;
    
    @ElementList
    private ArrayList<GraphicSection> sections = new ArrayList<GraphicSection>();
    
    /**
     * Default constructor
     */
    public GraphicRoad() {
        this.road = null;
    }
    
    /**
     * Constructor
     * @param road Reference to road
     */
    public GraphicRoad(Road road) {
        this.road = road;
    }

    /**
     * Get first section
     * @return First section
     */
    public Section getFirstSection() {
        return sections.get(0).getSection();
    }
    
    /**
     * Get last section 
     * @return Last section
     */
    public Section getLastSection() {
        Section sect = null;
        if(this.sections.size()>0)
             sect = this.sections.get(this.sections.size()-1).getSection();
        return sect;
    }
    
    /**
     * Get sections
     * @return Graphic sections list
     */
    public ArrayList<GraphicSection> getSections() {
        return sections;
    }
    
    /**
     * Set Graphic sections list
     * @param sections New graphic sections list
     */
    public void setSections(ArrayList<GraphicSection> sections) {
        this.sections = sections;
    }
    
    /**
     * Add new graphic section into list from simple section
     * @param s 
     */
    public void addSection(Section s){
        if(s.getGraphicSection() == null)
            this.sections.add(new GraphicSection(s,null,null));
        else
            this.sections.add(s.getGraphicSection()); 
    }
    
    /**
     * Remove section
     * @param s Section to remove
     */
    public void removeSection(Section s){
        this.sections.remove(s);
    }
}
