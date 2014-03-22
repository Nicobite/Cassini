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
public class Coordinates {
    private int x;
    private int y;
    
    public Coordinates(int x, int y){
        this.x=x;
        this.y=y;
    }
    
    public void setX(int x){
        this.x=x;
    }
    
    public int getX(){
        return this.x;
    }
    
    public void setY(int y){
        this.y=y;
    }
            
    public int getY(){
        return this.y;
    }
    
    @Override
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
}
