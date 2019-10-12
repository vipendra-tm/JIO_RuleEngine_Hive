package gcontrol.rule.model;

import java.util.Date;
/*
 * Auther Vipendra Kumar
 * ValidateRule Model class 
 * It is used to validate pass data from Rule Engine
 */
public class ValidateRule 
{
   /*
    * variables declaration are used the find out the data after validated the rules.
    */

	private String imsi;
	private int ruleId;
	private Date occurenceTime;
	private Validation validation;
	
	/*
	 * @return the validation
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
	 * @return the ruleId
	 */
	public int getRuleId() {
		return ruleId;
	}
	/*
	 * @param set the ruleId
	 */
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	/*
	 * @return the occurenceTime
	 */
	
	public Date getOccurenceTime() {
		return occurenceTime;
	}
	/*
	 * @param set the occurenceTime
	 */
	public void setOccurenceTime(Date occurenceTime) {
		this.occurenceTime = occurenceTime;
	}
	/*
	 * equals() is used to check the boolean status object after applying the input parameter. 
	 */
	@Override
	public boolean equals(Object obj) {
		ValidateRule vr =(ValidateRule) obj;
		
		if(vr.getImsi().equals(this.imsi)&& vr.getRuleId()==this.ruleId ) {
			return true;
		}
		return false;
	}
	
	/*
	 * toString() method is used to show the actual data of ValidateRule 
	 * class after applying the setter method such as such above declared  
	 */
	
	@Override
	public String toString() {
		return "ValidateRule [imsi=" + imsi + ", ruleId=" + ruleId + ", occurenceTime=" + occurenceTime
				+ ", validation=" + validation + "]";
	}
	
	
	
	
}
