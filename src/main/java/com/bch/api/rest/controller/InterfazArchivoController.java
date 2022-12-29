package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.InterfazArchivoBL;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controlador de Interfaces-Archivo para la API Banco Compensador
 * @author 160k
 */
@RestController
public class InterfazArchivoController {

 private static final Logger LOGGER = Logger.getLogger(InterfazArchivoController.class);
 
 @Autowired
 private InterfazArchivoBL intArchBL;
 
 /**
  * Procesa un tipo de interfaz de archivo definido
  * @param tipoInterfaz "trx-compen" o "tarifas"
  * @throws IOException => execion de input
  * @return => ResponseEntity<InputStreamResource>
  */
 @RequestMapping(value= "/procesar-interfaz-archivo", method = RequestMethod.GET, produces = "application/json")
 public ResponseEntity<InputStreamResource> procesarInterfazArchivo(String tipoInterfaz) throws IOException 
 {
	 LOGGER.debug("inicio procesarInterfazArchivo: "+tipoInterfaz);
	 return intArchBL.procesarInterfazArchivo(tipoInterfaz);
 }
}
