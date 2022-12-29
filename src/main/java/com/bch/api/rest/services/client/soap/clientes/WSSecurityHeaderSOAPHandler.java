package com.bch.api.rest.services.client.soap.clientes;

import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class WSSecurityHeaderSOAPHandler implements SOAPHandler<SOAPMessageContext> {

 private static final Logger LOGGER = Logger.getLogger(WSSecurityHeaderSOAPHandler.class);


 	@Value( "${wss.SOAP_ELEMENT_SECRET}" )
    private static String SOAP_ELEMENT_SECRET;
 	
 	@Value( "${wss.SOAP_ELEMENT_USERNAME}" )
    private static String SOAP_ELEMENT_USERNAME;
 	
 	@Value( "${wss.SOAP_ELEMENT_USERNAME_TOKEN}" )
    private static  String SOAP_ELEMENT_USERNAME_TOKEN;
 	
 	@Value( "${wss.SOAP_ELEMENT_SECURITY}" )
    private static  String SOAP_ELEMENT_SECURITY;
 	
 	@Value( "${wss.NAMESPACE_SECURITY}" )
    private static  String NAMESPACE_SECURITY;
 	
 	@Value( "${wss.PREFIX_SECURITY}" )
    private static  String PREFIX_SECURITY;

    private String usernameText;
    private String passwordText;

    private String nonceText;
    private String createText;

    public WSSecurityHeaderSOAPHandler(String usernameText, String passwordText, 
      String nonceText, String createText) {
        this.usernameText = usernameText;
        this.passwordText = passwordText;
        this.nonceText = nonceText;
        this.createText = createText;
    }

    public WSSecurityHeaderSOAPHandler(String usernameText, String passwordText) {
        this.usernameText = usernameText;
        this.passwordText = passwordText;
    }

    public boolean handleMessage(SOAPMessageContext soapMessageContext) {

        Boolean outboundProperty = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {

            try {
                SOAPEnvelope soapEnvelope = soapMessageContext.getMessage().getSOAPPart().getEnvelope();

                SOAPHeader header = soapEnvelope.getHeader();
                if (header == null) {
                    header = soapEnvelope.addHeader();
                }

                SOAPElement soapElementSecurityHeader = header.addChildElement(SOAP_ELEMENT_SECURITY, 
                  PREFIX_SECURITY, NAMESPACE_SECURITY);

                SOAPElement soapElementUsernameToken = soapElementSecurityHeader.addChildElement(
                  SOAP_ELEMENT_USERNAME_TOKEN, PREFIX_SECURITY);
                SOAPElement soapElementUsername = soapElementUsernameToken.addChildElement(SOAP_ELEMENT_USERNAME,
                  PREFIX_SECURITY);
                soapElementUsername.addTextNode(this.usernameText);

                SOAPElement soapElementPassword = soapElementUsernameToken.addChildElement(SOAP_ELEMENT_SECRET,
                  PREFIX_SECURITY);
                soapElementPassword.setAttribute("Type",
                  "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
                soapElementPassword.addTextNode(this.passwordText);

                if (nonceText != null) {
                    SOAPElement soapElementNonce = soapElementUsernameToken.addChildElement("Nonce", PREFIX_SECURITY);
                    soapElementNonce.addTextNode(this.nonceText);
                }

                if (nonceText != null) {
                    SOAPElement soapElementCreated = soapElementUsernameToken.addChildElement("Created", "wsu", 
                      "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                    soapElementCreated.addTextNode(this.createText);
                }

            } catch (Exception e) {
                
                LOGGER.error("Error on wsSecurityHandler: " + e);
            }

        }

        return true;
    }

    @Override
    public void close(MessageContext context) {
      LOGGER.debug("close WSSecurityHeaderSOAPHandler");
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
      return true;
    }

    @Override
    public Set<QName> getHeaders() {
      return (Set<QName>) new QName("nohay");
    }
}
