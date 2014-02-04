/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.GUI;
import config.files.ConfigFile;
import simulation.SimulationThread;
import java.io.File;

/**
 *
 * @author Sylvain
 */
public class MainController {
	private static GUI gui = null;
	private static final Object guiLock = new Object();
	private SimulationThread simulationThread = null;
	
	public static GUI getGui() {synchronized(guiLock) {return gui;}}
	
	public void start () {
		GUIThread guiThread = new GUIThread();
		guiThread.start();
	}
	
	private class GUIThread extends Thread {
		public void run () {
			try {
				synchronized(guiLock) {
					if (gui != null) throw new IllegalStateException("A GUI is already created");
					gui = new GUI(MainController.this);
				}
			}
			catch (Throwable t) {GlobalErrorHandler.notifyCriticalException(t);}
		}
	}
	
	public synchronized SimulationThread startNewSimulation(File map, ConfigFile testset) {
		simulationThread = new SimulationThread(map, testset);
		return simulationThread;
	}
	
	public synchronized void stopSimulation() {
		simulationThread.unload();
		simulationThread = null;
	}
}
