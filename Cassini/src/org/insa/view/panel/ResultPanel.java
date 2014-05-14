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
package org.insa.view.panel;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.insa.controller.MainController;
import org.insa.view.control.NumberBox;

/**
 *
 * @author Thomas Thiebaud
 */
public class ResultPanel extends BorderPane {
    
    protected XYChart.Series incidents = new XYChart.Series();
    ArrayList<Integer> incidentsQueue = new ArrayList<>();
    
    protected XYChart.Series collisions = new XYChart.Series();
    ArrayList<Integer> collisionsQueue = new ArrayList<>();
    
    protected XYChart.Series congestions = new XYChart.Series();
    ArrayList<Integer> congestionsQueue = new ArrayList<>();
    
    ObservableList<PieChart.Data> pieChartData = null;
    
    protected final NumberAxis xAxis = new NumberAxis();
    protected final NumberAxis yAxis = new NumberAxis();
    
    protected int incidentNumber = 0;
    protected int collisionNumber = 0;
    protected int congestionNumber = 0;
    
    protected LineChart<Number,Number> resultChart = new LineChart<Number,Number>(xAxis,yAxis);
    protected PieChart incidentDistribution = new PieChart();
    
    protected BorderPane chartLayout = new BorderPane();
    protected HBox resumeLayout = new HBox();
    
    /**
     * Default constructor
     */
    public ResultPanel() {
        resultChart.setTitle("Résultats de la simulation");
        yAxis.setLabel("Nombre d'occurences");
        xAxis.setLabel("Temps (ms)");
        
        incidents.setName("Incidents");
        collisions.setName("Collisions");
        congestions.setName("Embouteillage");
        
        resultChart.getData().add(incidents);
        resultChart.getData().add(collisions);
        resultChart.getData().add(congestions);
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
        
        this.setCenter(resultChart);
        this.setBottom(chartLayout);
    }
    
    /**
     * Create chart by getting elements from queues 
     */
    public void voidQueue() {
        for(int i=0;i<incidentsQueue.size(); i++) {
            incidents.getData().add(new XYChart.Data(incidentsQueue.get(i),i));
        }
        for(int i=0;i<collisionsQueue.size(); i++) {
            collisions.getData().add(new XYChart.Data(collisionsQueue.get(i),i));
        }
        for(int i=0;i<congestionsQueue.size(); i++) {
            congestions.getData().add(new XYChart.Data(congestionsQueue.get(i),i));
        }
    }
    
    /**
     * Add incident into incidents serie
     * @param totalTime incident' time
     */
    public void addIncident(final int totalTime) {
        incidentsQueue.add(totalTime);
    }
    
    /**
     * Add collision into collision serie
     * @param totalTime Collision' time
     */
    public void addCollision(final int totalTime) {
        collisionsQueue.add(totalTime);
    }
    
    /**
     * Add congestion into congestion serie
     * @param totalTime Congestion' time
     */
    public void addCongestion(final int totalTime) {
        congestionsQueue.add(totalTime);
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
}
