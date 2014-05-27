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
package org.insa.view.form;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Thiebaud Thomas
 */
public class MissionPicker extends GridPane {
    private NodePicker sourceNodePicker = new NodePicker(true);
    private NodePicker targetNodePicker = new NodePicker(false);
    
    /**
     * Default constructor
     */
    public MissionPicker() {
        this.setVgap(10);
        this.setHgap(10);
        
        this.add(new Label("Origine"), 0, 0);
        this.add(sourceNodePicker, 1, 0);
        
        this.add(new Label("Destination"), 0, 1);
        this.add(targetNodePicker, 1, 1);
    }

    /**
     * Get node picker control associated to the source node
     * @return Source node picker
     */
    public NodePicker getSourceNodePicker() {
        return sourceNodePicker;
    }

    /**
     * Get node picker control associated to the target node
     * @return Source node picker
     */
    public NodePicker getTargetNodePicker() {
        return targetNodePicker;
    }
}
