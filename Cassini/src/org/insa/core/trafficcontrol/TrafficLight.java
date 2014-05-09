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
package org.insa.core.trafficcontrol;

import org.insa.core.enums.StateTrafficLight;
import org.insa.core.roadnetwork.Node;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui &  Thomas Thiebaud
 Class TrafficLight
 */
public class TrafficLight {
    private long id;
    private Node position ; 

    private int counter ;
    
    private int redTime ;
    private int orangeTime ;
    private int greenTime ;
    
    private StateTrafficLight state ;

    public TrafficLight(Node position, int id, int redTime, int orangeTime, int greenTime) {
        this.id = id;
        this.position = position;
        this.redTime = redTime;
        this.orangeTime = orangeTime;
        this.greenTime = greenTime;
        this.state = StateTrafficLight.ORANGE ; 
    }
      public TrafficLight(long id, Node position) {
        this.position = position;
        this.id = id;
        this.redTime = 10;
        this.orangeTime = 1;
        this.greenTime = 10;
        this.state = StateTrafficLight.ORANGE ; 
    }
    
    public Node getPosition() {
        return position;
    }

    public int getCounter() {
        return counter;
    }

    public int getRedTime() {
        return redTime;
    }

    public int getOrangeTime() {
        return orangeTime;
    }

    public int getGreenTime() {
        return greenTime;
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }

    public void setOrangeTime(int orangeTime) {
        this.orangeTime = orangeTime;
    }

    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }

    public StateTrafficLight getState() {
        return state;
    }

    public void setState(StateTrafficLight state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    
    
    
}
