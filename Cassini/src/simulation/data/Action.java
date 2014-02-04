/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.data;

/**
 * Class Action 
 * Describes an action for a vehicle
 * @author Jean
 */
public class Action {

    /**
     * Enumeration of the different types of action that can be performed
     */
    public enum actionType {

        /**
         * The vehicle runs straight
         */
        Run,
        /**
         * The vehicle decelerates
         */
        Decelerate,
        /**
         * The vehicle accelerates
         */
        Accelerate,
        /**
         * The vehicule goes on the left way of the road
         */
        ChangeSectionLeft,
        /**
         * The vehicule goes on the right way of the road
         */
        ChangeSectionRight,
        /**
         * The vehicule stops
         */
        Off,
        /**
         * The vehicule is crashed on the road
         */
        Crashed;
    }
    // Specify the type of the action (see above)
    private actionType action;
    // objectiveValue is a value to be reached : speed, time...
    private int objectiveValue;

    /**
     * Creates new from action => specify the action type and the objectiveValue
     * @param action
     * @param objectiveValue
     */
    public Action(actionType action, int objective) {
        this.action = action;
        this.objectiveValue = objective;
    }

    /**
     * Gets the type of the action
     * @return
     */
    public actionType getAction() {
        return action;
    }

    /**
     * Sets the type of the action
     * @param action
     */
    public void setAction(actionType action) {
        this.action = action;
    }

    /**
     * Gets the objectiveValue of the action (objectiveValue is a value to be reached : speed, time...)
     * @return
     */
    public int getObjectiveValue() {
        return objectiveValue;
    }

    /**
     * Sets the objectiveValue of the action (objectiveValue is a value to be reached : speed, time...)
     * @param objectiveValue
     */
    public void setObjectiveValue(int objective) {
        this.objectiveValue = objective;
    }
}
