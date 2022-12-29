package com.bch.api.rest.properties;

import org.apache.log4j.Logger;

/**
 * Clase que guarda información de los properties del servicio de notificación a cca
 * @author 160k
 *
 */
public class ServicioNotificacionProperties {

 private static final Logger LOGGER = Logger.getLogger(ServicioNotificacionProperties.class);

 private ServicioNotificacionPropertiesDto prop;
 
 //---- Constructores
 public ServicioNotificacionProperties() {
  LOGGER.debug("inciio ServicioNotificacionProperties");
 }
 
 public ServicioNotificacionProperties(ServicioNotificacionPropertiesDto prop) 
 {
  this.prop = prop;
 }

 public ServicioNotificacionPropertiesDto getProp() {
  return prop;
 }

 public void setProp(ServicioNotificacionPropertiesDto prop) {
  this.prop = prop;
 } 
}