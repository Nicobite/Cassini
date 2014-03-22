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
package old.project2012.model;

import java.awt.Polygon;
import java.util.ArrayList;
import old.project2012.model.junction.CrossRoad;
import old.project2012.model.junction.Junction;
import old.project2012.model.junction.RoadSectionJunction;
import old.project2012.model.junction.TraficLight;
import old.project2012.model.road.Road;
import old.project2012.model.road.RoadPosition;
import old.project2012.model.road.RoadSection;
import old.project2012.model.vehicule.Behavior;
import old.project2012.model.vehicule.Motorbike;
import old.project2012.model.vehicule.Truck;
import old.project2012.model.vehicule.Vehicule;

/**
 *
 * @author gabriel
 */
public class GlobalModel {
    
    private ArrayList<Road> roads;
    private ArrayList<Vehicule> vehicules;
    private ArrayList<Junction> junctions;
    private ArrayList<TraficLight> traficLights;
    

    public GlobalModel(){
        roads = new ArrayList<Road>();
        vehicules = new ArrayList<Vehicule>();
        junctions = new ArrayList<Junction>();
        traficLights = new ArrayList<TraficLight>();
    }
        
     
    public void loadVehicules(){       
//        LoadTestRoadWays();
        //loadTestTraficLights();
        //loadTestIntersections();
        //loadTestHighWay();
        //loadRoadWayChange();


        //loadTestAccelerationLane();
        //loadOSM();
        //loadTestAccelerationLane();

       // sortVehicules();
        //loadRond();
        
        sortVehicules();
    }
    
