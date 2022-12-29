package com.bch.api.rest.dto;


/******************************************************************************************
 * Nombre class: ServicioResponseDTO..................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class ServicioResponseDTO {
    private String codigoRespuesta;
    private String glosaRespuesta;
    private Object respuesta;
    
 public String getCodigoRespuesta() {
  return codigoRespuesta;
 }
 public void setCodigoRespuesta(String codigoRespuesta) {
  this.codigoRespuesta = codigoRespuesta;
 }
 public String getGlosaRespuesta() {
  return glosaRespuesta;
 }
 public void setGlosaRespuesta(String glosaRespuesta) {
  this.glosaRespuesta = glosaRespuesta;
 }
 public Object getRespuesta() {
  return respuesta;
 }
 public void setRespuesta(Object respuesta) {
  this.respuesta = respuesta;
 }
}
