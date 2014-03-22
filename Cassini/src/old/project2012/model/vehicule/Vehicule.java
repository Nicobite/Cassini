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

package old.project2012.model.vehicule;

import java.util.ArrayList;
import old.project2012.model.Maths;
import old.project2012.model.junction.CrossRoad;
import old.project2012.model.road.RoadPosition;
import old.project2012.model.junction.IntersectionPosition;
import old.project2012.model.junction.Junction;
import old.project2012.model.junction.RoadSign;
import old.project2012.model.road.RoadSection;

/**
 *
 * @author jonathan
 */
public class Vehicule {

    private int length;// mm
    private int width;// mm
    private int height;// mm 
    private float maxSpeed;// m/s
    private float maxAcceleration;// m/s/s
    
    private float lateralSpeed;
    private float speed;// m/s
    private float acceleration;// m/s/s
    private VehiculePosition position;
    private Behavior behavior;
    
    private boolean broken = false;
    
    private boolean turnSignalLeft = false;
    private boolean turnSignalRight = false;
    private boolean brake = false;

    private boolean isArrived;
    
    private int phase;
    static public final int NORMAL_PHASE = 0;
    static public final int STOP_PHASE = 1;
    static public final int START_UP_PHASE = 2;
    static public final int SLOW_DOWN_PHASE = 3;

    private int roadSectionDestination;
    private int roadWayDestination;

    private static final int default_length = 3000;//mm
    private static final int default_width = 2000;//mm
    private static final float default_maxSpeed = 150/3.6f;// m/s = 150km/h
    private static final float default_maxAcceleration = 6f;// m/s/s

    private Driver driver;
    
    public Vehicule(int length, int width, float maxSpeed, float maxAcceleration){       
        isArrived = false;
        this.length = length;
        this.width = width;
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
        this.speed = 0;
        this.acceleration = 0;
        this.driver = new Driver();
        this.behavior = Behavior.getRandomBehavior();
    }
    
    public Vehicule(float maxSpeed){
        this(default_length, default_width, maxSpeed, default_maxAcceleration);
    }

    public Vehicule(){
        this(default_length, default_width, default_maxSpeed, default_maxAcceleration);
        
    }

    public VehiculePosition getPosition(){
        return position;
    }
    

