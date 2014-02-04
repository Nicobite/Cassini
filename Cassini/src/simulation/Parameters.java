/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

/**
 * Class Parameters
 * Specifies the general parameters of the simulation
 * @author Jean
 */
public class Parameters {
    // Temporal step of the simulation (time in ms beetween each calculation)
    private static int timeStep;
    // Distance step of the simulation (distance unit)
    private static int distanceStep;

    /**
     * Gets the time step (in ms)
     * @return
     */
    public static int getTimeStep() {
        return timeStep;
    }

    /**
     * Sets the time step (in ms)
     * @param timeStep
     */
    public static void setTimeStep(int timeStep) {
        Parameters.timeStep = timeStep;
    }

    /**
     * Gets the distance step
     * @return
     */
    public static int getDistanceStep() {
        return distanceStep;
    }

    /**
     * Sets the distance step
     * @param distanceStep
     */
    public static void setDistanceStep(int distanceStep) {
        Parameters.distanceStep = distanceStep;
    }
    
    
}
