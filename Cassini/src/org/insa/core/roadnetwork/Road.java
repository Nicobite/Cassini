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

import java.util.Objects;
import org.insa.core.enums.RoadType;
import org.insa.view.graphicmodel.GraphicRoad;
import org.insa.view.graphicmodel.GraphicSection;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Road
 * represent a road way.
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class Road {
    @Element
    private GraphicRoad gRoad;
    
    /**
     * type of this road
     */
    @Attribute(required = false)
    private RoadType type;
    
    @Attribute(required = false)
    private long id;
    
    /**
     * whether this lane is one-way
     */
    private boolean oneway;
    
    /**
     * Default constructor
     */
    public Road() {
        gRoad = new GraphicRoad(this);
    }
    
    /**
     * Get the first section of the road
     * @return First section
     */
    public GraphicSection getFirstSection() {
        return gRoad.getSections().get(0);
    }
    
    /**
     * Get the number of sections
     * @return Number of sections
     */
    public int size() {
        return gRoad.getSections().size();
    }
    
    /**
     * Get the last section of the road
     * @return Last section
     */
    public GraphicSection getLastSection() {
        GraphicSection sect = null;
        if(gRoad.getSections().size() > 0)
            sect = gRoad.getSections().get(gRoad.getSections().size()-1);
        return sect;
    }
    
    /**
     * Get the section of this road where this node is a source node
     * @param source Given node
     * @return Section found;
     */
    public Section findSectionBySourceNode(Node source){
        Section result = null;
        for(GraphicSection sect : this.gRoad.getSections()){
            if(sect.getSourceNode().getNode().getId() == source.getId()){
                result = sect.getSection();
            }
        }
        return result;
    }
    
    /**
     * Get the section of this road where this node is a target node
     * @param target
     * @return the section ;
     */
    public Section findSectionByTargetNode(Node target){
        Section result = null;
        for(GraphicSection sect : this.gRoad.getSections()){
            if(sect.getTargetNode().getNode().getId() == target.getId()){
                result = sect.getSection();
            }
        }
        return result;
    }

    /**
     * Add a section to the road
     * @param s Section to add
     */
    public void addSection(Section s){
        gRoad.addSection(s);
        s.setRoad(this);
    }
    
    /**
     * Remove a section from a road
     * @param s Section to remove
     */
    public void removeSection(Section s){
        gRoad.removeSection(s);
    }
    
    /**
     * Get type
     * @return Type
     */
    public RoadType getType() {
        return type;
    }
    
    /**
     * Get id
     * @return Id 
     */
    public long getId() {
        return id;
    }
    
    /**
     * Get oneway
     * @return true if oneway, false otherwise
     */
    public boolean isOneway() {
        return oneway;
    }
    
    /**
     * Get graphic road
     * @return Graphic road
     */
    public GraphicRoad getGraphicRoad() {
        return gRoad;
    }
    
    /**
     * Set type
     * @param type New type 
     */
    public void setType(RoadType type) {
        this.type = type;
    }
    
    /**
     * Set id
     * @param id New id 
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Set oneway
     * @param oneway new oneway boolean 
     */
    public void setOneway(boolean oneway) {
        this.oneway = oneway;
    }
    
    @Override
    public String toString(){
        return gRoad.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Road other = (Road) obj;
        if (!Objects.equals(gRoad, other.gRoad)) {
            return false;
        }
        return true;
    }

}
