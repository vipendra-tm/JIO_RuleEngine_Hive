package gcontrol.rules.configuration;
/* 
 * Auther Vipendra Kumar
 * Configuration class is used to defining constant parameter in one place and which is need to be called anywhere.  
 * */
public class Configuration
{
	//Defined Constants parameters for the database query and procedure
	//public static final String ACTIVE_ALERT_TABLE_NAME_DEVELOPMENT="jio_cdr_database.rule_engine_active_alert";//this for staging realease.
	public static final String PROCESS_ID="process 1"; 
	public static final String RULE_ENGINE_TABLE_NAME_DEVELOPMENT="rule_engine_rule";
	public static final String RULE_PROCEDURE="{CALL gcontrol_re_get_rule_by_process_id('"+PROCESS_ID+"')}";
	public static final String TRIGGER_PROCEDURE="CALL `gcontrol_re_get_triggers_by_rule_id`('<ruleId>')";
	//public static final String INSERT_ACTIVE_ALERT_QUREY="insert into "+ACTIVE_ALERT_TABLE_NAME_DEVELOPMENT+"(`imsi`,`ruleId`,`ruleCategory`,`occurenceTime`,`isCleared`) values ";
	public static final String UPDATE_ACTIVE_ALERT_QUREY="{CALL `gcontrol_re_clear_active_alert`(?,?)}";
	public static final String RULE_ACTION_PROCEDURE="CALL `gcontrol_re_get_action_by_rule_id`('<ruleId>')";
	public static final String UPDATE_RULE_QUREY="UPDATE "+RULE_ENGINE_TABLE_NAME_DEVELOPMENT +" SET `last_execution_time`=now() where `id` = '<ruleId>' ";
	//Data validation rule procedure
	public static final String DATA_PROCEDURE_USSAGE_MONITRING="{CALL `gcontrol_re_get_data_usage`(?,?,?,?,?)}";

	public static final String IMEI_PROCEDURE_TRACKING_CHANGES="{call gcontrol_re_get_data_for_imei_tracking(?)}";
	
	public static final String PROCEDURE_IMSI_LIST="{call gcontrol_re_get_imsi_rule_id(<ruleId>)}";
	
	public static final String DATA_PROCEDURE="{ CALL `gcontrol_re_get_data_for_connectivity_monitoring`(?,?,?,?,?,?)}";
	
	//Validation rule constants parameters are defined for the validating rules
	public static final String USAGE_MONITORING="Usage Monitoring";
	public static final String ALL_SERVICES="ALL SERVICES";
	public static final String IMEI_TRACKING="IMEI Locking";
	public static final String X_AMOUNT="X AMOUNT(In MB)";
	public static final String DATA="DATA";
	public static final String IMEI_CHANGES="IMEI Changes";
	//Constants parameters for date and time validation
	public static final String HOURS="HOURS";
	public static final String DAYS="DAYS";
	public static final String WEEKS="WEEKS";
	public static final String MONTHS="MONTHS";
}
