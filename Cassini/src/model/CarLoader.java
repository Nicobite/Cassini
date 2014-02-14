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

package model;

import java.util.logging.Level;
import java.util.logging.Logger;

import view.InfoView;
import view.MainView;

/**
 * Permet d'ajouter dynamiquement une a une les voitures
 * @author gblanc
 */
public class CarLoader extends Thread {

    private GlobalModel model;
    private boolean running = true;
    private boolean suspended;
    private int nbcar, maxcar;
    private MainView mainView;

    public CarLoader(GlobalModel mod, MainView mainView) {
        super("Car Loader Thread");
        this.model = mod;
        this.mainView = mainView;
        this.suspended = true;
        maxcar = 100;
        model.loadVehicules();
    }

    @Override
    public void run() {
        while(running){
                try {
                    synchronized(this){
                        while(suspended)
                            wait();
                    }
                    maxcar = ((InfoView)mainView.getInfoView()).getnbCar();
                    if(nbcar < maxcar){
                    	//model.newVehicule(((InfoView)mainView.getInfoView()).isSpeedStart());
                    	nbcar ++;
                    	((InfoView)this.mainView.getInfoView()).setLblNombreDeVoiture("Nb voiture: "+nbcar + "/" + maxcar);
                    	
                    }
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CarLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

        public synchronized void pause(){
                suspended = true;
        }

        public synchronized void restart(){
                suspended = true;
                notifyAll();
        }

        public synchronized void stopThread(){
                this.running=false;
        }

        public boolean isSuspended(){
                return suspended;
        }

        public synchronized void play() {
            this.suspended = false;
            this.notify();
        }


}
