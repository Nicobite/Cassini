
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

import java.util.Objects;
import org.insa.core.enums.Direction;
import org.insa.view.graphicmodel.GraphicLane;
import org.insa.view.graphicmodel.GraphicNode;
import org.insa.view.graphicmodel.GraphicSection;
import org.simpleframework.xml.Element;
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
    protected GraphicSection gSection;
    protected Lane lane;
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
     *
     * @param from
     * @param to
     */
    public Section(Node from, Node to){
        gSection = new GraphicSection(this, from, to);
        this.sourceNode = from;
        this.targetNode = to;
        //gSection.setSourceNode(new GraphicNode(sourceNode));
        //gSection.setTargetNode(new GraphicNode(targetNode));
        this.length = computeLength(from, to);
    }
    
    public Section() {
        gSection = new GraphicSection(this,null,null);
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
    
    public void setLength(float length) {
        this.length = length;
    }
    
    /**
     * add a nomber of forward lanes to this section \n
     * uses precedingSection to add connections between lanes
     * @param nbLanes
     * @param dir 
     * @param precedingSection 
     */
    public void addLanes(int nbLanes, Direction dir, Section precedingSection){
        Lane lane = null;
        for(int i = 0; i<nbLanes; i++){
            lane = new Lane();
            lane.setDirection(dir);
            lane.setSection(this);
            if(dir == Direction.FORWARD){
                gSection.getForwardLanes().add(lane.getGraphicLane());
                if(precedingSection!=null)
                    addConnectionFwd(precedingSection, this);
            }
            else{
                gSection.getBackwardLanes().add(lane.getGraphicLane());
                if(precedingSection!=null)
                    addConnectionBwd(this, precedingSection);
            }
        }
        this.lane = lane;
    }
    
    private void addConnectionFwd(Section from, Section to){
        int nb1 = from.getGraphicSection().getForwardLanes().size(), nb2 = to.getGraphicSection().getForwardLanes().size();
        int indice;
        Transition transition;
        for(int i=0; i<nb1; i++){
          indice = i<nb2 ? i : nb2-1;
          transition = new Transition();
          transition.setTargetLane(to.getGraphicSection().getForwardLanes().get(indice).getLane());
          from.getGraphicSection().getForwardLanes().get(i).getLane().addTransition(transition);
        }
    }
    
      private void addConnectionBwd(Section from, Section to){
        int nb1 = from.getGraphicSection().getBackwardLanes().size(), nb2 = to.getGraphicSection().getBackwardLanes().size();
        int indice;
        Transition transition;
        for(int i=0; i<nb1; i++){
          indice = i<nb2 ? i : nb2-1;
          transition = new Transition();
          transition.setTargetLane(to.getGraphicSection().getBackwardLanes().get(indice).getLane());
          from.getGraphicSection().getBackwardLanes().get(i).getLane().addTransition(transition);
        }
    }
    /**
     * called when deserializing this object
     */
    @Commit
    private void build(){
        for(GraphicLane l : this.getGraphicSection().getForwardLanes()){
            l.getLane().setSection(this);
        }
        for(GraphicLane l : this.getGraphicSection().getBackwardLanes()){
            l.getLane().setSection(this);
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
        double dLatitude = Math.toRadians(to.getGraphicNode().getLatitude()-from.getGraphicNode().getLatitude());
        double dLongitude = Math.toRadians(to.getGraphicNode().getLongitude()-from.getGraphicNode().getLongitude());
        double a = Math.sin(dLatitude/2) * Math.sin(dLatitude/2) +
                Math.cos(Math.toRadians(from.getGraphicNode().getLatitude())) * Math.cos(Math.toRadians(to.getGraphicNode().getLatitude())) *
                Math.sin(dLongitude/2) * Math.sin(dLongitude/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        double radiusEarth = 6371; // km
        double distance = radiusEarth * c;
        return (float)distance*1000*10;
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
    
    public GraphicSection getGraphicSection() {
        return gSection;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public Lane getLane() {
        return lane;
    }
}
