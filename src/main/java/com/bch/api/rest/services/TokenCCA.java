package com.bch.api.rest.services;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.bch.api.rest.dto.RequestTokenCCADTO;
import com.bch.api.rest.dto.ResponseTokenDTO;

/**
 * Clase encargada de obtener el token de la cca
 * @author 160k
 *
 */
public class TokenCCA {

 private static final Logger LOGGER = Logger.getLogger(TokenCCA.class);
 
 /**
  * Datos para generar el token
  */
 private RequestTokenCCADTO req;
 
 /**
  * Constructor
  * @param req
  */
 public TokenCCA(RequestTokenCCADTO req) 
 {
  this.req = req;
 }
 
 /**
  * Método para generar el token
  * @return
  * @throws IOException
  * @throws InterruptedException
  */
 public String obtenerToken()
 {
     String token = "-1";
  
     RestTemplate restTemplate = new RestTemplate();
     
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Host", req.getUrlToken());
        headers.add("X-IBM-Client-Id", req.getClientId());
        headers.add("X-IBM-Client-Secret", req.getClientSecret());
        
        String params = "grant_type="+req.getGrandType();
        
        HttpEntity<String> entity = new HttpEntity<String>(params, headers);
        
        ResponseEntity<ResponseTokenDTO> response = restTemplate.exchange(req.getUrlToken(), HttpMethod.POST,
          entity, ResponseTokenDTO.class);
        
        int statusCodeRs = response.getStatusCodeValue();
        
        if(statusCodeRs == 200) 
        {
         ResponseTokenDTO objResp = response.getBody();
         
         token = objResp.getAccess_token();
         
         LOGGER.debug("statusCodeRs: "+statusCodeRs+" | token cca obtenido: "+token);
        } 
        else 
        {
         LOGGER.debug("statusCodeRs: "+statusCodeRs);
        }
        
     return token;
 }
 
}
