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
package old.project2012.model.road;

/**
 *
 * @author gabriel
 */
public class RoadWay {
    private int width;
    private boolean direction;
    private int speedLimit;

    public RoadWay(int width, boolean direction, int speedLimit){
        this.width=width;
        this.direction=direction;
        this.speedLimit=speedLimit;
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean getDirection() {
        return this.direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public int getSpeedLimit() {
        return this.speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }
    
    
}
