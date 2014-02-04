/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.misc;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Sylvain
 */

// TODO [someday] error handling should be implemented in all those functions (even if they're trivial !)

public class ConfigFileTableSelectionListener implements ListSelectionListener  {
	private JButton btn1, btn2;
	private JTable table;

	public ConfigFileTableSelectionListener (JTable table, JButton btn1, JButton btn2) {
		this.table = table;
		this.btn1 = btn1;
		this.btn2 = btn2;
	}

	public void valueChanged(ListSelectionEvent e) {
		int selectedRow = table.getSelectionModel().getLeadSelectionIndex();
		
		if (selectedRow == -1) {
			if (btn1 != null) btn1.setEnabled(false);
			if (btn2 != null) btn2.setEnabled(false);
		}
		else {
			if (btn1 != null) btn1.setEnabled(true);
			if (btn2 != null) btn2.setEnabled(true);
		}	
	}
}
