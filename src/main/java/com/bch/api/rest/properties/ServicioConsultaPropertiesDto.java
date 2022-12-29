package com.bch.api.rest.properties;

import org.apache.log4j.Logger;

public class ServicioConsultaPropertiesDto {

 private static final Logger LOGGER = Logger.getLogger(ServicioConsultaPropertiesDto.class);

 private String domninio;
 private String urlServicio;
 private String consultaSoapAction;
 private String usuarioConsumidor;
 private String idCanal;
 private String numeroSucursal;
 private String urlSecuSecExt;
 private String urlSecuMessage;
 private String urlSecuUsrNameToken;
 private String urlSecuUtility;
 private String consultaSecuUser;
 private String consultaSecuUserPassword;
 private String consultaAuthUsuarioServicio;
 private String consultaAuthPasswordServicio;
 private String consultaAuthTipoAuth;
 private String consultaOsbNs;
 private String consultaOsbNs1;
 private String consultaOsbMpi;
 
 public ServicioConsultaPropertiesDto() {
  LOGGER.debug("inciio serviicioCOnsultaPropierte");
 }

 public String getDomninio() {
  return domninio;
 }

 public void setDomninio(String domninio) {
  this.domninio = domninio;
 }

 public String getUrlServicio() {
  return urlServicio;
 }

 public void setUrlServicio(String urlServicio) {
  this.urlServicio = urlServicio;
 }

 public String getConsultaSoapAction() {
  return consultaSoapAction;
 }

 public void setConsultaSoapAction(String consultaSoapAction) {
  this.consultaSoapAction = consultaSoapAction;
 }

 public String getUsuarioConsumidor() {
  return usuarioConsumidor;
 }

 public void setUsuarioConsumidor(String usuarioConsumidor) {
  this.usuarioConsumidor = usuarioConsumidor;
 }

 public String getIdCanal() {
  return idCanal;
 }

 public void setIdCanal(String idCanal) {
  this.idCanal = idCanal;
 }

 public String getNumeroSucursal() {
  return numeroSucursal;
 }

 public void setNumeroSucursal(String numeroSucursal) {
  this.numeroSucursal = numeroSucursal;
 }

 public String getUrlSecuSecExt() {
  return urlSecuSecExt;
 }

 public void setUrlSecuSecExt(String urlSecuSecExt) {
  this.urlSecuSecExt = urlSecuSecExt;
 }

 public String getUrlSecuMessage() {
  return urlSecuMessage;
 }

 public void setUrlSecuMessage(String urlSecuMessage) {
  this.urlSecuMessage = urlSecuMessage;
 }

 public String getUrlSecuUsrNameToken() {
  return urlSecuUsrNameToken;
 }

 public void setUrlSecuUsrNameToken(String urlSecuUsrNameToken) {
  this.urlSecuUsrNameToken = urlSecuUsrNameToken;
 }

 public String getUrlSecuUtility() {
  return urlSecuUtility;
 }

 public void setUrlSecuUtility(String urlSecuUtility) {
  this.urlSecuUtility = urlSecuUtility;
 }

 public String getConsultaSecuUser() {
  return consultaSecuUser;
 }

 public void setConsultaSecuUser(String consultaSecuUser) {
  this.consultaSecuUser = consultaSecuUser;
 }

 public String getConsultaSecuUserPassword() {
  return consultaSecuUserPassword;
 }

 public void setConsultaSecuUserPassword(String consultaSecuUserPassword) {
  this.consultaSecuUserPassword = consultaSecuUserPassword;
 }

 public String getConsultaAuthUsuarioServicio() {
  return consultaAuthUsuarioServicio;
 }

 public void setConsultaAuthUsuarioServicio(String consultaAuthUsuarioServicio) {
  this.consultaAuthUsuarioServicio = consultaAuthUsuarioServicio;
 }

 public String getConsultaAuthPasswordServicio() {
  return consultaAuthPasswordServicio;
 }

 public void setConsultaAuthPasswordServicio(String consultaAuthPasswordServicio) {
  this.consultaAuthPasswordServicio = consultaAuthPasswordServicio;
 }

 public String getConsultaAuthTipoAuth() {
  return consultaAuthTipoAuth;
 }

 public void setConsultaAuthTipoAuth(String consultaAuthTipoAuth) {
  this.consultaAuthTipoAuth = consultaAuthTipoAuth;
 }

 public String getConsultaOsbNs() {
  return consultaOsbNs;
 }

 public void setConsultaOsbNs(String consultaOsbNs) {
  this.consultaOsbNs = consultaOsbNs;
 }

 public String getConsultaOsbNs1() {
  return consultaOsbNs1;
 }

 public void setConsultaOsbNs1(String consultaOsbNs1) {
  this.consultaOsbNs1 = consultaOsbNs1;
 }

 public String getConsultaOsbMpi() {
  return consultaOsbMpi;
 }

 public void setConsultaOsbMpi(String consultaOsbMpi) {
  this.consultaOsbMpi = consultaOsbMpi;
 }

}