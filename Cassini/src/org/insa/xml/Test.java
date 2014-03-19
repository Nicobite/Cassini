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
import org.insa.model.items.RoadsModel;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Test
 */
public class Test {
    public static void main(String args[]){
         XmlParser parser = new XmlParser();
        try {
            RoadsModel map = parser.readOsmData(new File("data/osm/insa.osm"));
            parser.saveMapData(map, new File("data/maps/insa.map.xml"));
            System.out.println(map);
            System.out.println("complete");
        } 
        catch (Exception ex) {
        }
    }
}
