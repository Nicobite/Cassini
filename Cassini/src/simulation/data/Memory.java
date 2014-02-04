/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.data;

import simulation.road.Junction;
import simulation.road.Rule;

/**
 * Class Memory
 * 
 * @author Jean
 */
public class Memory {
    private Rule currentRule;
    private Junction nextJunction;
    private Rule nextRule;
    private int nextJunctionDistance;
    private Vehicle[] vehicle;
    private int[] distanceVehicle;
    private int[] speedVehicle;
    private FieldOfVision fieldOfVision;

    /**
     *
     * @param currentRule
     * @param nextJunction
     * @param nextRule
     * @param nextJunctionDistance
     * @param vehicle
     * @param distanceVehicle
     * @param speedVehicle
     * @param fieldOfVision
     */
    public Memory(Rule currentRule, Junction nextJunction, Rule nextRule, int nextJunctionDistance, Vehicle[] vehicle, int[] distanceVehicle, int[] speedVehicle, FieldOfVision fieldOfVision) {
        this.currentRule = currentRule;
        this.nextJunction = nextJunction;
        this.nextRule = nextRule;
        this.nextJunctionDistance = nextJunctionDistance;
        this.vehicle = vehicle;
        this.distanceVehicle = distanceVehicle;
        this.speedVehicle = speedVehicle;
        this.fieldOfVision = fieldOfVision;
    }

    /**
     *
     * @return
     */
    public Rule getCurrentRule() {
        return currentRule;
    }

    /**
     *
     * @param currentRule
     */
    public void setCurrentRule(Rule currentRule) {
        this.currentRule = currentRule;
    }

    /**
     *
     * @return
     */
    public Junction getNextJunction() {
        return nextJunction;
    }

    /**
     *
     * @param nextJunction
     */
    public void setNextJunction(Junction nextJunction) {
        this.nextJunction = nextJunction;
    }

    /**
     *
     * @return
     */
    public Rule getNextRule() {
        return nextRule;
    }

    /**
     *
     * @param nextRule
     */
    public void setNextRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    /**
     *
     * @return
     */
    public int getNextJunctionDistance() {
        return nextJunctionDistance;
    }

    /**
     *
     * @param nextJunctionDistance
     */
    public void setNextJunctionDistance(int nextJunctionDistance) {
        this.nextJunctionDistance = nextJunctionDistance;
    }

    /**
     *
     * @return
     */
    public Vehicle[] getVehicle() {
        return vehicle;
    }

    /**
     *
     * @param vehicle
     */
    public void setVehicle(Vehicle[] vehicle) {
        this.vehicle = vehicle;
    }

    /**
     *
     * @return
     */
    public int[] getDistanceVehicle() {
        return distanceVehicle;
    }

    /**
     *
     * @param distanceVehicle
     */
    public void setDistanceVehicle(int[] distanceVehicle) {
        this.distanceVehicle = distanceVehicle;
    }

    /**
     *
     * @return
     */
    public int[] getSpeedVehicle() {
        return speedVehicle;
    }

    /**
     *
     * @param speedVehicle
     */
    public void setSpeedVehicle(int[] speedVehicle) {
        this.speedVehicle = speedVehicle;
    }

    /**
     *
     * @return
     */
    public FieldOfVision getFieldOfVision() {
        return fieldOfVision;
    }

    /**
     *
     * @param fieldOfVision
     */
    public void setFielOfVision(FieldOfVision fieldOfVision) {
        this.fieldOfVision = fieldOfVision;
    }
    
    
}
