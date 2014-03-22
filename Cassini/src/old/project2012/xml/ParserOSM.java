/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <The Simulation Team> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer (or a Mojito for Julie)
 * in return.
 * Guillaume Blanc & Gabriel Charlemagne & Jonathan Fernandez & Julie Marti
 * ----------------------------------------------------------------------------
 */
package old.project2012.xml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import old.project2012.osmModel.Node;
import old.project2012.osmModel.OSMModel;
import old.project2012.osmModel.Way;

/**
 *
 * @author gabriel
 */
public class ParserOSM extends DefaultHandler{
    
    OSMModel osmModel;
    String context,tempVal;
    
    Node node;
    Way way;
    
    public ParserOSM(OSMModel osmModel){
        this.osmModel = osmModel;
    }
    
    public void parseFile(String file){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(file, this);
            
        } catch (IOException ex) {
            Logger.getLogger(ParserLoadingMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ParserLoadingMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ParserLoadingMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

                if (qName.equalsIgnoreCase("osm")) {
                    osmModel.clear();
                    context="osm";
                }else if(qName.equalsIgnoreCase("bounds")){
                    
                    for(int i=0;i<attributes.getLength();i++){
                        if(attributes.getQName(i).equalsIgnoreCase("minlat")){
                            System.out.println("lat");
                            osmModel.setMinLat(Float.parseFloat(attributes.getValue(i)));
                        }else if(attributes.getQName(i).equalsIgnoreCase("minlon")){
                            System.out.println("lon");
                            osmModel.setMinLon(Float.parseFloat(attributes.getValue(i)));
                        }
                    }
                }else if(qName.equalsIgnoreCase("node")){
                    node = new Node();
                    
                    for(int i=0;i<attributes.getLength();i++){
                        if(attributes.getQName(i).equalsIgnoreCase("id")){
                            node.setId(Integer.parseInt(attributes.getValue(i)));
                        }else if(attributes.getQName(i).equalsIgnoreCase("lat")){
                            node.setLatitude(Float.parseFloat(attributes.getValue(i)));
                        }else if(attributes.getQName(i).equalsIgnoreCase("lon")){
                            node.setLongitude(Float.parseFloat(attributes.getValue(i)));
                        }
                    }
                    
                    osmModel.getNodes().add(node);
                }else if(qName.equalsIgnoreCase("way")){
                    context="way";
                    way = new Way();
                    for(int i=0;i<attributes.getLength();i++){
                        if(attributes.getQName(i).equalsIgnoreCase("id")){
                            way.setId(Integer.parseInt(attributes.getValue(i)));
                        }
                    }
                }else if(context.equalsIgnoreCase("way")
                    && qName.equalsIgnoreCase("nd")){
                    for(int i=0;i<attributes.getLength();i++){
                        if(attributes.getQName(i).equalsIgnoreCase("ref")){
                            Node n = osmModel.findNodeById(Integer.parseInt(attributes.getValue(i)));
                            if(n!=null){
                                way.getNodes().add(n);
                                n.setNbWay(n.getNbWay()+1);
                            }else{
                                System.out.println("Node non trouvé");
                            }
                        }
                    }
                }else if(context.equalsIgnoreCase("way")
                    && qName.equalsIgnoreCase("tag")){
                    if(attributes.getLength()>=2){
                        //System.out.println(attributes.getQName(0)+"="+attributes.getValue(0));
                        if(attributes.getQName(0).equalsIgnoreCase("k")
                                && attributes.getValue(0).equalsIgnoreCase("highway")){
                            way.setType(attributes.getValue(1));
                        }else if (attributes.getQName(0).equalsIgnoreCase("k")
                                && attributes.getValue(0).equalsIgnoreCase("name")) {
                            way.setName(attributes.getValue(1));
                        }else if (attributes.getQName(0).equalsIgnoreCase("k")
                                && attributes.getValue(0).equalsIgnoreCase("ref")) {
                            way.setRef(attributes.getValue(1));
                        } else if (attributes.getQName(0).equalsIgnoreCase("k")
                                && attributes.getValue(0).equalsIgnoreCase("lanes")) {
                            way.setLanes(Integer.parseInt(attributes.getValue(1)));
                        }else if (attributes.getQName(0).equalsIgnoreCase("k")
                                && attributes.getValue(0).equalsIgnoreCase("oneway")) {
                            if(attributes.getValue(1).equalsIgnoreCase("yes")
                                    || attributes.getValue(1).equalsIgnoreCase("true")
                                    || attributes.getValue(1).equalsIgnoreCase("1")){
                                way.setOneWay(true);
                            } 
                        }else if (attributes.getQName(0).equalsIgnoreCase("k")
                                && attributes.getValue(0).equalsIgnoreCase("junction")) {
                            if(attributes.getValue(1).equalsIgnoreCase("roundabout")){
                                way.setOneWay(true);
                            }
                        }

                    }
                }
                
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch,start,length);
    }
    
    @Override
    public void endElement(String uri, String localName,
		String qName) throws SAXException {
        if (qName.equalsIgnoreCase("way")) {
            if(way.getType() != null){
                osmModel.getWays().add(way);
                context="osm";
            }
            
        }
        
        


    }


    
    
    
}
