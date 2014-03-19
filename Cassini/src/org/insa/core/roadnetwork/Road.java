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
package org.insa.core.roadnetwork;

import java.util.ArrayList;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Road
 * represent a road way.
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class Road {
    
    /**
     * list of sections making this road
     */
    @ElementList
    private ArrayList<Section>sections;
    
    public Road() {
        this.sections = new ArrayList<>();
    }
    
    
    /*
    * getters ans setters
    */
    public Section getOrigin() {
        return this.sections.get(0);
    }
    
    
    public Section getDestination() {
        return this.sections.get(this.sections.size()-1);
    }
    
    public ArrayList<Section> getSections() {
        return sections;
    }
    
    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }
    
    public void addSection(Section s){
        this.sections.add(s);
    }
    public void removeSection(Section s){
        this.sections.remove(s);
    }
    
    @Override
    public String toString(){
        return sections.toString();
    }
    
}
