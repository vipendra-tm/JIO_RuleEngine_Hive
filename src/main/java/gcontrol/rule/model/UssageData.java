package gcontrol.rule.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
/*
 * Auther Vipendra Kumar
 * UssageData Model class 
 * It is contain the details of usage data information which is to be validated from Rule Engine
 */
public class UssageData 
{
	/*
	 * Variables declaration are used to find out uses of data.
	 */
	private String imsi;
	private String imei;
	private BigDecimal totalDownbytes;
	private BigDecimal totalUpbytes;
	private BigDecimal totalBytes;
	private BigDecimal totalBytesinkb;
	private BigDecimal totalSessionCount;
	private Timestamp creationTime;
	private BigDecimal baseLimit;
	private BigDecimal overageLimit;
	private Validation validation = Validation.UNKNOWN;
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
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}
	/*
	 * @param set the imei
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}
	/*
	 * @return the totalDownbytes
	 */
	public BigDecimal getTotalDownbytes() {
		return totalDownbytes;
	}
	/*
	 * @param set the totalDownbytes
	 */
	public void setTotalDownbytes(BigDecimal totalDownbytes) {
		this.totalDownbytes = totalDownbytes;
	}
	/*
	 * @return the totalUpbytes
	 */
	public BigDecimal getTotalUpbytes() {
		return totalUpbytes;
	}
	/*
	 * @param set the totalUpbytes
	 */
	public void setTotalUpbytes(BigDecimal totalUpbytes) {
		this.totalUpbytes = totalUpbytes;
	}
	/*
	 * @return the totalBytes
	 */
	public BigDecimal getTotalBytes() {
		return totalBytes;
	}
	/*
	 * @param set the totalBytes
	 */
	public void setTotalBytes(BigDecimal totalBytes) {
		this.totalBytes = totalBytes;
	}
	/*
	 * @return the totalBytesinkb
	 */
	public BigDecimal getTotalBytesinkb() {
		return totalBytesinkb;
	}
	/*
	 * @param set the totalBytesinkb
	 */
	public void setTotalBytesinkb(BigDecimal totalBytesinkb) {
		this.totalBytesinkb = totalBytesinkb;
	}
	/*
	 * @return the totalSessionCount
	 */
	public BigDecimal getTotalSessionCount() {
		return totalSessionCount;
	}
	/*
	 * @param set the totalSessionCount
	 */
	public void setTotalSessionCount(BigDecimal totalSessionCount) {
		this.totalSessionCount = totalSessionCount;
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
	 * @return the baseLimit
	 */
	public BigDecimal getBaseLimit() {
		return baseLimit;
	}
	/*
	 * @param set the baseLimit
	 */
	public void setBaseLimit(BigDecimal baseLimit) {
		this.baseLimit = baseLimit;
	}
	/*
	 * @return the overageLimit
	 */
	public BigDecimal getOverageLimit() {
		return overageLimit;
	}
	/*
	 * @param set the overageLimit
	 */
	public void setOverageLimit(BigDecimal overageLimit) {
		this.overageLimit = overageLimit;
	}
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
	 * toString() method is used to show the actual data of UssageData 
	 * class after applying the setter method such as such above declared  
	 */
	@Override
	public String toString() {
		return "UssageData [imsi=" + imsi + ", imei=" + imei + ", totalDownbytes=" + totalDownbytes + ", totalUpbytes="
				+ totalUpbytes + ", totalBytes=" + totalBytes + ", totalBytesinkb=" + totalBytesinkb
				+ ", totalSessionCount=" + totalSessionCount + ", creationTime=" + creationTime + ", baseLimit="
				+ baseLimit + ", overageLimit=" + overageLimit + ", validation=" + validation + "]";
	}	

}
