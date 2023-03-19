package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.*;

public class HomePanel extends JPanel {
	
	JTextField IPText, periodText;

	public HomePanel(ActionListener goListener) {
		super(new BorderLayout());
		
		JPanel main = new JPanel(new GridLayout(0, 1));
		
		JPanel IPInput = new JPanel(new GridBagLayout());
		JLabel IPLabel = new JLabel("IP adresa: ");
		IPText = new JTextField(20);
		IPInput.add(IPLabel); IPInput.add(IPText);
		
		JPanel PeriodInput = new JPanel(new GridBagLayout());
		JLabel PeriodLabel = new JLabel("Period: ");
		periodText = new JTextField("5", 20);
		PeriodInput.add(PeriodLabel); PeriodInput.add(periodText);
		
		main.add(IPInput); main.add(PeriodInput);
		this.add(main, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new FlowLayout());
		JButton go = new JButton("Potvrdi");
		go.addActionListener(goListener);
		bottom.add(go);
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	public void reset() {
		IPText.setText("");
		periodText.setText("5");
	}
	
	public String getIP() { return IPText.getText(); }
	public int getPeriod() { return Integer.parseInt(periodText.getText()); }
}