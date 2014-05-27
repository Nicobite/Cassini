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
package org.insa.view.graphicmodel;

import java.util.ArrayList;
import java.util.Objects;
import org.insa.core.enums.Direction;
import org.insa.core.roadnetwork.Lane;
import org.insa.core.roadnetwork.NextLane;
import org.insa.core.roadnetwork.Section;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Thiebaud Thomas
 */
@Root
public class GraphicLane {
    
    protected Lane lane;
    
    @Element
    protected GraphicPoint sourcePoint;
    
    @Element
    protected GraphicPoint targetPoint;
    
    protected GraphicSection section;
    /**
     * possible movement from current lane
     * i.e All lanes visibles from current lane
     */
    @ElementList
    private ArrayList<NextLane> nextLanes;
    
    
    /**
     * direction of this lane
     * FORWARD if same direction as the road and
     * BACKWARD otherwise
     */
    @Attribute
    private Direction direction;
    
    @Attribute
    private String id;
    
    /**
     * Default constructor
     */
    public GraphicLane() {
        this.lane = new Lane(this);
        this.nextLanes = new ArrayList<>();
        this.sourcePoint = new GraphicPoint();
        this.targetPoint = new GraphicPoint();
    }
    
    /**
     * Constructor
     * @param gSection Reference to graphic section
     */
    public GraphicLane(GraphicSection gSection) {
        this.lane = new Lane(this);
        this.section = gSection;
        this.nextLanes = new ArrayList<>();
        this.sourcePoint = new GraphicPoint();
        this.targetPoint = new GraphicPoint();
    }
    
    /**
     * Get lane
     * @return Lane
     */
    public Lane getLane() {
        return lane;
    }
    
    /**
     * Add a transition to the transitions list
     * @param t Transition to add
     */
    public void addTransition(NextLane t){
        this.nextLanes.add(t);
    }
    
    /**
     * Remove a transition from the transition list
     * @param t Transition to remove
     */
    public void removeTransition(NextLane t){
        this.nextLanes.remove(t);
    }
    
    /**
     * find the transition to the next lane of a given section
     * @param section Given section
     * @return Next lane
     */
    public NextLane findNextLaneBySection(Section section){
        NextLane result = null;
        for(NextLane transition : this.getNextLanes()){
            if(transition.getTargetLane().getSection().getSection().isEqualTo(section)){
                result = transition;
            }
        }
        return result;
    }
    
    /**
     * Check if the transitions list is not empty
     * @return true if the transitions list is not empty, false otherwise
     */
    public boolean hasTransition(){
        return !this.getNextLanes().isEmpty();
    }
    
    /**
     * Get source point
     * @return Source point
     */
    public GraphicPoint getSourcePoint() {
        return sourcePoint;
    }
    
    /**
     * Get target point
     * @return Target point
     */
    public GraphicPoint getTargetPoint() {
        return targetPoint;
    }
    
    /**
     * Get direction
     * @return Direction
     */
    public Direction getDirection() {
        return direction;
    }
    
    /**
     * Get section
     * @return Section
     */
    public GraphicSection getSection() {
        return section;
    }
    
    /**
     * Get id
     * @return Id 
     */
    public String getId() {
        return id;
    }
    
    /**
     * Get next lanes
     * @return Next Lanes
     */
    public ArrayList<NextLane> getNextLanes() {
        return nextLanes;
    }
    
    /**
     * Set source point
     * @param sourcePoint New source point
     */
    public void setSourcePoint(GraphicPoint sourcePoint) {
        this.sourcePoint = sourcePoint;
    }
    
    /**
     * Set target point
     * @param targetPoint New target point
     */
    public void setTargetPoint(GraphicPoint targetPoint) {
        this.targetPoint = targetPoint;
    }
    
    /**
     * Set direction
     * @param direction New direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    
    /**
     * Set graphic section
     * @param gSection New graphic section
     */
    public void setSection(GraphicSection gSection) {
        this.section = gSection;
    }
    
    /**
     * Set id
     * @param id New id 
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Set next lanes
     * @param nextLanes New next lanes 
     */
    public void setNextLanes(ArrayList<NextLane> nextLanes) {
        this.nextLanes = nextLanes;
    }
    
    /**
     * Set lane
     * @param lane New lane
     */
    public void setLane(Lane lane) {
        this.lane = lane;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphicLane other = (GraphicLane) obj;
        if (!Objects.equals(this.sourcePoint, other.sourcePoint)) {
            return false;
        }
        if (!Objects.equals(this.targetPoint, other.targetPoint)) {
            return false;
        }
        return true;
    }
}
