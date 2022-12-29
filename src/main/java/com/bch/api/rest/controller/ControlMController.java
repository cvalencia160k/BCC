package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.ControlMBL;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ctrm.ResponseControlM;
import com.bch.api.rest.dto.ctrm.TransaccionControlM;

import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador para eventos Control-M
 * @author 160k
 */
@RestController
public class ControlMController {

 @Autowired
 private ControlMBL ctrM;
 
 private static final Logger LOGGER = LogManager.getLogger(ControlMController.class);
 
 /*****************************************************************************************************
  * Nombre funcion: listarTrxRechazadas...............................................................*
  * Action: Servicio para obtener la lista de transacciones rechazadas desde la BD....................*
  * Inp: .............................................................................................* 
  * @return completedFuture(rcm):CompletableFuture<ResponseControlM>=> lista de transacciones rechazadas*
  *****************************************************************************************************/
 @Async
 @ResponseBody
 @RequestMapping(value= "/lista-trx-rechazadas", method = RequestMethod.POST)
 public CompletableFuture<ResponseControlM> listarTrxRechazadas() 
 {
  ResponseControlM rcm = new ResponseControlM();
  try
  {
   rcm =  ctrM.consultartrxRechazadas();
   return CompletableFuture.completedFuture(rcm);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   rcm.setStatusCode(500);
   rcm.setListaTrxRechazadas(null);
   
   return CompletableFuture.completedFuture(rcm);
  }
 }

 
 /*****************************************************************************************************
  * Nombre funcion: listarTrxRechazadasFinal...............................................................*
  * Action: Servicio para obtener la lista de transacciones rechazadas desde la BD....................*
  * Inp: .............................................................................................* 
  * @return completedFuture(rcm):CompletableFuture<ResponseControlM>=> lista de transacciones rechazadas*
  *****************************************************************************************************/
 @Async
 @ResponseBody
 @RequestMapping(value= "/lista-trx-rechazadasCM", method = RequestMethod.GET)
 public CompletableFuture<String> listarTrxRechazadasCM() 
 {
  ResponseControlM rcm = new ResponseControlM();
  String origen = "CCA";
  try
  {
   String ids = "";
   rcm =  ctrM.consultartrxRechazadas();
   
   for (TransaccionControlM t : rcm.getListaTrxRechazadas()) {
	   if(t.getOrigen().equals(origen)) {
		   ids+=t.getIdTransaccion() + ",";
	   }
   }
   
   return CompletableFuture.completedFuture(ids);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   rcm.setStatusCode(500);
   rcm.setListaTrxRechazadas(null);
   
   return CompletableFuture.completedFuture("Error :" + ex);
  }
 }
 
 
 /*********************************************************************************************
  * Nombre funcion: ejecutarReintento.........................................................*
  * Action: Re-intento de cargo...............................................................*
  * @param idTrx <int> => id trx...............................................................* 
  * @return  status trx:CompletableFuture<ResponseDTO>=> objeto con statusCode, mensaje, codigoOp
  ********************************************************************************************/
 @Async
 @ResponseBody
 @RequestMapping(value= "/reintento-cargo", method = RequestMethod.POST)
 public CompletableFuture<ResponseDTO> ejecutarReintento(int idTrx) 
 {
  ResponseDTO resp = new ResponseDTO();
  try
  {
   resp = ctrM.reintentoCargo(idTrx,"Reintento");
   return CompletableFuture.completedFuture(resp);
  }
  catch(Exception ex) 
  {
   LOGGER.error("error ejecutar reintento:"+ex);
   resp.setStatusCode(500);
   resp.setCodigoOperacion("001");
   resp.setMessage(ex.getMessage());
   
   return CompletableFuture.completedFuture(resp);
  }
 }
}
