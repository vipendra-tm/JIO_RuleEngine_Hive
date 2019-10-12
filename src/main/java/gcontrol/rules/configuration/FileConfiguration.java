package gcontrol.rules.configuration;
/*
 * Ather Vipendra Kumar
 * FileConfiguration Model class 
 * It is contain the details of smtp information which is to be configured for email notification
 */
public class FileConfiguration {
	private String smtpHostName;

	/** SMTP host port number. */
	private String smtpHostPortNumber;

	/** SMTP host user name. */
	private String smtpHostUserName;

	/** SMTP host password. */
	private String smtpHostPassword;

	/** To recipient mail address for operator. */
	private String[] toRecipientMailAddress;

	/** cc recipient mail address for operator. */
	private String[] ccRecipientMailAddress;

	/** cc recipient mail address for operator. */
	private String[] bccRecipientMailAddress;
	
   /*
   *@return the smtpHostName
   */ 
	public String getSmtpHostName() {
		return smtpHostName;
	}
/*
 * @param set the smtpHostName
 */
	public void setSmtpHostName(String smtpHostName) {
		this.smtpHostName = smtpHostName;
	}
	/*
	*@return the smtpHostPortNumber
	*/ 
	public String getSmtpHostPortNumber() {
		return smtpHostPortNumber;
	}
	/*
	 * @param set the smtpHostPortNumber
	 */
	public void setSmtpHostPortNumber(String smtpHostPortNumber) {
		this.smtpHostPortNumber = smtpHostPortNumber;
	}
	/*
	*@return the smtpHostUserName
	*/ 
	public String getSmtpHostUserName() {
		return smtpHostUserName;
	}
	/*
	 * @param set the smtpHostUserName
	 */
	public void setSmtpHostUserName(String smtpHostUserName) {
		this.smtpHostUserName = smtpHostUserName;
	}
	/*
	*@return the smtpHostPassword
	*/ 
	public String getSmtpHostPassword() {
		return smtpHostPassword;
	}
	/*
	 * @param set the smtpHostPassword
	 */
	public void setSmtpHostPassword(String smtpHostPassword) {
		this.smtpHostPassword = smtpHostPassword;
	}
	/*
	*@return the toRecipientMailAddress
	*/ 
	public String[] getToRecipientMailAddress() {
		return toRecipientMailAddress;
	}
	/*
	 * @param set the toRecipientMailAddress
	 */
	public void setToRecipientMailAddress(String[] toRecipientMailAddress) {
		this.toRecipientMailAddress = toRecipientMailAddress;
	}
	/*
	*@return the ccRecipientMailAddress
	*/ 
	public String[] getCcRecipientMailAddress() {
		return ccRecipientMailAddress;
	}
	/*
	 * @param set the ccRecipientMailAddress
	 */
	public void setCcRecipientMailAddress(String[] ccRecipientMailAddress) {
		this.ccRecipientMailAddress = ccRecipientMailAddress;
	}
	/*
	*@return the bccRecipientMailAddress
	*/ 
	public String[] getBccRecipientMailAddress() {
		return bccRecipientMailAddress;
	}
	/*
	 * @param set the bccRecipientMailAddress
	 */
	public void setBccRecipientMailAddress(String[] bccRecipientMailAddress) {
		this.bccRecipientMailAddress = bccRecipientMailAddress;
	}

	
}
