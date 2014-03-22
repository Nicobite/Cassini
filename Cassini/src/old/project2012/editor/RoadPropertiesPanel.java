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
package old.project2012.editor;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.GridLayout;
import javax.swing.SpinnerNumberModel;

public class RoadPropertiesPanel extends JPanel{
	private JTextField largeurDirect, largeurIndirect;
	private JSpinner nbVoiesDirect, nbVoiesIndirect, vitesseMaxDirect, vitesseMaxIndirect;
	
	public RoadPropertiesPanel() {
		setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblNombreDeVoies = new JLabel("Nb voies directes");
		add(lblNombreDeVoies);
		
		nbVoiesDirect = new JSpinner();
		nbVoiesDirect.setModel(new SpinnerNumberModel(1, 0, 5, 1));
		add(nbVoiesDirect);
		
		JLabel lblVitesse = new JLabel("Vitesse max:");
		add(lblVitesse);
		
		vitesseMaxDirect = new JSpinner();
		vitesseMaxDirect.setModel(new SpinnerNumberModel(50, 30, 150, 20));
		add(vitesseMaxDirect);
		
		JLabel lblLargeur = new JLabel("Largeur: ");
		add(lblLargeur);
		
		largeurDirect= new JTextField();
		largeurDirect.setText("3000");
		add(largeurDirect);
		largeurDirect.setColumns(10);
		
		JLabel label = new JLabel("Nb voies indirectes");
		add(label);
		
		nbVoiesIndirect = new JSpinner();
		nbVoiesIndirect.setModel(new SpinnerNumberModel(1, 0, 5, 1));
		add(nbVoiesIndirect);
		
		JLabel label_1 = new JLabel("Vitesse max:");
		add(label_1);
		
		vitesseMaxIndirect = new JSpinner();
		vitesseMaxIndirect.setModel(new SpinnerNumberModel(50, 30, 150, 10));
		add(vitesseMaxIndirect);
		
		JLabel label_2 = new JLabel("Largeur: ");
		add(label_2);
		
		largeurIndirect = new JTextField();
		largeurIndirect.setText("3000");
		largeurIndirect.setColumns(10);
		add(largeurIndirect);
	}

	public JTextField getLargeurDirect() {
		return largeurDirect;
	}

	public void setLargeurDirect(JTextField vMaxDirect) {
		this.largeurDirect = vMaxDirect;
	}

	public JTextField getLargeurIndirect() {
		return largeurIndirect;
	}

	public void setSargeurIndirect(JTextField vMaxIndirect) {
		this.largeurIndirect = vMaxIndirect;
	}

	public JSpinner getNbVoiesDirect() {
		return nbVoiesDirect;
	}

	public void setNbVoiesDirect(JSpinner nbVoiesDirect) {
		this.nbVoiesDirect = nbVoiesDirect;
	}

	public JSpinner getNbVoiesIndirect() {
		return nbVoiesIndirect;
	}

	public void setNbVoiesIndirect(JSpinner nbVoiesIndirect) {
		this.nbVoiesIndirect = nbVoiesIndirect;
	}

	public JSpinner getVitesseMaxDirect() {
		return vitesseMaxDirect;
	}

	public void setVitesseMaxDirect(JSpinner vitesseMaxDirect) {
		this.vitesseMaxDirect = vitesseMaxDirect;
	}

	public JSpinner getVitesseMaxIndirect() {
		return vitesseMaxIndirect;
	}

	public void setVitesseMaxIndirect(JSpinner vitesseMaxIndirect) {
		this.vitesseMaxIndirect = vitesseMaxIndirect;
	}
}
