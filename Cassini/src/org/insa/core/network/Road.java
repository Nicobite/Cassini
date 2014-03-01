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

import java.util.ArrayList;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Road
 * represent a road way
 */
@Root
public class Road {
    /**
     * origine of the way
     */
    private Section origin;
    
    /**
     * destination of the road
     */
    private Section destination;
    
    /**
     * list of sections making this road
     */
    @ElementList
    private ArrayList<Section>segments;
    
    /**
     * constructor
     * @param from road origin
     * @param to road destination
     */
    public Road(Section from, Section to){
        this.origin = from;
        this.destination = to;
        this.segments = new ArrayList<>();
    }

    public Road() {
        this.segments = new ArrayList<>();
    }
    
    
    /*
    * getters ans setters
    */
    public Section getOrigin() {
        return origin;
    }
    
    public void setOrigin(Section origin) {
        this.origin = origin;
    }
    
    public Section getDestination() {
        return destination;
    }
    
    public void setDestination(Section destination) {
        this.destination = destination;
    }
    
    public ArrayList<Section> getSegments() {
        return segments;
    }
    
    public void setSegments(ArrayList<Section> segments) {
        this.segments = segments;
    }
    
    public void addSegment(Section s){
        this.segments.add(s);
    }
    public void removeSegment(Section s){
        this.segments.remove(s);
    }
    public boolean containsSegment(Section s){
        return this.segments.contains(s);
    }
    @Commit
    public void build(){
        this.origin = this.segments.get(0);
        this.destination = this.segments.get(this.segments.size()-1);
    }
    
}
