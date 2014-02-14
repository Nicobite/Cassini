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

import model.Coordinates;

/**
 *
 * @author jonathan
 */
public abstract class VehiculePosition {
    
    public abstract float getAngle();
    public abstract Coordinates getVehiculeCoordinates();
    
}
