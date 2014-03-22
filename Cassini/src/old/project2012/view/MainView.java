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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.text.MaskFormatter;

import old.project2012.main.MainThread;
import old.project2012.model.Coordinates;
import old.project2012.model.GlobalModel;
import old.project2012.model.junction.TraficLight;
import old.project2012.xml.ParserLoadingMap;
import old.project2012.editor.RoadPropertiesPanel;
import old.project2012.editor.Toolbox;
import old.project2012.osmModel.OSMModel;
import old.project2012.xml.ParserLoadingTrafic;
import old.project2012.xml.ParserOSM;
import old.project2012.xml.WriterXML;

/**
 *
 * @author gabriel
 */
public class MainView extends JFrame implements ActionListener{

    private JButton startButton, reloadButton, resetViewButton, timeOkButton;
    private java.awt.Scrollbar timeScrollBar;
    private JFormattedTextField timeTextField;
    private GlobalModel globalModel;
    
    private boolean isRunning, reload;
    private int timeScale;
    private File vehiculeToReload;

    private TraficView trafic;
    private JPanel infoView;
    private JMenu mnMode;
    private JRadioButtonMenuItem rdbtnmntmSimulation;
    private JRadioButtonMenuItem rdbtnmntmEditor;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    private RoadPropertiesPanel properties;
        
    private static final ImageIcon play = new ImageIcon("data/img/icons/play.png");
    private static final ImageIcon pause = new ImageIcon("data/img/icons/pause.png");
    private MainThread main;
    
    public MainView(TraficView trafic,MainThread main, GlobalModel model)

