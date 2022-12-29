package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.TarifaBL;
import com.bch.api.rest.dto.TarifaDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador para tarifas
 * @author 160k
 */
@RestController
public class TarifaController {

 private static final Logger LOGGER = Logger.getLogger(TarifaController.class);
 @Autowired
 private TarifaBL tarBL;
 
 /*****************************************************************************************************************
  * Nombre funcion: consultarTarifas..............................................................................*
  * Action: obtiene las tarifas de los emisores desde la BD (si no se ingresa emisor, obtiene todas las tarifas...*
  * @param codigoEmisor => int....................................................................................* 
  * @return lista de tarifas de emisores..........................................................................*
  ****************************************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/lista-tarifas", method = RequestMethod.GET)
 public List<TarifaDTO> listarTarifas(int codigoEmisor) 
 {
	 List<TarifaDTO> lista = new ArrayList<TarifaDTO>();
  try
  {
	  return tarBL.listarTarifas(codigoEmisor);
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al listar las tarifas: "+ex.getMessage());
   LOGGER.error("Error al listar las tarifas: "+ex);
   return lista;
  }
 }
 
}
