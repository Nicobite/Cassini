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
package main;



/**
 *
 * @author jonathan
 */
public class Main{
	
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		GlobalErrorHandler.initLoggers();
		
		MainController controller = new MainController();
		controller.start();
    }
}
