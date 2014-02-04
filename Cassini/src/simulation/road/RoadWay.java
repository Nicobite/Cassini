/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.road;

import java.util.ArrayList;
import simulation.data.Vehicle;

/**
 * Class RoadWay Represents au roadway belonging to a road
 *
 * @author Jean
 */
public class RoadWay {
    // Identifier of the RoadWay (left from junctionBegin of the road is number 1)
    private int id;
    // List of vehicles currently on the RoadWay
    private ArrayList<Vehicle> vehicles;
    // Width of the RoadWay
    private int width;
    // Direction of the RoadWay
    private int direction;
    // Road which the RoadWay belongs
    private Road road;

    /**
     * Makes a new RoadWay from the given parameters
     * @param id
     * @param width
     * @param direction
     * @param road
     */
    public RoadWay(int id, int width, int direction, Road road) {
        this.id = id;
        this.width = width;
        this.direction = direction;
        this.road = road;
    }

    /**
     * Gets the id of the roadway
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the roadway
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the list of vehicles currently on the roadway
     * @return
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles currently on the roadway
     * @param vehicles
     */
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Gets the number of vehicles currently on the roadway
     * @return
     */
    public int getNumberOfVehicules() {
        return vehicles.size();
    }

    /**
     * Adds a vehicle on the roadway
     * @param vehicle
     */
    public void addVehicule(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    /**
     * Removes a vehicle from the roadway
     * @param vehicle
     */
    public void deleteVehicule(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
    }

    /**
     * Gets the width of the roadway
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the roadway
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the direction of the roadway
     * @return
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the roadway
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Gets the road of the roadway
     * @return
     */
    public Road getRoad() {
        return road;
    }

    /**
     * Sets the road of the roadway
     * @param road
     */
    public void setRoad(Road road) {
        this.road = road;
    }
}
