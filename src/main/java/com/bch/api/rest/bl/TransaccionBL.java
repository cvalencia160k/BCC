package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.TransaccionDAL;
import com.bch.api.rest.dto.ListaResultadoPaginacionDTO;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ctrm.TransaccionControlM;
import com.bch.api.rest.dto.filtrobusq.ConsultaTransaccion;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.entities.TransaccionHistoria;
import com.bch.api.rest.enums.EventoTransaccion;
import com.bch.api.rest.utils.FuncionesGenerales;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Capa de negocio encargada de operar las transacciones
 * @author 160k
 *
 */
@Component
public class TransaccionBL {

 private static final Logger LOGGER = Logger.getLogger(TransaccionBL.class);
 
 @Autowired
 private TransaccionDAL tranDAL;
 
 /**
  * Obtiene la transaccionDAL
  * @return
  */
 public TransaccionDAL obtenerTransaccionDAL()
 {
  return this.tranDAL;
 }
 
 /************************************************************************
  * Nombre funcion: ingresarTransaccion..................................*
  * Action:  Ingresa la transacción a la base de datos...................*
  * Inp:@tranBD:Transaccion => tranBD datos de la transacción a ejecutar.*
  * Out:ingresarTransaccion(tranBD):String => lista de transacciones.....*
  ************************************************************************/
 public String ingresarTransaccion(Transaccion tranBD) 
 {
  return tranDAL.ingresarTransaccion(tranBD);
 }
 
 /***********************************************************************************
  * Nombre funcion: generarHistoriaTransaccion......................................*
  * Action:  Genera y registra la "Historia Tranasccion" según datos de entrada.....*
  * Inp:@tranBD:Transaccion =>objeto transacción dto de entrada.....................*
  * @tipoEvento:String => resp DTO..................................................*
  * @codigoOperacion:int => resp DTO................................................*
  * @detalles:String => detalles objeto de detalle a ser incorporados en la historia*    
  * Out:void........................................................................*
  ***********************************************************************************/
 public void generarHistoriaTransaccion(Transaccion tranBD, String tipoEvento, int codigoOperacion, String detalles) 
 {
  TransaccionHistoria his = new TransaccionHistoria();
  if(tranDAL==null) {
	  tranDAL = new TransaccionDAL();
  }
  his.setFechaRegistro(new Date());
  his.setIdTransaccion(tranBD.getIdTransaccion());
  his.setCodigoOperacion(codigoOperacion);
  his.setTipoEvento(tipoEvento);
  his.setDetalle(detalles);
  
  his.setEstadoActual(tranBD.getEstadoActual());
  
  if(tipoEvento.equals(EventoTransaccion.RECEPCION.getValue()) 
     || tipoEvento.equals(EventoTransaccion.NOTIFICACION.getValue())
     || tipoEvento.equals(EventoTransaccion.CAMBIO_ESTADO.getValue())) 
  {
	  his.setCodigoIdTrx(tranBD.getCodigoTransaccion());
  }
  if(tipoEvento.equals(EventoTransaccion.CARGO.getValue()) 
     || tipoEvento.equals(EventoTransaccion.REINTENTO.getValue())) 
  {
	  his.setCodigoIdTrx(tranBD.getCodigoIdTrx());
  }
    
  tranDAL.ingresarHistoriaTransaccion(his);
 }
 
 /***********************************************************************************
  * Nombre funcion: ingresarHistoriaTransaccion.....................................*
  * Action:  Registrar Historia en la transaccion...................................*
  * Inp:@tranHisBD:TransaccionHistoria => tranBD datos de la transacción a registrar*
  * @resp:ResponseDTO => resp DTO...................................................* 
  * Out:ingresarHistoriaTransaccion(tranHisBD):String lista de transacciones........*
  ***********************************************************************************/
 public String ingresarHistoriaTransaccion(TransaccionHistoria tranHisBD) 
 {
  LOGGER.debug("RESPUESTA Hisoptiria");
  return tranDAL.ingresarHistoriaTransaccion(tranHisBD);
 }
 
 /*****************************************************************************************************
  * Nombre funcion: verificarDuplicidadTransaccionCiclo...............................................*
  * Action:  Verificar si la transacción ya se registró el día de hoy (ciclo y codigo transacción)....*
  * Inp:@codigoInstitucion:String => codTrx codigo de la transacción..................................*
  * Out:verificarDuplicidadTransaccionCiclo(codigoInstitucion):boolean true/false si se registró o no.*
  *****************************************************************************************************/
 public boolean verificarDuplicidadTransaccionCiclo(Transaccion tranBD) 
 {
  return tranDAL.verificarDuplicidadTransaccionCiclo(tranBD);
 }
 
