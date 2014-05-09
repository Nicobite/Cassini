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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import org.insa.view.dock.DefaultDock;
import org.insa.view.dock.EditorToolsDock;
import org.insa.view.dock.ResizeMapDock;
import org.insa.view.toolbar.EditorToolBar;
import org.insa.view.utils.DrawingUtils;

/**
 *
 * @author Thiebaud Thomas
 */
public class EditorPanel extends BorderPane {   
    
    protected EditorArea editorArea;
    
    /**
     * Constructor
     */
    public EditorPanel() {   
        this.setTop(new EditorToolBar());
        this.setRight(new DefaultDock());
    }

    /**
     * Create editor area
     */
    public void createEditoArea() {
        editorArea = new EditorArea(new DrawingUtils(850, 1200));
        editorArea.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                editorArea.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
            }
        });
    }
    
    /**
     * Displayt the editor area into the editor panel
     */
    public void displayEditorArea() {
        if(editorArea == null) {
            this.createEditoArea();
        }
        this.setCenter(editorArea);
    }
    
    /**
     * Display resize dock into editor panel
     */
    public void displayResizeMapDock() {  
        this.setRight(new ResizeMapDock(editorArea,editorArea.getInitialBounds()));
    }
    
    /**
     * Display editor tools dock
     */
    public void displayEditorToolsDock() {
        this.setRight(new EditorToolsDock(editorArea));
    }
    
    /**
     * Get editor area
     * @return Editor area
     */
    public EditorArea getEditorArea() {
        return editorArea;
    }

    /**
     * Set editor area
     * @param editorArea New editor area 
     */
    public void setEditorArea(EditorArea editorArea) {
        this.editorArea = editorArea;
    }
}
