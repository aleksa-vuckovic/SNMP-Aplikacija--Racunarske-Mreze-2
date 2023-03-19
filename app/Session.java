package app;

import java.io.IOException;

import com.ireasoning.protocol.snmp.SnmpPdu;
import com.ireasoning.protocol.snmp.SnmpSession;
import com.ireasoning.protocol.snmp.SnmpTableModel;
import com.ireasoning.protocol.snmp.SnmpTarget;
import com.ireasoning.protocol.snmp.SnmpVarBind;

public class Session {
	SnmpSession session;
	private static final int port = 161;
	
	public Session(String ip, String communityRead, String communityWrite) throws ErrorSessionFailed {
		SnmpTarget target = new SnmpTarget(ip, port, communityRead, communityWrite, 1);
		try {
			session = new SnmpSession(target);
		} catch (IOException e) { throw new ErrorSessionFailed(); }
	}
	
	public SnmpTableModel getTCP() throws ErrorSessionFailed {
		try {
			return session.snmpGetTable(".1.3.6.1.2.1.6.13");
		} catch (IOException e) { throw new ErrorSessionFailed(); }
	}
	public SnmpTableModel getUDP() throws ErrorSessionFailed {
		try {
			return session.snmpGetTable(".1.3.6.1.2.1.7.5");
		} catch (IOException e) { throw new ErrorSessionFailed(); }
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
class ErrorSessionFailed extends Exception {}