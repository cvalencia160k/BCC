package com.bch.api.rest.bl;

import java.util.List;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ctrm.ResponseControlM;
import com.bch.api.rest.dto.ctrm.TransaccionControlM;
import com.bch.api.rest.dto.filtrobusq.ConsultaTransaccion;

@Component
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
/**
 * Controlador principal para Control-M
 * @author 160k
 *
 */
public class ControlMBL {

 @Autowired
 private ServicioBL servBL;

 @Autowired
 private TransaccionBL tranBL;
 
 @Value("${ws.girarCuenta.id-canal}")
 private  String idCanalGiro;
 
 private static final Logger LOGGER = Logger.getLogger(ControlMBL.class);

 /**
  * Consultar trx rechazadas
  * @return lista de trx rechazadas
  */
 public ResponseControlM consultartrxRechazadas()
 {
  ResponseControlM rcm = new ResponseControlM();
  
  try
  {
   //---- Filtro de búsqueda para rechazados
   ConsultaTransaccion ctran = new ConsultaTransaccion();
   ctran.setEstadoTrxs("R"); //--- R: rechazadas
   ctran.setTipoTrxs("R");  //--- R: Recibidas
   
   List<TransaccionControlM> lista = tranBL.consultarTrx(ctran);
   
   rcm.setStatusCode(200);
   rcm.setListaTrxRechazadas(lista);
   
   return rcm;
  }
  catch(Exception ex)
  {
   LOGGER.error(ex);
   rcm.setStatusCode(500);
   rcm.setListaTrxRechazadas(null);

   return rcm;
  }
 }
 
 /**
  * Reintentar un cargo
  * @param idTrx ID de bd de la transacción
  * @return
 * @throws TimeoutException 
  */
 public ResponseDTO reintentoCargo(int idTrx, String origen) throws TimeoutException 
 {
  ResponseDTO resp = new ResponseDTO();
  
  resp.setStatusCode(200);
   
   LOGGER.debug("idTrx: "+idTrx);
   
   tranBL.reintentarTrx(idTrx, servBL, resp, true, idCanalGiro, origen);
   
   return resp;
 }
}
