package com.bch.api.rest.services.client.soap.clientes;

import org.apache.log4j.Logger;
import com.bch.api.rest.dto.ServicioResponseDTO;
import com.bch.api.rest.dto.SoapSecurityDTO;
import com.bch.api.rest.dto.SoapXMLNsDTO;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.properties.ServicioTokenProperties;
import com.bch.api.rest.services.TokenCore;
import com.bch.api.rest.utils.FuncionesGenerales;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Clase principal para ejecutar en servicio Core
 * @author 160k
 *
 */
public class ClienteCore {

 private static final Logger LOGGER = Logger.getLogger(ClienteCore.class);
 
 /** Properties del servicio
 */
 private ServicioTokenProperties prop;
  
 public ClienteCore(ServicioTokenProperties prop) {
  this.prop = prop;
 }
 
  /**
  * Método principal para ejecutar un servicio core (cago, abono)
  * @param nombreServ nombre del servicio "cargo", "abono"
  * @param transB
  * @param em
  * @return
  */
 @SuppressWarnings("null")
 public ServicioResponseDTO ejecutarServicioCore(String nombreServ, Transaccion transB, Emisor em, 
		 String core, String origen)  
 {
  ServicioResponseDTO srv = new ServicioResponseDTO();
  
  //----- Obtener el token
  TokenCore tk = new TokenCore(prop);
  String token = tk.obtenerToken(nombreServ);
  LOGGER.debug("token: |||"+token+"|||");
  
  String soapURLS = prop.getProp().getServicioUrlServicioS();

  SoapSecurityDTO securityS = new SoapSecurityDTO();
  securityS.setWsseUrlSecuritySecext(prop.getProp().getServicioUrlSecuSecExtS());
  securityS.setWsseUrlSecuritySoapMessage(prop.getProp().getServicioUrlSecuMessageS());
  securityS.setWsseUrlSecurityUsernameToken(prop.getProp().getServicioUrlSecuUsrNameTokenS());
  securityS.setWsseUrlSecurityUtility(prop.getProp().getServicioUrlSecuUtilityS());
  securityS.setWsseUser(prop.getProp().getServicioSecuUserS());
  securityS.setWssePass(prop.getProp().getServicioSecuUserPasswordS());
  
  
  //---- Datos generación de los OSB del XML
  SoapXMLNsDTO xmlnsS = new SoapXMLNsDTO();

  xmlnsS.setNs(prop.getProp().getServicioOsbNsS());
  xmlnsS.setNs1(prop.getProp().getServicioOsbNs1S());
  xmlnsS.setMpi(prop.getProp().getServicioOsbMpiS());
        
     try 
     {
      //-------- Datos a consultar del servicio ------------
     
      String requestXML = FuncionesGenerales.generaRequest(transB, securityS, xmlnsS, em, prop,core,origen);
   
      OkHttpClient client = new OkHttpClient().newBuilder().build();
      MediaType mediaType = MediaType.parse("text/xml;charset=UTF-8");
        
      @SuppressWarnings("deprecation")
   RequestBody body = RequestBody.create(mediaType, requestXML);
      LOGGER.debug("body: "+requestXML);
      LOGGER.debug("soapURL: '"+soapURLS+"'");
      LOGGER.debug("getServicioSoapAction: '"+prop.getProp().getServicioSoapActionS()+"'");
      LOGGER.debug("getServicioDominio: '"+prop.getProp().getServicioDominio()+"'");
      
      
      
   Request request = new Request.Builder()
       .url(soapURLS)
       .method("POST", body)
       .addHeader("Accept-Encoding", "gzip,deflate")
       .addHeader("Content-Type", "text/xml;charset=UTF-8")
       .addHeader("SOAPAction", prop.getProp().getServicioSoapActionS())       
       .addHeader("Authorization", "Bearer "+token)
       .addHeader("Content-Length", "4245")
       .addHeader("Host", prop.getProp().getServicioDominio())
       .addHeader("Connection", "Keep-Alive")
       
       .build();

     FuncionesGenerales.llamarClienteSOAP(client, request, srv);
  }
     catch (Exception e) 
     {
      LOGGER.debug("Error ex: "+e.getMessage());
      LOGGER.error("Error stack: "+e);
         srv = null;
     }
  
  return srv;
 }
 
 
}