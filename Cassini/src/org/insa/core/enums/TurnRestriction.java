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
 * see http://wiki.openstreetmap.org/wiki/Restriction for further details
 */
public enum TurnRestriction {
    NO_LEFT_TURN,
    NO_RIGHT_TURN,
    NO_U_TURN,
    NO_ENTRY, //Used for not one-way roads, where entering across some point is prohibited
    NO_EXIT, // Used for not one-way roads, where exiting across some point is prohibited
    NO_PRIORITY //lower priority (must wait before getting to the next lane)
}
