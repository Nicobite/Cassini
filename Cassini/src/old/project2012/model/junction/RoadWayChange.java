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

import old.project2012.model.junction.RoadSign.Sign;
import old.project2012.model.road.RoadSection;

/**
 *
 * @author gabriel
 */
public class RoadWayChange extends CrossRoad implements InterfaceJunctionTwoRoad {

    public RoadWayChange(){
       super();
       roadSections.add(null);
       roadSections.add(null);
       beginOfRoadSections.add(null);
       beginOfRoadSections.add(null);
       roadSigns.add(new RoadSign(Sign.NONE));
       roadSigns.add(new RoadSign(Sign.NONE));

   }
   
    public RoadWayChange(RoadSection firstRoadSection, boolean beginOfFirstRoadSection, RoadSection secondRoadSection, boolean beginOfSecondRoadSection) {
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


   
}
