package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.TransaccionDTO;
import com.bch.api.rest.dto.ValidacionDTO;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.enums.EstadoTransaccion;
import com.bch.api.rest.enums.EventoTransaccion;
import com.bch.api.rest.utils.FuncionesGenerales;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import org.springframework.context.annotation.PropertySource;

@Component
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class InformarTefBL {
	 
@Value("${ws.girarCuenta.id-canal}")
 private String idCanalGiro;
	 	
@Autowired
 private ServicioBL servBL;
 @Autowired
 private TransaccionBL tranBL;
 @Autowired
 private EmisorBL emiBL;
 
 private static final Logger LOGGER = Logger.getLogger(InformarTefBL.class);
 
 private String faseRecepcion = EventoTransaccion.RECEPCION.getValue();
 
 String codigoOperacion ="";
 
 private static String strCCAn = "CCA";
 private static String strFin = "####################FIN PROCESO DE ACREDITAR FONDOS#############################";
 
 /***********************************************************
  * Nombre funcion: GestionarTRX............................*
  * Action:Gestionar las trx................................*
  * Inp:@tran:TransaccionDTO => transaccion.................* 
  * Out:@resp : ResponseDTO => response JSON................
  * @throws Throwable *
  **********************************************************/
 public ResponseDTO gestionarTRX(TransaccionDTO tran, String tipoTransaccion, String origen)
 {
  ResponseDTO resp = new ResponseDTO();
  
  String codOperacion = "013"; // Código operación en caso de error
  
  try
  {
   resp.setStatusCode(200);
   
   String mensaje = "";
   
   
   
   if(tran.getCodInstitucion().length() > 4 || tran.getCodTrx().length() > 100 || tran.getFechaHoraTrx().length() > 20 
		   || tran.getMontoTotalEnviadoCiclo().length() > 14) {
	   
	   mensaje="Error en al largo de los campos de entrada => Codigo Insitucion es de largo 4 y" 
			   + "numerico, fecha y hora en formato yyyy-mm-dd hh:mm:ss, codigo de transaccion largo maximo  100," 
			   + "monto largo 14 numerico";
	   
	   resp.setCodigoOperacion(codOperacion);
	   resp.setMessage(mensaje);
	   resp.setMessage("NOK");
	    	LOGGER.debug(strFin);
	    	
	    	 Transaccion tranBD = transformarTransaccionBD(tran);
	    	   //---- 1. Registrar la transacción de entrada
	    	   tranBD.setCore(tipoTransaccion);
	    	   tranBD.setOrigen(origen);  
	    	 String respTranDal  = (String)tranBL.ingresarTransaccion(tranBD);
	    	 
	    	 int idTransaccionGen = 0;
	    	   if(FuncionesGenerales.esNumerico(respTranDal)) 
	    	   { 
	    	     idTransaccionGen = Integer.parseInt(respTranDal);
	    	     if(idTransaccionGen == 0)
	    	     {
	    	      LOGGER.error(respTranDal);
	    	      
	    	      resp.setCodigoOperacion(codOperacion);
	    	      resp.setMessage("NOK");
	    	      	LOGGER.debug(strFin);
	    	      return resp; 
	    	     }
	    	   }
	    	   tranBD.setIdTransaccion(idTransaccionGen);
	    	   tranBL.generarHistoriaTransaccion(tranBD, faseRecepcion, Integer.parseInt(codOperacion), mensaje);
	    return resp; 
   }
   
   //--- Limitación de tamaños de string (según campos bd)
   tran.setCodigoIdTransaccion(FuncionesGenerales.limitarTamanoString(tran.getCodigoIdTransaccion(), 50));  
   tran.setEstadoActual(FuncionesGenerales.limitarTamanoString(tran.getEstadoActual(), 1));
   tran.setNombreInsitucion(FuncionesGenerales.limitarTamanoString(tran.getNombreInsitucion(), 50));
   tran.setRutEmisor(FuncionesGenerales.limitarTamanoString(tran.getRutEmisor(), 12));
   tran.setTipoTrx(FuncionesGenerales.limitarTamanoString(tran.getTipoTrx(), 1));
   
   //--- Clear XSS
   tran.setCodigoIdTransaccion(FuncionesGenerales.cleanXSS(tran.getCodigoIdTransaccion()));
   tran.setCodInstitucion(FuncionesGenerales.cleanXSS(tran.getCodInstitucion()));
   tran.setCodTrx(FuncionesGenerales.cleanXSS(tran.getCodTrx()));
   tran.setEstadoActual(FuncionesGenerales.cleanXSS(tran.getEstadoActual()));
   tran.setFechaHoraTrx(FuncionesGenerales.cleanXSS(tran.getFechaHoraTrx()));
   tran.setIdTrx(FuncionesGenerales.cleanXSS(tran.getIdTrx()));
   tran.setNombreInsitucion(FuncionesGenerales.cleanXSS(tran.getNombreInsitucion()));
   tran.setRutEmisor(FuncionesGenerales.cleanXSS(tran.getRutEmisor()));
   tran.setTipoTrx(FuncionesGenerales.cleanXSS(tran.getTipoTrx()));
   tran.setMontoTotalEnviadoCiclo(FuncionesGenerales.cleanXSS(tran.getMontoTotalEnviadoCiclo()));
   //--- Fin Clear XSS
   
   Transaccion tranBD = transformarTransaccionBD(tran);

   //---- 1. Registrar la transacción de entrada
   tranBD.setCore(tipoTransaccion);
   tranBD.setOrigen(origen);

  
   String respTranDal  = (String)tranBL.ingresarTransaccion(tranBD);
   
   int idTransaccionGen = 0;
   if(FuncionesGenerales.esNumerico(respTranDal)) 
   { 
     idTransaccionGen = Integer.parseInt(respTranDal);
     if(idTransaccionGen == 0)
     {
      LOGGER.error(respTranDal);
      
      resp.setCodigoOperacion(codOperacion);
      resp.setMessage("NOK");
      	LOGGER.debug(strFin);
      return resp; 
     }
   }
   
   codOperacion = "000";
   
   //---- Registrar la transacción
   tranBD.setIdTransaccion(idTransaccionGen);
   LOGGER.debug("1. tranBD.getIdTransaccion > idTrx: '"+tranBD.getIdTransaccion()+"'");
      
   mensaje = "Transacción '"+tranBD.getCodigoTransaccion()+"' "+EstadoTransaccion.RECIBIDA;   
   tranBL.generarHistoriaTransaccion(tranBD, faseRecepcion, Integer.parseInt(codOperacion), mensaje);
   
   //---- 2. Validar datos de la transacción
   if(origen.equals(strCCAn)) {
   ValidacionDTO respVal = this.validarDatosTransaccion(tranBD);
   if(!respVal.getMensajeValidacion().isEmpty()) 
   {
	   codOperacion = String.valueOf(respVal.getCodigoOperacionValidacion());
    
    resp.setCodigoOperacion(codOperacion);
    resp.setMessage("NOK");

    //--- Notificar a CCA
   ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> 
	this.inicioCCA(resp.getMessage(), tranBD)
   );
	   LOGGER.debug(strFin);    
    return resp;
 
   }
   }
   //---- 3. Verificar duplicidad de la transacción en el ciclo actual
   boolean estaDuplicada = true;
   estaDuplicada = tranBL.verificarDuplicidadTransaccionCiclo(tranBD);    
   
   if(estaDuplicada) 
   {
	   codOperacion = "012";
    
    tranBD.setEstadoActual("R"); //--- R: Rechazada
    mensaje = "La transacción ya se encuentra registrada dentro del ciclo";
    tranBL.generarHistoriaTransaccion(tranBD, faseRecepcion, Integer.parseInt(codOperacion), mensaje);
    
    resp.setCodigoOperacion(codOperacion);
    resp.setMessage("NOK");

    //--- Notificar a CCA
   ExecutorService executor = Executors.newSingleThreadExecutor();
	   executor.submit(() -> 
		this.inicioCCA(resp.getMessage(), tranBD)
	   );
 	   LOGGER.debug(strFin);   
    return resp;
   }
   
   //----- 4. Conexión a los Servicios
   //---- Generar codigoIdTrx previo al giro   
   servBL.generarCodigoIdTrx(idCanalGiro, tranBD);
   String codigoIdTrx = tranBD.getCodigoIdTrx();

   ejecutarServiciosCore(tipoTransaccion,origen,tranBD,codigoIdTrx,resp);
   
   return resp;

  }
  catch(Exception ex)
  {
   LOGGER.error("Error en InformarTef: "+ex);
   
   resp.setStatusCode(500);
   resp.setCodigoOperacion(codOperacion);
   resp.setMessage("Error en reintento: "+ex.getMessage());
	LOGGER.debug(strFin);

   return resp;
  }
 }
 
 public void inicioCCA(String message, Transaccion tranBD ) {
    try 
    {
    	NotificacionCCABL.notificacionTrxCCAHistoria(servBL, message, tranBD);
	} catch (TimeoutException e) {
		LOGGER.error("Error inicioCCA: "+e);
		LOGGER.error("Error inicioCCA: "+e.getMessage());
	}
 }
 
 /***********************************************************
  * Nombre funcion: InformarTEF.............................*
  * Action:Informar TEF.....................................*
  * Inp:@tran:TransaccionDTO => transaccion.................* 
  * Out:@resp : <ResponseDTO> => response JSON..............*
  **********************************************************/
 public CompletableFuture<ResponseDTO> informarTEF(TransaccionDTO tran, String Core ,String Origen, boolean fechaValida)
 {
	 
 if(fechaValida)	 
 {	 
  CompletableFuture<ResponseDTO> futureResult = new CompletableFuture<>();
  new Thread(() -> {
      try
      {
    	  
       ResponseDTO r1 = this.gestionarTRX(tran, Core,Origen);
       
       futureResult.complete(r1);
      }
      catch(Exception e)
      {
       ResponseDTO r1 = new ResponseDTO();       
       r1.setMessage("Exception");
       
       futureResult.completeExceptionally(e);
      }
      }).start();
  
  return futureResult;
 }else {
	 CompletableFuture<ResponseDTO> futureResult = new CompletableFuture<>();
	  new Thread(() -> {
		   
		   ResponseDTO resp = new ResponseDTO();
		   resp.setCodigoOperacion("13");
		   tran.setCodTrx(FuncionesGenerales.cleanXSS(tran.getCodTrx()));
		   tran.setEstadoActual(FuncionesGenerales.cleanXSS(tran.getEstadoActual()));
		   tran.setIdTrx(FuncionesGenerales.cleanXSS(tran.getIdTrx()));
		   Transaccion tranBD = transformarTransaccionBD(tran);
		   tranBD.setCore(Core);
		   tranBD.setOrigen(Origen);
		   String respTranDal =     tranBL.ingresarTransaccion(tranBD);
		   if(FuncionesGenerales.esNumerico(respTranDal)) 
		   { 
			   tranBD.setIdTransaccion(Integer.parseInt(respTranDal));
		   }
		   tranBL.generarHistoriaTransaccion(tranBD, faseRecepcion, 13, "Error en fecha mayor a la actual");
		   resp.setMessage("Error en fecha mayor a la actual");
		   resp.setMessage("NOK");
		   LOGGER.error("Error en fecha mayor a la actual ");	
		   LOGGER.debug(strFin);
		   futureResult.complete(resp); 
	  }).start();
	  
	  return futureResult;
 }

 }
 
 public ResponseDTO informarTEFAbono(TransaccionDTO tran, String Core ,String Origen)
 {
  
      try
      {
       ResponseDTO r1 = this.gestionarTRX(tran, Core,Origen);
       return r1;
  
      }
      catch(Exception e)
      {
       ResponseDTO r1 = new ResponseDTO();       
       r1.setMessage("Exception");
       LOGGER.error("Error en InformarTef: "+e);
       
       return r1;
  
      }
  
  
  
 }
 
 /******************************************************************************************************
  * Nombre funcion: validarDatosTransaccion............................................................*
  * Action:Valida los datos de la transacción DTO de entrada...........................................*
  * Inp:@tranBD:Transaccion => tran objeto transacción dto de entrada..................................*  
  * Out:@valida:ValidacionDTO => si la transacción es válida, de lo contrario retorna todos los errores
  * @throws SQLException error con sql
  ******************************************************************************************************/
 public ValidacionDTO validarDatosTransaccion(Transaccion tranBD) throws SQLException
 {
  ValidacionDTO valida = new ValidacionDTO();
  
  ValidacionTef valTef = new ValidacionTef(tranBL, emiBL);
  valTef.setTipoEvento(faseRecepcion);

  //--- 1. Validar codigo institución (númerico, 4 digitos)
  valTef.validacionCodigoInstitucion(tranBD);
  
  //--- 2. Validar fecha trnsacción (fecha válida yyyy-MM-dd HH:mm:ss)
  valTef.validarFechaTrx(tranBD);
  
  //--- 3. Validar codigo trnsacción (texto hexadecimal, letras y números)
  valTef.validarCodigoTrx(tranBD);
  
  //--- 4. Validar monto de transacción (numérico, hasta 14 cifras)
  valTef.validarMontoTrx(tranBD);
  
  //-------- Respuesta de la validación
  String strCodigosOperacion = valTef.getStrCodigosOperacion();
  String respValidacion = valTef.getRespValidacion();
  if(!respValidacion.isEmpty()) 
  {
   valTef.setCodigoOperacionIteracion(valTef.getCodigoOperacionValidacion());
   
   String mensaje = "Errores de validación encontrados para transacción '"+tranBD.getCodigoTransaccion()+"'";
     
   tranBD.setEstadoActual("R"); //-- R: Rechazada
       
   tranBL.generarHistoriaTransaccion(tranBD, valTef.getTipoEvento(), 
		   Integer.parseInt(valTef.getCodigoOperacionIteracion()), mensaje);
     
   strCodigosOperacion += valTef.getCodigoOperacionIteracion()+"|";
   respValidacion += mensaje+"\n";
  }
  else 
  {
   valTef.setCodigoOperacionIteracion("000");
   String mensaje = "Validación de la transacción '"+tranBD.getCodigoTransaccion()+"' OK";
       
   tranBL.generarHistoriaTransaccion(tranBD, valTef.getTipoEvento(), 
		   Integer.parseInt(valTef.getCodigoOperacionIteracion()), mensaje);
  }
    
  //---- Obtener datos finales de validación
  String codigoOperacionFinal = "0";
  if(strCodigosOperacion.contains(valTef.getCodigoValidacionErrorExistencia()+"|"))
  {
   codigoOperacionFinal = valTef.getCodigoValidacionErrorExistencia()+"";
  }
  else if(strCodigosOperacion.contains(valTef.getCodigoOperacionValidacion()+"|")) 
  {
   codigoOperacionFinal = valTef.getCodigoOperacionValidacion()+"";
  }
    
    
  valida.setCodigoOperacionValidacion(Integer.parseInt(codigoOperacionFinal));
  valida.setMensajeValidacion(respValidacion);
    
  return valida;
 }
 
 /************************************************************************************
  * Nombre funcion: transformarTransaccionBD.........................................*
  * Action: Transforma la transacción DTO de entrada en un registro de base de datos.*
  * Inp:@tran:TransaccionDTO => tran objeto transacción dto de entrada...............*  
  * Out:@tranBD:Transaccion => objeto transacción de bd..............................*
  ************************************************************************************/
 public static Transaccion transformarTransaccionBD(TransaccionDTO tran)
 {
  Transaccion tranBD = new Transaccion();
  
  tranBD.setCodInstitucion(tran.getCodInstitucion());
  tranBD.setCodigoTransaccion(tran.getCodTrx());
  tranBD.setFechaTransaccion(tran.getFechaHoraTrx());
  tranBD.setMontoTotalEnviadoCiclo(tran.getMontoTotalEnviadoCiclo());
  tranBD.setTipoTransaccion("R");  
  
  //---- Ciclo de la transacción
  tranBD.setCiclo(obtenerCicloTransaccion(tran.getFechaHoraTrx()));
  
  //---- Estado actual
  tranBD.setEstadoActual(EstadoTransaccion.RECIBIDA.getValue());
  
  return tranBD;
 } 
 
 /******************************************
  * Nombre funcion: obtenerCicloTransaccion*
  * Action: GObtiene ciclo trx.............*
  * Inp:@fechaTrx:String => fecha trx......* 
  * Out:@ciclo:int.........................*
  ******************************************/
 public static int obtenerCicloTransaccion(String fechaTrx) 
 {
  int ciclo = 0;
  
  try
  {
   if(FuncionesGenerales.parseDate(fechaTrx) != null)
   {
    //--- registrar ciclo de llegada (00:00 – 13:59 => Ciclo 1, 14:00 – 23:59: Ciclo 2)
    Date f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaTrx);
          LocalTime horaLlegada = LocalTime.parse(new SimpleDateFormat("HH:mm:ss").format(f1));
          
          if(horaLlegada.isAfter(LocalTime.parse("00:00:00")) && horaLlegada.isBefore(LocalTime.parse("13:59:59")))
          {
           ciclo = 1;
          }
          
          if(horaLlegada.isAfter(LocalTime.parse("14:00:00")) && horaLlegada.isBefore(LocalTime.parse("23:59:59")))
          {
           ciclo = 2;
          }
      }
  }
  catch(Exception ex) 
  {
   ciclo = 0;
   LOGGER.error(ex);
  }
  
        return ciclo;
 }
 
 public void ejecutarServiciosCore(String tipoTransaccion, String origen,Transaccion tranBD, 
		 String codigoIdTrx, ResponseDTO resp) throws NumberFormatException, SQLException {
	   String eventoTrx ="";  
	 switch(tipoTransaccion) {
	   	case "Cargo":
	   	   LOGGER.debug("codigoIdTrx CARGO: "+codigoIdTrx);
	   	   
	   	   eventoTrx = EventoTransaccion.CARGO.getValue();
	   	   servBL.ejecutarCargo(tranBD, eventoTrx, resp, false, origen);
	   	   
	   	  LOGGER.debug("####################FIN PROCESO DE ACREDITAR FONDOS CARGO#############################");
	   	   
	   		break;   
	   	case "Abono":
	   	  LOGGER.debug("codigoIdTrx ABONO: "+codigoIdTrx);
	       	   
	      eventoTrx = EventoTransaccion.ABONO.getValue();
	      servBL.ejecutarAbono(tranBD, eventoTrx, resp, origen);
	       	   
	       	   
	      LOGGER.debug("####################FIN PROCESO DE ACREDITAR FONDOS ABONO#############################");
	       	   
	   		break;
	   	  default:
	   	      LOGGER.debug("####################FIN PROCESO DE ACREDITAR FONDOS ABONO#############################");
	   		  break;
	   }
 }
 
}
