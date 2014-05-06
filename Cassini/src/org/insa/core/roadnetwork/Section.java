
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
import java.util.Objects;
import org.insa.view.graphicmodel.GraphicSection;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Section
 * road section as represented below
 *
 * section1   section2
 * ----------+------------
 * lane1     + lane1
 * ----------+------------
 * lane2     + lane2
 * ----------+------------
 *
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class Section {
    protected GraphicSection gSection;
    
    protected Lane lane;
    
    private Road road;
    
    /**
     * max speed in this section
     */
    @Element(required = false)
    private float maxSpeed;
    
    /**
     * the next sections that can be rached from the current one
     */
    @ElementList
    private ArrayList<NextSection> successors;
    
    /**
     * Constructor
     * @param gSection Reference to graphic section
     */
    public Section(GraphicSection gSection) {
        this.gSection = gSection;
        successors = new ArrayList<>();
    }
    
    /**
     * Add a section to the successor list
     * @param succ
     */
    public void addSuccessor(NextSection succ){
        this.successors.add(succ);
    }
    
    /**
     * Remove a section form the successor list
     * @param succ
     */
    public void removeSuccessor(NextSection succ){
        this.successors.remove(succ);
    }
    
    /**
     * Get length
     * @return Length
     */
    public float getLength() {
        return gSection.getLength();
    }
    
    /**
     * Get max speed
     * @return Max speed
     */
    public float getMaxSpeed() {
        return maxSpeed;
    }
    
    /**
     * Get lane
     * @return Lane
     */
    public Lane getLane() {
        return lane;
    }
    
    /**
     * Get road
     * @return Road
     */
    public Road getRoad() {
        return road;
    }
    
    /**
     * Get graphic section
     * @return Graphic section
     */
    public GraphicSection getGraphicSection() {
        return gSection;
    }
    
    /**
     * Get successors
     * @return Successors list
     */
    public ArrayList<NextSection> getSuccessors() {
        return successors;
    }
    
    /**
     * Set length
     * @param length New length
     */
    public void setLength(int length) {
        gSection.setLength(maxSpeed);
    }
    
    /**
     * Set length
     * @param length New length
     */
    public void setLength(float length) {
        gSection.setLength(length);
    }
    
    /**
     * Set max speed
     * @param maxSpeed New max speed
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    
    /**
     * Set lane
     * @param lane New lane
     */
    public void setLane(Lane lane) {
        this.lane = lane;
    }
    
    /**
     * Set road
     * @param road New road
     */
    public void setRoad(Road road) {
        this.road = road;
    }
    
    @Override
    public String toString() {
        return "Section{" + "gSection=" + gSection + '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Section other = (Section) obj;
        if (!Objects.equals(this.gSection, other.gSection)) {
            return false;
        }
        return true;
    }
    
    /**
     * Equals v2
     * @param b
     * @return
     */
    public boolean isEqualTo(Section b){
        return (this.getGraphicSection().getSourceNode().getNode().getId() == b.getGraphicSection().getSourceNode().getNode().getId()
                && this.getGraphicSection().getTargetNode().getNode().getId() == b.getGraphicSection().getTargetNode().getNode().getId());
    }
}
