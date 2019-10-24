import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import gcontrol.rule.model.DataModel;

public class TestClass 
{
	static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	public static Connection getHive_Connection()
	{
		Connection conn=null;
		try
		{
			 Class.forName("com.mysql.jdbc.Driver");
		     conn = DriverManager.getConnection("jdbc:mysql://192.168.1.34:3306/iotsmp_load_testing","root","Ttpl@123");
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args)
	{
		List<DataModel> listOfData=new ArrayList<DataModel>();
		List<String> listOfImsi=new ArrayList<String>();
		Connection conn=null;
		PreparedStatement psmt=null,psmt1=null;
		ResultSet res=null,rs=null;
	try
	{
		System.out.println("Query started execution========="+formatter.format(new Date(System.currentTimeMillis()))+"======");
		try
		{
		conn=getHive_Connection();
		psmt1=conn.prepareStatement("select DISTINCT servedimsi from iotsmp_load_testing.cdr_details");
		rs=psmt1.executeQuery();
		while(rs.next())
		{
		listOfImsi.add(rs.getString(1));
		}
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		if(listOfImsi.size()>0)
		for(String imsiVal:listOfImsi)
		{
		try
		{
		conn=getHive_Connection();
		psmt=conn.prepareStatement("SELECT servedimsi,downbytes,upbytes,totalbytes,totalbytesinkb from iotsmp_load_testing.cdr_details where servedimsi=?");
		psmt.setString(1, imsiVal);
		res=psmt.executeQuery();
		
		    BigDecimal downbytes=new BigDecimal(0);
		    BigDecimal upbytes=new BigDecimal(0);
		    BigDecimal totalbytes=new BigDecimal(0);
		    BigDecimal totalbytesinkb=new BigDecimal(0);	  
		    while (res.next()) 
		    {
		    downbytes=downbytes.add(new BigDecimal(res.getInt(2)));
	    	upbytes=upbytes.add(new BigDecimal(res.getInt(3)));
	    	totalbytes=totalbytes.add(new BigDecimal(res.getInt(4)));
	    	totalbytesinkb=totalbytesinkb.add(new BigDecimal(res.getInt(5))); 
		    }
		    DataModel dataModel=new DataModel();
		    dataModel.setImsi(imsiVal);
		    dataModel.setTotalDownbytes(downbytes);
		    dataModel.setTotalUpbytes(upbytes);
		    dataModel.setTotalBytes(totalbytes);
		    dataModel.setTotalBytesinkb(totalbytesinkb);
		    listOfData.add(dataModel);
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}		
		System.out.println("Total size of data is========="+listOfData.size()+"======");
		System.out.println("Query ended execution========="+formatter.format(new Date(System.currentTimeMillis()))+"======");		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
 }
}
