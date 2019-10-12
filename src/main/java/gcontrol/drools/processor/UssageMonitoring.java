package gcontrol.drools.processor;
import java.util.Collection;
import java.util.List;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import gcontrol.rule.model.InvalidRules;
import gcontrol.rule.model.Status;
import gcontrol.rule.model.Trigger;
import gcontrol.rule.model.ValidateRule;
import gcontrol.rules.configuration.Configuration;
import gcontrol.rules.initiator.RuleIntiatorV2;
/* 
 * Created by the Vipendra Kumar
 * UssageMonitoring class has been written for validating business rule from the drool rule engine
 */
public class UssageMonitoring {

	private UssageMonitoring() {}
	/*
	 * Method executeData is validated condition all of the data from the drool rule engine 
	 * whether it's Pass/fail by using Rule Category/Rule Parameter when tigger is fired
	 */
	  @SuppressWarnings("unchecked")
	public static  <T> void executeData( List<T>  datamodel,Status status,Trigger trigger) 
	  {		
		
		  String kieSessionName=trigger.getRuleCategoryName()+"_"+trigger.getRuleParameter();		  
		  KieContainer kieClasspathContainer = KieServices.Factory.get().getKieClasspathContainer();
		  if(trigger.getRuleCategoryName().equalsIgnoreCase(Configuration.CONNECTIVITY_MONITORING)) 
		  {
			  kieSessionName=trigger.getRuleCategoryName()+"_"+trigger.getRuleDefaultCondition();
		  }
		  if(trigger.getRuleParameter()!=null)
		  {
		  if(trigger.getRuleParameter().equalsIgnoreCase(Configuration.ALL_SERVICES)) 
		  {
		  kieSessionName=trigger.getRuleCategoryName()+"_"+trigger.getRuleParameter()+"_"+  trigger.getRuleCondition();
		  }
		 }
		  if(trigger.getRuleCategoryName().equalsIgnoreCase(Configuration.IMEI_TRACKING) && trigger.getRuleParameter()==null) 
		  {
			  kieSessionName=trigger.getRuleCategoryName()+"_"+trigger.getRuleDefaultCondition();
		  }
		  System.out.println("kieSessionName ::"+kieSessionName);
		  KieSession ksession = kieClasspathContainer.newKieSession(kieSessionName);
		    datamodel.forEach(ksession::insert);
		    RuleIntiatorV2.validRule.forEach(ksession::insert);
		    RuleIntiatorV2.invalidateRule.forEach(ksession::insert);
		    ksession.insert(trigger);
		    ksession.insert(status);
	    
	    System.out.println("==== DROOLS ENGINE SESSION START ==== ");
	    ksession.fireAllRules();	    
	    RuleIntiatorV2.validRule.addAll((Collection<gcontrol.rule.model.ValidateRule>) ksession.getObjects(o->o.getClass()==ValidateRule.class));
	    RuleIntiatorV2.invalidateRule.addAll((Collection<InvalidRules>)ksession.getObjects(o->o.getClass()==InvalidRules.class));	   
	    ksession.dispose();
	    System.out.println("==== DROOLS ENGINE SESSION END ==== ");
	  }

}
