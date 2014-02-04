/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.road;

import java.util.ArrayList;
import simulation.environment.GlobalEnvironment;
import simulation.data.Vehicle;
import static simulation.road.TrafficLight.Status.Orange;

/**
 * Class Junction
 * Represents the junction of one to four roads
 * @author Jean
 */

// TODO : gérer exceptions
public class Junction {
    // Roads crossing on the junction
    private ArrayList<Road> roads;
    // Vehicles currently on the junction
    private ArrayList<Vehicle> vehicles;
    /**
     * Enumeration of the possible crossroads rules
     */
    public enum CrossroadsIndication{
        /**
         * No priority nor roadsign
         */
        None(),
        /**
         * Stop roadsign
         */
        Stop(),
        /**
         * Right priority is applied at the junction
         */
        RightPriority(),
        /**
         * Information unknown
         */
        NoEntry(),
        /**
         * Yield priority
         */
        Yield();}
    // ArrayList of the crossroads rules on the current junction
    private ArrayList<CrossroadsIndication> indications;
    // Arraylist of the traffic lights on the current junction
    private ArrayList<TrafficLight> trafficLights;

    /**
     * Creates a junction from the given attributes
     * 
     * @param roads 
     * @param indications 
     * @param trafficLights 
     */
    public Junction(ArrayList<Road> roads, ArrayList<CrossroadsIndication> indications, ArrayList<TrafficLight> trafficLights) {
        this.roads = roads;
        this.indications = indications;
        this.trafficLights = trafficLights;
    }
    
    /**
     * Changes the traffic lights green to orange, orange to red and red to green
     * @return
     */
    private boolean upgradeTrafficLights(){
        boolean upgrade=false;
        for (TrafficLight trafficLight : this.trafficLights){
            if (trafficLight.getEndTime()<GlobalEnvironment.getTime()){
                trafficLight.setCurrent(TrafficLight.Status.KO);
                upgrade = true;
            }
            else{
             switch (trafficLight.getCurrent()){
                case Green : 
                    if (trafficLight.getLastUpdate()+trafficLight.getStatusDuration(TrafficLight.Status.Green)<GlobalEnvironment.getTime()){
                        trafficLight.setCurrent(TrafficLight.Status.Orange);
                        trafficLight.setLastUpdate(trafficLight.getLastUpdate()+trafficLight.getStatusDuration(TrafficLight.Status.Green));
                        upgrade = true;
                    }
                        break;
                case Orange : 
                    if (trafficLight.getLastUpdate()+trafficLight.getStatusDuration(TrafficLight.Status.Orange)<GlobalEnvironment.getTime()){
                        trafficLight.setCurrent(TrafficLight.Status.Red);
                        trafficLight.setLastUpdate(trafficLight.getLastUpdate()+trafficLight.getStatusDuration(TrafficLight.Status.Orange));
                        upgrade = true;
                        }
                        break;
                case Red : 
                    if (trafficLight.getLastUpdate()+trafficLight.getStatusDuration(TrafficLight.Status.Red)<GlobalEnvironment.getTime()){
                        trafficLight.setCurrent(TrafficLight.Status.Green);
                        trafficLight.setLastUpdate(trafficLight.getLastUpdate()+trafficLight.getStatusDuration(TrafficLight.Status.Red));
                        upgrade = true;
                        }
                        break;
                case KO :
                    if (trafficLight.getStartTime()>GlobalEnvironment.getTime()){
                        trafficLight.setCurrent(TrafficLight.Status.Orange);
                        trafficLight.setLastUpdate(GlobalEnvironment.getTime());
                        upgrade = true;
                        }
                    break;
            }
            }
        }
        return upgrade;
    }
    
    /**
     * Updates continuously the traffic lights
     */
    private void actualizeTrafficLights(){
        while(upgradeTrafficLights()){}
    }

    /**
     * Gets of the roads crossing on the junction
     * @return
     */
    public ArrayList<Road> getRoads() {
        actualizeTrafficLights();
        return roads;
    }

    /**
     * Sets of the roads crossing on the junction
     * @return
     */
    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    /**
     * Gets of the vehicles on the junction
     * @return
     */
    public ArrayList<Vehicle> getVehicles() {
         actualizeTrafficLights();
        return vehicles;
    }

    /**
     * Sets the vehicles on the junction
     * @return
     */
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Gets of the crossroads indications on the junction
     * @return
     */
    public ArrayList<CrossroadsIndication> getIndications() {
        actualizeTrafficLights();
        return indications;
    }

    /**
     * Sets of the crossroads indications on the junction
     * @return
     */
    public void setIndications(ArrayList<CrossroadsIndication> indications) {
        this.indications = indications;
    }

    /**
     * Gets of the trfic lights on the junction
     * @return
     */
    public ArrayList<TrafficLight> getTrafficLights() {
        actualizeTrafficLights();
        return trafficLights;
    }
    
    /**
     * Gets of the trfic lights on the junction
     * @return
     */
    public void setTrafficLights(ArrayList<TrafficLight> trafficLight) {
        this.trafficLights = trafficLight;
    }
    
    
    
}
