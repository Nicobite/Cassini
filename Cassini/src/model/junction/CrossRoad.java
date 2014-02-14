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

import java.util.ArrayList;
import model.Maths;
import model.road.RoadPosition;
import model.road.RoadSection;
import model.vehicule.Vehicule;
import model.vehicule.VehiculeEnvironment;

/**
 *
 * @author marti
 */
public class CrossRoad extends Junction{

    protected ArrayList<RoadSign>  roadSigns;
    
    public CrossRoad()
    {
        super();
        roadSigns = new ArrayList<RoadSign>();
    }
    @Override
    public void vehiculeEntry(Vehicule v, int roadSection, int wayNumber, int distance, int lateralOffset) {
        RoadPosition pos = (RoadPosition) v.getPosition();
        int comingRoadSection = this.getRoadSections().indexOf(pos.getRoadSection());
        v.changePosition(new IntersectionPosition(this, comingRoadSection, pos.getWayNumber(), roadSection, wayNumber, distance, lateralOffset));
        this.addVehicule(v);
    }

    public void addRoadSection (RoadSection road, boolean begin, RoadSign roadSign) {
        super.addRoadSection(road, begin);
        roadSigns.add(roadSign);
    }

    @Override
    public void fillFrontEnvironment(VehiculeEnvironment environment, int beginRoadSection, int beginWayNumber, int endRoadSection, int endWayNumber, int distanceToJunction, int nbVehiculeToFind){
        fillFrontEnvironment(environment, -1, beginRoadSection, beginWayNumber, endRoadSection, endWayNumber, distanceToJunction, nbVehiculeToFind); 
    }
    
    @Override
    public void fillFrontEnvironment(VehiculeEnvironment environment, int vehiculeIndex, int beginRoadSection, int beginWayNumber, int endRoadSection, int endWayNumber, int distanceToJunction, int nbVehiculeToFind){
        
        if(vehiculeIndex == -1){
        //Add this crossroad to the environment
            environment.setNextJunction(this);
            environment.setDistNextJunction(distanceToJunction);
            environment.setIndexForNextJunction(beginRoadSection);
        }
        IntersectionPosition vehiculePos = new IntersectionPosition(this, beginRoadSection, beginWayNumber, endRoadSection, endWayNumber, 0, 0); 
        int distanceToTheEnd = distanceToJunction+ vehiculePos.distanceToTravel();
        if(nbVehiculeToFind>0){
            for(int i=0;i<vehicules.size();i++){
                if(i != vehiculeIndex){
                   Vehicule v=vehicules.get(i);
                   IntersectionPosition vPos = (IntersectionPosition)v.getPosition();
                   int distToVehicule;
                   if((vPos.getStartRoadSection() == beginRoadSection && vPos.getStartWayNumber() == beginWayNumber)){
                       distToVehicule=distanceToJunction + vPos.getDistance();
                   }else{
                       distToVehicule= distanceToTheEnd - (vPos.distanceToTravel()-vPos.getDistance());   
                   }
                   
                        
                   //Front
                   if((vPos.getStartRoadSection() == beginRoadSection && vPos.getStartWayNumber() == beginWayNumber)
                           || (vPos.getEndRoadSection() == endRoadSection && vPos.getEndWayNumber() == endWayNumber)){

                       if(environment.getVehiculeFront() == null){
                           if(distToVehicule > 0){
                                nbVehiculeToFind--;
                                environment.setVehiculeFront(v, distToVehicule);
                            }
                       }else{
                           if(distToVehicule > 0 && distToVehicule < environment.getDistVehiculeFront()){
                               environment.setVehiculeFront(v, distToVehicule);
                           }
                       }

                   }
                   //FrontLeft
                   if(vPos.getStartRoadSection() == beginRoadSection 
                           && this.getRoadSection(beginRoadSection).haveAnotherRoadWayOnHisLeft(beginWayNumber)
                           && ((roadSections.get(beginRoadSection).getRoadWay(beginWayNumber).getDirection()==true
                                    && vPos.getStartWayNumber() == beginWayNumber + 1)
                              ||(roadSections.get(beginRoadSection).getRoadWay(beginWayNumber).getDirection()==false
                                    && vPos.getStartWayNumber() == beginWayNumber - 1))){
                       
                           if (environment.getVehiculeFrontLeft() == null) {
                               if (distToVehicule > 0) {
                                   nbVehiculeToFind--;
                                   environment.setVehiculeFrontLeft(v, distToVehicule);
                               }
                           } else {
                               if (distToVehicule > 0 && distToVehicule < environment.getDistVehiculeFrontRight()) {
                                   environment.setVehiculeFrontLeft(v, distToVehicule);
                               }
                           }
                   }
                   
                   //FrontRight
                   if(vPos.getStartRoadSection() == beginRoadSection 
                           && this.getRoadSection(beginRoadSection).haveAnotherRoadWayOnHisRight(beginWayNumber)
                           && ((roadSections.get(beginRoadSection).getRoadWay(beginWayNumber).getDirection()==true
                                    && vPos.getStartWayNumber() == beginWayNumber - 1)
                              ||(roadSections.get(beginRoadSection).getRoadWay(beginWayNumber).getDirection()==false
                                    && vPos.getStartWayNumber() == beginWayNumber + 1))){
                       
                           if (environment.getVehiculeFrontRight() == null) {
                               if (distToVehicule > 0) {
                                   nbVehiculeToFind--;
                                   environment.setVehiculeFrontRight(v, distToVehicule);
                               }
                           } else {
                               if (distToVehicule > 0 && distToVehicule < environment.getDistVehiculeFrontRight()) {
                                   environment.setVehiculeFrontRight(v, distToVehicule);
                               }
                           }
                   }
                }
            }
            if(environment.getVehiculeFront() == null){
              if(endRoadSection>=0){
                    roadSections.get(endRoadSection).fillFrontEnvironment(environment, endWayNumber, distanceToTheEnd, nbVehiculeToFind);  
                }
                    
            }
            
        }
        
        
    }

    public RoadSign getRoadSign(int index) {
        return roadSigns.get(index);
    }
    
    public void setRoadSign(RoadSign sign, int index){
        //roadSigns.get(index).setSign(sign.getSign());
        roadSigns.remove(index);
        roadSigns.add(index, sign);
    }
    
    @Override
    public void fillBackEnvironment(VehiculeEnvironment environment, int RoadSection, int WayNumber, int distanceToJunction, int nbVehiculeToFind) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public float getAngleNextDestination(int roadSectionDeparture, int roadSectionArrival){
        float angle;
        float angleD, angleA;
               
        if (getBeginOfRoadSection(roadSectionDeparture))
           angleD = (Maths.PI + getRoadSection(roadSectionDeparture).getSegmentAngle(0))%(Maths.PI*2);
        else
           angleD = getRoadSection(roadSectionDeparture).getSegmentAngle( getRoadSection(roadSectionDeparture).getNumberOfCoordinates()-2);
        
        if (getBeginOfRoadSection(roadSectionArrival))
           angleA = getRoadSection(roadSectionArrival).getSegmentAngle(0);
        else
           angleA = (Maths.PI + getRoadSection(roadSectionArrival).getSegmentAngle( getRoadSection(roadSectionArrival).getNumberOfCoordinates()-2))% (Maths.PI*2);
        
            angle = (Maths.PI*2+ (angleA-angleD)) % (Maths.PI*2);

        return angle;
        
    }
    
}
