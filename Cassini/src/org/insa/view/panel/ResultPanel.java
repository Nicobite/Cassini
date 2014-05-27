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
package org.insa.view.panel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.insa.controller.MainController;
import org.insa.view.form.NumberBox;

/**
 *
 * @author Thomas Thiebaud
 */
public class ResultPanel extends Accordion {
    /**
     * Data
     */
    private final XYChart.Series incidentsData = new XYChart.Series();
    private final XYChart.Series collisionsData = new XYChart.Series();
    private final XYChart.Series congestionsData = new XYChart.Series();
    private final ObservableList<PieChart.Data> pieChartData = null;
    private final XYChart.Series memoryData = new XYChart.Series();
    private final XYChart.Series cpuData = new XYChart.Series();
    
    /**
     * Axis
     */
    private final NumberAxis simulationXAxis = new NumberAxis();
    private final NumberAxis simulationYAxis = new NumberAxis();
    private final NumberAxis memoryXAxis = new NumberAxis();
    private final NumberAxis memoryYAxis = new NumberAxis();
    private final NumberAxis cpuXAxis = new NumberAxis();
    private final NumberAxis cpuYAxis = new NumberAxis();
    
    /**
     * Charts
     */
    private final LineChart<Number,Number> resultChart = new LineChart<>(simulationXAxis,simulationYAxis);
    private final PieChart incidentDistribution = new PieChart();
    private final LineChart<Number,Number> memoryChart = new LineChart<>(memoryXAxis,memoryYAxis);
    private final LineChart<Number,Number> cpuChart = new LineChart<>(cpuXAxis,cpuYAxis);
    
    /**
     * Layout & pane
     */
    private final BorderPane chartLayout = new BorderPane();
    private final HBox resumeLayout = new HBox();
    private final BorderPane mapLayout = new BorderPane();
    private final BorderPane performanceLayout = new BorderPane();
    private final TitledPane mapResult = new TitledPane();
    private final TitledPane performanceResult = new TitledPane();
    
    /**
     * Others
     */
    private int incidentNumber = 0;
    private int collisionNumber = 0;
    private int congestionNumber = 0;
    private int consoleTime = 0;
    private final int consolePeriod = MainController.getInstance().performGetConsolePeriod();
    private final int width = MainController.getInstance().getWidth() - 50 ;

    /**
     * Default constructor
     */
    public ResultPanel() {
        resultChart.setTitle("Résultats de la simulation");
        simulationYAxis.setLabel("Nombre d'occurences");
        simulationXAxis.setLabel("Temps (ms)");
        
        incidentsData.setName("Incidents");
        collisionsData.setName("Collisions");
        congestionsData.setName("Embouteillage");
        
        memoryChart.setTitle("Occupation mémoire");
        memoryXAxis.setLabel("Temps (ms)");
        memoryYAxis.setLabel("Occupation mémoire en Mo");
        memoryData.setName("Mémoire");
        
        cpuChart.setTitle("Activité du CPU");
        cpuXAxis.setLabel("Temps (ms)");
        cpuYAxis.setLabel("Activité (en %)");
        cpuData.setName("Activité");
        
        resultChart.getData().add(incidentsData);
        resultChart.getData().add(collisionsData);
        resultChart.getData().add(congestionsData);
        resultChart.getStyleClass().add("tool-bar");
        resultChart.getStyleClass().add("top-tool-bar");
        resultChart.getStyleClass().add("default-padding");
        
        incidentDistribution.setTitle("Répartitions des incidents");
        incidentDistribution.getStyleClass().add("tool-bar");
        incidentDistribution.getStyleClass().add("left-tool-bar");
        incidentDistribution.getStyleClass().add("default-padding");
        
        resumeLayout.getChildren().add(new NumberBox("Routes", MainController.getInstance().getModel().getRoadModel().getRoadNumber()));
        resumeLayout.getChildren().add(new NumberBox("Noeuds", MainController.getInstance().getModel().getRoadModel().getSectionNumber()));
        resumeLayout.getChildren().add(new NumberBox("Véhicules", MainController.getInstance().getModel().getVehiclesModel().getVehicleNumber()));
        resumeLayout.getChildren().add(new NumberBox("Feux", MainController.getInstance().getModel().getControlUnitsModel().getTrafficLightNumber()));
        
        chartLayout.setLeft(incidentDistribution);
        chartLayout.setCenter(resumeLayout);
        
        mapLayout.setCenter(resultChart);
        mapLayout.setBottom(chartLayout);
        mapLayout.setPadding(Insets.EMPTY);
        
        mapResult.setText("Carte");
        mapResult.setContent(mapLayout);
        mapResult.setMinWidth(width);
        mapResult.setPrefWidth(width);
        mapResult.setMaxWidth(width);
        mapResult.setPadding(Insets.EMPTY);
        
        memoryChart.getData().add(memoryData);
        memoryChart.getStyleClass().add("tool-bar");
        memoryChart.getStyleClass().add("top-tool-bar");
        memoryChart.getStyleClass().add("default-padding");
        cpuChart.getData().add(cpuData);
        cpuChart.getStyleClass().add("tool-bar");
        cpuChart.getStyleClass().add("default-padding");
        
        performanceLayout.setTop(memoryChart);
        performanceLayout.setBottom(cpuChart);
        performanceLayout.setPadding(Insets.EMPTY);
        
        performanceResult.setText("Performances");
        performanceResult.setContent(performanceLayout);
        performanceResult.setMinWidth(width);
        performanceResult.setPrefWidth(width);
        performanceResult.setMaxWidth(width);
        performanceResult.setPadding(Insets.EMPTY);
        
        this.getPanes().add(mapResult);
        this.getPanes().add(performanceResult);
        this.setMinWidth(width);
        this.setPrefWidth(width);
        this.setMaxWidth(width);
        this.setExpandedPane(mapResult);
        this.setPadding(Insets.EMPTY);
    }
    
    /**
     * Add incident into incidents serie
     * @param totalTime incident' time
     */
    public void addIncident(final int totalTime) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                incidentsData.getData().add(new XYChart.Data(totalTime,incidentNumber));
            }
        });
        incidentNumber++;
    }
    
    /**
     * Add collision into collision serie
     * @param totalTime Collision' time
     */
    public void addCollision(final int totalTime) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                collisionsData.getData().add(new XYChart.Data(totalTime,collisionNumber));
            }
        });
        collisionNumber++;
    }
    
    /**
     * Add congestion into congestion serie
     * @param totalTime Congestion' time
     */
    public void addCongestion(final int totalTime) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                congestionsData.getData().add(new XYChart.Data(totalTime,congestionNumber));
            }
        });
        congestionNumber++;
    }
    
    /**
     * Update incident distribution pie chart
     * @param pieChartData New datas
     */
    public void updateIncidentDistribution(final ObservableList<PieChart.Data> pieChartData) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                incidentDistribution.setData(pieChartData);
            }
        });
    }

    /**
     * Add some information about memory
     * @param residentMemory Information to add
     */
    public void addMemoryInfo(final int residentMemory) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                memoryData.getData().add(new XYChart.Data(consoleTime,residentMemory / 1000000));
            }
        });
    }

    /**
     * Add some information about cpu
     * @param cpuPercent Information to add
     */
    public void addCpuInfo(final int cpuPercent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cpuData.getData().add(new XYChart.Data(consoleTime,cpuPercent));
            }
        });
        consoleTime += consolePeriod;
    }
}
