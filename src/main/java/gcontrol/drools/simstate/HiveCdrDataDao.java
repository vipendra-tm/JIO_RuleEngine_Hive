package gcontrol.drools.simstate;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gcontrol.rule.model.ActiveAlert;
import gcontrol.rule.model.DataModel;
import gcontrol.rule.model.InvalidRules;
import gcontrol.rule.model.MappingModel;
import gcontrol.rule.model.MessageVo;
import gcontrol.rule.model.MsModel;
import gcontrol.rule.model.Trigger;
import gcontrol.rules.configuration.Configuration;
import gcontrol.rules.initiator.RuleIntiatorV2;

/*
 * @Author Vipendra Kumar Objective of this is that need to be implemented business component of MySql and Hive CDR data.
 */
public class HiveCdrDataDao
{
 public static final MappingModel properties=LoadProperties.LoadApi_MappingPropertiesData();
/*
 * Calculating hive CDR data daily bases for each IMSI
 */
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
			System.out.println("====Through error message during connecting with hive===="+e.getMessage());
		}
		finally
		{
		LoadProperties.close(conn);
		}
	    return dataModel;
	}
/*
 * Trigger call to executing MySql cdr data 
 */
private List<DataModel> getListOfHourlyCdrDataFromSql(Trigger trigger,String start_date,String end_date)
{
	System.out.println("MySQL CDR data calculation.....");
	List<DataModel> dataList = RuleIntiatorV2.getDataListFromProcedure(Configuration.DATA_PROCEDURE_USSAGE_MONITRING,
			trigger.getRuleId(), trigger.getTriggerId(), trigger.getRuleTriggerIntervalUnit(),
			end_date, start_date);
	return dataList;
}
/*
 * Trigger call to executing hive cdr data 
 */
@SuppressWarnings("unchecked")
private List<DataModel> getListOfHourlyCdrDataFromHadoop(Trigger trigger,String start_date,String end_date)
{
    System.out.println("===Hive CDR data calculation====");
    
	List<DataModel> dataList=new ArrayList<DataModel>();
	
	List<MsModel> listImsi=(List<MsModel>) RuleIntiatorV2.genericService.executeThirdPartyProcesure(MsModel.class,Configuration.PROCEDURE_IMSI_LIST.replaceAll(RuleIntiatorV2.RULE_PARAMETER, String.valueOf(trigger.getRuleId())));
	
	System.out.println("===listOfIMSI==="+listImsi);
	
	if(trigger.getRuleTriggerIntervalUnit().equals(Configuration.DAYS))
	{					
	for(MsModel msModel:listImsi)
	{
		DataModel dataModel=getListOfHourlyCdrData(msModel.getIMSI(),start_date,end_date);
		if(dataModel.getImsi()!=null)
		dataList.add(dataModel);
	}					
	}
	return dataList;
}
/*
 * @findListOfDataEitherMySqlOrHiveCdr() will be executed when condition of true case from MySql or Hive CDR.
 */
public List<DataModel> findListOfDataEitherMySqlOrHiveCdr(Trigger trigger,String start_date,String end_date)
{
	List<DataModel> dataList=new ArrayList<DataModel>();
	
	if(properties.getIsMySql().equalsIgnoreCase("True"))
	{
		dataList=getListOfHourlyCdrDataFromSql(trigger,start_date,end_date);	
	}
	if(properties.getIsHadoop().equalsIgnoreCase("true"))
	{
		dataList=getListOfHourlyCdrDataFromHadoop(trigger,start_date,end_date);
	}
	List<DataModel> distinctElements = dataList.stream().filter( distinctByKeys(p -> p.getImsi())).collect( Collectors.toList() );
	return distinctElements;
}
/*
 * @method will be updated to active alert into alert history.
 */
