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
package org.insa.mission;

import org.insa.core.enums.Direction;
import org.insa.core.enums.MissionStatus;
import org.insa.core.roadnetwork.Lane;
import org.insa.core.roadnetwork.NextLane;
import org.insa.core.roadnetwork.NextSection;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicLane;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class Mission
 * Mission for a vehicle : Run from one point to another
 */
public class Mission {
    /**
     * origin : departure point
     */
    private Section origin;
    /**
     * destination to reach
     */
    private Section destination;
    
    /**
     * shorthest path to take from the origin to join the destination
     */
    private Road path;
    /**
     * Total duration of the mission (in s)
     */
    private int duration;
    /**
     * mission status
     */
    private MissionStatus status;
    
    private int currentSectNum;
    
    /**
     * Constructor
     * @param org Source
     * @param dest Destination
     * @throws PathNotFoundException Path not found
     */
    public Mission(Section org, Section dest) throws PathNotFoundException {
        this.origin = org;
        this.destination = dest;
        this.duration = 0;
        this.status = MissionStatus.STARTED;
        this.currentSectNum = 0;
        
        //compute the path
        AStar a = new AStar(org, dest);
        path = a.getShortestPath();
    }
    
    /**
     * Constructor
     * @param m Road model
     * @param org Source
     * @param dest Destination
     * @throws PathNotFoundException Path not found
     */
    public Mission(RoadsModel m, Section org, Section dest) throws PathNotFoundException {
        this.origin = org;
        this.destination = dest;
        this.duration = 0;
        this.status = MissionStatus.STARTED;
        this.currentSectNum = 0;
        
        //compute the path
        AStar a = new AStar(m, org, dest);
        path = a.getShortestPath();
    }
    
    /**
     * get the next lane to reach
     * @param currentLane Curent lane
     * @return Next lane
     */
    public NextLane getNextLane(GraphicLane currentLane){
        NextLane result;
        Section nextSection = this.getPath().getGraphicRoad().getSections()
                .get(this.getCurrentSectNum()).getSection();
        result = currentLane.findNextLaneBySection(nextSection);
        return result;
    }
    
    /**
     * Get the initial lane to take
     * @return Initial lane 
     */
    public Lane getInitialLane(){
        Lane result; NextSection next = null;
        Section section = this.getPath().getGraphicRoad().getSections().get(1).getSection();
        for(NextSection s : origin.getSuccessors()){
            if(s.getSection().isEqualTo(section)){
                next = s;
            }
        }
        result = (next.getDirection() == Direction.FORWARD) ?
                origin.getGraphicSection().getForwardLanes().get(0).getLane() :
                origin.getGraphicSection().getBackwardLanes().get(0).getLane()       ;
        return result;
    }
    
    /**
     * Increment current section number
     */
    public void updateCurrentSectionNum() {
        this.currentSectNum++;
    }

    /**
     * Get origin
     * @return Origin
     */
    public Section getOrigin() {
        return origin;
    }

    /**
     * Get destination
     * @return Destination
     */
    public Section getDestination() {
        return destination;
    }

    /**
     * Get path
     * @return Path
     */
    public Road getPath() {
        return path;
    }

    /**
     * Get duration
     * @return Duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Get status
     * @return Status
     */
    public MissionStatus getStatus() {
        return status;
    }

    /**
     * Get current section number
     * @return Current section number
     */
    public int getCurrentSectNum() {
        return currentSectNum;
    }

    /**
     * Set origin
     * @param origin New origin 
     */
    public void setOrigin(Section origin) {
        this.origin = origin;
    }

    /**
     * Set destination
     * @param destination New destination 
     */
    public void setDestination(Section destination) {
        this.destination = destination;
    }

    /**
     * Set path
     * @param path New path
     */
    public void setPath(Road path) {
        this.path = path;
    }

    /**
     * Set duration
     * @param duration New duration 
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Set status
     * @param status New status 
     */
    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    /**
     * Set curretn section number
     * @param currentSectNum New current section number
     */
    public void setCurrentSectNum(int currentSectNum) {
        this.currentSectNum = currentSectNum;
    }
    
    
}
