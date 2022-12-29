package com.bch.api.rest.dto;
import com.bch.api.rest.entities.Sesion;

import java.util.List;

/******************************************************************************************
 * Nombre class: ResponseDTO..................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class ResponseSesion {
    private int statusCode;
    private String message;
    private List<Sesion> sesiones;
    
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
 public List<Sesion> getSesiones() {
  return sesiones;
 }
 public void setSesiones(List<Sesion> sesiones) {
  this.sesiones = sesiones;
 }
    
}
