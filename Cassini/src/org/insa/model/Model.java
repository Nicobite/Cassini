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
package org.insa.model;

import org.insa.model.items.ControlUnitsModel;
import org.insa.model.items.RoadsModel;
import org.insa.model.items.VehiclesModel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
    
    private VehiclesModel drivingVehiclesModel;
    
    
    /**
     * traffic control unit : traffic light management, priority management,
     * incidents detection (accident, congestion,...)
     */
    private ControlUnitsModel controlUnitsModel;
    
    /**
     * default constructor
     */
    public Model(){
        vehiclesModel = new VehiclesModel();
        drivingVehiclesModel = new VehiclesModel();
        roadModel = new RoadsModel();
        controlUnitsModel = new ControlUnitsModel();
    }
    
    /**
     * Clear the full model
     */
    public void clear() {
        roadModel.clear();
        vehiclesModel.clear();
        drivingVehiclesModel.clear();
    }
    
    /**
     * Get the number of vehicles
     * @return number of vehicles
     */
    public int getNbVehicles(){
        return this.vehiclesModel.getVehicles().size();
    }
    
    /**
     * Get the number of driving vehicles
     * @return Number of driving vehicles
     */
    public int getNbDrivingVehicles(){
        return this.drivingVehiclesModel.getVehicles().size();
    }
    
    /**
     * Get road model
     * @return Road model
     */
    public RoadsModel getRoadModel() {
        return roadModel;
    }
    
    /**
     * Get vehicles model
     * @return Vehicles model
     */
    public VehiclesModel getVehiclesModel() {
        return vehiclesModel;
    }
    
    /**
     * Get control units model
     * @return Control units model
     */
    public ControlUnitsModel getControlUnitsModel() {
        return controlUnitsModel;
    }
    
    /**
     * Get driving vehicles model
     * @return Driving vehicles model
     */
    public VehiclesModel getDrivingVehiclesModel() {
        return drivingVehiclesModel;
    }
    
    /**
     * Set road model
     * @param roadModel New road model
     */
    public void setRoadModel(RoadsModel roadModel) {
        this.roadModel = roadModel;
    }

    /**
     * Set vehicles model
     * @param vehiclesModel New vehicles model
     */
    public void setVehiclesModel(VehiclesModel vehiclesModel) {
        this.vehiclesModel = vehiclesModel;
    }
    
    /**
     * Set control units model
     * @param controlUnitsModel New control units model
     */
    public void setControlUnitsModel(ControlUnitsModel controlUnitsModel) {
        this.controlUnitsModel = controlUnitsModel;
    }
    
    /**
     * Set driving vehicles model
     * @param drivingVehiclesModel New driving vehicles model
     */
    public void setDrivingVehiclesModel(VehiclesModel drivingVehiclesModel) {
        this.drivingVehiclesModel = drivingVehiclesModel;
    }
}