    public void updatePosition(int dt){
        
        if(broken){
            //this.setPhase(STOP_PHASE);
        }
        else{
            this.setSpeed(this.speed+ this.acceleration * (float)dt / 1000f);
            int distance = (int)(this.speed * (float)dt);

            if(position instanceof RoadPosition){
                RoadPosition roadPos = (RoadPosition)this.position;
                
                //lateralOffset
                /*if(speed > 0.9){
                    roadPos.setLateralOffset(roadPos.getLateralOffset()+(int)(lateralSpeed*1000));
                }
                else{
                    roadPos.setLateralOffset(roadPos.getLateralOffset()+(int)((0.1+speed)*lateralSpeed*1000));
                }*/
                roadPos.setLateralOffset(roadPos.getLateralOffset()+(int)(lateralSpeed*1000));

                if(roadPos.getRoadSection().getRoadWay(roadPos.getWayNumber()).getDirection()){
                    
                    //Detection de changement de voie
                    if(roadPos.getLateralOffset()<0){
                        if(roadPos.getWayNumber()!=0){
                            roadPos.setWayNumber(roadPos.getWayNumber() - 1);
                            roadPos.setLateralOffset(roadPos.getRoadSection().getRoadWay(roadPos.getWayNumber()).getWidth() + roadPos.getLateralOffset());
                        }
                        
                    }else if(roadPos.getLateralOffset()>roadPos.getRoadSection().getRoadWay(roadPos.getWayNumber()).getWidth()){
                        if(roadPos.getWayNumber()!=roadPos.getRoadSection().getNumberWays()-1){
                            driver.setOvertake(false);
                            roadPos.setLateralOffset(roadPos.getLateralOffset() - roadPos.getRoadSection().getRoadWay(roadPos.getWayNumber()).getWidth());
                            roadPos.setWayNumber(roadPos.getWayNumber() + 1);
                        }
                    }
                    
                    
                    //Detection de fin d'un segement d'une roadSection
                    roadPos.setDistance(roadPos.getDistance()+distance);

                    float angle=roadPos.getRoadSection().getSegmentEndTurnAngle(roadPos.getSegmentNumber())-roadPos.getRoadSection().getSegmentAngle(roadPos.getSegmentNumber())-Maths.PI_2;

                    /*int totalWidth = -roadPos.getLateralOffset();
                    for(int i=0;i<roadPos.getWayNumber();i++){
                        totalWidth += roadPos.getRoadSection().getRoadWay(i).getWidth();
                    }

                    int dist= roadPos.getDistance() - roadPos.getRoadSection().getSegmentLength(roadPos.getSegmentNumber())-(int)(Math.tan(angle) * totalWidth);
                    if(dist>0){
                        if (roadPos.getSegmentNumber() == roadPos.getRoadSection().getNumberOfCoordinates()-2){
                            if(roadPos.getRoadSection().getEndJunction() == null){
                                isArrived = true;
                            }else{

                                roadPos.getRoadSection().getEndJunction().vehiculeEntry(this, getRoadSectionDestination(), roadWayDestination, dist, roadPos.getLateralOffset());
                                //roadPos.getRoadSection().getEndJunction().vehiculeEntry(this, getRoadSectionDestination(), roadPos.getWayNumber(), 0);

                            }

                        }
                        else{
                            roadPos.setDistance(roadPos.getDistance()-roadPos.getRoadSection().getSegmentLength(roadPos.getSegmentNumber())
                                                +(int)(2 * Math.tan(angle) * totalWidth));
                            roadPos.setSegmentNumber(roadPos.getSegmentNumber()+1);
                        }
                    }*/
                }
                else{
                    
                    //Detection de changement de voie
                    if(roadPos.getLateralOffset()<0){
                        if(roadPos.getWayNumber()!=roadPos.getRoadSection().getNumberWays()-1){
                            driver.setOvertake(false);
                            roadPos.setWayNumber(roadPos.getWayNumber()+1);
                            roadPos.setLateralOffset(roadPos.getRoadSection().getRoadWay(roadPos.getWayNumber()).getWidth()+roadPos.getLateralOffset());
                        }
                    }else if(roadPos.getLateralOffset()>roadPos.getRoadSection().getRoadWay(roadPos.getWayNumber()).getWidth()){
                        if(roadPos.getWayNumber()!=0){
                            roadPos.setLateralOffset(roadPos.getLateralOffset()-roadPos.getRoadSection().getRoadWay(roadPos.getWayNumber()).getWidth());
                            roadPos.setWayNumber(roadPos.getWayNumber()-1);
                        }
                    }
                    
                    
                    roadPos.setDistance(roadPos.getDistance()-distance);

                    /*float angle=roadPos.getRoadSection().getSegmentBeginTurnAngle(roadPos.getSegmentNumber())-roadPos.getRoadSection().getSegmentAngle(roadPos.getSegmentNumber())-Maths.PI_2;
                    int totalWidth = -roadPos.getLateralOffset();
                    for(int i=0;i<=roadPos.getWayNumber();i++){
                        totalWidth += roadPos.getRoadSection().getRoadWay(i).getWidth();
                    }

                    int dist = (int)(-Math.tan(angle) * totalWidth) - roadPos.getDistance();
                    //if(roadPos.getDistance() < -Math.tan(angle) * totalWidth){
                    if(dist>0){
                        if (roadPos.getSegmentNumber() == 0){
                            if(roadPos.getRoadSection().getBeginJunction() == null){
                                isArrived = true;
                            }else{
                                roadPos.getRoadSection().getBeginJunction().vehiculeEntry(this, getRoadSectionDestination(), roadWayDestination, dist, roadPos.getLateralOffset());

                            }
                        }
                        else{
                            roadPos.setSegmentNumber(roadPos.getSegmentNumber()-1);
                            roadPos.setDistance(roadPos.getRoadSection().getSegmentLength(roadPos.getSegmentNumber())+(int)(2 * Math.tan(angle) * totalWidth)
                                                +roadPos.getDistance());

                        }
                    }*/
                } 
                
                int totalWidth = -roadPos.getLateralOffset();
                for(int i=0;i<roadPos.getWayNumber();i++){
                    totalWidth += roadPos.getRoadSection().getRoadWay(i).getWidth();
                }
                
                float angle=roadPos.getRoadSection().getSegmentEndTurnAngle(roadPos.getSegmentNumber())-roadPos.getRoadSection().getSegmentAngle(roadPos.getSegmentNumber())-Maths.PI_2;
                int dist= roadPos.getDistance() - roadPos.getRoadSection().getSegmentLength(roadPos.getSegmentNumber())-(int)(Math.tan(angle) * totalWidth);
                
                if(dist>0){
                    if (roadPos.getSegmentNumber() == roadPos.getRoadSection().getNumberOfCoordinates()-2){
                        if(roadPos.getRoadSection().getEndJunction() == null){
                            isArrived = true;
                        }else{

                            roadPos.getRoadSection().getEndJunction().vehiculeEntry(this, getRoadSectionDestination(), roadWayDestination, dist, roadPos.getLateralOffset());
                            //roadPos.getRoadSection().getEndJunction().vehiculeEntry(this, getRoadSectionDestination(), roadPos.getWayNumber(), 0);

                        }

                    }
                    else{
                        roadPos.setDistance(roadPos.getDistance()-roadPos.getRoadSection().getSegmentLength(roadPos.getSegmentNumber())
                                            +(int)(2 * Math.tan(angle) * totalWidth));
                        roadPos.setSegmentNumber(roadPos.getSegmentNumber()+1);
                    }
                }
                
                angle=roadPos.getRoadSection().getSegmentBeginTurnAngle(roadPos.getSegmentNumber())-roadPos.getRoadSection().getSegmentAngle(roadPos.getSegmentNumber())-Maths.PI_2;
                dist = (int)(-Math.tan(angle) * totalWidth) - roadPos.getDistance();
                //if(roadPos.getDistance() < -Math.tan(angle) * totalWidth){
                if(dist>0){
                    if (roadPos.getSegmentNumber() == 0){
                        if(roadPos.getRoadSection().getBeginJunction() == null){
                            isArrived = true;
                        }else{
                            roadPos.getRoadSection().getBeginJunction().vehiculeEntry(this, getRoadSectionDestination(), roadWayDestination, dist, roadPos.getLateralOffset());

                        }
                    }
                    else{
                        roadPos.setSegmentNumber(roadPos.getSegmentNumber()-1);
                        roadPos.setDistance(roadPos.getRoadSection().getSegmentLength(roadPos.getSegmentNumber())+(int)(2 * Math.tan(angle) * totalWidth)
                                            +roadPos.getDistance());

                    }
                }
                

            }else if(position instanceof IntersectionPosition){
                IntersectionPosition intPos = (IntersectionPosition) position;

                //Move !
                intPos.setDistance(intPos.getDistance() + distance);

                //If at end of intersection
                if (intPos.getDistance() > intPos.distanceToTravel()){
                    intPos.getJunction().getRoadSection(intPos.getEndRoadSection()).vehiculeEntry(this, intPos.getEndWayNumber(), 0, intPos.getLateralOffset());
                }
            }
        }

    }

    
    public void setPosition(IntersectionPosition pos){
        position = pos;
    }

