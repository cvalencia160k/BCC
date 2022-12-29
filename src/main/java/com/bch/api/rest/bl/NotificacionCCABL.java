package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.bch.api.rest.entities.Transaccion;
import java.util.concurrent.TimeoutException;

/**
 * Capa de negocio encargada de emitir las notificaciones a cca
 * @author 160k
 *
 */
@Component
public class NotificacionCCABL {

 private static final Logger LOGGER = Logger.getLogger(NotificacionCCABL.class);
 
 
 NotificacionCCABL() {
	 LOGGER.debug("Inicio NotificacionCCABL");
 }
 
 /**
  * Notificación a la CCA para historia
  * @param servBL servicios utilizados
  * @param okNok "OK" o "NOK"
  * @param tranBD datos de la trx
  * @param codigoOperacion codigoOperación actual de la trx
  * @throws TimeoutException exception de timeout
  */
 public static void notificacionTrxCCAHistoria(ServicioBL servBL, String okNok,
		 Transaccion tranBD) throws TimeoutException 
 {
     try 
     {
        //--- Notificar a CCA
        boolean indicador = false;
        String statusCodeSucccess = "OK";
       if(okNok.equals(statusCodeSucccess)) 
       {
          indicador = true;
       }
       else
       {
          indicador = false;
       }
  
       servBL.notificarCCA(tranBD, indicador,10000);
     }
     catch (NumberFormatException e)
     {
         LOGGER.error("Error notificar TRX a CCA hilo paralelo:"+ e);
         LOGGER.error("Error notificar TRX a CCA hilo paralelo:"+ e.getMessage());
     }
     catch (TimeoutException e)
     {
         LOGGER.error("Error notificar TRX a CCA hilo paralelo:"+ e);
         LOGGER.error("Error notificar TRX a CCA hilo paralelo:"+ e.getMessage());
     }
 }
}
