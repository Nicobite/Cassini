/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.environment;

import java.util.ArrayList;
import simulation.Coordinates;

/**
 * Class ZoneEnvironment
 * Represents the environment of a part (zone) of the map
 * @author Jean
 */
public class ZoneEnvironment {
    private ArrayList<Coordinates> coordinates;
    private TypeEnvironment type;

    /**
     * Creates a neww ZoneEnvironment from the coordinates of the zone of application and the type of the created environment
     * @param coordinates
     * @param type
     */
    public ZoneEnvironment(ArrayList<Coordinates> coordinates, TypeEnvironment type) {
        this.coordinates = coordinates;
        this.type = type;
    }

    /**
     * Gets the coordinates of the zone of application of the environment
     * @return
     */
    public ArrayList<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates of the zone of application of the environment
     * @param coordinates
     */
    public void setCoordinates(ArrayList<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets the TypeEnvironment of the environment applied in the zone
     * @return
     */
    public TypeEnvironment getType() {
        return type;
    }

    /**
     * Sets the TypeEnvironment of the environment applied in the zone
     * @param type
     */
    public void setType(TypeEnvironment type) {
        this.type = type;
    }
    
    
    
}
