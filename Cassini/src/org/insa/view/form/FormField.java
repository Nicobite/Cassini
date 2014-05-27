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

import javafx.scene.Node;

/**
 *
 * @author Thomas Thiebaud
 */
public class FormField {
    protected String legend = "";
    protected Node control = null;
    
    /**
     * Constructor
     * @param legend Field text description
     * @param control Field (user input)
     */
    public FormField(String legend, Node control) {
        this(legend,control,300);
        this.legend = legend;
        this.control = control;
    }
    
    /**
     * Constructor
     * @param legend Field text description
     * @param control Field (user input)
     * @param controlWidth Control width
     */
    public FormField(String legend, Node control, int controlWidth) {
        this.legend = legend;
        this.control = control;
        //control.setMinWidth(controlWidth);
    }
    
    /**
     * Get legend
     * @return Legened text
     */
    public String getLegend() {
        return legend;
    }
    
    /**
     * Get control
     * @return Control (user input)
     */
    public Node getControl() {
        return control;
    }
}
