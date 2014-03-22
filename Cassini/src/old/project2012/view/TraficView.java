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
package old.project2012.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import old.project2012.model.Coordinates;
import old.project2012.model.GlobalModel;
import old.project2012.model.Maths;
import old.project2012.model.junction.AccelerationLane;
import old.project2012.model.junction.CrossRoad;
import old.project2012.model.junction.Junction;
import old.project2012.model.junction.RoadSign;
import old.project2012.model.junction.RoadWayChange;
import old.project2012.model.junction.TraficLight;
import old.project2012.model.road.Road;
import old.project2012.model.road.RoadSection;
import old.project2012.model.road.RoadWay;
import old.project2012.model.vehicule.Behavior;
import old.project2012.model.vehicule.Vehicule;
import old.project2012.editor.RoadPropertiesPanel;
import old.project2012.editor.Toolbox;
import javax.swing.JOptionPane;
import old.project2012.model.road.RoadPosition;
import old.project2012.model.vehicule.Motorbike;
import old.project2012.model.vehicule.Truck;

/**
 *
 * @author gabriel
 */
public class TraficView extends Canvas{
        
    //Length of dashed lines between roadways
    private static final int dashLength = 2000;//mm

    private Coordinates coordinatesView;

    private int widthView;
    private int heightView;

    private int lastMouseX;
    private int lastMouseY;
    
    private Integer firstPointRoadX;
    private Integer firstPointRoadY;

    private ArrayList<Coordinates> roadSectionConnector = new ArrayList<Coordinates>();
    private BufferedImage roadImage;
    private boolean redrawRoad;
    
    //Editeur
    private boolean isEdited;
    private int tools = 0;
    private RoadSign.Sign intersectionType = RoadSign.Sign.NONE;
    private String vehiculeToAdd;
    private final static int ROADTOOLS = 1;
    private static final int MOUSETOOLS = 0;
	private static final int INTERSECTIONTOOLS = 2;
    private static final int SIGNALISATIONTOOLS = 3;
    private static final int DELETETOOLS = 4;
    private static final int ADDVEHICULETOOLS = 5;
    
	private GlobalModel globalModel;
    private MainView mainView;

	private Road road;
	private Junction junction;
    
    
    /**
     * @wbp.parser.constructor
     */
    public TraficView(GlobalModel globalModel){
        this(globalModel, new Coordinates(0,0),100000,100000);
    }
    
