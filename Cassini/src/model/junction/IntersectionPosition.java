/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <The Simulation Team> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer (or a Mojito for Julie)
 * in return.
 * Guillaume Blanc & Gabriel Charlemagne & Jonathan Fernandez & Julie Marti
 * ----------------------------------------------------------------------------
 */

package model.junction;

import model.Coordinates;
import model.Maths;
import model.road.RoadSection;
import model.vehicule.VehiculePosition;

/**
 * Class representing the position of a vehicule within an intersection
 * @author jonathan
 */
public class IntersectionPosition extends VehiculePosition{

    private Junction junction;
    private int StartRoadSection;
    private int StartWayNumber;
    private int EndRoadSection;
    private int EndWayNumber;
    private int distance;
    private int lateralOffset;


    
     public IntersectionPosition(Junction junction, int StartRoadSection, int StartWayNumber, int EndRoadSection, int EndWayNumber, int distance, int lateralOffset) {
        this.junction = junction;
        this.StartRoadSection = StartRoadSection;
        this.StartWayNumber = StartWayNumber;
        this.EndRoadSection = EndRoadSection;
        this.EndWayNumber = EndWayNumber;
        this.distance = distance;
        this.lateralOffset = lateralOffset;
    }
    
     
     public Coordinates getArrivalCoordinates(){
         RoadSection endR = getJunction().getRoadSection(getEndRoadSection());
         Coordinates coordsE;
         int lateral_offset_end=0;
         
         if (!getJunction().getBeginOfRoadSection(EndRoadSection))
             lateral_offset_end = endR.getRoadWay(0).getWidth();
         
         for (int i=0; i<getEndWayNumber(); i++){
                lateral_offset_end += endR.getRoadWay(i).getWidth();
        }

         if(endR.getRoadWay(getEndWayNumber()).getDirection()){
         //if (getJunction().getBeginOfRoadSection(getEndRoadSection())){
           coordsE = endR.getCoordinate(0);
           coordsE = Maths.findArrivalCoordinateFromVector(coordsE, endR.getSegmentAngle(0) + Maths.PI_2, lateral_offset_end );
        }
        else{
            coordsE = endR.getCoordinate((endR.getNumberOfCoordinates()-1));
            coordsE = Maths.findArrivalCoordinateFromVector(coordsE, endR.getSegmentAngle((endR.getNumberOfCoordinates()-2))+ Maths.PI_2, lateral_offset_end );
        }         
         
         return coordsE;
     }
     
    public Coordinates getDepartureCoordinates(){
        RoadSection startR = getJunction().getRoadSection(getStartRoadSection());
        Coordinates coordsS;
        int lateral_offset_start=0;
        
        if (getJunction().getBeginOfRoadSection(StartRoadSection))
             lateral_offset_start = startR.getRoadWay(0).getWidth();
        
        for (int i=0; i<getStartWayNumber(); i++){
                lateral_offset_start += startR.getRoadWay(i).getWidth();
        }
        
        if(startR.getRoadWay(StartWayNumber).getDirection()){
        //if(getJunction().getBeginOfRoadSection(getStartRoadSection())){
            coordsS = startR.getCoordinate((startR.getNumberOfCoordinates()-1));
            coordsS = Maths.findArrivalCoordinateFromVector(coordsS, startR.getSegmentAngle((startR.getNumberOfCoordinates()-2))+ Maths.PI_2, lateral_offset_start );
        }
        else{
            coordsS = startR.getCoordinate(0);
            coordsS = Maths.findArrivalCoordinateFromVector(coordsS, startR.getSegmentAngle(0) + Maths.PI_2, lateral_offset_start );
        }
        
        return coordsS;
    }
     
    /**
     * Get the angle between the starting and the arrival position in the intersection
     * @return float : angle
     */
    @Override
    public float getAngle(){
        return Maths.angle(getDepartureCoordinates(), getArrivalCoordinates());
    }
     
    /**
     * Get the top right coordinate of the vehicule
     * @return Coordinates
     */
    @Override
     public Coordinates getVehiculeCoordinates(){
        Coordinates coordsS = getDepartureCoordinates();
        float angle = getAngle();
      
        return Maths.findArrivalCoordinateFromVectorAndOffset(coordsS, angle, getDistance(),lateralOffset);
     }
    
    public int getEndRoadSection() {
        return EndRoadSection;
    }

    public int getEndWayNumber() {
        return EndWayNumber;
    }

    public int getStartRoadSection() {
        return StartRoadSection;
    }

    public int getStartWayNumber() {
        return StartWayNumber;
    }

    public int getDistance() {
        return distance;
    }

    public Junction getJunction() {
        return junction;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLateralOffset() {
        return lateralOffset;
    }

    public void setLateralOffset(int lateralOffset) {
        this.lateralOffset = lateralOffset;
    }
    
    public int distanceToTravel(){
        return Maths.distance( getDepartureCoordinates(), getArrivalCoordinates());
    }

    

}
