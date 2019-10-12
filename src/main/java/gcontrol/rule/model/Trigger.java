package gcontrol.rule.model;
/*
 * Auther Vipendra Kumar
 * Trigger Model class 
 * It is contains the details of trigger information which is used to fire trigger
 */
public class Trigger
{
	/*
	 * Variables declaration are used to find out the trigger values to validating rules
	 */
	private int ruleId;
	private int triggerId;
	private String ruleCategoryName;
	private String ruleStatus;
	private String ruleDefaultCondition;
	private String ruleParameter;
	private String ruleComparator;
	private int ruleConditionValue;
	private String ruleCondition;
	private int ruleOccurrenceCount;
	private String ruleTriggerIntervalUnit;
	private int ruleTriggerIntervalUnitValue;
	private String ruleComparatorsymbol;
	
	/*
	 * return the ruleComparatorsymbol
	 */
	
	public String getRuleComparatorsymbol() {
		return ruleComparatorsymbol;
	}
	/*
	 * @param set the ruleComparatorsymbol
	 */
	public void setRuleComparatorsymbol(String ruleComparatorsymbol) {
		this.ruleComparatorsymbol = ruleComparatorsymbol;
	}
	/*
	 * return the triggerId
	 */
	public int getTriggerId() {
		return triggerId;
	}
	/*
	 * @param set the triggerId
	 */
	public void setTriggerId(int triggerId) {
		this.triggerId = triggerId;
	}
	/*
	 * return the ruleId
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
	 * return the ruleCategoryName
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
	 * return the ruleStatus
	 */
	public String getRuleStatus() {
		return ruleStatus;
	}
	/*
	 * @param set the ruleStatus
	 */
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}
	/*
	 * return the ruleDefaultCondition
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
	 * return the ruleParameter
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
	 * return the ruleComparator
	 */
	public String getRuleComparator() {
		return ruleComparator;
	}
	/*
	 * @param set the ruleComparator
	 */
	public void setRuleComparator(String ruleComparator) {
		this.ruleComparator = ruleComparator;
	}
	/*
	 * return the ruleConditionValue
	 */
	public int getRuleConditionValue() {
		return ruleConditionValue;
	}
	/*
	 * @param set the ruleConditionValue
	 */
	public void setRuleConditionValue(int ruleConditionValue) {
		this.ruleConditionValue = ruleConditionValue;
	}
	/*
	 * return the ruleComparatorsymbol
	 */
	public String getRuleCondition() {
		return ruleCondition;
	}
	/*
	 * @param set the ruleCondition
	 */
	public void setRuleCondition(String ruleCondition) {
		this.ruleCondition = ruleCondition;
	}
	/*
	 * return the ruleOccurrenceCount
	 */
	public int getRuleOccurrenceCount() {
		return ruleOccurrenceCount;
	}
	/*
	 * @param set the ruleOccurrenceCount
	 */
	public void setRuleOccurrenceCount(int ruleOccurrenceCount) {
		this.ruleOccurrenceCount = ruleOccurrenceCount;
	}
	/*
	 * return the ruleTriggerIntervalUnit
	 */	
	public String getRuleTriggerIntervalUnit() {
		return ruleTriggerIntervalUnit;
	}
	/*
	 * @param set the ruleTriggerIntervalUnit
	 */
	public void setRuleTriggerIntervalUnit(String ruleTriggerIntervalUnit) {
		this.ruleTriggerIntervalUnit = ruleTriggerIntervalUnit;
	}
	/*
	 * return the ruleTriggerIntervalUnitValue
	 */
	public int getRuleTriggerIntervalUnitValue() {
		return ruleTriggerIntervalUnitValue;
	}
	/*
	 * @param set the ruleTriggerIntervalUnitValue
	 */
	public void setRuleTriggerIntervalUnitValue(int ruleTriggerIntervalUnitValue) {
		this.ruleTriggerIntervalUnitValue = ruleTriggerIntervalUnitValue;
	}
	/*
	 * toString() method is used to show the actual data of Trigger 
	 * class after applying the setter method such as such above declared  
	 */
	@Override
	public String toString() {
		return "Trigger [ruleId=" + ruleId + ", triggerId=" + triggerId + ", ruleCategoryName=" + ruleCategoryName
				+ ", ruleStatus=" + ruleStatus + ", ruleDefaultCondition=" + ruleDefaultCondition + ", ruleParameter="
				+ ruleParameter + ", ruleComparator=" + ruleComparator + ", ruleConditionValue=" + ruleConditionValue
				+ ", ruleCondition=" + ruleCondition + ", ruleOccurrenceCount=" + ruleOccurrenceCount
				+ ", ruleTriggerIntervalUnit=" + ruleTriggerIntervalUnit + ", ruleTriggerIntervalUnitValue="
				+ ruleTriggerIntervalUnitValue + ", ruleComparatorsymbol=" + ruleComparatorsymbol + "]";
	}




}
