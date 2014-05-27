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

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.insa.controller.MainController;
import org.insa.core.roadnetwork.Node;

/**
 *
 * @author Thiebaud Thomas
 */
public class NodePicker extends HBox implements EventHandler<MouseEvent> {
    
    private Node node;
    private final TextField longField = new TextField();
    private final TextField latField = new TextField();
    private final Button getNodeButton = new Button();
    private boolean isSourceNodePicker;   
    
    
    /**
     * Constructor
     * @param isSourceNodePicker true if it is a source node picker, false otherwise
     */
    public NodePicker(boolean isSourceNodePicker) {
        this.isSourceNodePicker = isSourceNodePicker;
        longField.setPrefWidth(300);
        longField.setMaxWidth(Double.MAX_VALUE);
        longField.setEditable(false);
        
        latField.setPrefWidth(300);
        latField.setMaxWidth(Double.MAX_VALUE);
        latField.setEditable(false);
        
        getNodeButton.setMaxSize(26,26);
        getNodeButton.setMinSize(26,26);
        getNodeButton.setPrefSize(26,26);
        getNodeButton.setGraphic(new ImageView(new Image("/org/insa/view/image/get.png",26,26,true,true)));
        getNodeButton.getStyleClass().add("submit-button");
        getNodeButton.setOnMouseClicked(this);
        
        this.getChildren().add(longField);
        this.getChildren().add(latField);
        this.getChildren().add(getNodeButton);
        this.setFillHeight(true);
    }

    /**
     * Get node
     * @return Node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Get longitude text field
     * @return Longitude text field
     */
    public TextField getLongField() {
        return longField;
    }

    /**
     * Get latitude text field
     * @return Latitude text field
     */
    public TextField getLatField() {
        return latField;
    }

    /**
     * Get "get node button"
     * @return Node button
     */
    public Button getGetNodeButton() {
        return getNodeButton;
    }

    /**
     * Set node and update longitude and latitude fields
     * @param node New node 
     */
    public void setNode(Node node) {
        this.longField.setText(String.valueOf(node.getGraphicNode().getLongitude()));
        this.latField.setText(String.valueOf(node.getGraphicNode().getLatitude()));
        this.node = node;
    }
    
    @Override
    public void handle(MouseEvent event) {
        MainController.getInstance().performBeginGetNode(this,isSourceNodePicker);
    }
}
