package gcontrol.rule.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
/*
 * Auther Vipendra Kumar
 * SMSModel Model class 
 * It is contain the details of SMS data information which is validated from Rule Engine
 */
public class SMSModel {
	/* 
	 * Variables declaration are used to find the sms plans 
	 */
	private String imsi;
	private String msisdn;
	private String smsType;
	private String source;
	private String destination;
	private BigDecimal smsTypeCount;
	private Timestamp creationTime;
	private Validation validation = Validation.UNKNOWN;
	

	private static final ScriptEngineManager mgr = new ScriptEngineManager();
	private static final  ScriptEngine engine = mgr.getEngineByName("JavaScript");
  
/*
 * @return validation
 */
	public Validation getValidation() {
		return validation;
	}
	/*
	 * @param set the validation
	 */
	public void setValidation(Validation validation) {
		this.validation = validation;
	}
	/*
	 * @return the imsi
	 */
	public String getImsi() {
		return imsi;
	}
	/*
	 * @param set the imsi
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi.trim();
	}
	/*
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/*
	 * @param set the msisdn
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	/*
	 * @return the smsType
	 */
	
	public String getSmsType() {
		return smsType;
	}
	/*
	 * @param set the smsType
	 */
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	/*
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/*
	 * @param set the source
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/*
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/*
	 * @param set the destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	/*
	 * @return the smsTypeCount
	 */
	
	public BigDecimal getSmsTypeCount() {
		return smsTypeCount;
	}
	/*
	 * @param set the smsTypeCount
	 */
	public void setSmsTypeCount(BigDecimal smsTypeCount) {
		this.smsTypeCount = smsTypeCount;
	}
	/*
	 * @return the creationTime
	 */
	public Timestamp getCreationTime() {
		return creationTime;
	}
	/*
	 * @param set the creationTime
	 */
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}
	/*
	 * conditionStatus() is used to check the boolean status of the input parameter. 
	 */
public boolean conditionStatus(String conditionScript) {
		
	    System.out.println("condition string:"+conditionScript);
	    try {
			 return Boolean.valueOf(engine.eval(conditionScript).toString());
		} catch (ScriptException e) {
			
			e.printStackTrace();
			return false;
		}
	}
/*
 * toString() method is used to show the actual data values of SMSModel 
 * class after applying the setter method such as such above declared  
 */
	@Override
	public String toString() {
		return "SMSModel [imsi=" + imsi + ", msisdn=" + msisdn + ", smsType=" + smsType + ", source=" + source
				+ ", destination=" + destination + ", smsTypeCount=" + smsTypeCount + ", creationTime=" + creationTime
				+ ", validation=" + validation + "]";
	}
	
	
	
	
	

	
	
}
