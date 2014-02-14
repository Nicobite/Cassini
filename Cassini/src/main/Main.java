/*
 * Copyright 2014 Abel Juste Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
