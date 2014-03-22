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

import old.project2012.model.junction.DecelerationLane;
import old.project2012.model.junction.AccelerationLane;
import old.project2012.model.junction.CrossRoad;
import old.project2012.model.junction.RoadSign;
import old.project2012.model.junction.RoadWayChange;
import old.project2012.model.junction.IntersectionPosition;
import old.project2012.model.junction.TraficLight;
import java.awt.Color;
import java.util.ArrayList;
import old.project2012.model.Maths;
import old.project2012.model.road.RoadPosition;
import old.project2012.model.road.RoadSection;
import old.project2012.view.InfoView;

/**
 *
 * @author gabriel
 */
public class Behavior {
    public static Vehicule vehiculeToFollow = null;
    public static final int fieldOfVision = 1000000;// mm
    public int safetyDistanceTime = 1500;//ms
    public int dangerDistanceTime = (int)(safetyDistanceTime*0.75); //ms
    public float softAcceleration = 2f;//m/s/s
    public float strongAcceleration = 4f;
    
    public static Behavior StandardBehavior = null;
    public static Behavior NervousBehavior = null;
    public static Behavior CoolBehavior = null;
    public static Behavior GrannyBehavior = null;
    

    public float annoyanceSensibility = 0.01f; // delta_annoyance/s
    public float speedLimitRespect = 1; // Factor of respectfullness of the speed limit

    private java.awt.Color behaviorColor = Color.BLUE;
    
    public static float lateralSpeed = 0.02f;// m/s 
    
    //public int safetyDistanceTime;
    //public int dangerDistanceTime;

    static Behavior getStandardBehavior() {
        if(StandardBehavior == null){
            StandardBehavior = new Behavior();

        }
        return StandardBehavior;
    }
    
    static Behavior getNervousBehavior() {
        if(NervousBehavior == null){
            NervousBehavior = new Behavior(0.05f , 4f, 6f, 1000, 1.2f, Color.RED);
        }
        return NervousBehavior;
    }
    
    static Behavior getCoolBehavior() {
        if(CoolBehavior == null){
            CoolBehavior = new Behavior(0.00f , 2f, 4f, 1500, 1, new Color(50,200,50));
        }
        return CoolBehavior;
    }
    
    static Behavior getGrannyBehavior() {
        if(GrannyBehavior == null){
            GrannyBehavior = new Behavior(0.005f , 0.5f, 3f, 800, 0.8f, Color.DARK_GRAY);
        }
        return GrannyBehavior;
    }
    
    
    public Behavior(){
        
    }

    public Behavior( float annoyanceSensibility, float softAcceleration, float strongAcceleration, int safetyDistanceTime, float speedLimitRespect, Color color){
        this.annoyanceSensibility = annoyanceSensibility;
        this.softAcceleration = softAcceleration;
        this.strongAcceleration = strongAcceleration;
        this.safetyDistanceTime = safetyDistanceTime;
        this.dangerDistanceTime = (int)(safetyDistanceTime*0.75);
        this.speedLimitRespect = speedLimitRespect;
        this.behaviorColor = color;
    }
    
    public void updateBehaviorParameters(Vehicule vehicule, VehiculeEnvironment environment, int dt){

        if(environment.getDistNextJunction() < 20000
                && vehicule.getSpeed()<0.5){
                vehicule.getDriver().increaseAnnoyance(annoyanceSensibility, dt);
        }
        else{
            vehicule.getDriver().decreaseAnnoyance(annoyanceSensibility, dt);
        }
    }

    
    public float securityDistance(float v1, float v2){
        //System.out.println(safetyDistanceTime*v2/1000f + Math.max((v1-v2)/(softAcceleration) * (Math.abs(v1-v2)/2f),0));
        return safetyDistanceTime*v2/1000f + Math.max((v1-v2)/(softAcceleration) * (Math.abs(v1-v2)/2f),0);
    }
    
    /**
     * Returns the distance needed to stop the car in case of an emergency brake
     * @param v Vehicule concerned
     * @return braking distance (mm)
     */
    public float brakingDistance (Vehicule v){
        // DF = -v0²/(2*a) (with a<0 'cause deceleration)
        return (float) (1000*v.getSpeed()*v.getSpeed()/(2*v.getMaxAcceleration()));
    }
    
    public int estimateTravelTime(Vehicule v, int distance, float speedLimit){
        float timeToReachSpeedLimit, timeAcceleration;
        speedLimit = Math.max(v.getMaxSpeed(), speedLimit);
        timeToReachSpeedLimit = (speedLimit-v.getSpeed())/softAcceleration;
        timeAcceleration = Maths.plusSolutionOfQuadraticFormula(softAcceleration, v.getSpeed(), -(float)distance/1000);
        
        if(timeAcceleration<=timeToReachSpeedLimit){
            return (int)(1000f*timeAcceleration);
        }else{
            int distToReach = distance - (int)(1000f*(timeAcceleration*((softAcceleration*timeAcceleration)+v.getSpeed())));
            return (int)(1000f*(timeAcceleration + distToReach/speedLimit));
        }
        
    }
    