    public void setPosition(RoadPosition pos){
        position = pos;
        pos.getRoadSection().addVehicule(this);
    }
    
    public void changePosition(VehiculePosition position) {
        erasePosition();
        if(position instanceof RoadPosition){
            setPosition((RoadPosition) position);
        }
        else{
            setPosition((IntersectionPosition) position);
        }
    }
    
    public void erasePosition(){
        VehiculePosition pos = getPosition();
        if(pos instanceof RoadPosition){
            RoadPosition rpos = (RoadPosition)pos;
            rpos.getRoadSection().deleteVehicule(this);
        }else{
            ((IntersectionPosition)pos).getJunction().deleteVehicule(this);
        }
        
    }

    public int getLength(){
        return length;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public float getMaxSpeed(){
        return maxSpeed;
    }

    public float getMaxAcceleration(){
        return maxAcceleration;
    }

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(float speed){
        if(speed>this.maxSpeed){
            this.speed = this.maxSpeed;
        }
        else if(speed<0){
            this.speed=0;
        }
        else{
            this.speed = speed;
        }
            
    }
 
      
    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
        if(acceleration>this.maxAcceleration){
            this.acceleration = this.maxAcceleration;
        }
        else if(acceleration< -this.maxAcceleration){
            this.acceleration = -this.maxAcceleration;
        }
        
    }

