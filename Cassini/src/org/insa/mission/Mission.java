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
package org.insa.mission;

import org.insa.core.roadnetwork.Node;
import org.insa.core.roadnetwork.Road;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Mission
 * Mission for a vehicle : Run from one point to another
 */
public class Mission {
    /**
     * origin : departure point
     */
    private Node origin;
    /**
     * destination to reach
     */
    private Node destination;
    
    /**
     * shorthest path to take from the origin to join the destination
     */
    private Road path;
    /**
     * Total duration of the mission (in s)
     */
    private int duration;

    public Mission(Node org, Node dest) {
        this.origin = org;
        this.destination = dest;
        duration = 0;
    }
    
    /*     getters and setters */
    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Road getPath() {
        return path;
    }

    public void setPath(Road path) {
        this.path = path;
    }
    
    
}
