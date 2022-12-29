package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.ServicioBL;
import com.bch.api.rest.bl.TransaccionBL;
import com.bch.api.rest.dto.RequestServicioTrxDTO;
import com.bch.api.rest.utils.FuncionesGenerales;

import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controlador que ejecuta servicios sobre las trx
 * @author 160k
 */
@RestController
public class ServicioTrxController {

 private static final Logger LOGGER = Logger.getLogger(ServicioTrxController.class);
 @Autowired
 private ServicioBL servBL;
 @Autowired
 private TransaccionBL tranBL;
 
 
 /*****************************************************************************************************
  * Nombre funcion: servicioTrx.......................................................................*
  * Action: ejecuta cierto servicio sobre un grupo de trx (aceptación, reintento, notificación).......*
  * @param req = >RequestServicioTrxDTO -> "aceptar","reintentar","notificar".........................* 
  * @return ..........................................................................................* 
  ****************************************************************************************************/
 @RequestMapping(value = "/servicio-trx", method = RequestMethod.POST, produces ="application/text")
 public CompletableFuture<String> servicioTrx2(@RequestBody RequestServicioTrxDTO req)
 {
	String resp = "";
    try
    {
    	String tipoServicio = FuncionesGenerales.cleanXSS(req.getTipoServicio());
    	String strIdsTrx = FuncionesGenerales.cleanXSS(req.getArrIdTrx());
    	
    	boolean esVacioTipoServicio = FuncionesGenerales.isNullOrEmpty(tipoServicio);
    	boolean esVacioArrTrxStr = FuncionesGenerales.isNullOrEmpty(strIdsTrx);
    	
    	if(esVacioTipoServicio || esVacioArrTrxStr) 
    	{
    		if(esVacioTipoServicio)
    		{
    			resp+="- Debe indicarse un servicio a ejecutar.<br>";
    		}
    		if(esVacioArrTrxStr)
    		{
    			resp+="- Debe seleccionar una o varias transacciones a operar.<br>";
    		}
    	}
    	else
    	{
    		String[] arrIdTrxStr = strIdsTrx.split(",");
    		int[] arrIdTrx = new int[arrIdTrxStr.length];
    		for(int i=0;i<arrIdTrxStr.length;i++) 
    		{
    			arrIdTrx[i] = Integer.parseInt(arrIdTrxStr[i]);
    		}
    		
    		resp = this.ejecutarTiposervicioTrx(req.getTipoServicio(), arrIdTrx, servBL);
    	}
    }
    catch(Exception ex) 
    {
    	LOGGER.error(ex);
    	resp = "Error al ejecutar el servicio "+req.getTipoServicio()+" sobre el grupo de transacciones";
    }
    
    return CompletableFuture.completedFuture(resp);
 }
 
 /*****************************************************************************************************
  * Nombre funcion: ejecutarTiposervicioTrx...........................................................*
  * Action: ejecuta cierto servicio sobre un grupo de trx (aceptación, reintento, notificación).......*
  * @param tipoServicio => String -> "aceptar","reintentar","notificar"...............................* 
  * @param listaTrx =>  List<Integer> Liata de IDs de Trx.............................................*
  * @param servBL  => ServicioBL .....................................................................* 
  * @return ..........................................................................................* 
  ****************************************************************************************************/
 public String ejecutarTiposervicioTrx(String tipoServicio, int[] listaTrx, ServicioBL servBL) 
 {
	 String resp = "";
	 String rAceptar="aceptar";
	 String rNotificar="notificar";
	 String rReintentar="reintentar";
	    try
	    {
	    	//--- clear XSS
	    	tipoServicio = FuncionesGenerales.cleanXSS(tipoServicio);
	    	
	    	if(tipoServicio.equals(rAceptar) || 
	    		tipoServicio.equals(rNotificar) || 
	    		tipoServicio.equals(rReintentar))
	    	{
	    		resp = tranBL.ejecutarServicioTransaccionesManual(tipoServicio, listaTrx, servBL, "Manual");
	    	}
	    	else 
	    	{
	    		resp = "Servicio de trx no encontrado";
	    	}
	    }
	    catch(Exception ex) 
	    {
	       resp = "Error al ejecutar el servicio "+tipoServicio+" sobre el grupo de transacciones";
	       LOGGER.error(ex);
	    }

	    return resp;
 }
 
}
