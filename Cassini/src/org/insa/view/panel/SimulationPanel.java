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

import javafx.scene.layout.BorderPane;
import org.insa.controller.MainController;
import org.insa.view.form.ProblemStack;
import org.insa.view.graphicmodel.AbstractGraphicProblem;
import org.insa.view.toolbar.SimulationToolBar;

/**
 *
 * @author Thomas Thiebaud
 */
public class SimulationPanel extends BorderPane{
    
    private ProblemStack problemStack = null;

    private SimulationToolBar simulationToolBar = new SimulationToolBar();
    
    /**
     * Default constructor
     */
    public SimulationPanel() {
        problemStack = new ProblemStack((MainController.getInstance().getHeight() - 50) / 50);

        this.setBottom(simulationToolBar);
        this.setRight(problemStack);
    }

    /**
     * Add a problem into the problem stack
     * @param problem Problem to add
     */
    public void add(AbstractGraphicProblem problem) {
        problemStack.add(problem);
    }
    
    /**
     * Remove a problem from the problem stack
     * @param problem Problem to remove
     */
    public void remove(AbstractGraphicProblem problem) {
        problemStack.remove(problem);
    }
}
