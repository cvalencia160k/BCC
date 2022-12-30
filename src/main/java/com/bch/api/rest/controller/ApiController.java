package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.InformarTefBL;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.TransaccionDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador principal para ejecución de funcionalidades de la API Banco Compensador
 * @author 160k
 */
@RestController
public class ApiController {

 @Autowired
 private InformarTefBL infoBL;
 private static final Logger LOGGER = Logger.getLogger(ApiController.class);
private String dateString = "yyyy-MM-dd HH:mm:ss";
 /***********************************************************************************************************
  * Nombre funcion: InformarTEF3............................................................................*
  * Action: Servicio para ejecutar la acreditación de fondos................................................*
  * @param tran <TransaccionDTO> => tran objeto con datos de la transacción a registrar......................* 
  * @return: InformarTEF(tran):CompletableFuture<ResponseDTO>=> objeto con statusCode, mensaje, codigoOp*
  * @throws InterruptedException exception lanzada por interrupción abrupta o time out
  **********************************************************************************************************/
 @Async
 @ResponseBody
 @RequestMapping(value= "/acreditar-fondos", method = RequestMethod.POST, consumes = "application/json")
 public CompletableFuture<ResponseDTO> informarTEF3(@RequestBody TransaccionDTO tran) throws InterruptedException
 {
  try
  {
	  LOGGER.debug("####################INICIO PROCESO DE ACREDITAR FONDOS#############################");
	  
	  String fechaHoy = new SimpleDateFormat(dateString).format(new Date());
	   Date start = new SimpleDateFormat(dateString).parse(tran.getFechaHoraTrx()); 
	   Date end = new SimpleDateFormat(dateString).parse(fechaHoy);
	   
	   
	   boolean validaFecha = start.before(end);

	  
	  return infoBL.informarTEF(tran,"Cargo","CCA", validaFecha);
  }
  catch(Exception ex) 
  {
   ResponseDTO r2 = new ResponseDTO();
   r2.setMessage("Error!!!234");
   LOGGER.error("Error: InformatTEF3:" + ex );
   return CompletableFuture.completedFuture(r2);
  }
 }

}
