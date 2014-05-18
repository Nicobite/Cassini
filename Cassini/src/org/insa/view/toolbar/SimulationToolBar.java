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
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.view.menuelement.ToolBarToggleButton;

/**
 *
 * @author Thiebaud Thomas
 */
public class SimulationToolBar extends CustomToolBar {

    private final ToolBarToggleButton backwardButton = new ToolBarToggleButton("backward",this);
    private final ToolBarToggleButton playButton = new ToolBarToggleButton("play",this);
    private final ToolBarToggleButton pauseButton = new ToolBarToggleButton("pause",this);
    private final ToolBarToggleButton stopButton = new ToolBarToggleButton("stop",this);
    private final ToolBarToggleButton forwardButton = new ToolBarToggleButton("forward",this);
    
    /**
     * Default constructor
     */
    public SimulationToolBar() {
        super(Orientation.HORIZONTAL, "bottom-tool-bar");  
        
        backwardButton.setTooltip(new Tooltip("Ralentir la simulation"));
        playButton.setTooltip(new Tooltip("Lancer la simulation"));
        pauseButton.setTooltip(new Tooltip("Suspendre la simulation"));
        stopButton.setTooltip(new Tooltip("Arreter la simulation"));
        forwardButton.setTooltip(new Tooltip("Accélérer la simulation"));
        
        this.add(backwardButton);
        this.add(playButton);
        this.add(pauseButton);
        this.add(stopButton);
        this.add(forwardButton);
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
