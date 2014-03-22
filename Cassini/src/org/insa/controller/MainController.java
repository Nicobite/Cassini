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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.Text;
import org.insa.model.Model;
import org.insa.view.panel.ConfigurationPanel;
import org.insa.view.panel.DefaultPanel;
import org.insa.view.panel.DrawingPanel;
import org.insa.view.panel.MainPanel;
import org.insa.view.panel.MapPanel;
import org.insa.view.panel.ResultPanel;
import org.insa.view.panel.RoadDrawingPanel;
import org.insa.view.panel.SimulationPanel;
import org.insa.xml.XmlParser;

/**
 *
 * @author Thomas Thiebaud
 */
public class MainController {
    
    private static volatile MainController instance = null;
    
    private MainPanel mainPanel = null;
    private SimulationPanel simulationPanel = null;
    private ConfigurationPanel configurationPanel = null;
    private MapPanel mapPanel = null;
    private ResultPanel resultPanel = null;
    
    private Model model = new Model();
    
    private SimulationPanel simulationController;
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
        mapPanel = new MapPanel();
        mainPanel.setCenter(mapPanel);
    }
    
    /**
     * Display map panel
     */
    public void performDisplayResultPanel() {
        resultPanel = new ResultPanel();
        mainPanel.setCenter(resultPanel);
    }
    
    /**
     * Get model
     * @return Model
     */
    public Model getModel() {
        return model;
    }
    
    /**
     * Set model
     * @param model New model
     */
    public void setModel(Model model) {
        this.model = model;
    }
    
    /**
     * Perform OSM map. Get model from OSM file (.osm)
     */
    public void performDisplayOSMMap() {
        XmlParser p = new XmlParser();
        try {
            model.setRoadModel(p.readOsmData(new File("data/osm/insa.osm")));
            mapPanel.setCenter(new RoadDrawingPanel(1450,850));
        } catch (Exception ex) {
            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Save the current map to .map.xml format
     */
    public void performSaveMap() {
        mapPanel.setCenter(new Text("Not implemented yet"));
    }
    
    /**
     * Open a map (not an OSM map)
     */
    public void performOpenMap() {
        mapPanel.setCenter(new Text("Not implemented yet"));
    }
    
    /**
     * Display map editor in order to create a map
     */
    public void performDisplayMapEditor() {
        mapPanel.setCenter(new Text("Not implemented yet"));
    }
}
