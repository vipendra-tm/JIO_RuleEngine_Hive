package gcontrol.rules.initiator;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import gcontrol.drools.processor.UssageMonitoring;
import gcontrol.drools.simstate.HiveCdrDataDao;
import gcontrol.rule.model.ActiveAlert;
import gcontrol.rule.model.ConnectivityVo;
import gcontrol.rule.model.DataModel;
import gcontrol.rule.model.EmeiData;
import gcontrol.rule.model.InvalidRules;
import gcontrol.rule.model.MessageVo;
import gcontrol.rule.model.MsModel;
import gcontrol.rule.model.RuleAction;
import gcontrol.rule.model.Rules;
import gcontrol.rule.model.SMSModel;
import gcontrol.rule.model.Status;
import gcontrol.rule.model.Trigger;
import gcontrol.rule.model.ValidateRule;
import gcontrol.rule.model.VoiceModel;
import gcontrol.rules.configuration.Configuration;
import gcontrol.rules.configuration.FileConfiguration;
import gcontrol.rules.service.ActionService;
import gcontrol.rules.service.GenericService;
/* 
 * Author Vipendra Kumar
 * RuleIntiatorV2 is used to validating and implementing all business rule
 * All rules are to be passed into drool Rule Engine to validating case Pass/Fail to the generating alert email.
 * */
public class RuleIntiatorV2 
{
	public static final GenericService genericService = new GenericService();
	private static final String START_DATE_PARAMETER = "START_DATE";
	private static final String END_DATE_PARAMETER = "END_DATE";
	private static final String RULE_PARAMETER = "<ruleId>";
	public static Set<ValidateRule> validRule=null;
	public static Set<InvalidRules> invalidateRule=null;
	
	public static final List<String> ALL_SERVICES_DATA=new ArrayList<>(Arrays.asList("BASE LIMIT", "PERCENTAGE BASE LIMIT","OVERAGE LIMIT","PERCENTAGE OVERAGE LIMIT"));
	public static final List<String> ALL_SERVICES_SMS=new ArrayList<>(Arrays.asList("MO","MT"));
	
	@SuppressWarnings("unchecked")
	public static final List<ActiveAlert> activeAlertResult = (List<ActiveAlert>) genericService.executeThirdPartyProcesure(ActiveAlert.class, Configuration.ACTIVE_ALERT_QUREY);

	/** The configuration. */
	public static final FileConfiguration FILE_CONFIGURATION = new FileConfiguration();

