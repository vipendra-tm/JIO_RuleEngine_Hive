package gcontrol.rules.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import gcontrol.drools.simstate.SimStateChangeApi;
import gcontrol.rule.model.ActiveAlert;
import gcontrol.rule.model.InvalidRules;
import gcontrol.rule.model.MessageVo;
import gcontrol.rule.model.RuleAction;
import gcontrol.rule.model.ValidateRule;
import gcontrol.rules.configuration.Configuration;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.concurrent.ConcurrentHashMap;
import gcontrol.rules.initiator.RuleIntiatorV2;
import gcontrol.rules.mailService.MailService;
/* 
 * Author Vipendra Kumar
 * ActionService is used to action perform to triggering email alert when ActiveAlert are validated pass.
 * ActiveAlert would be updated in rule_engine_active_alert table For the InAnctive Rule 
 */
public class ActionService 
{	
 private ActionService()
 {
 }
/* MailService is created for sending email to validation rules */
private static	MailService mailer = new MailService();
	/* Created action method would be performed to action alert to sending email as well as inserting active alert into database table once input parameters of method are validated */
	@SuppressWarnings("unlikely-arg-type")
	public static void action(List<ActiveAlert> activeAlertResult,List<RuleAction> actions,int ruleCategory,String ruleName,String ruleCategoryName,String RuleDescription)
	{
		System.out.println("validate rules:-" + RuleIntiatorV2.validRule);
		System.out.println("invalidate rules :" + RuleIntiatorV2.invalidateRule);
		
		if((RuleIntiatorV2.invalidateRule.size() > 0) && (ruleCategoryName.equals(Configuration.IMEI_TRACKING)))
		{
			RuleIntiatorV2.invalidateRule.forEach(n -> {				
				List<MessageVo> updatemessage=RuleIntiatorV2.removeInvalidRuleMsiFromProcedure(Configuration.UPDATE_ACTIVE_ALERT_QUREY,n.getRuleId(),n.getImsi());
				updatemessage.stream().forEach(p->System.out.println("Alert rule message is:-"+p.getMessage()+":: amd ISMI is:-" + n.getImsi())); 
			});		
		}
		if (RuleIntiatorV2.invalidateRule.size() > 0 && (ruleCategoryName.equals(Configuration.CONNECTIVITY_MONITORING))) 
		{
				RuleIntiatorV2.invalidateRule.forEach(n -> {
				List<MessageVo> updatemessage=RuleIntiatorV2.removeInvalidRuleMsiFromProcedure(Configuration.UPDATE_ACTIVE_ALERT_QUREY,n.getRuleId(),n.getImsi());
				updatemessage.stream().forEach(p->System.out.println("Alert rule message is:-"+p.getMessage()+":: amd ISMI is:-" + n.getImsi())); 				
			});
		}
		if (RuleIntiatorV2.invalidateRule.size() > 0 && (ruleCategoryName.equals(Configuration.USAGE_MONITORING))) 
		{
				RuleIntiatorV2.invalidateRule.forEach(n -> {
				List<MessageVo> updatemessage=RuleIntiatorV2.removeInvalidRuleMsiFromProcedure(Configuration.UPDATE_ACTIVE_ALERT_QUREY,n.getRuleId(),n.getImsi());
				updatemessage.stream().forEach(p->System.out.println("Alert rule message is:-"+p.getMessage()+":: amd ISMI is:-" + n.getImsi())); 				
			});
		}
		
		Set<ValidateRule> validRule= RuleIntiatorV2.validRule.stream().filter(distinctByKeys(ValidateRule::getImsi))
		.collect(Collectors.toSet());
		
		validRule.remove(RuleIntiatorV2.invalidateRule);
		validRule.removeIf(n -> activeAlertResult.stream().anyMatch(
				p -> (p.getImsi().equalsIgnoreCase(n.getImsi()) && p.getRuleId() == n.getRuleId())));

		RuleIntiatorV2.invalidateRule.removeIf(n -> activeAlertResult.stream().anyMatch(
				p -> (!p.getImsi().equalsIgnoreCase(n.getImsi()) && p.getRuleId() != n.getRuleId())));
		
		
		StringBuilder sb = new StringBuilder();
		validRule.forEach(n -> {
			sb.append("('" + n.getImsi() + "','" + n.getRuleId() + "','" + ruleCategory
					+ "',now(),'0'),");
		});

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.insert(0, Configuration.INSERT_ACTIVE_ALERT_QUREY);
			System.out.println("insert query to active alert:-" + sb.toString());

			RuleIntiatorV2.genericService.executeAnySqlQuery(sb.toString());
		}
		/*StringBuilder invalidSb = new StringBuilder();
		RuleIntiatorV2.invalidateRule.forEach(n -> {
			invalidSb.append(Configuration.UPDATE_ACTIVE_ALERT_QUREY.replaceAll("<imsi>", n.getImsi())
					.replaceAll("<rule_id>", String.valueOf(n.getRuleId())));
		});
		if (RuleIntiatorV2.invalidateRule.size() > 0) {
			/*System.out.println("invalidSb update query:-" + invalidSb.toString());
			RuleIntiatorV2.genericService.executeSqlQuery(invalidSb.toString());			
		}*/		
		if (validRule.size() > 0)
		{
			System.out.println("imsiList for email sent::"+String.join(",",validRule.stream().map(ValidateRule::getImsi).collect(Collectors.toList())));
			HashMap <String,String> mailObject=new HashMap<>();
			mailObject.put("imsiList", String.join(",",validRule.stream().map(ValidateRule::getImsi).collect(Collectors.toList())));
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			mailObject.put("occuranceTime", dateFormat.format(date));
			mailObject.put("rulename", ruleName);
			mailObject.put("description", RuleDescription);
			actions.forEach(action -> {
				if (action.getActionType().equalsIgnoreCase("EMAIL"))
				{
					StringBuilder mailercontent = new StringBuilder();
					mailercontent.append(action.getActionDescription() + "\n");
					mailercontent.append("valid rule for these rule:-" + validRule.toString());
					//mailer.sendMail(action.getActionSubject(), mailercontent.toString(),action.getEmailId(),mailObject);
					mailer.sendMail(ruleCategoryName, mailercontent.toString(),action.getEmailId(),mailObject);
				}
				if (action.getActionType().equalsIgnoreCase("SIM STATE Change"))
				{
					SimStateChangeApi simStateChange=new SimStateChangeApi();
					Map<String,Object> reponseData=simStateChange.SimStateAuthenticationApi();
					int error_code=(int)reponseData.get("Response_Code");
					String authToken=(String)reponseData.get("Response_Status");
					if(error_code==200)
					{					
						Map<String,Object> mapObj=simStateChange.SimStateChangeResponse(authToken, validRule.stream().map(ValidateRule::getImsi).collect(Collectors.toList()), action.getStateChange());
					    if((int)mapObj.get("Response_Code")==200)
					    {
					    	System.out.println("SIM status has been updated with response:::"+mapObj.get("Response_Status"));	
					    }
					    else
					    {
					    	System.out.println("SIM status has not been updated.Please check response error message:::"+mapObj.get("Response_Status")+"::: with response code::"+mapObj.get("Response_Code"));		
					    }
					}
					else
					{
						System.out.println("Token is not generated with response code::"+error_code+":: with error message::"+authToken);
					}
					}
			});
		}
	}
	
	/* Created actionForInactiveRule method would be updated database table once input parameters of method are invalidated rules data */	
	public static void actionForInactiveRule(List<ActiveAlert> activeAlertResult,int ruleId) 
	{		
		System.out.println("inside actionForInactiveRule condition:::");
		Set<String> invalidRulesData = new HashSet<>();		
		invalidRulesData.addAll(RuleIntiatorV2.invalidateRule.stream().map(InvalidRules::getImsi).collect(Collectors.toSet()));
		invalidRulesData.addAll(RuleIntiatorV2.validRule.stream().map(ValidateRule::getImsi).collect(Collectors.toSet()));
		System.out.println("InactiveRule data list:-"+invalidRulesData);
		invalidRulesData.retainAll(activeAlertResult.stream().map(ActiveAlert::getImsi).collect(Collectors.toSet()));
		invalidRulesData.forEach(n -> {
			
			List<MessageVo> updatemessage=RuleIntiatorV2.removeInvalidRuleMsiFromProcedure(Configuration.UPDATE_ACTIVE_ALERT_QUREY,ruleId,n);
			updatemessage.stream().forEach(p->System.out.println("Alert rule message is:-"+p.getMessage()+":: amd ISMI is:-" + n)); 
			/*String invalidUpdateQuery = Configuration.UPDATE_ACTIVE_ALERT_QUREY.replaceAll("<imsi>", n)
					.replaceAll("<rule_id>", String.valueOf(ruleId));
			System.out.println("invalidSb update query:-" + invalidUpdateQuery);
			RuleIntiatorV2.genericService.executeAnySqlQuery(invalidUpdateQuery);*/
		});
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
