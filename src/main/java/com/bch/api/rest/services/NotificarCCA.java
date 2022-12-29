package com.bch.api.rest.services;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.bch.api.rest.dto.NotificarCCADTO;
import com.bch.api.rest.dto.RequestResetCCADTO;
import com.bch.api.rest.dto.RequestTokenCCADTO;
import com.bch.api.rest.dto.ResponseResetCCADTO;

/**
 * Clase principal para notificar a la CCA
 * @author 160k
 *
 */
public class NotificarCCA {

 private static final Logger LOGGER = Logger.getLogger(NotificarCCA.class);
 
 /**
  * Objeto de respuesta de la notificación
  */
 private ResponseResetCCADTO respObj;
 
 /**
  * Datos para la notificación
  */
 private NotificarCCADTO notifica;
 
 /**
  * Constructor de la clase
  */
 public NotificarCCA() 
 {
  this.respObj = new ResponseResetCCADTO();
 }
  
 
 /**
  * Constructor
  * @param notifica datos de la notificación
  */
 public NotificarCCA(NotificarCCADTO notifica) 
 {
  this.notifica = notifica;
 }
 
 /**
  * Método principal para notificar según parámetros enviados
  * @throws IOException
  * @throws InterruptedException
  */
 public ResponseResetCCADTO notificar()
 {
  String tokenCCA = "";
  try
  {
	  
 LOGGER.debug("URL: "+ notifica.getUrlToken());
 LOGGER.debug("ClienteID: "+notifica.getClientId());
 LOGGER.debug("ClientSecret: "+ notifica.getClientSecret());
 LOGGER.debug("GranType: "+notifica.getGrandType());

	  
   RequestTokenCCADTO reqTokenCCA = new RequestTokenCCADTO(
     notifica.getUrlToken(), 
     notifica.getClientId(), 
     notifica.getClientSecret(), 
     notifica.getGrandType());
   TokenCCA tcca = new TokenCCA(reqTokenCCA);
   tokenCCA = tcca.obtenerToken();
   
   this.notifica.setToken(tokenCCA);
   
   LOGGER.debug("tokenCCA: "+tokenCCA);
 
   this.ejecutarNotificacion();
  }
  catch(Exception ex) 
  {
   RequestTokenCCADTO reqTokenCCA = new RequestTokenCCADTO(
     notifica.getUrlToken(), 
     notifica.getClientId(), 
     notifica.getClientSecret(), 
     notifica.getGrandType());
   TokenCCA tcca = new TokenCCA(reqTokenCCA);
   tokenCCA = tcca.obtenerToken();
   this.notifica.setToken(tokenCCA);
   
   LOGGER.debug("tokenCCA: "+tokenCCA);
   
   //---- Reintentos en caso de error técnico
   int reintentos = notifica.getNumeroReintentos();
   for(int r=1; r<=reintentos; r++)
   {
    this.ejecutarNotificacion();
    LOGGER.error("error notificar: "+ex);
   }
  }
  
  return respObj;
 }
 
 /**
  * Ejecución de la notificación y obtención de respuesta
  */
 public void ejecutarNotificacion() 
 {
  RequestResetCCADTO reqResetCCA = new RequestResetCCADTO();
  reqResetCCA.setTipoAuth(notifica.getTipoAuth());
  reqResetCCA.setUrl(notifica.getUrlReset());
  reqResetCCA.setToken(notifica.getToken());
  reqResetCCA.setClientId(notifica.getClientId());
  reqResetCCA.setClientSecret(notifica.getClientSecret());
  reqResetCCA.setCodTrx(notifica.getCodTrx());
  reqResetCCA.setIndicadorReset(notifica.getIndicadorReset());
  reqResetCCA.setCodInstitucion(notifica.getCodInstitucion());

  LOGGER.debug("reqResetCCA: "+reqResetCCA);
  ResetCCA reset = new ResetCCA(reqResetCCA);
  this.respObj = reset.ejecutarReset();
  LOGGER.debug("respReset: "+respObj);
 }
 
}