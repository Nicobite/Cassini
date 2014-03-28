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
package org.insa.xml;

import java.io.File;
import org.insa.core.enums.RoadType;
import org.insa.core.roadnetwork.Lane;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.model.items.RoadsModel;
import org.insa.xml.osm.entities.OsmBound;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui &
 * Thomas Thiebaud Class Test
 */
public class Test {

    public static void main(String args[]) {
        XmlParser parser = new XmlParser();
        try {
           RoadsModel map = parser.readOsmData(new File("data/osm/map.osm"));
           //parser.saveMapData(map, new File("data/maps/jean-jaures.map.xml"));
         for(Road r : map.getRoads()){
             if(r.getType()== RoadType.ROUNDABOUT){
                 System.out.println("road n° "+r.getId()+"\n");
                 for(Section sect : r.getSections()){
      
                     for(Lane l : sect.getForwardLanes()){
                        System.out.println(l.getDirection()); 
                     }
          
                     for(Lane l : sect.getBackwardLanes()){
                        System.out.println(l.getDirection()); 
                     }
                 }
             }
         }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
