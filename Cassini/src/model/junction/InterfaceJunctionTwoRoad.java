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

import model.road.RoadSection;

/**
 *
 * @author gabriel
 */
public interface InterfaceJunctionTwoRoad {
    
    public RoadSection getFirstRoadSection();
    public RoadSection getSecondRoadSection();
    public boolean isBeginOfFirstRoadSection();
    public boolean isBeginOfSecondRoadSection();
    public void setFirstRoadSection(RoadSection firstRoadSection, boolean beginOfFirstRoadSection);
    public void setSecondRoadSection(RoadSection secondRoadSection, boolean beginOfSecondRoadSection);
}