    public boolean isArrived(){
        return isArrived;
    }


   public int getRelativeRoadWay(RoadSection roadSection, int wayNumber){
        if (roadSection.getRoadWay(wayNumber).getDirection()){
            return wayNumber;
        }
        else{
            return roadSection.getNumberWays()-1-wayNumber;
        }
    }
    
    public int getRegularRoadWay(RoadSection roadSection, boolean beginOfIntersection, int relativeWayNumber){
        if(beginOfIntersection){
            return relativeWayNumber;
        }
        else{
            return roadSection.getNumberWays()-1-relativeWayNumber;
        }
    }
    
   

    
    
    public void computeNextDestination(){
        RoadPosition roadPosition = (RoadPosition)this.position;
        Junction junction;
        int currentRoadSectionIndice;


        //Get Next Junction
        if ( roadPosition.getRoadSection().getRoadWay(roadPosition.getWayNumber()).getDirection() ){
            junction = roadPosition.getRoadSection().getEndJunction();
        }
        else{
            junction = roadPosition.getRoadSection().getBeginJunction();
        }
        
        
        if(junction != null && this.position instanceof RoadPosition){
            ArrayList<Integer> routesPossibles = new ArrayList<Integer>();
            int numWay = roadPosition.getWayNumber();
            
            if (roadPosition.getRoadSection().getRoadWay(roadPosition.getWayNumber()).getDirection()) {
                currentRoadSectionIndice = junction.getIndexRoadSection(roadPosition.getRoadSection(), true);
            } else {
                currentRoadSectionIndice = junction.getIndexRoadSection(roadPosition.getRoadSection(), false);
            }

     
            RoadSection roadSectionPotentielle;
            boolean voiePossible;
            int j ;
            int i = 0;
            while(i<junction.getRoadSections().size())
            {
                //Si on est sur la voie d'insertion, on enlève les RoadSection de "gauche"
                if(junction instanceof CrossRoad){
                    if (((CrossRoad)junction).getRoadSign(currentRoadSectionIndice).getSign() == RoadSign.Sign.ACCELERATION_LANE && i == 0)
                    {
                        i++;
                    }
                }
                    roadSectionPotentielle = junction.getRoadSection(i);
                    //Demi tours interdits
                    if(i != junction.getIndexRoadSection(roadPosition.getRoadSection(),roadPosition.getRoadSection().getRoadWay(numWay).getDirection())){
                        j=0;
                        voiePossible = false;
                        while(j < roadSectionPotentielle.getRoadWays().size() && !voiePossible)
                        {
                                //Si la voie parcourue est dans le même sens que le sens de circulation de la voiture étudiée avant l'intersection
                                if(sameDirection(i, j))
                                {
                                    routesPossibles.add(i);
                                    voiePossible = true;
                                }
                            j++;
                        }
                    }
                i++;
            }
            
            if(!routesPossibles.isEmpty()){
                roadSectionDestination = routesPossibles.get((int)(Math.random() * (routesPossibles.size())));
            }else{
                System.out.println(this+" : J'ai pas trouve de voies possible");
                roadSectionDestination =  junction.getIndexRoadSection(roadPosition.getRoadSection(),roadPosition.getRoadSection().getRoadWay(numWay).getDirection());
            }
            
            
            
        
            if (junction.getBeginOfRoadSection(roadSectionDestination)){
                roadWayDestination = 0;
            }
            else{
                roadWayDestination = junction.getRoadSection(roadSectionDestination).getNumberWays()-1;
            }
        }
        else{
            roadSectionDestination = -1;
        }
        

    }

