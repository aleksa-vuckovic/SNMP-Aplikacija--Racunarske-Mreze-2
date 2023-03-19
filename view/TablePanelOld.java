package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.table.*;

public class TablePanel extends JPanel {
	private JTable table;
	private String[] cols;
	public TablePanel(String title, String[] cols) {
		this.cols = cols;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
		JLabel top = new JLabel(title, SwingConstants.CENTER);
		this.add(top, BorderLayout.NORTH);
		table = new JTable();
		table.setShowGrid(true);
		JScrollPane pane = new JScrollPane(table);
		this.add(pane, BorderLayout.CENTER);
		setData(new String[0][0]);
	}
	public synchronized void setData(String[][] rows) {
		DefaultTableModel m = new DefaultTableModel(rows, cols);
		TableModel old = table.getModel();
		if (equal(old, m)) return;
		table.setModel(m);
		revalidate();
		System.out.println("Changed");
	}
	private static boolean equal(TableModel m1, TableModel m2) {
		int n = m1.getRowCount();
		if (n != m2.getRowCount()) return false;
		int m = m1.getColumnCount();
		if (m != m2.getColumnCount()) return false;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (!(m1.getValueAt(i, j)).equals(m2.getValueAt(i, j))) {
					System.out.println(m1.getValueAt(i, j) + " nije jednako " + m2.getValueAt(i, j));
					return false;
				}
		return true;
	}
}