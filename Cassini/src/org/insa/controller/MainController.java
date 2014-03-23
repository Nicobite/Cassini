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
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    
    private Stage primaryStage = null;
    
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
     * Add a reference to primary stage
     * @param primaryStage Primary stage
     */
    public void performDisplayMainPanel(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainPanel = new MainPanel();
        BorderPane layout = new BorderPane();
        layout.setCenter(mainPanel);
        
        StackPane root = new StackPane();
        root.getChildren().add(layout);
        
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        
        Scene scene = new Scene(root, 1500, 900);
        scene.getStylesheets().add("/org/insa/view/css/cassini.css");
        
        
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
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
     * Save the current map to .map.xml format
     */
    public void performSaveMap() {
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Choose place");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map", ".map.xml"));
        
        File file = fileChooser.showSaveDialog(primaryStage);
        
        if(file != null) {
            
            if(!file.getName().contains(".")) {
                file = new File(file.getAbsolutePath() + ".map.xml");
            }
            
            XmlParser p = new XmlParser();
            try {
                p.saveMapData(model.getRoadModel(), file);
            } catch (Exception ex) {
                Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Open a map (not an OSM map)
     */
    public void performOpenMap() {
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Open map");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map", "*.map.xml"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("osm", "*.osm"));
        
        File file = fileChooser.showOpenDialog(primaryStage);
        
        if(file != null) {
            XmlParser p = new XmlParser();
            try {
                String extension = file.getAbsolutePath().split("\\.")[1];
                if(extension.equals("osm"))
                    model.setRoadModel(p.readOsmData(file));
                else
                    model.setRoadModel(p.readMapData(file));
                mapPanel.setCenter(new RoadDrawingPanel(1450,850));
            } catch (Exception ex) {
                Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Display map editor in order to create a map
     */
    public void performDisplayMapEditor() {
        mapPanel.setCenter(new Text("Not implemented yet"));
    }
}
