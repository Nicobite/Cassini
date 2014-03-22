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
package old.project2012.model.road;

import old.project2012.model.vehicule.VehiculeEnvironment;
import old.project2012.model.vehicule.VehiculePosition;
import old.project2012.model.vehicule.Behavior;
import old.project2012.model.vehicule.Vehicule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import old.project2012.model.Coordinates;
import old.project2012.model.Maths;
import old.project2012.model.junction.Junction;


/**
 *
 * @author gabriel
 */
public class RoadSection {
    
    private static int newId=0;
    
    private int id;
    private ArrayList<Coordinates> coordinates;
    private ArrayList<RoadWay> roadWays;
    private ArrayList<Vehicule> vehicules;
    
    private Junction beginJunction;
    private Junction endJunction;

    //Create a comparator to sort vehicules in the ArrayList
    private static final Comparator<Vehicule> vehiculeComparator= new Comparator<Vehicule>() {
        @Override
        public int compare(Vehicule v1, Vehicule v2){
            return RoadPositionComparator.compare((RoadPosition)v1.getPosition(),(RoadPosition)v2.getPosition());
        }
    };
            
    private static final Comparator<RoadPosition> RoadPositionComparator= new Comparator<RoadPosition>() {
        @Override
        public int compare(RoadPosition rp1, RoadPosition rp2){
            if(rp1.getSegmentNumber() < rp2.getSegmentNumber()){
                return -1;
            }
            else if(rp1.getSegmentNumber() == rp2.getSegmentNumber()){
                if(rp1.getDistance() < rp2.getDistance()){
                    return -1;
                }
                else if(rp1.getDistance() == rp2.getDistance()){
                    if(rp1.getWayNumber() < rp2.getWayNumber()){
                        return -1;
                    }
                    else if(rp1.getWayNumber() == rp2.getWayNumber()){
                        return 0;
                    }
                    else{
                        return 1;
                    }

                }
                else{
                    return 1;
                }
            }
            else{
                return 1;
            }
            
        }
    };
    
    public static int getNewId(){
        return newId++;
    }
    
    public RoadSection(){
        this.coordinates= new ArrayList<Coordinates>();
        this.roadWays=new ArrayList<RoadWay>();
        this.vehicules = new ArrayList<Vehicule>();
        setId(newId);
    } 
    
    public void setCoordinates(ArrayList<Coordinates> coordinates){
        this.coordinates=coordinates;
    }

    public void addCoordinate(Coordinates coords){
        this.coordinates.add(coords);
    }
    
    public ArrayList<Coordinates> getCoordinates(){
        return this.coordinates;
    }

    public Coordinates getCoordinate(int index){
        return this.coordinates.get(index);
    }
    
    public void setRoadWays(ArrayList<RoadWay> roadWays){
        this.roadWays = roadWays;
    }
    
    public void addRoadWay(RoadWay roadWay){
        this.roadWays.add(roadWay);
    }

    public ArrayList<RoadWay> getRoadWays(){
        return this.roadWays;
    }

    public RoadWay getRoadWay(int index){
        return this.roadWays.get(index);
    }

    public int getNumberWays(){
        return roadWays.size();
    }

    public int getNumberOfCoordinates(){
        return coordinates.size();
    }

    public ArrayList<Vehicule> getVehicules(){
        return vehicules;
    }

    public int getNumberOfVehicules(){
        return vehicules.size();
    }

    public void addVehicule(Vehicule vehicule){
        this.vehicules.add(vehicule);
    }

    public void deleteVehicule(Vehicule vehicule){
        this.vehicules.remove(vehicule);
    }

    public void newVehicule(Vehicule vehicule, boolean direction, int numWay, int distance, int lateralOffset){
        int newNumSegment, newDistance, newNumWay;
        
        if(direction == true){
            newNumSegment= 0;
            newNumWay = numWay;
            newDistance = distance;
        }else{
            newNumSegment = getNumberOfCoordinates()-2;
            newNumWay = getNumberWays()- 1 - numWay;
            newDistance = getSegmentLength(newNumSegment)-distance;
        }
        
        RoadPosition rpos = new RoadPosition(this, newNumWay, newNumSegment, newDistance, lateralOffset);
        vehicule.changePosition((VehiculePosition)rpos);
    }
    
    
    