    public boolean vehiculeBotherMeOnTheNextCrossRoad(Vehicule vehicule, VehiculeEnvironment environment){
        
        CrossRoad c = (CrossRoad)environment.getNextJunction();
        RoadSign roadSign = c.getRoadSign(environment.getIndexForNextJunction());
        
        RoadPosition roadPos = (RoadPosition) vehicule.getPosition();
        float speedLimit = (0.27f) * roadPos.getRoadSection().getRoadWay(roadPos.getWayNumber()).getSpeedLimit();
        
        IntersectionPosition IntPos = new IntersectionPosition(environment.getNextJunction(), environment.getIndexForNextJunction(), roadPos.getWayNumber(), vehicule.getRoadSectionDestination(), vehicule.getRoadWayDestination(), 0, 0);
        int distToTheEndOfTheIntersection = environment.getDistNextJunction() + Maths.distance(IntPos.getDepartureCoordinates(), IntPos.getArrivalCoordinates());
        int vehiculeArrivaTime = (int) estimateTravelTime(vehicule, environment.getDistNextJunction(), speedLimit);//ms
        int vehiculeThroughTime = (int) estimateTravelTime(vehicule, distToTheEndOfTheIntersection + vehicule.getLength(), speedLimit);//ms
        
        //Pour chaque route differente de la mienne de l'intersection
        for (int i = 0; i < c.getNumberRoadSections(); i++) {
            if (i != environment.getIndexForNextJunction()) {
                Vehicule v = c.getRoadSection(i).getLastVehicule(!c.getBeginOfRoadSection(i));
                if (v != null) {
                    RoadPosition vPos = (RoadPosition) v.getPosition();
                    int vArrivalTime = estimateTravelTime(v, vPos.getRoadSection().distanceOfTheEnd(vPos), speedLimit);
                    int vThroughtTime = vArrivalTime + 3000 + v.getLength();
                    
                    //Prise en compte de l'enervement
                    vArrivalTime = (int)(vArrivalTime * (1f+vehicule.getDriver().getAnnoyance()));
                    vThroughtTime = (int)(vThroughtTime / (1f+vehicule.getDriver().getAnnoyance()));
                    
                    
                    if(RoadSign.higherPriority(roadSign, c.getRoadSign(i)) == 1){
                        
                        //Si un véhicule arrive sur une voie plus prioritaire que moi
                        
                        //Si le temps de traverser l'intersection est trop proche du temps de la voiture qui nous gêne, on s'arrête
                        //if(vehiculeToFollow == vehicule)
                        //    System.out.println(vArrivalTime +" < "+vehiculeThroughTime+" < "+vThroughtTime);
                        if(!(vehiculeThroughTime < vArrivalTime
                                || vThroughtTime < vehiculeArrivaTime)){
                            return true;
                        }

                    }else if(RoadSign.higherPriority(roadSign, c.getRoadSign(i))==0){
                        if (Maths.betweenModulo(i, vehicule.getRoadSectionDestination(), environment.getIndexForNextJunction(), c.getNumberRoadSections())) {
                            //Si un véhicule arrive sur une voie aussi prioritaire que moi
                            //Et que je traverse cette route      
                            //Et que le temps que je traverse l'intersection est plus grande que le temps d'arrivé du véhicule
                            if(!(vehiculeThroughTime < vArrivalTime
                                || vThroughtTime < vehiculeArrivaTime)){
                                //Et que l'on est pas interbloqué
                                if(vehicule.getSpeed()<1 && v.getSpeed()<1){
                                    //On est interbloqué
                                    if(vPos.getRoadSection().distanceOfTheEnd(vPos) < environment.getDistNextJunction())
                                        return true;
                                }else{
                                    //Je m'arrete
                                    return true;
                                }
                            }
                        }  
                    }                  
                }
            }
        }
        

        //On vérifie que personne nous gène dans l'intersection
        for(Vehicule v:environment.getNextJunction().getVehicules()){
            IntersectionPosition vPos = (IntersectionPosition) v.getPosition();
            //S'il vient de la même voie que nous on s'en tappe, il est déja dans vehiculeFront
            if(vPos.getStartRoadSection() != environment.getIndexForNextJunction()){
                
                if(Maths.betweenModulo(environment.getIndexForNextJunction(), vPos.getEndRoadSection(), vPos.getStartRoadSection(), vPos.getJunction().getNumberRoadSections())
                        || Maths.betweenModulo(vPos.getStartRoadSection(), vehicule.getRoadSectionDestination(), environment.getIndexForNextJunction(), vPos.getJunction().getNumberRoadSections())){
                        int exitTime = estimateTravelTime(v, vPos.distanceToTravel()+v.getLength()-vPos.getDistance(),v.getMaxSpeed());
                        //Je sais pas qui avait mis cette ligne, mais je comprend pas son intêret
                        //if(vehicule.getRoadSectionDestination()==vPos.getEndRoadSection() && vehicule.getRoadWayDestination()==vPos.getEndWayNumber()){
                            if(vehiculeArrivaTime < exitTime){
                                return true;
                            }
                        //}
                }
            }
        }
                    
        
        return false;
    }
    
    
    public void makeDecision(Vehicule vehicule, int dt){
        
        VehiculeEnvironment environment= new VehiculeEnvironment();
        float acceleration = softAcceleration;
        
        boolean cutInPossibility = false;
        boolean overtakePossibility = false;
        
        boolean overtake = false;
        boolean cutIn= false; // Se rabattre
        boolean brake = false;
        boolean emergencyBrake = false;
        boolean accelerate = false;
        boolean isInALWay = false;
        int desiredWay=0; // 0 tout droit, 1 gauche, 2 droite
        
        vehicule.setTurnSignalLeft(false);
        vehicule.setTurnSignalRight(false);
        
        float speedLimit = 100;
        
        if(vehicule.getPosition() instanceof RoadPosition){
                
            RoadPosition roadPos = (RoadPosition) vehicule.getPosition();
            RoadSection roadSection = roadPos.getRoadSection();
            int vehiculeIndex = roadSection.findVehiculeIndex(vehicule);
              
            int nbVehiculeToFind = 1;
            
            speedLimit = (0.27f)*roadSection.getRoadWay(roadPos.getWayNumber()).getSpeedLimit();

            //Préparation avant de remplir l'environnement, 
            //Savoir s'il est possible de doubler ou de ce rabattre
            //Pour connaitre le nombre de véhicules à trouver
            
            if(roadSection.haveAnotherRoadWayOnHisRight(roadPos.getWayNumber())){
                nbVehiculeToFind++;
                cutInPossibility = true;
            }
            if(roadSection.haveAnotherRoadWayOnHisLeft(roadPos.getWayNumber())){
                nbVehiculeToFind++;
                overtakePossibility = true;
            }
            
            //On remande de remplir l'environment en consequence
            roadSection.fillFrontEnvironment(environment, vehiculeIndex, roadPos.getWayNumber(), 0, nbVehiculeToFind);
            roadSection.fillBackEnvironment(environment, vehiculeIndex, roadPos.getWayNumber(), 0, nbVehiculeToFind);   
        }else{
            //Remplissage de l'environnement dans le cas ou on est dans une intersection
            IntersectionPosition vehiculePos = (IntersectionPosition)vehicule.getPosition();
            int vehiculeIndex = vehiculePos.getJunction().findVehiculeIndex(vehicule);
            vehiculePos.getJunction().fillFrontEnvironment(environment, vehiculeIndex, vehiculePos.getStartRoadSection(),vehiculePos.getStartWayNumber(),vehiculePos.getEndRoadSection(), vehiculePos.getEndWayNumber(),-vehiculePos.getDistance(),3);
        }

        //updateBehaviorParameters(vehicule, environment, dt);
            
        
        //DEBUT DE LA PRISE DE DECISION
        
        //VehiculesConerned contiendra les vehicules concernés pour le suivi de véhicule
        ArrayList<Vehicule> vehiculesConcerned = new ArrayList<Vehicule>();
        ArrayList<Integer> distVehiculesConcerned = new ArrayList<Integer>();
        ArrayList<Boolean> isVehiculesVeryConcerned = new ArrayList<Boolean>();
        
        
        
        
        //COMPORTEMENT A L'APPROCHE D'UNE JUNCTION
        if (environment.getNextJunction() != null){
           
           
           if(environment.getNextJunction() instanceof AccelerationLane){
               AccelerationLane al = (AccelerationLane) environment.getNextJunction();
               RoadSign roadSign = al.getRoadSign(environment.getIndexForNextJunction());
               
               if (roadSign.getSign() == RoadSign.Sign.ACCELERATION_LANE) {

                   
                   int way = 0;
                   if (al.getBeginOfBeginRoadSection() == true) {
                       way = al.getBeginRoadSection().getNumberWays() - 1;
                   }

                   int vehiculeArrivalTime = estimateTravelTime(vehicule, environment.getDistNextJunction(), speedLimit);

                   Vehicule v = al.getBeginRoadSection().getLastVehiculeOfWay(way);
                   if (v != null) {
                       RoadPosition vPos = (RoadPosition) v.getPosition();
                       int vArrivalTime = estimateTravelTime(v, vPos.getRoadSection().distanceOfTheEnd(vPos), speedLimit);

                       if (Math.abs(vehiculeArrivalTime - vArrivalTime) < 3000) {
                           vehicule.setPhase(Vehicule.STOP_PHASE);
                       } else {
                           vehicule.setPhase(Vehicule.NORMAL_PHASE);
                       }

                   } else {
                       vehicule.setPhase(Vehicule.NORMAL_PHASE);
                   }
                }
               if(environment.getIndexForNextJunction() == 0
                       && environment.getDistNextJunction()<300000){
                   if(vehicule.getPosition() instanceof RoadPosition){
                       RoadPosition roadPos = (RoadPosition) vehicule.getPosition();    

                       if((al.getBeginOfBeginRoadSection()==true && roadPos.getWayNumber() == al.getRoadSection(0).getNumberWays()-1)
                               || (al.getBeginOfBeginRoadSection()==false && roadPos.getWayNumber() == 0 )){
                           overtake=true;
                           vehicule.setTurnSignalLeft(true);
                       }
                       if((al.getBeginOfBeginRoadSection()==true && roadPos.getWayNumber() == al.getRoadSection(0).getNumberWays()-2)
                               || (al.getBeginOfBeginRoadSection()==false && roadPos.getWayNumber() == 1 )){
                           cutInPossibility=false;
                           
                       }
                   }
                       
               }
               
               
           }
           //DANS LE CAS D'UNE INTERSECTION
           else if(environment.getNextJunction() instanceof CrossRoad){
               CrossRoad c = (CrossRoad)environment.getNextJunction();
               RoadSign roadSign = c.getRoadSign(environment.getIndexForNextJunction());
               
               int wayNumber = ((RoadPosition) vehicule.getPosition()).getWayNumber();
               IntersectionPosition IntPos = new IntersectionPosition(environment.getNextJunction(), environment.getIndexForNextJunction(), wayNumber, vehicule.getRoadSectionDestination(), vehicule.getRoadWayDestination(), 0, 0);

               //DANS LE CAS D'UNE VOIE PRIORITAIRE, PRIORITE A DROITE OU CEDEZ LE PASSAGE
               if (roadSign.getSign() == RoadSign.Sign.NONE || roadSign.getSign() == RoadSign.Sign.GIVE_WAY
                   ){//|| roadSign.getSign()==RoadSign.Sign.ACCELERATION_LANE){
                   vehicule.setPhase(Vehicule.NORMAL_PHASE); 
                   if(environment.getDistNextJunction()/1000f < 0.5+securityDistance(vehicule.getSpeed(),0)){ 
                       if(vehiculeBotherMeOnTheNextCrossRoad(vehicule, environment)){
                           vehicule.setPhase(Vehicule.STOP_PHASE);
                       }
                   }   
                }
               /*else if(roadSign.getSign() == RoadSign.Sign.ACCELERATION_LANE){    
                   
                   AccelerationLane al= (AccelerationLane)environment.getNextJunction();
                   int way = 0;
                   if(al.getBeginOfBeginRoadSection()==true){
                       way=al.getBeginRoadSection().getNumberWays()-1;
                   }
                   
                   int vehiculeArrivalTime = estimateTravelTime(vehicule, environment.getDistNextJunction(), speedLimit);
                   
                   Vehicule v = al.getBeginRoadSection().getLastVehiculeOfWay(way);
                   if(v != null){
                       RoadPosition vPos = (RoadPosition) v.getPosition();
                       int vArrivalTime = estimateTravelTime(v, vPos.getRoadSection().distanceOfTheEnd(vPos), speedLimit);

                       if (Math.abs(vehiculeArrivalTime - vArrivalTime) < 3000) {
                           vehicule.setPhase(Vehicule.STOP_PHASE);
                       } else {
                           vehicule.setPhase(Vehicule.NORMAL_PHASE);
                       }
                   
                   }else{
                       vehicule.setPhase(Vehicule.NORMAL_PHASE);
                   }
                   
                   //if(vArrivalTime +3000 > vehiculeArrivalTime)
                   //int vehiculeArrivaTime = (int) estimateTravelTime(vehicule, environment.getDistNextJunction(), speedLimit);//ms
                   //int vehiculeThroughTime = (int) estimateTravelTime(vehicule, distToTheEndOfTheIntersection + vehicule.getLength(), speedLimit);//ms
                   
                   //Si la voie d'insertion est proche
                   /*if(environment.getDistNextJunction()/1000f < 1.0+securityDistance(vehicule.getSpeed(),0)){
                       
                       IntersectionPosition vehiculeIntPos = new IntersectionPosition(c, 2, 0, 1, vehicule.getRoadWayDestination(), 0, 0);
                       int distanceToExit = environment.getDistNextJunction() + vehiculeIntPos.distanceToTravel();
                       //Véhicule sur la voie d'entrée
                       Vehicule v = c.getRoadSection(0).getLastVehicule(!c.getBeginOfRoadSection(0));
                       //S'il y a au moins un véhicule
                       if(v != null){
                            RoadPosition vPos = (RoadPosition) v.getPosition();
                           IntersectionPosition vIntPos = new IntersectionPosition(c, 0, vPos.getWayNumber(), 1, v.getRegularRoadWay(c.getRoadSection(1), c.getBeginOfRoadSection(1), vPos.getWayNumber()), 0, 0);
                           int distanceOtherToExit = vPos.getRoadSection().distanceOfTheEnd(vPos) + vIntPos.distanceToTravel();
                           //temps de traversée pour nous
                           int travelTime = estimateTravelTime(vehicule, distanceToExit, speedLimit);
                           //temps de traversée pour l'autre véhicule
                           int otherTravelTime = estimateTravelTime(v, distanceOtherToExit, vPos.getRoadSection().getRoadWay(vPos.getWayNumber()).getSpeedLimit());

                           int differenceTime = Math.abs(otherTravelTime - travelTime);//ms
                            int secureDistance;//m
                            int secureTime;//ms
                            //Si on met moins de temps pour traverser que l'autre
                            if (travelTime < otherTravelTime) {
                               secureDistance = (int)((vehicule.getSpeed()*3.6*5.0)/9.0);//km
                               secureTime = (int)((vehicule.getSpeed()*1000.0) /secureDistance);
                            }
                            else{
                                secureDistance = (int)((v.getSpeed()*3.6*5.0)/9.0);
                                secureTime = (int)((v.getSpeed()*1000.0)/secureDistance);
                            }
                            //Si on met plus de temps à traverser mais qu'on est trop proche du temps de traversée de l'autre voiture, on freine
                            
                            if(differenceTime < secureTime && (travelTime > otherTravelTime || environment.getDistNextJunction()/1000f < securityDistance(vehicule.getSpeed(),0))){
                                //Je m'arrete
                                vehicule.setPhase(Vehicule.SLOW_DOWN_PHASE);
                            }
                            else{
                                vehicule.setPhase(Vehicule.START_UP_PHASE);
                                acceleration = strongAcceleration;
                                
                            }         

                       }
                       //S'il n'y a personne sur la voie d'entrée, on peut accélérer
                       else{
                          vehicule.setPhase(Vehicule.START_UP_PHASE);
                       }
                    } 
               }*/
                //DANS LE CAS DU STOP
                else if (roadSign.getSign() == RoadSign.Sign.STOP){
                   
                   if(vehicule.getPhase() == Vehicule.STOP_PHASE){
                       if(vehicule.getSpeed() < 0.1f){
                           int distToTheEndOfTheIntersection = environment.getDistNextJunction()+Maths.distance(IntPos.getDepartureCoordinates(), IntPos.getArrivalCoordinates());
                           int throughTime = (int)estimateTravelTime(vehicule, distToTheEndOfTheIntersection, speedLimit);//ms
                           
                           vehicule.setPhase(Vehicule.START_UP_PHASE); 
                           //Chercher si on peut passer
                           if (vehiculeBotherMeOnTheNextCrossRoad(vehicule, environment)) {
                               vehicule.setPhase(Vehicule.STOP_PHASE);
                           }
                        }
                   }
                   else if(vehicule.getPhase() != Vehicule.START_UP_PHASE){
                       if(environment.getDistNextJunction()/1000f < securityDistance(vehicule.getSpeed(),0))
                        {
                            vehicule.setPhase(Vehicule.STOP_PHASE);
                        }
                   }
                } //Fin gestion du STOP
               
               //FEUX TRICOLORES
                else if (roadSign.getSign() == RoadSign.Sign.TRAFIC_LIGHT){
                    TraficLight tl = (TraficLight) c.getRoadSign(environment.getIndexForNextJunction());
                    //Green Light
                    if (tl.getState()== TraficLight.Ligths.GREEN){
                       if(vehiculeBotherMeOnTheNextCrossRoad(vehicule, environment)){
                           vehicule.setPhase(Vehicule.STOP_PHASE);
                       }
                       else{
                            if ( environment.getDistNextJunction() < brakingDistance(vehicule)*3)
                                vehicule.setPhase(Vehicule.SLOW_DOWN_PHASE); //Slow down when you come closer to the green light to anticipate
                            else
                                vehicule.setPhase(Vehicule.NORMAL_PHASE);
                       }
                    }
                    //Orange Light
                    else if (tl.getState() == (TraficLight.Ligths.ORANGE)){
                        if(vehiculeBotherMeOnTheNextCrossRoad(vehicule, environment)){
                           vehicule.setPhase(Vehicule.STOP_PHASE);
                       }
                        else{
                            if (environment.getDistNextJunction() < brakingDistance(vehicule)) {
                                vehicule.setPhase(Vehicule.NORMAL_PHASE); //If no time to brake, pass through orange light
                            } else {
                                vehicule.setPhase(Vehicule.STOP_PHASE);
                            }
                        }
                    }   
                    //Red Light
                    else if (tl.getState() == (TraficLight.Ligths.RED)){
                       vehicule.setPhase(Vehicule.STOP_PHASE);
                        
                    }
               } //Fin gestion feux
               
               
               //Changement de phase START_UP_PHASE à NORMAL_PHASE si on dépasse une certaine vitesse
               if (vehicule.getPhase() == Vehicule.START_UP_PHASE) {
                   if (vehicule.getSpeed() > 0.5f) {
                       vehicule.setPhase(Vehicule.NORMAL_PHASE);
                   }
               }
           
               
               
             //Clignotant
             if(environment.getNextJunction() instanceof DecelerationLane
                        && environment.getDistNextJunction()<500000
                        && ((DecelerationLane)environment.getNextJunction()).getRoadSign(vehicule.getRoadSectionDestination()).getSign() == RoadSign.Sign.DECELERATION_LANE){
                    vehicule.setTurnSignalRight(true);
                    desiredWay=2;
                    //System.out.println("Patate");
                }
             else if(environment.getNextJunction() instanceof AccelerationLane){
                 AccelerationLane ac = (AccelerationLane)environment.getNextJunction();
                 if(ac.getRoadSign(environment.getIndexForNextJunction()).getSign()==RoadSign.Sign.ACCELERATION_LANE){
                     vehicule.setTurnSignalLeft(true);
                     desiredWay=0;
                 }else{
                     //vehicule.setTurnSignalLeft(true);
                     //desiredWay=1;
                     //overtake=true;
                     
                 }
                 
             }
             else if(environment.getDistNextJunction()<100000){
                
                
                if(vehicule.getPosition() instanceof RoadPosition){
                    int roadSection = c.getRoadSections().indexOf(((RoadPosition)(vehicule.getPosition())).getRoadSection());
                    float angleDirection = c.getAngleNextDestination(roadSection, vehicule.getRoadSectionDestination());
                    if( (angleDirection > Maths.PI/6f && angleDirection < Maths.PI)){
                        vehicule.setTurnSignalLeft(true);
                        desiredWay=1;
                    }
                    else if( angleDirection > Maths.PI && angleDirection < Math.PI*11f/6f ){
                        vehicule.setTurnSignalRight(true);
                        desiredWay=2;
                    }
                    else
                        desiredWay=0;
                }else{
                    desiredWay=0;
                }
             }else{
                 desiredWay=0;
             }
             
             //Reduction de voies
             if(cutInPossibility 
                     && environment.getNextJunction() != null
                     && environment.getNextJunction() instanceof RoadWayChange
                     && environment.getDistNextJunction()<100000//securityDistance(vehicule.getSpeed(),0)
                     && !c.getRoadSection(vehicule.getRoadSectionDestination()).haveAnotherRoadWayOnHisRight(vehicule.getRoadWayDestination())){

                 if(environment.getVehiculeFrontRight() != null
                         && environment.getDistVehiculeFrontRight() < environment.getDistNextJunction()){
                         vehiculesConcerned.add(environment.getVehiculeFrontRight());
                         distVehiculesConcerned.add(environment.getDistVehiculeFrontRight());
                         isVehiculesVeryConcerned.add(false);                       
                 }
              }
             if(overtakePossibility 
                     && environment.getNextJunction() != null
                     && environment.getNextJunction() instanceof RoadWayChange
                     && environment.getDistNextJunction()<100000//securityDistance(vehicule.getSpeed(),0)
                     && !c.getRoadSection(vehicule.getRoadSectionDestination()).haveAnotherRoadWayOnHisLeft(vehicule.getRoadWayDestination())){
                     
                 if(environment.getVehiculeFrontLeft() != null){
                     vehiculesConcerned.add(environment.getVehiculeFrontLeft());
                     distVehiculesConcerned.add(environment.getDistVehiculeFrontLeft());
                     isVehiculesVeryConcerned.add(false);
                 }
              }
           }
        }
        
        

        //Prise de décision dans le cas ou il est possible de doubler
        if(overtakePossibility){
            if (desiredWay==1){
                overtake=true;
            }

            if (environment.getVehiculeFront() != null) {
                if(vehicule.getDriver().isOvertake()){
                    overtake = true;
                }else{
                    float v1 = vehicule.getSpeed();
                    float v2 = environment.getVehiculeFront().getSpeed();
                    if (environment.getDistVehiculeFront() / 1000f < environment.getVehiculeFront().getLength() / 1000f + 1 + securityDistance(v1, v2)) {
                        overtake = true;
                        vehicule.setTurnSignalLeft(true);

                    }
                }
                
            }
            
            
            if(environment.getVehiculeFrontRight() != null){
                if(environment.getVehiculeFrontRight().isTurnSignalLeft()){
                    overtake = true;
                    vehicule.setTurnSignalLeft(true);
                }
            }

            if(overtake == true){
                if(environment.getVehiculeFrontLeft() != null){
                    float v1=vehicule.getSpeed();
                    float v2=environment.getVehiculeFrontLeft().getSpeed();
                    if(environment.getDistVehiculeFrontLeft()/1000f <= environment.getVehiculeFrontLeft().getLength()/1000f + 1 +securityDistance(v1,v2)){
                        overtake = false;
                    }
                }
                if(environment.getVehiculeBackLeft() != null){
                    float v1=environment.getVehiculeBackLeft().getSpeed();
                    float v2=vehicule.getSpeed();
                    if(environment.getDistVehiculeBackLeft()/1000f <= vehicule.getLength()/1000f + 1 + securityDistance(v1,v2)){
                        overtake = false;
                    }
                }
            }
            
            if (desiredWay==2){// && environment.getNextJunction()!=null && environment.getDistNextJunction()/1000f < 75){
                overtake=false;
                vehicule.setTurnSignalLeft(false);
            }
            /*if (desiredWay == 0){// && environment.getNextJunction()!=null && environment.getDistNextJunction()/1000f < 75){
                overtake=false;
                vehicule.setTurnSignalLeft(false);
            }*/
        }
        
        //Prise de décision dans le cas ou il est possible de se rabbatre
        if (cutInPossibility) {
            cutIn = true;
            
            if (desiredWay ==1){
                cutIn=false;
            }
            
            if (environment.getVehiculeFrontRight() != null) {
                float v1 = vehicule.getSpeed();
                float v2 = environment.getVehiculeFrontRight().getSpeed();
                //System.out.println("fr = "+distVehiculeFrontRight/1000f);
                if (environment.getDistVehiculeFrontRight() / 1000f <= environment.getVehiculeFrontRight().getLength() / 1000f + 1 +securityDistance(v1, v2)) {
                    cutIn = false;
                }
            }
            if (environment.getVehiculeBackRight() != null) {
                float v1 = environment.getVehiculeBackRight().getSpeed();
                float v2 = vehicule.getSpeed();
                //System.out.println("br = "+distVehiculeBackRight/1000f);
                if (environment.getDistVehiculeBackRight() / 1000f <= vehicule.getLength() / 1000f + 1 + securityDistance(v1, v2)) {
                    cutIn = false;
                }
            }
        }


        //Remplissage de VehiculeConcerned en fonction des choix pris
        /*if (cutIn && environment.getVehiculeFrontRight() != null) {
            vehiculesConcerned.add(environment.getVehiculeFrontRight());
            distVehiculesConcerned.add(environment.getDistVehiculeFrontRight());
            isVehiculesVeryConcerned.add(true);
        } else if (overtake && environment.getVehiculeFrontLeft() != null) {
            vehiculesConcerned.add(environment.getVehiculeFrontLeft());
            distVehiculesConcerned.add(environment.getDistVehiculeFrontLeft());
            isVehiculesVeryConcerned.add(true);
        } else if (environment.getVehiculeFront() != null) {
            vehiculesConcerned.add(environment.getVehiculeFront());
            distVehiculesConcerned.add(environment.getDistVehiculeFront());
            isVehiculesVeryConcerned.add(true);
        }*/
        if (environment.getVehiculeFront() != null) {
            vehiculesConcerned.add(environment.getVehiculeFront());
            distVehiculesConcerned.add(environment.getDistVehiculeFront());
            isVehiculesVeryConcerned.add(true);
            /*if(overtake){
                isVehiculesVeryConcerned.add(false);
            }else{
                isVehiculesVeryConcerned.add(true);
            }*/
        }
        
        
        
        
        
        //PRISE DU CHOIX DE LA VITESSE VOULUE
        //Speed adjustment
        float maxSpeed = Math.min(vehicule.getMaxSpeed(), speedLimit*speedLimitRespect); 
      
        //Pour ne pas doubler par la droite
        if(environment.getVehiculeFrontLeft() != null
                && environment.getVehiculeFrontLeft().getSpeed()> 5
                && environment.getDistVehiculeFrontLeft()/1000f < safetyDistanceTime * environment.getVehiculeFrontLeft().getSpeed() / 1000f){
            maxSpeed=Math.min(maxSpeed,environment.getVehiculeFrontLeft().getSpeed()-1f );
        }
        
        //Quand on veux se rabattre
        if(desiredWay==2 
               && environment.getVehiculeFrontRight() != null
                ){
            maxSpeed=Math.min(maxSpeed,environment.getVehiculeFrontRight().getSpeed()-1f );
        }
        
        //Quand une voiture veux se rabattre devant nous
        if(environment.getVehiculeFrontLeft() != null
                && environment.getVehiculeFrontLeft().isTurnSignalRight()
                ){
            maxSpeed=Math.min(maxSpeed,environment.getVehiculeFrontLeft().getSpeed()-1f );
        }
        

        float desiredSpeed = maxSpeed;
        float v1 = vehicule.getSpeed();
        
        //Prise en compte des véhicule devant nous
        for(int i=0;i<vehiculesConcerned.size();i++){
            Vehicule vehiculeConcerned = vehiculesConcerned.get(i);
            int distVehiculeConcerned = distVehiculesConcerned.get(i);
            boolean isVeryConcerned = isVehiculesVeryConcerned.get(i);
            
            float v2 = vehiculeConcerned.getSpeed();

            float distSecurity = vehiculeConcerned.getLength() / 1000f + 1 + securityDistance(v1, v2);
            if (distVehiculeConcerned / 1000f < distSecurity) {

                if (vehiculeConcerned.isBrake() && isVeryConcerned) {
                    emergencyBrake = true; 
                }
                 
                if (((distVehiculeConcerned / 1000f < vehiculeConcerned.getLength() / 1000f + 1 + dangerDistanceTime * v1 / 1000f
                        || distVehiculeConcerned / 1000f < distSecurity * 0.7)) && isVeryConcerned ) {
                    emergencyBrake = true;
                    
                } else if (distVehiculeConcerned / 1000f < vehiculeConcerned.getLength() / 1000f + 1 + safetyDistanceTime * v2 / 1000f) {
                    desiredSpeed = Math.min(desiredSpeed, v2 - 0.5f);
                } else {
                    desiredSpeed = Math.min(desiredSpeed,v2);
                }
            }
        }
        
        
        //Prise en compte de l'approche de l'intersection
        if(vehicule.getPhase()==Vehicule.STOP_PHASE && environment.getNextJunction()!= null){
            if(environment.getDistNextJunction()/1000f < 0.5f+securityDistance(vehicule.getSpeed(),0)*0.75){   
                emergencyBrake = true;
            }else if(environment.getDistNextJunction()/1000f < 0.5f+securityDistance(vehicule.getSpeed(),0))
            {
                desiredSpeed = 0;
            }
        }
        else if(vehicule.getPhase() == Vehicule.SLOW_DOWN_PHASE){
            desiredSpeed = desiredSpeed/2f;   
        }
        
        //Quand il y a une panne !
        if(vehicule.getPhase()==Vehicule.STOP_PHASE && vehicule.isBroken()){
            //desiredSpeed = 0;
        }
        
        
        //Si le véhicule est dans une intersection
        /*if(vehicule.getPosition() instanceof IntersectionPosition){
            IntersectionPosition intersectionPos = (IntersectionPosition)vehicule.getPosition();
            //Si le véhicule est dans une voie d'accélération
            if(intersectionPos.getJunction() instanceof AccelerationLane)
            {
                AccelerationLane al = (AccelerationLane)intersectionPos.getJunction();
                //Si le véhicule est dans la voie qui souhaite s'insérer
                if(al.isInALWay(vehicule.getPosition().getVehiculeCoordinates()))
                {
                    isInALWay=true;
                    emergencyBrake = false;
                    if(vehicule.getPhase() == Vehicule.SLOW_DOWN_PHASE){
                        desiredSpeed = desiredSpeed/2f;
                        if(desiredSpeed > vehicule.getSpeed())
                        {
                            desiredSpeed = vehicule.getSpeed();
                        }
                    }
                }
            }
        }*/
        
        if(vehicule.getSpeed() < desiredSpeed){
            accelerate = true;
        }else if(vehicule.getSpeed() > desiredSpeed){
            brake = true;
        }
        
        //ON EFFECTUE NOS CHOIX
        if(emergencyBrake){
            vehicule.setAcceleration(-vehicule.getMaxAcceleration());
            //vehicule.setSpeed(vehicule.getSpeed()/2);
            vehicule.setBrake(true);
        }
        else if(brake){
            vehicule.setAcceleration(-acceleration);
            vehicule.setBrake(false);
        }
        else if(accelerate){
            vehicule.setAcceleration(acceleration);
            vehicule.setBrake(false);
        }
        
        vehicule.getDriver().setOvertake(overtake);
        if(overtake){
            if(vehicule == vehiculeToFollow)System.out.println("OVERTAKEEEEEEEEEEEEEE");
            RoadPosition roadPos = (RoadPosition) vehicule.getPosition();
            RoadSection roadSection = roadPos.getRoadSection();
            vehicule.setLateralSpeed(lateralSpeed);
        }
        else if(cutIn){
            if(vehicule == vehiculeToFollow)System.out.println("CUTINNNNNNNNNNNNNNNNNNNNNNNNN");
            RoadPosition roadPos = (RoadPosition) vehicule.getPosition();
            RoadSection roadSection = roadPos.getRoadSection();
            vehicule.setLateralSpeed(-lateralSpeed);
        }
        else{
            //On decide de se centrer sur la route
            int wantedLateralOffset;
            if(vehicule.getPosition() instanceof RoadPosition){
                RoadPosition roadPos = (RoadPosition) vehicule.getPosition();
                RoadSection roadSection = roadPos.getRoadSection();
                
                wantedLateralOffset = (roadSection.getRoadWay(roadPos.getWayNumber()).getWidth() - vehicule.getWidth())/2;
                
                if(wantedLateralOffset > roadPos.getLateralOffset()){
                    vehicule.setLateralSpeed(lateralSpeed);
                }else if(wantedLateralOffset < roadPos.getLateralOffset()){
                    vehicule.setLateralSpeed(-lateralSpeed);
                }else{
                    vehicule.setLateralSpeed(0.0f);
                }
            }else{      
                IntersectionPosition intPos = (IntersectionPosition) vehicule.getPosition();
                int Roadwidth = intPos.getJunction().getRoadSection(intPos.getEndRoadSection()).getRoadWay(intPos.getEndWayNumber()).getWidth();
                wantedLateralOffset = (Roadwidth - intPos.getLateralOffset())/2;
                
                if(wantedLateralOffset > intPos.getLateralOffset()){
                    vehicule.setLateralSpeed(lateralSpeed);
                }else if(wantedLateralOffset < intPos.getLateralOffset()){
                    vehicule.setLateralSpeed(-lateralSpeed);
                }else{
                    vehicule.setLateralSpeed(0.0f);
                }
            }
            
            
            
        }
        
        if (environment.getNextJunction()!= null){
            if (environment.getDistNextJunction()/1000f <  50 && desiredWay==0){
                vehicule.updateRoadWayDestination();
            }
        }

        updateBehaviorParameters(vehicule, environment, dt);


        
        /*if(environment.getVehiculeFront() != null 
                && environment.getDistVehiculeFront()< environment.getVehiculeFront().getLength()){
            vehicule.setBroken(true);
        }*/

        //if(vehiculeIndex == roadSection.getVehicules().size()-1){
        //if(vehiculeIndex == 0 && roadSection.getId()==2){
        if(vehicule == vehiculeToFollow){
            System.out.println("Phase : "+vehicule.getPhase());
            System.out.println("v = "+ vehicule.getSpeed());
            if(emergencyBrake){
                System.out.println("EmergencyBrake!!");
            }else if(brake){
                System.out.println("Brake!!");
            }
            System.out.println("[bl]\t"+environment.getDistVehiculeBackLeft()/1000f+"\t\t"+environment.getDistVehiculeFrontLeft()/1000f+"\t[fl]");
            System.out.println("[b-]\t"+environment.getDistVehiculeBack()/1000f+"\t[ -]\t"+environment.getDistVehiculeFront()/1000f+"\t[f-]");
            System.out.println("[br]\t"+environment.getDistVehiculeBackRight()/1000f+"\t\t"+environment.getDistVehiculeFrontRight()/1000f+"\t[fr]");
            System.out.println("SpeedLimit : "+speedLimit);
            System.out.println("Intersection : "+environment.getDistNextJunction()/1000f);
            System.out.println("Braking Distance : "+ brakingDistance(vehicule)/1000f);
            
            String juncType="X";
            if (environment.getNextJunction() != null){
                if(environment.getNextJunction() instanceof CrossRoad){
                    CrossRoad c = (CrossRoad)environment.getNextJunction();
                    RoadSign roadSign = c.getRoadSign(environment.getIndexForNextJunction());
                    System.out.println(roadSign.toString());
                    juncType= roadSign.toString();
                }
            }

            InfoView.update(environment.getDistNextJunction()/1000f, juncType);
        }
            
         
    }
    
    public static void setVehiculeToFollow(Vehicule vehiculeToFollow) {
        Behavior.vehiculeToFollow = vehiculeToFollow;
    }
    
    public Color getBehaviorColor() {
        return this.behaviorColor;
    }

    public static Behavior getRandomBehavior(){
        int type = (int) (Math.random()*11);
        Behavior b;

        if(type < 6)
            b = Behavior.getStandardBehavior();
        else if (type <7)
            b = Behavior.getGrannyBehavior();
        else if (type <9)
            b = Behavior.getNervousBehavior();
        else if (type <11)
            b = Behavior.getCoolBehavior();
        else
            b= Behavior.getStandardBehavior();
        
        return b;
        
    }
    
    @Override
    public String toString (){
        String s;
        if (this.equals(NervousBehavior)){
            s="Nervous";
        }
        else if (this.equals(CoolBehavior)){
            s="Cool";
        }
        else if (this.equals(GrannyBehavior)){
            s="Granny";
        }
        else {
            s="Standard";
        }
                
        return s;
        
    }
    
}


