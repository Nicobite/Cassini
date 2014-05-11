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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.insa.controller.MainController;
import org.insa.core.driving.Vehicle;
import org.insa.model.items.VehiclesModel;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class TestVehicle
 */
public class TestVehicle {
    public static void main(String args[]) throws InterruptedException{
        //MainController.getInstance().performDisplayJConsole();
        VehiclesModel model = new VehiclesModel();
        Vehicle v;
           for(int i=0; i<30; i++){
            v = new Vehicle();
            v.setMaxSpeed(40);
            v.setMaxAcceleration(8);
            v.setMaxDeceleration(7);
            v.setLength(1);
            model.addVehicle(v);
            //Thread.sleep(1000000000);
        }
        for(int i=0; i<20; i++){
            v = new Vehicle();
            v.setMaxSpeed(50);
            v.setMaxAcceleration(10);
            v.setMaxDeceleration(10);
            v.setLength(1);
            model.addVehicle(v);
            //Thread.sleep(1000000000);
        }
          for(int i=0; i<30; i++){
            v = new Vehicle();
            v.setMaxSpeed(60);
            v.setMaxAcceleration(12);
            v.setMaxDeceleration(10);
            v.setLength(1);
            model.addVehicle(v);
            //Thread.sleep(1000000000);
        }
           for(int i=0; i<20; i++){
            v = new Vehicle();
            v.setMaxSpeed(70);
            v.setMaxAcceleration(12);
            v.setMaxDeceleration(10);
            v.setLength(1);
            model.addVehicle(v);
            //Thread.sleep(1000000000);
        }
            XmlParser p = new XmlParser();
        try {
            p.saveVehiclesData(model, new File("a.vhc.xml"));
        } catch (Exception ex) {
            Logger.getLogger(TestVehicle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
