package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.InstitucionBL;
import com.bch.api.rest.entities.Institucion;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador Emisor para ejecución de funcionalidades de la API Banco Compensador
 * @author 160k
 */
@RestController
public class InstitucionController {

 private static final Logger LOGGER = Logger.getLogger(InstitucionController.class);
 
 @Autowired
 private InstitucionBL insBL;
 
 /*****************************************************************************************************
  * Nombre funcion: listarEmisores...............................................................*
  * Action: obtiene los emisores desde la BD....................*
  * Inp: .............................................................................................* 
  * Inp: .............................................................................................* 

  * @return completedFuture(rcm):CompletableFuture<ResponseEmisor>=> lista de Emisores*
  *****************************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/lista-instituciones", method = RequestMethod.GET)
 public List<Institucion> listarInstituciones() 
 {
	 List<Institucion> lista = new ArrayList<Institucion>();
  try
  {
	  lista =  insBL.listarInstituciones();
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
  }
  
  return lista;
 }
 
}
