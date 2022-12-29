package com.bch.api.rest.dto;
import com.bch.api.rest.entities.Emisor;
import java.util.List;

/******************************************************************************************
 * Nombre class: ResponseDTO..................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class ResponseEmisor {
    private int statusCode;
    private String message;
    private List<Emisor> emisores;
    
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
 public List<Emisor> getEmisores() {
  return emisores;
 }
 public void setEmisores(List<Emisor> emisores) {
  this.emisores = emisores;
 }
    
}
