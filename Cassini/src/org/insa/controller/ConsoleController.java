/*
 * Copyright 2014 Abel Juste Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
import org.insa.controller.task.ConsoleTask;

/**
 *
 * @author Thiebaud Thomas
 */
public class ConsoleController {
    private final int PERIOD = 1000 * 5;
    
    private Timer timer = null;
    private ConsoleTask consoleTask = null;
    
    /**
     * Default constructor
     */
    public ConsoleController() {
        this.timer = new Timer();
        consoleTask = new ConsoleTask();
        
        this.timer.scheduleAtFixedRate(consoleTask,0, PERIOD);
    }

    /**
     * Get the console period
     * @return Console period
     */
    public int getPeriod() {
        return PERIOD;
    }

    /**
     * Stop the console 
     */
    public void stop(){
        timer.cancel();
        timer.purge();
    }
}