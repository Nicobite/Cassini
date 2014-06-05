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
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.mission.AStar;
import org.insa.mission.Mission;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicSection;
import org.insa.xml.osm.OsmRoot;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Thiebaud Class Test
 */
public class Test {
    
    public static void main(String args[]) throws Exception {
        XmlParser parser = new XmlParser();
        try{
           // RoadsModel map = parser.readOsmData(new File("data/osm/map.osm")).buildRoadModel();
            //parser.saveMapData(map, new File("data/maps/map.map.xml"));
            RoadsModel map = parser.readMapData(new File("data/maps/map.map.xml"));
            
            Section org = null;
            GraphicSection dest =null;
            org = map.getRoads().get(10).getFirstSection().getSection();
            dest = map.getRoads().get(10).getLastSection();
            Mission m = new Mission(map, org, dest.getSection());
            AStar a = new AStar(map, org, dest.getSection());
            Road r = a.getShortestPath();
            
            System.out.println(r.getGraphicRoad().getSections().size());
            System.out.println(map.getRoads().get(10).getGraphicRoad().getSections().size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

