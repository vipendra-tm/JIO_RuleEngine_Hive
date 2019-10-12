package gcontrol.rule.model;

import java.util.Date;
/*
 * Auther Vipendra Kumar
 * InvalidRules Model class 
 * It is used to validate fail data from Rule Engine
 */
public class InvalidRules {

	/* 
	 * Variables declaration are used to find the InvalidRules data
	 */
	private String imsi;
	private int ruleId;
	private Date occurenceTime;
	private Validation validation;
	
	/**
	 * @return the validation
	 */
	
	public Validation getValidation() {
		return validation;
	}
	/**
	 * @param set the validation parameter
	 */
	public void setValidation(Validation validation) {
		this.validation = validation;
	}
	/**
	 * @return the imsi
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
	 * @return the occurenceTime
	 */
	public Date getOccurenceTime() {
		return occurenceTime;
	}
	/**
	 * @param set the occurenceTime parameter
	 */
	public void setOccurenceTime(Date occurenceTime) {
		this.occurenceTime = occurenceTime;
	}

	/*
	 * equals() method return boolean condition whether InvalidRules are true or false after applying input parameter
	 */
	@Override
	public boolean equals(Object obj) {
		InvalidRules ivr =(InvalidRules) obj;
		
		if(ivr .getImsi().equals(this.imsi)&& ivr .getRuleId()==this.ruleId ) {
			return true;
		}
		return false;
	}
	/*
	 * toString() method is used to show the actual data values of InvalidRules 
	 * class after applying the setter method such as such above declared  
	 */
	@Override
	public String toString() {
		return "InvalidRules [imsi=" + imsi + ", ruleId=" + ruleId + ", occurenceTime=" + occurenceTime
				+ ", validation=" + validation + "]";
	}
	
	
}
