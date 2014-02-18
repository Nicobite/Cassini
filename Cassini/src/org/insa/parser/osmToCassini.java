/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.insa.parser;

import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.kodapan.osm.domain.root.PojoRoot;
import se.kodapan.osm.parser.xml.instantiated.InstantiatedOsmXmlParser;

/**
 *
 * @author ouedraog
 */
public class osmToCassini {
      
    public static void main(String[] args){
        osmToCassini osmToCassini = new osmToCassini();
    }
    public void testInsa() throws Exception {

    PojoRoot root = new PojoRoot();
    InstantiatedOsmXmlParser parser = InstantiatedOsmXmlParser.newInstance();
    parser.setRoot(root);

    parser.parse(new InputStreamReader(getClass().getResourceAsStream("/org/insa/parser/resources/insa.osm.xml"), "UTF8"));

   System.out.println(root.getNodes().size());
    System.out.println(root.getWays().size());
    System.out.println(root.getRelations().size());

    System.currentTimeMillis();
  }

    public osmToCassini() {
        try {
            testInsa();
        } catch (Exception ex) {
            Logger.getLogger(osmToCassini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
}
