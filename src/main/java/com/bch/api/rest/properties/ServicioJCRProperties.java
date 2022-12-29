package com.bch.api.rest.properties;

/**
 * Clase que guarda información de los properties para un servicio en particular (cargo, abono, etc)
 * @author 160k
 *
 */
public class ServicioJCRProperties {

 private ServicioJCRPropertiesDto prop;

 //---- Constructor
 public ServicioJCRProperties(ServicioJCRPropertiesDto prop) 
 {
  this.prop = prop;
 }

 public ServicioJCRPropertiesDto getProp() {
  return prop;
 }

 public void setProp(ServicioJCRPropertiesDto prop) {
  this.prop = prop;
 }
 
}