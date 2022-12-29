package com.bch.api.rest.properties;

import org.apache.log4j.Logger;

public class ServicioTokenPropertiesDto {

 private static final Logger LOGGER= Logger.getLogger(ServicioTokenPropertiesDto.class);
 
 private String tokenServicioUrl; 
 private String tokenServicioUser; 
 private String tokenServicioPass; 
 private String tipoAuthServicio;
 private String tokenServicioParamUser;
 private String tokenServicioParamPassUser;
 private String tokenServicioParamScope;
 private String tokenServicioParamGrandType; 
 private String servicioDominioS;
 private String servicioSoapActionS;
 private String servicioUsuarioConsumidorS;
 private String servicioIdCanalS;
 private String servicioNumeroSucursalS;
 private String servicioUrlServicioS;
 private String servicioUrlSecuSecExtS;
 private String servicioUrlSecuMessageS;
 private String servicioUrlSecuUsrNameTokenS;
 private String servicioUrlSecuUtilityS;
 private String servicioSecuUserS;
 private String servicioSecuUserPasswordS;
 private String servicioAuthUsuarioServicio;
 private String servicioAuthPasswordServicio;
 private String servicioAuthTipoAuth;
 private String servicioOsbNs;
 private String servicioOsbNs1;
 private String servicioOsbMpi;
 private String servicioRutOperadora;
 private String servicioBancoDestino;
 private String servicioBancoOrigen;
 private String servicioOficinaOrigenTx;
 private String servicioCanalOrigenTx;
 private String servicioMoneda;
 private String servicioCodigoProductoFC;
 private String servicioNombreCampo;
 private String servicioValorCampo;
 private String servicioNombreCampo2;
 private String servicioNombreCampo3;
 private String servicioPrioridad;
 private String codigoTransaccionFC;
 private String codigoExtendidoFC;
 
 public ServicioTokenPropertiesDto() {
  LOGGER.debug("inciio ServicioServicioPropertiesDto");
 }

 public String getTokenServicioUrl() {
  return tokenServicioUrl;
 }

 public void setTokenServicioUrl(String tokenServicioUrl) {
  this.tokenServicioUrl = tokenServicioUrl;
 }

 public String getTokenServicioUser() {
  return tokenServicioUser;
 }

 public void setTokenServicioUser(String tokenServicioUser) {
  this.tokenServicioUser = tokenServicioUser;
 }

 public String getTokenServicioPass() {
  return tokenServicioPass;
 }

 public void setTokenServicioPass(String tokenServicioPass) {
  this.tokenServicioPass = tokenServicioPass;
 }

 public String getTipoAuthServicio() {
  return tipoAuthServicio;
 }

 public void setTipoAuthServicio(String tipoAuthServicio) {
  this.tipoAuthServicio = tipoAuthServicio;
 }

 public String getTokenServicioParamUser() {
  return tokenServicioParamUser;
 }

 public void setTokenServicioParamUser(String tokenServicioParamUser) {
  this.tokenServicioParamUser = tokenServicioParamUser;
 }

 public String getTokenServicioParamPassUser() {
  return tokenServicioParamPassUser;
 }

 public void setTokenServicioParamPassUser(String tokenServicioParamPassUser) {
  this.tokenServicioParamPassUser = tokenServicioParamPassUser;
 }

 public String getTokenServicioParamScope() {
  return tokenServicioParamScope;
 }

 public void setTokenServicioParamScope(String tokenServicioParamScope) {
  this.tokenServicioParamScope = tokenServicioParamScope;
 }

 public String getTokenServicioParamGrandType() {
  return tokenServicioParamGrandType;
 }

 public void setTokenServicioParamGrandType(String tokenServicioParamGrandType) {
  this.tokenServicioParamGrandType = tokenServicioParamGrandType;
 }

 public String getServicioDominio() {
  return servicioDominioS;
 }

 public void setServicioDominioS(String servicioDominio) {
  this.servicioDominioS = servicioDominio;
 }

 public String getServicioSoapActionS() {
  return servicioSoapActionS;
 }

 public void setServicioSoapActionS(String servicioSoapAction) {
  this.servicioSoapActionS = servicioSoapAction;
 }

 public String getServicioUsuarioConsumidorS() {
  return servicioUsuarioConsumidorS;
 }

 public void setServicioUsuarioConsumidorS(String servicioUsuarioConsumidor) {
  this.servicioUsuarioConsumidorS = servicioUsuarioConsumidor;
 }

 public String getServicioIdCanalS() {
  return servicioIdCanalS;
 }

 public void setServicioIdCanalS(String servicioIdCanal) {
  this.servicioIdCanalS = servicioIdCanal;
 }

 public String getServicioNumeroSucursalS() {
  return servicioNumeroSucursalS;
 }

 public void setServicioNumeroSucursalS(String servicioNumeroSucursal) {
  this.servicioNumeroSucursalS = servicioNumeroSucursal;
 }

 public String getServicioUrlServicioS() {
  return servicioUrlServicioS;
 }

 public void setServicioUrlServicioS(String servicioUrlServicio) {
  this.servicioUrlServicioS = servicioUrlServicio;
 }

 public String getServicioUrlSecuSecExtS() {
  return servicioUrlSecuSecExtS;
 }

 public void setServicioUrlSecuSecExtS(String servicioUrlSecuSecExt) {
  this.servicioUrlSecuSecExtS = servicioUrlSecuSecExt;
 }

 public String getServicioUrlSecuMessageS() {
  return servicioUrlSecuMessageS;
 }

 public void setServicioUrlSecuMessageS(String servicioUrlSecuMessage) {
  this.servicioUrlSecuMessageS = servicioUrlSecuMessage;
 }

 public String getServicioUrlSecuUsrNameTokenS() {
  return servicioUrlSecuUsrNameTokenS;
 }

