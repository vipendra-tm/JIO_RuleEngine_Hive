package gcontrol.rule.model;

import java.math.BigInteger;
/*
 * Auther Vipendra Kumar
 * MessageVo Model class 
 * It is performed the action against alert history when validated condition from the Rule Engine
 */
public class MessageVo
{
private BigInteger code;
private String message;
/*
 * @return the code
 */
public BigInteger getCode() {
	return code;
}
/*
 * @param set the code
 */
public void setCode(BigInteger code) {
	this.code = code;
}
/*
 * @return the message
 */
public String getMessage() {
	return message;
}
/*
 * @param set the message
 */
public void setMessage(String message) {
	this.message = message;
}
}
