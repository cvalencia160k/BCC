package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.TransaccionBL;
import com.bch.api.rest.dto.ListaResultadoPaginacionDTO;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.utils.FuncionesGenerales;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador Tramo para ejecución de funcionalidades de la API Banco Compensador
 * @author 160k
 */
@RestController
public class TransaccionController {

 private static final Logger LOGGER = Logger.getLogger(TransaccionController.class);
 @Autowired
 private TransaccionBL tranBL;
 
 
 /********************************************************************
  * Nombre funcion: Listar historico de transacciones................*
  * Action: obtiene listado de transacciones a lo largo del tiempo...*
  * @param fechaIni  => String.......................................*
  * @param fechaFin  => String.......................................* 
  * @param idCodigoInstitucion  => int...............................*
  * @param estadoTrx  => String......................................*
  * @param  pageNumber  => int.......................................*   
  * @return lista de resultado + totalRegistros de la consulta.......* 
  *******************************************************************/
 @ResponseBody
 @RequestMapping(value= "/consulta-trx-historica", method = RequestMethod.GET)
 public ListaResultadoPaginacionDTO lisTrxHistorias(
		 String fechaIni, 
		 String fechaFin,
		 int idCodigoInstitucion, 
		 String estadoTrx, 
		 int pageNumber) 
 {
  try
  {
	 return tranBL.obtenerTransaccionHistoricas(
			 fechaIni,
			 fechaFin,
			 idCodigoInstitucion, 
			 estadoTrx, 
			 10, 
			 pageNumber);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return new ListaResultadoPaginacionDTO();
  }
 }
 
 /**********************************************************************
  * Nombre funcion: listarTrxHistorias.................................*
  * Action: obtiene las historias de una trx ordenadas por fecha desc..*
  * @param idTrx  => int..........................................* 
  * @return lista de resultado + totalRegistros de la consulta.........* 
  *********************************************************************/
 @ResponseBody
 @RequestMapping(value= "/historias-trx-detalle", method = RequestMethod.GET)
 public ListaResultadoPaginacionDTO listarTrxHistorias(int idTrx) 
 {
  try
  {
	 return tranBL.obtenerHistoriasTransaccion(idTrx);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return new ListaResultadoPaginacionDTO();
  }
 }
 
 
 /******************************************************************
  * Nombre funcion: obtenerTransaccionesRA.........................*
  * Action: obtiene las trx de las ultimas 24 horas habiles........*
  * @param estado  => String...............................*
  * @param pageNumber  => int.................................* 
  * @return lista de resultado + totalRegistros de la consulta.....* 
  *****************************************************************/
 @ResponseBody
 @RequestMapping(value= "/obtener-transacciones-RA", method = RequestMethod.GET)
 public ListaResultadoPaginacionDTO obtenerTransaccionesRA(String estado, int pageNumber) 
 {
  try
  {
	 return tranBL.obtenerTransaccionesRA(estado, 10, pageNumber);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return  new ListaResultadoPaginacionDTO();
  }
 }
 
 /***********************************************************************************************************
  * Nombre funcion: InformarTEF3............................................................................*
  * Action: Servicio para ejecutar la acreditación de fondos................................................*
  * @param estado  => int......................* 
  * @param idTrx  => int......................* 
  * @return: InformarTEF(tran):CompletableFuture<ResponseDTO>=> objeto con statusCode, mensaje, codigoOp*
  * @throws InterruptedException exception lanzada por interrupción abrupta o time out
  **********************************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/cambio-estado-trx", method = RequestMethod.PUT)
 public Transaccion cambioEstadoTrx(String estado, int idTrx) 
 {
	 Transaccion trx = new Transaccion();
  try
  {
	//--- Limitar tamaño string (según bd)
	estado = FuncionesGenerales.limitarTamanoString(estado, 1);	//-- P,A,N,R
			 
	//--- Limpiar XSS
	estado = FuncionesGenerales.cleanXSS(estado);
	
	trx = tranBL.obtenerTransaccion(idTrx);
	trx.setEstadoActual(estado);
	
	tranBL.actualizarEstadoTransaccion(trx);
	
	return trx;
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   trx.setIdTransaccion(-1);
   return trx;
   
  }
 }
 
}
