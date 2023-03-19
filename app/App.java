package app;

import java.awt.GraphicsConfiguration;
import view.View;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

import com.ireasoning.protocol.snmp.SnmpSession;
import com.ireasoning.protocol.snmp.SnmpTableModel;

public class App {
	
	private View view;
	private Session session;
	private State state;
	private SnmpTableModel tcpTm, udpTm;
	
	public App() {
		ActionListener goListener = ae -> { go(); };
		ActionListener backListener = ae -> { back(); };
		WindowListener exitListener = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				state.exit();
			}
		};
		view = new View(goListener, backListener, exitListener);
		state = new HomeState();
	}
	public synchronized void go() { state.go(); }
	public synchronized void back() { state.back(); }
	public synchronized void exit() { state.exit(); }
	
	private abstract class State {
		public abstract void go();
		public abstract void back();
		public abstract void exit();
	}
	
	private class HomeState extends State {
		public void go() {
			String IP = view.getInputIP();
			int period;
			try {
				period = view.getInputPeriod();
			} catch (Exception e) { error("Greška", "Period mora biti ceo broj veći od 0."); return;}
			if (!checkIPFormat(IP)) { error("Greška", "IP adresa nije u ispravnom formatu."); return; }
			if (period <= 0) { error("Greška", "Period mora biti ceo broj veći od 0."); return; }
			
			String name;
			try {
				session = new Session(IP, "si2019", "si2019");
				name = session.getHostName();
				tcpTm = session.getTCP();
				udpTm = session.getUDP();
			} catch (ErrorSessionFailed e) { error("Greška", "Ruter sa unetom IP adresom nije pronadjen"); return; }
			
			tcpTm.setColumnName(0, "Stanje");
			tcpTm.setColumnName(1, "Lokalna IP");
			tcpTm.setColumnName(2, "Lokalni Port");
			tcpTm.setColumnName(3, "Udaljena IP");
			tcpTm.setColumnName(4, "Udaljeni Port");
			udpTm.setColumnName(0, "Adresa");
			udpTm.setColumnName(1, "Port");
			tcpTm.startPolling(period);
			udpTm.startPolling(period);
			view.showData(name, new AlteredTableModel(tcpTm), udpTm);
			state = new DataState();
		}
		public void back() { return; }
		public void exit() { 
			view.dispose();
		}
	}
	
	private class DataState extends State {
		public void go() { return; }
		public void back() {
			tcpTm.stopPolling();
			udpTm.stopPolling();
			tcpTm = udpTm = null;
			view.showHome();
			state = new HomeState();
		}
		public void exit() {
			view.dispose();
			tcpTm.stopPolling();
			udpTm.stopPolling();
		}
	}
	
	private void error(String title, String message) {
		JOptionPane.showMessageDialog(view, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	private static boolean checkIPFormat(String IP) {
		Pattern p = Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
		if (!p.matcher(IP).matches()) return false;
		return true;
	}
	
	public static void main(String[] args) {
		SnmpSession.loadMib2();
		new App();
	}
}