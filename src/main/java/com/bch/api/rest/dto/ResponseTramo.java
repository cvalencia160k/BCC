package com.bch.api.rest.dto;
import com.bch.api.rest.entities.Tramo;

import java.util.List;

/******************************************************************************************
 * Nombre class: ResponseDTO..................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class ResponseTramo {
    private int statusCode;
    private String message;
    private List<Tramo> tramos;
    
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
 public List<Tramo> getTramos() {
  return tramos;
 }
 public void setTramos(List<Tramo> tramos) {
  this.tramos = tramos;
 }
    
}
