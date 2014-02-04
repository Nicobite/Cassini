/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Sylvain
 */
public class StatusBarItem {
	private static int delayAfterDone = 4000; //ms
	
	private String name;
	private int progression = 0;
	private ActionListener listener = null;
	private boolean failed = false;
	private boolean canBeRemoved =  false;
	
	
	public StatusBarItem (String name, int progression) {
		this.name = name;
		setProgression(progression);
	}
	
	public StatusBarItem (String name) {
		this.name = name;
	}
	
	public String getName() {return name;}
	public int getProgression() {return progression;}
	public boolean canBeRemoved() {return canBeRemoved;}
	public boolean hasFailed() {return failed;}
	
	public void setActionListener (ActionListener listener) {this.listener = listener;}
	
	public void setName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Null name");
		if (name.isEmpty())
			throw new IllegalArgumentException("Empty name");
		
		this.name = name;
		
		if (listener != null)
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_FIRST, ""));
	}
	
	public void setProgression(int progression) {
		this.progression = progression;
		
		if (progression < 0 || progression > 100)
			throw new IllegalArgumentException("Progression must be a percentage from 0 to 100");
		
		if (progression == 100) 
			launchTimer();
		
		if (listener != null)
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_FIRST, ""));
	}
	
	public void setFailed() {
		failed = true;
		launchTimer();
	}
	
	private void launchTimer () {
		ActionListener l = new ActionListener() {
			public void actionPerformed (ActionEvent e) {timerExpired();}
		};
		Timer timer = new Timer(delayAfterDone, l);
		timer.setRepeats(false);
		timer.start();
	}
	
	private void timerExpired () {
		canBeRemoved = true;
		
		if (listener != null)
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_FIRST, ""));
	}
	
	public String getDisplayString() {
		String s;
		if (failed)
			s = name.concat(" : échec");
		else if (progression < 100)
			s = name.concat(" : " + progression + " %");
		else
			s = name.concat(" : terminé");
		return s;
	}
}
