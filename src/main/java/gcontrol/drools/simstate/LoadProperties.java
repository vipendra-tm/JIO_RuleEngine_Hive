package gcontrol.drools.simstate;
import gcontrol.rule.model.MappingModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/*
 * @Author Vipendra Kumar this class would be used for getting connection from the hive database via Zookeeper port.
 */
public class LoadProperties
{
/*
 * @ returm MappingModel object after loading hive connection info from the properties file.
 */
public static MappingModel LoadApi_MappingPropertiesData()
{
	MappingModel mapModel=new MappingModel();
	File file = new File(System.getenv("Rule_Engine")+"/template/API_Mapping.properties");
	Properties property=new Properties();
	try {
		InputStream in=new FileInputStream(file);
		property.load(in);
		in.close();
	} 
	catch (IOException e) 
	{
	System.out.println("===Error message through during loading properties file data===="+e.getMessage());
	e.printStackTrace();
	}
	mapModel.setAuthenticationUrl(property.getProperty("rule.authentication_api_url"));
	mapModel.setStateChangeUrl(property.getProperty("rule.sim_state_change_api"));
	mapModel.setUsername(property.getProperty("rule.username"));
	mapModel.setPassword(property.getProperty("rule.password"));
	mapModel.setHive_driver(property.getProperty("hive_driver"));
	mapModel.setHive_url(property.getProperty("hive_url"));
	mapModel.setHive_username(property.getProperty("hive_username"));
	mapModel.setHive_password(property.getProperty("hive_password"));
	mapModel.setIsMySql(property.getProperty("rule.isMySql"));
	mapModel.setIsHadoop(property.getProperty("rule.isHadoop"));
	mapModel.setMySqlDatabase(property.getProperty("rule.database"));
  return mapModel;	
}
/*
 * @return connection object after open connection.
 */
public static Connection getHive_Connection()
{
	Connection conn=null;
	MappingModel mapModel=LoadApi_MappingPropertiesData();
	try
	{
		 Class.forName(mapModel.getHive_driver());
	     conn = DriverManager.getConnection(mapModel.getHive_url(),mapModel.getHive_username(),mapModel.getHive_password());	     
	}
	catch(Exception e)
	{
		System.out.println("=====Connection open error message===="+e.getMessage());
		e.printStackTrace();
	}
	return conn;
}
/*
 * @return connection object as null after closing connection.
 */
public static void close(Connection conn)
{
	
try {
if(conn!=null)
{
conn.close();
}
} 
catch (SQLException e)
{
System.out.println("=====Connection close error message===="+e.getMessage());
e.printStackTrace();
}
}
}