 public void setServicioUrlSecuUsrNameTokenS(String servicioUrlSecuUsrNameToken) {
  this.servicioUrlSecuUsrNameTokenS = servicioUrlSecuUsrNameToken;
 }

 public String getServicioUrlSecuUtilityS() {
  return servicioUrlSecuUtilityS;
 }

 public void setServicioUrlSecuUtilityS(String servicioUrlSecuUtility) {
  this.servicioUrlSecuUtilityS = servicioUrlSecuUtility;
 }

 public String getServicioSecuUserS() {
  return servicioSecuUserS;
 }

 public void setServicioSecuUserS(String servicioSecuUser) {
  this.servicioSecuUserS = servicioSecuUser;
 }

 public String getServicioSecuUserPasswordS() {
  return servicioSecuUserPasswordS;
 }

 public void setServicioSecuUserPasswordS(String servicioSecuUserPassword) {
  this.servicioSecuUserPasswordS = servicioSecuUserPassword;
 }

 public String getServicioAuthUsuarioServicio() {
  return servicioAuthUsuarioServicio;
 }

 public void setServicioAuthUsuarioServicio(String servicioAuthUsuarioServicio) {
  this.servicioAuthUsuarioServicio = servicioAuthUsuarioServicio;
 }

 public String getServicioAuthPasswordServicio() {
  return servicioAuthPasswordServicio;
 }

 public void setServicioAuthPasswordServicio(String servicioAuthPasswordServicio) {
  this.servicioAuthPasswordServicio = servicioAuthPasswordServicio;
 }

 public String getServicioAuthTipoAuth() {
  return servicioAuthTipoAuth;
 }

 public void setServicioAuthTipoAuth(String servicioAuthTipoAuth) {
  this.servicioAuthTipoAuth = servicioAuthTipoAuth;
 }

 public String getServicioOsbNsS() {
  return servicioOsbNs;
 }

 public void setServicioOsbNsS(String servicioOsbNs) {
  this.servicioOsbNs = servicioOsbNs;
 }

 public String getServicioOsbNs1S() {
  return servicioOsbNs1;
 }

 public void setServicioOsbNs1S(String servicioOsbNs1) {
  this.servicioOsbNs1 = servicioOsbNs1;
 }

 public String getServicioOsbMpiS() {
  return servicioOsbMpi;
 }

 public void setServicioOsbMpiS(String servicioOsbMpi) {
  this.servicioOsbMpi = servicioOsbMpi;
 }

 public String getServicioRutOperadora() {
  return servicioRutOperadora;
 }

 public void setServicioRutOperadora(String servicioRutOperadora) {
  this.servicioRutOperadora = servicioRutOperadora;
 }

 public String getServicioBancoDestino() {
  return servicioBancoDestino;
 }

 public void setServicioBancoDestino(String servicioBancoDestino) {
  this.servicioBancoDestino = servicioBancoDestino;
 }

 public String getServicioBancoOrigen() {
  return servicioBancoOrigen;
 }

 public void setServicioBancoOrigen(String servicioBancoOrigen) {
  this.servicioBancoOrigen = servicioBancoOrigen;
 }

 public String getServicioOficinaOrigenTx() {
  return servicioOficinaOrigenTx;
 }

 public void setServicioOficinaOrigenTx(String servicioOficinaOrigenTx) {
  this.servicioOficinaOrigenTx = servicioOficinaOrigenTx;
 }

 public String getServicioCanalOrigenTx() {
  return servicioCanalOrigenTx;
 }

 public void setServicioCanalOrigenTx(String servicioCanalOrigenTx) {
  this.servicioCanalOrigenTx = servicioCanalOrigenTx;
 }

 public String getServicioMoneda() {
  return servicioMoneda;
 }

 public void setServicioMoneda(String servicioMoneda) {
  this.servicioMoneda = servicioMoneda;
 }

 public String getServicioCodigoProductoFC() {
  return servicioCodigoProductoFC;
 }

 public void setServicioCodigoProductoFC(String servicioCodigoProductoFC) {
  this.servicioCodigoProductoFC = servicioCodigoProductoFC;
 }

 public String getServicioNombreCampo() {
  return servicioNombreCampo;
 }

 public void setServicioNombreCampo(String servicioNombreCampo) {
  this.servicioNombreCampo = servicioNombreCampo;
 }

 public String getServicioValorCampo() {
  return servicioValorCampo;
 }

 public void setServicioValorCampo(String servicioValorCampo) {
  this.servicioValorCampo = servicioValorCampo;
 }

 public String Servicio() {
  return servicioNombreCampo2;
 }

 public void setServicioNombreCampo2(String servicioNombreCampo2) {
  this.servicioNombreCampo2 = servicioNombreCampo2;
 }

 public String getServicioNombreCampo2() {
	  return servicioNombreCampo2;
 }
 
 public String getServicioNombreCampo3() {
  return servicioNombreCampo3;
 }

 public void setServicioNombreCampo3(String servicioNombreCampo3) {
  this.servicioNombreCampo3 = servicioNombreCampo3;
 }

 public String getServicioPrioridad() {
  return servicioPrioridad;
 }

 public void setServicioPrioridad(String servicioPrioridad) {
  this.servicioPrioridad = servicioPrioridad;
 }

 public String getCodigoTransaccionFC() {
  return codigoTransaccionFC;
 }

 public void setCodigoTransaccionFC(String codigoTransaccionFC) {
  this.codigoTransaccionFC = codigoTransaccionFC;
 }

 public String getCodigoExtendidoFC() {
  return codigoExtendidoFC;
 }

 public void setCodigoExtendidoFC(String codigoExtendidoFC) {
  this.codigoExtendidoFC = codigoExtendidoFC;
 }

}