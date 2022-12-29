package com.bch.api.rest.properties;

import org.apache.log4j.Logger;

public class ServicioNotificacionPropertiesDto {

 private static final Logger LOGGER = Logger.getLogger(ServicioNotificacionPropertiesDto.class);

 private int ccaNuemeroReintentos;
 private String ccaTokenUrl;
 private String ccaTokenGrandType;
 private String ccaClientId;
 private String ccaClientSecret;
 private String ccaResetTokenUrl;
 private String ccaResetTipoAuth;
 
 private String ccaCodInstitucion;
 private String ccaCodTrx;
 private boolean ccaIndicadorReset;
 
 public ServicioNotificacionPropertiesDto() {
  LOGGER.debug("inciio serviicioNotificacionPropierties");
 }

 public int getCcaNuemeroReintentos() {
  return ccaNuemeroReintentos;
 }

 public void setCcaNuemeroReintentos(int ccaNuemeroReintentos) {
  this.ccaNuemeroReintentos = ccaNuemeroReintentos;
 }

 public String getCcaTokenUrl() {
  return ccaTokenUrl;
 }

 public void setCcaTokenUrl(String ccaTokenUrl) {
  this.ccaTokenUrl = ccaTokenUrl;
 }

 public String getCcaTokenGrandType() {
  return ccaTokenGrandType;
 }

 public void setCcaTokenGrandType(String ccaTokenGrandType) {
  this.ccaTokenGrandType = ccaTokenGrandType;
 }

 public String getCcaClientId() {
  return ccaClientId;
 }

 public void setCcaClientId(String ccaClientId) {
  this.ccaClientId = ccaClientId;
 }

 public String getCcaClientSecret() {
  return ccaClientSecret;
 }

 public void setCcaClientSecret(String ccaClientSecret) {
  this.ccaClientSecret = ccaClientSecret;
 }

 public String getCcaResetTokenUrl() {
  return ccaResetTokenUrl;
 }

 public void setCcaResetTokenUrl(String ccaResetTokenUrl) {
  this.ccaResetTokenUrl = ccaResetTokenUrl;
 }

 public String getCcaResetTipoAuth() {
  return ccaResetTipoAuth;
 }

 public void setCcaResetTipoAuth(String ccaResetTipoAuth) {
  this.ccaResetTipoAuth = ccaResetTipoAuth;
 }

 public static Logger getLog() {
  return LOGGER;
 }

 public String getCcaCodInstitucion() {
  return ccaCodInstitucion;
 }

 public void setCcaCodInstitucion(String ccaCodInstitucion) {
  this.ccaCodInstitucion = ccaCodInstitucion;
 }

 public String getCcaCodTrx() {
  return ccaCodTrx;
 }

 public void setCcaCodTrx(String ccaCodTrx) {
  this.ccaCodTrx = ccaCodTrx;
 }

 public boolean getCcaIndicadorReset() {
  return ccaIndicadorReset;
 }

 public void setCcaIndicadorReset(boolean ccaIndicadorReset) {
  this.ccaIndicadorReset = ccaIndicadorReset;
 }
 
}