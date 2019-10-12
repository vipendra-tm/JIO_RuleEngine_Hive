package gcontrol.rule.model;
/*
 * Auther Vipendra Kumar
 * EmeiData Model class 
 * It is contain the details of IMEI data information which is to be validated from Rule Engine
 */
public class EmeiData 
{
	/* 
	 * Variables declaration are used to find the IMEI tracking data
	 */
	private String IMSI;
	private String PREVIOUS_IMEI;
	private String CURRENT_IMEI;
	/**
	 * @return the IMSI
	 */
	public String getIMSI() {
		return IMSI;
	}
	/**
	 * @param set the IMSI parameter
	 */
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	/**
	 * @return the PREVIOUS_IMEI
	 */
	public String getPREVIOUS_IMEI() {
		return PREVIOUS_IMEI;
	}
	/**
	 * @param set the PREVIOUS_IMEI parameter
	 */
	public void setPREVIOUS_IMEI(String pREVIOUS_IMEI) {
		PREVIOUS_IMEI = pREVIOUS_IMEI;
	}
	/**
	 * @return the CURRENT_IMEI
	 */
	public String getCURRENT_IMEI() {
		return CURRENT_IMEI;
	}
	/**
	 * @param set the CURRENT_IMEI parameter
	 */
	public void setCURRENT_IMEI(String cURRENT_IMEI) {
		CURRENT_IMEI = cURRENT_IMEI;
	}
	@Override
	public String toString() {
		return "EmeiData [IMSI=" + IMSI + ", PREVIOUS_IMEI=" + PREVIOUS_IMEI + ", CURRENT_IMEI=" + CURRENT_IMEI + "]";
	}	
	
	
	
}
