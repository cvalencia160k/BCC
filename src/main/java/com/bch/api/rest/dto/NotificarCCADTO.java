package com.bch.api.rest.dto;

import org.apache.log4j.Logger;

/**
 * Modelo de datos utilizado en la Notificación a la CCA
 * @author 160k
 *
 */
public class NotificarCCADTO {
 private String notiUrlToken;
 private String notiUrlReset;
 private String notiToken;
 private String notiClientId;
 private String notiClientSecret;

 private String notiCodInstitucion;
 private String notiCodTrx;
 private boolean notiIndicadorReset;
 
 private String notiTipoAuth;
 private String notiGrandType;
 
 private int notiNumeroReintentos;
 private static final Logger LOGGER = Logger.getLogger(NotificarCCADTO.class);

 
 /*************************************
  * Nombre class: NotificarCCADTO.....*
  * Actions: traer topdos atributos...*
  ************************************/ 
 public NotificarCCADTO(){
  LOGGER.debug("inicializa NotificarCCADTO");
 }

 public String getUrlToken() {
  return notiUrlToken;
 }

 public void setUrlToken(String url) {
  this.notiUrlToken = url;
 }

 public String getUrlReset() {
  return notiUrlReset;
 }

 public void setUrlReset(String url) {
  this.notiUrlReset = url;
 }

 public String getToken() {
  return notiToken;
 }

 public void setToken(String token) {
  this.notiToken = token;
 }

 public String getGrandType() {
  return notiGrandType;
 }

 public void setGrandType(String grand) {
  this.notiGrandType = grand;
 }

 public String getClientId() {
  return notiClientId;
 }

 public void setClientId(String clientId) {
  this.notiClientId = clientId;
 }

 public String getClientSecret() {
  return notiClientSecret;
 }

 public void setClientSecret(String clientSecret) {
  this.notiClientSecret = clientSecret;
 }
 

 public String getCodInstitucion() {
  return notiCodInstitucion;
 }

 public void setCodInstitucion(String codInstitucion) {
  this.notiCodInstitucion = codInstitucion;
 }

 public String getCodTrx() {
  return notiCodTrx;
 }

 public void setCodTrx(String codTrx) {
  this.notiCodTrx = codTrx;
 }

 public boolean getIndicadorReset() {
  return notiIndicadorReset;
 }

 public void setIndicadorReset(boolean indicadorReset) {
  this.notiIndicadorReset = indicadorReset;
 }
 
 public String getTipoAuth() {
  return notiTipoAuth;
 }

 public void setTipoAuth(String tipo) {
  this.notiTipoAuth = tipo;
 }

 public int getNumeroReintentos() {
  return notiNumeroReintentos;
 }

 public void setNumeroReintentos(int numero) {
  this.notiNumeroReintentos = numero;
 }
 
}
