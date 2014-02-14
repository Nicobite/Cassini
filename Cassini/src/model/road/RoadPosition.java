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

package model.road;

import model.Coordinates;
import model.Maths;
import model.vehicule.VehiculePosition;

/**
 *
 * @author jonathan
 */
public class RoadPosition extends VehiculePosition{

    private RoadSection roadSection;
    private int wayNumber;
    private int segmentNumber;
    private int distance;
    private int lateralOffset;

    // If segmentNumber and distance not referenced, set the vehicule at the begining of the road section.
    public RoadPosition(RoadSection roadSection, int wayNumber){
        this(roadSection, wayNumber, 0,0,0);

    }

    
    public RoadPosition(RoadSection roadSection, int wayNumber, int segmentNumber, int distance, int lateralOffset){
        this.roadSection = roadSection;
        this.wayNumber = wayNumber;
        this.segmentNumber = segmentNumber;
        this.distance = distance;
        this.lateralOffset = lateralOffset;
    }

    @Override
    public float getAngle(){
         if(getRoadSection().getRoadWays().get(getWayNumber()).getDirection() == true){
             return getRoadSection().getSegmentAngle(getSegmentNumber()); 
         }
         else{
             return getRoadSection().getSegmentAngle(getSegmentNumber())+Maths.PI;  
         }
         
    }

    @Override
     public Coordinates getVehiculeCoordinates(){
        int realLateralOffset;

        if(getRoadSection().getRoadWays().get(getWayNumber()).getDirection() == true){
            realLateralOffset=getLateralOffset();
        }
        else{
            realLateralOffset= getRoadSection().getRoadWays().get(getWayNumber()).getWidth() - getLateralOffset();
        }


        for(int i = 0 ; i< getWayNumber(); i++){
            realLateralOffset += getRoadSection().getRoadWays().get(i).getWidth();
        }

        return Maths.findArrivalCoordinateFromVectorAndOffset(getRoadSection().getCoordinate(getSegmentNumber()),
                getRoadSection().getSegmentAngle(getSegmentNumber()),
                distance,
                realLateralOffset);
     }
    
       
    public RoadSection getRoadSection(){
        return roadSection;
    }

    public int getWayNumber(){
        return wayNumber;
    }

    public int getSegmentNumber(){
        return segmentNumber;
    }

    public int getDistance(){
        return distance;
    }

    public int getLateralOffset(){
        return lateralOffset;
    }

    public void setRoadSection(RoadSection value){
        this.roadSection = value;
    }

    public void setWayNumber(int value){
        this.wayNumber = value;
    }

    public void setSegmentNumber(int value){
        this.segmentNumber = value;
    }

    public void setDistance(int value){
        this.distance = value;
    }

    public void setLateralOffset(int value){
        this.lateralOffset = value;
    }
    
    public boolean getRoadWayDirection(){
        return getRoadSection().getRoadWay(this.wayNumber).getDirection();
    }
    
}
