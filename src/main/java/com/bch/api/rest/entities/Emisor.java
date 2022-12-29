package com.bch.api.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/******************************************************************************************
 * Nombre class: Emisor........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
@Entity
@Table(name="emisores")
public class Emisor
{
 /**************************************
  *    Atributos
  **************************************/
 @Id
 @Column(name="codigo_emisor")
 private int codigoEmisor;
 
 @Column(name="rut")
    private String rutEmisor;
 
 @Column(name="nombre")
    private String nombreEmisor;
 
 @Column(name="estado")
    private int estado;
 
 @Column(name="numero_cuenta_cargo")
    private long numeroCuentaCargo;

 @Column(name="monto_maximo_transaccion")
    private String montoMaximoTransaccion;

 @Column(name="numero_cuenta_comision")
    private long numeroCuentaComision;

 @Column(name="tarifa_lbtr")
    private float tarifaLBTR;
 
 @Column(name="id_tramo")
 private int idTramo;
 
 @Transient
 private Tramo tram;
 
 @Transient
 private String nEstado;
  /**************************************
  *    Setter-Getter
  **************************************/ 
 
 public int getCodigoEmisor() {
  return codigoEmisor;
 }

 public long getNumeroCuentaCargo() {
  return numeroCuentaCargo;
 }

 public void setNumeroCuentaCargo(long numeroCuentaCargo) {
  this.numeroCuentaCargo = numeroCuentaCargo;
 }

 public void setCodigoEmisor(int codigoEmisor) {
  this.codigoEmisor = codigoEmisor;
 }

 public String getRutEmisor() {
  return rutEmisor;
 }

 public void setRutEmisor(String rutEmisor) {
  this.rutEmisor = rutEmisor;
 }

 public String getNombreEmisor() {
  return nombreEmisor;
 }

 public void setNombreEmisor(String nombreEmisor) {
  this.nombreEmisor = nombreEmisor;
 }

 public int getEstado() {
  return estado;
 }

 public void setEstado(int estado) {
  this.estado = estado;
 }

 public String getMontoMaximoTransaccion() {
  return montoMaximoTransaccion;
 }

 public void setMontoMaximoTransaccion(String montoMaximoTransaccion) {
  this.montoMaximoTransaccion = montoMaximoTransaccion;
 }

 public long getNumeroCuentaComision() {
  return numeroCuentaComision;
 }

 public void setNumeroCuentaComision(long numeroCuentaComision) {
  this.numeroCuentaComision = numeroCuentaComision;
 }

 public float getTarifaLBTR() {
  return tarifaLBTR;
 }

 public void setTarifaLBTR(float tarifaLBTR) {
  this.tarifaLBTR = tarifaLBTR;
 }

 public int getIdTramo() {
  return idTramo;
 }

 public void setIdTramo(int idTramo) {
  this.idTramo = idTramo;
 }
 
public void setTramo(Tramo tram) {
	  this.tram = tram;
	 }
	 
	 public Tramo getTramo() {
		  return tram;
		 }
 
	 public void setNEstado(String nEstado) {
		  this.nEstado = nEstado;
		 }
		 
		 public String getNEstado() {
			  return nEstado;
			 }	 
	 

}
