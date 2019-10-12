package gcontrol.rule.model;

import java.sql.Timestamp;
/*
 * Auther Vipendra Kumar
 * ActiveAlert Model class 
 * It is contain the details of alert information which is to be validated Rule Engine for generating alert.
 */
public class ActiveAlert
{
	/**
	 * Variable declaration required for alert process.
	 */
	private int id;
	private String imsi;
	private int ruleId;
	private String ruleCategory;
	private Timestamp occurenceTime;
	private int isCleared;
	/**
	 * @return the Id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param set the Id parameter
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the Imsi
	 */
	public String getImsi() {
		return imsi;
	}
	/**
	 * @param set the imsi parameter
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	/**
	 * @return the ruleId
	 */
	public int getRuleId() {
		return ruleId;
	}
	/**
	 * @param set the ruleId parameter
	 */
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	/**
	 * @return the ruleCategory
	 */
	public String getRuleCategory() {
		return ruleCategory;
	}
	/**
	 * @param set the ruleCategory parameter
	 */
	public void setRuleCategory(String ruleCategory) {
		this.ruleCategory = ruleCategory;
	}
	/**
	 * @return the occurenceTime
	 */
	public Timestamp getOccurenceTime() {
		return occurenceTime;
	}
	/**
	 * @param set the occurenceTime parameter
	 */
	public void setOccurenceTime(Timestamp occurenceTime) {
		this.occurenceTime = occurenceTime;
	}
	/**
	 * @return the isCleared
	 */
	public int getIsCleared() {
		return isCleared;
	}
	/**
	 * @param set the isCleared parameter
	 */
	public void setIsCleared(int isCleared) {
		this.isCleared = isCleared;
	}
	/*
	 * toString() method is used to show the actual data of ActiveAlert 
	 * class after applying the setter method such as such above declared  
	 */
	@Override
	public String toString() {
		return "ActiveAlert [id=" + id + ", imsi=" + imsi + ", ruleId=" + ruleId + ", ruleCategory=" + ruleCategory
				+ ", occurenceTime=" + occurenceTime + ", isCleared=" + isCleared + "]";
	}

	
}
