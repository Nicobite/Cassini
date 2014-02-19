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

package org.insa.model.data;

import java.util.ArrayList;
 
//TODO change import to org.insa.*
import model.junction.Junction;
import model.junction.TraficLight;
import model.road.Road;
import model.vehicule.Vehicule;

/**
 *
 * @author ouedraog
 * MVC pattern Model
 * Stores simulation global data
 */
public class Model {
    /**
     * Roads
     */
    private ArrayList<Road> roads;
    /**
     * Vehicules present in the simulation
     */
    private ArrayList<Vehicule> vehicules;
    /**
     * 
     */
    private ArrayList<Junction> junctions;
    private ArrayList<TraficLight> traficLights;

    public Model() {
        roads = new ArrayList<>();
        junctions = new ArrayList<>();
        traficLights = new ArrayList<>();
        vehicules = new ArrayList<>();
    }
    
}
