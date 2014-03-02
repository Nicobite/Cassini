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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.insa.model.RoadsModel;
import org.insa.xml.osm.OsmModel;
import org.insa.xml.osm.OsmNode;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Juste Abel Oueadraogo & Guillaume Garzone & François Aïssaoui &   Thomas Thiebaud
 * Class OsmParser
 */
public class OsmParser {
    
    public RoadsModel parseOsm(File source) throws Exception{
        return null;
    }
    public static void main(String args[]){
        Serializer s = new Persister();
        File source = new File("insa.osm");
        try {
            OsmModel m = s.read(OsmModel.class, source);
           /* for(OsmWay w : m.getWays()){
                for(OsmTag tag : w.getTags())
                    System.out.println("way N°"+w.getId()+",k = "+tag.getKey()+",v="+tag.getValue());
            }
            System.out.println("Okay");
                   */
            HashMap<Integer, OsmNode> nodes =  m.getOsmNodes();
            for(int key : nodes.keySet()){
                System.out.println("id = "+key+", value = "+nodes.get(key));
            }
        } catch (Exception ex) {
            Logger.getLogger(OsmParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
