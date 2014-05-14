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

import javafx.scene.input.MouseEvent;
import org.insa.controller.MainController;
import org.insa.core.trafficcontrol.Congestion;

/**
 *
 * @author thomasthiebaud
 */
public class GraphicCongestion extends AbstractGraphicProblem {

    private Congestion congestion;
    
    /**
     * Constructor
     * @param congestion Reference to congestion
     */
    public GraphicCongestion(Congestion congestion) {
        super("congestion");
    }
    
    @Override
    public void handle(MouseEvent event) {
        if(isFirstClick) {
            isFirstClick = false;
            MainController.getInstance().getSimulationController().pause();
            MainController.getInstance().performDisplayCongestion(congestion);
            this.getStyleClass().add("selected-problem");
        } else {
            MainController.getInstance().performHideCongestion(this);
            MainController.getInstance().getSimulationController().resume();
        }  
    }

    /**
     * Get congestion
     * @return Congestion
     */
    public Congestion getCongestion() {
        return congestion;
    }

    /**
     * Set congestion
     * @param congestion New congestion 
     */
    public void setCongestion(Congestion congestion) {
        this.congestion = congestion;
    } 
}
