/*
* Copyright 2014 Abel Juste Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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

package org.insa.view;

import org.insa.view.panel.MainPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Thomas Thiebaud
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane layout = new BorderPane();
        layout.setCenter(new MainPanel());
        
        StackPane root = new StackPane();
        root.getChildren().add(layout);
        
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        
        Scene scene = new Scene(root, 1500, 900);
        scene.getStylesheets().add("/org/insa/view/css/cassini.css");
        
        
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
