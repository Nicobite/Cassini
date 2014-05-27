/*
 * Copyright 2014 Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
package org.insa.controller;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.insa.model.Model;
import org.insa.xml.XmlParser;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Thiebaud Class TestSimulation
 */
public class TestSimulation {

    public static void main(String args[]) {

        try {
            SimulationController ctrl = new SimulationController(500, true);
            ctrl.setModel(loadModel());
            ctrl.start();

        } catch (Exception ex) {
            Logger.getLogger(TestSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Load a model
     * @return Model loaded
     * @throws Exception All exceptions
     */
    public static Model loadModel() throws Exception {
        Model model = new Model();
        Scanner sc = new Scanner(System.in);
        //System.out.print("Enter the name (without extension) of osm file : ");
        //String pathMap = "data/osm/" + sc.nextLine() + ".osm";
        String pathMap = "data/maps/map.map.xml";
        System.out.print("Enter the name (without extension) of vehicle xml file : ");
        String pathVhc = "data/vehicles/" + sc.nextLine() + ".vhc.xml";
        try {
            model.setVehiclesModel((new XmlParser()).readVehiclesData(new File(pathVhc)));
            //model.setRoadModel((new XmlParser()).readOsmData(new File(pathMap)));
            model.setRoadModel((new XmlParser()).readMapData(new File(pathMap)));
        } catch (Exception ex) {
            Logger.getLogger(TestSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }
}
