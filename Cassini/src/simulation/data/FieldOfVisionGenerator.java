package simulation.data;

import simulation.road.*;




/**
 *
 * @author Jean and Laure
 */
public class FieldOfVisionGenerator {
    
        public static FieldOfVision generateFieldOfVision(Driving driving, FieldOfVision fieldOfVision){
        RoadWay roadWay;
        Vehicle vehicle;
        vehicle = driving.getVehicule();
        roadWay = vehicle.getVehiclePosition().getRoadWay();
        Road road;
        road = roadWay.getRoad();
        Junction[] tabJunction = null;
        if (driving.getDirection()== 1) {
            tabJunction[0] = road.getJunctionEnd();
        }
            else {
            tabJunction[0] = road.getJunctionEnd();
        }
        
        return fieldOfVision;
        }
}
