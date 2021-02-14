import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sailpoint.api.SailPointContext;
import sailpoint.api.Terminator;
import sailpoint.object.Certification;
import sailpoint.object.CertificationGroup;
import sailpoint.object.Filter;
import sailpoint.object.QueryOptions;
import sailpoint.tools.GeneralException;

public class ContractorCompCertRule {

	public static void main(String[] args) throws GeneralException {
		// Author Sidhaant
		
		SailPointContext context = null;
		Terminator terminator = new Terminator(context);
		List ac = new ArrayList();
		List certList = new ArrayList();
		int CGcount = 0;
		int inactiveElementTobeDeleted = 0;
		Iterator certIA = context.search(CertificationGroup.class, null, "id");
		while (certIA.hasNext()) {
			Object[] abc = (Object[]) certIA.next();
			ac.add(abc[0]);
		}
	
		for (Object eachCG : ac) {
			System.out.println("Entering Certification Logic");
			Filter f1 = Filter.eq("certificationGroups.id", eachCG);
			Filter f2 = Filter.eq("phase", "End");
			Filter f3 = Filter.eq("complete", true);
			Filter f4 = Filter.eq("type", "Manager");
			Filter f5 = Filter.eq("manager", "io7000");
			QueryOptions qo = new QueryOptions();
			qo.add(f1);
			qo.add(f2);
			qo.add(f3);
			qo.add(f4);
			qo.add(f5);
			Iterator certJH = context.search(Certification.class, qo);
			int count = 0;
			while (certJH.hasNext()) {
				Certification cert = (Certification) certJH.next();

				int totalItems = cert.getStatistics().getTotalItems();

				List currentEntity = cert.getEntities();

				if (!currentEntity.isEmpty() && totalItems != 0) {
					// certList.add(cert.getId());

					if (certList.size() < 2300) {

						if (null != cert.getId()) {
							certList.add(cert.getId());
							try {
								if (null != cert && cert.getCertification() != null) {
									terminator.deleteObject(cert.getCertification(cert.getId()));
									System.out.println("Certification Deleted");
								} else {
									continue;
								}

							} finally {
								context.commitTransaction();
							}

							inactiveElementTobeDeleted++;
						}

					} else {

						break;

					}

				}

			}

		}
		CGcount = ac.size();
		System.out.println("Certification Group Count " + CGcount);
		System.out.println("Inactive Certificate  Count" + inactiveElementTobeDeleted);

		System.out.println("List added:" + certList);

		System.out.println("TotalItem Count:" + certList.size());
		// return "success";

	}

}
