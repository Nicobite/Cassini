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
package old.project2012.model.junction;

import old.project2012.model.road.RoadPosition;
import old.project2012.model.road.RoadSection;
import old.project2012.model.vehicule.Vehicule;
import old.project2012.model.vehicule.VehiculeEnvironment;

/**
 *
 * @author gabriel
 */
public class RoadSectionJunction extends Junction implements InterfaceJunctionTwoRoad{
    

   public RoadSectionJunction(){
       super();
       roadSections.add(null);
       roadSections.add(null);
       beginOfRoadSections.add(null);
       beginOfRoadSections.add(null);

   }
   
    public RoadSectionJunction(RoadSection firstRoadSection, boolean beginOfFirstRoadSection, RoadSection secondRoadSection, boolean beginOfSecondRoadSection) {
        super();
        this.addRoadSection(firstRoadSection, beginOfFirstRoadSection);
        this.addRoadSection(secondRoadSection, beginOfSecondRoadSection);
        
        if( beginOfFirstRoadSection ){
            firstRoadSection.setBeginJunction((Junction)this);
        }else{
            firstRoadSection.setEndJunction((Junction)this);
        }
                
        if( beginOfSecondRoadSection ){
            secondRoadSection.setBeginJunction((Junction)this);
        }else{
            secondRoadSection.setEndJunction((Junction)this);
        }
    }

    public RoadSection getFirstRoadSection() {
        return getRoadSection(0);
    }

    public RoadSection getSecondRoadSection() {
        return getRoadSection(1);
    }

    public boolean isBeginOfFirstRoadSection() {
        return getBeginOfRoadSection(0);
    }

    public boolean isBeginOfSecondRoadSection() {
        return getBeginOfRoadSection(1);
    }

    public void setFirstRoadSection(RoadSection firstRoadSection, boolean beginOfFirstRoadSection) {
        getRoadSections().set(0, firstRoadSection);
        getBeginOfRoadSections().set(0,beginOfFirstRoadSection);
               
        if(beginOfFirstRoadSection){
            firstRoadSection.setBeginJunction((Junction)this);
        }else{
            firstRoadSection.setEndJunction((Junction)this);
        }
    }

    public void setSecondRoadSection(RoadSection secondRoadSection, boolean beginOfSecondRoadSection) {
        getRoadSections().set(1, secondRoadSection);
        getBeginOfRoadSections().set(1,beginOfSecondRoadSection);

        if(beginOfSecondRoadSection){
            secondRoadSection.setBeginJunction((Junction)this);
        }else{
            secondRoadSection.setEndJunction((Junction)this);
        }
    }
    
    @Override
    public void vehiculeEntry(Vehicule v, int roadSection, int wayNumber, int distance, int lateralOffset){
        //System.out.println(v+" "+roadSection+" "+wayNumber+" "+distance+" "+lateralOffset);
        //System.out.println(roadSections);
        getRoadSection(roadSection).vehiculeEntry(v, ((RoadPosition)v.getPosition()).getWayNumber(), distance, lateralOffset);
    }
    
    @Override
    public void fillFrontEnvironment(VehiculeEnvironment environment, int beginRoadSection, int beginWayNumber, int endRoadSection, int endWayNumber, int distanceToJunction, int nbVehiculeToFind){
        roadSections.get(endRoadSection).fillFrontEnvironment(environment, endWayNumber, distanceToJunction, nbVehiculeToFind);
    }
    
    @Override
    public void fillBackEnvironment(VehiculeEnvironment environment, int roadSection, int wayNumber, int distanceToJunction, int nbVehiculeToFind){  
        roadSections.get((roadSection+1)%2).fillBackEnvironment(environment, wayNumber, distanceToJunction, nbVehiculeToFind);
    }

    @Override
    public void fillFrontEnvironment(VehiculeEnvironment environment, int vehiculeIndex, int beginRoadSection, int beginWayNumber, int endRoadSection, int endWayNumber, int distanceToJunction, int nbVehiculeToFind) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
            
    
}