	@SuppressWarnings("unchecked")
	/* this is main methed which is performed the action for executing all business rules*/
	public static void main(String[] args)
	{
		if (args[0] == null) 
		{
		System.out.println("No cofiguration file path in argument");
		System.exit(0);
		}
		setConfiguration(args[0]);		
		List<Rules> result = (List<Rules>) genericService.executeThirdPartyProcesure(Rules.class,Configuration.RULE_PROCEDURE);
		System.out.println("result::"+result);
		result.forEach(x -> {
			Status status = new Status();
			if (!getPosativeDateParameter(x.getExecutionIntervalUnit(), x.getExecutionInterval(), x.getRuleStartTime()))
				return;
			List<Trigger> triggers = (List<Trigger>) genericService.executeThirdPartyProcesure(Trigger.class,Configuration.TRIGGER_PROCEDURE.replaceAll(RULE_PARAMETER, String.valueOf(x.getRuleId())));
			List<RuleAction> actions = (List<RuleAction>) genericService.executeThirdPartyProcesure(RuleAction.class,Configuration.RULE_ACTION_PROCEDURE.replaceAll(RULE_PARAMETER, String.valueOf(x.getRuleId())));
			validRule = new HashSet<>();
			invalidateRule = new HashSet<>();
			
			triggers.forEach(tirgge -> {
				List<?> tempData = new ArrayList<>();
				System.out.println("trigger:-" + tirgge);
				if(tirgge.getRuleTriggerIntervalUnit()!=null && tirgge.getRuleTriggerIntervalUnitValue()!=0)
				{
				Map<String, String> dateMap = getDateParameter(tirgge.getRuleTriggerIntervalUnit(),tirgge.getRuleTriggerIntervalUnitValue());
								
				if(tirgge.getRuleParameter().equalsIgnoreCase(Configuration.ALL_SERVICES))
                {					
					System.out.println("tirgge.getRuleCondition():-"+tirgge.getRuleCondition());
					if (ALL_SERVICES_DATA.contains(tirgge.getRuleCondition()))
					{
						        System.out.println("inside data condition");
						        tempData = getDataListFromProcedure(Configuration.DATA_PROCEDURE_USSAGE_MONITRING,
								tirgge.getRuleId(), tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(),
								dateMap.get(END_DATE_PARAMETER), dateMap.get(START_DATE_PARAMETER));
					}
					if (ALL_SERVICES_SMS.contains(tirgge.getRuleCondition())) 
					{
						        System.out.println("inside SMS condition");
						        tempData = getSMSListFromProcedure(Configuration.SMS_PROCEDURE_USSAGE_MONITRING, tirgge.getRuleId(),
								tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(), dateMap.get(END_DATE_PARAMETER),
								dateMap.get(START_DATE_PARAMETER));
					}
					if(tirgge.getRuleCondition().equalsIgnoreCase(Configuration.X_AMOUNT))
					{
						        System.out.println("inside  VOICE else service of all service voice condition");
						        tempData = getVoiceListFromProcedure(Configuration.VOICE_PROCEDURE_USSAGE_MONITRING,
								tirgge.getRuleId(), tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(),
								dateMap.get(END_DATE_PARAMETER), dateMap.get(START_DATE_PARAMETER));
				 }										
				}		
				else
				{
				if (tirgge.getRuleParameter().equalsIgnoreCase(Configuration.DATA))
				{
					        tempData = getDataListFromProcedure(Configuration.DATA_PROCEDURE_USSAGE_MONITRING,
							tirgge.getRuleId(), tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(),
							dateMap.get(END_DATE_PARAMETER), dateMap.get(START_DATE_PARAMETER));		
				}
				if (tirgge.getRuleParameter().equalsIgnoreCase(Configuration.DATA) && tirgge.getRuleCondition().equalsIgnoreCase(Configuration.X_AMOUNT))
				{
					System.out.println("inside Data service call x_amount condition");
					HiveCdrDataDao hivCdr=new HiveCdrDataDao();
					List<DataModel> dataList=new ArrayList<DataModel>();
					List<MsModel> listImsi=(List<MsModel>) genericService.executeThirdPartyProcesure(MsModel.class,Configuration.PROCEDURE_IMSI_LIST.replaceAll(RULE_PARAMETER, String.valueOf(tirgge.getRuleId())));
					System.out.println("listOfIMSI:::"+listImsi);
					if(tirgge.getRuleTriggerIntervalUnit().equals(Configuration.HOURS))
					{					
					for(MsModel msModel:listImsi)
					{
						DataModel dataModel=hivCdr.getListOfHourlyCdrData(msModel.getIMSI(), dateMap.get(START_DATE_PARAMETER), dateMap.get(END_DATE_PARAMETER));
						if(dataModel.getImsi()!=null)
						dataList.add(dataModel);
					}					
					}
					else
					{
						for(MsModel msModel:listImsi)
						{
						 DataModel dataModel=hivCdr.getListOfDailyCdrData(msModel.getIMSI(), dateMap.get(START_DATE_PARAMETER), dateMap.get(END_DATE_PARAMETER));
						 if(dataModel.getImsi()!=null)
						 dataList.add(dataModel);
						}	
					}
					tempData=dataList;
				}
				if (tirgge.getRuleParameter().equalsIgnoreCase(Configuration.SMS))
				{
					        tempData = getSMSListFromProcedure(Configuration.SMS_PROCEDURE_USSAGE_MONITRING, tirgge.getRuleId(),
							tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(), dateMap.get(END_DATE_PARAMETER),
							dateMap.get(START_DATE_PARAMETER));
				}
				if (tirgge.getRuleParameter().equalsIgnoreCase(Configuration.VOICE))
				{
					        tempData = getVoiceListFromProcedure(Configuration.VOICE_PROCEDURE_USSAGE_MONITRING,
							tirgge.getRuleId(), tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(),
							dateMap.get(END_DATE_PARAMETER), dateMap.get(START_DATE_PARAMETER));
				}

				if (tirgge.getRuleDefaultCondition().equalsIgnoreCase(Configuration.SIM_SESSION))
				{
					        tempData = getDataListFromProcedure(Configuration.DATA_PROCEDURE, tirgge.getRuleId(),
							tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(), "DATA",
							dateMap.get(END_DATE_PARAMETER), dateMap.get(START_DATE_PARAMETER));
				}
				if (tirgge.getRuleDefaultCondition().equalsIgnoreCase(Configuration.MO_SMS))
				{
					        tempData = getSMSListFromProcedure(Configuration.DATA_PROCEDURE, tirgge.getRuleId(),
							tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(), "SMS",
							dateMap.get(END_DATE_PARAMETER), dateMap.get(START_DATE_PARAMETER));

				}
				if (tirgge.getRuleDefaultCondition().equalsIgnoreCase(Configuration.VOICE_CALLED)) 
				{
					        tempData = getVoiceListFromProcedure(Configuration.DATA_PROCEDURE, tirgge.getRuleId(),
							tirgge.getTriggerId(), tirgge.getRuleTriggerIntervalUnit(), "VOICE",
							dateMap.get(END_DATE_PARAMETER), dateMap.get(START_DATE_PARAMETER));
				}
				}
				}
				else
				{
				if (tirgge.getRuleDefaultCondition().equalsIgnoreCase(Configuration.IMEI_CHANGES)) 
				{
					         System.out.println("inside  IMEI Changes for IMEI Tracking condition");
					         tempData = getIMEIListFromProcedure(Configuration.IMEI_PROCEDURE_TRACKING_CHANGES,String.valueOf(tirgge.getRuleId()));							
				}
				}			
				System.out.println(tempData);
				if (tempData.isEmpty()) 
				{
					status.setNullFlag(true);
					return;
				}
				UssageMonitoring.executeData(tempData, status, tirgge);
				status.setStartFlag(false);
			});
			if (status.getNullFlag() == false) 
			{
				ActionService.action(activeAlertResult, actions, x.getRuleCategory(),x.getRuleName(),x.getRuleCategoryName(),x.getRuleDescription());
			} 
			else
			{
				ActionService.actionForInactiveRule(activeAlertResult, x.getRuleId());
			}
			genericService.executeAnySqlQuery(Configuration.UPDATE_RULE_QUREY.replaceAll(RULE_PARAMETER, String.valueOf(x.getRuleId())));
		});
	}
   /* Validating date parameter from input parameter triggerIntervalUnit and triggerIntervalUnitValue to getting start date and end date from getDateParameter()*/
	public static Map<String, String> getDateParameter(String triggerIntervalUnit, int triggerIntervalUnitValue)
    {
		Map<String, String> dateMap = new HashMap<>();
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = sdf.format(currentDate.getTime());

		String endDate = null;

		if (triggerIntervalUnit.equalsIgnoreCase(Configuration.HOURS)) {

			currentDate.add(Calendar.HOUR, -triggerIntervalUnitValue);
			endDate = sdf.format(currentDate.getTime());
		}
		if (triggerIntervalUnit.equalsIgnoreCase(Configuration.DAYS)) {
			currentDate.add(Calendar.DAY_OF_MONTH, -triggerIntervalUnitValue);
			endDate = sdf.format(currentDate.getTime());

		}
		if (triggerIntervalUnit.equalsIgnoreCase(Configuration.WEEKS)) {
			currentDate.add(Calendar.WEEK_OF_MONTH, -triggerIntervalUnitValue);
			endDate = sdf.format(currentDate.getTime());
		}
		if (triggerIntervalUnit.equalsIgnoreCase(Configuration.MONTHS)) {
			currentDate.add(Calendar.MONTH, -triggerIntervalUnitValue);
			endDate = sdf.format(currentDate.getTime());
		}
		dateMap.put(START_DATE_PARAMETER, startDate);
		dateMap.put(END_DATE_PARAMETER, endDate);

		return dateMap;
	}
	/* Validating date parameter from input parameter triggerIntervalUnit and triggerIntervalUnitValue and lastExectionTime to getting boolean status of date from getPosativeDateParameter()*/
	public static Boolean getPosativeDateParameter(String triggerIntervalUnit, int triggerIntervalUnitValue,Timestamp lastExectionTime) 
	{

		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endDate = null;
		if (lastExectionTime != null)
		{
			currentDate.setTimeInMillis(lastExectionTime.getTime());
		}
			
		if (triggerIntervalUnit.equalsIgnoreCase(Configuration.HOURS)) 
		{
			currentDate.add(Calendar.HOUR, triggerIntervalUnitValue);
			endDate = sdf.format(currentDate.getTime());
		}
		if (triggerIntervalUnit.equalsIgnoreCase(Configuration.DAYS)) 
		{
			currentDate.add(Calendar.DAY_OF_MONTH, triggerIntervalUnitValue);
			endDate = sdf.format(currentDate.getTime());

		}
		if (triggerIntervalUnit.equalsIgnoreCase(Configuration.WEEKS))
		{
			currentDate.add(Calendar.WEEK_OF_MONTH, triggerIntervalUnitValue);
			endDate = sdf.format(currentDate.getTime());
		}
		if (triggerIntervalUnit.equalsIgnoreCase(Configuration.MONTHS))
		{
			currentDate.add(Calendar.MONTH, triggerIntervalUnitValue);
			endDate = sdf.format(currentDate.getTime());
		}

		try {
			if (sdf.parse(endDate).after(new Date())) {
				return false;
			}
		} 
		catch (ParseException e) 
		{
			System.out.println("ParseException message is::"+e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Sets the configuration.
	 *
	 * @param configFileLocation
	 *            the new configuration
	 */
	private static void setConfiguration(String configFileLocation) 
	{
		Properties config = new Properties();
		try 
		{
			/**
			 * Load file path of configuration file
			 */
			config.load(new FileReader(new File(configFileLocation)));
			/** set SMTP configuration */
			FILE_CONFIGURATION.setSmtpHostName(config.getProperty("rule.hostSMTP"));
			FILE_CONFIGURATION.setSmtpHostPortNumber(config.getProperty("rule.portSMTP"));
			FILE_CONFIGURATION.setSmtpHostUserName(config.getProperty("rule.userSMTP"));
			FILE_CONFIGURATION.setSmtpHostPassword(config.getProperty("rule.passwordSMTP"));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	/* Collecting list of data into DataModel class from back end database after passing input parameter from getDataListFromProcedure() method*/
	public static List<DataModel> getDataListFromProcedure(String query, Object... objects) 
	{
	return (List<DataModel>) genericService.executeDynamicProcesure(DataModel.class, query, objects);
	}
	@SuppressWarnings("unchecked")
	/* Collecting list of sms data into SMSModel class from back end database after passing input parameter from getSMSListFromProcedure() method*/
	public static List<SMSModel> getSMSListFromProcedure(String query, Object... objects) 
	{
		return (List<SMSModel>) genericService.executeDynamicProcesure(SMSModel.class, query, objects);
	}
	@SuppressWarnings("unchecked")
	/* Collecting list of voice data into VoiceModel class from back end database after passing input parameter from getVoiceListFromProcedure() method*/
	public static List<VoiceModel> getVoiceListFromProcedure(String query, Object... objects) 
	{
	 return (List<VoiceModel>) genericService.executeDynamicProcesure(VoiceModel.class, query, objects);
	}
	@SuppressWarnings("unchecked")
	/* Collecting list of imei data into EmeiData class from back end database after passing input parameter from getIMEIListFromProcedure() method*/
	public static List<EmeiData> getIMEIListFromProcedure(String query, Object... objects) 
	{
	return (List<EmeiData>) genericService.executeDynamicProcesure(EmeiData.class, query, objects);
	}
	@SuppressWarnings("unchecked")
	/* Collecting list of connectivity monitoring data into ConnectivityVo class from back end database after passing input parameter from getConnectivityDetailsFromProcedure() method*/
	public static List<ConnectivityVo> getConnectivityDetailsFromProcedure(String query, Object... objects) 
	{
	return (List<ConnectivityVo>) genericService.executeDynamicProcesure(ConnectivityVo.class, query, objects);
	}
	/* Update invalid rule condition from removeInvalidRuleMsiFromProcedure() method*/
	@SuppressWarnings("unchecked")
	public static List<MessageVo> removeInvalidRuleMsiFromProcedure(String query, Object... objects) 
	{
	return (List<MessageVo>) genericService.executeDynamicProcesure(MessageVo.class, query, objects);
	}
}