    public void sortVehicules(){
        Collections.sort(vehicules, vehiculeComparator);    
    }
    
    //Work only if the vehicules are sorted
    public int findVehiculeIndex(Vehicule v){
        return Collections.binarySearch(vehicules, v, vehiculeComparator);
    }

    //Get the total width of the roadSection
    public int getTotalWidth(){
        int totalWidth = 0;
        for(RoadWay roadWay : this.roadWays){
            totalWidth += roadWay.getWidth();
        }
        
        return totalWidth;
    }
    
    public int getSegmentLength(int numSegment){
        return Maths.distance(coordinates.get(numSegment), coordinates.get(numSegment+1));
    }
    
    public float getSegmentAngle(int numSegment){
        return (float)Maths.angle(coordinates.get(numSegment), coordinates.get(numSegment+1));
    }
    
    //Get the Angle to place the intersection point of two segments
    public float getSegmentBeginTurnAngle(int numSegment){
        if(numSegment==0){
            return this.getSegmentAngle(numSegment)+Maths.PI_2;
        }
        else{
            return this.getSegmentAngle(numSegment-1)/2 + this.getSegmentAngle(numSegment)/2 + Maths.PI_2;    
        } 
    }
    
    //Get the Ratio to place the intersection point of two segments
    public float getSegmentBeginTurnRatio(int numSegment){
        if(numSegment==0){
            return 1;
        }
        else{
            return 1/((float) Math.cos(this.getSegmentAngle(numSegment-1)/2 - this.getSegmentAngle(numSegment)/2));
        } 
    }
    
    //Get the Angle to place the intersection point of two segments
    public float getSegmentEndTurnAngle(int numSegment){
        if(numSegment == this.getCoordinates().size()-2){
            return this.getSegmentAngle(numSegment)+Maths.PI_2;
        }
        else{
            return this.getSegmentAngle(numSegment)/2 + this.getSegmentAngle(numSegment+1)/2 + Maths.PI_2;        
        } 
    }
    
    //Get the Ratio to place the intersection point of two segments
    public float getSegmentEndTurnRatio(int numSegment){
        if(numSegment == this.getCoordinates().size()-2){
            return 1;
        }
        else{
            return 1/((float) Math.cos(this.getSegmentAngle(numSegment)/2 - this.getSegmentAngle(numSegment+1)/2));       
        } 
    }

    public Junction getBeginJunction() {
        return beginJunction;
    }

    public void setBeginJunction(Junction beginJunction) {
        this.beginJunction = beginJunction;
    }

    public Junction getEndJunction() {
        return endJunction;
    }

    public void setEndJunction(Junction endJunction) {
        this.endJunction = endJunction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        newId=Math.max(id,newId);
        newId++;
    }
    
    public int distanceOfTheEnd(RoadPosition rPos){
        if(this.getRoadWay(rPos.getWayNumber()).getDirection()==true){
            return distanceBetweenVehicules(rPos, this.getEndRoadPosition(rPos.getWayNumber()));
        }else{
            return distanceBetweenVehicules(this.getBeginRoadPosition(rPos.getWayNumber()), rPos);
        }
    }
    
