/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.data;

import java.util.Random;
import simulation.Coordinates;
import simulation.road.Junction;
import simulation.Parameters;
import simulation.Segment;
import simulation.road.Road;
import simulation.road.RoadWay;

/**
 * Class VehiclePosition
 * Represent a VehiclePosition from a road or a junction and the marking out (= kilométrage) of the road
 * @author Jean
 */
public class VehiclePosition {
    // Marking out of the position of the vehicle on the road (in km)
    private float position;
    // Way of the road of the vehicle
    private RoadWay roadWay;
    // Junction where is the vehicle (if it is currently on a junction, null otherwise)
    private Junction junction;

    /**
     * Specifies the VehiclePosition from the given roadway, junction and position in km
     * @param position
     * @param roadWay
     * @param junction
     */
    public VehiclePosition(float position, RoadWay roadWay, Junction junction) {
        this.position = position;
        this.roadWay = roadWay;
        this.junction = junction;
    }
    
    /**
     * Gets the marking out of the road where the vehicle is (in km)
     * @return
     */
    public float getPosition() {
        return position;
    }

    /**
     * Sets the marking out of the road where the vehicle is (in km)
     * @param position
     */
    public void setPosition(float position) {
        this.position = position;
    }

    /**
     * Gets the roadway where the vehicle is 
     * @return
     */
    public RoadWay getRoadWay() {
        return roadWay;
    }

    /**
     * Sets the roadway where the vehicle is 
     * @param roadWay
     */
    public void setRoadWay(RoadWay roadWay) {
        this.roadWay = roadWay;
    }

    /**
     * Gets the junction where the vehicle is (null if it is not on a junction)
     * @return
     */
    public Junction getJunction() {
        return junction;
    }

    /**
     * Sets the junction where the vehicle is (null if it is not on a junction)
     * @param junction
     */
    public void setJunction(Junction junction) {
        this.junction = junction;
    }
    
    /**
     * The vehicle changes roadway
     * @param driving 
     */
    private void changeSection(Driving driving){
        if (driving.getActions().get(0).getAction()== Action.actionType.ChangeSectionLeft){
            int i = this.roadWay.getRoad().getRoadWays().lastIndexOf(this.roadWay);
            if (i > 0){
                setRoadWay(this.roadWay.getRoad().getRoadWays().get(i-1)); 
            }
            else {
                driving.getActions().clear();
                driving.getActions().add(new Action(Action.actionType.Crashed,0));
            }
        }
        
        else if (driving.getActions().get(0).getAction()== Action.actionType.ChangeSectionRight){
            int i = this.roadWay.getRoad().getRoadWays().lastIndexOf(this.roadWay);
            if (i < this.roadWay.getRoad().getRoadWays().size()-1){
                setRoadWay(this.roadWay.getRoad().getRoadWays().get(i+1)); 
            }
            else {
                driving.getActions().clear();
                driving.getActions().add(new Action(Action.actionType.Crashed,0));
            }
        }
    }
    
    /**
     * Allows the vehicle to move
     * @param driving
     */
    public void move(Driving driving){
        int newRoad;
        Road oldRoad;
        changeSection(driving);
        if (driving.getActions().get(0).getAction()!= Action.actionType.Crashed){
            if (driving.getDirection() == 1){
                this.position+= (driving.getSpeed()*Parameters.getTimeStep()*(1.0/3600.0))*driving.getDirection();
                if (this.position > this.roadWay.getRoad().getLenght()){
                      Random random = new Random();
                      newRoad = random.nextInt(this.roadWay.getRoad().getJunctionEnd().getRoads().size()-1);
                      this.roadWay.deleteVehicule(driving.getVehicule());
                      if (this.roadWay.getRoad().getJunctionEnd().getRoads().get(newRoad) != this.roadWay.getRoad()){
                          oldRoad = this.roadWay.getRoad();
                          this.position-=this.roadWay.getRoad().getLenght();
                          this.roadWay=this.roadWay.getRoad().getJunctionEnd().getRoads().get(newRoad).getRoadWays().get(0);
                      }
                      else{
                          oldRoad = this.roadWay.getRoad();
                          this.position-=this.roadWay.getRoad().getLenght();
                          this.roadWay=this.roadWay.getRoad().getJunctionEnd().getRoads().get(0).getRoadWays().get(0);
                      }
                      if(oldRoad.getJunctionEnd() !=this.roadWay.getRoad().getJunctionBegin()){
                          this.roadWay=this.roadWay.getRoad().getRoadWays().get(this.roadWay.getRoad().getRoadWays().size()-1);
                          this.position=this.roadWay.getRoad().getLenght()-this.position;
                          driving.setDirection(-1);
                      }
                      this.roadWay.addVehicule(driving.getVehicule());
                     
                }
            }
            else{ 
                if (this.position < 0.0){
                     Random random = new Random();
                     newRoad = random.nextInt(this.roadWay.getRoad().getJunctionBegin().getRoads().size()-1);
                     this.roadWay.deleteVehicule(driving.getVehicule());
                     if (this.roadWay.getRoad().getJunctionBegin().getRoads().get(newRoad) != this.roadWay.getRoad()){
                            oldRoad = this.roadWay.getRoad();
                         this.position =this.roadWay.getRoad().getLenght() -this.position;
                         this.roadWay=this.roadWay.getRoad().getJunctionBegin().getRoads().get(newRoad).getRoadWays().get(0);   
                     }
                     else{
                          oldRoad = this.roadWay.getRoad();
                          this.position =this.roadWay.getRoad().getLenght() - this.position;
                          this.roadWay=this.roadWay.getRoad().getJunctionBegin().getRoads().get(0).getRoadWays().get(0);
                     }
                     if(oldRoad.getJunctionBegin() !=this.roadWay.getRoad().getJunctionBegin()){
                          this.roadWay=this.roadWay.getRoad().getRoadWays().get(this.roadWay.getRoad().getRoadWays().size()-1);
                     }
                     else{  
                         this.position =  this.roadWay.getRoad().getLenght() - this.position;
                         driving.setDirection(1);
                     }
                      this.roadWay.addVehicule(driving.getVehicule());
                }
            }
        }

    }
    
}