 /************************************************************************
  * Nombre funcion:  Actualizar el estado actual de la transacción.......*
  * Action:  Obtener una trx por su ID...................................*
  * Inp:@tran:Transaccion => tranBD datos de la transacción a actualizar.*
  * @resp:ResponseDTO => resp DTO........................................* 
  * Out:void.............................................................*
  ************************************************************************/
 public void actualizarEstadoTransaccion(Transaccion tran) 
 {
  tranDAL.actualizarEstadoTransaccion(tran); 
 }

 /**
  * Actualiza el codigo id trx de la transacción
  * @param tran
  */
 public void actualizarCodigoIdTrx(Transaccion tran)
 {
  tranDAL.actualizarCodigoIdTrx(tran); 
 }
 
 /********************************
  * Nombre funcion: ejecutarCargo*
  * Action:  obetiene trxs.......*
  * Inp:@idTrx:int => id trx.....*
  * Out:void.....................*
  ********************************/
 public Transaccion obtenerTransaccion(int idTrx) throws SQLException 
 {
  return tranDAL.obtenerTransaccion(idTrx);
 }
 
 /**
  * Obtiene la secuencia mas actual de la transacción
  * @return
  * @throws SQLException 
  */
 public int obtenerSeqTrx() throws SQLException 
 {
	 if(tranDAL==null) {
		  tranDAL = new TransaccionDAL();
	  }
  return tranDAL.obtenerSeqTrx();
 }
 
 /******************************************************************************
  * Nombre funcion: consultarTrx...............................................*
  * Action:Obtener todas las transacciones según filtro de búsqueda............*
  * Inp:@ctran:ConsultaTransaccion => tranBD datos de la transacción a ejecutar*
  * @resp:ConsultaTransaccion => resp DTO......................................* 
  * Out:consultarTrx(ctran):List<TransaccionControlM> => lista de transacciones*
  ******************************************************************************/
 public List<TransaccionControlM> consultarTrx(ConsultaTransaccion ctran) throws SQLException
 {
  return tranDAL.consultarTrx(ctran);
 }
 
 /**
  * Genera el codigo id trx de la transacción cada vez que se ejecute un cargo
  * @return
  * @throws SQLException 
  */
 public String generarCodigoIdTrx(String idCanal) throws SQLException 
 {
  int secuencia = this.obtenerSeqTrx();
  return idCanal + new SimpleDateFormat("YYYYMMDD").format(new Date()) + secuencia;
 }
 
 /**
  * Consulta las las trx que se han realizado a lo largo del tiempo
  * @param fechaIni fecha inicial (dd-mm-yyyy)
  * @param fechaFin fecha final (dd-mm-yyyy)
  * @param idCodigoInstitucion codigo de institución
  * @param estadoTrx estado de la trx
  * @param pageSize tamaño de la paginación
  * @param pageNumber numero de página a consultar
  * @return
  */
 public ListaResultadoPaginacionDTO obtenerTransaccionHistoricas(
		 String fechaIni, 
		 String fechaFin,
		 int idCodigoInstitucion, 
		 String estadoTrx, 
		 int pageSize, 
		 int pageNumber) 
 {
	 //--- Limitar tamaño string (según bd)
	 fechaIni = FuncionesGenerales.limitarTamanoString(fechaIni, 10);	//-- dd-mm-yyyy
	 fechaFin = FuncionesGenerales.limitarTamanoString(fechaFin, 10);	//-- dd-mm-yyyy
	 estadoTrx = FuncionesGenerales.limitarTamanoString(estadoTrx, 1);	//-- P,A,N,R
	 
	 //--- Limpiar XSS
	 fechaIni = FuncionesGenerales.cleanXSS(fechaIni);
	 fechaFin = FuncionesGenerales.cleanXSS(fechaFin);
	 estadoTrx = FuncionesGenerales.cleanXSS(estadoTrx);
	 
	 fechaIni = (!fechaIni.isEmpty())?FuncionesGenerales.fecbaStandard(fechaIni):"";
	 fechaFin = (!fechaFin.isEmpty())?FuncionesGenerales.fecbaStandard(fechaFin):"";
	 
	 ListaResultadoPaginacionDTO objRes = new ListaResultadoPaginacionDTO();
	 try {
		 objRes = tranDAL.obtenerTransaccionHistoricas(
				 fechaIni,
				 fechaFin,
				 idCodigoInstitucion, 
				 estadoTrx, 
				 pageSize, 
				 pageNumber);
	} catch (SQLException e) {
		LOGGER.debug("Error obtenerTransaccionHistoricas: "+e);
	}
	 return objRes;
 }
 