    public int distanceBetweenVehicules(RoadPosition rp1, RoadPosition rp2){
        

      if(RoadPositionComparator.compare(rp1, rp2) > 0){
            RoadPosition rp =rp2;
            rp2 = rp1;
            rp1 = rp;
        }


        //Distance of the first car in its segment
        int distance= 0;
                
        if(rp1.getSegmentNumber() == rp2.getSegmentNumber()){
            distance = rp2.getDistance()-rp1.getDistance();
        }else{
        
            distance = this.getSegmentLength(rp1.getSegmentNumber())-rp1.getDistance();
            
            int totalWidth=0;
            
            for (int i = 0; i < rp1.getWayNumber(); i++) {
                totalWidth += rp1.getRoadSection().getRoadWay(i).getWidth();
            }
            if(rp1.getRoadSection().getRoadWay(rp1.getWayNumber()).getDirection()){
                totalWidth += rp1.getLateralOffset();
            }else{
                totalWidth += rp1.getRoadSection().getRoadWay(rp1.getWayNumber()).getWidth() - rp1.getLateralOffset();
            }
            
             /*if(rp1.getRoadSection().getRoadWay(rp1.getWayNumber()).getDirection()){
                  totalWidth = -rp1.getLateralOffset();
                for(int i=0;i<rp1.getWayNumber();i++){
                    totalWidth += rp1.getRoadSection().getRoadWay(i).getWidth();
                }
            }
            else{
                totalWidth = +rp1.getLateralOffset();
                for(int i=0;i<rp1.getWayNumber()-1;i++){
                    totalWidth += rp1.getRoadSection().getRoadWay(i).getWidth();
                }
             }*/
            
        
            //Distance of segments between the two cars
            for (int segment = rp1.getSegmentNumber()+1; segment< rp2.getSegmentNumber(); segment++ ){
                //Distance difference of the Turn
                float angle=this.getSegmentEndTurnAngle(segment-1)-this.getSegmentAngle(segment-1)-Maths.PI_2;
                distance -= (int)(2 * Math.tan(angle) * totalWidth);
                //Distance of segments between the two cars
                distance += this.getSegmentLength(segment);
                
            }
            float angle=this.getSegmentEndTurnAngle(rp2.getSegmentNumber()-1)-this.getSegmentAngle(rp2.getSegmentNumber()-1)-Maths.PI_2;
            distance -= (int)(2 * Math.tan(angle) * totalWidth);

            //Distance of the second car in its segment
            distance += rp2.getDistance();
        }
        
        if(distance<0){
            return -distance;
        }else{
            return distance;
        }
        
        
    }

    public void vehiculeEntry(Vehicule v, int wayNumber,int distance, int lateralOffset){
        if(getRoadWay(wayNumber).getDirection()==true){
            v.changePosition(new RoadPosition(this, wayNumber, 0, distance, lateralOffset));
        }else{
            
            RoadPosition rPos=getEndRoadPosition(wayNumber);
            rPos.setDistance(rPos.getDistance());
            rPos.setLateralOffset(lateralOffset);
            v.changePosition(rPos);
        }
        
        v.computeNextDestination();
    }

    public void fillFrontEnvironment(VehiculeEnvironment environment, int wayNumber, int distanceToRoadSection, int nbVehiculeToFind){
        fillFrontEnvironment(environment, -1, wayNumber, distanceToRoadSection, nbVehiculeToFind);
    }
    
