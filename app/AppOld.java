package app;

import java.awt.GraphicsConfiguration;
import view.View;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class App {
	
	private View view;
	private Session session;
	private Updater updater;
	private State state;
	
	public App() {
		ActionListener goListener = ae -> { go(); };
		ActionListener backListener = ae -> { back(); };
		view = new View(goListener, backListener);
		state = new HomeState();
	}
	public synchronized void go() { state.go(); }
	public synchronized void back() { state.back(); }
	
	private class Updater extends Thread {
		private int period;
		private boolean finished = false;
		public Updater(int period) { this.period = period;	}
		@Override
		public void run() {
			try {
				while (true) {
					String[][] tcpData = session.getTCPData();
					String[][] udpData = session.getUDPData();
					view.updateData(tcpData, udpData);
					Thread.sleep(period * 1000);
				}
			}
			catch(InterruptedException ie) {}
			catch(ErrorSessionFailed esf) {}
			synchronized(this) {
				finished = true;
				notifyAll();
			}
		}
		public synchronized void waitFinish() {
			while (!finished) try { wait(); } catch (InterruptedException e) {}
		}
	}
	
	private abstract class State {
		public abstract void go();
		public abstract void back();
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
			} catch (ErrorSessionFailed e) { error("Greška", "Ruter sa unetom IP adresom nije pronadjen"); return; }
			
			state = new DataState();
			view.showData(name);
			updater = new Updater(period);
			updater.start();
		}
		public void back() { return; }
	}
	
	private class DataState extends State {
		public void go() { return; }
		public void back() {
			updater.interrupt();
			updater.waitFinish();
			updater = null; session = null;
			view.showHome();
			state = new HomeState();
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
		new App();
	}
}