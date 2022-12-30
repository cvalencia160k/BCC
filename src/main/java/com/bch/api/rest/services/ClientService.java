package com.bch.api.rest.services;

import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import com.bch.api.rest.dal.TransaccionDAL;
import com.bch.api.rest.dto.NotificarCCADTO;
import com.bch.api.rest.dto.ResponseResetCCADTO;
import com.bch.api.rest.dto.ServicioResponseDTO;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.factory.FactoryDAL;
import com.bch.api.rest.properties.ServicioConsultaProperties;
import com.bch.api.rest.properties.ServicioNotificacionProperties;
import com.bch.api.rest.properties.ServicioTokenProperties;
import com.bch.api.rest.services.client.soap.clientes.ClienteConsulta2;
import com.bch.api.rest.services.client.soap.clientes.ClienteCore;

/**
 * Cliente para conectarse a un servicio particular
 * @author 160k
 *
 */
public class ClientService {

 private TransaccionDAL tranDAL;
 
 private static final Logger LOGGER = Logger.getLogger(ClientService.class);

 /**
  * Constructores
  */
 public ClientService(TransaccionDAL tranDAL) 
 {
  this.tranDAL = tranDAL;
 }
 
 public Callable<ServicioResponseDTO> ejecutarServicio(
		   FactoryDAL fd,
		   Transaccion tranBD,
		   String nombreServicio, 
		   String codigoIdTrx,
		   Object propServ,
		   String origen
		   ) 
		 {
		String strAbono = "ABONO";
 		String strCargo = "CARGO";
 		String strConsulta = "CONSULTA";

		     return new Callable<ServicioResponseDTO>() {
		         @Override
		         public ServicioResponseDTO call() throws Exception {
		          
		          ServicioResponseDTO respServ = new ServicioResponseDTO();
		          
		          if(nombreServicio.equals(strConsulta)) 
		          { 
		           respServ = servicioBusConsulta306((ServicioConsultaProperties)propServ, tranDAL, codigoIdTrx); 
		          }
		          
		          if(nombreServicio.equals(strCargo)) 
		          {
		           respServ = servicioBusCargo406(fd, tranBD, (ServicioTokenProperties)propServ, origen);
		          }
		          
		          if(nombreServicio.equals(strAbono)) 
		          {
		           respServ = servicioBusAbono506(fd, tranBD, (ServicioTokenProperties)propServ, origen);
		          }      
		             return respServ;
		         }
		     };
		 }
 
 
 public Callable<ServicioResponseDTO> ejecutarServicioNotificar(
		   String nombreServicio, 
		   Object propServ
		   ) 
		 {
	 		String strNotificaion = "NOTIFICACION";
		     return new Callable<ServicioResponseDTO>() {
		         @Override
		         public ServicioResponseDTO call() throws Exception {
		          
		          ServicioResponseDTO respServ = new ServicioResponseDTO();
		          
		        
		          if(nombreServicio.equals(strNotificaion)) 
		          {
		           respServ = servicioNotificacionCCA((ServicioNotificacionProperties)propServ);
		          }

		             return respServ;
		         }
		     };
		 }
 
 
 /**
  * Servicio para conectarse al BusConsulta306
  */
 public ServicioResponseDTO servicioBusConsulta306(ServicioConsultaProperties prop,
   TransaccionDAL tranDAL, String codigoIdTrx) 
 {
  ServicioResponseDTO respServ = new ServicioResponseDTO();
  
  ClienteConsulta2 cli = new ClienteConsulta2(prop, tranDAL);
  
  respServ = cli.ejecutarConsulta(codigoIdTrx);
  
  return respServ;
 }
 
