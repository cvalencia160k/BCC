package com.bch.api.rest.dto;

import org.apache.log4j.Logger;

/**
 * Modelo utilizado para guardar datos de la seguridad de un servicio soap
 * @author 160k
 *
 */
public class SoapSecurityDTO {
 
 private static final Logger LOGGER = Logger.getLogger(SoapSecurityDTO.class);
 
 private String wsseUrlSecuritySecext;
 private String wsseUrlSecurityUtility;
 private String wsseUrlSecurityUsernameToken;
 private String wsseUrlSecuritySoapMessage;
 private String wsseUser;
 private String wssePass;
 
 /******************************************************************************************
  * Nombre class: SoapSecurityDTO.......................................................*
  * Actions: Obtiene y Setea los parámetros de seguridad del servicio......................*
  *****************************************************************************************/
 public SoapSecurityDTO() {
  LOGGER.debug("Inicio: SoapSecurityDTO");
 }

 public String getWsseUrlSecuritySecext() {
  return wsseUrlSecuritySecext;
 }

 public void setWsseUrlSecuritySecext(String wsseUrlSecuritySecext) {
  this.wsseUrlSecuritySecext = wsseUrlSecuritySecext;
 }

 public String getWsseUrlSecurityUtility() {
  return wsseUrlSecurityUtility;
 }

 public void setWsseUrlSecurityUtility(String wsseUrlSecurityUtility) {
  this.wsseUrlSecurityUtility = wsseUrlSecurityUtility;
 }

 public String getWsseUrlSecurityUsernameToken() {
  return wsseUrlSecurityUsernameToken;
 }

 public void setWsseUrlSecurityUsernameToken(String wsseUrlSecurityUsernameToken) {
  this.wsseUrlSecurityUsernameToken = wsseUrlSecurityUsernameToken;
 }

 public String getWsseUrlSecuritySoapMessage() {
  return wsseUrlSecuritySoapMessage;
 }

 public void setWsseUrlSecuritySoapMessage(String wsseUrlSecuritySoapMessage) {
  this.wsseUrlSecuritySoapMessage = wsseUrlSecuritySoapMessage;
 }

 public String getWsseUser() {
  return wsseUser;
 }

 public void setWsseUser(String wsseUser) {
  this.wsseUser = wsseUser;
 }

 public String getWssePass() {
  return wssePass;
 }

 public void setWssePass(String wssePass) {
  this.wssePass = wssePass;
 }
}
