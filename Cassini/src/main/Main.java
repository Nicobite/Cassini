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

/**
 *
 * @author jonathan
 */
public class Main{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //GlobalModel globalModel = new GlobalModel();
        
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
        
        /*ParserLoadingMap parser= new ParserLoadingMap(globalModel);
        parser.parseFile("data/xml/testRoadWayChange.xml");
        
        ParserLoadingTrafic parser= new ParserLoadingTrafic(globalModel);
        parser.parseFile("data/xml/trafic/HighWay.xml");
        
        WriterXML writerXML = new WriterXML(globalModel);
        writerXML.saveGlobalModel("data/xml/testWrite.xml");

        globalModel.loadVehicules();*/
             
        //globalModel.loadJunctions();
             
        /*Thread t = Thread.currentThread();
        TraficView trafic = new TraficView(globalModel);*/
        
        //trafic.setCoordinatesView(new Coordinates((int)(111600000*0.3485700),(int)(111600000*46.7730500)));
           
        /*MainView mainView = new MainView(trafic,globalModel);
        CarControler controler = new CarControler(globalModel);
        while (true) {
            try {
                t.sleep((long)(controler.getDt()/mainView.getTimeScale()));
                trafic.repaint();

                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            if(mainView.isRunning()){
                controler.Refresh();
            }

            if(mainView.toReload()){
                globalModel.clearVehicules();
                globalModel.loadVehicules();
            }
    }
         

    }*/
    	MainThread thread = new MainThread("mainThread");
        thread.start();

    }
}
