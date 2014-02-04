/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.road;

import java.util.HashMap;

/**
 * Class TrafficLight
 * @author Jean
 */
public class TrafficLight {
    // Time of the bginning of the traffic light
    private int startTime;
    // Time of the end of the traffic light
    private int endTime;
    /**
     * List of the possible status of the traffic lights
     */
    public enum Status{
        /**
         * Green => vehicles can go
         */
        Green,
        /**
         * Orange => vehicles will stop
         */
        Orange,
        /**
         * Red => vehicles are stopped
         */
        Red,
        /**
         * KO => the traffic light doesn't work
         */
        KO;};
    // Duration of the different status
    private HashMap<Status,Integer> statusDuration;
    // Current status
    private Status current;
    // Time of the last update
    private int lastUpdate;

    /**
     * Creates a new TrafficLight from the given parameters
     * @param startTime
     * @param endTime
     * @param statusDuration
     */
    public TrafficLight(int startTime, int endTime, HashMap<Status,Integer> statusDuration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.statusDuration = statusDuration;
    }
 
    /**
     * Gets the start time of the traffic light
     * @return
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the traffic light
     * @param startTime
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the traffic light
     * @return
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the traffic light
     * @param endTime
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the current status of the traffic light
     * @return
     */
    public Status getCurrent() {
        return current;
    }
    
    /**
     * Sets the current status of the traffic light
     * @param status
     */
    public void setCurrent(Status status) {
        this.current = status;
    }

    /**
     * Gets the duration of the given status for the current trafic light
     * @param status
     * @return
     */
    public Integer getStatusDuration(Status status) {     
        return statusDuration.get(status);
    }

    /**
     * Sets the duration of the given status for the current trafic light
     * @param statusDuration
     */
    public void setStatusDuration(HashMap<Status, Integer> statusDuration) {
        this.statusDuration = statusDuration;
    }

    /**
     * Gets the time of the last update of the trafic light
     * @return
     */
    public int getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the time of the last update of the trafic light
     * @param lastUpdate
     */
    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    
}