    public TraficView(GlobalModel globalModel, Coordinates coordinatesView, int widthView, int heightView){

    	this.globalModel=globalModel;
        this.coordinatesView = coordinatesView;
        this.widthView = widthView;
        this.heightView = heightView;      
        
        this.setBackground(Color.white);

        redrawRoad=true;
        
        this.setPreferredSize(new Dimension(600,600));
        
        this.addMouseWheelListener(new MouseAdapter() { 
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                mouseWheelMovedEvent(e);
            }
        });
        
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                mouseDraggedEvent(e);
            }
        });
        
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e){
                mousePressedEvent(e);
            }
        });

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e){
                componentResizedEvent(e);
            }
        });
        
    }
      
    public void mousePressedEvent(MouseEvent e){
        //We save the last mouse position to use for the draggedEvent
        
        if(e.getButton() == MouseEvent.BUTTON1){
            lastMouseX = e.getPoint().x;
            lastMouseY = e.getPoint().y;
            Vehicule v = globalModel.findVehiculeByCoordinates(new Coordinates(projInvX(lastMouseX), projInvY(lastMouseY)));
            if (v != null){
                Behavior.setVehiculeToFollow(v);
                InfoView.setVehiculeSelected(v);
            }       
        }
        
        System.out.println(projInvX(lastMouseX));
        System.out.println(projInvY(lastMouseY));
        
        if(isEdited){
        	System.out.println(""+tools);
        	//If we want to add a road
        	if(this.tools == MOUSETOOLS){
        		roadSectionConnector.clear();
        		RoadSection road = globalModel.findRoadSectionByCoordinates(new Coordinates(projInvX(lastMouseX), projInvY(lastMouseY)));
        		//Si on a cliquer sur une route
        		if(road != null){
        			if(mainView.getPropertiesPanel()==null){
			            RoadPropertiesPanel properties = new RoadPropertiesPanel();
			            properties.getNbVoiesDirect().setValue(road.getNbWayByDirection(true));
			            properties.getNbVoiesIndirect().setValue(road.getNbWayByDirection(false));
			            mainView.addPropertiesPanel(properties);
        			}
        			else{
        				mainView.getPropertiesPanel().getNbVoiesDirect().setValue(road.getNbWayByDirection(true));
        				mainView.getPropertiesPanel().getNbVoiesIndirect().setValue(road.getNbWayByDirection(false));
		            }
        		}
        		else{
        			mainView.removePropertiesPanel();
        		}
        	}
        	
        	if(this.tools == ROADTOOLS){
        		
        		if(e.getButton() == MouseEvent.BUTTON1){
    	        	if(this.roadSectionConnector.size()==0){
    	        		
    		            Integer firstPointRoadX = new Integer(projInvX(e.getPoint().x));
    		            Integer firstPointRoadY = new Integer(projInvY(e.getPoint().y));
    		            roadSectionConnector.add(new Coordinates(firstPointRoadX,firstPointRoadY));
    		            road = new Road("NewRoad");
    		            
    		            JPanel tool = mainView.getInfoView();
    		            RoadPropertiesPanel properties = ((Toolbox)tool).getRoadprop();
    		            
    	        		RoadSection section = new RoadSection();
    	        		section.addCoordinate(new Coordinates(firstPointRoadX,firstPointRoadY));
    	        		for(int i = 0; i<((Integer)properties.getNbVoiesDirect().getValue());i++){
    	        			section.addRoadWay(new RoadWay(Integer.parseInt(properties.getLargeurDirect().getText()), true,(Integer)properties.getVitesseMaxDirect().getValue()));
    	        		}
    	        		for(int i = 0; i<((Integer)properties.getNbVoiesIndirect().getValue());i++){
    	        			section.addRoadWay(new RoadWay(Integer.parseInt(properties.getLargeurIndirect().getText()), false, (Integer)properties.getVitesseMaxIndirect().getValue()));
    	        		}
    	        		road.getRoadSections().add(section);
    	        		globalModel.getRoads().add(road);
    		            }
    	        	else{
    	        		Integer firstPointRoadX = new Integer(projInvX(e.getPoint().x));
    		            Integer firstPointRoadY = new Integer(projInvY(e.getPoint().y));
    		            //roadSectionConnector.add(new Coordinates(firstPointRoadX,firstPointRoadY));

    		            Road temp = globalModel.getRoad(globalModel.getRoads().size()-1);
    	        		temp.getRoadSection(temp.getRoadSections().size()-1).addCoordinate(new Coordinates(firstPointRoadX,firstPointRoadY));
    	        		
    	        		this.redrawRoad();
    	        	}
    		        
                    
    	        }
            	else if(e.getButton() == MouseEvent.BUTTON3){
    	        		
            			System.out.println("clic droit");
            			
            			Integer firstPointRoadX = new Integer(projInvX(e.getPoint().x));
    		            Integer firstPointRoadY = new Integer(projInvY(e.getPoint().y));
    		            //roadSectionConnector.add(new Coordinates(firstPointRoadX,firstPointRoadY));
    		          
    		            Road temp = globalModel.getRoad(globalModel.getRoads().size()-1);
    	        		temp.getRoadSection(temp.getRoadSections().size()-1).addCoordinate(new Coordinates(firstPointRoadX,firstPointRoadY));
    	        		
    	        		roadSectionConnector.clear();
    	        		
    	        		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    	        		this.tools = 0;
    	        		this.redrawRoad();
    	        	
    	        }
        	}
	        
        	if(this.tools==INTERSECTIONTOOLS){
        		if(e.getButton() == MouseEvent.BUTTON1){
	        		if(junction == null){
	        			junction = new CrossRoad();
	        		}
                    Coordinates clicPos = new Coordinates(projInvX(lastMouseX), projInvY(lastMouseY));
            		RoadSection road = globalModel.findRoadSectionByCoordinates(clicPos);
            		//Si on a cliqué sur une route
            		if(road != null){
                        boolean begin= true;
                        if (Maths.distance(clicPos,road.getEndCoordinates()) < Maths.distance(clicPos, road.getCoordinate(0))){
                            begin=false;
                        }
                       
            			((CrossRoad)junction).addRoadSection(road, begin,new RoadSign("none"));
            			System.out.println("Route ajoutée");
            		}
        		}
        		if(e.getButton() == MouseEvent.BUTTON3){
        			
                        junction.sortRoadSection();
            		globalModel.getJunctions().add(junction);
            		junction = null;
                        this.redrawRoad();
        		}
        	}
                    	
            //Bouton de Signalisation 
            if(this.tools==SIGNALISATIONTOOLS){
                Coordinates clicPos = new Coordinates(projInvX(lastMouseX), projInvY(lastMouseY));
                RoadSection road = globalModel.findRoadSectionByCoordinates(clicPos);
                //Si on a cliqué sur une route
                if(road != null){
                    Junction j;
                    boolean begin=true;
                    if (Maths.distance(clicPos, road.getEndCoordinates()) < Maths.distance(clicPos, road.getCoordinate(0))) {
                        j = road.getEndJunction();
                        begin=false;
                    } else {
                        j = road.getBeginJunction();
                    }

                    if (this.intersectionType == RoadSign.Sign.TRAFIC_LIGHT){
                        int delay;
                        String state;
                        RoadSign tl;
                        
                        String[] states = {"Red", "Green"};
                        JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
                        state = ((String) jop.showInputDialog(null,
                                "Etat initial du feu ?",
                                "Nouvea feu tricolore",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                states,
                                states[0]) ).substring(0,1);
                        
                        delay =  Integer.parseInt(jop.showInputDialog(null, "Durée du feu en secondes ?", "Nouveau feu tricolore", JOptionPane.QUESTION_MESSAGE))*1000;
                        
                        tl = new TraficLight(state, delay);
                        globalModel.getTraficLights().add((TraficLight) tl); 
                        ((TraficLight)tl).pause();
                        
                        ((CrossRoad)j).setRoadSign(tl, j.getRoadSections().indexOf(road));
                    }
                    else{
                        ((CrossRoad)j).setRoadSign(new RoadSign(this.intersectionType), j.getRoadSections().indexOf(road));
                    }
                    
                    this.redrawRoad();
                            
                }
            }
            
             if(this.tools==DELETETOOLS){
                Coordinates clicPos = new Coordinates(projInvX(lastMouseX), projInvY(lastMouseY));
                RoadSection road = globalModel.findRoadSectionByCoordinates(clicPos);
                //Si on a cliqué sur une route
                if(road != null){
                    Junction j = road.getBeginJunction();
                    if (j != null){
                        int n = j.getRoadSections().indexOf(road);
                        j.getRoadSections().remove(road);
                        j.getBeginOfRoadSections().remove(n);
                    }
                    
                    j = road.getEndJunction();
                    if (j != null){
                        int n = j.getRoadSections().indexOf(road);
                        j.getRoadSections().remove(road);
                        j.getBeginOfRoadSections().remove(n);
                    }  
                    
                    globalModel.removeRoadSection(road);
                    this.redrawRoad();
                }
             }
             
             if(this.tools==ADDVEHICULETOOLS){
                Coordinates clicPos = new Coordinates(projInvX(lastMouseX), projInvY(lastMouseY));
                RoadSection road = globalModel.findRoadSectionByCoordinates(clicPos);
                //Si on a cliqué sur une route
                if(road != null){        
                    Vehicule v;
                    if (vehiculeToAdd.equalsIgnoreCase("Motorbike")) {
                        v= new Motorbike();
                    } else if (vehiculeToAdd.equalsIgnoreCase("Truck")) {
                        v = new Truck();
                    } else {
                        v = new Vehicule();
                    }                  
                    
                    this.globalModel.getVehicules().add(v);
                    v.setPhase(Vehicule.NORMAL_PHASE);
                    RoadPosition roadPos = findPositionWithCoordinates(clicPos, road);
                    v.setPosition(roadPos);
                    v.computeNextDestination();                    
                    
                }
             }
        }

        
    }
    
    RoadPosition findPositionWithCoordinates(Coordinates coords, RoadSection roadSection){
        RoadPosition roadPos;
        int minD = Integer.MAX_VALUE;
        int goodSegmentNumber=0;
        int distanceToBegining = Maths.distance(coords, roadSection.getCoordinate(0));
        
        
        for (int i=0; i< roadSection.getNumberOfCoordinates(); i++){
            int distance = Maths.distance(coords, roadSection.getCoordinate(i));
            int distanceToBegining2 = Maths.distance(roadSection.getCoordinate(i), roadSection.getCoordinate(0));
            if (minD > distance  && distanceToBegining2 < distanceToBegining){
                minD = distance;
                goodSegmentNumber=i;
            }
        }
        if (goodSegmentNumber >= roadSection.getNumberOfCoordinates()-1){
            goodSegmentNumber = roadSection.getNumberOfCoordinates()-2;
        }
        roadPos = new RoadPosition(roadSection, 0, goodSegmentNumber, 
                Maths.distance(coords, roadSection.getCoordinate(goodSegmentNumber)), 400);
        return roadPos;
    }
    
    
    public void setTools(int tools) {
		this.tools = tools;
	}
    
    public void setIntersectionType(RoadSign.Sign type){
         this.intersectionType = type;
    }
    
    public void setVehiculeToAdd (String vType){
        this.vehiculeToAdd = vType;
        
    }

	public void mouseDraggedEvent(MouseEvent e){
        if( (e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK){
           //We move the map in the same way of the drag
           coordinatesView.setX(projInvX(-(e.getPoint().x-lastMouseX)));
           coordinatesView.setY(projInvY(-(e.getPoint().y-lastMouseY)+getHeight()));
           lastMouseX = e.getPoint().x;
           lastMouseY = e.getPoint().y;
           redrawRoad = true;
        }
       
    }
    
    public void mouseWheelMovedEvent(MouseWheelEvent e){
        
        redrawRoad = true;
        if(e.getWheelRotation()>0){
            //Dezoom
            coordinatesView.setX(projInvX(e.getPoint().x)-widthView);
            coordinatesView.setY(projInvY(e.getPoint().y)-heightView);
            widthView*=2;
            heightView*=2;
        }
        else if(e.getWheelRotation()<0){
            //Zoom
            coordinatesView.setX(projInvX(e.getPoint().x)-widthView/4);
            coordinatesView.setY(projInvY(e.getPoint().y)-heightView/4);
            widthView/=2;
            heightView/=2;
            
            if(widthView<1){
                widthView=1;
            }
            if(heightView<1){
                heightView=1;
            }
            
        }
    }

    private void componentResizedEvent(ComponentEvent e) {
        redrawRoad= true;
    }
    
    public void setGlobalModel(GlobalModel globalModel){
        this.globalModel = globalModel;
    }
    
    public GlobalModel getGlobalModel(){
        return this.globalModel;
    }
    
    public void setView(Coordinates minCoord, Coordinates maxCoords){
        this.coordinatesView = minCoord;
        
        this.widthView=maxCoords.getX()-minCoord.getX();
        this.heightView=maxCoords.getY()-minCoord.getY();
    }
    
    @Override
    public synchronized void update(Graphics g)
    {
        BufferStrategy strategy = this.getBufferStrategy();
        Graphics2D gBuffer = (Graphics2D)strategy.getDrawGraphics();
        gBuffer.setBackground(Color.white);
        
        gBuffer.clearRect(0, 0, this.getWidth(), this.getHeight());

        if(redrawRoad ==true){
           //Draw all roads
            roadImage= new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB);
            Graphics2D gImage=(Graphics2D)roadImage.getGraphics();
            gImage.setBackground(Color.white);
            gImage.clearRect(0, 0, this.getWidth(), this.getHeight());
            gImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for(Road road:globalModel.getRoads()){
                this.drawRoad(road,gImage);
            }
                        
            //Draw all junctions
            for(Junction junction : globalModel.getJunctions()){
                this.drawJunction(junction, gImage);
            }
            
            redrawRoad = false;
        }
        gBuffer.drawImage(roadImage, null, 0, 0);
        
        
        redrawTraficLights(gBuffer);
        
        //Draw all vehicules
        for(Vehicule vehicule : globalModel.getVehicules()){
            this.drawVehicule(vehicule,gBuffer);
        }
        
        strategy.show();
        //System.out.println(globalModel.getVehicules().size());
    }
   
    private int projX(int x) {
	return (int)(this.getWidth() * ((float)(x-coordinatesView.getX())/widthView)) ;
    }
    
    private int projY(int y) {
	return this.getHeight()-(int)(this.getHeight() * ((float)(y-coordinatesView.getY())/heightView)) ;
    }
    
    private int ratioX(int x) {
	return (int)(x*this.getWidth()/widthView) ;
    }
    
    private int ratioY(int y) {
	return (int)(y*this.getHeight()/heightView) ;
    }
    
    private int projInvX(int x) {
	return (int)(coordinatesView.getX() + widthView*(float)(x)/(float)(this.getWidth()));
    }
    
    private int projInvY(int y) {
	return (int)(coordinatesView.getY() + heightView*(float)(this.getHeight()-y)/(float)(this.getHeight()));
    }
    
    private Coordinates proj(Coordinates c){
        return new Coordinates(projX(c.getX()),projY(c.getY()));
    }
    
    private void drawRoad(Road road, Graphics2D g2){
        
        for(RoadSection roadSection : road.getRoadSections()){
            
            if(roadSection.getCoordinates().size() >= 2){
                
                ArrayList<Coordinates> roadSectionCoordinates = roadSection.getCoordinates();
                int totalWidth = roadSection.getTotalWidth();
                
                for(int numSegment=0;numSegment<roadSectionCoordinates.size()-1;numSegment++){
                   
                    Coordinates beginSegment = roadSectionCoordinates.get(numSegment);
                    Coordinates endSegment = roadSectionCoordinates.get(numSegment+1);
                    
                    float beginAngle = roadSection.getSegmentBeginTurnAngle(numSegment);
                    float beginRatio = roadSection.getSegmentBeginTurnRatio(numSegment);
    
                    float endAngle = roadSection.getSegmentEndTurnAngle(numSegment);
                    float endRatio = roadSection.getSegmentEndTurnRatio(numSegment);
                    
                    /*Coordinates beginSegment2 = Maths.findArrivalCoordinateFromVector(beginSegment, beginAngle, (int)(totalWidth*beginRatio));
                                                                
                    Coordinates endSegment2 = Maths.findArrivalCoordinateFromVector(endSegment, endAngle, (int)(totalWidth*endRatio));
                    */
                    Coordinates beginSegment2 = roadSection.getOpposedCoordinates(numSegment);
                    Coordinates endSegment2 = roadSection.getOpposedCoordinates(numSegment+1);       
                    
                    //Draw the background of the road
                    Polygon poly = new Polygon();
                    poly.addPoint(projX(beginSegment.getX()), projY(beginSegment.getY()));
                    poly.addPoint(projX(endSegment.getX()), projY(endSegment.getY()));
                    poly.addPoint(projX(endSegment2.getX()), projY(endSegment2.getY()));
                    poly.addPoint(projX(beginSegment2.getX()), projY(beginSegment2.getY()));
                    
                    g2.setColor(Color.lightGray);
                    g2.fillPolygon(poly);
                    
                    //Draw the outline of the road
                    g2.setColor(Color.black);
                    g2.drawLine(projX(beginSegment.getX()),projY(beginSegment.getY()),
                                projX(endSegment.getX()), projY(endSegment.getY()));
                    g2.drawLine(projX(beginSegment2.getX()),projY(beginSegment2.getY()),
                                projX(endSegment2.getX()), projY(endSegment2.getY()));
                    
                    //draw the first point of our road
                    if(isEdited){
                    	Polygon source = new Polygon();
                        source.addPoint(projX(beginSegment.getX()-1000), projY(beginSegment.getY()-1000));
                        source.addPoint(projX(beginSegment.getX()-1000), projY(beginSegment.getY()+1000));
                        source.addPoint(projX(beginSegment.getX()+1000), projY(beginSegment.getY()+1000));
                        source.addPoint(projX(beginSegment.getX()+1000), projY(beginSegment.getY()-1000));
                        g2.setColor(Color.yellow);
                        g2.fillPolygon(source);
                    }
                    // Draw roadways separation lines
                    int dashOffset = 0;
                    //ArrayList<RoadWay> roadWays = roadSection.getRoadWays();
                    for(int numWay=0;numWay<roadSection.getNumberWays()-1;numWay++){
                        dashOffset += roadSection.getRoadWay(numWay).getWidth();
                        
                        beginSegment2 = Maths.findArrivalCoordinateFromVector(beginSegment, beginAngle, (int)(dashOffset*beginRatio));
                        endSegment2 = Maths.findArrivalCoordinateFromVector(endSegment, endAngle, (int)(dashOffset*endRatio));      
                        
                        g2.setColor(Color.WHITE);

                        //If the ways are in the same direction - dashed line
                        if(roadSection.getRoadWay(numWay).getDirection() == roadSection.getRoadWay(numWay+1).getDirection()){
                            int dx = (int) (dashLength*Math.cos(Maths.angle(beginSegment2, endSegment2)));
                            int dy = (int) (dashLength*Math.sin(Maths.angle(beginSegment2, endSegment2)));
                            
                            for (int i=0; i < roadSection.getSegmentLength(numSegment)/dashLength/2; i++){
                                g2.drawLine(projX(beginSegment2.getX()+ 2*i*dx) ,projY(beginSegment2.getY()+ 2*i*dy),
                                    projX(beginSegment2.getX() + dx*(1+2*i)), projY(beginSegment2.getY()+ dy*(1+2*i)));
                            }

                        }
                        else{

                            //If the ways are in opposite direction - solid line
                            g2.drawLine(projX(beginSegment2.getX()),projY(beginSegment2.getY()),
                                        projX(endSegment2.getX()), projY(endSegment2.getY()));
                            
                        }
                    }
                    

                }
            }

            
        }
    }
    
    private void drawVehicule(Vehicule vehicule, Graphics2D g2){
             
        Polygon poly = new Polygon();
        float angle = vehicule.getPosition().getAngle();
        
        Coordinates fr = vehicule.getPosition().getVehiculeCoordinates();
        Coordinates fl = Maths.findArrivalCoordinateFromVector(fr, angle + Maths.PI_2, vehicule.getWidth());
        Coordinates br = Maths.findArrivalCoordinateFromVector(fr, angle, -vehicule.getLength());
        Coordinates bl = Maths.findArrivalCoordinateFromVector(fl, angle, -vehicule.getLength());
                
        poly.addPoint(projX(fr.getX()), projY(fr.getY()));
        poly.addPoint(projX(fl.getX()), projY(fl.getY()));
        poly.addPoint(projX(bl.getX()), projY(bl.getY()));
        poly.addPoint(projX(br.getX()), projY(br.getY()));
          
        //g2.setColor(Color.BLUE);
        g2.setColor(vehicule.getBehavior().getBehaviorColor());
        g2.fillPolygon(poly);
        
        
        g2.setColor(Color.ORANGE);
        if(vehicule.isTurnSignalLeft()){
            int rx = ratioX(1000), ry=ratioY(1000);
            g2.fillOval(projX(fl.getX())-rx/2, projY(fl.getY())-ry/2,rx ,ry);
        }
        if(vehicule.isTurnSignalRight()){
            int rx = ratioX(1000), ry=ratioY(1000);
            g2.fillOval(projX(fr.getX())-rx/2, projY(fr.getY())-ry/2,rx ,ry);
        }
        
        g2.setColor(Color.RED);
        if(vehicule.isBrake()){
            int rx = ratioX(1000), ry=ratioY(1000);
            g2.fillOval(projX(br.getX())-rx/2, projY(br.getY())-ry/2,rx ,ry);
            g2.fillOval(projX(bl.getX())-rx/2, projY(bl.getY())-ry/2,rx ,ry);
        }
        
    }
    
    private void drawJunction(Junction junction, Graphics2D g2){
        
        if(junction instanceof RoadWayChange){
            RoadWayChange roadWayChange = (RoadWayChange)junction;
            Coordinates beginSegment, beginOppSeg, beginMidSeg,endSegment, endOppSeg, endMidSeg;
            
            if(roadWayChange.isBeginOfFirstRoadSection()){
                beginSegment = roadWayChange.getFirstRoadSection().getCoordinate(0);
                beginMidSeg = roadWayChange.getFirstRoadSection().getBeginMiddleCoordinates();
                beginOppSeg = roadWayChange.getFirstRoadSection().getOpposedBeginCoordinates();
            }else{
                beginSegment = roadWayChange.getFirstRoadSection().getCoordinate(roadWayChange.getSecondRoadSection().getCoordinates().size()-1);
                beginMidSeg = roadWayChange.getFirstRoadSection().getEndMiddleCoordinates();
                beginOppSeg = roadWayChange.getFirstRoadSection().getOpposedEndCoordinates();
            }
        
            if(roadWayChange.isBeginOfSecondRoadSection()){
                endSegment = roadWayChange.getSecondRoadSection().getCoordinate(0);
                endMidSeg = roadWayChange.getSecondRoadSection().getBeginMiddleCoordinates();
                endOppSeg = roadWayChange.getSecondRoadSection().getOpposedBeginCoordinates();
            }else{
                endSegment = roadWayChange.getSecondRoadSection().getCoordinate(roadWayChange.getSecondRoadSection().getCoordinates().size()-1);
                endMidSeg = roadWayChange.getSecondRoadSection().getEndMiddleCoordinates();
                endOppSeg = roadWayChange.getSecondRoadSection().getOpposedEndCoordinates();
            }
            
            
            //Draw the background of the road
            Polygon poly = new Polygon();
            poly.addPoint(projX(beginSegment.getX()), projY(beginSegment.getY()));
            poly.addPoint(projX(endSegment.getX()), projY(endSegment.getY()));
            poly.addPoint(projX(endOppSeg.getX()), projY(endOppSeg.getY()));
            poly.addPoint(projX(beginOppSeg.getX()), projY(beginOppSeg.getY()));
            
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillPolygon(poly);
            
            
            g2.setColor(Color.WHITE);
            g2.drawLine(projX(beginMidSeg.getX()),projY(beginMidSeg.getY()),
                                projX(endMidSeg.getX()), projY(endMidSeg.getY()));
            //Draw the border of the road
            g2.setColor(Color.BLACK);
            g2.drawLine(projX(beginSegment.getX()),projY(beginSegment.getY()),
                                projX(endSegment.getX()), projY(endSegment.getY()));
            g2.drawLine(projX(beginOppSeg.getX()),projY(beginOppSeg.getY()),
                                projX(endOppSeg.getX()), projY(endOppSeg.getY()));
            

            
        }
        else if(junction instanceof AccelerationLane){
            AccelerationLane accelerationLane = (AccelerationLane)junction;
            //Draw the background of the road
            Polygon poly = new Polygon();
            RoadSection roadSection;
            Coordinates c,c1,c2;

             for (int i=0; i<accelerationLane.getNumberRoadSections(); i++){
                 roadSection = accelerationLane.getRoadSection(i);
                 if(accelerationLane.getBeginOfRoadSection(i)== true)
                 {
                    c = roadSection.getOpposedBeginCoordinates();
                    poly.addPoint(projX(c.getX()), projY(c.getY()));
                    c = roadSection.getCoordinate(0);
                    poly.addPoint(projX(c.getX()), projY(c.getY()));
                 }
                 else{
                     c = roadSection.getEndCoordinates();
                    poly.addPoint(projX(c.getX()), projY(c.getY()));
                    c = roadSection.getOpposedEndCoordinates();
                    poly.addPoint(projX(c.getX()), projY(c.getY()));
                 }
             }
              g2.setColor(Color.GRAY);
              g2.fillPolygon(poly);
              //Draw the external lines of the road
              g2.setColor(Color.BLACK);
              if(accelerationLane.getBeginOfBeginRoadSection() == true)
              {
                  c1 = accelerationLane.getBeginRoadSection().getCoordinate(0);
              }
              else{
                  c1 = accelerationLane.getBeginRoadSection().getOpposedEndCoordinates();
              }
              if(accelerationLane.getBeginOfEndRoadSection() == true)
              {
                  c2 = accelerationLane.getEndRoadSection().getOpposedBeginCoordinates();
              }
              else{
                  c2 = accelerationLane.getEndRoadSection().getEndCoordinates();
              }
              g2.drawLine(projX(c1.getX()), projY(c1.getY()), projX(c2.getX()), projY(c2.getY()));
              if(accelerationLane.getBeginOfBeginRoadSection() == true)
              {
                  c1 = accelerationLane.getBeginRoadSection().getOpposedBeginCoordinates();
              }
              else{
                  c1 = accelerationLane.getBeginRoadSection().getEndCoordinates();
              }
              if(accelerationLane.getBeginOfALRoadSection() == true)
              {
                  c2 = accelerationLane.getALRoadSection().getCoordinate(0);
              }
              else{
                  c2 = accelerationLane.getALRoadSection().getOpposedEndCoordinates();
              }
              g2.drawLine(projX(c1.getX()), projY(c1.getY()), projX(c2.getX()), projY(c2.getY()));
              if(accelerationLane.getBeginOfEndRoadSection() == true)
              {
                  c1 = accelerationLane.getEndRoadSection().getCoordinate(0);
              }
              else{
                  c1 = accelerationLane.getEndRoadSection().getOpposedEndCoordinates();
              }
              if(accelerationLane.getBeginOfALRoadSection() == true)
              {
                  c2 = accelerationLane.getALRoadSection().getOpposedBeginCoordinates();
              }
              else{
                  c2 = accelerationLane.getALRoadSection().getEndCoordinates();
              }
              float angleAL = Maths.angle(c2, c1);
             g2.drawLine(projX(c1.getX()), projY(c1.getY()), projX(c2.getX()), projY(c2.getY()));
             //draw the internal lines
             int dashOffset = 0;
             RoadSection beginRoadSection,endRoadSection;
             beginRoadSection = accelerationLane.getBeginRoadSection();
             endRoadSection = accelerationLane.getEndRoadSection();
             Coordinates beginSegment,beginSegment2 ;
            Coordinates endSegment,endSegment2;
             float angle;

            if(accelerationLane.getBeginOfBeginRoadSection()== true)
            {
                beginSegment = beginRoadSection.getOpposedBeginCoordinates();

            }
            else{
                beginSegment = beginRoadSection.getEndCoordinates();
            }
            if(accelerationLane.getBeginOfEndRoadSection()== true)
            {
                endSegment = endRoadSection.getCoordinate(0);
            }
            else{
                endSegment = endRoadSection.getOpposedEndCoordinates();
            }
            angle = Maths.angle(beginSegment, endSegment);

            for( int numWay = 0; numWay < beginRoadSection.getNumberWays()- 1;numWay++){
                dashOffset += beginRoadSection.getRoadWay(numWay).getWidth();

                beginSegment2 = Maths.findArrivalCoordinateFromVector(beginSegment, angle+Maths.PI_2, (int)(dashOffset));
                endSegment2 = Maths.findArrivalCoordinateFromVector(endSegment, angle+Maths.PI_2, (int)(dashOffset));

                g2.setColor(Color.WHITE);

                //If the ways are in the same direction - dashed line
                if(beginRoadSection.getRoadWay(numWay).getDirection() == beginRoadSection.getRoadWay(numWay+1).getDirection()){
                    int dx = (int) (dashLength*Math.cos(Maths.angle(beginSegment2, endSegment2)));
                    int dy = (int) (dashLength*Math.sin(Maths.angle(beginSegment2, endSegment2)));

                    for (int i=0; i < Maths.distance(beginSegment2, endSegment2) /dashLength/2; i++){
                        g2.drawLine(projX(beginSegment2.getX()+ 2*i*dx) ,projY(beginSegment2.getY()+ 2*i*dy),
                            projX(beginSegment2.getX() + dx*(1+2*i)), projY(beginSegment2.getY()+ dy*(1+2*i)));
                    }
                }
                else{

                    //If the ways are in opposite direction - solid line
                    g2.drawLine(projX(beginSegment2.getX()),projY(beginSegment2.getY()),
                                projX(endSegment2.getX()), projY(endSegment2.getY()));

                }
            }
              //draw stop way of the acceleration lane
             g2.setColor(Color.WHITE);
             //AL road section
              if(accelerationLane.getBeginOfBeginRoadSection() == true)
              {
                  c2 = accelerationLane.getBeginRoadSection().getOpposedBeginCoordinates();
              }
              else{
                  c2 = accelerationLane.getBeginRoadSection().getEndCoordinates();
              }

             int widthToReach = 0;
            int width = Maths.distance(c1, c2);
            float angleLine = Maths.angle(c2, c1);

            Polygon square;
            Coordinates a,b,d;

            b = c2;
            a = Maths.findArrivalCoordinateFromVector(b,angleLine-Maths.PI_2,accelerationLane.getWidthLine());

            while(widthToReach+accelerationLane.getLengthLine() < width && (Maths.angle(Maths.findArrivalCoordinateFromVector(a, angleLine, accelerationLane.getLengthLine()), c1) < angleAL))
            {
                c = Maths.findArrivalCoordinateFromVector(b, angleLine, accelerationLane.getLengthLine());
                d = Maths.findArrivalCoordinateFromVector(a, angleLine, accelerationLane.getLengthLine());
                square = new Polygon();
                square.addPoint(projX(a.getX()), projY(a.getY()));
                square.addPoint(projX(b.getX()), projY(b.getY()));
                square.addPoint(projX(c.getX()), projY(c.getY()));
                square.addPoint(projX(d.getX()), projY(d.getY()));
                g2.fill(square);
                b = Maths.findArrivalCoordinateFromVector(c, angleLine,accelerationLane.getBetweenLines() );
                a = Maths.findArrivalCoordinateFromVector(d, angleLine,accelerationLane.getBetweenLines() );
                widthToReach += (accelerationLane.getBetweenLines()+accelerationLane.getLengthLine());
            }
        }
        else if(junction instanceof CrossRoad){
            CrossRoad crossRoad = (CrossRoad)junction;
            //Draw the background of the road
            Polygon poly = new Polygon();
            //Draw the background of the road
            Polygon polyLines=new Polygon();
            float ratio;
            Coordinates c1,c2,c3,middle,middle2;
        
            //Draw each cross road
            for(int i=0; i< crossRoad.getRoadSections().size();i++)
            {

                if(crossRoad.getBeginOfRoadSection(i)== true)
                {
                    c1 = crossRoad.getRoadSection(i).getOpposedBeginCoordinates();
                    c2 = crossRoad.getRoadSection(i).getCoordinate(0);
                    ratio = crossRoad.getRoadSection(i).getSegmentBeginTurnRatio(0);
                    //Draw lines indicate the type of the Cross road
                    middle = crossRoad.getRoadSection(i).getBeginMiddleCoordinates();
                    middle2 = Maths.findArrivalCoordinateFromVector(middle,crossRoad.getRoadSection(i).getSegmentAngle(0),(int)(500*ratio));
                    c3 = Maths.findArrivalCoordinateFromVector(c1,crossRoad.getRoadSection(i).getSegmentAngle(0),(int)(500*ratio));

                }
                else{
                    c1 = crossRoad.getRoadSection(i).getEndCoordinates();
                    c2 = crossRoad.getRoadSection(i).getOpposedEndCoordinates();
                    ratio = crossRoad.getRoadSection(i).getSegmentEndTurnRatio(0);
                    //Draw lines indicate the type of the Cross road 
                    middle = crossRoad.getRoadSection(i).getEndMiddleCoordinates();
                    middle2 = Maths.findArrivalCoordinateFromVector(middle,(crossRoad.getRoadSection(i).getSegmentAngle(crossRoad.getRoadSection(i).getCoordinates().size()-2)+Maths.PI),(int)(500*ratio));
                    c3 = Maths.findArrivalCoordinateFromVector(c1,(crossRoad.getRoadSection(i).getSegmentAngle(crossRoad.getRoadSection(i).getCoordinates().size()-2)+Maths.PI),(int)(500*ratio));
                    
                  
                }
                if(crossRoad.getRoadSign(i).getSign() == RoadSign.Sign.STOP)
                {
                    polyLines.addPoint(projX(middle.getX()), projY(middle.getY()));
                    polyLines.addPoint(projX(c1.getX()), projY(c1.getY()));
                    polyLines.addPoint(projX(c3.getX()), projY(c3.getY()));
                    polyLines.addPoint(projX(middle2.getX()), projY(middle2.getY()));
                    g2.setColor(Color.WHITE);
                    g2.fillPolygon(polyLines);
                }
                else if(crossRoad.getRoadSign(i).getSign() == RoadSign.Sign.GIVE_WAY)
                {
                    int widthToReach =0;
                    int width = Maths.distance(middle, c1);
                    float angleLine = Maths.angle(middle, c1);
                    Polygon square;
                    Coordinates a,b,c,d;
                    a = middle2;
                    b = middle;
                    while(widthToReach < width)
                    {
                        c = Maths.findArrivalCoordinateFromVector(b, angleLine, 500);
                        d = Maths.findArrivalCoordinateFromVector(a, angleLine, 500);
                        square = new Polygon();
                        square.addPoint(projX(a.getX()), projY(a.getY()));
                        square.addPoint(projX(b.getX()), projY(b.getY()));
                        square.addPoint(projX(c.getX()), projY(c.getY()));
                        square.addPoint(projX(d.getX()), projY(d.getY()));
                        g2.setColor(Color.WHITE);
                        g2.fill(square);
                        b = Maths.findArrivalCoordinateFromVector(b, angleLine, 1000);
                        a = Maths.findArrivalCoordinateFromVector(a, angleLine, 1000);
                        widthToReach += 1000;
                    }
                }
         
                poly.addPoint(projX(c1.getX()), projY(c1.getY()));
                poly.addPoint(projX(c2.getX()), projY(c2.getY()));

                g2.setColor(Color.WHITE);
                g2.drawLine(projX(middle.getX()), projY(middle.getY()), projX(middle2.getX()), projY(middle2.getY()));
                g2.setColor(Color.BLACK);
                g2.drawLine(projX(c1.getX()), projY(c1.getY()), projX(c3.getX()), projY(c3.getY()));
            }

            g2.setColor(Color.GRAY);
            g2.fillPolygon(poly);

            
            
         
        }
        

    }
    
    
    void redrawTraficLights(Graphics2D g2){
        
        //For all junctions
        for(Junction junction : globalModel.getJunctions()){
            if( junction instanceof CrossRoad){
                CrossRoad crossRoad = (CrossRoad) junction;    

                //Draw trafic lights
                for (int i=0; i<crossRoad.getNumberRoadSections(); i++){
                    TraficLight traficLight;
                    Coordinates c;
                    int offset;

                    if (crossRoad.getRoadSign(i).getSign() == RoadSign.Sign.TRAFIC_LIGHT ){
                        traficLight = (TraficLight) crossRoad.getRoadSign(i);
                        float angle;

                        if(crossRoad.getBeginOfRoadSection(i)){
                            c= crossRoad.getRoadSection(i).getCoordinate(0);
                            offset = crossRoad.getRoadSection(i).getTotalWidth() + 2000;
                            angle = crossRoad.getRoadSection(i).getSegmentAngle(0);
                            c = Maths.findArrivalCoordinateFromVector(c, angle + Maths.PI_2 , offset);
                        }
                        else{
                            c= crossRoad.getRoadSection(i).getCoordinate(crossRoad.getRoadSection(i).getNumberOfCoordinates()-1);
                            angle = crossRoad.getRoadSection(i).getSegmentAngle( crossRoad.getRoadSection(i).getNumberOfCoordinates()-2 ) + Maths.PI;
                            c = Maths.findArrivalCoordinateFromVector(c, angle + Maths.PI_2 , 2000);
                            c = Maths.findArrivalCoordinateFromVector(c, angle, 2000);
                        }

                        int rayon = 1200000/widthView;

                        g2.setColor(Color.BLACK);;
                        Coordinates cT[] = new Coordinates[3];
                        cT[2]= Maths.findArrivalCoordinateFromVector(c, angle , 2*2000);
                        cT[1]= Maths.findArrivalCoordinateFromVector(c, angle , 2000);
                        cT[0]=c;
                        g2.drawOval(projX(c.getX()), projY(c.getY()), rayon ,rayon);
                        g2.drawOval(projX(cT[1].getX()), projY(cT[1].getY()), rayon ,rayon);
                        g2.drawOval(projX(cT[2].getX()), projY(cT[2].getY()), rayon ,rayon);

                        g2.setColor(traficLight.getColor());
                        int n=traficLight.getLightNo();
                        g2.fillOval(projX(cT[n].getX()), projY(cT[n].getY()), rayon, rayon);
                    }
                }
            }
        }
    }
    
    
    private void drawRoadSign(Graphics2D g2,RoadSection roadSection,Boolean beginRoadSection,String roadSign)
    {
        Coordinates beginSegment,endSegment;
        int widthWays =0,numSegment;
        float angle,ratio;

        g2.setColor(Color.BLUE);
        int numWay;
        
        if(beginRoadSection){
            numSegment = 0;
            angle = roadSection.getSegmentBeginTurnAngle(numSegment);
            ratio = roadSection.getSegmentBeginTurnRatio(numSegment);
            beginSegment = roadSection.getOpposedBeginCoordinates();
            numWay = roadSection.getNumberWays()-1;

            while(numWay > 0 && roadSection.getRoadWay(numWay).getDirection() == roadSection.getRoadWay(roadSection.getNumberWays()-1).getDirection()){
                widthWays += roadSection.getRoadWay(numWay).getWidth();
                numWay--;
            }
        }
        else{
            numSegment = roadSection.getNumberOfCoordinates()-2;
            angle = roadSection.getSegmentEndTurnAngle(numSegment);
            ratio = roadSection.getSegmentBeginTurnRatio(numSegment);
            beginSegment = roadSection.getCoordinate(numSegment);
            numWay = 0;

             while(numWay < roadSection.getNumberWays()-1 && roadSection.getRoadWay(numWay).getDirection() == roadSection.getRoadWay(0).getDirection()){
                widthWays += roadSection.getRoadWay(numWay).getWidth();
                numWay++;
            }
        }
        endSegment = Maths.findArrivalCoordinateFromVector(beginSegment, angle, (int)(widthWays*ratio));
        
        if(roadSign.equalsIgnoreCase("stop"))
        {

        }else if(roadSign.equalsIgnoreCase("giveWay"))
        {
            //g2.setStroke(new BasicStroke(widthWays));
                    //(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, {.5f, .5f}, .0f));
            g2.drawLine(projX(beginSegment.getX()),projY(beginSegment.getY()),
                                projX(endSegment.getX()), projY(endSegment.getY()));
       }
    }
     public void setCoordinatesView(Coordinates coordinatesView) {
        this.coordinatesView = coordinatesView;
    }


    public void setHeightView(int heightView) {
        this.heightView = heightView;
    }

    public void setWidthView(int widthView) {
        this.widthView = widthView;
    }

    public void redrawRoad() {
        this.redrawRoad = true;
    }
    
    public void setEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}
    
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

}
