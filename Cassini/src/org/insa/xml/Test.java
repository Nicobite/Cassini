/*
* Copyright 2014 Juste Abel Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
package org.insa.xml;

import java.io.File;
import org.insa.core.roadnetwork.Node;
import org.insa.model.items.RoadsModel;
import org.insa.xml.osm.OsmRoot;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Thiebaud Class Test
 */
public class Test {
    
    public static void main(String args[]) throws Exception {
        XmlParser parser = new XmlParser();
        
        OsmRoot r = parser.readOsmData(new File("data/osm/map.osm"));
        //parser.saveMapData(r.buildRoadModel(), new File("data/maps/map.map.xml"));
        RoadsModel m = parser.readMapData(new File("data/maps/map.map.xml"));
        System.out.println("Map "+m.getNodes().size()+","+m.getRoads().size());
        System.out.println("Osm "+r.buildRoadModel().getNodes().size()+","+r.buildRoadModel().getRoads().size());
        System.err.println("\n\n");
        System.out.println("...");
        r.buildRoadModel().getJunctions();
        Node n1, n2;
        for(int i=0; i<m.getRoadNumber();i++){
            //System.err.println(n.getId());
            n1 = m.getNodes().get(i);
            n2 = r.buildRoadModel().getNodes().get(i);
            System.err.println(n1.getId()+" : "+n2.getId());
            /*for(int j=0; j<n1.getRoads().size();j++)
            System.out.println(n1.getRoads().get(j).getId());*/
            System.out.println("-------");
            for(int j=0; j<n2.getRoads().size();j++)
                System.out.println(n2.getRoads().get(j).getId());
            System.out.println("Fin N2-------");
        }
        
        /*for(TrafficLight l : r.getTrafficLightFromRoads()){
        System.out.println(l.getId());
        }
        /* System.out.println("Map traffic lights");
        RoadsModel map = parser.readMapData(new File("data/maps/insa.map.xml"));
        for(TrafficLight l : map.getTrafficLightFromRoads()){
        System.out.println(l.getId());
        }
        /* RoadsModel map = parser.readOsmData(new File("data/osm/map.osm")).buildRoadModel();
        parser.saveMapData(map, new File("data/maps/map.map.xml"));
        RoadsModel map2 = parser.readMapData(new File("data/maps/map.map.xml"));
        
        Section org = null;
        GraphicSection dest = map.getRoads().get(10).getLastSection();
        int i=0;
        for(Road road : map.getRoads()){
        
        /* if(road.getId() == 14689628 ){
        org = road.getFirstSection().getSection();
        }
        
        if(road.getId() == 4299503)
        dest = road.getLastSection();
        /*for( i=0; i< road.getGraphicRoad().getSections().size();i++){
        System.out.print(map.getRoads().get(i).getFirstSection().getSuccessors().size()+"\t");
        System.out.println(map2.getRoads().get(i).getFirstSection().getSuccessors().size());
        
        }*/
    }
    //org = map2.getRoads().get(1).getFirstSection();
    //dest = map2.getRoads().get(1).getLastSection();
    /*  Mission m = new Mission(map2, org, dest.getSection());
    m.getInitialLane();
    AStar a = new AStar(map2, org, dest.getSection());
    Road r = a.getShortestPath();
    
    System.out.println(r.getGraphicRoad().getSections().size());
    } catch (Exception ex) {
    ex.printStackTrace();
    }*/
}