private void updateHadoopHive(int ruleId,String in_imsi)
{
	//Initialized variables
	Connection conn=null;
	PreparedStatement psmt=null,psmt1=null,psmt2=null;
	ResultSet rs=null;
	int temp_alert_id=0;
	try
	{
	conn=LoadProperties.getHive_Connection();
	psmt=conn.prepareStatement("SELECT id FROM jio_cdr_database.rule_engine_active_alert WHERE RuleId =? AND IMSI =?");
	psmt.setInt(1, ruleId);
	psmt.setString(2, in_imsi);
	rs=psmt.executeQuery();
	if(rs.next())
	{
	temp_alert_id=rs.getInt(1);
	}
	LoadProperties.close(conn);
	//check condition to moving data from alert to alert history when get id against to imsi
	if(temp_alert_id>0)
	conn=LoadProperties.getHive_Connection();
	psmt1=conn.prepareStatement("update jio_cdr_database.rule_engine_alert_history set IsCleared = 1,cleared_time=current_timestamp() WHERE id = ? AND IMSI = ?");
	psmt1.setInt(1, temp_alert_id);
	psmt1.setString(2, in_imsi);
	int updateCount=psmt1.executeUpdate();
	LoadProperties.close(conn);
	//==check condition to removing data from alert table if get records from alert table
	if(updateCount>0)
	conn=LoadProperties.getHive_Connection();
	psmt2=conn.prepareStatement("DELETE FROM jio_cdr_database.rule_engine_active_alert WHERE id = ? AND IMSI = ?");
	psmt2.setInt(1, temp_alert_id);
	psmt2.setString(2, in_imsi);
	int deleteCount=psmt2.executeUpdate();
	if(deleteCount>0)
	{
	System.out.println("Alert rule message is:-Alert Cleared Sucessfuly:: amd ISMI is:-"+in_imsi);
	}
	else
	{
	 System.out.println("Alert rule message is:-Alert Not Cleared:: amd ISMI is:-"+in_imsi);	
	}
	}
	catch(Exception e)
	{
	e.printStackTrace();	
	}
	finally
	{
	LoadProperties.close(conn);	
	}
}
/*
 * @method is used to either move active alert into hive or MySql database.
 */
public void updateInValidBusinessRule(Set<InvalidRules> invalidateRule)
{
	System.out.println("check the condition of alert and alert history moving data:");
	if(properties.getIsMySql().equalsIgnoreCase("True"))
	{
	invalidateRule.forEach(n -> {				
		List<MessageVo> updatemessage=RuleIntiatorV2.removeInvalidRuleMsiFromProcedure(Configuration.UPDATE_ACTIVE_ALERT_QUREY,n.getRuleId(),n.getImsi());
		updatemessage.stream().forEach(p->System.out.println("Alert rule message is:-"+p.getMessage()+":: amd ISMI is:-" + n.getImsi())); 
	});	
	}
	if(properties.getIsHadoop().equalsIgnoreCase("true"))
	{
		invalidateRule.forEach(n -> {
			updateHadoopHive(n.getRuleId(),n.getImsi());	
		});
	}
}
/*
 * @method is used to updating inActive alert into history alert.
 */
public void updateForInactiveRule(Set<String> invalidateRule,int ruleId)
{
	System.out.println("===check the condition of inActive alert====");
	if(properties.getIsMySql().equalsIgnoreCase("true"))
	{
	invalidateRule.forEach(n -> {				
		List<MessageVo> updatemessage=RuleIntiatorV2.removeInvalidRuleMsiFromProcedure(Configuration.UPDATE_ACTIVE_ALERT_QUREY,ruleId,n);
		updatemessage.stream().forEach(p->System.out.println("Alert rule message is:-"+p.getMessage()+":: amd ISMI is:-" + n)); 
	});	
	}
	if(properties.getIsHadoop().equalsIgnoreCase("true"))
	{
		invalidateRule.forEach(n -> {
			updateHadoopHive(ruleId,n);	
		});
	}
 }
