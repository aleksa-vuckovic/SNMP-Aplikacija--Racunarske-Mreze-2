package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

public class TablePanel extends JPanel {
	JTable table;

	public TablePanel(String title) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
		table = new JTable();
		table.setShowGrid(true);
		JScrollPane pane = new JScrollPane(table);
		this.add(pane, BorderLayout.CENTER);
		JLabel top = new JLabel(title, SwingConstants.CENTER);
		this.add(top, BorderLayout.NORTH);
	}
	public void setModel(TableModel tm) {
		table.setModel(tm);
	}
}