 /**
  * Obtener Historias de una Transaccion
  * @param idTrx
  * @return lista de historias de la trx
  */
 public ListaResultadoPaginacionDTO obtenerHistoriasTransaccion(int idTrx) 
 {
     ListaResultadoPaginacionDTO objRes = new ListaResultadoPaginacionDTO();
     try 
     {
		 objRes = tranDAL.obtenerHistoriasTransaccion(idTrx);
     } 
     catch (SQLException e) 
     {
		LOGGER.debug("Error obtenerHistoriasTransaccion: "+e);
     }
	 return objRes;
  }

 /**
  * obtenerTransaccionesRA Obtener Historias de una Transaccion
  * @param obtenerTransaccionesRA
  * @return lista de trx de las ultimas 24 Horas
  */
 public ListaResultadoPaginacionDTO obtenerTransaccionesRA(String estado, int numRegistrosPagina, int pageNumber) 
 {
  try
  {
	//--- Limitar tamaño string (según bd)
	estado = FuncionesGenerales.limitarTamanoString(estado, 1);	//-- P,A,N,R
		 
		 //--- Limpiar XSS
	estado = FuncionesGenerales.cleanXSS(estado);
	  
	 return tranDAL.obtenerTransaccionesRA(estado, numRegistrosPagina, pageNumber);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return  new ListaResultadoPaginacionDTO();
  }
 }
 
 /*****************************************************************************************************
  * Nombre funcion: ejecutarServicioTransaccionesManual...............................................*
  * Action: ejecuta un servicio sobre grupo de transacciones..........................................*
  * Inp: tipoServicio:String -> "aceptar","reintentar","notificar"....................................* 
  * Inp: List<Integer> listaTrx: Liata de IDs de Trx..................................................* 
  * @return String: respuesta de la ejecución.........................................................* 
  *****************************************************************************************************/
 public String ejecutarServicioTransaccionesManual(String tipoServicio, int[] listaTrx, ServicioBL servBL,
		 String origen)
 {
     String respTrx = "";
     String aceptarStr = "aceptar";
     String notificarStr = "notificar";
     String reintentarStr = "reintentar";

     try
     {
    	 for(int t=0;t<listaTrx.length;t++) 
         {
        	 if(tipoServicio.equals(aceptarStr))
        	 {
        		 respTrx += this.aceptarTrx(listaTrx[t], servBL);
        	 }
        	 if(tipoServicio.equals(notificarStr))
        	 {
        		 respTrx += this.notificarTrx(listaTrx[t], "OK", servBL);
        	 }
        	 if(tipoServicio.equals(reintentarStr))
        	 {
        		 respTrx += this.reintentarTrx(listaTrx[t], servBL, new ResponseDTO(), false, servBL.obtenerPropertiesServicioCargo().getProp().getServicioIdCanalS(), origen);
        	 }
         }
     }
     catch(Exception ex) 
     {
    	 respTrx = "Error al ejecutar el servicio "+tipoServicio+" sobre las transacciones";
         LOGGER.error("Error inicioCCA:  "+  respTrx + " | "  +ex);

     }
     
     return respTrx;
 }
 
 /*****************************************************************************************************
  * Nombre funcion: aceptarTrx........................................................................*
  * Action: acepta el idTrx cambiando su estado a "P" y notificando a CCA.............................*
  * Inp: idTrx:int -> ID de la trx a aceptar..........................................................* 
  * @return String: respuesta de la trx...............................................................* 
 * @throws SQLException 
  *****************************************************************************************************/
 public String aceptarTrx(int idTrx, ServicioBL servBL) throws SQLException
 {
     String respTrx = "";

    	 Transaccion trx = this.obtenerTransaccion(idTrx);
    	 trx.setEstadoActual("P");	//--- P: Procesada (aceptada)
         this.actualizarEstadoTransaccion(trx);
         
         String tipoEvento = EventoTransaccion.CAMBIO_ESTADO.getValue();
         String detalleTrx = "La transacción se ha aceptado manualmente";
         this.generarHistoriaTransaccion(trx, tipoEvento, 0, detalleTrx);
         
         tipoEvento = EventoTransaccion.NOTIFICACION.getValue();
        try 
        {
        	NotificacionCCABL.notificacionTrxCCAHistoria(servBL, "OK", trx);
     		
     		detalleTrx = "La transacción aceptada manualmente, se ha notificado a CCA";
     		this.generarHistoriaTransaccion(trx, tipoEvento, 0, detalleTrx);
        }
        catch (TimeoutException e) 
        {
            detalleTrx = "La transacción aceptada manualmente, no se pudo notificar a CCA: "+e.getMessage();
            this.generarHistoriaTransaccion(trx, tipoEvento, 13, detalleTrx);
     		
           LOGGER.error("Error inicioCCA: "+e);
           LOGGER.error("Error inicioCCA: "+e.getMessage());
        }
     
    
     
     return respTrx;
 }
 

