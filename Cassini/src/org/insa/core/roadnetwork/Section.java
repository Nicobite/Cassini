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
import java.util.Objects;
import org.insa.core.enums.Direction;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
    /**
     * source node
     */
    @Element(name="from")
    private Node sourceNode;
    /**
     *target node
     */
    @Element(name="to")
    private Node targetNode;
    
    /**
     * link length in m
     */
    private float length;
    
    /**
     * max speed in this section
     */
    @Element(required = false)
    private float maxSpeed;
    
    /**
     * forward lanes in this section
     *
     */
    @ElementList
    private ArrayList<Lane>forwardLanes;
    
    /**
     * backward lanes in this section
     *
     */
    @ElementList
    private ArrayList<Lane>backwardLanes;
    
    /**
     *
     * @param from
     * @param to
     */
    public Section( Node from, Node to){
        this.sourceNode = from;
        this.targetNode = to;
        this.length = computeLength(from, to);
        this.forwardLanes = new ArrayList<>();
        this.backwardLanes = new ArrayList<>();
    }
    
    public Section() {
        this.forwardLanes = new ArrayList<>();
        this.backwardLanes = new ArrayList<>();
    }
    
    /*
    * getters ans setters
    */
    public Node getTargetNode() {
        return targetNode;
    }
    
    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }
    
    public Node getSourceNode() {
        return sourceNode;
    }
    
    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }
    
    public float getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    
    public float getMaxSpeed() {
        return maxSpeed;
    }

    public ArrayList<Lane> getBackwardLanes() {
        return backwardLanes;
    }

    public ArrayList<Lane> getForwardLanes() {
        return forwardLanes;
    }

    public void setBackwardLanes(ArrayList<Lane> backwardLanes) {
        this.backwardLanes = backwardLanes;
    }

    public void setForwardLanes(ArrayList<Lane> forwardLanes) {
        this.forwardLanes = forwardLanes;
    }

    public void setLength(float length) {
        this.length = length;
    }
    
  
    public void addForwardLanes(int nbForward){
        for(int i = 0; i<nbForward; i++){
            Lane lane = new Lane();
            lane.setDirection(Direction.FORWARD);
            this.forwardLanes.add(lane);
        }
    
    }
     public void addBackwardLanes(int nbBackward){
        for(int i = 0; i<nbBackward; i++){
            Lane lane = new Lane();
            lane.setDirection(Direction.BACKWARD);
            this.backwardLanes.add(lane);
        }
    
    }
    
    /**
     * called when deserializing this object
     */
    @Commit
    private void build(){
        for(Lane l : this.forwardLanes){
            l.setSection(this);
        }
         for(Lane l : this.backwardLanes){
            l.setSection(this);
        }
        
        this.length = computeLength(sourceNode, targetNode);
    }
    /**
     * compute the length between the source node and the target node
     * @param from
     * @param to
     * @return the section length
     */
    private float computeLength(Node from, Node to){
        double dLatitude = Math.toRadians(to.getLatitude()-from.getLatitude());
        double dLongitude = Math.toRadians(to.getLongitude()-from.getLongitude());
        double a = Math.sin(dLatitude/2) * Math.sin(dLatitude/2) +
                Math.cos(Math.toRadians(from.getLatitude())) * Math.cos(Math.toRadians(to.getLatitude())) *
                Math.sin(dLongitude/2) * Math.sin(dLongitude/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        double radiusEarth = 6371; // km
        double distance = radiusEarth * c;
        return (float)distance;
    }
    
    @Override
    public String toString(){
        return "src ="+sourceNode+",dest="+targetNode+",length = "+length+"\n";
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
        if (!Objects.equals(this.sourceNode, other.sourceNode)) {
            return false;
        }
        if (!Objects.equals(this.targetNode, other.targetNode)) {
            return false;
        }
        return true;
    }
}
