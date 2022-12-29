package com.bch.api.rest.services;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.bch.api.rest.dto.RequestResetCCADTO;
import com.bch.api.rest.dto.ResponseResetCCADTO;

/**
 * Clase encargada de llamar el Reset de la CCA
 * @author 160k
 *
 */
public class ResetCCA {

 private static final Logger LOGGER = Logger.getLogger(ResetCCA.class);
 
 private RequestResetCCADTO req;
 
 /**
  * Constructor
  * @param req
  */
 public ResetCCA(RequestResetCCADTO req) 
 {
  this.req = req;
 }
 
 /**
  * Método principal de ejecución del reset
  * @return objeto ResponseReset
  * @throws IOException
  * @throws InterruptedException
  */
 public ResponseResetCCADTO ejecutarReset() 
 {
  ResponseResetCCADTO objResp = null;
  
     RestTemplate restTemplate = new RestTemplate();
     
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", req.getTipoAuth()+" " + req.getToken());
        headers.add("Content-Type", "application/json");
        headers.add("Host", req.getUrl());
        headers.add("X-IBM-Client-Id", req.getClientId());
        headers.add("X-IBM-Client-Secret", req.getClientSecret());
        
        LOGGER.debug("Host: "+ req.getUrl());
        LOGGER.debug("Authorization: "+req.getTipoAuth()+" " + req.getToken());
        LOGGER.debug("ClientSecret: "+ req.getClientSecret());
        LOGGER.debug("ClientId: "+req.getClientId());
        
        LOGGER.debug("notificacion CCA Host" + req.getUrl());

        String params = "{ "
          + "\"codInstitucion\":\""+req.getCodInstitucion()+"\""
          + ",\"codTrx\":\""+req.getCodTrx()+"\""
          + ",\"indicadorReset\":\""+req.getIndicadorReset()+"\""
          + " }";
        
        HttpEntity<String> entity = new HttpEntity<String>(params, headers);
        
        LOGGER.debug("notificacion CCA request params" + params);
        LOGGER.debug("notificacion CCA request headers" + headers);

        ResponseEntity<ResponseResetCCADTO> response = restTemplate.exchange(req.getUrl(), HttpMethod.POST, 
          entity, ResponseResetCCADTO.class);
        
        LOGGER.debug("notificacion CCA Response" + String.valueOf(response));

        
        int statusCodeRs = response.getStatusCodeValue();
        
        if(statusCodeRs == 200) 
        {
         objResp = response.getBody();
         LOGGER.debug("objResp.codResp: "+objResp.getCodResp());
        } 
        else 
        {
         objResp = null;
         LOGGER.debug("statusCodeRs: "+statusCodeRs);
        }
        
  return objResp;
 }
 
}