    public void fillFrontEnvironment(VehiculeEnvironment environment , int vehiculeIndex, int wayNumber, int distanceTraveled, int nbVehiculeToFind){
        Vehicule vehicule = null; 
        RoadPosition vehiculePos = null;
               
        if(vehiculeIndex>= 0){
            vehicule = vehicules.get(vehiculeIndex);
            vehiculePos = (RoadPosition)vehicule.getPosition(); 
        }else{
            if(roadWays.get(wayNumber).getDirection() == true){
                vehiculePos = getBeginRoadPosition(wayNumber);
                vehiculeIndex=-1;
            }else{
                vehiculePos = getEndRoadPosition(wayNumber);
                vehiculeIndex = getVehicules().size();
            }
            
        }
        
        if(roadWays.get(wayNumber).getDirection() == true){
            for(int i=vehiculeIndex+1;i<getNumberOfVehicules() && nbVehiculeToFind>0;i++){
                Vehicule v= getVehicules().get(i);
                RoadPosition vPos = (RoadPosition) v.getPosition();
                int distance = distanceBetweenVehicules(vehiculePos, vPos);
                if(distance+distanceTraveled > Behavior.fieldOfVision){
                    //We see beyond our field of vision, so we stop to browse vehicule
                    break;
                }
                else{
                    if(environment.getVehiculeFront()==null && vPos.getWayNumber() == wayNumber){
                        environment.setVehiculeFront(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                        if(v.getWidth()+ vPos.getLateralOffset() > roadWays.get(wayNumber).getWidth()){
                            environment.setVehiculeFrontLeft(v, distance+distanceTraveled);
                            nbVehiculeToFind--;
                        }
                            
                    }else if(environment.getVehiculeFrontLeft()==null && vPos.getWayNumber() == wayNumber+1 
                                && this.getRoadWay(wayNumber+1).getDirection()==true){
                        environment.setVehiculeFrontLeft(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                    }else if(environment.getVehiculeFrontRight()==null && vPos.getWayNumber() == wayNumber-1){
                        environment.setVehiculeFrontRight(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                        if(v.getWidth()+ vPos.getLateralOffset() > roadWays.get(wayNumber-1).getWidth()){
                            environment.setVehiculeFront(v, distance+distanceTraveled);
                            nbVehiculeToFind--;
                        }
                    }
                }
            }
            if(nbVehiculeToFind>= 0){
                int distEnd =  distanceTraveled+distanceBetweenVehicules(vehiculePos, getEndRoadPosition(wayNumber));
                if(getEndJunction() != null && distEnd < Behavior.fieldOfVision){
                    int indexRoadSection = getEndJunction().getIndexRoadSection(this, roadWays.get(wayNumber).getDirection());
                    if(indexRoadSection != -1 && vehicule!= null){
                        getEndJunction().fillFrontEnvironment(environment, indexRoadSection, wayNumber, vehicule.getRoadSectionDestination(), vehicule.getRoadWayDestination(), distEnd, nbVehiculeToFind);
                    }
                    
                }
            }
            
        }else{
            for(int i=vehiculeIndex-1;i>=0 && nbVehiculeToFind >0 ;i--){
                Vehicule v= getVehicules().get(i);
                RoadPosition vPos = (RoadPosition) v.getPosition();
                int distance = distanceBetweenVehicules(vPos, vehiculePos);
                if(distance > Behavior.fieldOfVision){
                    //We see beyond our field of vision, so we stop to browse vehicule
                    break;
                }
                else{
                    if(environment.getVehiculeFront()==null && vPos.getWayNumber() == wayNumber){
                        environment.setVehiculeFront(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                        if(v.getWidth()+ vPos.getLateralOffset() > roadWays.get(wayNumber).getWidth()){
                            environment.setVehiculeFrontLeft(v, distance+distanceTraveled);
                            nbVehiculeToFind--;
                        }
                    }else if(environment.getVehiculeFrontLeft()==null && vPos.getWayNumber() == wayNumber-1
                            && this.getRoadWay(wayNumber-1).getDirection()==false){
                        environment.setVehiculeFrontLeft(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                    }else if(environment.getVehiculeFrontRight()==null && vPos.getWayNumber() == wayNumber+1){
                        environment.setVehiculeFrontRight(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                        if(v.getWidth()+ vPos.getLateralOffset() > roadWays.get(wayNumber+1).getWidth()){
                            environment.setVehiculeFront(v, distance+distanceTraveled);
                            nbVehiculeToFind--;
                        }
                    }
                }
            }
            
            if(nbVehiculeToFind>= 0){
                int distEnd =  distanceTraveled+distanceBetweenVehicules(vehiculePos, getBeginRoadPosition(wayNumber));
                if(getBeginJunction() != null && distEnd < Behavior.fieldOfVision){
                    int indexRoadSection = getBeginJunction().getIndexRoadSection(this, roadWays.get(wayNumber).getDirection());
                    if(indexRoadSection != -1 && vehicule != null){
                        getBeginJunction().fillFrontEnvironment(environment, indexRoadSection, wayNumber, vehicule.getRoadSectionDestination(), vehicule.getRoadWayDestination(), distEnd, nbVehiculeToFind);
                    }
                    
                }
            }
        }
        
        
    }
    
    public void fillBackEnvironment(VehiculeEnvironment environment, int wayNumber, int distanceToRoadSection, int nbVehiculeToFind){
        fillBackEnvironment(environment, -1, wayNumber, distanceToRoadSection, nbVehiculeToFind);
    }
    
    public void fillBackEnvironment(VehiculeEnvironment environment , int vehiculeIndex, int wayNumber, int distanceTraveled, int nbVehiculeToFind){
        Vehicule vehicule = null; 
        RoadPosition vehiculePos = null;
        
        if(vehiculeIndex>= 0){
            vehicule = vehicules.get(vehiculeIndex);
            vehiculePos = (RoadPosition)vehicule.getPosition(); 
        }else{
            if(wayNumber<roadWays.size()
                    &&roadWays.get(wayNumber).getDirection() == true){
                vehiculePos = getEndRoadPosition(wayNumber);
                vehiculeIndex = getVehicules().size();
            }else{
                vehiculePos = getBeginRoadPosition(wayNumber);
            }
        }
        
        if(wayNumber<roadWays.size()
                    && roadWays.get(wayNumber).getDirection() == true){
            for(int i=vehiculeIndex-1;i>=0 && nbVehiculeToFind>0;i--){
                Vehicule v= getVehicules().get(i);
                RoadPosition vPos = (RoadPosition) v.getPosition();
                int distance = distanceBetweenVehicules(vPos, vehiculePos);
                if(distance+distanceTraveled > Behavior.fieldOfVision){
                    //We see beyond our field of vision, so we stop to browse vehicule
                    break;
                }
                else{
                    if(environment.getVehiculeBack()==null && vPos.getWayNumber() == wayNumber){
                        environment.setVehiculeBack(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                        if(v.getWidth()+ vPos.getLateralOffset() > roadWays.get(wayNumber).getWidth()){
                            environment.setVehiculeBackLeft(v, distance+distanceTraveled);
                            nbVehiculeToFind--;
                        }
                    }else if(environment.getVehiculeBackLeft()==null && vPos.getWayNumber() == wayNumber+1
                            && this.getRoadWay(wayNumber+1).getDirection()==true){
                        environment.setVehiculeBackLeft(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                    }else if(environment.getVehiculeBackRight()==null && vPos.getWayNumber() == wayNumber-1){
                        environment.setVehiculeBackRight(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                        if(v.getWidth()+ vPos.getLateralOffset() > roadWays.get(wayNumber-1).getWidth()){
                            environment.setVehiculeBack(v, distance+distanceTraveled);
                            nbVehiculeToFind--;
                        }
                    }
                }
            }
            if(nbVehiculeToFind>= 0){
                int distEnd =  distanceTraveled+distanceBetweenVehicules(vehiculePos, getBeginRoadPosition(wayNumber));
                if(getBeginJunction() != null && distEnd < Behavior.fieldOfVision){
                    int indexRoadSection = getBeginJunction().getIndexRoadSection(this, !roadWays.get(wayNumber).getDirection());
                    if(indexRoadSection != -1 && vehicule!= null){
                        getBeginJunction().fillBackEnvironment(environment, indexRoadSection, wayNumber, distEnd, nbVehiculeToFind);
                    }
                    
                }
            }
            
            
        }else{
            for(int i=vehiculeIndex+1;i<getNumberOfVehicules() && nbVehiculeToFind>0;i++){
                Vehicule v= getVehicules().get(i);
                RoadPosition vPos = (RoadPosition) v.getPosition();
                int distance = distanceBetweenVehicules(vehiculePos,vPos);
                if(distance > Behavior.fieldOfVision){
                    //We see beyond our field of vision, so we stop to browse vehicule
                    break;
                }
                else{
                    if(environment.getVehiculeBack()==null && vPos.getWayNumber() == wayNumber){
                        environment.setVehiculeBack(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                        if(v.getWidth()+ vPos.getLateralOffset() > roadWays.get(wayNumber).getWidth()){
                            environment.setVehiculeBackLeft(v, distance+distanceTraveled);
                            nbVehiculeToFind--;
                        }
                    }else if(environment.getVehiculeBackLeft()==null && vPos.getWayNumber() == wayNumber-1
                            && this.getRoadWay(wayNumber-1).getDirection()==false){
                        environment.setVehiculeBackLeft(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                    }else if(environment.getVehiculeBackRight()==null && vPos.getWayNumber() == wayNumber+1){
                        environment.setVehiculeBackRight(v, distance+distanceTraveled);
                        nbVehiculeToFind--;
                        if(v.getWidth()+ vPos.getLateralOffset() > roadWays.get(wayNumber+1).getWidth()){
                            environment.setVehiculeBack(v, distance+distanceTraveled);
                            nbVehiculeToFind--;
                        }
                    }
                }
            }
            if(nbVehiculeToFind>= 0){
                int distEnd =  distanceTraveled+distanceBetweenVehicules(vehiculePos, getEndRoadPosition(wayNumber));
                if(getEndJunction() != null && distEnd < Behavior.fieldOfVision){
                    int indexRoadSection = getEndJunction().getIndexRoadSection(this, !roadWays.get(wayNumber).getDirection());
                    if(indexRoadSection != -1 && vehicule != null){
                        getEndJunction().fillBackEnvironment(environment, indexRoadSection, wayNumber, distEnd, nbVehiculeToFind);
                    }
                    
                }
            }
        }
    }
    
    

    private RoadPosition getBeginRoadPosition(int wayNumber) {
        return new RoadPosition(this, wayNumber, 0, 0, 0);
    }
    
    private RoadPosition getEndRoadPosition(int wayNumber) {
        return new RoadPosition(this, wayNumber, getNumberOfCoordinates()-2, getSegmentLength(getNumberOfCoordinates()-2), 0);
    }

    public Coordinates getOpposedCoordinates(int index){
        float angle;
        float ratio;
        
        if(index<coordinates.size()-1){
            angle=getSegmentBeginTurnAngle(index);
            ratio=getSegmentBeginTurnRatio(index);
        }else{
            angle=getSegmentEndTurnAngle(index-1);
            ratio=getSegmentEndTurnRatio(index-1);
        }
        
        return Maths.findArrivalCoordinateFromVector(coordinates.get(index), angle, (int)(getTotalWidth()*ratio));
    }
    public Coordinates getOpposedBeginCoordinates()
    {
        return (Maths.findArrivalCoordinateFromVector(this.getCoordinate(0),getSegmentAngle(0)+Maths.PI_2,this.getTotalWidth()));
    }
    public Coordinates getBeginMiddleCoordinates()
    {
        int widthHalfRoad = 0;
        int numWay =0;
        while(numWay<this.getNumberWays() && this.getRoadWay(numWay).getDirection())
        {
            widthHalfRoad += this.getRoadWay(numWay).getWidth();
            numWay++;
        }
        return (Maths.findArrivalCoordinateFromVector(this.getCoordinate(0),getSegmentAngle(0)+Maths.PI_2,widthHalfRoad));             
    }
    public Coordinates getEndCoordinates()
    {
        return(this.getCoordinate(this.getCoordinates().size()-1));
    }
    
    
    public Coordinates getOpposedEndCoordinates()
    {
        return(Maths.findArrivalCoordinateFromVector(this.getEndCoordinates(),getSegmentAngle(this.getCoordinates().size()-2)+Maths.PI_2,this.getTotalWidth()));
    }
    public Coordinates getEndMiddleCoordinates()
    {
        int widthHalfRoad = 0;
        int numWay = 0;
        while(numWay < this.getNumberWays() && this.getRoadWay(numWay).getDirection())
        { 
            widthHalfRoad += this.getRoadWay(numWay).getWidth();
            numWay++;
        }
        return (Maths.findArrivalCoordinateFromVector(this.getEndCoordinates(),getSegmentAngle(this.getCoordinates().size()-2)+Maths.PI_2,widthHalfRoad));             
    }

    public Vehicule getLastVehicule(boolean direction) {
        if(direction == true){
            for(int i=vehicules.size()-1; i>=0 ;i--){
                RoadPosition vPos = (RoadPosition)vehicules.get(i).getPosition();
                if(this.getRoadWay(vPos.getWayNumber()).getDirection() == direction){
                    return vehicules.get(i);
                }
            }
        }else{
            for(int i=0; i<vehicules.size() ;i++){
                RoadPosition vPos = (RoadPosition)vehicules.get(i).getPosition();
                if(this.getRoadWay(vPos.getWayNumber()).getDirection() == direction){
                    return vehicules.get(i);
                }
            }
            
        }
        
        return null;
    }

    public Vehicule getLastVehiculeOfWay(int numWay) {
        boolean direction = getRoadWay(numWay).getDirection();
        if(direction == true){
            for(int i=vehicules.size()-1; i>=0 ;i--){
                RoadPosition vPos = (RoadPosition)vehicules.get(i).getPosition();
                if(vPos.getWayNumber() == numWay){
                    return vehicules.get(i);
                }
            }
        }else{
            for(int i=0; i<vehicules.size() ;i++){
                RoadPosition vPos = (RoadPosition)vehicules.get(i).getPosition();
                if(vPos.getWayNumber() == numWay){
                    return vehicules.get(i);
                }
            }

        }

        return null;
    }
    
    public boolean haveAnotherRoadWayOnHisRight(int waynumber){
        if (roadWays.get(waynumber).getDirection() == true) {
            if (waynumber > 0) {
                return true;
            }else{
                return false;
            }
        }else{
            if (waynumber < roadWays.size() - 1) {
                return true;
            } else {
                return false;
            }
        }     
    }
    
    public boolean haveAnotherRoadWayOnHisLeft(int waynumber){
        if (roadWays.get(waynumber).getDirection() == true) {
            if(waynumber+1<roadWays.size()
                && roadWays.get(waynumber+1).getDirection() == roadWays.get(waynumber).getDirection()){
                return true;
            }else{
                return false;
            }
        }else{
            if(waynumber >= 1
                 && roadWays.get(waynumber-1).getDirection() == roadWays.get(waynumber).getDirection()){
                return true;
            } else {
                return false;
            }
        }   
        
        
    }
    
    public void reduceExtremity(boolean isBegin, int distance){
        float angle;

        if(isBegin == true){
            
            if(Maths.distance(coordinates.get(0),coordinates.get(1))<=distance){
                if(coordinates.size()>2){
                    coordinates.remove(0);
                    reduceExtremity(isBegin,distance-Maths.distance(coordinates.get(0),coordinates.get(1)));
                }
                else{
                    reduceExtremity(isBegin,Maths.distance(coordinates.get(0),coordinates.get(1))-500);
                }
                
            }else{
                angle = Maths.angle(coordinates.get(0), coordinates.get(1));
                coordinates.set(0, Maths.findArrivalCoordinateFromVector(coordinates.get(0), angle, distance));
            }
            
        }else{
            if(Maths.distance(coordinates.get(coordinates.size()-1),coordinates.get(coordinates.size()-2))<=distance){
                if(coordinates.size()>2){
                    coordinates.remove(coordinates.size() - 1);
                    reduceExtremity(isBegin,distance-Maths.distance(coordinates.get(coordinates.size() - 1),coordinates.get(coordinates.size() - 2)));
                }
                else{
                    reduceExtremity(isBegin,Maths.distance(coordinates.get(coordinates.size() - 1),coordinates.get(coordinates.size() - 2))-500);
                }
                
            }else{
                angle = Maths.angle(coordinates.get(coordinates.size() - 1), coordinates.get(coordinates.size() - 2));
                coordinates.set(coordinates.size() - 1, Maths.findArrivalCoordinateFromVector(coordinates.get(coordinates.size() - 1), angle, distance));
            }
       }
        
    }

    public void centralToNormalCoordinates() {
        
        int dist = getTotalWidth()/2;
        float angle, ratio;
        
        for(int i=0;i<getNumberOfCoordinates()-1;i++){
            angle = -getSegmentBeginTurnAngle(i);
            ratio = getSegmentBeginTurnRatio(i);

            coordinates.set(i,Maths.findArrivalCoordinateFromVector(coordinates.get(i), angle, (int)(dist*ratio)));
        }
        
        angle = -getSegmentEndTurnAngle(getNumberOfCoordinates()-2);
        ratio = getSegmentEndTurnRatio(getNumberOfCoordinates()-2);
        coordinates.set(getNumberOfCoordinates()-1,Maths.findArrivalCoordinateFromVector(coordinates.get(getNumberOfCoordinates()-1), angle, (int)(dist*ratio)));
        
    }
    

    public int getMostLeftRoadWay(boolean direction){
        int roadWay;
        if (direction){
            roadWay = 0;
            while (haveAnotherRoadWayOnHisLeft(roadWay)){
                roadWay++;
            }
        }
        else{
            roadWay = this.roadWays.size()-1;
            while (haveAnotherRoadWayOnHisLeft(roadWay)){
                roadWay--;
            }
        }
        
        return roadWay;
    }

    public int getMostRightRoadWay(boolean direction){
        if (direction)
            return 0;
        else
            return this.roadWays.size()-1;
    }
    
    public int getNbWayByDirection(boolean direction){
    	int cpt=0;
    	for (int i = 0; i < this.roadWays.size(); i++) {
			if(roadWays.get(i).getDirection() == direction)
				cpt++;
		}
    	return cpt;
    }
    
}
