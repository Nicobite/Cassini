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
package org.insa.view.graphicmodel;

import java.util.Objects;
import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.core.trafficcontrol.Incident;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicIncident extends AbstractGraphicProblem {
    
    private Incident incident;
    private GraphicTarget graphicTarget;
    
    
    /**
     * Constructor
     * @param incident Reference to incident 
     */
    public GraphicIncident(Incident incident) {
        super("incident");
        this.incident = incident;
        
        switch(incident.getIncidentType()) {
            case WRONG_SPEED_LIMIT :
                this.setImage("speed");
                break;
            case WRONG_DIRECTION :
                this.setImage("direction");
                break;
            case WRONG_PRIORITY :
                this.setImage("priority");
                break;
            case WRONG_STOP :
                this.setImage("no_entry");
                break;
        }
    }
    
    @Override
    public void handle(MouseEvent event) {
        if(isFirstClick) {
            isFirstClick = false;
            MainController.getInstance().getSimulationController().pause();
            MainController.getInstance().performDisplayIncident(incident);
            this.getStyleClass().add("selected-problem");
        } else {
            MainController.getInstance().performHideIncident(this);
            MainController.getInstance().getSimulationController().resume();
        }  
    }

    /**
     * Get incident
     * @return Incident
     */
    public Incident getIncident() {
        return incident;
    }

    /**
     * Get graphic target
     * @return Graphic target
     */
    public GraphicTarget getGraphicTarget() {
        return graphicTarget;
    }

    /**
     * Set incident
     * @param incident New incident 
     */
    public void setIncident(Incident incident) {
        this.incident = incident;
    } 
    
    /**
     * Set graphic target
     * @param graphicTarget New graphicTarget
     */
    public void setGraphicTarget(GraphicTarget graphicTarget) {
        this.graphicTarget = graphicTarget;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphicIncident other = (GraphicIncident) obj;
        if (!Objects.equals(this.incident, other.incident)) {
            return false;
        }
        return true;
    }
}
