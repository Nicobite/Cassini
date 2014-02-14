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

package controler;

import model.GlobalModel;
import model.vehicule.Vehicule;

/**
 *
 * @author jonathan
 */
public class CarControler {

    // Time interval of discretisation (ms)
    private final int dt = 20;

    private GlobalModel globalModel;

    public CarControler(GlobalModel model) {
        globalModel = model;
    }

    public synchronized void Refresh(){
        
        //Make decision for each vehicules
    	synchronized(globalModel.getVehicules()){
            for (Vehicule v:globalModel.getVehicules()){
                v.getBehavior().makeDecision(v,dt);
            }
    	}


        //Move each vehicules
    	synchronized(globalModel.getVehicules()){
            for (Vehicule v:globalModel.getVehicules()){
                    v.updatePosition(dt);
            }
    	}
        

        //Erase vehicules at the end of the road
        synchronized(globalModel.getVehicules()){
            for (int v=0; v<globalModel.getNumberOfVehicules(); v++){
                 if(globalModel.getVehicules().get(v).isArrived()){
                    globalModel.eraseVehicule( globalModel.getVehicules().get(v) );
                    v--;
                }
            }
        }

        //Sort all the vehicules in each road section \o/
        globalModel.sortVehicules();
        
        /*for(Road road:globalModel.getRoads()){
            for(RoadSection roadSection:road.getRoadSections()){
                System.out.println(roadSection.getVehicules());
            }
        }*/
        
        /*for(Junction j : globalModel.getJunctions()){
            System.out.println(j.getVehicules());
        }
        System.out.println("");*/
        
        //System.out.println(globalModel.getRoad(0).getRoadSection(0).getVehicules());

    	}

    public long getDt() {
        return this.dt;
    }
}
