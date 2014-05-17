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

package org.insa.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hyperic.sigar.Sigar;
import org.insa.core.driving.Vehicle;
import org.insa.core.trafficcontrol.Collision;
import org.insa.core.trafficcontrol.Congestion;
import org.insa.core.trafficcontrol.Incident;
import org.insa.model.Model;
import org.insa.model.items.RoadsModel;
import org.insa.model.items.VehiclesModel;
import org.insa.view.form.NodePicker;
import org.insa.view.graphicmodel.GraphicCollision;
import org.insa.view.graphicmodel.GraphicCongestion;
import org.insa.view.graphicmodel.GraphicIncident;
import org.insa.view.graphicmodel.GraphicNode;
import org.insa.view.panel.DefaultPanel;
import org.insa.view.panel.DrawingPanel;
import org.insa.view.panel.EditorArea;
import org.insa.view.panel.EditorPanel;
import org.insa.view.panel.MainPanel;
import org.insa.view.panel.MapPanel;
import org.insa.view.panel.ResultPanel;
import org.insa.view.panel.SimulationPanel;
import org.insa.view.panel.VehicleDataPanel;
import org.insa.view.panel.VehiclesPanel;
import org.insa.view.utils.DrawingUtils;
import org.insa.xml.XmlParser;
import org.insa.xml.osm.OsmRoot;

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
    private DrawingPanel drawingPanel = null;
    private EditorPanel editorPanel = null;
    private NodePicker lastNodePicker = null;
      
    private Model model = new Model();
    
    private SimulationController simulationController = null;
    private boolean isPickingNode = false;
    
    private int height;
    private int width;
    
    /**
     * Default private constructor
     */
    private MainController(){
        //Empty for the moment
    }
    
   public void performDisplayJConsole(){
        try {
            Sigar sigar = new Sigar();
            long pid = sigar.getPid();
            Process proc = Runtime.getRuntime().exec("jconsole " + pid);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } 
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
        
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add("/org/insa/view/css/cassini.css");
        
        
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
        width = (int) primaryStage.getWidth();
        height = (int) primaryStage.getHeight();
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
        simulationController = new SimulationController(500, false);
        resultPanel = new ResultPanel();
        simulationPanel = new SimulationPanel(); 
        
        mainPanel.setCenter(simulationPanel);
        
        if(model.getRoadModel().getRoads().isEmpty())
            this.performDisplayMessage(simulationPanel, "Aucune carte sélectionnée");
        else if(model.getVehiclesModel().getVehicles().isEmpty())
            this.performDisplayMessage(simulationPanel, "Aucun véhicule sélectionné");   
        else
            simulationPanel.setCenter(drawingPanel);
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
        if(resultPanel == null)
            resultPanel = new ResultPanel();
        mainPanel.setCenter(resultPanel);
        
        
        resultPanel.voidQueue();
        int allIncidents = model.getControlUnitsModel().getAllIncidents().size();
        if (allIncidents != 0) {
            int directionsIncidents = model.getControlUnitsModel().getDirectionIncidents().size() * 100 / allIncidents;
            int priorityIncidents = model.getControlUnitsModel().getPriorityIncidents().size() * 100 / allIncidents;
            int speedIncidents = model.getControlUnitsModel().getSpeedLimitIncidents().size() * 100 / allIncidents;
            int stopIncidents = model.getControlUnitsModel().getStopIncidents().size() * 100 / allIncidents;

            ArrayList<PieChart.Data> data = new ArrayList<>();
            if(directionsIncidents != 0)
                data.add(new PieChart.Data("Sens", directionsIncidents));
            if(priorityIncidents != 0)
                data.add(new PieChart.Data("Priorité", priorityIncidents));
            if(speedIncidents != 0)
                data.add(new PieChart.Data("Vitesse", speedIncidents));
            if(stopIncidents != 0)
                data.add(new PieChart.Data("Stop", stopIncidents));

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(data);
            resultPanel.updateIncidentDistribution(pieChartData);
        }
        model.clear();
        drawingPanel = null;
        
        if(simulationController != null)
            simulationController.stop();
        else
            this.performDisplayMessage(mainPanel, "Aucune simulation en cours. Pas de résultats à afficher");
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
        editorPanel.setCenter(pi);
        
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
                                editorPanel.setCenter(editorPanel.getEditorArea());
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
     * Open a map
     */
    public void performOpenMap(final boolean isMapEditor) {
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Open map");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map", "*.map.xml"));
        
        if(isMapEditor)
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("osm", "*.osm"));
        
        final File file = fileChooser.showOpenDialog(primaryStage);
        
        ProgressIndicator pi = new ProgressIndicator();
        pi.setVisible(true);
        pi.setMaxWidth(50);
        pi.setMaxHeight(50);
        
        if(isMapEditor)
            editorPanel.setCenter(pi);
        else
            mapPanel.setCenter(pi);
        
        if(file != null) {
            new Thread() {
                public void run() {
                    XmlParser p = new XmlParser();
                    try {
                        String extension = file.getAbsolutePath().split("\\.")[1];
                        if(extension.equals("osm")){
                            OsmRoot r = p.readOsmData(file);
                            model.setRoadModel(r.buildRoadModel());
                            model.getControlUnitsModel().setTrafficLights(r.getTrafficLightFromRoads());
                        }
                        else{
                            RoadsModel r = p.readMapData(file);
                            model.setRoadModel(r);
                            model.getControlUnitsModel().setTrafficLights(r.getTrafficLightFromRoads());
                        }
                        
                        
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(isMapEditor) {
                                    final EditorArea editorArea = new EditorArea(new DrawingUtils(height - 50, width - 300));
                                    editorArea.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
                                        @Override
                                        public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                                            editorArea.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
                                        }
                                    });
                                    editorPanel.setEditorArea(editorArea);
                                    editorPanel.setCenter(editorArea);
                                    editorPanel.displayEditorToolsDock();
                                } else {
                                    drawingPanel = new DrawingPanel(width - 50,height - 50);
                                    mapPanel.setCenter(drawingPanel);
                                }
                                
                            }
                        });
                    } catch (Exception ex) {
                        Logger.getLogger(DrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }
        else {
            if(isMapEditor)
               this.performDisplayMessage(editorPanel, "Erreur lors de l'ouverture de la carte"); 
            else
                this.performDisplayMessage(mapPanel, "Erreur lors de l'ouverture de la carte");
        }
    }
    
    /**
     * Display map editor in order to create a map
     */
    public void performDisplayMapEditor() {
        editorPanel = new EditorPanel();
        mainPanel.setCenter(editorPanel);
    }
    
    public void performOpenMapIntoEditor() {
        final EditorArea editorArea = new EditorArea(new DrawingUtils(height - 50, width - 300));
        
        editorArea.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                editorArea.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
            }
        });
        
        editorPanel.setCenter(editorArea);
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
                            VehiclesModel toSave = new VehiclesModel();
                            toSave.setVehicles(model.getVehiclesModel().getVehicles());
                            toSave.getVehicles().addAll(model.getDrivingVehiclesModel().getVehicles());
                            p.saveVehiclesData(model.getVehiclesModel(), newFile);
                            toSave = null;
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
    
    /**
     * Get simulation controller
     * @return Simulation controller
     */
    public SimulationController getSimulationController(){
        return this.simulationController ;
    }
    
    /**
     * Repaint all vehicles
     */
    public void performRepaintVehicles() {
        if(drawingPanel != null) {
            drawingPanel.repaintVehicles();
        }
    }
    
    /**
     * Repaint all roads
     */
    public void performRepaintRoads() {
        if(drawingPanel != null) {
            drawingPanel.repaintRoads();
        }
    }
    
    /**
     * Display resize map dock
     */
    public void performDisplayResizeMapDock() {
        if(editorPanel != null)
            editorPanel.displayResizeMapDock();
    }

    /**
     * Begin to get a node from the map
     * @param nodePicker Reference to the node picker control
     */
    public void performBeginGetNode(NodePicker nodePicker,boolean isSourceNode) {
        if(mapPanel != null) {     
            lastNodePicker = nodePicker;
            drawingPanel.performDisplayNode(isSourceNode);
            mapPanel.setCenter(drawingPanel);
            mainPanel.setCenter(mapPanel);
            isPickingNode = true;
        } else {
            this.performDisplayMessage(mainPanel, "Aucune carte sélectionnée");
        }
    }

    /**
     * Get a node from the map
     * @param graphicNode Graphic node got from the map
     */
    public void performGetNode(GraphicNode graphicNode) {
        if(isPickingNode) {
            lastNodePicker.setNode(graphicNode.getNode());
            drawingPanel.performHideNode();
            mainPanel.setCenter(vehiclesPanel);
            isPickingNode = false;
        } else {
            editorPanel.getEditorArea().addConnectionBetweenRoads(graphicNode);
        }
    }

    /**
     * Ask for map size in order to create a new map
     */
    public void performCreateNewMap() {
        model.clear();
        editorPanel.createEditoArea();
        editorPanel.getEditorArea().setIsWaitingSize(true);
        this.performDisplayMessage(editorPanel, "Veuillez entrer les dimensions de la carte");
        editorPanel.displayResizeMapDock();
    }

    /**
     * Display editor tools and drawing area in order to draw a new map
     */
    public void performDisplayEditorArea() {
        editorPanel.displayEditorArea();
        editorPanel.displayEditorToolsDock();
        editorPanel.getEditorArea().setIsWaitingSize(false);
    }

    /**
     * Add incident into result panel
     */
    public void performAddIncident(Incident i) {
        if(resultPanel == null)
            resultPanel = new ResultPanel();
        resultPanel.addIncident(simulationController.getTotalTime());
        
        simulationPanel.add(i.getGraphicIncident());
    }
    
    /**
     * Add collision into result panel
     * @param c Collision to add
     */
    public void performAddCollision(Collision c) {
        if(resultPanel == null)
            resultPanel = new ResultPanel();
        resultPanel.addCollision(simulationController.getTotalTime());
        
        simulationPanel.add(c.getGraphicCollision());
    }

    /**
     * Add congestion into result panel
     * @param c Congestion to add
     */
    public void performAddCongestion(Congestion c) {
        if(resultPanel == null)
            resultPanel = new ResultPanel();
        resultPanel.addCongestion(simulationController.getTotalTime());
        
        simulationPanel.add(c.getGraphicCongestion());
    }

    /**
     * Display an incident
     * @param incident Incident to display
     */
    public void performDisplayIncident(Incident incident) {
        drawingPanel.getVehicleDrawingPanel().displayIncident(incident);
    }
    
    /**
     * Display a congestion
     * @param congestion Congestion to display
     */
    public void performDisplayCongestion(Congestion congestion) {
        drawingPanel.getRoadDrawingPanel().displayCongestion(congestion);
    }
    
    /**
     * Display a collision
     * @param collision Collision to display
     */
    public void performDisplayCollision(Collision collision) {
        drawingPanel.getVehicleDrawingPanel().displayCollision(collision);
    }
    
    /**
     * Hide an incident
     * @param gIncident Incident to hide
     */
    public void performHideIncident(GraphicIncident gIncident) {
        simulationPanel.remove(gIncident);
        drawingPanel.getVehicleDrawingPanel().hideIncident(gIncident);
    }    

    /**
     * Hide a congestion
     * @param gCongestion Congestion to hide 
     */
    public void performHideCongestion(GraphicCongestion gCongestion) {
        simulationPanel.remove(gCongestion);
        drawingPanel.getRoadDrawingPanel().hideCongestion(gCongestion);
    }

    /**
     * Hide a collision
     * @param gCollision Collision to hide
     */
    public void performHideCollision(GraphicCollision gCollision) {
        simulationPanel.remove(gCollision);
        drawingPanel.getVehicleDrawingPanel().hideCollision(gCollision);
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
}
