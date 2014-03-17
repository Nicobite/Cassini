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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.insa.core.vehicle.Vehicle;
import org.insa.model.items.RoadsModel;
import org.insa.model.items.VehiclesModel;
import org.junit.Test;

/**
 *
 * @author Juste Abel Oueadraogo &  Guillaumape Garzone & François Aïssaoui &  Thomapas Thiebaud
 Class TestXmaplParser
 */
public class TestXmlParser {
    XmlParser parser;
    RoadsModel map;
    VehiclesModel vehicles;
    @Test
    public void shouldReadOsmData(){
        parser = new XmlParser();
        try {
            map = parser.readOsmData(new File("data/osm/insa.osm"));
            
        } catch (Exception ex) {
            Logger.getLogger(TestXmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void shouldSaveMapData(){
        try {
            parser = new XmlParser();
            parser.saveMapData(map, new File("data/maps/insa.map.xml"));
        } catch (Exception ex) {
            Logger.getLogger(TestXmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void shouldSaveVehiclesData(){
        Vehicle v1 = new Vehicle();
        v1.setLength(10);
        v1.setMaxSpeed(50);
        
        Vehicle v2 = new Vehicle();
        v2.setLength(10);
        v2.setMaxSpeed(50);
        
        vehicles = new VehiclesModel();
        vehicles.addVehicle(v1).addVehicle(v2);
        try {
            parser = new XmlParser();
            parser.saveVehiclesData(vehicles, new File("data/vehicles/test.veh.xml"));
        } catch (Exception ex) {
            Logger.getLogger(TestXmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void shouldReadVehiclesDataFromXml(){
        try {
            parser.readVehiclesData(new File("vehicles/test.veh.xml"));
        } catch (Exception ex) {
            Logger.getLogger(TestXmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
