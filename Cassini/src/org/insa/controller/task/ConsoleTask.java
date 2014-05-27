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
package org.insa.controller.task;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.insa.controller.MainController;

/**
 * 
 * @author Thiebaud Thomas
 */
public class ConsoleTask extends TimerTask {

    private Sigar sigar = new Sigar();
    private long pid = -1;
    
    
    /**
     * Default constructor
     */
    public ConsoleTask() {
        pid = sigar.getPid();
    }
    
    @Override
    public void run() {
        try {
            long residentMemory = sigar.getProcMem(pid).getResident();
            double cpuPercent = sigar.getProcCpu(pid).getPercent();
            
            MainController.getInstance().performDisplayMemoryInfo(residentMemory);
            MainController.getInstance().performDisplayCpuInfo(cpuPercent);
        } catch (SigarException ex) {
            Logger.getLogger(ConsoleTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
