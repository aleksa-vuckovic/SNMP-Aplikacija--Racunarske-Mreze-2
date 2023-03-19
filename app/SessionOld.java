package app;

import java.io.IOException;

import com.ireasoning.protocol.snmp.*;

public class Session {
	SnmpSession session;
	private static final int port = 161;
	
	public Session(String ip, String communityRead, String communityWrite) throws ErrorSessionFailed {
		SnmpTarget target = new SnmpTarget(ip, port, communityRead, communityWrite, 1);
		try {
			session = new SnmpSession(target);
		} catch (IOException e) { throw new ErrorSessionFailed(); }
		
	}
	
	public String[][] getTCPData() throws ErrorSessionFailed {
		SnmpVarBind[] arr;
		try {
			arr = session.snmpGetSubtree(".1.3.6.1.2.1.6.13");
		} catch (IOException e) { throw new ErrorSessionFailed(); }
		int len = arr.length / 5;
		String[][] res = new String[len][4];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < 4; j++) {
				int k = (j + 1) * len + i;
				int tmp = 0;
				if (j == 1) tmp = 1;
				else if (j == 2) tmp = -1;
				res[i][j + tmp] = arr[k].getValue().toString();
			}
		}
		return res;
	}
	public String[][] getUDPData() throws ErrorSessionFailed {
		SnmpVarBind[] arr;
		try {
			arr = session.snmpGetSubtree(".1.3.6.1.2.1.7.5");
		} catch (IOException e) { throw new ErrorSessionFailed(); }
		int len = arr.length / 2;
		String[][] res = new String[len][2];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < 2; j++) {
				int k = j * len + i;
				res[i][j] = arr[k].getValue().toString();
			}
		}
		return res;
	}
	public String getHostName() throws ErrorSessionFailed {
		SnmpPdu t;
		try {
			t = session.snmpGetRequest(".1.3.6.1.2.1.1.5.0");
		} catch (IOException e) { throw new ErrorSessionFailed(); }
		return t.getFirstVarBind().getValue().toString();
	}
	@Override
	public void finalize() {
		if (session != null) { session.close(); session = null; }
	}
}

class ErrorSessionFailed extends Exception {
	
}