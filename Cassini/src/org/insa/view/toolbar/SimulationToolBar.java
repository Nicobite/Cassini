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
package org.insa.view.toolbar;

import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.view.menuelement.ToolBarToggleButton;

/**
 *
 * @author Thiebaud Thomas
 */
public class SimulationToolBar extends CustomToolBar {

    /**
     * Default constructor
     */
    public SimulationToolBar() {
        super(Orientation.HORIZONTAL, "bottom-tool-bar");  
        this.add(new ToolBarToggleButton("backward",this));
        this.add(new ToolBarToggleButton("play",this));
        this.add(new ToolBarToggleButton("pause",this));
        this.add(new ToolBarToggleButton("stop",this));
        this.add(new ToolBarToggleButton("forward",this));
    }
    
    @Override
    public void handle(MouseEvent t) {
        ToolBarToggleButton button = (ToolBarToggleButton)t.getSource();
        switch(button.getImageName()) {
            case "play" :
                MainController.getInstance().performPlaySimulation();
                break;
            case "pause" : 
                MainController.getInstance().performPauseSimulation();
                break;
            case "stop" :
                MainController.getInstance().performStopSimulation();
                break;
            case "backward" :
                MainController.getInstance().performBackwardSimulation();
                break;
            case "forward" :
                MainController.getInstance().performForwardSimulation();
                break;
        } 
    }
}
