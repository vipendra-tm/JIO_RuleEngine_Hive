package gcontrol.drools.simstate;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import gcontrol.rule.model.DataModel;
public class HiveCdrDataDao
{
public DataModel getListOfDailyCdrData(String servedimsi,String start_date,String end_date)
{
		DataModel dataModel=new DataModel();
		Connection conn=null;
		PreparedStatement pstmt=null;
		try
		{
		conn=LoadProperties.getHive_Connection();
		pstmt = conn.prepareStatement("select servedimsi,downbytes,upbytes,totalbytes,totalbytesinkb from jio_cdr_database.cdr_data_details_daily WHERE servedimsi = ? AND creation_time >= ? AND creation_time <= ?");
		pstmt.setString(1, servedimsi);
		pstmt.setString(2, start_date);
		pstmt.setString(3, end_date);
	    ResultSet res = pstmt.executeQuery();
	    String imsi=null;  
	    BigDecimal downbytes=new BigDecimal(0);
	    BigDecimal upbytes=new BigDecimal(0);
	    BigDecimal totalbytes=new BigDecimal(0);
	    BigDecimal totalbytesinkb=new BigDecimal(0);	  
	    while (res.next()) 
	    {    	
	    	imsi=res.getString(1);    	
	    	downbytes=downbytes.add(new BigDecimal(res.getInt(2)));
	    	upbytes=upbytes.add(new BigDecimal(res.getInt(3)));
	    	totalbytes=totalbytes.add(new BigDecimal(res.getInt(4)));
	    	totalbytesinkb=totalbytesinkb.add(new BigDecimal(res.getInt(5)));    	     
	    } 
	    dataModel.setImsi(imsi);
	    dataModel.setTotalDownbytes(downbytes);
	    dataModel.setTotalUpbytes(upbytes);
	    dataModel.setTotalBytes(totalbytes);
	    dataModel.setTotalBytesinkb(totalbytesinkb);  
	  	}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
		LoadProperties.close(conn);
		}
	    return dataModel;
	}
public DataModel getListOfHourlyCdrData(String servedimsi,String start_date,String end_date)
{
		DataModel dataModel=new DataModel();
		Connection conn=null;
		PreparedStatement pstmt=null;
		try
		{
		conn=LoadProperties.getHive_Connection();
		pstmt = conn.prepareStatement("select servedimsi,downbytes,upbytes,totalbytes,totalbytesinkb from jio_cdr_database.cdr_data_details_hourly WHERE servedimsi = ? AND creation_time >= ? AND creation_time <= ?");
		pstmt.setString(1, servedimsi);
		pstmt.setString(2, start_date);
		pstmt.setString(3, end_date);
	    ResultSet res = pstmt.executeQuery();
	    String imsi=null;  
	    BigDecimal downbytes=new BigDecimal(0);
	    BigDecimal upbytes=new BigDecimal(0);
	    BigDecimal totalbytes=new BigDecimal(0);
	    BigDecimal totalbytesinkb=new BigDecimal(0);
	    while (res.next()) 
	    {    	
	    	imsi=res.getString(1);    	
	    	downbytes=downbytes.add(new BigDecimal(res.getInt(2)));
	    	upbytes=upbytes.add(new BigDecimal(res.getInt(3)));
	    	totalbytes=totalbytes.add(new BigDecimal(res.getInt(4)));
	    	totalbytesinkb=totalbytesinkb.add(new BigDecimal(res.getInt(5)));    	     
	    } 
	    dataModel.setImsi(imsi);
	    dataModel.setTotalDownbytes(downbytes);
	    dataModel.setTotalUpbytes(upbytes);
	    dataModel.setTotalBytes(totalbytes);
	    dataModel.setTotalBytesinkb(totalbytesinkb);  
	   	}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
		LoadProperties.close(conn);
		}
	    return dataModel;
	}
}
