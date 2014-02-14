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

package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.GlobalModel;
import view.MainView;
import view.TraficView;
import xml.ParserLoadingMap;
import controler.CarControler;

/**
 *
 * @author gblanc
 */
public class MainThread extends Thread {

private boolean running = true;
private boolean suspended;

TraficView trafic;
MainView mainView ;
CarControler controler;
//CarLoader loader ;
GlobalModel globalModel;
ParserLoadingMap parser;
private int nbcar, maxcar;
private int timebeforecar,timecar;
private long refresh;

public MainThread(String name) {
    super(name);

    globalModel = new GlobalModel();

    /*parser= new ParserLoadingMap(globalModel);
    parser.parseFile("data/xml/Demo.xml");*/
     
    /* 
    //Test OSM
    OSMModel osmModel = new OSMModel();
    ParserOSM parserOSM= new ParserOSM(osmModel);
    parserOSM.parseFile("data/xml/osm/mapSud.osm");
    //System.out.println(osmModel);
    osmModel.toGlobalModel(globalModel);
    //globalModel.loadVehicules();
    //System.out.println(globalModel.getRoads());
    //Fin test OSM
    */   
     
     /*ParserLoadingTrafic parser= new ParserLoadingTrafic(globalModel);
     parser.parseFile("data/xml/trafic/Demo.xml");*/
      
     this.running = true;
     this.suspended = true;
     trafic = new TraficView(globalModel);
     
     mainView = new MainView(trafic, this, globalModel);
     trafic.setMainView(mainView);
     controler = new CarControler(globalModel);
     
     maxcar = 100;
     timecar = 3000;
     timebeforecar = 0;
     globalModel.loadVehicules();
     
    // loader = new CarLoader(globalModel, mainView);
     //loader.start();
}

    public void run(){
        long beginDate, currentDate,elapseTime;
         while (running) {
            try {
                 refresh = (long)(controler.getDt()/mainView.getTimeScale());
                 beginDate = System.currentTimeMillis();

                 if (!suspended){
                    controler.Refresh(); 

                    timebeforecar += refresh;

                    /*if(nbcar < maxcar && timebeforecar > timecar){
                       //globalModel.newVehicule(true);
                       nbcar ++;
                       timebeforecar = 0;
                       ((InfoView)this.mainView.getInfoView()).setLblNombreDeVoiture("Nb voiture: "+nbcar);

                    }*/
                 }
                 trafic.repaint();
                 currentDate = System.currentTimeMillis();
                 elapseTime = currentDate-beginDate;
                 //System.out.println(elapseTime);
                 if(refresh-elapseTime >= 0)
                   sleep(refresh-elapseTime); 
                 else
                   System.out.println("lag "+elapseTime);

               } catch (InterruptedException ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }

        }
        
    }
        public synchronized void pause(){
                suspended = true;
                //loader.pause();
        }

        public synchronized void play(){
                suspended = false;
               // loader.play();
                //this.notify();
        }


        public synchronized void restart(){   
                globalModel.clearVehicules();
                globalModel.loadVehicules();
               // loader = new CarLoader(globalModel, mainView);
                this.pause();
        }

        public synchronized void stopThread(){
                this.running=false;
        }

        public boolean isSuspended(){
                return suspended;
        }

}