    public int getRoadSectionDestination() {
        return roadSectionDestination;
    }
    
    public int getRoadWayDestination() {
        return roadWayDestination;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public boolean isTurnSignalLeft() {
        return turnSignalLeft;
    }

    public void setTurnSignalLeft(boolean turnSignalLeft) {
        this.turnSignalLeft = turnSignalLeft;
    }

    public boolean isTurnSignalRight() {
        return turnSignalRight;
    }

    public void setTurnSignalRight(boolean turnSignalRight) {
        this.turnSignalRight = turnSignalRight;
    }

    public boolean isBrake() {
        return brake;
    }

    public void setBrake(boolean brake) {
        this.brake = brake;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    
    public boolean sameDirection(int roadSectionArrival,int wayArrival){
       Junction junction;
        boolean RSsame=false;
        boolean RWsame = false;
        
        if (this.position instanceof RoadPosition){
            RoadPosition roadPosition = (RoadPosition)this.position;
            boolean currentDirection = roadPosition.getRoadSection().getRoadWay(roadPosition.getWayNumber()).getDirection();
            if ( currentDirection ){
                junction = roadPosition.getRoadSection().getEndJunction();
            }
            else{
                junction = roadPosition.getRoadSection().getBeginJunction();
            }

            if (junction!= null){
                int currentRoadSection = junction.getRoadSections().indexOf(roadPosition.getRoadSection());
                
                if (junction.getBeginOfRoadSection(roadSectionArrival) == junction.getBeginOfRoadSection(currentRoadSection) ){
                    RSsame=true;
                }

                if( (RSsame && currentDirection == junction.getRoadSection(roadSectionArrival).getRoadWay(wayArrival).getDirection()) ){
                    RWsame = true;
                }
                else if (!RSsame && !(currentDirection == junction.getRoadSection(roadSectionArrival).getRoadWay(wayArrival).getDirection())){
                    RWsame = true;
                }

            }

            return !RWsame;
        }
        else 
            return false;
    }
    
    public void updateRoadWayDestination() {
        
        RoadPosition roadPosition = (RoadPosition)this.position;
        Junction junction;
        
        if ( roadPosition.getRoadSection().getRoadWay(roadPosition.getWayNumber()).getDirection() ){
            junction = roadPosition.getRoadSection().getEndJunction();
        }
        else{
            junction = roadPosition.getRoadSection().getBeginJunction();
        }

        if(junction != null){
            int currentRoadWay = roadPosition.getWayNumber();
            RoadSection rsDestination = junction.getRoadSection(roadSectionDestination);
            int wayD = getRelativeRoadWay(roadPosition.getRoadSection(), currentRoadWay);
            int wayA = getRegularRoadWay(rsDestination, junction.getBeginOfRoadSection(roadSectionDestination), wayD);
            
            while (wayA >= rsDestination.getNumberWays()) {
                wayA = getRegularRoadWay(rsDestination, junction.getBeginOfRoadSection(roadSectionDestination), --wayD);
            }
            


            while (wayD>=0 && ! (sameDirection(roadSectionDestination,wayA))) {
                wayA = getRegularRoadWay(rsDestination, junction.getBeginOfRoadSection(roadSectionDestination), --wayD);
            }
            
            //if(wayD>=0)
                roadWayDestination = wayA;
            //else
                //roadWayDestination = 0;    
        }
                  
    }

    /**
     * @return the driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public float getLateralSpeed() {
        return lateralSpeed;
    }

    public void setLateralSpeed(float lateralSpeed) {
        this.lateralSpeed = lateralSpeed;
    }
    

}