@SuppressWarnings("unchecked")
public List<ActiveAlert> getListOfActiveAlert()
{
	Connection conn=null;
	PreparedStatement psmt=null;
	ResultSet rs=null;
	List<ActiveAlert> activeAlertResult=new ArrayList<ActiveAlert>();
	if(properties.getIsMySql().equalsIgnoreCase("true"))
	{
	String ACTIVE_ALERT_QUREY="SELECT * FROM "+properties.getMySqlDatabase()+".rule_engine_active_alert";
	activeAlertResult = (List<ActiveAlert>) RuleIntiatorV2.genericService.executeThirdPartyProcesure(ActiveAlert.class,ACTIVE_ALERT_QUREY);
	}
	if(properties.getIsHadoop().equalsIgnoreCase("true"))
	{
	try
	{
		conn=LoadProperties.getHive_Connection();
		psmt=conn.prepareStatement("select * from jio_cdr_database.rule_engine_active_alert");
		rs=psmt.executeQuery();
		while(rs.next())
		{
			ActiveAlert activeAlert=new ActiveAlert();
			activeAlert.setId(rs.getInt(1));
			activeAlert.setImsi(rs.getString(2));
			activeAlert.setIsCleared(rs.getInt(6));
			activeAlert.setOccurenceTime(rs.getTimestamp(5));
			activeAlert.setRuleCategory(rs.getString(4));
			activeAlert.setRuleId(rs.getInt(3));
			activeAlertResult.add(activeAlert);
		}
	}
	catch(Exception e)
	{
		System.out.println("=====Connection open error message from getListOfActiveAlert()method is===="+e.getMessage());
		e.printStackTrace();
	}
	finally
	{
		LoadProperties.close(conn);
	}
	}
	return activeAlertResult;
}
public void insertIntoAlertTable(StringBuilder sb)
{
	if(properties.getIsMySql().equalsIgnoreCase("true"))
	{
		String INSERT_ACTIVE_ALERT_QUREY="insert into "+properties.getMySqlDatabase()+".rule_engine_active_alert (`imsi`,`ruleId`,`ruleCategory`,`occurenceTime`,`isCleared`) values ";
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.insert(0, INSERT_ACTIVE_ALERT_QUREY);
		System.out.println("===insert query to mysql active alert===" + sb.toString());
		RuleIntiatorV2.genericService.executeAnySqlQuery(sb.toString());
	}
	if(properties.getIsHadoop().equalsIgnoreCase("true"))
	{
		Connection conn=null;
		PreparedStatement psmt=null,psmt1=null;
		try
		{
		conn=LoadProperties.getHive_Connection();
		String INSERT_ACTIVE_ALERT_QUREY="insert into jio_cdr_database.rule_engine_active_alert (`imsi`,`ruleId`,`ruleCategory`,`occurenceTime`,`isCleared`) values ";
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.insert(0, INSERT_ACTIVE_ALERT_QUREY);
		psmt=conn.prepareStatement(sb.toString());
		System.out.println("===insert query to hive active alert===" + sb.toString());
		int count=psmt.executeUpdate();
		if(count>0)
		{
		String INSERT_ACTIVE_ALERT_HISTORY="insert into jio_cdr_database.rule_engine_alert_history (`imsi`,`ruleId`,`ruleCategory`,`occurenceTime`,`isCleared`) values ";
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.insert(0, INSERT_ACTIVE_ALERT_HISTORY);
		psmt1=conn.prepareStatement(sb.toString());
		System.out.println("===insert query to hive active alert history====" + sb.toString());
		psmt1.executeUpdate();
		}
		}
		catch(Exception e)
		{
			System.out.println("=====Data insert into hive alert and history table is through error message===="+e.getMessage());
			e.printStackTrace();	
		}
		finally
		{
			LoadProperties.close(conn);	
		}
	}
}
/* Created generic distinctByKeys method for filtering imsi key values to collecting into Set*/
@SafeVarargs
private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) 
{
	final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
	return t -> {
		final List<?> keys = Arrays.stream(keyExtractors).map(ke -> ke.apply(t)).collect(Collectors.toList());
		return seen.putIfAbsent(keys, Boolean.TRUE) == null;
	};
}
}
