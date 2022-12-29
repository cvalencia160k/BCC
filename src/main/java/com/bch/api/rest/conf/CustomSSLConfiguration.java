package com.bch.api.rest.conf;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Clase encargada de obtener los certificados para la ejecución de la API-REST
 * @author 160k
 *
 */
@Configuration
@Component
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class CustomSSLConfiguration {
 private static final Logger LOGGER = Logger.getLogger(CustomSSLConfiguration.class);

 /*****************************************
   * Nombre funcion: CustomSSLConfiguration*
   * Action:generar config SSL.............*
   * Inp:@keyStoreDir:String...............*
   * @keyPassword:String...................*
   * Out:void..............................*
   *****************************************/
  public CustomSSLConfiguration() 
  {
    LOGGER.debug("Constructor CustomSSLConfiguration()");
  } 
  

 /*********************************
  * Nombre funcion: restTemplate..*
  * Action:template SSL...........*
  * Inp:..........................*
  * Out:..........................*
  *********************************/
 @Bean
 public RestTemplate restTemplate(){
  try{
  //------------------- Sin KeyStore y Pass ------------------------------
  SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null,
    new TrustSelfSignedStrategy()).build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

  
  }catch(KeyManagementException | NoSuchAlgorithmException | KeyStoreException  e){
     LOGGER.error("Error conexion Base Datos: Error:" + e.getMessage());
     LOGGER.error("Stack: Error:" + e);
  }
  return new RestTemplate();
 }
 
}
