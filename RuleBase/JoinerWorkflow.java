import java.util.ArrayList;
import java.util.List;

import sailpoint.api.SailPointContext;
import sailpoint.object.Identity;
import sailpoint.object.ProvisioningPlan;
import sailpoint.object.ProvisioningPlan.AccountRequest;
import sailpoint.object.ProvisioningPlan.AttributeRequest;
import sailpoint.tools.GeneralException;


public class JoinerWorkflow {
	public static void main(String...args ) {
		String identityName ="Adam.Kennedy";
		SailPointContext context = null;
		try {
			Identity identityObject = context.getObjectByName(Identity.class,identityName);
			ProvisioningPlan plan = new ProvisioningPlan();
			
			if(identityObject.getAttribute("status").equals("Employee")) {
				
				List acccReqs = new ArrayList();
				
				AccountRequest acctReq = new AccountRequest();
				acctReq.setOperation(AccountRequest.Operation.Create);
				acctReq.setApplication("AD");
				
				acctReq.add( new AttributeRequest("samAccountName",identityName));
				acctReq.add(new AttributeRequest("ObjectType","User"));
				acctReq.add(new AttributeRequest("IIQDisabled",false));
				acctReq.setNativeIdentity(identityName);
				
				acccReqs.add(acctReq);
				plan.setAccountRequests(acccReqs);
				plan.setIdentity(identityObject);
			//	System.out.println(plan.toXml());
			}
	//		return plan;
			
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
