package com.bch.api.rest.dto;

import org.apache.log4j.Logger;

/******************************************************************************************
 * Nombre class: SoapXMLNsDTO.............................................................*
 * Actions: Obtiene y Setea las url del xml-ns del SOAP Evvelope..........................*
 *****************************************************************************************/
public class SoapXMLNsDTO {
 private static final Logger LOGGER = Logger.getLogger(SoapXMLNsDTO.class);

 private String ns;
 private String ns1;
 private String mpi;
 
 /**
  * Constructor
  */
 public SoapXMLNsDTO() {
  LOGGER.debug("Inicio: SoapXMLNsDTo");
 }

 public String getNs() {
  return ns;
 }

 public void setNs(String ns) {
  this.ns = ns;
 }

 public String getNs1() {
  return ns1;
 }

 public void setNs1(String ns1) {
  this.ns1 = ns1;
 }

 public String getMpi() {
  return mpi;
 }

 public void setMpi(String mpi) {
  this.mpi = mpi;
 }
}
