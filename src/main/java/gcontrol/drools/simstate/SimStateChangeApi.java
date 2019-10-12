package gcontrol.drools.simstate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * @Author Vipendra Kumar
 * @Objective this would be generated the status of SIM Change state from rule engine.
 */
public class SimStateChangeApi 
{
private String stateChangeUrl;
private String authenticationUrl;
private String username;
private String password;

public SimStateChangeApi() 
{
    File file = new File(System.getenv("Rule_Engine")+"/template/API_Mapping.properties");
	Properties property=new Properties();
	try {
		InputStream in=new FileInputStream(file);
		property.load(in);
		in.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
authenticationUrl=property.getProperty("rule.authentication_api_url");	
stateChangeUrl=property.getProperty("rule.sim_state_change_api");
username=property.getProperty("rule.username");
password=property.getProperty("rule.password");
System.out.println("AuthenticationUrl::"+authenticationUrl);
System.out.println("StateChangeUrl::"+stateChangeUrl);
}
/*
 * @return the stateChangeUrl
 */
public String getStateChangeUrl() {
	return stateChangeUrl;
}
/*
 * @param set the stateChangeUrl
 */
public void setStateChangeUrl(String stateChangeUrl) {
	this.stateChangeUrl = stateChangeUrl;
}
/*
 * @return the authenticationUrl
 */
public String getAuthenticationUrl() {
	return authenticationUrl;
}
/*
 * @param set the authenticationUrl
 */
public void setAuthenticationUrl(String authenticationUrl) {
	this.authenticationUrl = authenticationUrl;
}
/*
 * @return the username
 */
public String getUsername() {
	return username;
}
/*
 * @param set the username
 */
public void setUsername(String username) {
	this.username = username;
}
/*
 * @return the password
 */
public String getPassword() {
	return password;
}
/*
 * @param set the password
 */
public void setPassword(String password) {
	this.password = password;
}

/*
 * This  method give response status about the SIM state change condition 
 * after passing @param url
 * @param:AuthToken
 * @param:chgMsi
 * @param: statusChg
  */
public Map<String,Object> SimStateChangeResponse(String AuthToken,List<String> chgMsi,String statusChg)
{
	 Map<String,Object> updStatusVal= new HashMap<String,Object>();	 
	 HttpURLConnection conn=null;	
     try
     {
       //make connection 
   	   URL url = new URL(getStateChangeUrl().trim());
   	   conn = (HttpURLConnection) url.openConnection();
	   conn.setDoOutput(true);
	   conn.setRequestMethod("PUT");
       //It Content Type is so important to support JSON call
       conn.setRequestProperty("Authorization", AuthToken);
       conn.setRequestProperty("Accept", "application/json");
       conn.setRequestProperty("Content-Type", "application/json");
       //Insert your JSON query request
       JSONArray listOfImsi = new JSONArray(chgMsi);
       JSONObject stChgApi  = new JSONObject();
       stChgApi.put("imsi", listOfImsi);
       stChgApi.put("newState", statusChg);
       System.out.println("Request format:::"+stChgApi.toString());
       OutputStream os = conn.getOutputStream();
	   os.write(stChgApi.toString().getBytes());
	   os.flush();			
		//Get response for the valid error
		if (conn.getResponseCode() == 200)
		{
		System.out.println("Request successully processed..");				
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));				
		String output;
		System.out.println("Response fetching from the Server .... \n");
		while ((output = br.readLine()) != null)
		{
		System.out.println("Response result::::"+output);
		ObjectMapper objectMapper = new ObjectMapper();
       	JsonNode jsonNode = objectMapper.readTree(output);
        JsonNode responsStatus = jsonNode.get("status");
       	updStatusVal.put("Response_Code", conn.getResponseCode());
       	updStatusVal.put("Response_Status", responsStatus.textValue());					 
		}
		}
		//Invalidating error code 
		else
		{
			updStatusVal.put("Response_Code", conn.getResponseCode());
			updStatusVal.put("Response_Status",conn.getResponseMessage());	
		}
		conn.disconnect();
	    } 
       // Throwing exception despite known error code.
		catch (MalformedURLException e)
		{
		e.printStackTrace();
		conn.disconnect();
	    } 
		catch (IOException e)
		{
		e.printStackTrace();
		conn.disconnect();
	   }
     return updStatusVal;
}
/*
 * This  method give valid response authentication token value After passing
 * @param:username
 * @param password
 * @param:apiUrl
 */
public Map<String,Object> SimStateAuthenticationApi()
{
	Map<String,Object> toakenVal= new HashMap<String,Object>();
    HttpURLConnection conn=null;
	 try
	  {
		URL url = new URL(getAuthenticationUrl().trim());
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		JSONObject inputJson  = new JSONObject();
		inputJson.put("username", getUsername().trim());
		inputJson.put("password", getPassword().trim());
		System.out.println("Json format::"+inputJson.toString());
		OutputStream os = conn.getOutputStream();
		os.write(inputJson.toString().getBytes());
		os.flush();				
		if (conn.getResponseCode() == 200)
		{
		System.out.println("Request successully processed..");				
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));				
		String output;
		System.out.println("Response fetching from the Server .... \n");
		while ((output = br.readLine()) != null)
		{
			 System.out.println("Response result::::"+output);
			 ObjectMapper objectMapper = new ObjectMapper();
        	 JsonNode jsonNode = objectMapper.readTree(output);        	 
        	 JsonNode taken = jsonNode.get("token");
			 toakenVal.put("Response_Code", conn.getResponseCode());
			 toakenVal.put("Response_Status", taken.textValue());					 
		}
		}
		else
		{
			toakenVal.put("Response_Code", conn.getResponseCode());
			toakenVal.put("Response_Status",conn.getResponseMessage());	
		}
		conn.disconnect();
	    } 
		catch (MalformedURLException e)
		{
		e.printStackTrace();
		conn.disconnect();
	    } 
		catch (IOException e)
		{
		e.printStackTrace();
		conn.disconnect();
	   }
	 return toakenVal;	
}
/* this is used for the testing purpose.
public static void main(String arg[])
{
	SimStateChangeApi simStateChangeApi=new SimStateChangeApi();
	String token_value=(String) simStateChangeApi.SimStateAuthenticationApi().get("Response_Status");
	System.out.println("token_value result::::"+token_value);
	List<String> list=new ArrayList<String>();
	list.add("240270000333454");
	System.out.println("list::::"+list);
	Map<String,Object> mappingData=simStateChangeApi.SimStateChangeResponse(token_value, list, "Deactivated");
	System.out.println("sim status::::"+mappingData.get("Response_Code"));
	System.out.println("Error_Code::::"+mappingData.get("Response_Status"));
}*/
}
