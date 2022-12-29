package com.bch.api.rest.dto;

/******************************************************************************************
 * Nombre class: ResponseTokenDTO..................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class ResponseTokenDTO {
 private String access_token;
 private String token_type;
 private int expires_in;
 private String scope;
 private String jti;
 
 public String getAccess_token() {
  return access_token;
 }
 public void setAccess_token(String accessToken) {
  this.access_token = accessToken;
 }
 public String getToken_type() {
  return token_type;
 }
 public void setToken_type(String token_type) {
  this.token_type = token_type;
 }
 public int getExpiresIn() {
  return expires_in;
 }
 public void setExpiresIn(int expires_in) {
  this.expires_in = expires_in;
 }
 public String getScope() {
  return scope;
 }
 public void setScope(String scope) {
  this.scope = scope;
 }
 public String getJti() {
  return jti;
 }
 public void setJti(String jti) {
  this.jti = jti;
 }
 
}
