package com.bch.api.rest.services.client.soap.clientes;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import org.apache.log4j.Logger;
import com.bch.api.rest.dal.TransaccionDAL;
import com.bch.api.rest.dto.ServicioConsultaDatosDTO;
import com.bch.api.rest.dto.ServicioResponseDTO;
import com.bch.api.rest.dto.SoapSecurityDTO;
import com.bch.api.rest.dto.SoapXMLNsDTO;
import com.bch.api.rest.properties.ServicioConsultaProperties;
import com.bch.api.rest.utils.FuncionesGenerales;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Cliente que ejecuta el servicio de consulta
 * @author 160k
 *
 */
public class ClienteConsulta2 {

 private static final Logger LOGGER = Logger.getLogger(ClienteConsulta2.class);
 /**
  * Properties del servicio
  */
 private ServicioConsultaProperties prop;
 private TransaccionDAL tranDAL;
 
 public ClienteConsulta2(ServicioConsultaProperties prop, TransaccionDAL tranDAL) 
 {
  this.prop = prop;
  this.tranDAL = tranDAL;
 }
 
 /**
  * Método principal para ejecutar el servicio de consulta
  * @param codigoTrxCargoConsulta Codigo trx de cargo que se generó a consultar
  * @return objeto ServicioResponseDTO
  */
 public ServicioResponseDTO ejecutarConsulta(String codigoTrxCargoConsulta) 
 {
  ServicioResponseDTO srv = new ServicioResponseDTO();
  
  String soapURL = prop.getProp().getUrlServicio();
  String soapAction = prop.getProp().getConsultaSoapAction();

  SoapSecurityDTO security = new SoapSecurityDTO();
  security.setWsseUrlSecuritySecext(prop.getProp().getUrlSecuSecExt());
  security.setWsseUrlSecuritySoapMessage(prop.getProp().getUrlSecuMessage());
  security.setWsseUrlSecurityUsernameToken(prop.getProp().getUrlSecuUsrNameToken());
  security.setWsseUrlSecurityUtility(prop.getProp().getUrlSecuUtility());
  security.setWsseUser(prop.getProp().getConsultaSecuUser());
  security.setWssePass(prop.getProp().getConsultaSecuUserPassword());
  
  String tipoAuth = prop.getProp().getConsultaAuthTipoAuth();  
  String usuarioServ = prop.getProp().getConsultaAuthUsuarioServicio();
  String passServ = prop.getProp().getConsultaAuthPasswordServicio();
  String encodingAuth = Base64.getEncoder().encodeToString((usuarioServ + ":" + passServ).getBytes());
  
  //---- Datos generación de los OSB del XML
  SoapXMLNsDTO xmlns = new SoapXMLNsDTO();

  xmlns.setNs(prop.getProp().getConsultaOsbNs());
  xmlns.setNs1(prop.getProp().getConsultaOsbNs1());
  xmlns.setMpi(prop.getProp().getConsultaOsbMpi());

     try 
     {
      //--- Parámetros iniciales de consulta
      
      // 1. Codigo trx de cargo que se generó a consultar
      
      //  2. Establecer la fecha/hora de consulta necesarios
      Date fechaConsulta = new Date();
      String fechaConT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(fechaConsulta);
      String fechaYMD = new SimpleDateFormat("yyyyMMdd").format(fechaConsulta);
      
      // 3. Establecer secuencia unica
      String secuenciaUnica = "0";
      int secUnic = tranDAL.getSequence();
      secuenciaUnica = secUnic+"";
      
      //-------- Datos a consultar del servicio ------------
      ServicioConsultaDatosDTO dataServ = new ServicioConsultaDatosDTO();
      dataServ.setConsUsuario(prop.getProp().getUsuarioConsumidor());
      dataServ.setConsPathServices("?");
      
      dataServ.setTranIdTransaccionNegocio(prop.getProp().getIdCanal() + fechaYMD + secuenciaUnica);
      dataServ.setTranInternalCode("BCOCOMPEN");
      dataServ.setTranFechaHora(fechaConT+"-03:00");
      dataServ.setTranIdCanal(prop.getProp().getIdCanal());
      dataServ.setTranNumeroSucursal(prop.getProp().getNumeroSucursal());
      dataServ.setTranPrioridad(1);
      dataServ.setBodyIdTransaccion(codigoTrxCargoConsulta);
      
      String requestXML = generaRequest(security, xmlns, dataServ);

      OkHttpClient client = new OkHttpClient().newBuilder().build();
      MediaType mediaType = MediaType.parse("text/xml;charset=UTF-8");
        
      @SuppressWarnings("deprecation")
      RequestBody body = RequestBody.create(mediaType, requestXML);
 
      Request request = new Request.Builder()
       .url(soapURL)
       .method("POST", body)
       .addHeader("Accept-Encoding", "gzip,deflate")
       .addHeader("Content-Type", "text/xml;charset=UTF-8")
       .addHeader("SOAPAction", soapAction)
       .addHeader("Authorization", tipoAuth+" "+encodingAuth)
       .addHeader("Content-Length", "2054")
       .addHeader("Host", prop.getProp().getDomninio())
       .addHeader("Connection", "Keep-Alive")
       
       .build();
   
      FuncionesGenerales.llamarClienteSOAP(client, request, srv);
      
     }
     catch (Exception e) 
     {
         LOGGER.error("Error al intentar servicio de consulta: "+e);
         srv = null;
     }
  
  return srv;
 }
 
