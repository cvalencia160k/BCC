package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.SesionBL;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseSesion;
import com.bch.api.rest.entities.Sesion;
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
public class SesionController {

 private static final Logger LOGGER = Logger.getLogger(SesionController.class);
 
 @Autowired
 private SesionBL sesBL;
 
 /****************************************************************************************
  * Nombre funcion: newSesion............................................................*
  * Action: Agrega un nueva sesion.......................................................*
  * @param ses => Sesion objeto con datos del ssion.....................................* 
  * @return: Resultado de ingreso de nuevo sesion........................................*
  * @throws InterruptedException Exception execpcion Interru´pcion.......................*
  * @throws Exception Exception execpcion Global...................................*
  ***************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/nuevo-sesion", method = RequestMethod.POST, consumes = "application/json")
 public ResponseDTO newSesion(@RequestBody Sesion ses) throws InterruptedException
 {
   ResponseDTO response = new ResponseDTO();
   try {
	 LOGGER.debug("Ingresa a generar nuevo sesion");
	 return sesBL.ingresarSesion(ses);

   }catch(Exception ex) {
	   
	   response.setMessage("Error al ingresar el sesion");
	   LOGGER.error("Error: nuevo sesion:" + ex );
	   response.setStatusCode(400);
	   response.setCodigoOperacion("400");
	   return response;
   }
  
 }
  
 /*****************************************************************************************************
  * Nombre funcion: listarSesiones....................................................................*
  * Action: obtiene los sesiones desde la BD..........................................................*
  * @param  fechaIni => String........................................................................*
  * @param  fechaFin => String........................................................................* 
  * @return ResponseSesion => ........................................................................*
  * @throws SQLException => execpcion de SQL..........................................................*
  * @throws Exception execpcion Global................................................................*
  *****************************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/lista-sesiones", method = RequestMethod.GET)
 public ResponseSesion listarSesiones(String fechaIni, String fechaFin) throws SQLException 
 {
  ResponseSesion rSesion = new ResponseSesion();
  rSesion =  sesBL.obtenerSesiones(fechaIni, fechaFin);
  try
  {
	 return rSesion;
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   rSesion.setStatusCode(500);
   rSesion.setSesiones(null);
   
   return rSesion;
  }
 }
 
}
