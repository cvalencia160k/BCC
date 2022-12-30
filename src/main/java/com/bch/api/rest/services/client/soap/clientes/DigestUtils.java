package com.bch.api.rest.services.client.soap.clientes;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;


/**
 * DigestUtils
 * @author oracle
 */

public class DigestUtils {

 private static final Logger LOGGER = Logger.getLogger(DigestUtils.class);

 private static final String  ALGORITMO ="SHA-1";
 
 /**
  * Constructor
  */
 private DigestUtils() {
  LOGGER.error("inicio Digest");
 }
 
    public static String createNonce() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public static String createTimestamp() 
    {
        XMLGregorianCalendar now = null;
        try 
        {
            DatatypeFactory xmlDatatypeFactory = DatatypeFactory.newInstance();
            now = xmlDatatypeFactory.newXMLGregorianCalendar(new GregorianCalendar(TimeZone.getTimeZone("UTC")));
            
            return now.toXMLFormat();
        } 
        catch (DatatypeConfigurationException dtce) 
        {
            LOGGER.error("Error Digest:" + dtce);
            return "";
        }
    }

    public static String createDigest(String base64EncodedNonce, String utf8Timestamp,
      String utf8password)  {
        MessageDigest md = null;

        try 
        {
 // Ensure SHA-1 algorithm is supported
      md = MessageDigest.getInstance(ALGORITMO);

 // Decode the incoming Base64 encoded nonce
         byte[] decodedNonce = null;
         if (null != base64EncodedNonce) {
 
             decodedNonce = DatatypeConverter
                     .parseBase64Binary(base64EncodedNonce);
 
         }
 // Get the timestamp in bytes
         byte[] utf8BytesTimestamp = null;
 
 // the created date and utf8Timestamp are assumed to be utf-8 encoded
         utf8BytesTimestamp = utf8Timestamp.getBytes("utf-8");
 
 // Get the password in bytes
         byte[] utf8BytesPassword = null;
 
 // the created date and utf8Timestamp are assumed to be utf-8 encoded
         utf8BytesPassword = utf8password.getBytes("utf-8");
 
 // Update the digest with the byte arrays and then encode in base64
 // Hashing formula is: Base64( SHA-1( nonce + created + password ))
         md.update(decodedNonce);
         md.update(utf8BytesTimestamp);
         md.update(utf8BytesPassword);
         return DatatypeConverter.printBase64Binary(md.digest());
        }
        catch(Exception ex) 
        {
         LOGGER.error("error digest:" + ex);
         
         if(md != null) 
         {
          return DatatypeConverter.printBase64Binary(md.digest());
         }
         else 
         {
          return "";
         }
        }
    }

}