 /**
  * Genera el soap xml del servicio
  * @param security objeto con parámetros seguridad para el servicio
  * @param xmlns objeto con las url de OSB del servicio
  * @param dataServ objeto con data para consulta general del servicio
  * @return XML de salida
  * @throws Exception error
  */
 public static String generaRequest(SoapSecurityDTO security, 
   SoapXMLNsDTO xmlns, ServicioConsultaDatosDTO dataServ)  
 {
  try {
  
        StringBuilder bf = new StringBuilder();

        bf.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' ");
        bf.append("xmlns:ns='"+xmlns.getNs()+"' ");
        bf.append("xmlns:ns1='"+xmlns.getNs1()+"' ");
        bf.append("xmlns:mpi='"+xmlns.getMpi()+"' >");
        
        bf.append(FuncionesGenerales.getWSSecuritySoap(security));
        
        bf.append(dataServ.getConsUsuario());
        bf.append("</ns1:usuario>");
        bf.append("<ns1:pathServices>");
        bf.append(dataServ.getConsPathServices());
        bf.append("</ns1:pathServices>");
        bf.append("</ns:datosConsumidor>");
        bf.append("<ns:datosTransaccion>");
        bf.append("<ns:idTransaccionNegocio>");
        bf.append(dataServ.getTranIdTransaccionNegocio());
        bf.append("</ns:idTransaccionNegocio>");
        bf.append("<ns:internalCode>");
        bf.append(dataServ.getTranInternalCode());
        bf.append("</ns:internalCode>");
        bf.append("<ns:fechaHora>");
        bf.append(dataServ.getTranFechaHora().toString());
        bf.append("</ns:fechaHora>");
        bf.append("<ns:idCanal>");
        bf.append(dataServ.getTranIdCanal());
        bf.append("</ns:idCanal>");
        bf.append("<ns:numeroSucursal>");
        bf.append(dataServ.getTranNumeroSucursal());
        bf.append("</ns:numeroSucursal>");
        bf.append("<ns:prioridad>");
        bf.append(dataServ.getTranPrioridad());
        bf.append("</ns:prioridad>");
        bf.append("</ns:datosTransaccion>");
        bf.append("</ns:headerRequest>");
        bf.append("</soapenv:Header>");

        //body
        bf.append("<soapenv:Body>");
        bf.append("<mpi:consultarTransaccionCoreRq>");       
        bf.append("<mpi:idTransaccion>");
        bf.append(dataServ.getBodyIdTransaccion());
        bf.append("</mpi:idTransaccion>");       
        bf.append("</mpi:consultarTransaccionCoreRq>");
        bf.append("</soapenv:Body>");
        bf.append("</soapenv:Envelope>");
        
        return bf.toString();
  }catch(Exception ex) {
   LOGGER.error("Error bodyXML:" + ex);
   return "";
  }
 }
}