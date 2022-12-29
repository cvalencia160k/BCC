package com.bch.api.rest.services.client.soap.clientes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.bch.api.rest.dal.InstitucionDAL;
import com.bch.api.rest.dto.SoapSecurityDTO;
import com.bch.api.rest.dto.SoapXMLNsDTO;
import com.bch.api.rest.dto.TransaccionDTO;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.entities.Institucion;
import com.bch.api.rest.properties.ServicioJCRProperties;
import com.bch.api.rest.utils.FuncionesGenerales;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ClientJCR {

	 private static final Logger LOGGER = Logger.getLogger(FuncionesGenerales.class);

	 public void enviaJCR(ServicioJCRProperties prop, TransaccionDTO transB, String tipoOperacion, Emisor em, String nomInstitucion)  
	 {
	  
	  //----- Obtener el token
	  
	  String soapURLJ = prop.getProp().getServicioUrlServicio();

	  SoapSecurityDTO securityJ = new SoapSecurityDTO();
	  securityJ.setWsseUrlSecuritySecext(prop.getProp().getServicioUrlSecuSecExt());
	  securityJ.setWsseUrlSecuritySoapMessage(prop.getProp().getServicioUrlSecuMessage());
	  securityJ.setWsseUrlSecurityUsernameToken(prop.getProp().getServicioUrlSecuUsrNameToken());
	  securityJ.setWsseUrlSecurityUtility(prop.getProp().getServicioUrlSecuUtility());
	  securityJ.setWsseUser(prop.getProp().getServicioSecuUser());
	  securityJ.setWssePass(prop.getProp().getServicioSecuUserPassword());
	  
	  
	  //---- Datos generación de los OSB del XML
	  SoapXMLNsDTO xmlnsJ = new SoapXMLNsDTO();

	  xmlnsJ.setNs(prop.getProp().getServicioOsbNs());
	  xmlnsJ.setNs1(prop.getProp().getServicioOsbNs1());
	  xmlnsJ.setMpi(prop.getProp().getServicioOsbMpi());
	        
	     try 
	     {
	      //-------- Datos a consultar del servicio ------------
	     
	      String requestXML = generaRequestJCR(xmlnsJ, securityJ, prop,transB,tipoOperacion,em,nomInstitucion);
	   
	      OkHttpClient client = new OkHttpClient().newBuilder().build();
	      MediaType mediaType = MediaType.parse("application/soap+xml;charset=UTF-8");
	        
	      @SuppressWarnings("deprecation")
	      RequestBody body = RequestBody.create(mediaType, requestXML);
	      LOGGER.debug("body: "+requestXML);
	      LOGGER.debug("soapURL: '"+soapURLJ+"'");

	      
	      
	      
	   Request request = new Request.Builder()
	       .url(soapURLJ)
	       .method("POST", body)
	       .addHeader("Accept-Encoding", "gzip,deflate")
	       .addHeader("Content-Type", "application/soap+xml;charset=UTF-8;action='" +
	       prop.getProp().getServicioSoapAction() + "'")      
	       .addHeader("Content-Length", "3862")
	       .addHeader("Host",  prop.getProp().getServicioDominio())
	       .addHeader("Connection", "Keep-Alive")
	       .addHeader("Accept:", "*/*")
	       .build();

	     llamarClienteSOAPJCR(client, request);
	  }
	     catch (Exception e) 
	     {
	      LOGGER.debug("Error ex: "+e.getMessage());
	      LOGGER.error("Error stack: "+e);
	  
	     }
	  
	  
	 }
	
	 public static String generaRequestJCR(SoapXMLNsDTO xmlns, SoapSecurityDTO security, 
			 ServicioJCRProperties prop,TransaccionDTO transB, String tipoOperacion, Emisor em, String nomInstitucion) 
	 {
		 try {
			 
			 LOGGER.debug("Genera Body XML Request JCR!!");
	  
			 Date date = new Date();
			 SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd");
			 String stringDate= dateFor.format(date);
	  
			 SimpleDateFormat dateForJ = new SimpleDateFormat("yyyy-MM-dd");
			 String stringDateJ = dateForJ.format(date);
	  
			 String fechaHoy = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());

			 SimpleDateFormat dateH = new SimpleDateFormat("aa");  
			 String stringDateAMPM= dateH.format(date);

			 StringBuilder bfJ = new StringBuilder();

			 String nonce = DigestUtils.createNonce();
		     String timestamp = DigestUtils.createTimestamp();
		  
			 String usuario =prop.getProp().getServicioUsuarioConsumidor(); 
			 String pathServices =prop.getProp().getServicioIdCanal(); 
			 String idCanal = prop.getProp().getServicioIdCanal();;
			 String numeroSucursal = prop.getProp().getServicioNumeroSucursal();
	   
			 String idTransaccionNegocio = transB.getCodigoIdTransaccion();
			 String txId = transB.getCodTrx();
	
			 String ipOrigen =prop.getProp().getSsrvicioIpOrigen();
			 String estacion =prop.getProp().getServicioEstacion();
		
			 String canal = prop.getProp().getServicioIdCanal();
			 String appOrgien = prop.getProp().getServicioAppOrgien();
			 String funcionalidad = prop.getProp().getServicioFuncionalidad();   
			 String servicioRest = prop.getProp().getServicioRest();   
			 String oficinaOrigen = prop.getProp().getServicioOficinaOrigen();	 	   
			 String fechaEjecucionInicial = fechaHoy;
			 String fechaEjecucionFinal = fechaHoy;
			 String fechaContable = stringDate;
	         
	         String rutCliente = em.getRutEmisor();
	         String rutEjecutivo = prop.getProp().getServicioRutEjecutivo();
	         String tipoProductoOrigen = prop.getProp().getServicioTipoProductoOrigen();
	         String productoOrigen = prop.getProp().getServicioProductoOrigen();
	         String tipoProductoDestino = prop.getProp().getServicioTipoProductoDestino();

	         String productoDestino = prop.getProp().getServicioProductoDestino();

	         String monto = transB.getMontoTotalEnviadoCiclo();
	         String tipoMoneda = prop.getProp().getServicioMoneda();
	         String estado = prop.getProp().getServicioEstado();
	         String estadoDescripcion = prop.getProp().getServicioEstadoDescripcion();
	         String tipoRechazo = prop.getProp().getServicioTipoRechazo();	
	         String codigoRechazo = prop.getProp().getServicioCodigoRechazo();
	         String descripcionRechazo = prop.getProp().getServicioDescripcionRechaz();
	         String modo = prop.getProp().getServicioModo();
	         String txRelacionada = prop.getProp().getServicioTxRelacionada();
	         String bancoOrigen = prop.getProp().getServicioBancoOrigen();
	         String numProducto = prop.getProp().getServicioNumProducto();
	         
	         
	         String datoAdicional = String.valueOf(em.getNumeroCuentaCargo());
	         
	         String codMoneda = prop.getProp().getServicioCodMoneda();
	         String tipoCambio = prop.getProp().getServicioCodigoTipoCambio();
	         String nInstitucion = nomInstitucion;
	      
	         
	         if(nInstitucion.length()>11) {
	        	 nInstitucion = nInstitucion.substring(0,10);
	         }
	         nInstitucion = nInstitucion.toUpperCase();
	         String tipoProducto =(em.getCodigoEmisor()==1?prop.getProp().getServicioTipoProductoInt()
	        		 :prop.getProp().getServicioTipoProducto());
	         String toperacion = (tipoOperacion=="BCOCOMPENCAR"?"CARGO_" +tipoProducto :"ABONO_" +tipoProducto );
	         
	         String medioPagoStr ="BCO COMP " + nInstitucion;
	         String critterioTrx =prop.getProp().getSevicioCriterioTrx();
	         
	         
	         String jsonStr = "{'cabecera':" +
	         "{" +
	         "'appOrigen':'" + idCanal + "'," + 
	         "'funcionalidad':'"+ funcionalidad + "'," +
	         "'operacion\':'" + tipoOperacion + "'," +
	         "'cioTrxId':'"+txId +"'," +
	         "'bancoOrigen':'" + bancoOrigen + "'," +
	         "'moneda':'" + tipoMoneda+ "',"+
	         "'tipoRechazo':'" + tipoRechazo+ "',"+
	         "'codigoRechazo':'" + codigoRechazo+ "',"+
	         "'descripcionRechazo':'" + descripcionRechazo+ "'"+
	         "}," +
	         "'datosNegocio':" +
	         "{" +
	         "'fechaValor':'" + stringDateJ +"'," +
	         "'horario':'"+ stringDateAMPM.replace(".", "") + "'," +
	         "'tipoProducto':'"+ tipoProducto +"',"+
	         "'numProducto':'"+ numProducto +"'," +
	         "'toperacion':'"+ toperacion + "',"+
	         "'fechaVencimiento':'" + stringDateJ + "'," +
	         "'datoAdicional':'" + datoAdicional + "',"+
	         "'codMoneda':'" + codMoneda + "',"+
	         "'criterio_trx':'" + critterioTrx + "',"+
	         "'mediosPago':[{" +
	         "'moneda':'" + tipoMoneda+ "',"+
	         "'monto':'" + monto + "'," + 
	         "'montoPesos':'" + monto + "',"+
	         "'tipoCambio':'" + tipoCambio + "',"+
	         "'tokenProducto':'" + tipoProducto + "',"+
	         "'medioPago':'"+ medioPagoStr + "'}]}}";
	         
	     
	         bfJ.append("<soap:Envelope xmlns:soap='http://www.w3.org/2003/05/soap-envelope' "
	        		 + "xmlns:ns='"+xmlns.getNs()+"' "
	                 + "xmlns:ns1='"+xmlns.getNs1()+"' "
	        		 + "xmlns:mpi='"+xmlns.getMpi()+"'>");
  
	   
	         bfJ.append("<soap:Header>");
	         bfJ.append("<wsse:Security soap:mustUnderstand='true' xmlns:wsse='"+security.getWsseUrlSecuritySecext()+"' "
	         		+ "xmlns:wsu='"+ security.getWsseUrlSecurityUtility()+ "'>");
	         bfJ.append("<wsse:UsernameToken wsu:Id='UsernameToken-8117079E05C73649D516638604663061' >");
	         bfJ.append("<wsse:Username>");
	         bfJ.append(security.getWsseUser());
	         bfJ.append("</wsse:Username>");
	         bfJ.append("<wsse:Password Type='"+security.getWsseUrlSecurityUsernameToken()+"'>");
	         bfJ.append(DigestUtils.createDigest(nonce, timestamp, security.getWssePass()));
	         bfJ.append("</wsse:Password>");
	         bfJ.append("<wsse:Nonce EncodingType='"+security.getWsseUrlSecuritySoapMessage()+"'>");
	         bfJ.append(nonce);
	         bfJ.append("</wsse:Nonce>");
	         bfJ.append("<wsu:Created xmlns:wsu='"+security.getWsseUrlSecurityUtility()+"'>");
	         bfJ.append(timestamp);
	         bfJ.append("</wsu:Created>");
	         bfJ.append("</wsse:UsernameToken>");
	         bfJ.append("</wsse:Security>");
	         bfJ.append("<ns:headerRequest>");
	         bfJ.append("<ns:datosConsumidor>");
	         bfJ.append("<ns1:usuario>");
	         
	         bfJ.append(usuario);
	         bfJ.append("</ns1:usuario>");
	         bfJ.append("<ns1:pathServices>");
	         bfJ.append(pathServices);
	         bfJ.append("</ns1:pathServices>");
	         bfJ.append("</ns:datosConsumidor>");
	         bfJ.append("<ns:datosTransaccion>");
	         bfJ.append("<ns:idTransaccionNegocio>");
	         bfJ.append(idTransaccionNegocio);
	         bfJ.append("</ns:idTransaccionNegocio>");
	         bfJ.append("<ns:fechaHora>");
	         bfJ.append(fechaEjecucionInicial);
	         bfJ.append("</ns:fechaHora>");
	         bfJ.append("<ns:idCanal>");
	         bfJ.append(idCanal);
	         bfJ.append("</ns:idCanal>");
	         bfJ.append("<ns:numeroSucursal>");
	         bfJ.append(numeroSucursal);
	         bfJ.append("</ns:numeroSucursal>");
	         bfJ.append("</ns:datosTransaccion>");
	         bfJ.append("</ns:headerRequest>");
	         bfJ.append("</soap:Header>");

	         //body
	         bfJ.append("<soap:Body>");
	         bfJ.append("<mpi:ingresarJournalCanalRemotoRq>");
	         bfJ.append("<mpi:txId>");
	         bfJ.append(idTransaccionNegocio);
	         bfJ.append("</mpi:txId>");
	         bfJ.append("<mpi:tipoOperacion>");
	         bfJ.append(tipoOperacion);
	         bfJ.append("</mpi:tipoOperacion>");
	         bfJ.append("<mpi:ipOrigen>");
	         bfJ.append(ipOrigen);
	         bfJ.append("</mpi:ipOrigen>");        
	         bfJ.append("<mpi:estacion>");
	         bfJ.append(estacion);
	         bfJ.append("</mpi:estacion>");
	         bfJ.append("<mpi:usuario>");
	         bfJ.append(usuario);
	         bfJ.append("</mpi:usuario>");
	         bfJ.append("<mpi:canal>");
	         bfJ.append(canal);
	         bfJ.append("</mpi:canal>");
	         bfJ.append("<mpi:appOrgien>");
	         bfJ.append(appOrgien);
	         bfJ.append("</mpi:appOrgien>");
	         bfJ.append("<mpi:funcionalidad>");
	         bfJ.append(funcionalidad);
	         bfJ.append("</mpi:funcionalidad>");
	         bfJ.append("<mpi:servicioRest>");
	         bfJ.append(servicioRest);
	         bfJ.append("</mpi:servicioRest>");
	         bfJ.append("<mpi:oficinaOrigen>");
	         bfJ.append(oficinaOrigen);
	         bfJ.append("</mpi:oficinaOrigen>");
	         bfJ.append("<mpi:fechaEjecucionInicial>");
	         bfJ.append(fechaEjecucionInicial);
	         bfJ.append("</mpi:fechaEjecucionInicial>");
	         bfJ.append("<mpi:fechaEjecucionFinal>");
	         bfJ.append(fechaEjecucionFinal);        
	         bfJ.append("</mpi:fechaEjecucionFinal>");
	         bfJ.append("<mpi:fechaContable>");
	         bfJ.append(fechaContable); 
	         bfJ.append("</mpi:fechaContable>");
	         bfJ.append("<mpi:rutEjecutivo>");
	         bfJ.append(rutEjecutivo);
	         bfJ.append("</mpi:rutEjecutivo>");
	         bfJ.append("<mpi:rutCliente>");
	         bfJ.append(rutCliente);
	         bfJ.append("</mpi:rutCliente>");
	         bfJ.append("<mpi:tipoProductoOrigen>");
	         bfJ.append(tipoProductoOrigen);
	         bfJ.append("</mpi:tipoProductoOrigen>");
	         bfJ.append("<mpi:productoOrigen>");
	         bfJ.append(productoOrigen);
	         bfJ.append("</mpi:productoOrigen>");
	         bfJ.append("<mpi:tipoProductoDestino>");
	         bfJ.append(tipoProductoDestino);
	         bfJ.append("</mpi:tipoProductoDestino>");
	         bfJ.append("<mpi:productoDestino>");
	         bfJ.append(productoDestino);
	         bfJ.append("</mpi:productoDestino>");
	         bfJ.append("<mpi:monto>");
	         bfJ.append(monto);
	         bfJ.append("</mpi:monto>");
	         bfJ.append("<mpi:moneda>");
	         bfJ.append(tipoMoneda);
	         bfJ.append("</mpi:moneda>");
	         bfJ.append("<mpi:estado>");
	         bfJ.append(estado);
	         bfJ.append("</mpi:estado>");
	         bfJ.append("<mpi:estadoDescripcion>");
	         bfJ.append(estadoDescripcion);
	         bfJ.append("</mpi:estadoDescripcion>");
	         bfJ.append("<mpi:tipoRechazo>");
	         bfJ.append(tipoRechazo);
	         bfJ.append("</mpi:tipoRechazo>");
	         bfJ.append("<mpi:codigoRechazo>");
	         bfJ.append(codigoRechazo);
	         bfJ.append("</mpi:codigoRechazo>");
	         bfJ.append("<mpi:descripcionRechazo>");
	         bfJ.append(descripcionRechazo);
	         bfJ.append("</mpi:descripcionRechazo>");
	         bfJ.append("<mpi:modo>");
	         bfJ.append(modo);
	         bfJ.append("</mpi:modo>");
	         bfJ.append("<mpi:txRelacionada>");
	         bfJ.append(txRelacionada);
	         bfJ.append("</mpi:txRelacionada>");
	         bfJ.append("<mpi:json>");
	         bfJ.append(jsonStr);
	         bfJ.append("</mpi:json>");
	         bfJ.append("</mpi:ingresarJournalCanalRemotoRq>");        
	         bfJ.append("</soap:Body>");
	         bfJ.append("</soap:Envelope>");
	        
	         
	         return bfJ.toString();
	  }
	  catch(Exception ex) {
	   LOGGER.error("Error body xml:"+ex);
	   return "";
	  }
	 }
	 	 
	 public static void llamarClienteSOAPJCR(
			   OkHttpClient client, 
			   Request request) 
			 {
			  StringBuilder outputJ = new StringBuilder();
			  
			  Response responseJ;
			  try 
			  {
			   responseJ = client.newCall(request).execute();

			   try(BufferedReader bufferedReaderJ = new BufferedReader(new InputStreamReader(responseJ.body().byteStream())))
			   {
			    String bufStringJ;
			       while((bufStringJ = bufferedReaderJ.readLine()) != null)
			       {
			        outputJ.append(bufStringJ);
			       }
			       
			       bufferedReaderJ.close();
			   }
			      
			      String xmlJc = outputJ.toString();
			      LOGGER.debug("response:"+outputJ.toString());
			      
			      //----- Obtener los valores de salida
			      DocumentBuilderFactory factoryJ = DocumentBuilderFactory.newInstance(); 
			            DocumentBuilder builderJ = factoryJ.newDocumentBuilder(); 
			            Document documentJ = builderJ.parse(new InputSource(new StringReader(xmlJc))); 
			            Element formatoJ = documentJ.getDocumentElement();
			            			            
			            String codigoRespuestaJ = FuncionesGenerales.getValueTag("ns2:codigoRespuesta",formatoJ);
			            String glosaRespuestaJ = FuncionesGenerales.getValueTag("ns2:glosaRespuesta",formatoJ);
			            
			            LOGGER.debug("codigoRespuesta: " + codigoRespuestaJ);
			            LOGGER.debug("glosaRespuesta: " + glosaRespuestaJ);
			   
			  }
			  catch (Exception e2) 
			  {
			   LOGGER.info(e2);
			  }
			  
			 }
	 
}
