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
package org.insa.view.graphicmodel;

import java.util.Objects;
import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.core.trafficcontrol.Collision;

/**
 *
 * @author Thiebaud Thomas
 */
public class GraphicCollision extends AbstractGraphicProblem {
    
    private Collision collision;
    private GraphicTarget graphicTarget;
    
    /**
     * Constructor
     * @param collision Reference to collision
     */
    public GraphicCollision(Collision collision) {
        super("collision");
        this.collision = collision;
    }
    
    @Override
    public void handle(MouseEvent event) {
        if(isFirstClick) {
            isFirstClick = false;
            MainController.getInstance().getSimulationController().pause();
            MainController.getInstance().performDisplayCollision(collision);
            this.getStyleClass().add("selected-problem");
        } else {
            MainController.getInstance().performHideCollision(this);
            MainController.getInstance().getSimulationController().resume();
        }   
    }

    /**
     * Get collision
     * @return Collision
     */
    public Collision getCollision() {
        return collision;
    }
    
    /**
     * Get graphic target
     * @return Graphic target
     */
    public GraphicTarget getGraphicTarget() {
        return graphicTarget;
    }

    /**
     * Set collision
     * @param collision New collision
     */
    public void setCollision(Collision collision) {
        this.collision = collision;
    }
    
    /**
     * Set graphic target
     * @param graphicTarget New Graphic target
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
        final GraphicCollision other = (GraphicCollision) obj;
        if (!Objects.equals(this.collision, other.collision)) {
            return false;
        }
        return true;
    }
    
    
}
