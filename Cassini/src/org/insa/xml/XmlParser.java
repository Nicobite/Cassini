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
package org.insa.xml;

import java.io.File;
import org.insa.model.items.RoadsModel;
import org.insa.model.Model;
import org.insa.model.items.VehiclesModel;
import org.insa.xml.matcher.CustomMatcher;
import org.insa.xml.osm.OsmRoot;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class XmlParser
 */
public class XmlParser {
    
    /**
     * Default constructor
     */
    public XmlParser(){
        //Empty for the moment
    }
    
    /**
     * load the model data from xml
     * @param source Source file
     * @return Model
     * @throws java.lang.Exception General exception
     */
    public Model readData(File source) throws Exception{
        Serializer serializer = new Persister(new CustomMatcher());
        return serializer.read(Model.class, source);
    }
    
    /**
     * saves the data in xml file
     * @param model the data to save
     * @param output the output file
     * @throws Exception General exception
     */
    public void saveData(Model model, File output) throws Exception{
        Serializer serializer = new Persister(new CustomMatcher());
        serializer.write(model, output);
    }
    
    /**
     * load the vehicles data from xml
     * @param source Source file
     * @return Vehicle model
     * @throws java.lang.Exception General exception
     */
    public VehiclesModel readVehiclesData(File source) throws Exception{
        Serializer serializer = new Persister(new CustomMatcher());
        return serializer.read(VehiclesModel.class, source);
    }
    
    /**
     * saves the vehicles data in xml file
     * @param model Vehicle model to save
     * @param output Output file
     * @throws Exception General exception
     */
    public void saveVehiclesData(VehiclesModel model, File output) throws Exception{
        Serializer serializer = new Persister(new CustomMatcher());
        serializer.write(model, output);
    }
    
    /**
     * load the map data from xml
     * @param source Source file
     * @return Road model
     * @throws java.lang.Exception General exception
     */
    public RoadsModel readMapData(File source) throws Exception{
        Serializer serializer = new Persister(new CustomMatcher());
        return serializer.read(RoadsModel.class, source);
    }
    
    /**
     * saves the map data in xml file
     * @param model Road model
     * @param output Output file
     * @throws Exception General exception
     */
    public void saveMapData(RoadsModel model, File output) throws Exception{
        Serializer serializer = new Persister(new CustomMatcher());
        serializer.write(model, output);
    }
    
    /**
     * load the map data from OpenStreetMap data
     * @param source Source file
     * @return Osm root element
     * @throws java.lang.Exception general exception
     */
    public OsmRoot readOsmData(File source) throws Exception{
        Serializer serializer = new Persister(new CustomMatcher());
        OsmRoot root = serializer.read(OsmRoot.class, source);
        return root;
    }
}
