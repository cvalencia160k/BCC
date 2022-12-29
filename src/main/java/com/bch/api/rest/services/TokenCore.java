package com.bch.api.rest.services;

import java.io.IOException;
import java.util.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.bch.api.rest.conf.CustomSSLConfiguration;
import com.bch.api.rest.dto.ResponseTokenDTO;
import com.bch.api.rest.properties.ServicioTokenProperties;

/**
 * Clase principal encrgada de obtener el token de un servicio en core (cargo, abono)
 * @author 160k
 *
 */
public class TokenCore {

 private static final Logger LOGGER = Logger.getLogger(TokenCore.class);
 
 /**
  * Datos para generar el token de cargo
  */
 private ServicioTokenProperties prop;
 
 /**
  * Constructor
  * @param req
  */
 public TokenCore(ServicioTokenProperties prop) 
 {
  this.prop = prop;
 }
 
 /**
  * Método principal para obtener el token
  * @return
  * @throws IOException
  * @throws InterruptedException
  */
 public String obtenerToken(String nommbreServicio)  
 {
	 
  String elToken = "-1";
  LOGGER.debug("Entra a la generacion del token "+nommbreServicio);
  try 
        {
         CustomSSLConfiguration cust = new CustomSSLConfiguration();
         RestTemplate restTemplate = cust.restTemplate();
            
            String userBase64Credentials = new String(Base64.getEncoder().encode((prop.getProp().getTokenServicioUser()
              +":"+prop.getProp().getTokenServicioPass()).getBytes()));
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", prop.getProp().getTipoAuthServicio()+" " + userBase64Credentials);
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            
            headers.add("Connection", "Keep-Alive");
            headers.add("Accept-Encoding", "gzip,deflate");
            headers.add("Host", prop.getProp().getTokenServicioUrl());

            String params = "";
            params+="username="+prop.getProp().getTokenServicioParamUser();
            params+="&password="+prop.getProp().getTokenServicioParamPassUser();
            params+="&scope="+prop.getProp().getTokenServicioParamScope();
            params+="&grant_type="+prop.getProp().getTokenServicioParamGrandType();

            HttpEntity<String> entity = new HttpEntity<String>(params, headers);
            
            String response2 = String.valueOf(restTemplate.exchange(prop.getProp().getTokenServicioUrl(),
                    HttpMethod.POST, entity, ResponseTokenDTO.class));
            LOGGER.debug(" response "+nommbreServicio+":" + response2);
            
            ResponseEntity<ResponseTokenDTO> response = restTemplate.exchange(prop.getProp().getTokenServicioUrl(),
              HttpMethod.POST, entity, ResponseTokenDTO.class);
            LOGGER.debug(" token "+nommbreServicio+" Response :" + String.valueOf(response));

            int statusCodeRs = response.getStatusCodeValue();
            
            if(statusCodeRs == 200) 
            {
             ResponseTokenDTO objResp = response.getBody();
             LOGGER.debug(" token "+nommbreServicio+" OK");
             elToken = objResp.getAccess_token();
             
             LOGGER.debug("statusCodeRs: "+statusCodeRs+" | token "+nommbreServicio+" obtenido: "+elToken);
            } 
            else 
            {
             LOGGER.debug(" Error al generar token "+nommbreServicio);
             LOGGER.debug("statusCodeRs "+nommbreServicio+": "+statusCodeRs);
            }
            
        } 
        catch (Exception  e) 
        {
         LOGGER.error("Error al intentar obtener token "+nommbreServicio+": "+e);
        }
  
  return elToken;
 }
 
}