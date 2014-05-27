/*
* Copyright 2014 Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.insa.controller.MainController;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicBounds;
import org.insa.view.panel.EditorArea;
import org.insa.view.form.FloatSpinner;

/**
 *
 * @author Thiebaud Thomas
 */
public class ResizeMapDock extends AbstractDock {
    
    private RoadsModel model = MainController.getInstance().getModel().getRoadModel();
    
    private FloatSpinner minLong;
    private FloatSpinner maxLong;
    private FloatSpinner minLat;
    private FloatSpinner maxLat;
    
    private final Button submit = new Button("Redimensionner");
    
    private final VBox layout = new VBox();
    
    /**
     * Constructor
     * @param editorArea reference to editor area
     */
    public ResizeMapDock(EditorArea editorArea) {
        super(editorArea);
    }
    
    /**
     * Constructor
     * @param editorArea reference to editor area
     * @param initialBounds Bounds of the current map
     */
    public ResizeMapDock(EditorArea editorArea,GraphicBounds initialBounds) {
        super(editorArea);
        minLong = new FloatSpinner(initialBounds.getMinLong());
        maxLong = new FloatSpinner(initialBounds.getMaxLong());
        minLat = new FloatSpinner(initialBounds.getMinLat());
        maxLat = new FloatSpinner(initialBounds.getMaxLat());
        this.init();
    }
    
    /**
     * Initialize resize map dock' components
     */
    private void init() {        
        minLong.getField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                editorArea.drawVerticalBound(minLong.getValue(),true);
            }
        });
        
        maxLong.getField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                editorArea.drawVerticalBound(maxLong.getValue(),false);
            }
        });
        
        minLat.getField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                editorArea.drawHorizontalBound(minLat.getValue(),true);
            }
        });
        
        maxLat.getField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                editorArea.drawHorizontalBound(maxLat.getValue(),false);
            }
        });
        
        
        submit.setPrefWidth(WIDTH);
        submit.getStyleClass().add("submit-button");
        submit.getStyleClass().add("bottom-tool-bar");
        
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {       
                model.setMinLon(minLong.getValue());
                model.setMaxLon(maxLong.getValue());
                model.setMinLat(minLat.getValue());
                model.setMaxLat(maxLat.getValue());
                editorArea.reload();
                if(editorArea.isWaitingSize())
                    MainController.getInstance().performDisplayEditorArea();
            }
        });
        
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(3);
        layout.getChildren().add(new Text("Longitude minimale : "));
        layout.getChildren().add(minLong);
        layout.getChildren().add(new Text("Longitude maximale : "));
        layout.getChildren().add(maxLong);
        layout.getChildren().add(new Text("Latitude minimale : "));
        layout.getChildren().add(minLat);
        layout.getChildren().add(new Text("Latitude maximale : "));
        layout.getChildren().add(maxLat);
        
        this.setCenter(layout);
        this.setBottom(submit);
    }
}
