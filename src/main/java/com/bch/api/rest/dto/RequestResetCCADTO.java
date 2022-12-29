package com.bch.api.rest.dto;

import org.apache.log4j.Logger;

/******************************************************************************************
 * Nombre class: RequestResetCCADTO........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class RequestResetCCADTO {
 
 private static final Logger LOGGER = Logger.getLogger(RequestResetCCADTO.class);
 
 private String resetUrl;
 private String resetToken;
 private String resetClientId;
 private String resetClientSecret;
 private String resetCodInstitucion;
 private String resetCodTrx;
 private boolean resetIndicadorReset;
 private String resetTipoAuth;
 
 /******************************************************************************************
  * Nombre class: RequestResetCCADTO.....................................................*
  * Actions: traer topdos atributos........................................................*
  *****************************************************************************************/
 public RequestResetCCADTO() 
 {
  LOGGER.debug("inicializa RequestResetCCADTO");
 }
 
 public String getUrl() {
  return resetUrl;
 }

 public void setUrl(String url) {
  this.resetUrl = url;
 }

 public String getToken() {
  return resetToken;
 }

 public void setToken(String token) {
  this.resetToken = token;
 }

 public String getClientId() {
  return resetClientId;
 }

 public void setClientId(String clientId) {
  this.resetClientId = clientId;
 }

 public String getClientSecret() {
  return resetClientSecret;
 }

 public void setClientSecret(String clientSecret) {
  this.resetClientSecret = clientSecret;
 }
 

 public String getCodInstitucion() {
  return resetCodInstitucion;
 }

 public void setCodInstitucion(String codInstitucion) {
  this.resetCodInstitucion = codInstitucion;
 }

 public String getCodTrx() {
  return resetCodTrx;
 }

 public void setCodTrx(String codTrx) {
  this.resetCodTrx = codTrx;
 }

 public boolean getIndicadorReset() {
  return resetIndicadorReset;
 }

 public void setIndicadorReset(boolean indicadorReset) {
  this.resetIndicadorReset = indicadorReset;
 }
 
 public String getTipoAuth() {
  return resetTipoAuth;
 }

 public void setTipoAuth(String tipo) {
  this.resetTipoAuth = tipo;
 }
 
}
