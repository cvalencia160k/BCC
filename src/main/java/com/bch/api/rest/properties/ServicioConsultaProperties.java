package com.bch.api.rest.properties;

import org.apache.log4j.Logger;

/**
 * Clase que guarda información de los properties del servicio de consulta
 * @author 160k
 *
 */
public class ServicioConsultaProperties {

 private static final Logger LOGGER = Logger.getLogger(ServicioConsultaProperties.class);

 private ServicioConsultaPropertiesDto prop;
 
 //---- Constructores
 public ServicioConsultaProperties() {
  LOGGER.debug("incio ServicioConsultaProperties");
 }
 
 public ServicioConsultaProperties(ServicioConsultaPropertiesDto prop) 
 {
  this.prop = prop;
 }

 public ServicioConsultaPropertiesDto getProp() {
  return prop;
 }

 public void setProp(ServicioConsultaPropertiesDto prop) {
  this.prop = prop;
 }
}