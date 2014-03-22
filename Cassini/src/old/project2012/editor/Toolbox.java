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

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import old.project2012.model.junction.RoadSign;
import old.project2012.model.vehicule.Vehicule;

import old.project2012.view.MainView;

public class Toolbox extends JPanel implements ActionListener{
	
	private static final int ROADTOOLS = 1;
	private static final int MOUSETOOLS = 0;
    private static final int INTERSECTIONTOOLS = 2;
    private static final int SIGNALISATIONTOOLS = 3;
    private static final int DELETETOOLS = 4;
    private static final int ADDVEHICULETOOLS = 5;
    
    
	JButton btnRoads,btnNone,btnIntersection;
	private MainView mainView;
	private JButton btnMouse;
	private RoadPropertiesPanel roadprop;
    private JButton btnStop;
    private JButton btnGiveWay;
    private JButton btnTraficLight;
    private JButton btnDelete;
    private JButton btnAddVehicule;
	
	
	public Toolbox(MainView mainView) {
		
		roadprop = new RoadPropertiesPanel();
		
		this.mainView = mainView;
		setPreferredSize(new Dimension(200, 600));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		btnRoads = new JButton("Roads");
		btnRoads.addActionListener(this);
        btnRoads.setMinimumSize(new Dimension(200, 25));
        btnRoads.setPreferredSize(new Dimension(200, 25));
        btnRoads.setMaximumSize(new Dimension(200, 25));
        panel.add(btnRoads);
        
		btnMouse = new JButton("Mouse");
		btnMouse.addActionListener(this);
        btnMouse.setMinimumSize(new Dimension(200, 25));
        btnMouse.setPreferredSize(new Dimension(200, 25));
        btnMouse.setMaximumSize(new Dimension(200, 25));
        panel.add(btnMouse);
        
		
		btnIntersection = new JButton("Intersection");
		btnIntersection.addActionListener(this);
        btnIntersection.setMinimumSize(new Dimension(200, 25));
        btnIntersection.setPreferredSize(new Dimension(200, 25));
        btnIntersection.setMaximumSize(new Dimension(200, 25));
		panel.add(btnIntersection);
		
        btnStop = new JButton("STOP");
		btnStop.addActionListener(this);
        btnStop.setMinimumSize(new Dimension(200, 25));
        btnStop.setPreferredSize(new Dimension(200, 25));
        btnStop.setMaximumSize(new Dimension(200, 25));
		panel.add(btnStop);
               
        btnGiveWay = new JButton("Give Way");
		btnGiveWay.addActionListener(this);
        btnGiveWay.setMinimumSize(new Dimension(200, 25));
        btnGiveWay.setPreferredSize(new Dimension(200, 25));
        btnGiveWay.setMaximumSize(new Dimension(200, 25));
		panel.add(btnGiveWay);
        
        btnTraficLight = new JButton("Trafic Light");
		btnTraficLight.addActionListener(this);
        btnTraficLight.setMinimumSize(new Dimension(200, 25));
        btnTraficLight.setPreferredSize(new Dimension(200, 25));
        btnTraficLight.setMaximumSize(new Dimension(200, 25));
		panel.add(btnTraficLight);
        
        btnNone = new JButton("None");
        btnNone.addActionListener(this);
        btnNone.setMinimumSize(new Dimension(200, 25));
        btnNone.setPreferredSize(new Dimension(200, 25));
        btnNone.setMaximumSize(new Dimension(200, 25));
		panel.add(btnNone);

        btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this);
        btnDelete.setMinimumSize(new Dimension(200, 25));
        btnDelete.setPreferredSize(new Dimension(200, 25));
        btnDelete.setMaximumSize(new Dimension(200, 25));
		panel.add(btnDelete);

        
        btnAddVehicule = new JButton("Add Vehicule");
		btnAddVehicule.addActionListener(this);
        btnAddVehicule.setMinimumSize(new Dimension(200, 25));
        btnAddVehicule.setPreferredSize(new Dimension(200, 25));
        btnAddVehicule.setMaximumSize(new Dimension(200, 25));
		panel.add(btnAddVehicule);
        
		
		JLabel lblToolbox = new JLabel("ToolBox");
		add(lblToolbox, BorderLayout.NORTH);
	}

	public RoadPropertiesPanel getRoadprop() {
		return roadprop;
	}

	public void setRoadprop(RoadPropertiesPanel roadprop) {
		this.roadprop = roadprop;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()== btnRoads){
			this.setCursor(Cursor.getDefaultCursor());
			mainView.getTraficView().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			mainView.getTraficView().setTools(ROADTOOLS);
			mainView.addPropertiesPanel(roadprop);
		}
		else if(arg0.getSource() == btnMouse){
			mainView.getTraficView().setTools(MOUSETOOLS);
			mainView.getTraficView().setCursor(Cursor.getDefaultCursor());
			mainView.removePropertiesPanel();
		}
		else if(arg0.getSource() == btnIntersection){
			mainView.getTraficView().setTools(INTERSECTIONTOOLS);
			System.out.println("Btn intersection");
		}
		
        else if(arg0.getSource() == btnGiveWay){            
            mainView.getTraficView().setTools(SIGNALISATIONTOOLS);
            mainView.getTraficView().setIntersectionType(RoadSign.Sign.GIVE_WAY);
			System.out.println("Btn sign");
		}
        else if(arg0.getSource() == btnStop){  
            mainView.getTraficView().setTools(SIGNALISATIONTOOLS);
            mainView.getTraficView().setIntersectionType(RoadSign.Sign.STOP);
			System.out.println("Btn sign");
		}
        else if(arg0.getSource() == btnTraficLight){
            mainView.getTraficView().setTools(SIGNALISATIONTOOLS);
            mainView.getTraficView().setIntersectionType(RoadSign.Sign.TRAFIC_LIGHT);
			System.out.println("Btn sign");
		}
        else if(arg0.getSource() == btnNone){
            mainView.getTraficView().setTools(SIGNALISATIONTOOLS);
            mainView.getTraficView().setIntersectionType(RoadSign.Sign.NONE);
			System.out.println("Btn sign");
		}
		else if(arg0.getSource() == btnDelete){
            mainView.getTraficView().setTools(DELETETOOLS);
		}
        else if(arg0.getSource() == btnAddVehicule){
            mainView.getTraficView().setTools(ADDVEHICULETOOLS);
            String VehiculeType[] = {"Car", "Motorbike", "Truck"};
            JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
            Vehicule v;
            String vType = (String) jop.showInputDialog(null,
                                "Type de véhicule ?",
                                "Ajouter un véhicule",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                VehiculeType,
                                VehiculeType[0]);
            
            
            mainView.getTraficView().setVehiculeToAdd(vType);
          
            
		}
        
	}
	
	

}
