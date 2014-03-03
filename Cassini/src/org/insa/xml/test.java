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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.insa.model.RoadsModel;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class test
 */
public class test {
    public static void main(String args[]){
        try {
            /*
            * Read data from openstreetmap and save it to xml 
            */
            XmlParser p = new XmlParser();
            RoadsModel roads = p.readOsmData(new File("data/osm/insa.osm"));
            //save data in xml file
            p.saveMapData(roads, new File("data/maps/insa.map.xml"));
         
            
        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
