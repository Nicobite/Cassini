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

package org.insa.controller;

import javafx.scene.control.Label;
import org.insa.view.panel.ConfigurationPanel;
import org.insa.view.panel.DefaultPanel;
import org.insa.view.panel.MainPanel;
import org.insa.view.panel.SimulationPanel;

/**
 *
 * @author Thomas Thiebaud
 */
public class MainController {
    
    private static volatile MainController instance = null;
    
    private MainPanel mainPanel = null;
    private SimulationPanel simulationPanel = null;
    private ConfigurationPanel configurationPanel = null;
    
    /**
     * Default private constructor
     */
    private MainController(){
        //Empty for the moment
    }
    
    /**
     * Return the unique instance of the class
     * @return The unique instance of the class
     */
    public static MainController getInstance(){
        if (MainController.instance == null) {
            synchronized(MainController.class) {
                if (MainController.instance == null) {
                    MainController.instance = new MainController();
                }
            }
        }
        return MainController.instance;
    }

    /**
     * Add a reference to MainPanel
     * @param mainPanel 
     */
    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    /**
     * Display default panel 
     */
    public void performDisplayDefaultPanel() {
        mainPanel.setCenter(new DefaultPanel());
    }

    /**
     * Display simulation panel
     */
    public void performDisplaySimulationPanel() {
        //TODO : Simulation panel could be uprade using pattern Singleton
        simulationPanel = new SimulationPanel();
        mainPanel.setCenter(simulationPanel);       
    }

    /**
     * Display configuration panel
     */
    public void performDisplayConfigurationPanel() {
        configurationPanel = new ConfigurationPanel();
        mainPanel.setCenter(configurationPanel);
    }

    /**
     * Display map panel
     */
    public void performDisplayMapPanel() {
        mainPanel.setCenter(new Label("Not implemented yet"));
    }

    /**
     * Start simulation
     */
    public void performPlaySimulation() {
        simulationPanel.setCenter(new Label("Not implemented yet"));
    }

    /**
     * Suspend simulation
     */
    public void performPauseSimulation() {
        simulationPanel.setCenter(new Label("Not implemented yet"));
    }

    /**
     * Stop simulation
     */
    public void performStopSimulation() {
        simulationPanel.setCenter(new Label("Not implemented yet"));
    }

    /**
     * Increase simulation speed
     */
    public void performBackwardSimulation() {
        simulationPanel.setCenter(new Label("Not implemented yet"));
    }

    /**
     * Decrease simulation speed
     */
    public void performForwardSimulation() {
        simulationPanel.setCenter(new Label("Not implemented yet"));
    }

    /**
     * Display car configuration panel
     */
    public void performDisplayCarConfigurationPanel() {
        configurationPanel.setCenter(new Label("Not implemented yet"));
    }

    /**
     * Display model configuration panel
     */
    public void performDisplayModelConfigurationPanel() {
        configurationPanel.setCenter(new Label("Not implemented yet"));
    }
}
