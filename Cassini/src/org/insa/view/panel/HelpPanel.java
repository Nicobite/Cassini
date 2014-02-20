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
package org.insa.view.panel;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Thomas Thiebaud
 */
public class HelpPanel extends BorderPane{
    private VBox layout = new VBox();
    
    private String style = "";
    private String htmlPage = "";
    
    /**
     * Default Contructor
     */
    public HelpPanel() {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        
        
        style       += "<style> "
                + "h1 {text-align:center;background-color:#EFEFEF;margin:0px;}"
                + "</style>";
        
        htmlPage    += "<h1>Aide</h1>"
                + "<p>L'aide peut être écrite directement en HTML et affichée à cet endroit.</p>"
                + "<p>Comme toute page html, elle peut être mise en page grâce au CSS.</p>"
                + "<p>L'idéal serait d'écrire le code dans un fichier a part et inclure le contenu dans l'application java.</p></br></br>"
                + "<p>Aide a rédiger une fois l'application terminée.</p>";
        
        webEngine.loadContent(style+"\n"+htmlPage);
        this.setCenter(browser);
    }
}
