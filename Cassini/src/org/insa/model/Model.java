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

import org.insa.model.items.ControlUnitsModel;
import org.insa.model.items.RoadsModel;
import org.insa.model.items.VehiclesModel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Model
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root(name = "cassini")
public class Model {
    /**
     * roads network
     */
    @Element
    private RoadsModel roadModel;
    /**
     * vehicles
     */
    @Element
    private VehiclesModel vehiclesModel;
    
    
    /**
     * traffic control unit : traffic light management, priority management,
     * incidents dectection (accident, congestion,...)
     */
    private ControlUnitsModel controlUnitsModel;
    
    
    public Model(){
    }
    
    
    /*
    * getters and setters
    */

    public RoadsModel getRoadModel() {
        return roadModel;
    }

    public void setRoadModel(RoadsModel roadModel) {
        this.roadModel = roadModel;
    }

    public VehiclesModel getVehiclesModel() {
        return vehiclesModel;
    }

    public void setVehiclesModel(VehiclesModel vehiclesModel) {
        this.vehiclesModel = vehiclesModel;
    }

    public void setControlUnitsModel(ControlUnitsModel controlUnitsModel) {
        this.controlUnitsModel = controlUnitsModel;
    }

    public ControlUnitsModel getControlUnitsModel() {
        return controlUnitsModel;
    }
    
    
}
