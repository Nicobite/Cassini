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
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.insa.core.driving.Vehicle;
import org.insa.model.Model;
import org.insa.view.panel.DefaultPanel;
import org.insa.view.panel.DrawingPanel;
import org.insa.view.panel.Editor;
import org.insa.view.panel.MainPanel;
import org.insa.view.panel.MapPanel;
import org.insa.view.panel.ResultPanel;
import org.insa.view.panel.RoadDrawingPanel;
import org.insa.view.panel.SimulationPanel;
import org.insa.view.panel.VehicleDataPanel;
import org.insa.view.panel.VehiclesPanel;
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
    private MapPanel mapPanel = null;
    private ResultPanel resultPanel = null;
    private VehiclesPanel vehiclesPanel = null;
    private VehicleDataPanel vehicleDataPanel = null;
    private RoadDrawingPanel roadDrawingPanel = null;
    
    
    private Model model = new Model();
    
    private SimulationController simulationController = null;
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
        simulationController = new SimulationController(50);
        simulationPanel = new SimulationPanel();
        mainPanel.setCenter(simulationPanel);
        if(roadDrawingPanel != null)
            simulationPanel.setCenter(roadDrawingPanel);
        else
            this.performDisplayMessage(simulationPanel, "Aucune carte sélectionnée");
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
        this.performDisplayMessage(resultPanel, "Not implemented yet");
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
        
        final File file = fileChooser.showSaveDialog(primaryStage);
        
        ProgressIndicator pi = new ProgressIndicator();
        pi.setVisible(true);
        pi.setMaxWidth(50);
        pi.setMaxHeight(50);
        mapPanel.setCenter(pi);
        
        if(file != null) {
            new Thread() {
                public void run() {
                    if(file != null) {
                        File newFile = file;
                        
                        if(!file.getName().contains(".")) {
                            newFile = new File(file.getAbsolutePath() + ".map.xml");
                        }
                        
                        XmlParser p = new XmlParser();
                        try {
                            p.saveMapData(model.getRoadModel(), newFile);
                        } catch (Exception ex) {
                            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mapPanel.setCenter(roadDrawingPanel);
                            }
                        });
                    }
                }
            }.start();
        } else {
            this.performDisplayMessage(mapPanel, "Erreur lors de la sauvegarde de la carte");
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
        
        final File file = fileChooser.showOpenDialog(primaryStage);
        
        ProgressIndicator pi = new ProgressIndicator();
        pi.setVisible(true);
        pi.setMaxWidth(50);
        pi.setMaxHeight(50);
        mapPanel.setCenter(pi);
        
        if(file != null) {
            new Thread() {
                public void run() {
                    XmlParser p = new XmlParser();
                    try {
                        String extension = file.getAbsolutePath().split("\\.")[1];
                        if(extension.equals("osm"))
                            model.setRoadModel(p.readOsmData(file));
                        else
                            model.setRoadModel(p.readMapData(file));
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                roadDrawingPanel = new RoadDrawingPanel(1450,850);
                                mapPanel.setCenter(roadDrawingPanel);
                            }
                        });
                    } catch (Exception ex) {
                        Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }
        else {
            this.performDisplayMessage(mapPanel, "Erreur lors de l'ouverture de la carte");
        }
    }
    
    /**
     * Display map editor in order to create a map
     */
    public void performDisplayMapEditor() {
        //mapPanel.setCenter(new Text("Not implemented yet"));
        mapPanel.setCenter(new Editor(1450,850));
    }
    
    /**
     * Display a message in a pane
     * @param pane Pane containing the message
     * @param message Message to display
     */
    public void performDisplayMessage(BorderPane pane, String message) {
        Text text = new Text(message);
        text.setFont(Font.font("Arial", 15));
        pane.setCenter(text);
    }
    
    /**
     * Display vehicles panel
     */
    public void performDisplayVehiclesPanel() {
        vehicleDataPanel = new VehicleDataPanel();
        vehiclesPanel = new VehiclesPanel();
        vehiclesPanel.setCenter(vehicleDataPanel);
        mainPanel.setCenter(vehiclesPanel);
    }
    
    /**
     * Add a vehicle o the vehicle model
     * @param vehicle Vehicle to add
     * @param quantity Number of vehicles to add
     */
    public void performAddVehicle(Vehicle vehicle, int quantity) {
        for(int i=0; i<quantity; i++)
            model.getVehiclesModel().addVehicle(vehicle);
        MainController.getInstance().performUpdateVehiclesTable();
    }
    
    /**
     * Reset vehicles model by reseting the vehicles list
     */
    public void performResetVehiclesModel() {
        model.getVehiclesModel().getVehicles().clear();
        MainController.getInstance().performUpdateVehiclesTable();
    }
    
    public void performOpenVehicles() {
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Open vehicles model");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("vehicle", "*.vhc.xml"));
        
        final File file = fileChooser.showOpenDialog(primaryStage);
        
        ProgressIndicator pi = new ProgressIndicator();
        pi.setVisible(true);
        pi.setMaxWidth(50);
        pi.setMaxHeight(50);
        vehiclesPanel.setCenter(pi);
        
        if(file != null) {
            new Thread() {
                public void run() {
                    XmlParser p = new XmlParser();
                    try {
                        model.setVehiclesModel(p.readVehiclesData(file));
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MainController.getInstance().performUpdateVehiclesTable();
                                vehiclesPanel.setCenter(vehicleDataPanel);
                            }
                        });
                    } catch (Exception ex) {
                        Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }
        else {
            this.performDisplayMessage(vehiclesPanel, "Erreur lors de l'ouverture de la liste des véhiules");
        }
    }
    
    public void performSaveVehicles() {
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Choose place");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("vehicle", ".vhc.xml"));
        
        final File file = fileChooser.showSaveDialog(primaryStage);
        
        ProgressIndicator pi = new ProgressIndicator();
        pi.setVisible(true);
        pi.setMaxWidth(50);
        pi.setMaxHeight(50);
        vehiclesPanel.setCenter(pi);
        
        if(file != null) {
            new Thread() {
                public void run() {
                    if(file != null) {
                        File newFile = file;
                        
                        if(!file.getName().contains(".")) {
                            newFile = new File(file.getAbsolutePath() + ".vhc.xml");
                        }
                        
                        XmlParser p = new XmlParser();
                        try {
                            p.saveVehiclesData(model.getVehiclesModel(), newFile);
                        } catch (Exception ex) {
                            Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MainController.getInstance().performUpdateVehiclesTable();
                                vehiclesPanel.setCenter(vehicleDataPanel);
                            }
                        });
                    }
                }
            }.start();
        } else {
            this.performDisplayMessage(vehiclesPanel, "Erreur lors de la sauvegarde de la liste des véhicules");
        }
    }
    
    /**
     * Update vehicule table view
     */
    public void performUpdateVehiclesTable() {
        if(vehicleDataPanel != null) {
            vehicleDataPanel.performUpdateData(model.getVehiclesModel().getVehicles());
        }
    }
    
    /**
     * Start the simulation
     */
    public void performPlaySimulation() {
        simulationController.start();
    }
    
    /**
     * Perform a break during the simulation
     */
    public void performPauseSimulation() {
        simulationController.pause();
    }
    
    /**
     * Stop the simulation
     */
    public void performStopSimulation() {
        simulationController.stop();
    }
    
    /**
     * Decrease simulation speed
     */
    public void performBackwardSimulation() {
        int simulationStep = simulationController.getSimulationStep();
        simulationStep += 10;
        simulationController.setSimulationStep(simulationStep);
    }
    
    /**
     * Increase simulation speed
     */
    public void performForwardSimulation() {
        int simulationStep = simulationController.getSimulationStep();
        simulationStep -= 10;
        simulationController.setSimulationStep(simulationStep);
    }
    
    public SimulationController getSimulationController(){
        return this.simulationController ; 
    }
}
