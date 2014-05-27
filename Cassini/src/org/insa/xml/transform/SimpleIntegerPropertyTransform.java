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
package org.insa.xml.transform;

import javafx.beans.property.SimpleIntegerProperty;
import org.simpleframework.xml.transform.Transform;

/**
 * Enable Simple Framework to serialize SimpleIntegerProperty
 * @author Thomas Thiebaud
 */
public class SimpleIntegerPropertyTransform implements Transform<SimpleIntegerProperty> {

    @Override
    public SimpleIntegerProperty read(String string) throws Exception {
        return new SimpleIntegerProperty(Integer.valueOf(string));
    }

    @Override
    public String write(SimpleIntegerProperty t) throws Exception {
        return String.valueOf(t.get());
    }
    
}
