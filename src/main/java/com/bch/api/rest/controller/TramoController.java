package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bch.api.rest.bl.TramoBL;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseTramo;
import com.bch.api.rest.entities.Tramo;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador Tramo para ejecución de funcionalidades de la API Banco Compensador
 * @author 160k
 */
@RestController
public class TramoController {

 private static final Logger LOGGER = Logger.getLogger(TramoController.class);
 @Autowired
 private TramoBL tramBL;
 
 /***************************************************************************************
  * Nombre funcion: newTramo............................................................*
  * Action: Agrega un nuevo tramo.......................................................*
  * @param tram => Tramo objeto con datos del tramo....................................* 
  * @return Resultado de ingreso de nuevo tramo........................................*
  * @throws InterruptedException  => exception lanzada por interrupción abrupta
  * @throws Exception =>  exception lanzada por interrupción abrupta o time out
  **************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/nuevo-tramo", method = RequestMethod.POST, consumes = "application/json")
 public ResponseDTO newTramo(@RequestBody Tramo tram) throws InterruptedException
 {
   ResponseDTO response = new ResponseDTO();
   try 
   {
     LOGGER.debug("Ingresa a generar nuevo tramo");
	 response = tramBL.ingresarTramo(tram);

   }catch(Exception ex) {
	   LOGGER.error("Error: nuevo tramo:" + ex );
	   response.setMessage("Error al registrar nuevo tramo");
	   response.setStatusCode(400);
	   response.setCodigoOperacion("400");
   }
   
   return response;
  
 }
 
 /****************************************************************************************
  * Nombre funcion: updateTramo..........................................................*
  * Action: Actualiza un  tramo..........................................................*
  * @param tram => Tramo objeto con datos del tramo.....................................* 
  * @return: Resultado de actualizacion del tramo........................................*
  * @throws InterruptedException => exception lanzada por interrupción abrupta o time out
  * @throws Exception =>  exception lanzada por interrupción abrupta o time out
  ***************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/actualizar-tramo", method = RequestMethod.PUT, consumes = "application/json")
 public ResponseDTO updateTramo(@RequestBody Tramo tram) throws InterruptedException
 {
	 ResponseDTO response = new ResponseDTO();
	 try {
		 LOGGER.debug("Ingresar a actualizar tramo :" + tram.getIdTramo());
		 return tramBL.updateTramo(tram);
	 
 	}catch(Exception ex) {
	   response.setMessage("Error al actualizar tramo");
	   response.setStatusCode(400);
	   response.setCodigoOperacion("400");
	   LOGGER.error("Error: actualizar tramo:" + ex );
	   return response;
 	}
 }
 
 /**************************************************************************************
  * Nombre funcion: listarTramos.......................................................*
  * Action: obtiene los tramos desde la BD.............................................*
  * @throws SQLException =>  exception lanzada por interrupción abrupta o time out
  * @throws Exception =>  exception lanzada por interrupción abrupta o time out
  * @return completedFuture(rcm):CompletableFuture<ResponseEmisor>=> lista de Emisores.*
  *************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/lista-tramos", method = RequestMethod.GET)
 public ResponseTramo listarTramos() throws SQLException 
 {
	 ResponseTramo rTramo = new ResponseTramo();
	 rTramo =  tramBL.obtenerTramos();
  try
  {
	 return rTramo;
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   rTramo.setStatusCode(500);
   rTramo.setTramos(null);
   
   return rTramo;
  }
 }
 
 /**************************************************************************************
  * Nombre funcion: deleteTramo........................................................*
  * Action: oelimina tramo desde la BD.................................................*
  * @param idTramo => int .............................................................* 
  * @return completedFuture(rcm):CompletableFuture<ResponseEmisor>=> lista de Emisores.*
  *************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/eliminar-tramo", method = RequestMethod.DELETE)
 public ResponseDTO deleteTramo(int idTramo) 
 {
	 ResponseDTO rTrammo = new ResponseDTO();
	 rTrammo =  tramBL.deleteTramo(idTramo);
  try
  {
	return  rTrammo;
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   rTrammo.setStatusCode(500);
   
   return rTrammo;
  }
 }

 /**************************************************************************************
  * Nombre funcion: obtenerTramos......................................................*
  * Action: obtiene los tramos desde la BD.............................................*
  * @param idTramo => int .............................................................* 
  * @throws SQLException =>  exception lanzada por interrupción abrupta o time out
  * @throws Exception =>  exception lanzada por interrupción abrupta o time out
  * @return completedFuture(rcm):CompletableFuture<ResponseEmisor>=> lista de Emisores.*
  *************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/obtener-tramo", method = RequestMethod.GET)
 public ResponseTramo getTramo(int idTramo) throws SQLException 
 {
	 ResponseTramo rTramo = new ResponseTramo();
	 rTramo =  tramBL.obtenerTramo(idTramo);
  try
  {
	 return rTramo;
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   rTramo.setStatusCode(500);
   rTramo.setTramos(null);
   
   return rTramo;
  }
 }
 
}
