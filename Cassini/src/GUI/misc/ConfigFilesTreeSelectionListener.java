/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.misc;

import GUI.GUI;
import config.files.ConfigFile;
import config.data.ConfigFileTableModel;
import config.data.DataType;
import config.files.IncorrectFormatException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import main.GlobalErrorHandler;

/**
 *
 * @author Sylvain
 */
public class ConfigFilesTreeSelectionListener implements TreeSelectionListener {
	private GUI gui;
	private DataType dataType;
	private JTable table;
	private FilesComponentsUpdater graphicalUpdater;
	private DefaultMutableTreeNode previousSelectedNode = null;
	
	public ConfigFilesTreeSelectionListener (GUI gui, DataType dataType, JTable table, FilesComponentsUpdater graphicalUpdater) {
		this.gui = gui;
		this.dataType = dataType;
		this.table = table;
		this.graphicalUpdater = graphicalUpdater;
	}

	public void valueChanged(TreeSelectionEvent e) {
		try {
			JTree tree = (JTree) e.getSource();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			if (previousSelectedNode != null) {
				final ConfigFile cf = (ConfigFile) previousSelectedNode.getUserObject();

				final StatusBarItem item = new StatusBarItem("Sauvegarde du fichier " + cf.getFile().getName());
				gui.addStatusBarItem(item);

				SwingWorker worker = new SwingWorker<Void, Void>() {
					public Void doInBackground() {
						try {
							try {cf.save();}
							catch (IOException ex) {GlobalErrorHandler.notifyNonCriticalException(ex);}
							item.setProgression(100);
						} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
						return null;
					}
				};
				worker.execute();
			}

			previousSelectedNode = node;

			table.setModel(new ConfigFileTableModel.EmptyConfigFileTableModel(dataType));
			
			graphicalUpdater.update();
			
			if (node != null) {
				final ConfigFile cf = (ConfigFile) node.getUserObject();
				
				final StatusBarItem item = new StatusBarItem("Ouverture du fichier " + cf.getFile().getName());
				gui.addStatusBarItem(item);

				SwingWorker worker = new SwingWorker<ConfigFileTableModel, Void>() {
					public ConfigFileTableModel doInBackground() {
						ConfigFileTableModel model = null;
						try {
							try {model = new ConfigFileTableModel(cf);}
							catch (IncorrectFormatException | InstantiationException ex) {GlobalErrorHandler.notifyNonCriticalException(ex);}
							item.setProgression(100);
						} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
						return model;
					}
					public void done() {
						ConfigFileTableModel model = null;
						try {model = get();}
						catch (InterruptedException | ExecutionException ex) {GlobalErrorHandler.notifyNonCriticalException(ex);}
						if (model != null)
							table.setModel(model);
					}
				};
				worker.execute();
			}
		}
		catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
	}
}
