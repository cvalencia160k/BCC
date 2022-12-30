package com.bch.api.rest.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.bch.api.rest.dto.ServicioResponseDTO;
import com.bch.api.rest.dto.SoapSecurityDTO;
import com.bch.api.rest.dto.SoapXMLNsDTO;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.properties.ServicioTokenProperties;
import com.bch.api.rest.services.client.soap.clientes.DigestUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Clase que almacena funciones generales o útiles
 * @author 160k
 *
 */

public class FuncionesGenerales {

 private static final Logger LOGGER = Logger.getLogger(FuncionesGenerales.class);



 
 private FuncionesGenerales() 
 {
  LOGGER.info("Inicio de FuncionesGenerales");
 }
 
 /**
  * Verifica si un string es null o vacío
  * @param str texto a probar
  * @return true/false si es null or empty
  */
 public static boolean isNullOrEmpty(String str) 
 {
	 if(str == null || str.length() == 0) 
	 {
		 return true;
	 }
	 else
	 {
		 return false;
	 }
 }
 
 /**
  * Cambio de fecha a formato Standard (In: dd-mm-yyyy -> out: yyyy-mm-dd)
  * @param fechaIn Fecha a formatear (dd-mm-yyyy)
  * @return fecha salida (yyyy-mm-dd)
  */
 public static String fecbaStandard(String fechaIn) 
 {
    String fechaOut = "";
    String[] arr = fechaIn.split("-");
    
    if(arr.length > 2) {
    fechaOut = arr[2]+"-"+arr[1]+"-"+arr[0];
    }
    LOGGER.debug("fechaIn: '"+fechaIn+"' | fechaOut: '"+fechaOut+"'");
    
    return fechaOut;
 }
 
 /*********************************************************************
  * Nombre funcion: parseDate.........................................*
  * Action:Validar el formato de una fecha YYYY-MM-DD HH:mm:ss........*
  * Inp:@value:String => texto a formatear............................* 
  * Out::Date => formato date.........................................*
  *********************************************************************/
 public static Date parseDate(String value) 
 {
        Date result = null;
        try {
            result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
        } 
        catch (ParseException ex) {
            result = null;
        }
        return result;
    }
 
 /**
  * Retorna si el día de hoy (ahora) tiene o no horas hábiles
  * @return
  */
 public static int horasHabiles() 
 {
	 int horasNoHabiles = 0;
	  
	   Calendar hoy = Calendar.getInstance();
	   int diaHoy = hoy.get(Calendar.DAY_OF_WEEK);
	   int horasDeldia = hoy.get(Calendar.HOUR_OF_DAY);
	   switch (diaHoy){
    case 1:
 	   horasNoHabiles = 24 + horasDeldia; 
        break;
    case 2: 
 	   horasNoHabiles = 48; 
        break;
    case 7:
 	   horasNoHabiles = horasDeldia; 
        break;
    default:
 	   horasNoHabiles = 0; 
 	   break;
	   }
	   
	return horasNoHabiles;
 }
 
 /************************************************************************************************************
  * Nombre funcion: esAlfaNumericoGuion......................................................................*
  * Action:Validar Validar si la cadena de entrada es alfanumeroco-guión (letras, numeros y guión unicamente)*
  * Inp:@codigo:String => texto a verificar..................................................................* 
  * Out::boolean => true/false dependiendo si es o no alfanumerico...........................................*
  ************************************************************************************************************/
 public static boolean esAlfaNumericoGuion(String codigo)
 {
  Pattern pat = Pattern.compile("^[a-zA-Z0-9-]*$");
     Matcher mat = pat.matcher(codigo);
     
     return mat.matches();
 }
 
 /*************************************************************************
  * Nombre funcion: esNumerico............................................*
  * Action:Validar si la cadena de entrada es numeroco....................*
  * Inp:@texto:String => texto a verificar................................* 
  * Out::boolean => true/false dependiendo si es o no alfanumerico........*
  *************************************************************************/
 public static boolean esNumerico(String texto)
 {
  Pattern pat = Pattern.compile("^[0-9]*$");
     Matcher mat = pat.matcher(texto);
     
     return mat.matches();
 }
 
