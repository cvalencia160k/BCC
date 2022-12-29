package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.TransaccionDAL;
import com.bch.api.rest.dto.RequestTokenCargoDTO;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseResetCCADTO;
import com.bch.api.rest.dto.ServicioResponseDTO;
import com.bch.api.rest.dto.TransaccionDTO;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.enums.EventoTransaccion;
import com.bch.api.rest.factory.FactoryDAL;
import com.bch.api.rest.properties.ServicioConsultaProperties;
import com.bch.api.rest.properties.ServicioConsultaPropertiesDto;
import com.bch.api.rest.properties.ServicioJCRProperties;
import com.bch.api.rest.properties.ServicioJCRPropertiesDto;
import com.bch.api.rest.properties.ServicioNotificacionProperties;
import com.bch.api.rest.properties.ServicioNotificacionPropertiesDto;
import com.bch.api.rest.properties.ServicioTokenProperties;
import com.bch.api.rest.properties.ServicioTokenPropertiesDto;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.bch.api.rest.services.ClientService;
import com.bch.api.rest.services.client.soap.clientes.ClientJCR;
import com.bch.api.rest.utils.FuncionesGenerales;

import org.apache.commons.lang3.StringUtils;

/**
 * Capa de negocio de servicios encargada de llamar a los servicios
 * @author 160k
 *
 */
