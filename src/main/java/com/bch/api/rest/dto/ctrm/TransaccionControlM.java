package com.bch.api.rest.dto.ctrm;

import javax.persistence.Column;


/******************************************************************************************
 * Nombre class: TransaccionControlM........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class TransaccionControlM {

 @Column(name="idTransaccion")
 private int idTransaccion;
 
 @Column(name="codigoTrx")
 private String codigoTrx;
 
 @Column(name="codigoInstitucion")
 private String codigoInstitucion;
 
 @Column(name="rutEmisor")
 private String rutEmisor;
 
 @Column(name="numeroCuentaCargo")
 private long numeroCuentaCargo;
 
 @Column(name="montoTotalEnviadoCiclo")
 private String montoTotalEnviadoCiclo;
 
 @Column(name="montoCargo")
 private double montoCargo;
 
 @Column(name="tipoTrx")
 private String tipoTrx;
 
 @Column(name="estadoActual")
 private String estadoActual;

 @Column(name="origen")
 private String origen;
 
 @Column(name="core")
 private String core;
 
 public String getCore() {
	  return core;
	 }

public void setCore(String core) {
	 this.core = core;;
	 }
 
 public String getOrigen() {
	  return origen;
	 }
 
 public void setOrigen(String origen) {
	 this.origen = origen;;
	 }
 
 public int getIdTransaccion() {
  return idTransaccion;
 }
 public void setIdTransaccion(int idTransaccion) {
  this.idTransaccion = idTransaccion;
 }
 public String getCodigoTrx() {
  return codigoTrx;
 }
 public void setCodigoTrx(String codigoTrx) {
  this.codigoTrx = codigoTrx;
 }
 public String getCodigoInstitucion() {
  return codigoInstitucion;
 }
 public void setCodigoInstitucion(String codigoInstitucion) {
  this.codigoInstitucion = codigoInstitucion;
 }
 public String getRutEmisor() {
  return rutEmisor;
 }
 public void setRutEmisor(String rutEmisor) {
  this.rutEmisor = rutEmisor;
 }
 public long getNumeroCuenta() {
  return numeroCuentaCargo;
 }
 public void setNumeroCuenta(long numeroCuenta) {
  this.numeroCuentaCargo = numeroCuenta;
 }
 public String getMontoTotalEnviadoCiclo() {
  return montoTotalEnviadoCiclo;
 }
 public void setMontoTotalEnviadoCiclo(String montoTotalEnviadoCiclo) {
  this.montoTotalEnviadoCiclo = montoTotalEnviadoCiclo;
 }
 public double getMontoCargo() {
  return montoCargo;
 }
 public void setMontoCargo(double montoCargo) {
  this.montoCargo = montoCargo;
 }
 public String getTipoTrx() {
  return tipoTrx;
 }
 public void setTipoTrx(String tipoTrx) {
  this.tipoTrx = tipoTrx;
 }
 public String getEstadoActual() {
  return estadoActual;
 }
 public void setEstadoActual(String estadoActual) {
  this.estadoActual = estadoActual;
 }
}

