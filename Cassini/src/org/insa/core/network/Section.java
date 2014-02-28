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
 */
public class Section {
    /**
     * source node
     */
    private Node sourceNode;
    /**
     *target node
     */
    private Node targetNode;
    
    /**
     * link length in m
     */
    private float length;
    
    /**
     * max speed in this section
     */
    private float maxSpeed;
    
    /**
     * lanes in this section
     *
     */
    private ArrayList<Lane>lanes;
    
    /**
     *
     * @param from
     * @param to
     */
    public Section(Node from, Node to){
        this.sourceNode = from;
        this.targetNode = to;
        this.length = computeLength(from, to);
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
    
    public double getLength() {
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
    
    public void setLanes(ArrayList<Lane> l) {
        this.lanes = l;
    }
    
    public ArrayList<Lane> getLanes() {
        return lanes;
    }
    public void addLane(Lane l){
        this.lanes.add(l);
    }
    public void removeLane(Lane l){
        this.lanes.remove(l);
    }
    public boolean containsLane(Lane l){
        return this.lanes.contains(l);
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
    
}
