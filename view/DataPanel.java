package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableModel;

public class DataPanel extends JPanel {
	TablePanel tcp, udp;
	JLabel top;
	public DataPanel(ActionListener backListener) {
		top = new JLabel("", SwingConstants.CENTER);
		top.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		top.setFont(getFont().deriveFont((float)20).deriveFont(Font.BOLD));
		setRouterName("");
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		
		tcp = new TablePanel("TCP");
		udp = new TablePanel("UDP");
		
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(1, 2));
		main.add(tcp);
		main.add(udp);
		this.add(main, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		JButton back = new JButton("Nazad");
		back.addActionListener(backListener);
		bottom.add(back);
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	public void setModel(TableModel tcpTm, TableModel udpTm) {
		tcp.setModel(tcpTm);
		udp.setModel(udpTm);
	}
	public void setRouterName(String name) {
		top.setText("Ruter " + name);
		top.revalidate();
	}
	

}