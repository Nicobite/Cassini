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
package model.junction;

/**
 * Class representing the intersection road sign at the end of a roadsection
 * @author jonathan
 */
public class RoadSign {
    
    /**
     * Type of a road sign : [NONE | STOP | GIVE_WAY | TRAFIC_LIGHT ]
     */
    public enum Sign {NONE, STOP, GIVE_WAY, TRAFIC_LIGHT, ACCELERATION_LANE, DECELERATION_LANE}
    
    private Sign sign;

    public RoadSign(Sign sign) {
        this.sign = sign;
    }

    public RoadSign(String s) {
        if(s.compareToIgnoreCase("stop")==0){
            this.sign = Sign.STOP;
        }else if(s.compareToIgnoreCase("giveWay")==0){
            this.sign = Sign.GIVE_WAY;
        }else if(s.compareToIgnoreCase("traficLight")==0){
            this.sign = Sign.TRAFIC_LIGHT;
        }else if(s.compareToIgnoreCase("accelerationLane")==0){
            this.sign = Sign.TRAFIC_LIGHT;
        }
         else{
            this.sign = Sign.NONE;
        }
    }
    
    public int priorityValue(){
        if(this.sign==Sign.STOP 
                || (this.sign==Sign.TRAFIC_LIGHT && ((TraficLight)this).getState()== TraficLight.Ligths.RED)){
            return 0;
        }else if(this.sign==Sign.GIVE_WAY
                ||this.sign==Sign.ACCELERATION_LANE
                || (this.sign==Sign.TRAFIC_LIGHT && ((TraficLight)this).getState()== TraficLight.Ligths.ORANGE)){
            return 1;    
        }else if(this.sign==Sign.NONE
                || (this.sign==Sign.TRAFIC_LIGHT && ((TraficLight)this).getState()== TraficLight.Ligths.GREEN)){
            return 2;    
        }else{
            System.out.println("Erreur priorityValue");
            return -1;
        }
    }
    
    static public int higherPriority(RoadSign s1, RoadSign s2){
        int v1=s1.priorityValue();
        int v2=s2.priorityValue();
        
        if(v1>v2){
            return -1;
        }else if(v1 == v2){
            return 0;
        }else{
            return 1;
        }
    }
    
    /**
     * @return RoadSign.Sign.[NONE | STOP | GIVE_WAY | TRAFIC_LIGHT ]
     */
    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }
    
    public String toString(){
       String type;
        switch (sign){
           case STOP:
               type = "stop"; break;
           case TRAFIC_LIGHT:
               type = ("Traficlight, " + ((TraficLight)this).getState()); break;
           case GIVE_WAY:
               type = "give way"; break;
           case ACCELERATION_LANE:
               type = "acceleration lane"; break;
           default :
               type = "normal";
       }
       return type;
    }
    
    public String toXMLData(){
        String type;
        switch (sign){
           case STOP:
               return "stop";
           case GIVE_WAY:
               return "giveWay";
           case TRAFIC_LIGHT:
               return "traficLight "+((TraficLight)this).getInitState()+" "+((TraficLight)this).getDelay();
           default :
               return "none";
       }
    }
    
}
