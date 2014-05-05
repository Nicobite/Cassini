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
package org.insa.model.items;

import java.util.ArrayList;
import org.insa.core.trafficcontrol.ControlUnit;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class ControlUnitsModel
 * trafic controller and regulator (rafic lights management, incident dectection and mangement,...)
 */
public class ControlUnitsModel{
    /**
     * control units
     */
    private ArrayList<ControlUnit>controlUnits;
    
    private ControlUnitsModel(){
        
    }

    public ArrayList<ControlUnit> getControlUnits() {
        return controlUnits;
    }

    public void setControlUnits(ArrayList<ControlUnit> controlUnits) {
        this.controlUnits = controlUnits;
    }
    
    
}
