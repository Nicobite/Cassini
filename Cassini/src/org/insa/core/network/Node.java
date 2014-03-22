/*
* Copyright 2014 Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
package org.insa.core.network;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
 * Class Node
 * Represent a graph node
 * The simulation model is based on graph
 */
public class Node {
    /**
     * node id (node number)
     */
    private int id;
    /**
     * longitude
     */
    private float longitude;
    /**
     * latitude
     */
    private float latitude;
    
    /**
     * whether it is a crossing point
     * We can put traffic light in those points
     */
    private boolean crossingPoint;
    
    /**
     * whether it contains a traffic light
     */
    private boolean trafficLight;
    
    /**
     * Constructor
     * @param longitude Longitude
     * @param latitude Latitude
     */
    public Node(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    /**
     *  Default constructor
     */
    public Node() {
    }

    /**
     * Check if the node is a crossing point
     * @return true if the node is a crossing point, false otherwise
     */
    public boolean isCrossingPoint() {
        return crossingPoint;
    }

    /**
     * Check if the node is a traffic light
     * @return true if the node is a traffic light, false otherwise
     */
    public boolean isTrafficLight() {
        return trafficLight;
    }

    /**
     * Set traffic light tag
     * @param trafficLight New traffic light tag
     * @throws Exception 
     */
    public void setTrafficLight(boolean trafficLight) throws Exception{
        this.trafficLight = trafficLight;
    }

    /**
     * Set crossing point tag
     * @param crossingPoint New crossing point tag
     */
    public void setCrossingPoint(boolean crossingPoint) {
        this.crossingPoint = crossingPoint;
    }
    
    /**
     * Get id
     * @return Id 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set id
     * @param id New id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Get longitude
     * @return Longitude
     */
    public float getLongitude() {
        return longitude;
    }
    
    /**
     * Set longitude
     * @param longitude New longitude 
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    
    /**
     * Get latitude
     * @return Latitude
     */
    public float getLatitude() {
        return latitude;
    }
    
    /**
     * Set latitude
     * @param latitude New latitude 
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }  
    
}
