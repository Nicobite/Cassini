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
package old.project2012.model.vehicule;

import old.project2012.model.junction.Junction;

/**
 *
 * @author gabriel
 */
public class VehiculeEnvironment {
    
    private Vehicule vehiculeFront;
    private Vehicule vehiculeFrontLeft;
    private Vehicule vehiculeFrontRight;
    private Vehicule vehiculeBack;
    private Vehicule vehiculeBackLeft;
    private Vehicule vehiculeBackRight;
    
    private int distVehiculeFront=-1;
    private int distVehiculeFrontLeft=-1;
    private int distVehiculeFrontRight=-1;
    private int distVehiculeBack=-1;
    private int distVehiculeBackLeft=-1;
    private int distVehiculeBackRight=-1;
    
    
    private Junction nextJunction;
    private int indexForNextJunction=-1;
    private int distNextJunction=-1;
    
    public VehiculeEnvironment(){
        
    }
    
    public void setVehiculeFront(Vehicule vehiculeFront, int distVehiculeFront){
        this.vehiculeFront = vehiculeFront;
        this.distVehiculeFront = distVehiculeFront;
    }
    
    public void setVehiculeFrontLeft(Vehicule vehiculeFrontLeft, int distVehiculeFrontLeft){
        this.vehiculeFrontLeft = vehiculeFrontLeft;
        this.distVehiculeFrontLeft = distVehiculeFrontLeft;
    }
    
    public void setVehiculeFrontRight(Vehicule vehiculeFrontRight, int distVehiculeFrontRight){
        this.vehiculeFrontRight = vehiculeFrontRight;
        this.distVehiculeFrontRight = distVehiculeFrontRight;
    }
    
    public void setVehiculeBack(Vehicule vehiculeBack, int distVehiculeBack){
        this.vehiculeBack = vehiculeBack;
        this.distVehiculeBack = distVehiculeBack;
    }
    
    public void setVehiculeBackLeft(Vehicule vehiculeBackLeft, int distVehiculeBackLeft){
        this.vehiculeBackLeft = vehiculeBackLeft;
        this.distVehiculeBackLeft = distVehiculeBackLeft;
    }
    
    public void setVehiculeBackRight(Vehicule vehiculeBackRight, int distVehiculeBackRight){
        this.vehiculeBackRight = vehiculeBackRight;
        this.distVehiculeBackRight = distVehiculeBackRight;
    }

    public Vehicule getVehiculeFront() {
        return vehiculeFront;
    }

    public Vehicule getVehiculeFrontLeft() {
        return vehiculeFrontLeft;
    }

    public Vehicule getVehiculeFrontRight() {
        return vehiculeFrontRight;
    }

    public Vehicule getVehiculeBack() {
        return vehiculeBack;
    }

    public Vehicule getVehiculeBackLeft() {
        return vehiculeBackLeft;
    }

    public Vehicule getVehiculeBackRight() {
        return vehiculeBackRight;
    }

    public int getDistVehiculeFront() {
        return distVehiculeFront;
    }

    public int getDistVehiculeFrontLeft() {
        return distVehiculeFrontLeft;
    }

    public int getDistVehiculeFrontRight() {
        return distVehiculeFrontRight;
    }

    public int getDistVehiculeBack() {
        return distVehiculeBack;
    }

    public int getDistVehiculeBackLeft() {
        return distVehiculeBackLeft;
    }

    public int getDistVehiculeBackRight() {
        return distVehiculeBackRight;
    }

    public Junction getNextJunction() {
        return nextJunction;
    }

    public void setNextJunction(Junction nextJunction) {
        this.nextJunction = nextJunction;
    }

    public int getIndexForNextJunction() {
        return indexForNextJunction;
    }

    public void setIndexForNextJunction(int indexForNextJunction) {
        this.indexForNextJunction = indexForNextJunction;
    }

    public int getDistNextJunction() {
        return distNextJunction;
    }

    public void setDistNextJunction(int distNextJunction) {
        this.distNextJunction = distNextJunction;
    }
    
    
    
}
