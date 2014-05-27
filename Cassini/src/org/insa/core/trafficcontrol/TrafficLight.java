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
package org.insa.core.trafficcontrol;

import org.insa.controller.MainController;
import org.insa.core.enums.StateTrafficLight;
import org.insa.core.roadnetwork.Node;
import org.insa.model.Model;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class TrafficLight
 */
public class TrafficLight {
    /**
     * Position of te traffic light
     */
    private long id;
    private Node position ;
    
    /**
     * counter to determine in how much time the traffic light will swap
     * his state
     */
    private int counter ;
    
    /**
     * Timers that determine the time the light will stay in a specific state
     */
    private int redTime ;
    private int orangeTime ;
    private int greenTime ;
    
    /**
     * state of the traffic light
     */
    private StateTrafficLight state ;
    
    /**
     * Constructor
     * @param position Position
     * @param id Id
     * @param redTime Red light time
     * @param orangeTime Orange light time
     * @param greenTime Green light time
     */
    public TrafficLight(Node position, int id, int redTime, int orangeTime, int greenTime) {
        this.id = id;
        this.position = position;
        this.redTime = redTime;
        this.orangeTime = orangeTime;
        this.greenTime = greenTime;
        this.state = StateTrafficLight.ORANGE ;
    }
    
    /**
     * Constructor
     * @param id Id
     * @param position Position 
     */
    public TrafficLight(long id, Node position) {
        this.position = position;
        this.id = id;
        this.redTime = 10;
        this.orangeTime = 1;
        this.greenTime = 10;
        this.state = StateTrafficLight.ORANGE ;
    }
    
    /**
     * Get traffic light from given node
     * @param node Given node
     * @return Traffic light
     */
    public static TrafficLight fromNode(Node node){
        Model model = MainController.getInstance().getModel() ;
        TrafficLight light = null;
        for(TrafficLight trafficLight : model.getControlUnitsModel().getTrafficLights()){
            if (trafficLight.getPosition().equals(node)){
                light = trafficLight ;
            }
        }
        return light ;
    }

    /**
     * Get id
     * @return Id 
     */
    public long getId() {
        return id;
    }

    /**
     * Get position
     * @return Position
     */
    public Node getPosition() {
        return position;
    }

    /**
     * Get counter
     * @return Counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Get red time
     * @return Red time
     */
    public int getRedTime() {
        return redTime;
    }

    /**
     * Get orange time
     * @return Orange time
     */
    public int getOrangeTime() {
        return orangeTime;
    }

    /**
     * Get green time
     * @return Green time
     */
    public int getGreenTime() {
        return greenTime;
    }

    /**
     * Get state
     * @return State
     */
    public StateTrafficLight getState() {
        return state;
    }

    /**
     * Set id 
     * @param id New id 
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Set position
     * @param position New position
     */
    public void setPosition(Node position) {
        this.position = position;
    }

    /**
     * Set counter
     * @param counter New counter 
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Set red time
     * @param redTime New red time 
     */
    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }

    /**
     * Set orange time
     * @param orangeTime New orange time 
     */
    public void setOrangeTime(int orangeTime) {
        this.orangeTime = orangeTime;
    }

    /**
     * Set green time
     * @param greenTime New green time 
     */
    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }

    /**
     * Set state
     * @param state New state
     */
    public void setState(StateTrafficLight state) {
        this.state = state;
    }
}
