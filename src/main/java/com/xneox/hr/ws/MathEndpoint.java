package com.xneox.hr.ws;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MathEndpoint {
	
	private static final String NAMESPACE_URI = "http://mycompany.com/hr/schemas";

	private XPath aExpression;

	private XPath bExpression;
	
	
	public MathEndpoint() throws JDOMException {
		Namespace namespace = Namespace.getNamespace("sch", NAMESPACE_URI);

		aExpression = XPath.newInstance("//sch:a");
		aExpression.addNamespace(namespace);

		bExpression = XPath.newInstance("//sch:b");
		bExpression.addNamespace(namespace);
		
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SumRequest")                  
	 public @ResponsePayload Element handleHolidayRequest(@RequestPayload Element sumrequest)                
	      throws Exception {
		Namespace namespace = Namespace.getNamespace("sch", NAMESPACE_URI);
		
	    int a = Integer.valueOf(aExpression.valueOf(sumrequest));
	    int b = Integer.valueOf(bExpression.valueOf(sumrequest));
	    

	    Element el = new Element("SumResponse");
	    el.setAttribute("RESULT", "1");
	    el.addNamespaceDeclaration(namespace);
	    
	    // build the response XML with JDOM
        Element root = new Element("SumResponse", namespace);
        Element echoResponse = new Element("SumResponse", namespace);
        echoResponse.setText(String.valueOf(a+b));
        root.addContent(echoResponse);
        Document doc = new Document(root);
        
        return doc.getRootElement();
	  }
	
}
