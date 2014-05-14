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
package org.insa.view.dock;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.insa.view.form.IntegerSpinner;
import org.insa.view.panel.EditorArea;

/**
 *
 * @author Thiebaud Thomas
 */
public class EditorToolsDock extends AbstractDock {
    
    private final VBox layout = new VBox();
    private final IntegerSpinner forwardLaneSize = new IntegerSpinner(1);
    private final IntegerSpinner backwardLaneSize = new IntegerSpinner(1);
    private final IntegerSpinner maxSpeed = new IntegerSpinner(50);
    
    /**
     * Constructor
     * @param editorArea reference to editor area
     */
    public EditorToolsDock(EditorArea editorArea) {
        super(editorArea);
        editorArea.setEditorToolsDock(this);
        this.init();
    }
    
    /**
     * Initialize edito tools dock
     */
    private void init() {
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(3);
        layout.getChildren().add(new Text("Nombre de voies (endroit) : "));
        layout.getChildren().add(forwardLaneSize);
        layout.getChildren().add(new Text("Nombre de voies (envers) : "));
        layout.getChildren().add(backwardLaneSize);
        layout.getChildren().add(new Text("Nvitesse maximale : "));
        layout.getChildren().add(maxSpeed);
      
        this.setCenter(layout);
    }
    
    /**
     * Get forward lane size value
     * @return Number of forward lanes
     */
    public int getForwardLaneSizeValue() {
        return forwardLaneSize.getValue();
    }
    
    /**
     * Get backward lane size value
     * @return Number of backward lanes
     */
    public int getBackwardLaneSizeValue() {
        return backwardLaneSize.getValue();
    }
    
    /**
     * Get max speed value
     * @return Max speed
     */
    public int getMaxSpeedValue() {
        return maxSpeed.getValue();
    }
}
