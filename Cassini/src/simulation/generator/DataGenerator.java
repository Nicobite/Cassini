/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.generator;


import java.util.ArrayList;
import java.awt.Polygon;
import simulation.Coordinates;
import simulation.environment.Environment;
import simulation.environment.ZoneEnvironment;
import simulation.data.Behavior;
import simulation.data.Driving;
import simulation.data.Action;
import simulation.data.Person;
import simulation.data.Vehicle;

/**
 * Class DataGenerator
 * Generates Behavior, Driving, Polygon and Environment
 * @author Jean
 */

public class DataGenerator {
    private static Action Off;
    
    /**
     * Generates a behavior for the given person
     * @param person
     * @return
     */
    public static Behavior generateBehavior(Person person){
        float effAwarness = (float) 1.0;
	float effKindness = (float) 1.0;
	float effIrritability = (float) 1.0;
	float effAggressivity = (float) 1.0;
        return new Behavior(effAwarness, effKindness, effIrritability, effAggressivity, person);     
    }
    
    /**
     * Generates driving from the given vehicle and behavior
     * @param vehicle
     * @param behavior
     * @return
     */
    public static Driving generateDriving(Vehicle vehicle, Behavior behavior){
        float effWeight = (float)1.0;
        int speed = 0;
        int acceleration = 0;
        int direction = 0;
        Action mAction = new Action(Action.actionType.Off, 0);
        ArrayList<Action> actions = null;
        actions.add(mAction);
        boolean stoplight = false;
        boolean warning = false;
        int turnlight = 0;
        return new Driving(effWeight, speed, acceleration, direction, actions, stoplight, warning, turnlight,behavior, vehicle);         
    }
    
    /**
     * Generates a polygon from the given coordinates
     * @param coordinates
     * @return
     */
    public static Polygon generatePolygon(ArrayList<Coordinates> coordinates){
        Polygon polygon = new Polygon();
        
        for (Coordinates mCoordinates : coordinates){
            polygon.addPoint(mCoordinates.getX(), mCoordinates.getY());
        }
        
        return polygon;
    }
    
    /**
     * Generates an Environment from the given coordinates and ZoneEnvironment
     * @param coordinates
     * @param zone
     * @return
     */
    public static Environment generateEnvironment(ArrayList<Coordinates> coordinates,ArrayList<ZoneEnvironment> zone){
        int awarenessOffset=0;
	int kindnessOffset=0;
	int aggressivityOffset=0;
	int irritabilityOffset=0;
        
        for (ZoneEnvironment zoneEnvironment : zone){
           Polygon polygon = generatePolygon(zoneEnvironment.getCoordinates());
           for (Coordinates mCoordinates : coordinates){
               if(polygon.contains(mCoordinates.getX(), mCoordinates.getY())){
                   awarenessOffset+=zoneEnvironment.getType().getAwarenessOffset();
                   kindnessOffset+=zoneEnvironment.getType().getKindnessOffset();
                   aggressivityOffset+=zoneEnvironment.getType().getAggressivityOffset();
                   irritabilityOffset+=zoneEnvironment.getType().getIrritabilityOffset();
                  }
           }
        }
                
        return new Environment(awarenessOffset, kindnessOffset, aggressivityOffset, irritabilityOffset);
    }
    
}
