/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

/**
 * 
 * @author Jean
 */
public class Segment {
    
    public static boolean belongToLine(Coordinates point, Coordinates segmentBegin, Coordinates segmentEnd){
        boolean belong;
        belong = ((point.getX()-segmentBegin.getX())*(segmentBegin.getY()-segmentEnd.getY()) == ((point.getY()-segmentBegin.getY())*(segmentBegin.getX()-segmentEnd.getX())));
        return belong;
    }
    
    public static boolean belongToSegment(Coordinates point, Coordinates segmentBegin, Coordinates segmentEnd){
        boolean belong;
        belong =    belongToLine(point,segmentBegin,segmentEnd)
                    && (point.getY() < Math.max(segmentBegin.getY(),segmentEnd.getY()))
                    && (point.getX() < Math.max(segmentBegin.getX(),segmentEnd.getX()))
                    && (point.getY() > Math.min(segmentBegin.getY(),segmentEnd.getY()))
                    && (point.getX() < Math.min(segmentBegin.getX(),segmentEnd.getX()));
        return belong;
    }
    
}
