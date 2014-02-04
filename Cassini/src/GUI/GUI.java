/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import config.data.ConfigFileTableModel;
import config.data.DataType;
import config.types.vehicles.DriverVehicleAssociation;
import config.types.vehicles.TestSetItem;
import config.types.vehicles.VehicleConfig;
import GUI.misc.ConfigFileTableSelectionListener;
import GUI.misc.ConfigFilesTreeSelectionListener;
import GUI.misc.FilesComponentsUpdater;
import GUI.misc.StatusBarItem;
import GUI.roads.RoadsDisplayPanel;
import config.files.ConfigFile;
import config.types.vehicles.DriverConfig;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import main.GlobalErrorHandler;
import main.MainController;
import simulation.SimulationThread;

public class GUI extends javax.swing.JFrame {

	private GUICore core;
	private SimulationThread st;
	private MainController mainController;
	
	private FilesComponentsUpdater vehiclesFilesComponentsUpdater;
	private FilesComponentsUpdater driversFilesComponentsUpdater;
	private FilesComponentsUpdater proportionsFilesComponentsUpdater;
	private FilesComponentsUpdater testsetsFilesComponentsUpdater;

    /**
     * Creates new form GUI
     */
    public GUI(MainController mainController) {
		this.mainController = mainController;
		try {this.core = new GUICore(mainController, this);}
		catch (IOException e) {GlobalErrorHandler.notifyCriticalException(e);}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// initialisation code initially located in the auto-generated main()
				/* Set the Nimbus look and feel */
				//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
				/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
				 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
				 */
				try {
					for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							javax.swing.UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (ClassNotFoundException ex) {
					java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				} catch (InstantiationException ex) {
					java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				} catch (IllegalAccessException ex) {
					java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				} catch (javax.swing.UnsupportedLookAndFeelException ex) {
					java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				}
				//</editor-fold>

				initComponents();	// components initialisation (auto-generated code)
				performCustomInitCode();
				setVisible(true);	// (auto-generated code)
			}
		});
		
        // TODO this.st = mainController.startNewSimulation(null, null);
    }

    private void performCustomInitCode() {
        vehiclesFilesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        driversFilesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        proportionsFilesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        testsetsFilesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JLabel[] vehiclesLabels = {vehiclesFileNameLabel, vehiclesParentFileLabel, vehiclesGlobalFileLabel, null, null, null};
        JLabel[] driversLabels = {driversFileNameLabel, driversParentFileLabel, driversGlobalFileLabel, null, null, null};
        JLabel[] proportionsLabels = {proportionsFileNameLabel, proportionsParentFileLabel, proportionsGlobalFileLabel, proportionsVehiclesLinkLabel, proportionsDriversLinkLabel, null};
        JLabel[] testsetsLabels = {null, null, null, null, null, testsetsProportionsLinkLabel};
        JButton[] vehiclesButtons = {vehiclesRenameFileButton, vehiclesDeleteFileButton, vehiclesAddRowButton};
        JButton[] driversButtons = {driversRenameFileButton, driversDeleteFileButton, driversAddRowButton};
        JButton[] proportionsButtons = {proportionsRenameFileButton, proportionsDeleteFileButton, proportionsAddRowButton};
        JButton[] testsetsButtons = {testsetsRenameFileButton, testsetsDeleteFileButton, testsetsAddRowButton, testsetsGenerateButton};
		
		vehiclesFilesComponentsUpdater = new FilesComponentsUpdater (vehiclesFilesTree, vehiclesLabels, vehiclesButtons);
		driversFilesComponentsUpdater = new FilesComponentsUpdater (driversFilesTree, driversLabels, driversButtons);
		proportionsFilesComponentsUpdater = new FilesComponentsUpdater (proportionsFilesTree, proportionsLabels, proportionsButtons);
		testsetsFilesComponentsUpdater = new FilesComponentsUpdater (testsetsFilesTree, testsetsLabels, testsetsButtons);
		
        vehiclesFilesTree.addTreeSelectionListener(new ConfigFilesTreeSelectionListener(this, DataType.VEHICLES, vehiclesTable, vehiclesFilesComponentsUpdater));
        driversFilesTree.addTreeSelectionListener(new ConfigFilesTreeSelectionListener(this, DataType.DRIVERS, driversTable, driversFilesComponentsUpdater));
        proportionsFilesTree.addTreeSelectionListener(new ConfigFilesTreeSelectionListener(this, DataType.DRIVER_VEHICLE_ASSOCIATIONS, proportionsTable, proportionsFilesComponentsUpdater));
        testsetsFilesTree.addTreeSelectionListener(new ConfigFilesTreeSelectionListener(this, DataType.TESTSET, testsetsTable, testsetsFilesComponentsUpdater));

        vehiclesTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        proportionsTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        driversTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        testsetsTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        vehiclesTable.getSelectionModel().addListSelectionListener(new ConfigFileTableSelectionListener(vehiclesTable, vehiclesDeleteRowButton, vehiclesResetRowButton));
        driversTable.getSelectionModel().addListSelectionListener(new ConfigFileTableSelectionListener(driversTable, driversDeleteRowButton, driversResetRowButton));
        proportionsTable.getSelectionModel().addListSelectionListener(new ConfigFileTableSelectionListener(proportionsTable, proportionsDeleteRowButton, proportionsResetRowButton));
        testsetsTable.getSelectionModel().addListSelectionListener(new ConfigFileTableSelectionListener(testsetsTable, testsetsDeleteRowButton, null));
    }
	
	public void updateAllFilesComponents (JTree tree) {
		vehiclesFilesComponentsUpdater.update();
		driversFilesComponentsUpdater.update();
		proportionsFilesComponentsUpdater.update();
		testsetsFilesComponentsUpdater.update();
	}
			
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        roadDialogBox = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        mainPanel = new javax.swing.JPanel();
        toolBar = new javax.swing.JToolBar();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        mainTabbedPane = new javax.swing.JTabbedPane();
        roadsPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        addRoad = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        proprietesPanel = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        roadsContainerPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        vehiclesAndDriversPanel = new javax.swing.JPanel();
        vehiclesAndDriversTabbedPane = new javax.swing.JTabbedPane();
        vehiclesPanel = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        vehiclesHelpButton = new javax.swing.JToggleButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        vehiclesTable = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        vehiclesFilesTree = new javax.swing.JTree();
        vehiclesFileNameLabel = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        vehiclesGlobalFileLabel = new javax.swing.JLabel();
        vehiclesAddFileButton = new javax.swing.JButton();
        vehiclesRenameFileButton = new javax.swing.JButton();
        vehiclesDeleteFileButton = new javax.swing.JButton();
        vehiclesParentFileLabel = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        vehiclesHelpPanel = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        vehiclesAddRowButton = new javax.swing.JButton();
        vehiclesDeleteRowButton = new javax.swing.JButton();
        vehiclesResetRowButton = new javax.swing.JButton();
        driversPanel = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        driversHelpButton = new javax.swing.JToggleButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        driversTable = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        driversFilesTree = new javax.swing.JTree();
        driversFileNameLabel = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        driversGlobalFileLabel = new javax.swing.JLabel();
        driversAddFileButton = new javax.swing.JButton();
        driversRenameFileButton = new javax.swing.JButton();
        driversDeleteFileButton = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        driversParentFileLabel = new javax.swing.JLabel();
        driversHelpPanel = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        driversAddRowButton = new javax.swing.JButton();
        driversDeleteRowButton = new javax.swing.JButton();
        driversResetRowButton = new javax.swing.JButton();
        proportionsPanel = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        proportionsHelpButton = new javax.swing.JToggleButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        proportionsTable = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        proportionsFilesTree = new javax.swing.JTree();
        proportionsFileNameLabel = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        proportionsGlobalFileLabel = new javax.swing.JLabel();
        proportionsAddFileButton = new javax.swing.JButton();
        proportionsRenameFileButton = new javax.swing.JButton();
        proportionsDeleteFileButton = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        proportionsParentFileLabel = new javax.swing.JLabel();
        proportionsHelpPanel = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        proportionsAddRowButton = new javax.swing.JButton();
        proportionsDeleteRowButton = new javax.swing.JButton();
        proportionsResetRowButton = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        proportionsVehiclesLinkLabel = new javax.swing.JLabel();
        proportionsDriversLinkLabel = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        testsetsPanel = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        testsetsHelpButton = new javax.swing.JToggleButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        testsetsTable = new javax.swing.JTable();
        testsetsHelpPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        testsetsFilesTree = new javax.swing.JTree();
        testsetsAddFileButton = new javax.swing.JButton();
        testsetsRenameFileButton = new javax.swing.JButton();
        testsetsDeleteFileButton = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        testsetsAddRowButton = new javax.swing.JButton();
        testsetsDeleteRowButton = new javax.swing.JButton();
        testsetsGenerateButton = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        testsetsProportionsLinkLabel = new javax.swing.JLabel();
        environmentPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        statusBarLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Ajouter Route"));

        jLabel3.setText("jLabel3");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("jLabel4");

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roadDialogBoxLayout = new javax.swing.GroupLayout(roadDialogBox.getContentPane());
        roadDialogBox.getContentPane().setLayout(roadDialogBoxLayout);
        roadDialogBoxLayout.setHorizontalGroup(
            roadDialogBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roadDialogBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roadDialogBoxLayout.setVerticalGroup(
            roadDialogBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roadDialogBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        toolBar.setRollover(true);

        jButton7.setText("Bouton...");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton7);

        jButton8.setText("...avec...");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton8);

        jButton9.setText("...image");
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton9);

        addRoad.setText("Route");
        addRoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRoadActionPerformed(evt);
            }
        });

        jButton2.setText("Intersection");

        jButton1.setText("Souris");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addRoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addRoad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addRoad, jButton1});

        jTabbedPane1.addTab("Outils d'édition", jPanel1);

        jLabel1.setText("Nombre de voies directes");

        jLabel2.setText("Nombre de voies indirectes");

        jLabel5.setText("Vitesse");

        jLabel6.setText("Largeur");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField2, jTextField3, jTextField4, jTextField6});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        proprietesPanel.addTab("Propriétés", jPanel2);

        jLabel11.setText("Not implemented yet");

        javax.swing.GroupLayout roadsContainerPanelLayout = new javax.swing.GroupLayout(roadsContainerPanel);
        roadsContainerPanel.setLayout(roadsContainerPanelLayout);
        roadsContainerPanelLayout.setHorizontalGroup(
            roadsContainerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roadsContainerPanelLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel11)
                .addContainerGap(745, Short.MAX_VALUE))
        );
        roadsContainerPanelLayout.setVerticalGroup(
            roadsContainerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roadsContainerPanelLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel11)
                .addContainerGap(424, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roadsPanelLayout = new javax.swing.GroupLayout(roadsPanel);
        roadsPanel.setLayout(roadsPanelLayout);
        roadsPanelLayout.setHorizontalGroup(
            roadsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roadsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roadsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proprietesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(roadsContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roadsPanelLayout.setVerticalGroup(
            roadsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roadsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roadsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roadsContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roadsPanelLayout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proprietesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 278, Short.MAX_VALUE)))
                .addContainerGap())
        );

        mainTabbedPane.addTab("Routes", roadsPanel);

        jLabel36.setText("Listes des véhicules :");

        vehiclesHelpButton.setText("Masquer l'aide");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vehiclesHelpPanel, org.jdesktop.beansbinding.ELProperty.create("${visible}"), vehiclesHelpButton, org.jdesktop.beansbinding.BeanProperty.create("selected"), "vehiclesHelpButtonBinding");
        bindingGroup.addBinding(binding);

        vehiclesHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addGap(399, 399, 399)
                .addComponent(vehiclesHelpButton)
                .addContainerGap(581, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(vehiclesHelpButton)))
        );

        vehiclesTable.setModel(new ConfigFileTableModel.EmptyConfigFileTableModel(DataType.VEHICLES));
        jScrollPane6.setViewportView(vehiclesTable);

        jLabel37.setText("Sélectionner un fichier :");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("NotInitializedYet");
        vehiclesFilesTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        vehiclesFilesTree.setRootVisible(false);
        jScrollPane7.setViewportView(vehiclesFilesTree);

        vehiclesFileNameLabel.setText(" - ");

        jLabel39.setText("Fichier : ");

        jLabel40.setText("Commun à toutes les cartes :");

        vehiclesGlobalFileLabel.setText(" - ");

        vehiclesAddFileButton.setText("Nouveau...");
        vehiclesAddFileButton.setEnabled(false);
        vehiclesAddFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        vehiclesRenameFileButton.setText("Renommer");
        vehiclesRenameFileButton.setEnabled(false);
        vehiclesRenameFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        vehiclesDeleteFileButton.setText("Supprimer");
        vehiclesDeleteFileButton.setEnabled(false);
        vehiclesDeleteFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        vehiclesParentFileLabel.setText(" - ");

        jLabel41.setText("Fichier parent :");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jScrollPane7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vehiclesAddFileButton)
                            .addComponent(vehiclesRenameFileButton)
                            .addComponent(vehiclesDeleteFileButton)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(vehiclesFileNameLabel))
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(vehiclesGlobalFileLabel)
                                .addGap(53, 53, 53)
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(vehiclesParentFileLabel))
                            .addComponent(jLabel37))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel22Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {vehiclesAddFileButton, vehiclesDeleteFileButton, vehiclesRenameFileButton});

        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(vehiclesAddFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vehiclesRenameFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vehiclesDeleteFileButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(vehiclesFileNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41)
                        .addComponent(vehiclesParentFileLabel))
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel40)
                        .addComponent(vehiclesGlobalFileLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel42.setText("Il peut aussi contenir de nouveaux véhicules.");

        jLabel43.setText("En gras, les fichiers communs à toutes les cartes.");

        jLabel44.setText("Les autres fichiers sont spécifiques à cette carte.");

        jLabel45.setText("Des fichiers peuvent dépendre d'autres fichiers : ils contiennent un jeu de modification par rapport au fichier parent.");

        jLabel46.setText("Toute modification du fichier parent entraîne une modification du fichier fils.");

        jLabel47.setText("Le fichier fils permet d'ajuster des paramètres pour certains véhicules tout en conservant les autres.");

        javax.swing.GroupLayout vehiclesHelpPanelLayout = new javax.swing.GroupLayout(vehiclesHelpPanel);
        vehiclesHelpPanel.setLayout(vehiclesHelpPanelLayout);
        vehiclesHelpPanelLayout.setHorizontalGroup(
            vehiclesHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiclesHelpPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(vehiclesHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel43)
                    .addComponent(jLabel45)
                    .addGroup(vehiclesHelpPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(vehiclesHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel46)
                            .addComponent(jLabel42))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vehiclesHelpPanelLayout.setVerticalGroup(
            vehiclesHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiclesHelpPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44)
                .addGap(18, 18, 18)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vehiclesAddRowButton.setText("Nouvelle ligne");
        vehiclesAddRowButton.setEnabled(false);
        vehiclesAddRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        vehiclesDeleteRowButton.setText("Supprimer");
        vehiclesDeleteRowButton.setEnabled(false);
        vehiclesDeleteRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        vehiclesResetRowButton.setText("Remettre à la valeur du fichier parent");
        vehiclesResetRowButton.setEnabled(false);
        vehiclesResetRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(vehiclesAddRowButton)
                .addGap(18, 18, 18)
                .addComponent(vehiclesDeleteRowButton)
                .addGap(18, 18, 18)
                .addComponent(vehiclesResetRowButton)
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {vehiclesAddRowButton, vehiclesDeleteRowButton});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vehiclesAddRowButton)
                    .addComponent(vehiclesDeleteRowButton)
                    .addComponent(vehiclesResetRowButton))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout vehiclesPanelLayout = new javax.swing.GroupLayout(vehiclesPanel);
        vehiclesPanel.setLayout(vehiclesPanelLayout);
        vehiclesPanelLayout.setHorizontalGroup(
            vehiclesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiclesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vehiclesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(vehiclesHelpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        vehiclesPanelLayout.setVerticalGroup(
            vehiclesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiclesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vehiclesHelpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vehiclesAndDriversTabbedPane.addTab("Véhicules", vehiclesPanel);

        jLabel26.setText("Listes des conducteurs :");

        driversHelpButton.setText("Masquer l'aide");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, driversHelpPanel, org.jdesktop.beansbinding.ELProperty.create("${visible}"), driversHelpButton, org.jdesktop.beansbinding.BeanProperty.create("selected"), "driversHelpButtonBinding");
        bindingGroup.addBinding(binding);

        driversHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(384, 384, 384)
                .addComponent(driversHelpButton)
                .addContainerGap(581, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(driversHelpButton))
                .addGap(0, 0, 0))
        );

        driversTable.setModel(new ConfigFileTableModel.EmptyConfigFileTableModel(DataType.DRIVERS));
        jScrollPane4.setViewportView(driversTable);

        jLabel31.setText("Sélectionner un fichier :");

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("NotInitializedYet");
        driversFilesTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        driversFilesTree.setRootVisible(false);
        jScrollPane5.setViewportView(driversFilesTree);

        driversFileNameLabel.setText(" - ");

        jLabel33.setText("Fichier : ");

        jLabel34.setText("Commun à toutes les cartes :");

        driversGlobalFileLabel.setText(" - ");

        driversAddFileButton.setText("Nouveau...");
        driversAddFileButton.setEnabled(false);
        driversAddFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        driversRenameFileButton.setText("Renommer");
        driversRenameFileButton.setEnabled(false);
        driversRenameFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        driversDeleteFileButton.setText("Supprimer");
        driversDeleteFileButton.setEnabled(false);
        driversDeleteFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        jLabel50.setText("Fichier parent :");

        driversParentFileLabel.setText(" - ");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(driversAddFileButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(driversRenameFileButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(driversDeleteFileButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(driversFileNameLabel))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(driversGlobalFileLabel)
                                .addGap(53, 53, 53)
                                .addComponent(jLabel50)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(driversParentFileLabel))
                            .addComponent(jLabel31))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel18Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {driversAddFileButton, driversDeleteFileButton, driversRenameFileButton});

        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(driversAddFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(driversRenameFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(driversDeleteFileButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(driversFileNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(driversGlobalFileLabel)
                    .addComponent(jLabel50)
                    .addComponent(driversParentFileLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel30.setText("Il peut aussi contenir de nouveaux conducteurs.");

        jLabel25.setText("En gras, les fichiers communs à toutes les cartes.");

        jLabel24.setText("Les autres fichiers sont spécifiques à cette carte.");

        jLabel27.setText("Des fichiers peuvent dépendre d'autres fichiers : ils contiennent un jeu de modification par rapport au fichier parent.");

        jLabel28.setText("Toute modification du fichier parent entraîne une modification du fichier fils.");

        jLabel29.setText("Le fichier fils permet d'ajuster des paramètres pour certains véhicules tout en conservant les autres.");

        javax.swing.GroupLayout driversHelpPanelLayout = new javax.swing.GroupLayout(driversHelpPanel);
        driversHelpPanel.setLayout(driversHelpPanelLayout);
        driversHelpPanelLayout.setHorizontalGroup(
            driversHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(driversHelpPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(driversHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel27)
                    .addGroup(driversHelpPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(driversHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28)
                            .addComponent(jLabel30))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        driversHelpPanelLayout.setVerticalGroup(
            driversHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(driversHelpPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        driversAddRowButton.setText("Nouvelle ligne");
        driversAddRowButton.setEnabled(false);
        driversAddRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        driversDeleteRowButton.setText("Supprimer");
        driversDeleteRowButton.setEnabled(false);
        driversDeleteRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        driversResetRowButton.setText("Remettre à la valeur du fichier parent");
        driversResetRowButton.setEnabled(false);
        driversResetRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(driversAddRowButton)
                .addGap(18, 18, 18)
                .addComponent(driversDeleteRowButton)
                .addGap(18, 18, 18)
                .addComponent(driversResetRowButton)
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {driversAddRowButton, driversDeleteRowButton});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driversAddRowButton)
                    .addComponent(driversDeleteRowButton)
                    .addComponent(driversResetRowButton))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout driversPanelLayout = new javax.swing.GroupLayout(driversPanel);
        driversPanel.setLayout(driversPanelLayout);
        driversPanelLayout.setHorizontalGroup(
            driversPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, driversPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(driversPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(driversHelpPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        driversPanelLayout.setVerticalGroup(
            driversPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(driversPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(driversHelpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vehiclesAndDriversTabbedPane.addTab("Conducteurs", driversPanel);

        jLabel48.setText("Proportions de conducteurs/véhicules :");

        proportionsHelpButton.setText("Masquer l'aide");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, proportionsHelpPanel, org.jdesktop.beansbinding.ELProperty.create("${visible}"), proportionsHelpButton, org.jdesktop.beansbinding.BeanProperty.create("selected"), "proportionsHelpButtonBinding");
        bindingGroup.addBinding(binding);

        proportionsHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addGap(313, 313, 313)
                .addComponent(proportionsHelpButton)
                .addContainerGap(583, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(proportionsHelpButton))
                .addGap(0, 0, 0))
        );

        proportionsTable.setModel(new ConfigFileTableModel.EmptyConfigFileTableModel(DataType.DRIVER_VEHICLE_ASSOCIATIONS));
        jScrollPane8.setViewportView(proportionsTable);

        jLabel49.setText("Sélectionner un fichier :");

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("NotInitializedYet");
        proportionsFilesTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        proportionsFilesTree.setRootVisible(false);
        jScrollPane9.setViewportView(proportionsFilesTree);

        proportionsFileNameLabel.setText(" - ");

        jLabel51.setText("Fichier : ");

        jLabel52.setText("Commun à toutes les cartes :");

        proportionsGlobalFileLabel.setText(" - ");

        proportionsAddFileButton.setText("Nouveau...");
        proportionsAddFileButton.setEnabled(false);
        proportionsAddFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        proportionsRenameFileButton.setText("Renommer");
        proportionsRenameFileButton.setEnabled(false);
        proportionsRenameFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        proportionsDeleteFileButton.setText("Supprimer");
        proportionsDeleteFileButton.setEnabled(false);
        proportionsDeleteFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        jLabel53.setText("Fichier parent :");

        proportionsParentFileLabel.setText(" - ");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addComponent(jLabel51)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(proportionsFileNameLabel))
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addComponent(jLabel52)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(proportionsGlobalFileLabel)
                                        .addGap(53, 53, 53)
                                        .addComponent(jLabel53)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(proportionsParentFileLabel)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proportionsAddFileButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(proportionsRenameFileButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(proportionsDeleteFileButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(0, 1061, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel25Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {proportionsAddFileButton, proportionsDeleteFileButton, proportionsRenameFileButton});

        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(proportionsAddFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proportionsRenameFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proportionsDeleteFileButton))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(proportionsFileNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(proportionsGlobalFileLabel)
                            .addComponent(jLabel53)
                            .addComponent(proportionsParentFileLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel54.setText("Il peut aussi contenir de nouveaux véhicules.");

        jLabel55.setText("En gras, les fichiers communs à toutes les cartes.");

        jLabel56.setText("Les autres fichiers sont spécifiques à cette carte.");

        jLabel57.setText("Des fichiers peuvent dépendre d'autres fichiers : ils contiennent un jeu de modification par rapport au fichier parent.");

        jLabel58.setText("Toute modification du fichier parent entraîne une modification du fichier fils.");

        jLabel59.setText("Le fichier fils permet d'ajuster des paramètres pour certains véhicules tout en conservant les autres.");

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel9.setText("Chaque association conducteur/véhicule possède une proportion par rapport à l'ensemble du trafic (en pourcentage).");

        javax.swing.GroupLayout proportionsHelpPanelLayout = new javax.swing.GroupLayout(proportionsHelpPanel);
        proportionsHelpPanel.setLayout(proportionsHelpPanelLayout);
        proportionsHelpPanelLayout.setHorizontalGroup(
            proportionsHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proportionsHelpPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(proportionsHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56)
                    .addComponent(jLabel9)
                    .addComponent(jLabel57)
                    .addGroup(proportionsHelpPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(proportionsHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59)
                            .addComponent(jLabel58)
                            .addComponent(jLabel54))))
                .addContainerGap(484, Short.MAX_VALUE))
        );
        proportionsHelpPanelLayout.setVerticalGroup(
            proportionsHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proportionsHelpPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addGap(18, 18, 18)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addContainerGap())
        );

        proportionsAddRowButton.setText("Nouvelle ligne");
        proportionsAddRowButton.setEnabled(false);
        proportionsAddRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        proportionsDeleteRowButton.setText("Supprimer");
        proportionsDeleteRowButton.setEnabled(false);
        proportionsDeleteRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        proportionsResetRowButton.setText("Remettre à la valeur du fichier parent");
        proportionsResetRowButton.setEnabled(false);
        proportionsResetRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        jLabel61.setText("Fichier de véhicules :");

        proportionsVehiclesLinkLabel.setText(" - ");

        proportionsDriversLinkLabel.setText(" - ");

        jLabel63.setText("Fichier de conducteurs :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proportionsVehiclesLinkLabel))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proportionsDriversLinkLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(proportionsAddRowButton)
                .addGap(18, 18, 18)
                .addComponent(proportionsDeleteRowButton)
                .addGap(18, 18, 18)
                .addComponent(proportionsResetRowButton)
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {proportionsAddRowButton, proportionsDeleteRowButton});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(proportionsVehiclesLinkLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63)
                            .addComponent(proportionsDriversLinkLabel)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(proportionsAddRowButton)
                            .addComponent(proportionsDeleteRowButton)
                            .addComponent(proportionsResetRowButton))))
                .addContainerGap())
        );

        javax.swing.GroupLayout proportionsPanelLayout = new javax.swing.GroupLayout(proportionsPanel);
        proportionsPanel.setLayout(proportionsPanelLayout);
        proportionsPanelLayout.setHorizontalGroup(
            proportionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proportionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proportionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proportionsHelpPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        proportionsPanelLayout.setVerticalGroup(
            proportionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proportionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proportionsHelpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vehiclesAndDriversTabbedPane.addTab("Proportions", proportionsPanel);

        jLabel60.setText("Jeux de test :");

        testsetsHelpButton.setText("Masquer l'aide");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, testsetsHelpPanel, org.jdesktop.beansbinding.ELProperty.create("${visible}"), testsetsHelpButton, org.jdesktop.beansbinding.BeanProperty.create("selected"), "testsetsHelpButtonBinding");
        bindingGroup.addBinding(binding);

        testsetsHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60)
                .addGap(433, 433, 433)
                .addComponent(testsetsHelpButton)
                .addContainerGap(581, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(testsetsHelpButton))
                .addGap(0, 0, 0))
        );

        testsetsTable.setModel(new ConfigFileTableModel.EmptyConfigFileTableModel(DataType.TESTSET));
        jScrollPane10.setViewportView(testsetsTable);

        jLabel10.setText("Les jeux de tests contiennent une liste exhaustive des véhicules, générée à partir des fichiers de proportions.");

        jLabel7.setText("Ils contiennent aussi les données initiales (position, vitesse...) de chaque véhicule.");

        jLabel8.setText("Les jeux de tests sont spécifiques à chaque carte.");

        javax.swing.GroupLayout testsetsHelpPanelLayout = new javax.swing.GroupLayout(testsetsHelpPanel);
        testsetsHelpPanel.setLayout(testsetsHelpPanelLayout);
        testsetsHelpPanelLayout.setHorizontalGroup(
            testsetsHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testsetsHelpPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(testsetsHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        testsetsHelpPanelLayout.setVerticalGroup(
            testsetsHelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testsetsHelpPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel62.setText("Sélectionner un fichier :");

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("NotInitializedYet");
        testsetsFilesTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        testsetsFilesTree.setRootVisible(false);
        jScrollPane11.setViewportView(testsetsFilesTree);

        testsetsAddFileButton.setText("Nouveau...");
        testsetsAddFileButton.setEnabled(false);
        testsetsAddFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        testsetsRenameFileButton.setText("Renommer");
        testsetsRenameFileButton.setEnabled(false);
        testsetsRenameFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        testsetsDeleteFileButton.setText("Supprimer");
        testsetsDeleteFileButton.setEnabled(false);
        testsetsDeleteFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jScrollPane11)
                        .addGap(107, 107, 107))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(testsetsAddFileButton)
                        .addGap(10, 10, 10)
                        .addComponent(testsetsRenameFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(testsetsDeleteFileButton)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel26Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {testsetsAddFileButton, testsetsDeleteFileButton, testsetsRenameFileButton});

        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testsetsAddFileButton)
                    .addComponent(testsetsRenameFileButton)
                    .addComponent(testsetsDeleteFileButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        testsetsAddRowButton.setText("Nouvelle ligne");
        testsetsAddRowButton.setEnabled(false);
        testsetsAddRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        testsetsDeleteRowButton.setText("Supprimer");
        testsetsDeleteRowButton.setEnabled(false);
        testsetsDeleteRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        testsetsGenerateButton.setText("Générer des données...");
        testsetsGenerateButton.setEnabled(false);
        testsetsGenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsButtonActionPerformed(evt);
            }
        });

        jLabel64.setText("Fichier de proportions :");

        testsetsProportionsLinkLabel.setText(" - ");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testsetsProportionsLinkLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(testsetsAddRowButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testsetsDeleteRowButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testsetsGenerateButton)
                .addContainerGap())
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {testsetsAddRowButton, testsetsDeleteRowButton});

        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel64)
                        .addComponent(testsetsProportionsLinkLabel))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(testsetsAddRowButton)
                        .addComponent(testsetsDeleteRowButton)
                        .addComponent(testsetsGenerateButton)))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout testsetsPanelLayout = new javax.swing.GroupLayout(testsetsPanel);
        testsetsPanel.setLayout(testsetsPanelLayout);
        testsetsPanelLayout.setHorizontalGroup(
            testsetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testsetsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(testsetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, testsetsPanelLayout.createSequentialGroup()
                        .addGroup(testsetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(testsetsHelpPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        testsetsPanelLayout.setVerticalGroup(
            testsetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testsetsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testsetsHelpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        vehiclesAndDriversTabbedPane.addTab("Jeux de tests", testsetsPanel);

        javax.swing.GroupLayout vehiclesAndDriversPanelLayout = new javax.swing.GroupLayout(vehiclesAndDriversPanel);
        vehiclesAndDriversPanel.setLayout(vehiclesAndDriversPanelLayout);
        vehiclesAndDriversPanelLayout.setHorizontalGroup(
            vehiclesAndDriversPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiclesAndDriversPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vehiclesAndDriversTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1217, Short.MAX_VALUE)
                .addContainerGap())
        );
        vehiclesAndDriversPanelLayout.setVerticalGroup(
            vehiclesAndDriversPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiclesAndDriversPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vehiclesAndDriversTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 570, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPane.addTab("Véhicules & conducteurs", vehiclesAndDriversPanel);

        jLabel12.setText("Not implemented yet");

        jLabel14.setText("This should look like the Vehicles & Drivers tab");

        javax.swing.GroupLayout environmentPanelLayout = new javax.swing.GroupLayout(environmentPanel);
        environmentPanel.setLayout(environmentPanelLayout);
        environmentPanelLayout.setHorizontalGroup(
            environmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(environmentPanelLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(environmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel12))
                .addContainerGap(917, Short.MAX_VALUE))
        );
        environmentPanelLayout.setVerticalGroup(
            environmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(environmentPanelLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel12)
                .addGap(30, 30, 30)
                .addComponent(jLabel14)
                .addContainerGap(402, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Environnement", environmentPanel);

        jButton3.setText("Charger modèle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadSimulation(evt);
            }
        });

        jButton4.setText("Lancer la simulation");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchSimulation(evt);
            }
        });

        jButton5.setText("Pause");
        jButton5.setMaximumSize(new java.awt.Dimension(69, 23));
        jButton5.setMinimumSize(new java.awt.Dimension(69, 23));
        jButton5.setPreferredSize(new java.awt.Dimension(69, 23));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseSimulation(evt);
            }
        });

        jButton6.setText("Redémarrer la simulation");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartSimulation(evt);
            }
        });

        jButton10.setText("Retirer modèle");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unloadSimulation(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setText("Not implemented yet");

        jLabel15.setText("Functionnalities to display roads and map should be reused from the Road tab");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13))
                .addContainerGap(917, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel13)
                .addGap(38, 38, 38)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton11.setText("Charger carte");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMap(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 323, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        mainTabbedPane.addTab("Simulation", jPanel6);

        statusBarLabel.setText("Prêt");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statusBarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(statusBarLabel)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        fileMenu.setText("Fichier");

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText("Ouvrir");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemsActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Enregistrer");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemsActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        menuBar.add(fileMenu);

        jMenu2.setText("Édition");
        menuBar.add(jMenu2);

        jMenu3.setText("Affichage");
        menuBar.add(jMenu3);

        jMenu4.setText("Outils");
        menuBar.add(jMenu4);

        jMenu5.setText("Données");
        menuBar.add(jMenu5);

        jMenu6.setText("Aide");
        menuBar.add(jMenu6);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addRoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRoadActionPerformed

    }//GEN-LAST:event_addRoadActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        JToggleButton btn = (JToggleButton) evt.getSource();
        if (btn.isSelected()) {
            btn.setText("Masquer l'aide");
        } else {
            btn.setText("Afficher l'aide");
        }
    }//GEN-LAST:event_helpButtonActionPerformed

    private void menuItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemsActionPerformed
        Object s = evt.getSource();
        if (s == openMenuItem) {
            core.openMap();
        }
        if (s == saveMenuItem) {
            core.saveMap();
        }
    }//GEN-LAST:event_menuItemsActionPerformed

    private void filesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filesButtonActionPerformed
        Object s = evt.getSource();
        JTree tree = null;

        if (s == vehiclesRenameFileButton || s == vehiclesDeleteFileButton || s == vehiclesAddFileButton) {
            tree = vehiclesFilesTree;
        } else if (s == driversRenameFileButton || s == driversDeleteFileButton || s == driversAddFileButton) {
            tree = driversFilesTree;
        } else if (s == proportionsRenameFileButton || s == proportionsDeleteFileButton || s == proportionsAddFileButton) {
            tree = proportionsFilesTree;
        } else if (s == testsetsRenameFileButton || s == testsetsDeleteFileButton || s == testsetsAddFileButton) {
            tree = testsetsFilesTree;
        }

        if (s == vehiclesRenameFileButton || s == driversRenameFileButton || s == proportionsRenameFileButton || s == testsetsRenameFileButton) {
            core.renameConfigFile(tree);
        } else if (s == vehiclesDeleteFileButton || s == driversDeleteFileButton || s == proportionsDeleteFileButton || s == testsetsDeleteFileButton) {
            core.deleteConfigFile(tree);
        } else if (s == vehiclesAddFileButton || s == driversAddFileButton || s == proportionsAddFileButton || s == testsetsAddFileButton) {
            if (tree == vehiclesFilesTree) {
                core.addConfigFile(tree, DataType.VEHICLES);
            } else if (tree == driversFilesTree) {
                core.addConfigFile(tree, DataType.DRIVERS);
            } else if (tree == proportionsFilesTree) {
                core.addConfigFile(tree, DataType.DRIVER_VEHICLE_ASSOCIATIONS);
            } else if (tree == testsetsFilesTree) {
                core.addConfigFile(tree, DataType.TESTSET);
            }
        }
    }//GEN-LAST:event_filesButtonActionPerformed

    private void rowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rowsButtonActionPerformed
        Object s = evt.getSource();
        JTable table = null;

        if (s == vehiclesAddRowButton || s == vehiclesDeleteRowButton || s == vehiclesResetRowButton) {
            table = vehiclesTable;
        } else if (s == driversAddRowButton || s == driversDeleteRowButton || s == driversResetRowButton) {
            table = driversTable;
        } else if (s == proportionsAddRowButton || s == proportionsDeleteRowButton || s == proportionsResetRowButton) {
            table = proportionsTable;
        } else if (s == testsetsAddRowButton || s == testsetsDeleteRowButton || s == testsetsGenerateButton) {
            table = testsetsTable;
        }

        if (table.getModel() instanceof ConfigFileTableModel) {
            ConfigFileTableModel model = (ConfigFileTableModel) table.getModel();
            if (s == vehiclesAddRowButton) {
                model.insertRow(new VehicleConfig());
            } else if (s == driversAddRowButton) {
                model.insertRow(new DriverConfig());
            } else if (s == proportionsAddRowButton) {
                model.insertRow(new DriverVehicleAssociation());
            } else if (s == testsetsAddRowButton) {
                model.insertRow(new TestSetItem());
            } else if (s == vehiclesDeleteRowButton || s == driversDeleteRowButton || s == proportionsDeleteRowButton || s == testsetsDeleteRowButton) {
                model.removeRow(table.getSelectionModel().getLeadSelectionIndex());
            } else if (s == vehiclesResetRowButton || s == driversResetRowButton || s == proportionsResetRowButton) {
                model.resetRowToParent(table.getSelectionModel().getLeadSelectionIndex());
            } else if (s == testsetsGenerateButton) {
                int nb = -1;
                do {
                    Object o = JOptionPane.showInputDialog(this, "Combien de véhicules voulez-vous générer ?\nIls seront générés à partir du fichier lié et ajoutés à ceux déjà présents", "Générer un jeu de test", JOptionPane.PLAIN_MESSAGE);
                    if (o != null) {
                        try {
                            nb = Integer.parseInt(o.toString());
                        } catch (NumberFormatException e) {
                            nb = -1;
                        }
                    }
                } while (nb <= 0);

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) testsetsFilesTree.getLastSelectedPathComponent();
                core.generateTestItemSet((ConfigFile) node.getUserObject(), nb, model);
            }
        }
    }//GEN-LAST:event_rowsButtonActionPerformed
	
	//TODO simulation in the functions below
    private void loadSimulation(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadSimulation
        // Permet à l'utilisateur de choisir un fichier
        ConfigFileChooser.showConfigFileChooser(this, testsetsFilesTree.getModel());
        
        //this.st = mainController.startNewSimulation(null, aCharger);
        st.load();
        statusBarLabel.setText(st.getStatus().toHumanReadableString());

    }//GEN-LAST:event_loadSimulation

    private void launchSimulation(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchSimulation
        st.load();
        statusBarLabel.setText(st.getStatus().toHumanReadableString());
        st.launch();
        statusBarLabel.setText(st.getStatus().toHumanReadableString());
        statusBarLabel.setText(st.getStatus().toHumanReadableString());
    }//GEN-LAST:event_launchSimulation

    private void pauseSimulation(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseSimulation
        st.pause();
        statusBarLabel.setText(st.getStatus().toHumanReadableString());
    }//GEN-LAST:event_pauseSimulation

    private void restartSimulation(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartSimulation
        st.restart();
        statusBarLabel.setText(st.getStatus().toHumanReadableString());
    }//GEN-LAST:event_restartSimulation

    private void unloadSimulation(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unloadSimulation
        st.unload();
        statusBarLabel.setText(st.getStatus().toHumanReadableString());
    }//GEN-LAST:event_unloadSimulation

    private void loadMap(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMap
        
    }//GEN-LAST:event_loadMap

	
    /* HERE ARE THE CUSTOM FUNCTIONS (listeners and initializers are above) */
    // status bar handling
    private final ArrayList<StatusBarItem> statusBarItems = new ArrayList<>();
    private ActionListener statusBarActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            refreshStatusBar();
        }
    };

    private void refreshStatusBar() {
        synchronized (statusBarItems) {
            StringBuilder s = new StringBuilder(100);

            ArrayList<StatusBarItem> items = (ArrayList<StatusBarItem>) statusBarItems.clone();
            for (StatusBarItem item : items) {
                if (item.canBeRemoved()) {
                    statusBarItems.remove(item);
                } else {
                    if (s.length() != 0) {
                        s.append("          ");
                    }
                    s.append(item.getDisplayString());
                }
            }

            if (statusBarItems.isEmpty()) {
                statusBarLabel.setText("Prêt");
            } else {
                statusBarLabel.setText(s.toString());
            }
        }
    }

    public void addStatusBarItem(StatusBarItem item) {
        synchronized (statusBarItems) {
            item.setActionListener(statusBarActionListener);
            statusBarItems.add(item);
            refreshStatusBar();
        }
    }
	
	// error display
	public void displayError(Throwable t, String infoMsg) {
		StringBuilder msg = new StringBuilder();
		msg.append(infoMsg + "\n");
		msg.append("Type : " + t.getClass().toString() + "\n");
		msg.append("Message : " + t.getMessage() + "\n");
		while (t.getCause() != null) {
			t = t.getCause();
			msg.append("\n");
			msg.append("Exception chainée :\n");
			msg.append("Type : " + t.getClass().toString() + "\n");
			msg.append("Message : " + t.getMessage() + "\n");
		}
		msg.append("\n");
		msg.append("Se référer à la console pour la stack trace\n");
		
		displayError(msg.toString());
	}
	
	public void displayError(final String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {JOptionPane.showMessageDialog(GUI.this, msg, "Erreur", JOptionPane.ERROR_MESSAGE);}
		});
	}

    // vehicles and drivers functions
    public void setDriversTreeModel(DefaultTreeModel treeModel) {
        driversFilesTree.setModel(treeModel);
    }

    public void setVehiclesTreeModel(DefaultTreeModel treeModel) {
        vehiclesFilesTree.setModel(treeModel);
    }

    public void setProportionsTreeModel(DefaultTreeModel treeModel) {
        proportionsFilesTree.setModel(treeModel);
    }

    public void setTestsetsTreeModel(DefaultTreeModel treeModel) {
        testsetsFilesTree.setModel(treeModel);
    }

    public JTree[] getVehiclesAndDriversTrees() {
        JTree trees[] = {vehiclesFilesTree, driversFilesTree, proportionsFilesTree, testsetsFilesTree};
        return trees;
    }

    public void enableVehiclesAndDrivers(boolean enabled) {
        vehiclesAddFileButton.setEnabled(enabled);
        driversAddFileButton.setEnabled(enabled);
        proportionsAddFileButton.setEnabled(enabled);
        testsetsAddFileButton.setEnabled(enabled);
    }
	
	public void setRoadsDisplayPanel(RoadsDisplayPanel panel) {
		// TODO this is some experiemental code to display roads, but it is not complete (not even tested or functional)
		
		panel.setMinimumSize(new Dimension(roadsContainerPanel.getWidth(), roadsContainerPanel.getHeight()));
		panel.setPreferredSize(new Dimension(roadsContainerPanel.getWidth(), roadsContainerPanel.getHeight()));
		panel.setMaximumSize(new Dimension(roadsContainerPanel.getWidth(), roadsContainerPanel.getHeight()));
		roadsContainerPanel.add(new JButton("plop"));
		//roadsContainerPanel.add(panel);
		roadsContainerPanel.revalidate();
		
		JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(roadsContainerPanel.getWidth(), getHeight());
        frame.setLocation(200, 200);
        frame.setVisible(true);
	}
	
	// <editor-fold defaultstate="collapsed" desc="Variables declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRoad;
    private javax.swing.JButton driversAddFileButton;
    private javax.swing.JButton driversAddRowButton;
    private javax.swing.JButton driversDeleteFileButton;
    private javax.swing.JButton driversDeleteRowButton;
    private javax.swing.JLabel driversFileNameLabel;
    private javax.swing.JTree driversFilesTree;
    private javax.swing.JLabel driversGlobalFileLabel;
    private javax.swing.JToggleButton driversHelpButton;
    private javax.swing.JPanel driversHelpPanel;
    private javax.swing.JPanel driversPanel;
    private javax.swing.JLabel driversParentFileLabel;
    private javax.swing.JButton driversRenameFileButton;
    private javax.swing.JButton driversResetRowButton;
    private javax.swing.JTable driversTable;
    private javax.swing.JPanel environmentPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JButton proportionsAddFileButton;
    private javax.swing.JButton proportionsAddRowButton;
    private javax.swing.JButton proportionsDeleteFileButton;
    private javax.swing.JButton proportionsDeleteRowButton;
    private javax.swing.JLabel proportionsDriversLinkLabel;
    private javax.swing.JLabel proportionsFileNameLabel;
    private javax.swing.JTree proportionsFilesTree;
    private javax.swing.JLabel proportionsGlobalFileLabel;
    private javax.swing.JToggleButton proportionsHelpButton;
    private javax.swing.JPanel proportionsHelpPanel;
    private javax.swing.JPanel proportionsPanel;
    private javax.swing.JLabel proportionsParentFileLabel;
    private javax.swing.JButton proportionsRenameFileButton;
    private javax.swing.JButton proportionsResetRowButton;
    private javax.swing.JTable proportionsTable;
    private javax.swing.JLabel proportionsVehiclesLinkLabel;
    private javax.swing.JTabbedPane proprietesPanel;
    private javax.swing.JDialog roadDialogBox;
    private javax.swing.JPanel roadsContainerPanel;
    private javax.swing.JPanel roadsPanel;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JLabel statusBarLabel;
    private javax.swing.JButton testsetsAddFileButton;
    private javax.swing.JButton testsetsAddRowButton;
    private javax.swing.JButton testsetsDeleteFileButton;
    private javax.swing.JButton testsetsDeleteRowButton;
    private javax.swing.JTree testsetsFilesTree;
    private javax.swing.JButton testsetsGenerateButton;
    private javax.swing.JToggleButton testsetsHelpButton;
    private javax.swing.JPanel testsetsHelpPanel;
    private javax.swing.JPanel testsetsPanel;
    private javax.swing.JLabel testsetsProportionsLinkLabel;
    private javax.swing.JButton testsetsRenameFileButton;
    private javax.swing.JTable testsetsTable;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton vehiclesAddFileButton;
    private javax.swing.JButton vehiclesAddRowButton;
    private javax.swing.JPanel vehiclesAndDriversPanel;
    private javax.swing.JTabbedPane vehiclesAndDriversTabbedPane;
    private javax.swing.JButton vehiclesDeleteFileButton;
    private javax.swing.JButton vehiclesDeleteRowButton;
    private javax.swing.JLabel vehiclesFileNameLabel;
    private javax.swing.JTree vehiclesFilesTree;
    private javax.swing.JLabel vehiclesGlobalFileLabel;
    private javax.swing.JToggleButton vehiclesHelpButton;
    private javax.swing.JPanel vehiclesHelpPanel;
    private javax.swing.JPanel vehiclesPanel;
    private javax.swing.JLabel vehiclesParentFileLabel;
    private javax.swing.JButton vehiclesRenameFileButton;
    private javax.swing.JButton vehiclesResetRowButton;
    private javax.swing.JTable vehiclesTable;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
	//</editor-fold>
}