@Component
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class ServicioBL 
{
 
   private String keyStore = System.getProperty("weblogic.security.SSL.trustedCAKeyStore");
 
   @Value("${key.secret.enc}")
   private String secretKy;
   
   @Value("${keystore.password}")
   private String passKeyStore;
 
   @Autowired
   private TransaccionBL tranBL;
 
   @Autowired
   private FactoryDAL facDAL;
 
   private static final Logger LOGGER = Logger.getLogger(ServicioBL.class);
   private static String nombreServConsulta = "CONSULTA";
   private static String nombreServCargo = "CARGO";
   private static String nombreServAbono = "ABONO";
   private static String nombreServNotificacion = "NOTIFICACION";
   
   //----------------- CONSULTA ------------------------
   
   @Value("${ws.consulta.dominio}")
   private static String consultaDominio;
 
   @Value("${ws.consulta.usuario-consumidor}")
   private static String consultaUsuarioConsumidor;
 
   @Value("${ws.consulta.id-canal}")
   private static String consultaIdCanal;
 
   @Value("${ws.consulta.numero-sucursal}")
   private static String consultaNumeroSucursal;
 
   @Value("${ws.consulta.url-servicio}")
   private static String consultaUrlServicio;
 
   @Value("${ws.consulta.url-soapaction}")
   private static String consultaSoapAction;

   @Value("${ws.consulta.url-security-secext}")
   private String consultaUrlSecuSecExt;

   @Value("${ws.consulta.url-security-message}")
   private String consultaUrlSecuMessage;

   @Value("${ws.consulta.url-security-username-token}")
   private String consultaUrlSecuUsrNameToken;

   @Value("${ws.consulta.url-security-utility}")
   private String consultaUrlSecuUtility;

   @Value("${ws.consulta.url-security-user.enc}")
   private String consultaSecuUser;

   @Value("${ws.consulta.url-security-password.enc}")
   private String consultaSecuUserPassword;

   @Value("${ws.consulta.auth-usuario-servicio.enc}")
   private String consultaAuthUsuarioServicio;

   @Value("${ws.consulta.auth-password-servicio.enc}")
   private String consultaAuthPasswordServicio;

   @Value("${ws.consulta.auth-tipo-auth}")
   private String consultaAuthTipoAuth;

   @Value("${ws.consulta.osb-ns}")
   private String consultaOsbNs;

   @Value("${ws.consulta.osb-ns1}")
   private String consultaOsbNs1;

   @Value("${ws.consulta.osb-mpi}")
   private String consultaOsbMpi;

   //----------------- CARGO ------------------------
 
   @Value("${ws.cargo.token.url}")
   private String tokenCargoUrl;

   @Value("${ws.cargo.token.user.enc}")
   private String tokenCargoUser;

   @Value("${ws.cargo.token.password.enc}")
   private String tokenCargoPass;

   @Value("${ws.cargo.tipoAuth}")
   private String tipoAuthCargo;

   @Value("${ws.cargo.token.param.user.enc}")
   private String tokenCargoParamUser;

   @Value("${ws.cargo.token.param.passUser.enc}")
   private String tokenCargoParamPassUser;

   @Value("${ws.cargo.token.param.scope}")
   private String tokenCargoParamScope;

   @Value("${ws.cargo.token.param.grandType}")
   private String tokenCargoParamGrandType;

   //----------------- CARGO -----------------
 
   @Value("${ws.girarCuenta.dominio}")
   private String cargoDominio;
 
   @Value("${ws.girarCuenta.usuario-consumidor}")
   private String cargoUsuarioConsumidor;
 
   @Value("${ws.girarCuenta.id-canal}")
   private String cargoIdCanal;
 
   @Value("${ws.girarCuenta.numero-sucursal}")
   private String cargoNumeroSucursal;
 
   @Value("${ws.girarCuenta.prioridad}")
   private String cargoPrioridad;
 
   @Value("${ws.girarCuenta.rutOperadora}")
   private String cargoRutOperadora;
 
   @Value("${ws.girarCuenta.bancoDestino}")
   private String cargoBancoDestino;
 
   @Value("${ws.girarCuenta.bancoOrigen}")
   private String cargoBancoOrigen;
 
   @Value("${ws.girarCuenta.oficinaOrigenTx}")
   private String cargoOficinaOrigenTx;
 
   @Value("${ws.girarCuenta.canalOrigenTx}")
   private String cargoCanalOrigenTx;
 
   @Value("${ws.girarCuenta.moneda}")
   private String cargoMoneda;
  
   @Value("${ws.girarCuenta.codigoProductoFC}")
   private String cargoCodigoProductoFC;
  
   @Value("${ws.girarCuenta.nombreCampo}")
   private String cargoNombreCampo;
 
   @Value("${ws.girarCuenta.valorCampo}")
   private String cargoValorCampo;
 
   @Value("${ws.girarCuenta.nombreCampo2}")
   private String cargoNombreCampo2;
 
   @Value("${ws.girarCuenta.nombreCampo3}")
   private String cargoNombreCampo3;

   @Value("${ws.girarCuenta.url-servicio}")
   private String cargoUrlServicio;
 
   @Value("${ws.girarCuenta.url-soapaction}")
   private String cargoSoapAction;
 
   @Value("${ws.girarCuenta.url-security-secext}")
   private String cargoUrlSecuSecExt;
 
   @Value("${ws.girarCuenta.url-security-message}")
   private String cargoUrlSecuMessage;
 
   @Value("${ws.girarCuenta.url-security-username-token}")
   private String cargoUrlSecuUsrNameToken;
 
   @Value("${ws.girarCuenta.url-security-utility}")
   private String cargoUrlSecuUtility;
 
   @Value("${ws.girarCuenta.url-security-user.enc}")
   private String cargoSecuUser;
 
   @Value("${ws.girarCuenta.url-security-password.enc}")
   private String cargoSecuUserPassword;

   @Value("${ws.girarCuenta.auth-usuario-servicio.enc}")
   private String cargoAuthUsuarioServicio;
 
   @Value("${ws.girarCuenta.auth-password-servicio.enc}")
   private String cargoAuthPasswordServicio;
 
   @Value("${ws.girarCuenta.auth-tipo-auth}")
   private String cargoAuthTipoAuth;

   @Value("${ws.girarCuenta.osb-ns}")
   private String cargoOsbNs;
 
   @Value("${ws.girarCuenta.osb-ns1}")
   private String cargoOsbNs1;
 
   @Value("${ws.girarCuenta.osb-mpi}")
   private String cargoOsbMpi; 
 
   @Value("${ws.girarCuenta.emisor.codigoTransaccionFC}")
   private String codigoTransaccionFC;
  
   @Value("${ws.girarCuenta.emisor.codigoExtendidoFC}")
   private String codigoExtendidoFC;
 
   //----------------- CARGO ------------------------
 
   @Value("${ws.cargo.token.url}")
   private String tokenAbonoUrl;

   @Value("${ws.cargo.token.user.enc}")
   private String tokenAbonoUser;

   @Value("${ws.cargo.token.password.enc}")
   private String tokenAbonoPass;

   @Value("${ws.cargo.tipoAuth}")
   private String tipoAuthAbono;

   @Value("${ws.cargo.token.param.user.enc}")
   private String tokenAbonoParamUser;

   @Value("${ws.cargo.token.param.passUser.enc}")
   private String tokenAbonoParamPassUser;

   @Value("${ws.cargo.token.param.scope}")
   private String tokenAbonoParamScope;

   @Value("${ws.cargo.token.param.grandType}")
   private String tokenAbonoParamGrandType;
 
   //----------------- ABONO -----------------
 
   @Value("${ws.abonarCuenta.dominio}")
   private String abonarDominio;

   @Value("${ws.abonarCuenta.usuario-consumidor}")
   private String abonarUsuarioConsumidor;

   @Value("${ws.abonarCuenta.id-canal}")
   private String abonarIdCanal;

   @Value("${ws.abonarCuenta.numero-sucursal}")
   private String abonarNumeroSucursal;

   @Value("${ws.abonarCuenta.prioridad}")
   private String abonarPrioridad;

   @Value("${ws.abonarCuenta.rutOperadora}")
   private String abonarRutOperadora;

   @Value("${ws.abonarCuenta.bancoDestino}")
   private String abonarBancoDestino;

   @Value("${ws.abonarCuenta.bancoOrigen}")
   private String abonarBancoOrigen;

   @Value("${ws.abonarCuenta.oficinaOrigenTx}")
   private String abonarOficinaOrigenTx;

   @Value("${ws.abonarCuenta.canalOrigenTx}")
   private String abonarCanalOrigenTx;

   @Value("${ws.abonarCuenta.moneda}")
   private String abonarMoneda;
 
   @Value("${ws.abonarCuenta.codigoProductoFC}")
   private String abonarCodigoProductoFC;
 
   @Value("${ws.abonarCuenta.nombreCampo}")
   private String abonarNombreCampo;

   @Value("${ws.abonarCuenta.valorCampo}")
   private String abonarValorCampo;

   @Value("${ws.abonarCuenta.nombreCampo2}")
   private String abonarNombreCampo2;

   @Value("${ws.abonarCuenta.nombreCampo3}")
   private String abonarNombreCampo3;

   @Value("${ws.abonarCuenta.url-servicio}")
   private String abonarUrlServicio;

   @Value("${ws.abonarCuenta.url-soapaction}")
   private String abonarSoapAction;

   @Value("${ws.abonarCuenta.url-security-secext}")
   private String abonarUrlSecuSecExt;

   @Value("${ws.abonarCuenta.url-security-message}")
   private String abonarUrlSecuMessage;

   @Value("${ws.abonarCuenta.url-security-username-token}")
   private String abonarUrlSecuUsrNameToken;

   @Value("${ws.abonarCuenta.url-security-utility}")
   private String abonarUrlSecuUtility;

   @Value("${ws.abonarCuenta.url-security-user.enc}")
   private String abonarSecuUser;

   @Value("${ws.abonarCuenta.url-security-password.enc}")
   private String abonarSecuUserPassword;

   @Value("${ws.abonarCuenta.auth-usuario-servicio.enc}")
   private String abonarAuthUsuarioServicio;

   @Value("${ws.abonarCuenta.auth-password-servicio.enc}")
   private String abonarAuthPasswordServicio;

   @Value("${ws.abonarCuenta.auth-tipo-auth}")
   private String abonarAuthTipoAuth;

   @Value("${ws.abonarCuenta.osb-ns}")
   private String abonarOsbNs;

   @Value("${ws.abonarCuenta.osb-ns1}")
   	private String abonarOsbNs1;

   @Value("${ws.abonarCuenta.osb-mpi}")
   private String abonarOsbMpi; 

   @Value("${ws.abonarCuenta.emisor.codigoTransaccionFC}")
   private String abonarTransaccionFC;
 
   @Value("${ws.abonarCuenta.emisor.codigoExtendidoFC}")
   private String abonarExtendidoFC;

   //----------------- NOTIFICACION CCA ----------------
 
   @Value("${ws.cca.nuemero-reintento}")
   private int ccaNumeroReintento;

   @Value("${ws.cca.token.url}")
   private String ccaTokenUrl;

   @Value("${ws.cca.token.grandType}")
   private String grandTypeTokenCCA;
 
   @Value("${ws.cca.client-id}")
   private String ccaClientId;

   @Value("${ws.cca.client-secret}")
   private String ccaClientSecret;

   @Value("${ws.cca.reset-token-url}")
   private String ccaResetUrl;
 
   @Value("${ws.cca.reset-tipoAuth}")
   private String ccaTipoAuth;
 
   //----------------- JCR -----------------
 
   @Value("${ws.jcr.dominio}")
   private String jcrDominio;

   @Value("${ws.jcr.usuario-consumidor}")
   private String jcrUsuarioConsumidor;

   @Value("${ws.jcr.id-canal}")
   private String jcrIdCanal;

   @Value("${ws.jcr.numero-sucursal}")
   private String jcrNumeroSucursal;

   @Value("${ws.jcr.url-servicio}")
   private String jcrUrlServicio;

   @Value("${ws.jcr.url-soapaction}")
   private String jcrSoapAction;

   @Value("${ws.jcr.url-security-secext}")
   private String jcrUrlSecuSecExt;

   @Value("${ws.jcr.url-security-message}")
   private String jcrUrlSecuMessage;

   @Value("${ws.jcr.url-security-username-token}")
   private String jcrUrlSecuUsrNameToken;

   @Value("${ws.jcr.url-security-utility}")
   private String jcrUrlSecuUtility;

   @Value("${ws.jcr.url-security-user.enc}")
   private String jcrSecuUser;

   @Value("${ws.jcr.url-security-password.enc}")
   private String jcrSecuUserPassword;

   @Value("${ws.jcr.osb-ns}")
	private String jcrOsbNs;

   @Value("${ws.jcr.osb-ns1}")
   private String jcrOsbNs1;

   @Value("${ws.jcr.osb-mpi}")
   private String jcrOsbMpi; 

   @Value("${ws.jcr.ipOrigen}")
   private String jcrIpOrigen; 

   @Value("${ws.jcr.estacion}")
   private String jcreEstacion; 

   @Value("${ws.jcr.appOrgien}")
   private String jcrAppOrgien; 

   @Value("${ws.jcr.funcionalidad}")
   private String jcrFuncionalidad;

   @Value("${ws.jcr.servicioRest}")
   private String jcrServicioRest; 

   @Value("${ws.jcr.oficinaOrigen}")
   private String jcrOficinaOrigen; 

   @Value("${ws.jcr.rutEjecutivo}")
   private String jcrRutEjecutivo; 

   @Value("${ws.jcr.tipoProductoOrigen}")
   private String jcrTipoProductoOrigen; 

   @Value("${ws.jcr.productoOrigen}")
   private String jcrProductoOrigen; 

   @Value("${ws.jcr.tipoProductoDestino}")
   private String jcrTipoProductoDestino; 

   @Value("${ws.jcr.productoDestino}")
   private String jcrProductoDestino; 

   @Value("${ws.jcr.moneda}")
   private String jcrMoneda; 

   @Value("${ws.jcr.estado}")
   private String jcrEstado; 

   @Value("${ws.jcr.estadoDescripcion}")
   private String jcrEstadoDescripcion; 

   @Value("${ws.jcr.tipoRechazo}")
   private String jcrTipoRechazo; 

   @Value("${ws.jcr.codigoRechazo}")
   private String jcrCodigoRechazo; 

   @Value("${ws.jcr.descripcionRechazo}")
   private String jcrDescripcionRechaz; 

   @Value("${ws.jcr.modo}")
   private String jcrModo; 

   @Value("${ws.jcr.txRelacionada}")
   private String jcrTxRelacionada; 

   @Value("${ws.jcr.bancoOrigen}")
   private String jcrBancoOrigen; 

   @Value("${ws.jcr.descripcionRechazo}")
   private String jcrDescripcionRechazo; 

   @Value("${ws.jcr.tipoProducto}")
   private String jcrTipoProducto; 
   
   @Value("${ws.jcr.tipoProductoInterno}")
   private String jcrTipoProductoInt; 

   @Value("${ws.jcr.numProducto}")
   private String jcrNumProducto; 

   @Value("${ws.jcr.codMoneda}")
   private String jcrCodMoneda; 

   @Value("${ws.jcr.tipoCambio}")
   private String jcrTipoCambio; 

   @Value("${ws.jcr.tokenProducto}")
   private String jcrTokenProducto; 

   @Value("${ws.jcr.criterio-trx}")
   private String jcrCriterioTrx;
   
   //----------------------------------------------------------
 
   @Value("${spring.mvc.async.request-timeout}")
   private int timeOut;
 
   private   boolean indicadorReset;
 
 /************************************************************************
  * Nombre funcion: ejecutarConsulta.....................................*
  * Action:  Ejecutar el servcio de consulta.............................*
  * Inp:@tranBD:Transaccion => tranBD datos de la transacción a ejecutar.*
  * Inp:@codigoTrxCargoConsulta => codigo trx de cargo a consultar.......* 
  * Out:void.............................................................*
  ************************************************************************/
 public void ejecutarConsulta(Transaccion tranBD, String codigoTrxCargoConsulta, String origen) 
 {
  try
  {
   String eventoTrx = EventoTransaccion.CONSULTA.getValue();
   this.conexionServicio(nombreServConsulta, eventoTrx, tranBD, codigoTrxCargoConsulta, origen);
  }
  catch(Exception ex) 
  {
    LOGGER.error("Error EjecutarConsulta: "+ex);
  }
 }
 
 /***********************************************************************
  * Nombre funcion: ejecutarCargo.......................................*
  * Action:  Ejecutar el servcio de cargo...............................*
  * Inp:@tranBD:Transaccion => tranBD datos de la transacción a ejecutar
 * @throws SQLException 
 * @throws NumberFormatException 
  * @throws Throwable *
  * @resp:ResponseDTO => resp DTO.......................................* 
  * Out:void............................................................*
  ***********************************************************************/
 public void ejecutarCargo(Transaccion tranBD, String tipoEvento, ResponseDTO resp, 
		 boolean flagReintento, String origen) throws NumberFormatException, SQLException
 {
  String nombreServC = nombreServCargo;
  indicadorReset = false;
  String mensajeC = "";
  String codigoOPExitosoC = "00";
  String glosaOPExitosaC = "OK";
  String codigoIdTrxC = tranBD.getCodigoIdTrx();
  String codigoOperacionC = "000";

  //---- Transacción recepcionada en flujo de cargo
  resp.setCodigoOperacion(codigoOperacionC);
  resp.setMessage("OK");
  
  try
  {
   LOGGER.debug("SERV tokenCargoUrl: "+tokenCargoUrl);
   
   ServicioResponseDTO sr = conexionServicio(nombreServC, tipoEvento, tranBD, codigoIdTrxC, origen); 
   
   String codigoRespuestaServ = sr.getCodigoRespuesta();
   String glosaRespuesta = sr.getGlosaRespuesta();
   
   LOGGER.debug("RESPUESTA SERVICIO codigoRespuestaServ: "+codigoRespuestaServ);
   LOGGER.debug("RESPUESTA SERVICIO glosaRespuesta: "+glosaRespuesta);

   codigoRespuestaServ = StringUtils.deleteWhitespace(codigoRespuestaServ);
   glosaRespuesta = StringUtils.deleteWhitespace(glosaRespuesta);
 
   if(codigoRespuestaServ.equals(codigoOPExitosoC) || glosaRespuesta.equals(glosaOPExitosaC)) 
   {
    tranBD.setEstadoActual("P");
    tranBL.actualizarEstadoTransaccion(tranBD);
    
    indicadorReset = true;
    
    mensajeC = "Se realizó el cargo en la cuenta";
    tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,  Integer.parseInt(codigoOperacionC), mensajeC);
    
   }
   else
   {
    tranBD.setEstadoActual("R");
    tranBL.actualizarEstadoTransaccion(tranBD);
    
    indicadorReset = false;
    
    mensajeC = "No se realizó el cargo en la cuenta. "+sr.getGlosaRespuesta();
    tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,Integer.parseInt(codigoOperacionC), mensajeC);
   
   }
   LOGGER.debug("HISTORIA : "+mensajeC);
  }
  catch(Exception ex) 
  {
   tranBD.setEstadoActual("R"); //--- R: Rechazada
   
   LOGGER.error("Error en Ejecutar Cargo: "+ex);

   mensajeC = "Error No se realizó el cargo en la cuenta";
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,Integer.parseInt(codigoOperacionC), mensajeC);
   
   indicadorReset = false;
   
   try
   {
    //---- Ejecutar servicio de Consulta (comprobar estado trx)
    this.ejecutarConsulta(tranBD, codigoIdTrxC,origen);
   }
   catch(Exception ex1) 
   {
    LOGGER.error("Error en ejecutar servicio cuenta: "+ex1);
    mensajeC = "No se pudo ejecutar el servicio de consulta de la cuenta";
    tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,Integer.parseInt(codigoOperacionC), mensajeC);
   }
  }
  
  if(!flagReintento || indicadorReset) {
	   String tipoEventoNoti = EventoTransaccion.NOTIFICACION.getValue();  
  try
  {
	  String nombreEventoMayus = tipoEvento.toUpperCase();
	  String reintentoMayus = EventoTransaccion.REINTENTO.getValue().toUpperCase();

   if(nombreEventoMayus.equals(nombreServCargo) && origen=="CCA") 
   {
	 //--- Finalmente Notificar de la respuesta a la CCA

	   LOGGER.debug("Inicia evento de notificacion CCA" + tipoEventoNoti);

	  ExecutorService executor = Executors.newSingleThreadExecutor();
	   executor.submit(() -> 
	   		this.inicioCCA(tranBD, indicadorReset, nombreEventoMayus, reintentoMayus, 
	   				tipoEventoNoti,codigoOperacionC )
	   );    
   }
   else if(nombreEventoMayus.equals(reintentoMayus) && indicadorReset && origen=="CCA") 
   {
    //--- En caso de evento 'REINTENTO' y sólo en OK, responder a CCA
     ExecutorService executor = Executors.newSingleThreadExecutor();
	   executor.submit(() -> 
		   this.inicioCCA(tranBD, indicadorReset, nombreEventoMayus, reintentoMayus, 
				   tipoEventoNoti,codigoOperacionC )
		);
   }
  }
  catch(Exception ex)
  {
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,Integer.parseInt(codigoOperacionC),
		   "No se pudo notificar a la CCA");
   LOGGER.error("Error en notificar CCA: "+ex);
  }
  }
  
 }

 /***********************************************************************
  * Nombre funcion: ejecutarAbono.......................................*
  * Action:  Ejecutar el servcio de abono...............................*
  * Inp:@tranBD:Transaccion => tranBD datos de la transacción a ejecutar
 * @throws SQLException 
 * @throws NumberFormatException 
  * @throws Throwable *
  * @resp:ResponseDTO => resp DTO.......................................* 
  * Out:void............................................................*
  ***********************************************************************/
 public void ejecutarAbono(Transaccion tranBD, String tipoEvento, ResponseDTO resp, 
		 String origen) throws NumberFormatException, SQLException
 {
  String nombreServA = nombreServAbono;
  indicadorReset = false;
  String mensajeA = "";
  String codigoOPExitosoA = "00";
  String glosaOPExitosaA = "OK";
  String codigoIdTrxA = tranBD.getCodigoIdTrx();
  String codigoOperacionA = "000";
  

  //---- Transacción recepcionada en flujo de cargo
  resp.setCodigoOperacion(codigoOperacionA);
  resp.setMessage("OK");
  
  try
  {
	  
   LOGGER.debug("SERV tokenAbonoUrl: "+tokenAbonoUrl);
   
   ServicioResponseDTO sr = conexionServicio(nombreServA, tipoEvento, tranBD, codigoIdTrxA, origen); 
   
   String codigoRespuestaServ = sr.getCodigoRespuesta();
   String glosaRespuesta = sr.getGlosaRespuesta();
   
   LOGGER.debug("RESPUESTA SERVICIO codigoRespuestaServ: "+codigoRespuestaServ);
   LOGGER.debug("RESPUESTA SERVICIO glosaRespuesta: "+glosaRespuesta);

   codigoRespuestaServ = StringUtils.deleteWhitespace(codigoRespuestaServ);
   glosaRespuesta = StringUtils.deleteWhitespace(glosaRespuesta);
   
   if(codigoRespuestaServ.equals(codigoOPExitosoA) || glosaRespuesta.equals(glosaOPExitosaA)) 
   {
    tranBD.setEstadoActual("P");
    tranBL.actualizarEstadoTransaccion(tranBD);
    
    indicadorReset = true;
    
    mensajeA = "Se realizó el abono en la cuenta";
    tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,  Integer.parseInt(codigoOperacionA), mensajeA);

   }
   else
   {
    tranBD.setEstadoActual("R");
    tranBL.actualizarEstadoTransaccion(tranBD);
    
    indicadorReset = false;
    
    mensajeA = "No se realizó el abono en la cuenta. "+sr.getGlosaRespuesta();
    tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,Integer.parseInt(codigoOperacionA), mensajeA);
    
   }
   LOGGER.debug("HISTORIA : "+mensajeA);
  }
  catch(Exception ex) 
  {
   tranBD.setEstadoActual("R"); //--- R: Rechazada
   
   LOGGER.error("Error en Ejecutar Abono: "+ex);

   mensajeA = "Error No se realizó el abono en la cuenta";
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,Integer.parseInt(codigoOperacionA), mensajeA);
   
   indicadorReset = false;
   
  }
  
 }
 
 /***********************************************************************
  * Nombre funcion: ejecutarJCR.......................................*
  * Action:  Ejecutar el servcio de cargo...............................*
  * Inp:@tranBD:Transaccion => tranBD datos de la transacción a ejecutar
  * @throws Throwable *
  * @resp:ResponseDTO => resp DTO.......................................* 
  * Out:void............................................................*
  ***********************************************************************/
 public void ejecutarJCR(TransaccionDTO tranB, Emisor em, String tipoOperacion, String nomInstitucion )
 {
 
	  try {
		  LOGGER.debug("SERV JCR");
		  
		//--- Obtener las propiedades del servicio requerido
		  ServicioJCRProperties servProp = this.obtenerPropertiesServicioJCR();
		   ClientJCR jcr = new ClientJCR();
		   jcr.enviaJCR(servProp,tranB,tipoOperacion,em,nomInstitucion);
	  }catch(Exception ex)
	  {
		LOGGER.error("Error en servicio JCR: "+ex);
	  }
 }
  
 /***************************************************************
  * Nombre funcion: obtenerPropiedadesServicio..................*
  * Action:  Obtiene las Properties de un servicio por su nombre*
  * Inp: nombre del servicio....................................*
  * Out::Propiedades del servicio especificado..................*
  ***************************************************************/
 public Object obtenerPropiedadesServicio(String nombreServicio) 
 {
  Object propServ = null;
  if(nombreServicio.equals(nombreServCargo)) 
  {
   propServ = obtenerPropertiesServicioCargo();
  }
  else if(nombreServicio.equals(nombreServConsulta)) 
  {
   propServ = obtenerPropertiesServicioConsulta();
  }
  else if(nombreServicio.equals(nombreServNotificacion)) 
  {
   propServ = obtenerPropertiesServicioNotificacion();
  }else if(nombreServicio.equals(nombreServAbono)) 
  {
	   propServ = obtenerPropertiesServicioAbono();
  }
  
  return propServ;
 }

 /*******************************************************************
  * Nombre funcion: conexionServicio................................*
  * Action: Desición de conectarse al servicio de 'nombreServicio'..*
  * Inp:@nombreServ:String => "CARGO o CONSULTA"....................*
  * @tranBD:Transaccion => ranBD datos de la transacción a realizar.*
  * Out:@sr:ServicioResponseDTO.....................................*
  *******************************************************************/
 public ServicioResponseDTO conexionServicio(String nombreServ, String tipoEvento, Transaccion tranBD,
   String codigoIdTrx, String origen) throws TimeoutException
 {
  ServicioResponseDTO sr = new ServicioResponseDTO();
  String codigoOPExitoso = "00";
  String glosaOPExitosa = "OK";
  LOGGER.debug("Conexión al servicio");
  try
  {
   ExecutorService executorService = Executors.newSingleThreadExecutor();
   
   TransaccionDAL tranDAL = tranBL.obtenerTransaccionDAL();
   ClientService cli = new ClientService(tranDAL);
   
   Callable<ServicioResponseDTO> rs1 = null;
   
   LOGGER.debug("nombreServ: "+nombreServ);
   
   //--- Obtener las propiedades del servicio requerido
   Object servProp = this.obtenerPropiedadesServicio(nombreServ);
   
   rs1 = cli.ejecutarServicio(facDAL, tranBD, nombreServ, codigoIdTrx, servProp, origen);
   
   Future<ServicioResponseDTO> future = executorService.submit(rs1);
   sr = future.get(timeOut, TimeUnit.MILLISECONDS);
   
   String codigoRespuestaServ = sr.getCodigoRespuesta();
   String glosaRespuesta = sr.getGlosaRespuesta();
   
   LOGGER.debug("RESPUESTA SERVICIO codigoRespuestaServ: "+codigoRespuestaServ);
   LOGGER.debug("RESPUESTA SERVICIO glosaRespuesta: "+glosaRespuesta);

   codigoRespuestaServ = StringUtils.deleteWhitespace(codigoRespuestaServ);
   glosaRespuesta = StringUtils.deleteWhitespace(glosaRespuesta);
   
   if(nombreServ.equals(nombreServCargo) || nombreServ.equals(nombreServAbono))
   {
    //---- Generar 'codigoIdTrx' antes del "cargo" o "abono"
    
    if(codigoRespuestaServ.equals(codigoOPExitoso) || glosaRespuesta.equals(glosaOPExitosa)) 
    {
       //---- Transacción realizada
     String mensajeO = "Se realizó "+nombreServ;
       tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, 0, mensajeO);
       LOGGER.debug("HISTORIA :" + mensajeO);

    }
    else 
    {
        //---- Trx no realizada
     String mensajeNK = "No se pudo realizar "+nombreServ+". "+glosaRespuesta;
        tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, 13, mensajeNK);
        LOGGER.debug("HISTORIA :" + mensajeNK);
    }
    
   }
   
   LOGGER.debug("SERVICIO "+nombreServ+" ejecutado");
   
   return sr;
  }
  catch(Exception ex3) 
  {
   String mensajeError = "";
   LOGGER.error("ex3.toString() -> '" + ex3.toString()+"'"); 
   LOGGER.error("ex3.toString()> '" + ex3);
   if(ex3.toString().toLowerCase().contains("timeout")) 
   {
    mensajeError = "Error de Timeout en servicio "+nombreServ;
   }
   else 
   {
    mensajeError = "Error ejecución servicio "+nombreServ;
   }
   
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, 300, mensajeError);
   
   sr.setCodigoRespuesta("");
   sr.setGlosaRespuesta("");
   sr.setRespuesta(null);
   
   return sr;
  }
 }
 
 /**
  * Generar y asignar el codigo id trx. Ej. COM20210805000130000003
  * @param idCanal
  * @param tranBD
  * @throws SQLException 
  */
 public void generarCodigoIdTrx(String idCanal, Transaccion tranBD) throws SQLException 
 {

  int lastSeq = tranBL.obtenerSeqTrx();
  String codigoIdTrx = idCanal.toUpperCase() + new SimpleDateFormat("yyyyMMdd").format(new Date()) + lastSeq;
  
  tranBD.setCodigoIdTrx(codigoIdTrx);
  
  tranBL.actualizarCodigoIdTrx(tranBD);
 }

 /***************************************************************************
  * Nombre funcion: obtenerRequestTokenCargo................................*
  * Action:  Obtiene el objeto RequestTokenCargo para ser utilizado a futuro*
  * Inp:....................................................................*
  * Out::RequestTokenCargo..................................................*
  ***************************************************************************/
 public RequestTokenCargoDTO obtenerRequestTokenCargo() 
 {

  RequestTokenCargoDTO req = new RequestTokenCargoDTO();
  req.setKeyStore(keyStore);
  req.setKeyPassword(passKeyStore);
  req.setWsUrl(tokenCargoUrl);
  req.setWsUser(FuncionesGenerales.desencriptar(tokenCargoUser, secretKy));
  req.setWsPass(FuncionesGenerales.desencriptar(tokenCargoPass, secretKy));
  req.setTipoAuth(tipoAuthCargo);
  req.setParamUser(FuncionesGenerales.desencriptar(tokenCargoParamUser, secretKy));
  req.setParamPassUser(FuncionesGenerales.desencriptar(tokenCargoParamPassUser, secretKy));
  req.setParamScope(tokenCargoParamScope);
  req.setParamGrandType(tokenCargoParamGrandType);
  
  return req;
 }
 
 /***********************************************************
  * Nombre funcion: obtenerPropertiesServicioConsulta.......*
  * Action:  Obtiene las Properties del Servicio de Consulta*
  * Inp:....................................................*
  * Out::ServicioConsultaProperties.........................*
  ***********************************************************/
 public ServicioConsultaProperties obtenerPropertiesServicioConsulta() 
 {
  ServicioConsultaPropertiesDto propDto = new ServicioConsultaPropertiesDto();
	 
  propDto.setDomninio(consultaDominio);
  propDto.setConsultaSoapAction(consultaSoapAction);
  propDto.setUsuarioConsumidor(consultaUsuarioConsumidor);
  propDto.setIdCanal(consultaIdCanal);
  propDto.setNumeroSucursal(consultaNumeroSucursal);
  propDto.setUrlServicio(consultaUrlServicio);
  propDto.setUrlSecuSecExt(consultaUrlSecuSecExt);
  propDto.setUrlSecuMessage(consultaUrlSecuMessage);
  propDto.setUrlSecuUsrNameToken(consultaUrlSecuUsrNameToken);
  propDto.setUrlSecuUtility(consultaUrlSecuUtility);
  propDto.setConsultaSecuUser(FuncionesGenerales.desencriptar(consultaSecuUser, secretKy));
  propDto.setConsultaSecuUserPassword(FuncionesGenerales.desencriptar(consultaSecuUserPassword, secretKy));
  propDto.setConsultaAuthUsuarioServicio(FuncionesGenerales.desencriptar(consultaAuthUsuarioServicio, secretKy));
  propDto.setConsultaAuthPasswordServicio(FuncionesGenerales.desencriptar(consultaAuthPasswordServicio, secretKy));
  propDto.setConsultaAuthTipoAuth(consultaAuthTipoAuth);
  propDto.setConsultaOsbNs(consultaOsbNs);
  propDto.setConsultaOsbNs1(consultaOsbNs1);
  propDto.setConsultaOsbMpi(consultaOsbMpi);
  
  return new ServicioConsultaProperties(propDto);
 }
 
 /********************************************************
  * Nombre funcion: obtenerPropertiesServicioCargo.......*
  * Action:  Obtiene las Properties del Servicio Core (cargo o abono)*
  * Inp:.................................................*
  * Out::ServicioConsultaProperties......................*
  ********************************************************/
 public ServicioTokenProperties obtenerPropertiesServicioCargo() 
 {
  ServicioTokenPropertiesDto propDto = new ServicioTokenPropertiesDto();

  propDto.setTokenServicioUrl(tokenCargoUrl);
  propDto.setTokenServicioUser(FuncionesGenerales.desencriptar(tokenCargoUser, secretKy));
  propDto.setTokenServicioPass(FuncionesGenerales.desencriptar(tokenCargoPass, secretKy));
  propDto.setTipoAuthServicio(tipoAuthCargo);
  propDto.setTokenServicioParamUser(FuncionesGenerales.desencriptar(tokenCargoParamUser, secretKy));
  propDto.setTokenServicioParamPassUser(FuncionesGenerales.desencriptar(tokenCargoParamPassUser, secretKy));
  propDto.setTokenServicioParamScope(tokenCargoParamScope);
  propDto.setTokenServicioParamGrandType(tokenCargoParamGrandType);  
  propDto.setServicioDominioS(cargoDominio);
  propDto.setServicioSoapActionS(cargoSoapAction);
  propDto.setServicioUsuarioConsumidorS(cargoUsuarioConsumidor);
  propDto.setServicioIdCanalS(cargoIdCanal);
  propDto.setServicioNumeroSucursalS(cargoNumeroSucursal);
  propDto.setServicioUrlServicioS(cargoUrlServicio);
  propDto.setServicioUrlSecuSecExtS(cargoUrlSecuSecExt);
  propDto.setServicioUrlSecuMessageS(cargoUrlSecuMessage);
  propDto.setServicioUrlSecuUsrNameTokenS(cargoUrlSecuUsrNameToken);
  propDto.setServicioUrlSecuUtilityS(cargoUrlSecuUtility);
  propDto.setServicioSecuUserS(FuncionesGenerales.desencriptar(cargoSecuUser, secretKy));
  propDto.setServicioSecuUserPasswordS(FuncionesGenerales.desencriptar(cargoSecuUserPassword, secretKy));
  propDto.setServicioAuthUsuarioServicio(cargoAuthUsuarioServicio);
  propDto.setServicioAuthPasswordServicio(cargoAuthPasswordServicio);
  propDto.setServicioAuthTipoAuth(cargoAuthTipoAuth);
  propDto.setServicioOsbNsS(cargoOsbNs);
  propDto.setServicioOsbNs1S(cargoOsbNs1);
  propDto.setServicioOsbMpiS(cargoOsbMpi);
  propDto.setServicioRutOperadora(cargoRutOperadora);
  propDto.setServicioBancoDestino(cargoBancoDestino);
  propDto.setServicioBancoOrigen(cargoBancoOrigen);
  propDto.setServicioOficinaOrigenTx(cargoOficinaOrigenTx);
  propDto.setServicioCanalOrigenTx(cargoCanalOrigenTx);
  propDto.setServicioMoneda(cargoMoneda);
  propDto.setServicioCodigoProductoFC(cargoCodigoProductoFC);
  propDto.setServicioNombreCampo(cargoNombreCampo);
  propDto.setServicioValorCampo(cargoValorCampo);
  propDto.setServicioNombreCampo2(cargoNombreCampo2);
  propDto.setServicioNombreCampo3(cargoNombreCampo3);
  propDto.setServicioPrioridad(cargoPrioridad);
  propDto.setCodigoTransaccionFC(codigoTransaccionFC);
  propDto.setCodigoExtendidoFC(codigoExtendidoFC);
  
  //----- Se debe implementar
  return new ServicioTokenProperties(propDto);
 }

 /********************************************************
  * Nombre funcion: obtenerPropertiesServicioAbono.......*
  * Action:  Obtiene las Properties del Servicio de Abono*
  * Inp:.................................................*
  * Out::ServicioConsultaProperties......................*
  ********************************************************/
 public ServicioTokenProperties obtenerPropertiesServicioAbono() 
 {
	 ServicioTokenPropertiesDto propDto = new ServicioTokenPropertiesDto();

  propDto.setTokenServicioUrl(tokenAbonoUrl);
  propDto.setTokenServicioUser(FuncionesGenerales.desencriptar(tokenAbonoUser, secretKy));
  propDto.setTokenServicioPass(FuncionesGenerales.desencriptar(tokenAbonoPass, secretKy));
  propDto.setTipoAuthServicio(tipoAuthAbono);
  propDto.setTokenServicioParamUser(FuncionesGenerales.desencriptar(tokenAbonoParamUser, secretKy));
  propDto.setTokenServicioParamPassUser(FuncionesGenerales.desencriptar(tokenAbonoParamPassUser, secretKy));
  propDto.setTokenServicioParamScope(tokenAbonoParamScope);
  propDto.setTokenServicioParamGrandType(tokenAbonoParamGrandType);  
  propDto.setServicioDominioS(abonarDominio);
  propDto.setServicioSoapActionS(abonarSoapAction);
  propDto.setServicioUsuarioConsumidorS(abonarUsuarioConsumidor);
  propDto.setServicioIdCanalS(abonarIdCanal);
  propDto.setServicioNumeroSucursalS(abonarNumeroSucursal);
  propDto.setServicioUrlServicioS(abonarUrlServicio);
  propDto.setServicioUrlSecuSecExtS(abonarUrlSecuSecExt);
  propDto.setServicioUrlSecuMessageS(abonarUrlSecuMessage);
  propDto.setServicioUrlSecuUsrNameTokenS(abonarUrlSecuUsrNameToken);
  propDto.setServicioUrlSecuUtilityS(abonarUrlSecuUtility);
  propDto.setServicioSecuUserS(FuncionesGenerales.desencriptar(abonarSecuUser, secretKy));
  propDto.setServicioSecuUserPasswordS(FuncionesGenerales.desencriptar(abonarSecuUserPassword, secretKy));
  propDto.setServicioAuthUsuarioServicio(abonarAuthUsuarioServicio);
  propDto.setServicioAuthPasswordServicio(abonarAuthPasswordServicio);
  propDto.setServicioAuthTipoAuth(abonarAuthTipoAuth);
  propDto.setServicioOsbNsS(abonarOsbNs);
  propDto.setServicioOsbNs1S(abonarOsbNs1);
  propDto.setServicioOsbMpiS(abonarOsbMpi);
  propDto.setServicioRutOperadora(abonarRutOperadora);
  propDto.setServicioBancoDestino(abonarBancoDestino);
  propDto.setServicioBancoOrigen(abonarBancoOrigen);
  propDto.setServicioOficinaOrigenTx(abonarOficinaOrigenTx);
  propDto.setServicioCanalOrigenTx(abonarCanalOrigenTx);
  propDto.setServicioMoneda(abonarMoneda);
  propDto.setServicioCodigoProductoFC(abonarCodigoProductoFC);
  propDto.setServicioNombreCampo(abonarNombreCampo);
  propDto.setServicioValorCampo(abonarValorCampo);
  propDto.setServicioNombreCampo2(abonarNombreCampo2);
  propDto.setServicioNombreCampo3(abonarNombreCampo3);
  propDto.setServicioPrioridad(abonarPrioridad);
  propDto.setCodigoTransaccionFC(abonarTransaccionFC);
  propDto.setCodigoExtendidoFC(abonarExtendidoFC);
  
  //----- Se debe implementar
  return new ServicioTokenProperties(propDto);
 }

 /********************************************************
  * Nombre funcion: obtenerPropertiesServicioNotificacion*
  * Action:  Obtiene las Properties del Servicio de Notif*
  * Inp:.................................................*
  * Out::ServicioNotificacionProperties..................*
  ********************************************************/
 public ServicioNotificacionProperties obtenerPropertiesServicioNotificacion() 
 {
  ServicioNotificacionPropertiesDto propDto = new ServicioNotificacionPropertiesDto();

  propDto.setCcaResetTipoAuth(ccaTipoAuth);
  propDto.setCcaTokenGrandType(grandTypeTokenCCA);
  propDto.setCcaTokenUrl(ccaTokenUrl);
  propDto.setCcaResetTokenUrl(ccaResetUrl);
  propDto.setCcaClientId(ccaClientId);
  propDto.setCcaClientSecret(ccaClientSecret);
  propDto.setCcaNuemeroReintentos(ccaNumeroReintento);
  
  //----- CodInst, CodTrx e IndicadorReset faltantes se setearán afuera
  
  return new ServicioNotificacionProperties(propDto);
 }

 /********************************************************
  * Nombre funcion: obtenerPropertiesServicioJCR.........*
  * Action:  Obtiene las Properties del Servicio de Abono*
  * Inp:.................................................*
  * Out::ServicioJCRProperties......................*
  ********************************************************/
 public ServicioJCRProperties obtenerPropertiesServicioJCR() 
 {
	 ServicioJCRPropertiesDto propDto = new ServicioJCRPropertiesDto();

  propDto.setServicioDominio(jcrDominio);
  propDto.setServicioSoapAction(jcrSoapAction);
  propDto.setServicioUsuarioConsumidor(jcrUsuarioConsumidor);
  propDto.setServicioIdCanal(jcrIdCanal);
  propDto.setServicioNumeroSucursal(jcrNumeroSucursal);
  propDto.setServicioUrlServicio(jcrUrlServicio);
  propDto.setServicioUrlSecuSecExt(jcrUrlSecuSecExt);
  propDto.setServicioUrlSecuMessage(jcrUrlSecuMessage);
  propDto.setServicioUrlSecuUsrNameToken(jcrUrlSecuUsrNameToken);
  propDto.setServicioUrlSecuUtility(jcrUrlSecuUtility);
  propDto.setServicioSecuUser(FuncionesGenerales.desencriptar(jcrSecuUser, secretKy));
  propDto.setServicioSecuUserPassword(FuncionesGenerales.desencriptar(jcrSecuUserPassword, secretKy));
  propDto.setServicioIpOrigen(jcrIpOrigen);
  propDto.setServicioEstacion(jcreEstacion);
  propDto.setServicioAppOrgien(jcrAppOrgien);
  propDto.setServicioFuncionalidad(jcrFuncionalidad);
  propDto.setServicioRest(jcrServicioRest);
  propDto.setServicioOficinaOrigen(jcrOficinaOrigen);
  propDto.setServicioRutEjecutivo(jcrRutEjecutivo);
  propDto.setServicioTipoProductoOrigen(jcrTipoProductoOrigen);
  propDto.setServicioProductoOrigen(jcrProductoOrigen);
  propDto.setServicioTipoProductoDestino(jcrTipoProductoDestino);
  propDto.setServicioProductoDestino(jcrProductoDestino);
  propDto.setServicioMoneda(jcrMoneda);
  propDto.setServicioEstado(jcrEstado);
  propDto.setServicioEstadoDescripcion(jcrEstadoDescripcion);
  propDto.setServicioTipoRechazo(jcrTipoRechazo);
  propDto.setServicioCodigoRechazo(jcrCodigoRechazo);
  propDto.setServicioDescripcionRechaz(jcrDescripcionRechaz);
  propDto.setServicioModo(jcrModo);
  propDto.setServicioTxRelacionada(jcrTxRelacionada);
  propDto.setServicioBancoOrigen(jcrBancoOrigen);
  propDto.setServicioTipoProducto(jcrTipoProducto);
  propDto.setServicioNumProducto(jcrNumProducto);
  propDto.setServicioCodMoneda(jcrCodMoneda);
  propDto.setServicioCodigoTipoCambio(jcrTipoCambio);
  propDto.setServicioTipoProductoOrigen(jcrTipoProductoOrigen);
  propDto.setServicioOsbNs(jcrOsbNs);
  propDto.setServicioOsbNs1(jcrOsbNs1);
  propDto.setServicioOsbMpi(jcrOsbMpi);
  propDto.setServicioTipoProductoInt(jcrTipoProductoInt);
  propDto.setSevicioCriterioTrx(jcrCriterioTrx);
  //-----Se debe implementar
  return new ServicioJCRProperties(propDto);
 } 
 
