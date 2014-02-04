/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.road;

import java.util.HashMap;

/**
 * Class Rule
 * Specifies the rule currently in force on a road
 * @author Jean
 */
public  class Rule {
    // Defines the limit of speed on the roads
    private int speedLimit;
    // Specifies if there is a roadsign indicating a danger on the road
    private boolean danger;

    /**
     * Creates a new from Rule from the given parameters
     * @param speedLimit
     * @param danger
     */
    public Rule(int speedLimit, boolean danger) {
        this.speedLimit = speedLimit;
        this.danger = danger;
    }

    /**
     * Gets the limit of speed of the road which belongs the rule
     * @return
     */
    public int getSpeedLimit() {
        return speedLimit;
    }

    /**
     * Sets the limit of speed of the rule which belongs the rule
     * @param speedLimit
     */
    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    /**
     * Indicates if there is a danger on the road which belongs the rule
     * @return
     */
    public boolean isDanger() {
        return danger;
    }

    /**
     * Sets if there is a danger on the road which belongs the rule
     * @param danger
     */
    public void setDanger(boolean danger) {
        this.danger = danger;
    }   
}
