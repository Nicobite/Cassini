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
package org.insa.core.enums;

/**
 *
 * @author ouedraog
 * turning instructions
 * see http://wiki.openstreetmap.org/wiki/Key:turn for further details
 */
public enum TurningIndication {
    NONE, //there are no turn indications on this lane
    LEFT, // left turn (only) 
    RIGHT,
    THROUGH, // going straight through (only) 
    MERGE_TO_LEFT,//this lane merges with the lane to the left of it (only) 
    MERGE_TO_RIGHT,
    SLIGHT_RIGHT,
    SLIGHT_LEFT // slight left turn
}