public void inicioCCA(Transaccion tranBD, boolean indicadorReset, String nombreEventoMayus, 
		 String reintentoMayus, String tipoEventoNoti, String codigoOperacion ) {
	//--- En caso de evento 'CARGO' OK/NOK, responder a CCA
	   ResponseResetCCADTO respNoti = new ResponseResetCCADTO();
	   String mensajeNoti = "";
	   LOGGER.debug("notificacion CCA" + nombreEventoMayus);
	   LOGGER.debug("notificacion CCA" + reintentoMayus);
	   LOGGER.debug("notificacion CCA" + mensajeNoti);
	    try {
	    	
			respNoti = this.notificarCCA(tranBD, indicadorReset,10000);
		} catch (TimeoutException e) {
			 LOGGER.error("Error notificar CCA hilo paralelo:"+ e);
			 LOGGER.error("Error notificar CCA hilo paralelo:"+ e.getMessage());
		}finally{
			 mensajeNoti = "Notificación de Cargo a CCA: "+respNoti.getCodResp();
			 LOGGER.debug("notificacion CCA indicadorReset" + indicadorReset);
			 LOGGER.debug("notificacion CCA mensajeNoti " + mensajeNoti);
			 tranBL.generarHistoriaTransaccion(tranBD, tipoEventoNoti, Integer.parseInt(codigoOperacion), mensajeNoti);
		}
 }

 /**************************************************************************
  * Nombre funcion: notificarCCA...........................................*
  * Action: Notificar a la CCA.............................................*
  * Inp:@tranBD:Transaccion => tranBD datos de la trx a notiicar...........
 * @throws  
  * @throws Throwable *
  * @indicadorReset:boolean => indicadorReset indicador si hace reset o no.*
  * Out:void...............................................................*
  **************************************************************************/
 public ResponseResetCCADTO notificarCCA(Transaccion tranBD, boolean indicadorReset, int tiempoEspera)
    throws TimeoutException
 {
	   Calendar calendario = Calendar.getInstance();
	   int hora, minutos, segundos;
	   hora =calendario.get(Calendar.HOUR_OF_DAY);
	   minutos = calendario.get(Calendar.MINUTE);
	   segundos = calendario.get(Calendar.SECOND);
	   
  try {
	   LOGGER.debug(hora + ":" + minutos + ":" + segundos + 
			  "/ Se inicia proceso de espera para notificar CCA.. " + tiempoEspera + " milisegundos");
	Thread.sleep(tiempoEspera);
  } catch (InterruptedException e) {
	   LOGGER.error("Error notificar CCA interrupcion"+ e);
	   LOGGER.error("Error notificar CCA interrupcion"+ e.getMessage());
  }	 
  calendario = Calendar.getInstance();
  hora =calendario.get(Calendar.HOUR_OF_DAY);
  minutos = calendario.get(Calendar.MINUTE);
  segundos = calendario.get(Calendar.SECOND);
  LOGGER.debug(hora + ":" + minutos + ":" + segundos + " / Tiempo de espera terminado y comienza reset CCA");
  String nombreServ = "NOTIFICACION";
  String tipoEvento = EventoTransaccion.NOTIFICACION.getValue();
  LOGGER.debug("notificacion CCA tipoEvento" + tipoEvento);
  
  ResponseResetCCADTO objResp = new ResponseResetCCADTO();
  ServicioResponseDTO sr = new ServicioResponseDTO();
  
  int codigoOperacionHistoria = 13; //--- Código en caso de error
  
  try 
  {
   ExecutorService executorService = Executors.newSingleThreadExecutor();
   
   TransaccionDAL tranDAL = tranBL.obtenerTransaccionDAL();
   ClientService cli = new ClientService(tranDAL);
   
   Callable<ServicioResponseDTO> rs1 = null;
   
   LOGGER.debug("nombreServ: "+nombreServ);
   
   //--- Obtener las propiedades del servicio requerido
   ServicioNotificacionProperties servProp = 
   (ServicioNotificacionProperties) this.obtenerPropiedadesServicio(nombreServ);
   
   ServicioNotificacionPropertiesDto snp = new ServicioNotificacionPropertiesDto();
   snp.setCcaResetTipoAuth(servProp.getProp().getCcaResetTipoAuth());
   snp.setCcaTokenGrandType(servProp.getProp().getCcaTokenGrandType());
   snp.setCcaTokenUrl(servProp.getProp().getCcaTokenUrl());
   snp.setCcaResetTokenUrl(servProp.getProp().getCcaResetTokenUrl());
   snp.setCcaClientId(servProp.getProp().getCcaClientId());
   snp.setCcaClientSecret(servProp.getProp().getCcaClientSecret());
   snp.setCcaNuemeroReintentos(servProp.getProp().getCcaNuemeroReintentos());
   //---- Valores para la notificación
   snp.setCcaCodInstitucion(tranBD.getCodInstitucion());
   snp.setCcaCodTrx(tranBD.getCodigoTransaccion());
   snp.setCcaIndicadorReset(indicadorReset);
   
   servProp.setProp(snp);
   
   rs1 = cli.ejecutarServicioNotificar(nombreServ, servProp);
   
   Future<ServicioResponseDTO> future = executorService.submit(rs1);
   Thread.sleep(tiempoEspera);
   sr = future.get(timeOut, TimeUnit.MILLISECONDS);
   
   String codigoRespuestaServ = sr.getCodigoRespuesta();
   objResp.setCodResp(codigoRespuestaServ);
   
   LOGGER.debug("RESPUESTA SERVICIO NOTIFICACION codigoRespuestaServ: "+objResp.getCodResp());
   String mensaje = "Notificar a CCA: "+objResp.getCodResp();
   codigoOperacionHistoria = 0;
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, codigoOperacionHistoria, mensaje);
  }
  catch(Exception ex)
  {
   String mensajeError = "";
   LOGGER.error("ex.toString() -> '" + ex.toString()+"'");  
   LOGGER.error("ex.toString() > '" + ex);
   if(ex.toString().toLowerCase().contains("timeout")) 
   {
    mensajeError = "Error de Timeout en servicio Notificación CCA";
   }
   else 
   {
    mensajeError = "Error notificar CCA";
   }
   
   objResp.setCodResp("NK");
   LOGGER.error("Error notificar CCA"+ mensajeError);
   
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, codigoOperacionHistoria, mensajeError);
  }
  
  return objResp;
 }
 
}
