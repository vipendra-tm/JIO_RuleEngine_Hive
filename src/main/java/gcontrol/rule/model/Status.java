package gcontrol.rule.model;
/*
 * Auther Vipendra Kumar
 * Status Model class 
 * It is contain boolean status which is validated from Rule Engine for the further process.
 */
public class Status 
{
/*
 * Variable declaration are used to set the boolean flag
 */
	private Boolean startFlag=true;
	
	private Boolean nullFlag=false;
/*
 * @return startFlag
 */
	public Boolean getStartFlag() {
		return startFlag;
	}
/*
 * @param set the startFlag
 */
	public void setStartFlag(Boolean startFlag) {
		this.startFlag = startFlag;
	}
/*
 * @return the nullFlag
 */
	public Boolean getNullFlag() {
		return nullFlag;
	}
	/*
	 * @param set the nullFlag
	 */
	public void setNullFlag(Boolean nullFlag) {
		this.nullFlag = nullFlag;
	}
	/*
	 * toString() method is used to show the boolean status of Status 
	 * class after applying the setter method such as such above declared  
	 */
	@Override
	public String toString() {
		return "Status [startFlag=" + startFlag + ", nullFlag=" + nullFlag + "]";
	}
	
	
}
