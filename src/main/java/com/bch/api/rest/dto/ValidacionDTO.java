package com.bch.api.rest.dto;

/**
 * Utilizado en la validación de las transaccionex
 * @author 160k
 *
 */
public class ValidacionDTO {
    private String mensajeValidacion;
    private int codigoOperacionValidacion;
    
 public String getMensajeValidacion() {
  return mensajeValidacion;
 }
 public void setMensajeValidacion(String mensajeValidacion) {
  this.mensajeValidacion = mensajeValidacion;
 }
 public int getCodigoOperacionValidacion() {
  return codigoOperacionValidacion;
 }
 public void setCodigoOperacionValidacion(int codigoOperacionValidacion) {
  this.codigoOperacionValidacion = codigoOperacionValidacion;
 }
    
}
