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
package org.insa.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Model
 */
@Root(name = "cassini")
public class Model {
    /**
     * roads network
     */
    @Element
    private MapModel roadModel;
    /**
     * vehicles
     */
    @Element
    private VehiclesModel vehiclesModel;
    
    
    public Model(){
    }

    public MapModel getRoadModel() {
        return roadModel;
    }

    public void setRoadModel(MapModel roadModel) {
        this.roadModel = roadModel;
    }

    public VehiclesModel getVehiclesModel() {
        return vehiclesModel;
    }

    public void setVehiclesModel(VehiclesModel vehiclesModel) {
        this.vehiclesModel = vehiclesModel;
    }
}