    private void loadRond(){
        Vehicule v;
        RoadPosition roadPos;
        
        v = new Vehicule((float)20);
        roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 0, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        vehicules.add(v);
        
        v = new Vehicule((float)20);
        roadPos = new RoadPosition(getRoadSectionById(1), 0, 1, 0, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        vehicules.add(v);
        
        v = new Vehicule((float)20);
        roadPos = new RoadPosition(getRoadSectionById(1), 1, 0, 0, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        vehicules.add(v);
        
        v = new Vehicule((float)20);
        roadPos = new RoadPosition(getRoadSectionById(1), 1, 1, 0, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        vehicules.add(v);
    }
 
    private void loadOSM() {
        Vehicule v;
        RoadPosition roadPos;
        
        for(Road r:roads){
            /*for(RoadSection roadSection:r.getRoadSections()){
                for(int i=0;i<5;i++){
                    v = new Vehicule((float)(40-Math.random()*30));
                    roadPos = new RoadPosition(roadSection, 0, 0, 1000*(10*i), 400);
                    v.setPosition(roadPos);
                    v.computeNextDestination();
                    vehicules.add(v);
                }
            }*/
            for (int i = 0; i < 5; i++) {
                if(r.getRoadSection(0).getNumberWays()>0
                        && r.getRoadSection(0).getRoadWay(0).getWidth()>=2000){
                    v = new Vehicule((float) (50 - Math.random() * 30));
                    roadPos = new RoadPosition(r.getRoadSection(0), 0, 0, 1000 * (10 * i), 400);
                    v.setPosition(roadPos);
                    v.computeNextDestination();
                    vehicules.add(v); 
                }

            }
        }
    }
    private void loadTestHighWay() {
        Vehicule v;
        RoadPosition roadPos;
        
        RoadSectionJunction j = new RoadSectionJunction();
        j.setFirstRoadSection(getRoadSectionById(1), false);
        j.setSecondRoadSection(getRoadSectionById(1), true);
        junctions.add(j);
        

        /*v = new Vehicule((float)1);
        roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 600000, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        vehicules.add(v);
        Behavior.vehiculeToFollow=v;*/
        
        
        for(int i=0;i<100;i++){
            v = new Vehicule((float)(40-(int)(Math.random()*10)));
            roadPos = new RoadPosition(getRoadSectionById(1), 1, 0, 1000*(700+20*i), 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            //v.setSpeed(20);
            vehicules.add(v);
            
            v = new Truck();
            roadPos = new RoadPosition(getRoadSectionById(1), 1, 0, 1000*(710+20*i), 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            //v.setSpeed(20);
            vehicules.add(v);
            
            /*v = new Vehicule((float)(40-Math.random()*30));
            roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 1000*(700+10*i), 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            v.setSpeed(20);
            if(v.getRoadWayDestination() != -1)
                vehicules.add(v);*/
            
            /*v = new Vehicule((float)(40-Math.random()*30));
            roadPos = new RoadPosition(getRoadSectionById(1), 4, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);*/
        }
    }
    
    public void loadTestTraficLights(){
        Vehicule v;
        RoadPosition roadPos;
        
        /*RoadSectionJunction j = new RoadSectionJunction();
        j.setFirstRoadSection(getRoadSectionById(1), true);
        j.setSecondRoadSection(getRoadSectionById(2), false);
        RoadSectionJunction j2 = new RoadSectionJunction();
        j2.setFirstRoadSection(getRoadSectionById(3), false);
        j2.setSecondRoadSection(getRoadSectionById(4), true);

        junctions.add(j);
        junctions.add(j2);*/


         for(int i=0;i<7;i++){
            v = new Vehicule(3000, 2000, 20-0*i, 4);
            roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
            
            
            v = new Vehicule(3000, 2000, 20-0*i, 4);
            roadPos = new RoadPosition(getRoadSectionById(2), 3, 0, 5000*i + 60000, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
            
            v = new Vehicule(3000, 2000, 20-0*i, 4);
            roadPos = new RoadPosition(getRoadSectionById(3), 3, 0, 5000*i + 60000, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
            
            v = new Vehicule(3000, 2000, 20-0*i, 4);
            roadPos = new RoadPosition(getRoadSectionById(4), 0, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
         }
         
        Truck t = new Truck();
        roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 5000*10, 400);
        t.setPosition(roadPos);
        t.computeNextDestination();
        vehicules.add(t);
    }
         
     public void loadTestRoadWays(){
        Vehicule v;
        RoadPosition roadPos;
        
        RoadSectionJunction j = new RoadSectionJunction();
        j.setFirstRoadSection(getRoadSectionById(1), true);
        j.setSecondRoadSection(getRoadSectionById(2), false);
 
        junctions.add(j);


         for(int i=0;i<10;i++){
            v = new Vehicule(3000, 2000, 20-0*i, 4);
            roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
            
            
            v = new Vehicule(3000, 2000, 20-0*i, 4);
            roadPos = new RoadPosition(getRoadSectionById(2), 2, 0, 5000*i + 60000, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
           
         }
         
       
    }
      
    public void loadRoadWayChange(){
        Vehicule v;
        RoadPosition roadPos;
        
        RoadSectionJunction j = new RoadSectionJunction();
        j.setFirstRoadSection(getRoadSectionById(2), false);
        j.setSecondRoadSection(getRoadSectionById(1), true);

        junctions.add(j);
        
        for(int i=0;i<300;i++){
            v = new Vehicule((float)(30-Math.random()*20));
            roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            v.setSpeed(0);
            vehicules.add(v);
            
            v = new Vehicule((float)(30-Math.random()*20));
            roadPos = new RoadPosition(getRoadSectionById(1), 1, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            v.setSpeed(0);
            vehicules.add(v);
        }
         
        
    }
    
    
    public void loadTestIntersections(){
        Vehicule v;
        RoadPosition roadPos;
        
        RoadSectionJunction j = new RoadSectionJunction();
        j.setFirstRoadSection(getRoadSectionById(3), false);
        j.setSecondRoadSection(getRoadSectionById(1), true);

        junctions.add(j);
        
        
        
        /*v = new Vehicule((float)20);
        roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 1000*50, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        vehicules.add(v);

        v = new Vehicule((float)20);
        roadPos = new RoadPosition(getRoadSectionById(2), 1, 0, 1000*40, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        vehicules.add(v);*/

         for(int i=0;i<15;i++){
            v = new Vehicule((float)20-0*i);
            roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
            
            /*v = new Vehicule((float)20-0*i);
            roadPos = new RoadPosition(getRoadSectionById(2), 1, 0, 80000-5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);*/
            
            v = new Motorbike();
            roadPos = new RoadPosition(getRoadSectionById(3),1, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
            
            
         }
         
         for(int i=0;i<5;i++){
             v = new Truck();
            roadPos = new RoadPosition(getRoadSectionById(2),1, 0, 15000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            vehicules.add(v);
         }
         
        /*v = new Vehicule(3000, 2000, 20, 4);
        roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 50000, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        vehicules.add(v);*/
                 
     }
    
    private void loadTestAccelerationLane() {
        Vehicule v;
        RoadPosition roadPos;

        RoadSectionJunction j = new RoadSectionJunction(getRoadSectionById(1), true,getRoadSectionById(5), false);
        junctions.add(j);

        v = new Vehicule((float)(30));
        roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 5000*0, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        v.setSpeed(0);
        vehicules.add(v);

        /*v = new Vehicule((float)(30-Math.random()*20));
        roadPos = new RoadPosition(getRoadSectionById(1), 1, 0, 5000*0, 400);
        v.setPosition(roadPos);
        v.computeNextDestination();
        v.setSpeed(0);
        vehicules.add(v);*/
        
        for(int i=0;i<5;i++){

            v = new Vehicule((float) (30));
            roadPos = new RoadPosition(getRoadSectionById(1), 0, 0, 5000 * i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            v.setSpeed(0);
            vehicules.add(v);
            
            v = new Vehicule((float)(30));
            roadPos = new RoadPosition(getRoadSectionById(3), 0, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            v.setSpeed(0);
            vehicules.add(v);

            /*v = new Vehicule((float)(30-Math.random()*20));
            roadPos = new RoadPosition(getRoadSectionById(4), 0, 0, 5000*i, 400);
            v.setPosition(roadPos);
            v.computeNextDestination();
            v.setSpeed(0);
            vehicules.add(v);*/
        }
    }

    public void sortVehicules(){
        for(Road road : this.getRoads()){
            for(RoadSection roadSection : road.getRoadSections()){
                roadSection.sortVehicules();           
            }
        }
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public Road getRoad(int index){
        return roads.get(index);
    }

    public int getNumberOfRoads(){
        return roads.size();
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public ArrayList<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(ArrayList<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }


    public void clearMap() {
        this.roads.clear();
        this.junctions.clear();
    }

    public void clearVehicules(){
        this.vehicules.clear();

        for (int i=0; i< this.getNumberOfRoads(); i++){
            for (int j=0; j<this.roads.get(i).getNumberOfSections(); j++){
                this.roads.get(i).getRoadSection(j).getVehicules().clear();
            }
        }
        
        for (Junction j:getJunctions()){
            j.getVehicules().clear();
            
        }
            
    }


    public int getNumberOfVehicules(){
        return vehicules.size();
    }

    public ArrayList<Junction> getJunctions() {
        return junctions;
    }
    

    public RoadSection getRoadSectionById(int id){
        for(Road road: roads){
            for(RoadSection roadSection: road.getRoadSections()){
                if(roadSection.getId() == id){
                    return roadSection;
                }
            }
        }
        return null;
    }

    public synchronized void eraseVehicule(Vehicule vehic){
        this.vehicules.remove(vehic);
        ((RoadPosition)vehic.getPosition()).getRoadSection().deleteVehicule(vehic);
    }

    /**
     * Get ALL the trafic lights of the globalmodel
     * @return ArrayList<TraficLights>
     */
    public ArrayList<TraficLight> getTraficLights() {
        return traficLights;
    }

    public Vehicule findVehiculeByCoordinates(Coordinates c) {
        Polygon p = new Polygon();
        for(Vehicule v:vehicules){
            p.reset();
            float angle = v.getPosition().getAngle();
            Coordinates fr = v.getPosition().getVehiculeCoordinates();
            Coordinates fl = Maths.findArrivalCoordinateFromVector(fr, angle + Maths.PI_2, v.getWidth());
            Coordinates br = Maths.findArrivalCoordinateFromVector(fr, angle, -v.getLength());
            Coordinates bl = Maths.findArrivalCoordinateFromVector(fl, angle, -v.getLength());
            
            p.addPoint(fr.getX(), fr.getY());
            p.addPoint(fl.getX(), fl.getY());
            p.addPoint(bl.getX(), bl.getY());
            p.addPoint(br.getX(), br.getY());
            
            if(p.contains(c.getX(),c.getY())){
                return v;
            }
        }
        
        return null;
    }
    
    public RoadSection findRoadSectionByCoordinates(Coordinates c) {
        Polygon p = new Polygon();
        for(Road r:roads){
        	for(RoadSection roadSection : r.getRoadSections()){
	            p.reset();
	            for(Coordinates coord: roadSection.getCoordinates()){
		         		            
		            p.addPoint(coord.getX(), coord.getY());          

	        	}
	            for(int i=roadSection.getCoordinates().size()-1; i>=0;i--){
	            	p.addPoint(roadSection.getOpposedCoordinates(i).getX(), roadSection.getOpposedCoordinates(i).getY());
	            }
	            if(p.contains(c.getX(),c.getY())){
	            	System.out.println("Route trouv�");
	            	return roadSection;
		            }
        	}
        }
        
        return null;
    }

    


    public void removeRoadSection(RoadSection roadSection){
        for(int i=0; i<roads.size(); i++){
            roads.get(i).getRoadSections().remove(roadSection);
        }
    }
    

    
    
}
