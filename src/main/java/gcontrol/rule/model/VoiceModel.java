package gcontrol.rule.model;
import java.math.BigInteger;
import java.sql.Timestamp;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
/*
 * Other Vipendra Kumar
 * VoiceModel Model class 
 * It is contain the details of voice data information which is to be validated from Rule Engine
 */
public class VoiceModel {

	/* 
	 * Variables declaration are used to find the voice plans 
	 */
	private String imsi;
	private String  calledimsi;
	private String  callingimsi;
	private BigInteger  callduration;
	private Timestamp  creationTime;
	private Integer  voiceEventType;
	private BigInteger  voiceTypeCount;

	private static final ScriptEngineManager mgr = new ScriptEngineManager();
	private static final  ScriptEngine engine = mgr.getEngineByName("JavaScript");
  
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
		this.imsi = imsi;
	}
	/*
	 * @return the calledimsi
	 */
	public String getCalledimsi() {
		return calledimsi;
	}
	/*
	 * @param set the calledimsi
	 */
	public void setCalledimsi(String calledimsi) {
		this.calledimsi = calledimsi;
	}
	/*
	 * @return the callingimsi
	 */
	public String getCallingimsi() {
		return callingimsi;
	}
	/*
	 * @param set the callingimsi
	 */
	public void setCallingimsi(String callingimsi) {
		this.callingimsi = callingimsi;
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
	 * @return the voiceEventType
	 */
	
	public Integer getVoiceEventType() {
		return voiceEventType;
	}
	/*
	 * @param set the voiceEventType
	 */
	public void setVoiceEventType(Integer voiceEventType) {
		this.voiceEventType = voiceEventType;
	}
	/*
	 * @return the callduration
	 */
	public BigInteger getCallduration() {
		return callduration;
	}
	/*
	 * @param set the callduration
	 */
	public void setCallduration(BigInteger callduration) {
		this.callduration = callduration;
	}
	/*
	 * @return the voiceTypeCount
	 */
	public BigInteger getVoiceTypeCount() {
		return voiceTypeCount;
	}
	/*
	 * @param set the voiceTypeCount
	 */
	public void setVoiceTypeCount(BigInteger voiceTypeCount) {
		this.voiceTypeCount = voiceTypeCount;
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
	 * toString() method is used to show the actual data of VoiceModel 
	 * class after applying the setter method such as such above declared  
	 */
	@Override
	public String toString() {
		return "VoiceModel [imsi=" + imsi + ", calledimsi=" + calledimsi + ", callingimsi=" + callingimsi
				+ ", callduration=" + callduration + ", creationTime=" + creationTime + ", voiceEventType="
				+ voiceEventType + ", voiceTypeCount=" + voiceTypeCount + "]";
	}

	
	


	

	

	
	
}
