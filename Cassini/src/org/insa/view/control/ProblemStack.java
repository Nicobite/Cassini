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
package org.insa.view.control;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.insa.controller.MainController;
import org.insa.core.graphicmodel.AbstractGraphicProblem;
import org.insa.core.graphicmodel.GraphicCollision;
import org.insa.core.graphicmodel.GraphicCongestion;
import org.insa.core.graphicmodel.GraphicIncident;

/**
 * Create a stack with limited number of elements in order to stock last problem
 * @author Thiebaud Thomas
 */
public class ProblemStack extends BorderPane {
    
    private int size;
    private int currentSize = 0;
    private ArrayList<AbstractGraphicProblem> problems = new ArrayList<>();
    
    private VBox layout = new VBox();
    
    /**
     * Constructor
     * @param size Limit size
     */
    public ProblemStack(int size) {
        this.size = size;
        this.setCenter(layout);
        this.setMinWidth(50);
        this.setMaxWidth(50);
        this.setPrefWidth(50);
        this.getStyleClass().add("tool-bar");
        this.getStyleClass().add("right-tool-bar");
    }
    
    /**
     * Add a problem into the stack
     * @param problem Problem to add
     */
    public void add(final AbstractGraphicProblem problem) {        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(currentSize >= size) {
                    AbstractGraphicProblem pb = problems.get(problems.size() - 1);
                    if(pb instanceof GraphicIncident)
                        MainController.getInstance().performHideIncident((GraphicIncident)pb); 
                    else if(pb instanceof GraphicCollision)
                        MainController.getInstance().performHideCollision((GraphicCollision)pb);
                    else if(pb instanceof GraphicCongestion)
                        MainController.getInstance().performHideCongestion((GraphicCongestion)pb);
                }

                problems.add(0, problem);
                layout.getChildren().add(0,problem);
                currentSize ++ ;
            }
        });
    }
    
    /**
     * remove a problem from the stack
     * @param problem Problem to remove
     */
    public void remove(final AbstractGraphicProblem problem) {
        layout.getChildren().remove(problem);
        problems.remove(problem); 
        currentSize --;
    }
}
