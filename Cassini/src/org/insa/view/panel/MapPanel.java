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

import java.io.File;
import java.io.FileFilter;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.insa.controller.MainController;

/**
 *
 * @author Thomas Thiebaud
 */
public class MapPanel extends BorderPane implements EventHandler<MouseEvent> {
    
    private final ListView<String> mapList = new ListView<>();
    private final Button otherMapButton = new Button("Autres");
    
    private final BorderPane layout = new BorderPane();
    private final File file = new File("data/maps");
    /**
     * Default constructor
     */
    public MapPanel() {
        otherMapButton.setMaxWidth(Double.MAX_VALUE);
        otherMapButton.getStyleClass().add("submit-button");
        otherMapButton.setOnMouseClicked(this);
        
        File files[] = file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if(pathname.getAbsolutePath().split("\\.")[1].equals("map"))
                    return true;
                return false;    
            }
        });
        
        for (File f : files) {
            mapList.getItems().add(f.getName().split("\\.")[0]);
        }
        
        mapList.setOnMouseClicked(this);
        
        layout.setCenter(mapList);
        layout.setBottom(otherMapButton);
        this.setCenter(layout);
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getSource() instanceof Button) {
            MainController.getInstance().performOpenMap(false);
        }
        else {
            String name = file.getAbsolutePath() + "/" + mapList.getItems().get(mapList.getSelectionModel().getSelectedIndex()) + ".map.xml";
            MainController.getInstance().performOpenMap(false,new File(name));
        }
    }
}