 /*****************************************************************************************************
  * Nombre funcion: notificarTrx......................................................................*
  * Action: notifica la idTrx como OK a CCA...........................................................*
  * Inp: idTrx:int -> ID de la trx a aceptar..........................................................* 
  * @return String: respuesta de la trx...............................................................* 
  *****************************************************************************************************/
 public String notificarTrx(int idTrx, String okNok, ServicioBL servBL)
 {
     String respTrx = "";
     String detalleTrxX = "";
     String tipoEventoX  = "";
     Transaccion trxX = null;
     try
     {
    	 trxX = this.obtenerTransaccion(idTrx);
    	 tipoEventoX = EventoTransaccion.NOTIFICACION.getValue();
    	

        	NotificacionCCABL.notificacionTrxCCAHistoria(servBL, okNok, trxX);
     		
     		detalleTrxX = "La transacción se ha notificado manualmente a CCA con un "+okNok;
     		this.generarHistoriaTransaccion(trxX, tipoEventoX, 0, detalleTrxX);
        
     }
     catch(Exception ex) 
     {
    	 respTrx = ex.getMessage();
    	 LOGGER.error(ex);
    	 detalleTrxX = "La transacción no se pudo notificar manualmente a CCA: "+ex.getMessage();
         this.generarHistoriaTransaccion(trxX, tipoEventoX, 13, detalleTrxX);
  		
        LOGGER.error("Error notificación CCA manual: "+ex);
        LOGGER.error("Error notificación CCA manual: "+ex.getMessage());
     }

     
     return respTrx;
 }
 
 /*****************************************************************************************************
  * Nombre funcion: reintentarTrx.....................................................................*
  * Action: reintenta una trx.........................................................................*
  * Param: idTrx:int -> ID de la trx a aceptar........................................................* 
  * Param: servBL:ServicioBL -> Clase que ejeuta el servicior.........................................* 
  * Param: resp:ResponseDTO -> Clase interna requerida en el reintento................................* 
  * Param: generarCodigoIdTrx:boolean -> verifica si se genera o no el codigoIdTrx....................* 
  * Param: idCanalGiro:String -> Id del canal de giro.................................................* 
  * @return String: respuesta de la trx...............................................................* 
  * @throws SQLException 
  *****************************************************************************************************/
 public String reintentarTrx(int idTrx, ServicioBL servBL, ResponseDTO resp,
		 boolean generarCodigoIdTrx, String idCanalGiro, String origen) throws TimeoutException
 {
     String respTrx = "X";
     String tipoEvento = EventoTransaccion.REINTENTO.getValue();
     String detalleTrx = "";
     Transaccion trxBD = null;
	try {
		trxBD = this.obtenerTransaccion(idTrx);
		servBL.generarCodigoIdTrx(idCanalGiro, trxBD);
		actualizarCodigoIdTrx(trxBD);
	} catch (SQLException e) {
        LOGGER.debug("error SQL: "+e);
	}
     try
     {
    	 LOGGER.debug("inicio del reintento de la trx id: "+idTrx);
    	 
        detalleTrx = "Inicio del reintento manual de trx";
		this.generarHistoriaTransaccion(trxBD, tipoEvento, 0, detalleTrx);
		
		if(generarCodigoIdTrx) 
		{
			//---- Generar codigoIdTrx previo al giro
			String codigoIdTrx = this.generarCodigoIdTrx(idCanalGiro);
			if(trxBD!=null) {
			trxBD.setCodigoIdTrx(codigoIdTrx);
			LOGGER.debug("codIdTrx de reintento: "+trxBD.getCodigoIdTrx());

			}
			
		}
		
		//---- El reintento del cargo genera mas historias internas
        servBL.ejecutarCargo(trxBD, tipoEvento, resp, true, origen);
        
        LOGGER.debug("Fin reintento trx id: "+idTrx);
     }
     catch(Exception ex) 
     {
    	 detalleTrx = "La transacción no se pudo reintentar "+ex.getMessage();
         this.generarHistoriaTransaccion(trxBD, tipoEvento, 13, detalleTrx);
  		
        LOGGER.error("Error reintento trx manual: "+ex);
     }
     
     return respTrx;
 }

}
