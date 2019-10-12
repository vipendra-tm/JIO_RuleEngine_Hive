package gcontrol.rule.model;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
/*
 * Auther Vipendra Kumar
 * DataModel Model class 
 * It is contain the details of data information which is to be validated from Rule Engine
 */
public class DataModel {
/* 
 * Variables declaration are used to find the data plans 
 */
	private String imsi;
	private String imei;
	private BigDecimal totalDownbytes;
	private BigDecimal totalUpbytes;
	private BigDecimal totalBytes;
	private BigDecimal totalBytesinkb;
	//private BigDecimal totalSessionCount;
	private Timestamp creationTime;
	private Double baseLimit;
	private Double overageLimit;
	private Validation validation = Validation.UNKNOWN;
	
	private static final ScriptEngineManager mgr = new ScriptEngineManager();
	private static final  ScriptEngine engine = mgr.getEngineByName("JavaScript");
	/**
	 * @return the imsi
	 */

	public String getImsi() {
		return imsi;
	}

	/**
	 * @param set the ruleId parameter
	 */

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	/**
	 * @return the imei
	 */

	public String getImei() {
		return imei;
	}

	/**
	 * @param set the imei parameter
	 */

	public void setImei(String imei) {
		this.imei = imei;
	}
	/**
	 * @return the totalDownbytes
	 */


	public BigDecimal getTotalDownbytes() {
		return totalDownbytes;
	}

	/**
	 * @param set the totalDownbytes parameter
	 */

	public void setTotalDownbytes(BigDecimal totalDownbytes) {
		this.totalDownbytes = totalDownbytes;
	}

	/**
	 * @return the totalUpbytes
	 */

	public BigDecimal getTotalUpbytes() {
		return totalUpbytes;
	}
	/**
	 * @param set the totalUpbytes parameter
	 */


	public void setTotalUpbytes(BigDecimal totalUpbytes) {
		this.totalUpbytes = totalUpbytes;
	}

	/**
	 * @return the totalBytes
	 */

	public BigDecimal getTotalBytes() {
		return totalBytes;
	}

	/**
	 * @param set the totalBytes parameter
	 */

	public void setTotalBytes(BigDecimal totalBytes) {
		this.totalBytes = totalBytes;
	}

	/**
	 * @return the totalBytesinkb
	 */

	public BigDecimal getTotalBytesinkb() {
		return totalBytesinkb;
	}

	/**
	 * @param set the totalBytesinkb parameter
	 */

	public void setTotalBytesinkb(BigDecimal totalBytesinkb) {
		this.totalBytesinkb = totalBytesinkb;
	}


	/**
	 * @return the totalSessionCount
	 */
	//public BigDecimal getTotalSessionCount() {
	//	return totalSessionCount;
	//}

	/**
	 * @param set the totalSessionCount parameter
	 */

	//public void setTotalSessionCount(BigDecimal totalSessionCount) {
	//	this.totalSessionCount = totalSessionCount;
	//}

	/**
	 * @return the creationTime
	 */

	public Timestamp getCreationTime() {
		return creationTime;
	}

	/**
	 * @param set the creationTime parameter
	 */

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

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
	 * @return the baseLimit
	 */


	public Double getBaseLimit() {
		return baseLimit;
	}

	/**
	 * @param set the baseLimit parameter
	 */

	public void setBaseLimit(Double baseLimit) {
		this.baseLimit = baseLimit;
	}
	
	
	/**
	 * @return the overageLimit
	 */

	public Double getOverageLimit() {
		return overageLimit;
	}

	/**
	 * @param set the overageLimit parameter
	 */

	public void setOverageLimit(Double overageLimit) {
		this.overageLimit = overageLimit;
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
 * toString() method is used to show the actual data of DataModel 
 * class after applying the setter method such as such above declared  
 */
	@Override
	public String toString() {
		return "DataModel [imsi=" + imsi + ", imei=" + imei + ", totalDownbytes=" + totalDownbytes + ", totalUpbytes="
				+ totalUpbytes + ", totalBytes=" + totalBytes + ", totalBytesinkb=" + totalBytesinkb
				+ ", creationTime=" + creationTime + ", baseLimit="
				+ baseLimit + ", overageLimit=" + overageLimit + ", validation=" + validation + "]";
	}






	
}
