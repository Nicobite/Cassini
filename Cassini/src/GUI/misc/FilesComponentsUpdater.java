/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.misc;

import config.data.DataType;
import config.files.ConfigFile;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Sylvain
 */

public class FilesComponentsUpdater {
	private JTree tree;
	private JLabel[] labels;
	private JLabel pathLabel;
	private JLabel parentLabel;
	private JLabel isGlobalLabel;
	private JLabel vehiclesLinkLabel;
	private JLabel driversLinkLabel;
	private JLabel associationsLinkLabel;
	private JButton[] buttons;
	
	public FilesComponentsUpdater(JTree tree, JLabel[] labels, JButton[] buttons) {
		this.tree = tree;
		if (labels.length != 6) throw new IllegalArgumentException ("Le nombre de labels doit être égal à 6)");
		this.labels = labels;
		this.pathLabel = labels[0];
		this.parentLabel = labels[1];
		this.isGlobalLabel = labels[2];
		this.vehiclesLinkLabel = labels[3];
		this.driversLinkLabel = labels[4];
		this.associationsLinkLabel = labels[5];
		this.buttons = buttons;
	}
	
	public void update() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		
		if (node == null) {
			for (JLabel lbl : labels)
				if (lbl != null) lbl.setText(" - ");
			for (JButton btn : buttons)
				if (btn != null) btn.setEnabled(false);
		}
		else {
			ConfigFile cf = (ConfigFile) node.getUserObject();
			
			for (JButton btn : buttons)
				if (btn != null) btn.setEnabled(true);

			if (pathLabel != null) pathLabel.setText (cf.getCanonicalPath());

			if (parentLabel != null) {
				if (cf.getParent() != null)
					parentLabel.setText (cf.getParent().getCanonicalPath());
				else
					parentLabel.setText(" - ");
			}

			if (isGlobalLabel != null) {
				if (cf.isGlobal())
					isGlobalLabel.setText("oui");
				else
					isGlobalLabel.setText("non");
			}

			if (vehiclesLinkLabel != null)
				vehiclesLinkLabel.setText(cf.getLinkedFile(DataType.VEHICLES).getFile().getPath());
			if (driversLinkLabel != null)
				driversLinkLabel.setText(cf.getLinkedFile(DataType.DRIVERS).getFile().getPath());
			if (associationsLinkLabel != null)
				associationsLinkLabel.setText(cf.getLinkedFile(DataType.DRIVER_VEHICLE_ASSOCIATIONS).getFile().getPath());
		}
	}
}
