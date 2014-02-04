/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.road;

import java.util.ArrayList;
import simulation.Coordinates;
import simulation.environment.Environment;
import simulation.environment.ZoneEnvironment;
import simulation.generator.DataGenerator;

/**
 * Class Road
 * Represents a road in the simulation heart
 * @author Jean
 */
public class Road {
    // Name of the road :
    private String name;
    // Roadways of the road :
    private ArrayList<RoadWay> roadWays;
    // Coordinates of the points of the road :
    private ArrayList<Coordinates> coordinates;
    // Length of the road
    private float length;
    // Junction indicating the beginning of the road
    private Junction junctionBegin;
    // Junction indicating the end of the road
    private Junction junctionEnd;
    // Rules currently applied to the road
    private Rule rules;
    // Evironment of the road :
    private Environment environment;

    /**
     * Creates a road from the given attributes
     * 
     * @param name 
     * @param roadWays 
     * @param coordinates 
     * @param junctionBegin
     * @param junctionEnd
     * @param rule
     * @param zone
     */
    public Road(String name, ArrayList<RoadWay> roadWays, ArrayList<Coordinates> coordinates, Junction junctionBegin, Junction junctionEnd, Rule rule, ArrayList<ZoneEnvironment> zone) {
        this.name = name;
        this.roadWays = roadWays;
        this.coordinates = coordinates;
        // TODO this.length = length;
        this.junctionBegin = junctionBegin;
        this.junctionEnd = junctionEnd;
        this.rules = rule;
        this.environment = DataGenerator.generateEnvironment(coordinates, zone);
    }
    
    /**
     * Gets of the name of the road
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the road
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ArrayList of the roadways
     * @return
     */
    public ArrayList<RoadWay> getRoadWays() {
        return roadWays;
    }

    /**
     * Sets the ArrayList of the roadways
     * @param roadWays
     */
    public void setRoadWays(ArrayList<RoadWay> roadWays) {
        this.roadWays = roadWays;
    }

      /**
     * Adds a roadWay to the road
     * @param roadway
     */
    public void addRoadWays(RoadWay roadway){
       this.roadWays.add(roadway);
    }
    
    /**
     * Gets the Coordinates of the road
     * @return
     */
    public ArrayList<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the Coordinates of the road
     * @param coordinates
     */
    public void setCoordinates(ArrayList<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Add a Coordinate to the road
     * @param coordinate
     */
    public void addCoordinates (Coordinates coordinate){
       this.coordinates.add(coordinate);
    }
    
    /**
     * Gets the junction of the beginning of the road
     * @return
     */
    public Junction getJunctionBegin() {
        return junctionBegin;
    }

    /**
     * Sets the junction of the beginning of the road
     * @param junctionBegin
     */
    public void setJunctionBegin(Junction junctionBegin) {
        this.junctionBegin = junctionBegin;
    }

    /**
     * Gets the junction of the end of the road
     * @return
     */
    public Junction getJunctionEnd() {
        return junctionEnd;
    }

    /**
     * Sets the junction of the end of the road
     * @param junctionEnd
     */
    public void setJunctionEnd(Junction junctionEnd) {
        this.junctionEnd = junctionEnd;
    }

    /**
     * Gets the environement of the road
     * @return
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Sets the environement of the road
     * @param environment
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Gets the rules of the road
     * @return
     */
    public Rule getRules() {
        return rules;
    }

    /**
     * Sets the rules of the road
     * @param rule
     */
    public void setRules(Rule rule) {
        this.rules = rule;
    }

    /**
     * Gets the length of the road
     * @return
     */
    public float getLenght() {
        return length;
    }

    /**
     * Sets the length of the road
     * @param lenght
     */
    public void setLenght(float lenght) {
        this.length = lenght;
    }
    
    
}
