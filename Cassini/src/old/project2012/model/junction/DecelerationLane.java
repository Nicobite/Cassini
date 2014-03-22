/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package old.project2012.model.junction;

import java.awt.Polygon;
import old.project2012.model.Coordinates;
import old.project2012.model.Maths;
import old.project2012.model.road.RoadSection;



/**
 *
 * @author Djou
 */
public class DecelerationLane extends CrossRoad{
    private int lengthLine = 3000;
    private int widthLine = 222;
    private int betweenLines = 3500;


    @Override
    public void addRoadSection (RoadSection road, boolean begin) {
        RoadSign rs ;
        if(roadSections.size() >= 2)
        {
            rs = new RoadSign(RoadSign.Sign.DECELERATION_LANE);
        }
        else{
            rs = new RoadSign(RoadSign.Sign.NONE);
        }
        super.roadSigns.add(rs);
        roadSections.add(road);
        beginOfRoadSections.add(begin);
        if (begin) {
            road.setBeginJunction(this);
        }
        else{
            road.setEndJunction(this);
        }
    }
    public int getBetweenLines() {
        return betweenLines;
    }

    public int getLengthLine() {
        return lengthLine;
    }

    public int getWidthLine() {
        return widthLine;
    }
    public RoadSection getBeginRoadSection(){
        return(this.getRoadSection(0));
    }
    public RoadSection getEndRoadSection(){
        return(this.getRoadSection(1));
    }
    public RoadSection getALRoadSection(){
        return(this.getRoadSection(2));
    }
    public boolean getBeginOfBeginRoadSection(){
        return(this.getBeginOfRoadSection(0));
    }
    public boolean getBeginOfEndRoadSection(){
        return(this.getBeginOfRoadSection(1));
    }
    public boolean getBeginOfDLRoadSection(){
        return(this.getBeginOfRoadSection(2));
    }
    
}
