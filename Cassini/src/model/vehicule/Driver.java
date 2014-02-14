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

package model.vehicule;

/**
 *
 * @author gcharlem
 */
public class Driver {
    private float annoyance = 0;
    private float maxAnnoyance = 5;
    private boolean overtake = false;

    public Driver(){


    }

    /**
     * @return the annoyance
     */
    public float getAnnoyance() {
        return annoyance;
    }

    /**
     * @param annoyance the annoyance to set
     */
    public void setAnnoyance(float annoyance) {
        this.annoyance = annoyance;
    }

    public void increaseAnnoyance (float annoyanceSensibility, int dt){
        if (this.annoyance < this.maxAnnoyance)
            this.annoyance += annoyanceSensibility/(1000f/dt);
    }

    public void decreaseAnnoyance (float annoyanceSensibility, int dt){
        if (this.annoyance>0)
            this.annoyance -= annoyanceSensibility/(1000f/dt);
    }

    public boolean isOvertake() {
        return overtake;
    }

    public void setOvertake(boolean overtake) {
        this.overtake = overtake;
    }

}
