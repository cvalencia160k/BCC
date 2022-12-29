package com.bch.api.rest.properties;

/**
 * Clase que guarda información de los properties para un servicio en particular (cargo, abono, etc)
 * @author 160k
 *
 */
public class ServicioTokenProperties {

 private ServicioTokenPropertiesDto prop;

 //---- Constructor
 public ServicioTokenProperties(ServicioTokenPropertiesDto prop) 
 {
  this.prop = prop;
 }

 public ServicioTokenPropertiesDto getProp() {
  return prop;
 }

 public void setProp(ServicioTokenPropertiesDto prop) {
  this.prop = prop;
 }
 
}