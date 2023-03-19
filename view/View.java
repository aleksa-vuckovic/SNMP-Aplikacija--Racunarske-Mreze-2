package view;

import javax.swing.JFrame;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class View extends JFrame {
	DataPanel dataPanel;
	HomePanel homePanel;
	public View(ActionListener goListener, ActionListener backListener, WindowListener exitListener) {
		dataPanel = new DataPanel(backListener);
		homePanel = new HomePanel(goListener);
		this.setVisible(true);
		this.setLocation(200, 200);
		this.addWindowListener(exitListener);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("RM2 Projekat");
		showHome();
	}
	public void showHome() {
		this.getContentPane().removeAll();
		homePanel.reset();
		this.add(homePanel);
		this.setSize(350, 200);
		this.repaint();
	}
	public void showData(String name, TableModel tcpTm,  TableModel udpTm) {
		dataPanel.setRouterName(name);
		dataPanel.setModel(tcpTm, udpTm);
		this.getContentPane().removeAll();
		this.add(dataPanel);
		this.setSize(800, 300);
		this.repaint();
	}
	public String getInputIP() { return homePanel.getIP(); }
	public int getInputPeriod() { return homePanel.getPeriod(); }
}