 /*************************************************************************
  * Nombre funcion: Encriptar.............................................*
  * Action:encriptar texto con secretKey..................................*
  * Inp:@texto:String => texto a encriptar................................* 
  * Out:@base64EncryptedString:string => cadena encriptada................*
  *************************************************************************/
 public static String encriptar(String texto,String secretKy ) {

        String base64EncryptedString = "";
        String stringToEncrypted = texto + secretKy;
        try {

         base64EncryptedString = Base64.encodeBase64String(stringToEncrypted.getBytes("UTF8"));
            
        } catch (Exception exs) {
         LOGGER.error("Error ecncriptar:"+ exs);
        }
        return base64EncryptedString;

 }
 
 /*************************************************************************
  * Nombre funcion: desencriptar.............................................*
  * Action:desencriptar texto con secretKey..................................*
  * Inp:@texto:String => texto a encriptar................................* 
  * Out:@base64EncryptedString:string => cadena desencriptada................*
  *************************************************************************/
 public static String desencriptar(String textoEncriptado, String secretKy)  {

        String base64DesencryptedString = "";
        String base64DesencryptedStringFinal = "";
         
        try {
        
         String base64KeySecret= new String(Base64.decodeBase64(secretKy.getBytes("UTF8")));
         base64DesencryptedString = new String(Base64.decodeBase64(textoEncriptado.getBytes("UTF8")));
         
         
         base64DesencryptedStringFinal = base64DesencryptedString.substring(0, 
         base64DesencryptedString.length() - base64KeySecret.length());
          
         
         
        } catch (Exception ex) {
         LOGGER.error("Error dsencriptar:"+ ex);
        }
        
        return base64DesencryptedStringFinal;
 }
 
 
 /**
  * Obtiene el valor de un tag dentro del xml
  * @param tagName etiqueta del xml
  * @param element formato del documento xml
  * @return valor de la etiqueta. Retorna NULL cuando no encuentra valor
  */
 public static String getValueTag(String tagName, Element element)
 {
        String res = "";
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) 
        {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0)
            {
                res = subList.item(0).getNodeValue();
            }
        }

        if(res.isEmpty())
        {
            res = "No se encuestra etiqueta " + tagName;
        }

        return res;
    }
 
 /**
  * Obtiene el formato de fecha con la "T" en medio
  * @param fechaIn
  * @return fecha con formato T
  */
 public static String generarTFecha(String fechaIn) 
 {
  String str = fechaIn;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
  OffsetDateTime datetime = OffsetDateTime.parse(str, formatter);
  
  return datetime.toString();
 }
 
 /**
  * Genera el string del security del soap
  * @param security datos del security
  * @return string del security
  */
 public static StringBuilder getWSSecuritySoap(SoapSecurityDTO security) 
 {
   StringBuilder bf = new StringBuilder();
  
  String nonce = DigestUtils.createNonce();
        String timestamp = DigestUtils.createTimestamp();
  
  bf.append("<soapenv:Header>");
        bf.append("<wsse:Security xmlns:wsse='"+security.getWsseUrlSecuritySecext()+"'>");
        bf.append("<wsse:UsernameToken wsu:Id='UsernameToken-2' xmlns:wsu='"+
        security.getWsseUrlSecurityUtility()+"'>");
        bf.append("<wsse:Username>");
        bf.append(security.getWsseUser());
        bf.append("</wsse:Username>");
        bf.append("<wsse:Password Type='"+security.getWsseUrlSecurityUsernameToken()+"'>");
        bf.append(DigestUtils.createDigest(nonce, timestamp, security.getWssePass()));
        bf.append("</wsse:Password>");
        bf.append("<wsse:Nonce EncodingType='"+security.getWsseUrlSecuritySoapMessage()+"'>");
        bf.append(nonce);
        bf.append("</wsse:Nonce>");
        bf.append("<wsu:Created xmlns:wsu='"+security.getWsseUrlSecurityUtility()+"'>");
        bf.append(timestamp);
        bf.append("</wsu:Created>");
        bf.append("</wsse:UsernameToken>");
        bf.append("</wsse:Security>");
        bf.append("<ns:headerRequest>");
        bf.append("<ns:datosConsumidor>");
        bf.append("<ns1:usuario>");
        
        return bf;

 }

 /**
  * Llamar a un cliente SOAP
  * @param client datos del cliente
  * @param request datos del request
  * @param srv datos de la respuesta del servicio
  */
 public static void llamarClienteSOAP(
   OkHttpClient client, 
   Request request, 
   ServicioResponseDTO srv) 
 {
  StringBuilder output = new StringBuilder();
  
  Response response;
  try 
  {
   response = client.newCall(request).execute();
   
   try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.body().byteStream())))
   {
    String bufString;
       while((bufString = bufferedReader.readLine()) != null)
       {
        output.append(bufString);
       }
       
       bufferedReader.close();
   }
      
      String xml = output.toString();
      LOGGER.debug("response:"+output.toString());
      
      //----- Obtener los valores de salida
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder builder = factory.newDocumentBuilder(); 
            Document document = builder.parse(new InputSource(new StringReader(xml))); 
            Element formato = document.getDocumentElement();
            
            
      String codigoRespuesta = FuncionesGenerales.getValueTag("ns1:codigoRespuesta",formato);
            String glosaRespuesta = FuncionesGenerales.getValueTag("ns1:glosaRespuesta",formato);
            
            //--- Setear los datos de salida
            srv.setCodigoRespuesta(codigoRespuesta);
            srv.setGlosaRespuesta(glosaRespuesta);
            
            LOGGER.debug("codigoRespuesta: " + srv.getCodigoRespuesta());
            LOGGER.debug("glosaRespuesta: " + srv.getGlosaRespuesta());
   
  }
  catch (Exception e2) 
  {
   LOGGER.info(e2);
  }
  
 }
 
 /**
  * Intento de cierre de conexión y smtm del concepto indicado
  * @param conn objeto conexión
  * @param stmt objeto stmt
  * @param concepto concepto a cerrar (Ej. "transacción", "emisor", etc)
  */
 public static void cerrarConexionStmt(Connection conn, CallableStatement stmt, String concepto) 
 {
  if(conn != null) 
  {
   try 
   {
    conn.close();
   } 
   catch (Exception ex) 
   {
    LOGGER.error("Error al cerrar conexión "+concepto+" : "+ex);
   }
  }
  
  if(stmt != null) 
  {
   try 
   {
    stmt.close();
   } 
   catch (Exception ex) 
   {
    LOGGER.error("Error al cerrar el stmt "+concepto+": "+ex);
   }
  }
 }
 
 /**
  * Respetar el tamaño para un string
  * @param str cadena de texto a limitar
  * @param tam cantidad de caraceres a limitar
  * @return cadena de texto limitada a los caracteres definidos
  */
 public static String limitarTamanoString(String str, int tam) 
 {
	 if(str == null) {
		 return null;
	 }else {
		 return str.substring(0, (str.length()>tam)?tam-1:str.length()); 
	 }
	 
 }
 
 
 /**
  * Prevenir ataques XSS a string de entrada
  * @param strIn string vulnerado
  * @return string sanitizado
  */
 public static String cleanXSS(String strIn) 
 {
	 String result = strIn;
	 if(result != null)
	 {
	 result = result.replaceAll("(eval|EVAL|alert|ALERT|javascript|JAVASCRIPT|script|SCRIPT)", "");
	 result = result.replaceAll("(truncate|TRUNCATE|table|TABLE|delete|DELETE|"
	 		+ "select|SELECT|update|UPDATE|insert|INSERT|into|INTO|from|FROM)", "");
	 result = result.replaceAll("[`~!@#$%^&*()_+[\\]\\;\\'./{}|\\\"<>?]", "");	//--- caracteres permitidos " -,:"	 
	 }
	 
	 return result;
 }

 public static String generaRequest(Transaccion transB, SoapSecurityDTO security,
		 SoapXMLNsDTO xmlns, Emisor em, ServicioTokenProperties prop, String core, String origen ) 
 {
 try {
	 String strAbono = "Abono";
  LOGGER.debug("generaRequest!!");
  
  Date date = new Date();
  SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd");
  String stringDate= dateFor.format(date);
  
  String coreXML ="";
  
  if(core.equals(strAbono)) {
	  coreXML="depositarCuentaRq";
  }else {
	  coreXML="girarCuentaRq";
  }
  
  String fechaHoraTrx = transB.getFechaTransaccion().replace(" ", "T");
  
  String fechaHoy = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());

  SimpleDateFormat dateH = new SimpleDateFormat("aa");  
  String stringDateAMPM= dateH.format(date);

   StringBuilder bf = new StringBuilder();

   String usuarioConsumidor = prop.getProp().getServicioUsuarioConsumidorS(); 
   String idCanal = prop.getProp().getServicioIdCanalS();
   String numeroSucursal = prop.getProp().getServicioNumeroSucursalS();
   String prioridad = prop.getProp().getServicioPrioridad();
   String rutOperadora = prop.getProp().getServicioRutOperadora();
   String bancoOrigen = prop.getProp().getServicioBancoOrigen();
   String bancoDestino = prop.getProp().getServicioBancoDestino();
   String oficinaOrigenTx =  prop.getProp().getServicioOficinaOrigenTx();
   String canalOrigenTx = prop.getProp().getServicioCanalOrigenTx();
   String moneda =prop.getProp().getServicioMoneda();
   String codigoProductoFC = prop.getProp().getServicioCodigoProductoFC();
   String codigoTransaccionFC = prop.getProp().getCodigoTransaccionFC();
   String codigoExtendidoFC = prop.getProp().getCodigoExtendidoFC();
   String nombreCampo = prop.getProp().getServicioNombreCampo();
   
   String valorCampo = ""; 
   String strRegularizacion="Regularizacion";
   String[] valorCampoGlosas = prop.getProp().getServicioValorCampo().split("-");
   if(origen.equals(strRegularizacion)) {
	   valorCampo = valorCampoGlosas[1];
   }else {
	   valorCampo = valorCampoGlosas[0];
   }
    
   String nombreCampo2 = prop.getProp().getServicioNombreCampo2();   
   String nombreCampo3 = prop.getProp().getServicioNombreCampo3();	 
   
         //---- Parametros de entrada header
   
   
   
   

         String pathServices = "?";
         String idTransaccionNegocio = transB.getCodigoIdTrx();
         String internalCode = "1";

         String fechaHora = fechaHoraTrx;

         
         //----- Parámetros entrada body

         String cajero = "?";


         String rutCliente = em.getRutEmisor();

         String cuiOrigenTx = "?";
         String oficinaOrigenExternaTx = "1";

         String rutSupervisor = "0";
         String supervisor = "0";
         String fechaContable = stringDate;
         String fechaHoraCorrienteTx = fechaHoy;
         String horario = stringDateAMPM;
         String txId = transB.getCodigoIdTrx();
         String txIdParaReversar = "?";

         String cuenta = em.getNumeroCuentaCargo()+"";

         String monto = transB.getMontoTotalEnviadoCiclo();
         Long montoParse = Long.parseLong(monto);
         
         String origenCCA="CCA";
         
         monto = String.valueOf(montoParse);
      
         if(origen.equals(origenCCA)) {
        	 String montoIni = monto;
        	 monto = monto.substring(0,monto.length()-2);
        	String decimimales =  montoIni.substring(montoIni.length()-2,montoIni.length());
        	monto = monto + "." + decimimales;
         }else {
        	 monto = monto + ".00";	 
         }
         
         
         String numeroBoleta = "?";
         String llaveLibretaAhorro = "?";
         String saldoLibreta = "?";
         String flagRespuesta = "?";
         String valorCampo2 = String.valueOf(transB.getCiclo()); //--- numero de ciclo (1 o 2)
         String valorCampo3 = transB.getCodigoTransaccion(); //--- codigo trx
         
         bf.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                 + "xmlns:mpi=\""+xmlns.getMpi()+"\" "
                 + "xmlns:ns=\""+xmlns.getNs()+"\" "
                 + "xmlns:ns1=\""+xmlns.getNs1()+"\">");         
         
         bf.append(FuncionesGenerales.getWSSecuritySoap(security));
         
         bf.append(usuarioConsumidor);
         bf.append("</ns1:usuario>");
         bf.append("<ns1:pathServices>");
         bf.append(pathServices);
         bf.append("</ns1:pathServices>");
         bf.append("</ns:datosConsumidor>");
         bf.append("<ns:datosTransaccion>");
         bf.append("<ns:idTransaccionNegocio>");
         bf.append(idTransaccionNegocio);
         bf.append("</ns:idTransaccionNegocio>");
         bf.append("<ns:internalCode>");
         bf.append(internalCode);
         bf.append("</ns:internalCode>");
         bf.append("<ns:fechaHora>");
         bf.append(fechaHora);
         bf.append("</ns:fechaHora>");
         bf.append("<ns:idCanal>");
         bf.append(idCanal);
         bf.append("</ns:idCanal>");
         bf.append("<ns:numeroSucursal>");
         bf.append(numeroSucursal);
         bf.append("</ns:numeroSucursal>");
         bf.append("<ns:prioridad>");
         bf.append(prioridad);
         bf.append("</ns:prioridad>");
         bf.append("</ns:datosTransaccion>");
         bf.append("</ns:headerRequest>");
         bf.append("</soapenv:Header>");

         //body
         bf.append("<soapenv:Body>");
         bf.append("<mpi:" + coreXML +">");
         bf.append("<mpi:datosCabeceraNegocio>");
         bf.append("<mpi:rutOperadora>");
         bf.append(rutOperadora);
         bf.append("</mpi:rutOperadora>");
         bf.append("<mpi:cajero>");
         bf.append(cajero);
         bf.append("</mpi:cajero>");
         bf.append("<mpi:bancoDestino>");
         bf.append(bancoDestino);
         bf.append("</mpi:bancoDestino>");        
         bf.append("<mpi:bancoOrigen>");
         bf.append(bancoOrigen);
         bf.append("</mpi:bancoOrigen>");
         bf.append("<mpi:rutCliente>");
         bf.append(rutCliente);
         bf.append("</mpi:rutCliente>");
         bf.append("<mpi:oficinaOrigenTx>");
         bf.append(oficinaOrigenTx);
         bf.append("</mpi:oficinaOrigenTx>");
         bf.append("<mpi:cuiOrigenTx>");
         bf.append(cuiOrigenTx);
         bf.append("</mpi:cuiOrigenTx>");
         bf.append("<mpi:oficinaOrigenExternaTx>");
         bf.append(oficinaOrigenExternaTx);
         bf.append("</mpi:oficinaOrigenExternaTx>");
         bf.append("<mpi:canalOrigenTx>");
         bf.append(canalOrigenTx);
         bf.append("</mpi:canalOrigenTx>");
         bf.append("<mpi:rutSupervisor>");
         bf.append(rutSupervisor);
         bf.append("</mpi:rutSupervisor>");
         bf.append("<mpi:supervisor>");
         bf.append(supervisor);
         bf.append("</mpi:supervisor>");
         bf.append("<mpi:fechaContable>");
         bf.append(fechaContable);        
         bf.append("</mpi:fechaContable>");
         bf.append("<mpi:fechaHoraCorrienteTx>");
         bf.append(fechaHoraCorrienteTx); 
         bf.append("</mpi:fechaHoraCorrienteTx>");
         bf.append("<mpi:horario>");
         bf.append(horario.replace(".", "").replace(" ", "").toUpperCase());
         bf.append("</mpi:horario>");
         bf.append("<mpi:txId>");
         bf.append(txId);
         bf.append("</mpi:txId>");
         bf.append("<mpi:txIdParaReversar>");
         bf.append(txIdParaReversar);
         bf.append("</mpi:txIdParaReversar>");
         bf.append("</mpi:datosCabeceraNegocio>");
         bf.append("<mpi:datosNegocio>");
         bf.append("<mpi:cuenta>");
         bf.append(cuenta);
         bf.append("</mpi:cuenta>");
         bf.append("<mpi:moneda>");
         bf.append(moneda);
         bf.append("</mpi:moneda>");
         bf.append("<mpi:monto>");
         bf.append(monto);
         bf.append("</mpi:monto>");
         bf.append("<mpi:numeroBoleta>");
         bf.append(numeroBoleta);
         bf.append("</mpi:numeroBoleta>");
         bf.append("<mpi:codigoProductoFC>");
         bf.append(codigoProductoFC);
         bf.append("</mpi:codigoProductoFC>");
         bf.append("<mpi:codigoTransaccionFC>");
         bf.append(codigoTransaccionFC);
         bf.append("</mpi:codigoTransaccionFC>");
         bf.append("<mpi:codigoExtendidoFC>");
         bf.append(codigoExtendidoFC);
         bf.append("</mpi:codigoExtendidoFC>");
         bf.append("<mpi:llaveLibretaAhorro>");
         bf.append(llaveLibretaAhorro);
         bf.append("</mpi:llaveLibretaAhorro>");
         bf.append("<mpi:saldoLibreta>");
         bf.append(saldoLibreta);
         bf.append("</mpi:saldoLibreta>");
         bf.append("<mpi:flagRespuesta>");
         bf.append(flagRespuesta);
         bf.append("</mpi:flagRespuesta>");
         bf.append("</mpi:datosNegocio>");        
         bf.append("<mpi:listaDataCartola>");

         String dataCartolaOpen = "<mpi:dataCartola>";
         String dataCartolaClose = "</mpi:dataCartola>";
         String nombreCampoOpen = "<mpi:nombreCampo>";
         String nombreCampoClose = "</mpi:nombreCampo>";
         String valorCampoOpen = "<mpi:valorCampo>";
         String valorCampoClose = "</mpi:valorCampo>";
         
         bf.append(dataCartolaOpen);
         bf.append(nombreCampoOpen);
         bf.append(nombreCampo);
         bf.append(nombreCampoClose);
         bf.append(valorCampoOpen);
         bf.append(valorCampo);
         bf.append(valorCampoClose);
         bf.append(dataCartolaClose);
         
         bf.append(dataCartolaOpen);
         bf.append(nombreCampoOpen);
         bf.append(nombreCampo2);
         bf.append(nombreCampoClose);
         bf.append(valorCampoOpen);
         bf.append(valorCampo2);
         bf.append(valorCampoClose);
         bf.append(dataCartolaClose);

         bf.append(dataCartolaOpen);
         bf.append(nombreCampoOpen);
         bf.append(nombreCampo3);
         bf.append(nombreCampoClose);
         bf.append(valorCampoOpen);
         bf.append(valorCampo3);
         bf.append(valorCampoClose);
         bf.append(dataCartolaClose);
         
         bf.append("</mpi:listaDataCartola>");         
         bf.append("</mpi:" + coreXML +">");
         bf.append("</soapenv:Body>");
         bf.append("</soapenv:Envelope>");
        
         
         return bf.toString();
  }
  catch(Exception ex) {
   LOGGER.error("Error body xml:"+ex);
   return "";
  }
 }
}
