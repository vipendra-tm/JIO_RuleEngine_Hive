package gcontrol.rule.model;

public class MsModel 
{
private String IMSI;

public String getIMSI() {
	return IMSI;
}

public void setIMSI(String iMSI) {
	IMSI = iMSI;
}

@Override
public String toString() {
	return "MsModel [IMSI=" + IMSI + "]";
}

}
