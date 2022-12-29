package com.bch.api.rest.dto;

/**
 * Modelo utilizado para iniciar el llamado de un Token-CCA
 * @author 160k
 *
 */
public class RequestTokenCCADTO {
 private String urlToken;
 private String clientId;
 private String clientSecret;
 private String grandType;
 
 /******************************************************************************************
  * Nombre class: RequestTokenCCADTO..................................................*
  * Actions: traer topdos atributos........................................................*
  *****************************************************************************************/
 public RequestTokenCCADTO(String urlToken, String clientId, String clientSecret, String grandType) 
 {
  this.urlToken = urlToken;
  this.clientId = clientId;
  this.clientSecret = clientSecret;
  this.grandType = grandType;
  
 }

 public String getUrlToken() {
  return urlToken;
 }

 public void setUrlToken(String urlToken) {
  this.urlToken = urlToken;
 }

 public String getClientId() {
  return clientId;
 }

 public void setClientId(String clientId) {
  this.clientId = clientId;
 }

 public String getClientSecret() {
  return clientSecret;
 }

 public void setClientSecret(String clientSecret) {
  this.clientSecret = clientSecret;
 }

 public String getGrandType() {
  return grandType;
 }

 public void setGrandType(String grand) {
  this.grandType = grand;
 }

}
