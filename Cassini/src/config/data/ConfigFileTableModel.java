/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config.data;

import config.files.ConfigFile;
import config.files.IncorrectFormatException;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import main.GlobalErrorHandler;

/**
 *
 * @author Sylvain
 */


// a model for the JTable displayed in the GUI (to show/edit config data)


// TODO [someday] implement error logging in all those functions

public class ConfigFileTableModel extends AbstractTableModel {
	ArrayList<Object> rowsData;
	ConfigData configData;
	ConfigFile file;
	ConfigObjectManipulator objectManipulator;
	
	public ConfigFileTableModel (ConfigFile file) throws IncorrectFormatException, InstantiationException {
		this.file = file;
		this.configData = new ConfigData (file);
		objectManipulator = file.getDataType().getObjectManipulator();
		
		rowsData = configData.getObjects();
		
		addTableModelListener(new ConfigFileTableModelListener());
	}
	
	public int getRowCount() {return rowsData.size();}
	public int getColumnCount() {return objectManipulator.getAttributesCount();}
	
	public Class<?> getColumnClass(int column) {return objectManipulator.getAttributeClass(column);}
	public String getColumnName(int column) {return objectManipulator.getReadableName(column);}
	
	public Object getValueAt(int row, int column) {return objectManipulator.get(rowsData.get(row), column);}
	public boolean isCellEditable(int rowIndex, int columnIndex) {return true;}
	
	public void setValueAt(final Object value, final int row, final int column) {
		SwingWorker worker = new SwingWorker<Void, Void>() {
			public Void doInBackground() {
				try {
					objectManipulator.set(rowsData.get(row), column, value);
					fireTableCellUpdated(row, column);
				} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
				return null;
			}
		};
		worker.execute();
	}
	
	public void insertRow(final Object o) throws IllegalArgumentException {
		SwingWorker worker = new SwingWorker<Void, Void>() {
			public Void doInBackground() {
				try {
					configData.addObject(o);
					rowsData.add(o);
					fireTableRowsInserted(rowsData.size(), rowsData.size());
				}
				catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
				return null;
			}
		};
		worker.execute();
	}
	
	public void removeRow(final int row) {
		SwingWorker worker = new SwingWorker<Void, Void>() {
			public Void doInBackground() {
				try{
					configData.removeObject(rowsData.get(row));
					rowsData.remove(row);
					fireTableRowsDeleted(row, row);
				}
				catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
				return null;
			}
		};
		worker.execute();
	}
	
	public boolean isRowSameAsParent(int row) {
		return objectManipulator.isSameAsParent(rowsData.get(row));
	}
	
	public boolean hasRowAParent(int row) {
		return ((Heritable)rowsData.get(row)).getParent() == null;
	}
	
	public void resetRowToParent(int row) {
		objectManipulator.resetToParent(rowsData.get(row));
		fireTableRowsUpdated(row, row);
	}
	
	public static class EmptyConfigFileTableModel extends AbstractTableModel {
		
		ConfigObjectManipulator objectManipulator;
		
		public EmptyConfigFileTableModel(DataType dataType) {
			objectManipulator = dataType.getObjectManipulator();
		}
		
		public int getRowCount() {return 0;}
		public int getColumnCount() {return objectManipulator.getAttributesCount();}
		public String getColumnName(int column) {return objectManipulator.getReadableName(column);}
		public boolean isCellEditable(int rowIndex, int columnIndex) {return false;}
		public Object getValueAt(int row, int column) {return "null";}
	}
	
	private class ConfigFileTableModelListener implements TableModelListener {
		public void tableChanged(final TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
				SwingWorker worker = new SwingWorker<Void, Void>() {
					public Void doInBackground() {
						try {
							for (int i = e.getFirstRow() ; i <= e.getLastRow() ; i++)
								try {configData.updateObject(rowsData.get(i));}
								catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
						} catch (Throwable t) {GlobalErrorHandler.notifyNonCriticalException(t);}
						return null;
					}
				};
				worker.execute();
			}
		}
	}
}
