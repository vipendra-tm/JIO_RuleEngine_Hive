package gcontrol.rule.model;

public class MappingModel
{
	private String stateChangeUrl;
	private String authenticationUrl;
	private String username;
	private String password;
	private String hive_driver;
	private String hive_url;
	private String hive_username;
	private String hive_password;
	public String getStateChangeUrl() {
		return stateChangeUrl;
	}
	public void setStateChangeUrl(String stateChangeUrl) {
		this.stateChangeUrl = stateChangeUrl;
	}
	public String getAuthenticationUrl() {
		return authenticationUrl;
	}
	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHive_driver() {
		return hive_driver;
	}
	public void setHive_driver(String hive_driver) {
		this.hive_driver = hive_driver;
	}
	public String getHive_url() {
		return hive_url;
	}
	public void setHive_url(String hive_url) {
		this.hive_url = hive_url;
	}
	public String getHive_username() {
		return hive_username;
	}
	public void setHive_username(String hive_username) {
		this.hive_username = hive_username;
	}
	public String getHive_password() {
		return hive_password;
	}
	public void setHive_password(String hive_password) {
		this.hive_password = hive_password;
	}
	@Override
	public String toString() {
		return "MappingModel [stateChangeUrl=" + stateChangeUrl + ", authenticationUrl=" + authenticationUrl
				+ ", username=" + username + ", password=" + password + ", hive_driver=" + hive_driver + ", hive_url="
				+ hive_url + ", hive_username=" + hive_username + ", hive_password=" + hive_password + "]";
	}
	
	
}