 /**
  * Servicio para conectarse al BusCargo406
  */
 public ServicioResponseDTO servicioBusCargo406(FactoryDAL fd, Transaccion tranBD, 
   ServicioTokenProperties prop, String origen)
 {
  try {
  ServicioResponseDTO respServ = new ServicioResponseDTO();
  
  ClienteCore cli = new ClienteCore(prop);
  
  Emisor em = fd.getEmiDAL().obtenerEmisor(Integer.parseInt(tranBD.getCodInstitucion()));
    
  respServ = cli.ejecutarServicioCore("cargo", tranBD, em, "Cargo", origen);

  return respServ;
  
  }catch(Exception ex) {
  
   LOGGER.error("Error BuscarCargo406:" + ex);
   return new ServicioResponseDTO();
  }
 }
 
 /**
  * Servicio para conectarse al BusAbono506
  */
 public ServicioResponseDTO servicioBusAbono506(FactoryDAL fd, Transaccion tranBD, 
   ServicioTokenProperties prop, String origen)
 {
  try {
  ServicioResponseDTO respServ = new ServicioResponseDTO();
  
  ClienteCore cli = new ClienteCore(prop);
  
  Emisor em = fd.getEmiDAL().obtenerEmisor(Integer.parseInt(tranBD.getCodInstitucion()));
    
  respServ = cli.ejecutarServicioCore("abono", tranBD, em, "Abono", origen);

  return respServ;
  
  }catch(Exception ex) {
  
   LOGGER.error("Error BuscarAbono506:" + ex);
   return new ServicioResponseDTO();
  }
 }
 
 public ServicioResponseDTO servicioNotificacionCCA( 
   ServicioNotificacionProperties prop)
 {
  try {
   
  ServicioResponseDTO respServ = new ServicioResponseDTO();
  ResponseResetCCADTO objResp = new ResponseResetCCADTO();
  
  //---- Notificación CCA (notificar ante trx procesada o rechazada)
  NotificarCCADTO notiDto = new NotificarCCADTO();   
  notiDto.setTipoAuth(prop.getProp().getCcaResetTipoAuth());
  notiDto.setGrandType(prop.getProp().getCcaTokenGrandType());
  notiDto.setUrlToken(prop.getProp().getCcaTokenUrl());
  notiDto.setUrlReset(prop.getProp().getCcaResetTokenUrl());
  notiDto.setClientId(prop.getProp().getCcaClientId());
  notiDto.setClientSecret(prop.getProp().getCcaClientSecret());
  notiDto.setCodInstitucion(prop.getProp().getCcaCodInstitucion());
  notiDto.setCodTrx(prop.getProp().getCcaCodTrx());
  notiDto.setIndicadorReset(prop.getProp().getCcaIndicadorReset());
  notiDto.setNumeroReintentos(prop.getProp().getCcaNuemeroReintentos());
  LOGGER.debug("notificacion CCA TipoAuth" + notiDto.getTipoAuth());
  LOGGER.debug("notificacion CCA GrandType" + notiDto.getGrandType());
  LOGGER.debug("notificacion CCA UrlToken" + notiDto.getUrlToken());
  LOGGER.debug("notificacion CCA UrlReset" + notiDto.getUrlReset());
  LOGGER.debug("notificacion CCA ClientId" + notiDto.getClientId());
  LOGGER.debug("notificacion CCA ClientSecret" + notiDto.getClientSecret());
  LOGGER.debug("notificacion CCA CodInstitucion" + notiDto.getCodInstitucion());
  LOGGER.debug("notificacion CCA CodTrx" + notiDto.getCodTrx());
  LOGGER.debug("notificacion CCA IndicadorReset" + notiDto.getIndicadorReset());
  LOGGER.debug("notificacion CCA NumeroReintentos" + notiDto.getNumeroReintentos());

  NotificarCCA noti = new NotificarCCA(notiDto);
  objResp = noti.notificar();
  
  respServ.setCodigoRespuesta(objResp.getCodResp());

  return respServ;
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error Servicio Notificacion CCA:" + ex);
   return new ServicioResponseDTO();
  }
 }


 
}
