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
import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import org.insa.core.enums.Direction;
import org.insa.core.roadnetwork.NextLane;
import org.insa.core.roadnetwork.NextSection;
import org.insa.core.roadnetwork.Section;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.core.Commit;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicSection extends Polygon {
    protected Section section;
    
    @Attribute
    protected double sourceDeltaX;
    
    @Attribute
    protected double sourceDeltaY;
    
    @Attribute
    protected double targetDeltaX;
    
    @Attribute
    protected double targetDeltaY;
    
    @ElementList
    protected ArrayList<Double> longLatPoints = new ArrayList<>();
    
    @Element(name="from")
    protected GraphicNode sourceNode;
    
    @Element(name="to")
    protected GraphicNode targetNode;
    
    @ElementList
    protected ArrayList<GraphicLane>forwardLanes = new ArrayList<>();
    
    @ElementList
    protected ArrayList<GraphicLane>backwardLanes = new ArrayList<>();
    
    private float length;
    
    /**
     * Default constructor
     */
    public GraphicSection() {
        this.section = new Section(this);
        this.setStrokeLineCap(StrokeLineCap.BUTT);
        this.setFill(Color.GRAY);
    }
    
    /**
     * Constructor
     * @param from Source node
     * @param to Target node
     */
    public GraphicSection(GraphicNode from,GraphicNode to) {
        this.section = new Section(this);
        sourceNode = from;
        targetNode = to;
        length = computeLength(sourceNode, targetNode) * 8;
        this.setStrokeLineCap(StrokeLineCap.BUTT);
        this.setFill(Color.GRAY);
    }
    
    /**
     * compute the length between the source node and the target node
     * @param from
     * @param to
     * @return the section length
     */
    public float computeLength(GraphicNode from, GraphicNode to){
        double dLatitude = Math.toRadians(to.getLatitude() - from.getLatitude());
        double dLongitude = Math.toRadians(to.getLongitude() - from.getLongitude());
        double a = Math.sin(dLatitude/2) * Math.sin(dLatitude/2) +
                Math.cos(Math.toRadians(from.getLatitude())) * Math.cos(Math.toRadians(to.getLatitude())) *
                Math.sin(dLongitude/2) * Math.sin(dLongitude/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        double radiusEarth = 6371; // km
        double distance = radiusEarth * c;
        return (float)distance*1000;
    }
    
    /**
     * add a given number of forward and backward lanes to this section \n
     * uses precedingSection to add connections between lanes
     * @param nbLanes
     * @param dir 
     * @param precedingSection 
     */
    public void addLanes(int nbLanes, Direction dir, GraphicSection precedingSection){
        GraphicLane gLane = null;
        for(int i = 0; i<nbLanes; i++){
            
            gLane = new GraphicLane();
            gLane.getLane().setDirection(dir);
            gLane.setSection(this);
            if(dir == Direction.FORWARD){
                forwardLanes.add(gLane);
                if(precedingSection!=null) {
                    addConnections(precedingSection.getForwardLanes(),forwardLanes);
                    precedingSection.addSuccessor(new NextSection(this));
                }
            }
            else{
                backwardLanes.add(gLane);
                if(precedingSection!=null) {
                    addConnections(backwardLanes,precedingSection.getBackwardLanes());
                    this.addSuccessor(new NextSection(precedingSection));
                }
            }
        }   
    }
    
    /**
     * Add connection(link) between lanes
     * @param from origin 
     * @param to target
     */
    public void addConnections(ArrayList<GraphicLane> from, ArrayList<GraphicLane>to){
        int nb1 = from.size(), nb2 = to.size();
        int indice;
        NextLane nextLane;
        if(nb1>0 && nb2>0)
            for(int i=0; i<nb1; i++){
              indice = i<nb2 ? i : nb2-1;
              nextLane = new NextLane();
              nextLane.setTargetLane(to.get(indice).getLane());
              from.get(i).getLane().addTransition(nextLane);
            }
    }
    
    /**
     * called when deserializing this object
     */
    @Commit
    private void build(){
        for(GraphicLane l : forwardLanes){
            l.setSection(this);
        }
        for(GraphicLane l : backwardLanes){
            l.setSection(this);
        }
        length = computeLength(sourceNode, targetNode);
    }
    
    /**
     * Add a section to the successor list
     * @param succ 
     */
    public void addSuccessor(NextSection succ){
        this.section.addSuccessor(succ);
    }
    
    /**
     * Remove a section form the successor list
     * @param succ 
     */
    public void removeSuccessor(NextSection succ){
        this.section.removeSuccessor(succ);
    }
    
    /**
     * Get section
     * @return Section
     */
    public Section getSection() {
        return section;
    }

    /**
     * Get source node
     * @return Source node
     */
    public GraphicNode getSourceNode() {
        return sourceNode;
    }

    /**
     * Get target node
     * @return Target node
     */
    public GraphicNode getTargetNode() {
        return targetNode;
    }

    /**
     * Get forward lanes
     * @return Forward lanes list
     */
    public ArrayList<GraphicLane> getForwardLanes() {
        return forwardLanes;
    }

    /**
     * Get backward lanes
     * @return Backward lanes list
     */
    public ArrayList<GraphicLane> getBackwardLanes() {
        return backwardLanes;
    }
    
    /**
     * Get source delta x
     * @return Source delta x
     */
    public double getSourceDeltaX() {
        return sourceDeltaX;
    }

    /**
     * Get source delta y
     * @return Source delta y
     */
    public double getSourceDeltaY() {
        return sourceDeltaY;
    }

    /**
     * Get target delta x
     * @return 
     */
    public double getTargetDeltaX() {
        return targetDeltaX;
    }

    /**
     * Get target delta y
     * @return Target delta y
     */
    public double getTargetDeltaY() {
        return targetDeltaY;
    }
    
    /**
     * Get longitude and latitude points list
     * @return Points list
     */
    public ArrayList<Double> getLongLatPoints() {
        return longLatPoints;
    }
    
    /**
     * Get length
     * @return Length
     */
    public float getLength() {
        return length;
    }

    /**
     * Set section
     * @param section New section
     */
    public void setSection(Section section) {
        this.section = section;
    }

    /**
     * Set source node
     * @param sourceNode New source node 
     */
    public void setSourceNode(GraphicNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    /**
     * Set target node
     * @param targetNode New target node 
     */
    public void setTargetNode(GraphicNode targetNode) {
        this.targetNode = targetNode;
    }

    /**
     * Set forward lanes
     * @param forwardLanes New forward lanes list 
     */
    public void setForwardLanes(ArrayList<GraphicLane> forwardLanes) {
        this.forwardLanes = forwardLanes;
    }

    /**
     * Set backward lanes
     * @param backwardLanes New backward lanes list 
     */
    public void setBackwardLanes(ArrayList<GraphicLane> backwardLanes) {
        this.backwardLanes = backwardLanes;
    }
   
    /**
     * Set source delta x
     * @param sourceDeltaX New source delta x 
     */
    public void setSourceDeltaX(double sourceDeltaX) {
        this.sourceDeltaX = sourceDeltaX;
    }

    /**
     * Set source delta y
     * @param sourceDeltaY New source delta y 
     */
    public void setSourceDeltaY(double sourceDeltaY) {
        this.sourceDeltaY = sourceDeltaY;
    }

    /**
     * Set target delta x
     * @param targetDeltaX New source delta x 
     */
    public void setTargetDeltaX(double targetDeltaX) {
        this.targetDeltaX = targetDeltaX;
    }

    /**
     * Set target delta y
     * @param targetDeltaY New source delta y
     */
    public void setTargetDeltaY(double targetDeltaY) {
        this.targetDeltaY = targetDeltaY;
    }
    
    /**
     * Set longitude and latitude points list
     * @param longLatPoints New points list
     */
    public void setLongLatPoints(ArrayList<Double> longLatPoints) {
        this.longLatPoints = longLatPoints;
    }
    
    /**
     * Set length
     * @param length New length 
     */
    public void setLength(float length) {
        this.length = length;
    }
    
    /**
     * Set length
     * @param from Source node
     * @param to Target node
     */
    public void setLength(GraphicNode from, GraphicNode to) {
        length = computeLength(from, to);
    }

    @Override
    public String toString() {
        return "GraphicSection{sourceNode=" + sourceNode + ", targetNode=" + targetNode + ", forwardLanes=" + forwardLanes + ", backwardLanes=" + backwardLanes + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphicSection other = (GraphicSection) obj;
        if (!Objects.equals(this.section, other.section)) {
            return false;
        }
        return true;
    }   
}
