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

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class representing a trafic light (R/O/G).
 * @author jonathan
 */
public class TraficLight extends RoadSign{

    public String getInitState() {
        return initState;
    }

    public void setInitState(String initState) {
        this.initState = initState;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    

    public enum Ligths {GREEN, ORANGE, RED}
    private String initState;
    private Ligths state;
    private Timer timer;
    private int delay; //ms
    private static final int orangeTime = 1800; //ms

    //To Pause-Resume the timer
    long remaining;
    long lastStart;
            
    public TraficLight(int delay) {
        super("traficLight");
        this.state = Ligths.RED;
        this.delay = delay;
        this.timer = new Timer(this.getDelay(), UpdateLight);
        lastStart = System.currentTimeMillis();
        this.timer.start();
    }
    
   /**
    * Constuctor
    * @param initState Initial state ("G" for Green and "R" for Red)
    * @param delay  Time between red and green lights
    */
    public TraficLight(String initState, int delay) {
        super("traficLight");
        this.initState = initState;
        this.delay = delay;
                
        if (initState.equalsIgnoreCase("G")){
            this.state = Ligths.GREEN;
            this.timer = new Timer(this.getDelay()-orangeTime, UpdateLight);    
        }
        else{
            this.state = Ligths.RED;
            this.timer = new Timer(this.getDelay(), UpdateLight);
        }
        lastStart = System.currentTimeMillis();
        this.timer.start();
    }
    
    
    private ActionListener UpdateLight = new ActionListener ()
    {
        @Override
        public void actionPerformed(ActionEvent event){
            timer.stop();
            switch (state){
                case GREEN : 
                    state = Ligths.ORANGE;
                    timer.setInitialDelay(orangeTime); break;
                case ORANGE : 
                    state = Ligths.RED;
                    timer.setInitialDelay(getDelay()); break;
                case RED : 
                    state = Ligths.GREEN;
                    timer.setInitialDelay(getDelay()-orangeTime); break;
            }
            lastStart = System.currentTimeMillis();
            timer.start();
        }
    };
    
    /**
     * State of the trafic light
     * @return TraficLight.Ligths.[GREEN|RED|ORANGE]
     */
    public TraficLight.Ligths getState(){
        return state;
    }
     
   /**
     * Returns the color of the light (for the drawing)
     * @return java.awt.Color
     */
    public Color getColor(){
        Color c;
        switch (state){
            case GREEN : 
                c=Color.GREEN;  break;
            case ORANGE : 
                c=Color.ORANGE;  break;
            case RED : 
                c=Color.RED;  break;
            default:
                c=Color.PINK;
        }
        return c;
    }

    /**
     * Return the time of the orange light
     * @return int orange light time
     */
    public static int getOrangeTime() {
        return orangeTime;
    } 
    
    public int getLightNo(){
        int n=0;
        switch (state){
            case GREEN : 
                n=2;  break;
            case ORANGE : 
                n=1;  break;
            case RED : 
                n=0;  break;
        }
        return n;
    }
    
    /**
     * Set the timer of the Trafic Light in pause mode
     */
    public void pause(){
        if(timer.isRunning()){
            remaining = timer.getInitialDelay() - (System.currentTimeMillis() - lastStart);
         //   System.out.println("LS: " + lastStart + "  NOW: " + System.currentTimeMillis() + "  TOGO: " + remaining);
            timer.stop();
        }
    }
    
    /**
     * Resume the timer if the Trafic Light was in pause
     */
    public void resume(){
        if(!timer.isRunning()){
            timer.setInitialDelay((int) remaining);
            lastStart = System.currentTimeMillis();
            timer.start();
        }
    }
}
