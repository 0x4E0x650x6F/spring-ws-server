package com.xneox.hr.ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import com.xneox.hr.HumanResourceService;

@Endpoint
public class HolidayEndpoint {

	private static final String NAMESPACE_URI = "http://mycompany.com/hr/schemas";

	private XPath startDateExpression;

	private XPath endDateExpression;

	private XPath nameExpression;

	private HumanResourceService humanResourceService;

	@Autowired
	public HolidayEndpoint(HumanResourceService humanResourceService)
			throws JDOMException {
		this.humanResourceService = humanResourceService;

		Namespace namespace = Namespace.getNamespace("sch", NAMESPACE_URI);

		startDateExpression = XPath.newInstance("//sch:StartDate");
		startDateExpression.addNamespace(namespace);

		endDateExpression = XPath.newInstance("//sch:EndDate");
		endDateExpression.addNamespace(namespace);

		nameExpression = XPath
				.newInstance("concat(//sch:FirstName,' ',//sch:LastName)");
		nameExpression.addNamespace(namespace);
	}

	@PayloadRoot(namespace = "http://mycompany.com/hr/schemas", localPart = "HolidayRequest")
	public void handleHolidayRequest(@RequestPayload Element holidayRequest)
			throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = dateFormat.parse(startDateExpression
				.valueOf(holidayRequest));
		Date endDate = dateFormat.parse(endDateExpression
				.valueOf(holidayRequest));
		String name = nameExpression.valueOf(holidayRequest);

		humanResourceService.bookHoliday(startDate, endDate, name);
		
	}

}
