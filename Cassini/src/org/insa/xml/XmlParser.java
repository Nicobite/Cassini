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
package org.insa.xml;

import java.io.File;
import org.insa.model.RoadsModel;
import org.insa.model.Model;
import org.insa.model.VehiclesModel;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui &    Thomas Thiebaud
 Class XmlParser
 */
public class XmlParser {
    /**
     * serializer : see Simple framework for details
     */
    private final Serializer serializer;
    /**
     * openstreetmap data reader
     */
    private final OsmParser osmParser;
    public XmlParser(){
        serializer = new Persister();
        osmParser = new OsmParser();
    }
    
    /**
     * load the model data from xml
     * @param source
     * @return 
     * @throws java.lang.Exception
     */
    public Model readData(File source) throws Exception{
        return serializer.read(Model.class, source);
    }
    /**
     * saves the data in xml file
     * @param model the data to save
     * @param output the output file
     * @throws Exception 
     */
    public void saveData(Model model, File output) throws Exception{
        serializer.write(model, output);
    }
    /**
     * load the vehicles data from xml
     * @param source
     * @return 
     * @throws java.lang.Exception
     */
    public VehiclesModel readVehiclesData(File source) throws Exception{
        return serializer.read(VehiclesModel.class, source);
    }
    
    /**
     * saves the vehicles data in xml file
     * @param model
     * @param output
     * @throws Exception 
     */
    public void saveVehiclesData(VehiclesModel model, File output) throws Exception{
         serializer.write(model, output);
    }
     /**
     * load the map data from xml
     * @param source
     * @return 
     * @throws java.lang.Exception
     */
    public RoadsModel readMapData(File source) throws Exception{
        return serializer.read(RoadsModel.class, source);
    }
    
      /**
     * saves the map data in xml file
     * @param model
     * @param output
     * @throws Exception 
     */
    public void saveMapData(RoadsModel model, File output) throws Exception{
         serializer.write(model, output);
    }
      /**
     * load the map data from OpenStreetMap data
     * @param source
     * @return 
     * @throws java.lang.Exception
     */
    public RoadsModel readOsmData(File source) throws Exception{
        return osmParser.parseOsm(source);
    }
}
