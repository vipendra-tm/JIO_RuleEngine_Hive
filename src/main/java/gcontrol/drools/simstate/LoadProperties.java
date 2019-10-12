package gcontrol.drools.simstate;
import gcontrol.rule.model.MappingModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LoadProperties {
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
		// TODO Auto-generated catch block
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
  return mapModel;	
}
public static Connection getHive_Connection()
{
	Connection conn=null;
	MappingModel mapModel=LoadApi_MappingPropertiesData();
	try
	{
		 Class.forName(mapModel.getHive_driver());
	     conn = DriverManager.getConnection(mapModel.getHive_url(),mapModel.getHive_username(),mapModel.getHive_password());
	     System.out.println("Hive database is successfully connected");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return conn;
}
public static void close(Connection conn)
{
	
		try {
			if(conn!=null)
			{
			conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
public static void main(String arg[]) throws SQLException
{
	Connection conn=getHive_Connection();
	Statement stmt = conn.createStatement();
    String tableName = "select servedimsi,downbytes,upbytes,totalbytes,totalbytesinkb,create_date from cdr_details WHERE servedimsi ='234206210072037'";
    ResultSet res = stmt.executeQuery(tableName);
    String imsi=null;  
    BigDecimal downbytes=new BigDecimal(0);
    BigDecimal upbytes=new BigDecimal(0);
    BigDecimal totalbytes=new BigDecimal(0);
    BigDecimal totalbytesinkb=new BigDecimal(0);
    String create_date=null;
    while (res.next()) 
    {
    	imsi=res.getString(1);    	
    	downbytes=downbytes.add(new BigDecimal(res.getInt(2)));
    	upbytes=upbytes.add(new BigDecimal(res.getInt(3)));
    	totalbytes=totalbytes.add(new BigDecimal(res.getInt(4)));
    	totalbytesinkb=totalbytesinkb.add(new BigDecimal(res.getInt(5)));
    	create_date=res.getString(6);       
    }
    System.out.println(imsi+"::"+downbytes+"::"+upbytes+"::"+totalbytes+"::"+totalbytesinkb+"::"+create_date);
    close(conn);
}
}
