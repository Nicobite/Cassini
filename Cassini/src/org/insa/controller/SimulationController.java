/*
* Copyright 2014 Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
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
package org.insa.controller;

import java.util.Timer;
import org.insa.controller.task.SimulationTask;
import org.insa.model.Model;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class SimulationController
 * Manage trafic : priority, trafic lights, and vehicle movement
 */
public class SimulationController {
    
    /**
     * simulation step (in ms)
     */
    private int simulationStep;
    
    /**
     * simulation timer in charge of traffic update
     */
    private Timer timer;
    
    /**
     * the model
     */
    private Model model;
    
    /**
     * debug mode
     */
    private boolean debug;
    
    private SimulationTask simulationTask = null;
    
    /**
     * Constructor
     * @param step Simulation period
     * @param debug true if debug, false otherwise
     */
    public SimulationController(int step, boolean debug) {
        super();
        this.simulationStep = step;
        this.debug = debug;
        this.model = MainController.getInstance().getModel();
    }
 
    /**
     *start simulation
     */
    public void start(){
        int totalTime = 0;
        this.timer = new Timer();
        if(simulationTask != null)
            totalTime = simulationTask.getTotalTime();
        
        simulationTask = new SimulationTask(this.model, simulationStep, debug);
        simulationTask.setTotalTim(totalTime);
        
        this.timer.scheduleAtFixedRate(simulationTask,0, simulationStep);
    }
    
    /**
     * pause simulation
     */
    public void pause(){
        this.timer.cancel();
    }
     
    /**
     *resume simulation
     */
    public void resume(){
        this.start();
    }
    /**
     * Stop simulation
     */
    public void stop(){
        simulationTask.setTotalTim(0);
        timer.cancel();
        timer.purge();
    }
    
    /**
     * Get total time from simulation beginning
     * @return Total time
     */
    public int getTotalTime() {
        return simulationTask.getTotalTime();
    }

    /**
     * Get simulation step
     * @return Simulation step
     */
    public int getSimulationStep() {
        return simulationStep;
    }

    /**
     * Get timer
     * @return Timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Get simulation task
     * @return Simulation task
     */
    public SimulationTask getSimulationTask() {
        return simulationTask;
    }
    
    /**
     * Get model
     * @return Model
     */
    public Model getModel() {
        return model;
    }

    /**
     * Set simulation step
     * @param simulationStep New simulation step 
     */
    public void setSimulationStep(int simulationStep) {
        this.simulationStep = simulationStep;
    }

    /**
     * Set timer
     * @param timer New timer 
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     * Set simulation task
     * @param simulationTask New simulation task 
     */
    public void setSimulationTask(SimulationTask simulationTask) {
        this.simulationTask = simulationTask;
    }
    
    /**
     * Set model
     * @param model New model 
     */
    public void setModel(Model model) {
        this.model = model;
    }
}
