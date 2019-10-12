package gcontrol.rule.model;
/*
 * Auther Vipendra Kumar
 * RuleAction Model class 
 * It is performed the action against Pass/Fail condition when validated from the Rule Engine
 */
public class RuleAction {
	/* 
	 * Variables declaration are used to find the data of rule action
	 */
	private Integer actionId;
	private Integer ruleId;
	private Integer actionTypeId;
	private String emailId;
	private String contactNumber;
	private String actionSubject;
	private String actionDescription;
	private Integer changedServicePlaneId;
	private Integer changedCoverageId;
	private String stateChange;
	private String actionType;
	
	/*
	 * @return the actionId
	 */
	
	public Integer getActionId() {
		return actionId;
	}

/*
 * @param set the actionId
 */

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	/*
	 * @return the ruleId
	 */

	public Integer getRuleId() {
		return ruleId;
	}

	/*
	 * @param set the ruleId
	 */

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	/*
	 * @return the actionTypeId
	 */

	public Integer getActionTypeId() {
		return actionTypeId;
	}

	/*
	 * @param set the actionTypeId
	 */

	public void setActionTypeId(Integer actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	/*
	 * @return the emailId
	 */

	public String getEmailId() {
		return emailId;
	}

	/*
	 * @param set the emailId
	 */

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/*
	 * @return the contactNumber
	 */

	public String getContactNumber() {
		return contactNumber;
	}

	/*
	 * @param set the contactNumber
	 */

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/*
	 * @return the actionSubject
	 */

	public String getActionSubject() {
		return actionSubject;
	}

	/*
	 * @param set the actionSubject
	 */

	public void setActionSubject(String actionSubject) {
		this.actionSubject = actionSubject;
	}
	/*
	 * @return the actionDescription
	 */


	public String getActionDescription() {
		return actionDescription;
	}

	/*
	 * @param set the actionDescription
	 */

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	/*
	 * @return the changedServicePlaneId
	 */

	public Integer getChangedServicePlaneId() {
		return changedServicePlaneId;
	}

	/*
	 * @param set the changedServicePlaneId
	 */

	public void setChangedServicePlaneId(Integer changedServicePlaneId) {
		this.changedServicePlaneId = changedServicePlaneId;
	}

	/*
	 * @return the changedCoverageId
	 */

	public Integer getChangedCoverageId() {
		return changedCoverageId;
	}

	/*
	 * @param set the changedCoverageId
	 */

	public void setChangedCoverageId(Integer changedCoverageId) {
		this.changedCoverageId = changedCoverageId;
	}
	/*
	 * @return the stateChange
	 */
	public String getStateChange() {
		return stateChange;
	}
	/*
	 * @param set the stateChange
	 */
	public void setStateChange(String stateChange) {
		this.stateChange = stateChange;
	}
	/*
	 * @return the actionType
	 */

	public String getActionType() {
		return actionType;
	}

	/*
	 * @param set the actionType
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/*
	 * toString() method is used to show the actual data values of RuleAction 
	 * class after applying the setter method such as such above declared  
	 */

	@Override
	public String toString() {
		return "RuleAction [actionId=" + actionId + ", ruleId=" + ruleId + ", actionTypeId=" + actionTypeId
				+ ", emailId=" + emailId + ", contactNumber=" + contactNumber + ", actionSubject=" + actionSubject
				+ ", actionDescription=" + actionDescription + ", changedServicePlaneId=" + changedServicePlaneId
				+ ", changedCoverageId=" + changedCoverageId + ", actionType=" + actionType + "]";
	}
	
	
}
