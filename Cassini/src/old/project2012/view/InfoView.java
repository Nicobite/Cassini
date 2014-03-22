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
package old.project2012.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JPanel;
import old.project2012.model.vehicule.Vehicule;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JCheckBox;

/**
 *
 * @author gabriel
 */
public class InfoView extends JPanel{
    
    private static Vehicule vehiculeSelected;
    private static JPanel vehiculePanel;
    
    private static Label phase;
    private static Label speed;
    private static Label junctionType;
    private static Label junctionDist;
    private static Label brakingDist;
    private static Label behavior;

    private JPanel panel;
    private JLabel lblNumberOfVehicule;
    private JSlider slider;
    private JPanel panel_1;
    private JCheckBox chckbxSpeedStart;
    private JLabel lblNombreDeVoiture;

    private static Label annoyance;

    
    private static JButton breakDown;
    
    
    public InfoView(){
        this.setPreferredSize(new Dimension(200,500));
        //setLayout(new BorderLayout(0, 0));
        
        vehiculePanel = new JPanel();
        vehiculePanel.setLayout(new GridLayout(8,1));
        vehiculePanel.add(new Label("Vehicule Infos:"));
        
        phase=new Label("");
        phase.setPreferredSize(new Dimension(200,10));
        speed=new Label("");
        junctionDist=new Label("");
        junctionType=new Label("");
        brakingDist=new Label("");
        annoyance=new Label("");
        behavior = new Label("");
        
        vehiculePanel.add(behavior);
        vehiculePanel.add(phase);
        vehiculePanel.add(speed);
        vehiculePanel.add(junctionType);
        vehiculePanel.add(junctionDist);
        vehiculePanel.add(brakingDist);
        vehiculePanel.add(annoyance);
             
        this.add(vehiculePanel);
/*        
        lblNombreDeVoiture = new JLabel("Nombre de voiture: ");
        vehiculePanel.add(lblNombreDeVoiture);
        
        panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));
        
        lblNumberOfVehicule = new JLabel("Number of vehicule");
        panel.add(lblNumberOfVehicule, BorderLayout.NORTH);
        
        slider = new JSlider();
        slider.setValue(100);
        slider.setMinorTickSpacing(50);
        slider.setMajorTickSpacing(200);
        slider.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMaximum(1000);
        panel.add(slider, BorderLayout.WEST);
        
        panel_1 = new JPanel();
        panel.add(panel_1, BorderLayout.SOUTH);
        
        chckbxSpeedStart = new JCheckBox("Speed Start");
        chckbxSpeedStart.setToolTipText("Check to make appears cars with high speed");
        panel_1.add(chckbxSpeedStart);
        vehiculeSelected=null; */
    }

    public static void setVehiculeSelected(Vehicule vehiculeSelected) {
        InfoView.vehiculeSelected = vehiculeSelected;
    }
    

    
    public static void update(float distNextJunc, String JuncType){
        if (vehiculeSelected != null){
            DecimalFormat df = new DecimalFormat("0.00");
            String sphase = "NORMAL";
            
            switch (vehiculeSelected.getPhase()){
                case 0:
                    sphase = "NORMAL"; break;
                case 1:
                    sphase = "STOP"; break;
                case 2:
                    sphase = "START_UP"; break;
                case 3:
                    sphase = "SLOW_DOWN"; break;
            }
            phase.setText("Phase " + sphase);
            junctionType.setText("Intersection " + JuncType);
            junctionDist.setText("                    (" + distNextJunc + " m)");
            speed.setText("Speed=" + df.format(vehiculeSelected.getSpeed()) + " m/s");
            brakingDist.setText("Braking Dist.=" + df.format(vehiculeSelected.getBehavior().brakingDistance(vehiculeSelected)/1000f) + " m");
            annoyance.setText("Annoyance : " + df.format(vehiculeSelected.getDriver().getAnnoyance()));
            behavior.setText("Behavior : " + vehiculeSelected.getBehavior().toString());
        }
    }
    
	public void setLblNombreDeVoiture(String nbvoiture) {
		this.lblNombreDeVoiture.setText(nbvoiture);
	}

	public int getnbCar() {
		
		return slider.getValue();
	}
	
	public boolean isSpeedStart(){
		return chckbxSpeedStart.isSelected();
	}
}
