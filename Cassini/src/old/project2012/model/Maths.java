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
package old.project2012.model;

/**
 *
 * @author gabriel
 */
public class Maths {

    static public final float PI2 = (float)Math.PI*2; 
    static public final float PI = (float)Math.PI; 
    static public final float PI_2 = (float)Math.PI/2; 
    static public final float PI_4 = (float)Math.PI/4; 

    /**
     * Get absolute angle between two points
     * @param c1 Coordinate
     * @param c2 Coordinate
     * @return float : angle
     */
    static public float angle(Coordinates c1, Coordinates c2){  
            return (float)Math.atan2(c2.getY()-c1.getY(),c2.getX()-c1.getX());
    }
    
    /**
     * Get absolute distance between two points
     * @param c1 First point
     * @param c2 Second point
     * @return int distance (mm)
     */
    static public int distance(Coordinates c1, Coordinates c2){
        float x=c2.getX()-c1.getX();
        float y=c2.getY()-c1.getY();
        return (int) Math.sqrt((x*x+y*y));
    }
    
    
    /**
     * Get the arrival coordinates for an initial coordinate translated by a vector given by an angle and a distance 
     * @param beginCoordinate Coordinate
     * @param angle Angle
     * @param distance Distance
     * @return Coordinates
     */
    static public Coordinates findArrivalCoordinateFromVector(Coordinates beginCoordinate, float angle, int distance){
    
        return new Coordinates( (int)(beginCoordinate.getX() + (float)Math.cos(angle)*distance), 
                                (int)(beginCoordinate.getY() + (float)Math.sin(angle)*distance) );
        
    }
    
     /**
     * Get the arrival coordinates for an initial coordinate translated by a vector given by an angle, a distance and a perpendicular offset
     * @param beginCoordinate Coordinate
     * @param angle Angle
     * @param distance Distance
     * @param offset Offset
     * @return Coordinates
     */
    static public Coordinates findArrivalCoordinateFromVectorAndOffset(Coordinates beginCoordinate, float angle, int distance, int offset){
        
        Coordinates c = findArrivalCoordinateFromVector(beginCoordinate, angle, distance);
        
        return findArrivalCoordinateFromVector(c, angle + Maths.PI_2, offset);
        
    }
    
    static public float plusSolutionOfQuadraticFormula(float a, float b, float c){
        float delta=b*b-4*a*c;
        
        if(delta>=0){
            return (float)((-b+Math.sqrt(delta))/(2*a));
        }else{
            System.out.println("Deltat négatif");
            return -1000;
            //throw new UnsupportedOperationException("Deltat négatif");
        }
        
    }
    
    static public boolean betweenModulo(int n, int a, int b, int mod){
        if(a<=b){
            return a<n && n<b;
        }else{
            return (a<n && n<mod) || (0<=n && n<b);
        }
    }

}
