/*
 * Copyright 2014 Abel Juste Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.mission.AStar;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicSection;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas
 * Thiebaud Class Test
 */
public class Test {

    public static void main(String args[]) {
        XmlParser parser = new XmlParser();
        try {
            //RoadsModel map = parser.readMapData(new File("data/maps/map.map.xml"));
            RoadsModel map = parser.readOsmData(new File("data/osm/map.osm"));
            parser.saveMapData(map, new File("data/maps/map.map.xml"));
            RoadsModel map2 = parser.readMapData(new File("data/maps/map.map.xml"));
            
            Section org = null;
            GraphicSection dest = map.getRoads().get(10).getLastSection();
            int i=0;
            for(Road road : map2.getRoads()){
                
                if(road.getId() == 14689628 ){
                    org = road.getFirstSection();
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
            AStar a = new AStar(/*map2,*/ org, dest.getSection());
            Road r = a.getShortestPath();
            
            System.out.println(r.getGraphicRoad().getSections().size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
