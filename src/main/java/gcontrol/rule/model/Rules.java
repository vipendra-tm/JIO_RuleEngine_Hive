package gcontrol.rule.model;

import java.sql.Timestamp;
public class Rules
{
	/*
	 * Variables are defined to find the set of instruction rules to validating purpose.
	 */
	private int ruleId;
	private String ruleName;
	private int ruleCategory;
	private String ruleDescription;
	private int executionInterval;
	private String executionIntervalUnit;
	private String ruleCategoryName;
	private String ruleDefaultCondition;
	private String ruleParameter;
	private Timestamp ruleStartTime;	
	private Timestamp lastExecutionTime;
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
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}
	/*
	 * @param set the ruleName
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	/*
	 * @return the ruleCategory
	 */
	public int getRuleCategory() {
		return ruleCategory;
	}
	/*
	 * @param set the ruleCategory
	 */
	public void setRuleCategory(int ruleCategory) {
		this.ruleCategory = ruleCategory;
	}
	/*
	 * @return the executionInterval
	 */
	public int getExecutionInterval() {
		return executionInterval;
	}
	/*
	 * @param set the executionInterval
	 */
	public void setExecutionInterval(int executionInterval) {
		this.executionInterval = executionInterval;
	}
	/*
	 * @return the executionIntervalUnit
	 */
	public String getExecutionIntervalUnit() {
		return executionIntervalUnit;
	}
	/*
	 * @param set the executionIntervalUnit
	 */
	public void setExecutionIntervalUnit(String executionIntervalUnit) {
		this.executionIntervalUnit = executionIntervalUnit;
	}
	/*
	 * @return the ruleCategoryName
	 */
	public String getRuleCategoryName() {
		return ruleCategoryName;
	}
	/*
	 * @param set the ruleCategoryName
	 */
	public void setRuleCategoryName(String ruleCategoryName) {
		this.ruleCategoryName = ruleCategoryName;
	}
	/*
	 * @return the ruleDefaultCondition
	 */
	public String getRuleDefaultCondition() {
		return ruleDefaultCondition;
	}
	/*
	 * @param set the ruleDefaultCondition
	 */
	public void setRuleDefaultCondition(String ruleDefaultCondition) {
		this.ruleDefaultCondition = ruleDefaultCondition;
	}
	/*
	 * @return the ruleParameter
	 */
	public String getRuleParameter() {
		return ruleParameter;
	}
	/*
	 * @param set the ruleParameter
	 */
	public void setRuleParameter(String ruleParameter) {
		this.ruleParameter = ruleParameter;
	}
	/*
	 * @return the ruleStartTime
	 */
	public Timestamp getRuleStartTime() {
		return ruleStartTime;
	}
	/*
	 * @param set the ruleStartTime
	 */
	public void setRuleStartTime(Timestamp ruleStartTime) {
		this.ruleStartTime = ruleStartTime;
	}
	
	/*
	 * @return the lastExecutionTime
	 */	

	public Timestamp getLastExecutionTime() {
		return lastExecutionTime;
	}
	/*
	 * @param set the lastExecutionTime
	 */
	public void setLastExecutionTime(Timestamp lastExecutionTime) {
		this.lastExecutionTime = lastExecutionTime;
	}
	/*
	 * @return the ruleDescription
	 */	
	public String getRuleDescription() {
		return ruleDescription;
	}
	/*
	 * @param set the ruleDescription
	 */
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	/*
	 * toString() method is used to show the actual data values of Rules 
	 * class after applying the setter method such as such above declared  
	 */
	@Override
	public String toString() {
		return "Rules [ruleId=" + ruleId + ", ruleName=" + ruleName + ", ruleCategory=" + ruleCategory+ ", ruleDescription=" + ruleDescription
				+ ", executionInterval=" + executionInterval + ", executionIntervalUnit=" + executionIntervalUnit
				+ ", ruleCategoryName=" + ruleCategoryName + ", ruleDefaultCondition=" + ruleDefaultCondition
				+ ", ruleParameter=" + ruleParameter + ", ruleStartTime=" + ruleStartTime + ", lastExecutionTime="
				+ lastExecutionTime + "]";
	}

	
	
	
	
}
