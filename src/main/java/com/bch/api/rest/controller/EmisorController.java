package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.EmisorBL;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseEmisor;
import com.bch.api.rest.entities.Emisor;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador Emisor para ejecución de funcionalidades de la API Banco Compensador
 * @author 160k
 */
@RestController
public class EmisorController {

 private static final Logger LOGGER = Logger.getLogger(EmisorController.class);
 
 @Autowired
 private EmisorBL emiBL;
 
 /****************************************************************************************
  * Nombre funcion: newEmisor............................................................*
  * Action: Agrega un nuevo emisor.......................................................*
  * @param emi => Emisor objeto con datos del emisor.....................................* 
  * @return: Resultado de ingreso de nuevo emisor........................................*
  * @throws InterruptedException Exception execpcion Interru´pcion.......................*
  * @throws Exception Exception execpcion Global...................................*
  ***************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/nuevo-emisor", method = RequestMethod.POST, consumes = "application/json")
 public ResponseDTO newEmisor(@RequestBody Emisor emi) throws InterruptedException
 {
   ResponseDTO response = new ResponseDTO();
   try {
	 LOGGER.debug("Ingresa a generar nuevo emisor");
	 return emiBL.ingresarEmisor(emi);

   }catch(Exception ex) {
	   
	   response.setMessage("Error al ingresar el emisor");
	   LOGGER.error("Error: nuevo emisor:" + ex );
	   response.setStatusCode(400);
	   response.setCodigoOperacion("400");
	   return response;
   }
  
 }
 
 /****************************************************************************************
  * Nombre funcion: updateEmisor.........................................................*
  * Action: Actualiza un  emisor.........................................................*
  * @param emi => Emisor objeto con datos del emisor..................................* 
  * @return Resultado de actualizacion del emisor.......................................*
  * @throws InterruptedException Exception execpcion Interru´pcion.......................*
  * @throws Exception Exception execpcion Global.........................................*
  ***************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/actualizar-emisor", method = RequestMethod.PUT, consumes = "application/json")
 public ResponseDTO updateEmisor(@RequestBody Emisor emi) throws InterruptedException
 {
	 ResponseDTO response = new ResponseDTO();
	 try {
		 LOGGER.debug("Ingresa a actualizar emisor :" + emi.getCodigoEmisor());
		 return emiBL.updateEmisor(emi);
	 
 	}catch(Exception ex) {
	   response.setMessage("Error al actualizar emisor");
	   response.setStatusCode(400);
	   response.setCodigoOperacion("400");
	   LOGGER.error("Error: actualizar emisor:" + ex );
	   return response;
 	}
 }
 
 /*****************************************************************************************************
  * Nombre funcion: listarEmisores....................................................................*
  * Action: obtiene los emisores desde la BD..........................................................*
  * @param  estadoEmisor => int.......................................................................* 
  * @return completedFuture(rcm) => CompletableFuture<ResponseEmisor>=> lista de Emisores.............*
  * @throws SQLException => execpcion de SQL..........................................................*
  * @throws Exception execpcion Global................................................................*
  *****************************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/lista-emisores", method = RequestMethod.GET)
 public ResponseEmisor listarEmisores(int estadoEmisor) throws SQLException 
 {
  ResponseEmisor rEmisor = new ResponseEmisor();
  rEmisor =  emiBL.obtenerEmisores(estadoEmisor);
  try
  {
	 return rEmisor;
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   rEmisor.setStatusCode(500);
   rEmisor.setEmisores(null);
   
   return rEmisor;
  }
 }
 
 /***********************************************************************************************
  * Nombre funcion: listarEmisores..............................................................*
  * Action: obtiene los emisores desde la BD....................................................*
  * @param estadoEmisor  => int..................................................................* 
  * @param codigoEmisor => int...................................................................* 
  * @return completedFuture(rcm):CompletableFuture<ResponseEmisor>=> lista de Emisores..........*
  * @throws SQLException ........................................................................*
  ***********************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/cambio-estado-emisor", method = RequestMethod.PUT)
 public ResponseDTO cambioEstadoEmisor(int estadoEmisor, int codigoEmisor) throws SQLException 
 {
	 ResponseDTO rEmisor = new ResponseDTO();
	 rEmisor =  emiBL.cambioEstadoEmisor(codigoEmisor, estadoEmisor);
  try
  {
	return  rEmisor;
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   rEmisor.setStatusCode(500);
   
   return rEmisor;
  }
  

 }
 
}
