package app;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class AlteredTableModel implements TableModel {

	private TableModel baseModel;
	public AlteredTableModel(TableModel baseModel) {
		this.baseModel = baseModel;
	}
	@Override
	public int getRowCount() {
		return baseModel.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return baseModel.getColumnCount() - 1;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return baseModel.getColumnName(columnIndex + 1);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return baseModel.getColumnClass(columnIndex + 1);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return baseModel.isCellEditable(rowIndex, columnIndex + 1);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return baseModel.getValueAt(rowIndex, columnIndex + 1);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		baseModel.setValueAt(aValue, rowIndex, columnIndex + 1);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		baseModel.addTableModelListener(l);

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		baseModel.addTableModelListener(l);

	}

}
