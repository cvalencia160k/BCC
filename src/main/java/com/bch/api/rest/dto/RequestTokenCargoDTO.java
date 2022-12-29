package com.bch.api.rest.dto;

import org.apache.log4j.Logger;

/**
 * Modelo utilizado para iniciar el llamado de un Token-Cargo
 * @author 160k
 *
 */
public class RequestTokenCargoDTO {
 
 private static final Logger LOGGER = Logger.getLogger(RequestTokenCargoDTO.class);
 private String keyStore;
 private String keyPassword;
 private String wsUrl;
 private String wsUser;
 private String wsPass;
 private String tipoAuth;
 private String paramUser;
 private String paramPassUser;
 private String paramScope;
 private String paramGrandType;
 
 /******************************************************************************************
  * Nombre class: RequestTokenCargoDTO..................................................*
  * Actions: traer topdos atributos........................................................*
  *****************************************************************************************/
 public RequestTokenCargoDTO() 
 {
  LOGGER.debug("inicializa RequestTokenCargoDTO");
 }
  
 public String getKeyStore() {
  return keyStore;
 }
 public void setKeyStore(String keyStore) {
  this.keyStore = keyStore;
 }
 public String getKeyPassword() {
  return keyPassword;
 }
 public void setKeyPassword(String keyPassword) {
  this.keyPassword = keyPassword;
 }
 public String getWsUrl() {
  return wsUrl;
 }
 public void setWsUrl(String wsUrl) {
  this.wsUrl = wsUrl;
 }
 public String getWsUser() {
  return wsUser;
 }
 public void setWsUser(String wsUser) {
  this.wsUser = wsUser;
 }
 public String getWsPass() {
  return wsPass;
 }
 public void setWsPass(String wsPass) {
  this.wsPass = wsPass;
 }
 public String getTipoAuth() {
  return tipoAuth;
 }
 public void setTipoAuth(String tipo) {
  this.tipoAuth = tipo;
 }

 public String getParamUser() {
  return paramUser;
 }

 public void setParamUser(String paramUser) {
  this.paramUser = paramUser;
 }

 public String getParamPassUser() {
  return paramPassUser;
 }

 public void setParamPassUser(String paramPassUser) {
  this.paramPassUser = paramPassUser;
 }

 public String getParamScope() {
  return paramScope;
 }

 public void setParamScope(String paramScope) {
  this.paramScope = paramScope;
 }

 public String getParamGrandType() {
  return paramGrandType;
 }

 public void setParamGrandType(String paramGrandType) {
  this.paramGrandType = paramGrandType;
 }

}
