package com.bch.api.rest.dto;

/******************************************************************************************
 * Nombre class: ResponseDTO..................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class ResponseDTO {
    private int statusCode;
    private String message;
    private String codigoOperacion;
    
 public int getStatusCode() {
  return statusCode;
 }
 public void setStatusCode(int statusCode) {
  this.statusCode = statusCode;
 }
 public String getMessage() {
  return message;
 }
 public void setMessage(String message) {
  this.message = message;
 }
 public String getCodigoOperacion() {
  return codigoOperacion;
 }
 public void setCodigoOperacion(String codigoOperacion) {
  this.codigoOperacion = codigoOperacion;
 }
    
}