    {
    	this.main = main;
        this.trafic = trafic;
        this.globalModel = model;
        isRunning = false;
        reload = false;
        timeScale = 100;
        this.setTitle("PTUT 2012 - Simulation trafic routier");
        
                
        //Default exit on close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        
        
        JMenuItem menuNew = new JMenuItem("New");
        menuNew.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
               openNewClick();
            }
        });
        
        menuFile.add(menuNew);
        
        JMenuItem menuFileOpenMap = new JMenuItem("Open Map");
        menuFileOpenMap.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
               openMapClick();
            }
        });
        menuFile.add(menuFileOpenMap);
        
        JMenuItem menuFileOpenTrafic = new JMenuItem("Open Trafic");
        menuFileOpenTrafic.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
               openTraficClick();
            }
        });
        menuFile.add(menuFileOpenTrafic);
        
        JMenuItem menuFileOpenOSM = new JMenuItem("Open OSM");
        menuFileOpenOSM.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
               openOSMClick();
            }
        });
        menuFile.add(menuFileOpenOSM);
        
        JMenuItem menuFileSave = new JMenuItem("Save");
        menuFileSave.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
               saveClick();
            }
        });
        menuFile.add(menuFileSave);
        
        JMenuItem menuFileClose = new JMenuItem("Close");
        menuFileClose.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
               System.exit(0);
            }
        });
        menuFile.add(menuFileClose);
        
        menuBar.add(menuFile);
        
        this.setJMenuBar(menuBar);
        
        mnMode = new JMenu("Mode");
        menuBar.add(mnMode);
        
        rdbtnmntmEditor = new JRadioButtonMenuItem("Editor");
        rdbtnmntmEditor.addActionListener(this);
        buttonGroup.add(rdbtnmntmEditor);
        mnMode.add(rdbtnmntmEditor);
        
        rdbtnmntmSimulation = new JRadioButtonMenuItem("Simulator");
        rdbtnmntmSimulation.setSelected(true);
        rdbtnmntmSimulation.addActionListener(this);
        buttonGroup.add(rdbtnmntmSimulation);
        mnMode.add(rdbtnmntmSimulation);
        
        
        getContentPane().setLayout(new BorderLayout());
        
        infoView = new InfoView();
        this.getContentPane().add(infoView, BorderLayout.WEST);
        
        this.getContentPane().add(this.trafic, BorderLayout.CENTER);

        
        //Button start
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                StartPauseClick();
            }
        });
        startButton.setIcon(play);
        
        //Button reload
        reloadButton = new JButton("Reload");
        reloadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                ReloadClick();
            }
        });
        
        //Button rest view
        resetViewButton = new JButton("Reset view");
        resetViewButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                ResetViewClick();
            }
        });
        
        try {
            MaskFormatter scale = new MaskFormatter("###");
            timeTextField = new JFormattedTextField(scale);
            timeTextField.setPreferredSize(new Dimension(40,20));
            timeTextField.setText("100");
        } catch (ParseException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        timeScrollBar = new java.awt.Scrollbar(Scrollbar.HORIZONTAL, 100, 1, 1, 999);
        timeScrollBar.setPreferredSize(new Dimension(100,20));
        timeOkButton = new JButton("ok");
        timeOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                timeScale = Integer.parseInt(timeTextField.getText());
                timeScrollBar.setValue(timeScale);
            }
        });
        
        timeScrollBar.addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent arg0) {
                timeScale = timeScrollBar.getValue();
                timeTextField.setText(Integer.toString(timeScale));
            }
        });
        
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(startButton);
        buttonPanel.add(reloadButton);
        buttonPanel.add(resetViewButton);
        buttonPanel.add(new Label("Time scale :"));
        buttonPanel.add(timeTextField);
        buttonPanel.add(new Label("%"));
        buttonPanel.add(timeOkButton);
        buttonPanel.add(timeScrollBar);

        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        this.pack();
        //To place the windows on the center of the screen
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        //Double Buffering
        trafic.createBufferStrategy(2);
    }
    
    public void StartPauseClick(){
        
        if(isRunning){
             startButton.setText("Start");
             startButton.setIcon(play);
             isRunning = false;
             for (TraficLight tl : trafic.getGlobalModel().getTraficLights())
                tl.pause();
             main.pause();
        }

        else{
            reload = false;
            startButton.setText("Pause");
            startButton.setIcon(pause);
            isRunning = true;
            for (TraficLight tl : trafic.getGlobalModel().getTraficLights())
                tl.resume();
            main.play();
        }

    }
    
    public void ReloadClick(){
        reload = true;
        isRunning = false;
        startButton.setText("Start");
        startButton.setIcon(play);
        if(vehiculeToReload != null){
            new ParserLoadingTrafic(trafic.getGlobalModel()).parseFile(vehiculeToReload.getPath());
            trafic.redrawRoad();
        }else{
            for (TraficLight tl : trafic.getGlobalModel().getTraficLights())
                tl.pause();
            main.restart();
        }
        
    }
    
    public void ResetViewClick(){
        trafic.setCoordinatesView(new Coordinates(0, 0));
        trafic.setHeightView(100000);
        trafic.setWidthView(100000);
        trafic.redrawRoad();
    }
    
    public void openNewClick(){
       globalModel.clearVehicules();
       globalModel.clearMap();
       trafic.redrawRoad();
    }
    
    public void openMapClick(){
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.setCurrentDirectory(new File("."));
       int returnVal = fileChooser.showOpenDialog(MainView.this);
       if (returnVal == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           System.out.println("Opening: " + file.getName());
           
           vehiculeToReload = null;
                   
           new ParserLoadingMap(trafic.getGlobalModel()).parseFile(file.getPath());
           trafic.redrawRoad();
       }
    }
    
    public void openTraficClick(){
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.setCurrentDirectory(new File("."));
       int returnVal = fileChooser.showOpenDialog(MainView.this);
       if (returnVal == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           System.out.println("Opening: " + file.getName());
           vehiculeToReload=new File(file.getPath());
           new ParserLoadingTrafic(trafic.getGlobalModel()).parseFile(file.getPath());
           trafic.redrawRoad();
       }
    }
    
    public void openOSMClick(){
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.setCurrentDirectory(new File("."));
       int returnVal = fileChooser.showOpenDialog(MainView.this);
       if (returnVal == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           System.out.println("Opening: " + file.getName());
           
           vehiculeToReload=null;
                   
           globalModel.clearVehicules();
           OSMModel osmModel = new OSMModel();
           ParserOSM parserOSM= new ParserOSM(osmModel);
           parserOSM.parseFile(file.getPath());
           osmModel.toGlobalModel(globalModel);
           trafic.redrawRoad();
       }
    }
    
    public void saveClick(){
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.setCurrentDirectory(new File("."));
       int returnVal = fileChooser.showOpenDialog(MainView.this);
       if (returnVal == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           System.out.println("Saving: " + file.getName());

           WriterXML writerXML = new WriterXML(globalModel);
           writerXML.saveGlobalModel(file.getPath());
       }
    }



    public boolean isRunning(){
        return this.isRunning;
    }
    
    public boolean toReload(){

        return this.reload;
    }

    /**
     * @return the time scale in percentage (100% <=> 1)
     */
    public float getTimeScale() {
        return timeScale/100f;
    }

	@Override
	public void actionPerformed(ActionEvent e) {

		this.getContentPane().remove(infoView);
		
		//Mode edition
		if (rdbtnmntmEditor.isSelected()){
			this.infoView = new Toolbox(this);
			this.globalModel.clearVehicules();
			this.trafic.setEdited(true);
		}
		//Mode simulation
		if (rdbtnmntmSimulation.isSelected()){
			this.infoView = new InfoView();
			this.trafic.setEdited(false);
			
		}
		
		this.getContentPane().add(infoView, BorderLayout.WEST);
		infoView.setVisible(true);
		this.validate();
		
	}
	
	public JPanel getInfoView(){
		return this.infoView;
	}

	public TraficView getTraficView() {
		return this.trafic;
		
	}
	
	public RoadPropertiesPanel getPropertiesPanel(){
		return this.properties;
	}
	
	public void addPropertiesPanel(RoadPropertiesPanel prop){
		if(this.properties == null){
			this.properties = prop;
			this.getContentPane().add(prop,BorderLayout.NORTH);
			this.validate();
		}
	}
	
	public void removePropertiesPanel(){
		if(this.properties != null){
			this.getContentPane().remove(properties);
			this.properties = null;
			this.validate();
		}
	}

}
