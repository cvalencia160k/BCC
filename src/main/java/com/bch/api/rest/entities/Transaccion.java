package com.bch.api.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/******************************************************************************************
 * Nombre class: Transaccion........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
@Entity
@Table(name="transacciones")
public class Transaccion {
 
 /**************************************
  *    Atributos
  **************************************/
 @Id
 @Column(name="id_transaccion")
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private int idTransaccion;
 
 @Column(name="cod_institucion")
    private String codInstitucion;
 
 @Column(name="fecha_trx")
    private String fechaTransaccion;
 
 @Column(name="cdo_trx")
    private String codigoTransaccion;
 
 @Column(name="monto_total_enviado_ciclo")
    private String montoTotalEnviadoCiclo;

 @Column(name="tipo_trx")
    private String tipoTransaccion;
    
 @Column(name="ciclo")
    private int ciclo;
 
 @Column(name="estado_actual")
    private String estadoActual;

 @Column(name="codigo_id_transaccion")
    private String codigoIdTrx;
 
 @Column(name="origen")
 private String origen;
 
 @Column(name="core")
 private String core;
 
 
 /**************************************
  *    Setter-Getter
  **************************************/
    public int getIdTransaccion() {
  return idTransaccion;
 }
 public void setIdTransaccion(int idTransaccion) {
  this.idTransaccion = idTransaccion;
 }
 public String getCodInstitucion() {
  return codInstitucion;
 }
 public void setCodInstitucion(String codInstitucion) {
  this.codInstitucion = codInstitucion;
 }
 public String getFechaTransaccion() {
  return fechaTransaccion;
 }
 public void setFechaTransaccion(String fechaTransaccion) {
  this.fechaTransaccion = fechaTransaccion;
 }
 public String getCodigoTransaccion() {
  return codigoTransaccion;
 }
 public void setCodigoTransaccion(String codigoTransaccion) {
  this.codigoTransaccion = codigoTransaccion;
 }
 public String getMontoTotalEnviadoCiclo() {
  return montoTotalEnviadoCiclo;
 }
 public void setMontoTotalEnviadoCiclo(String montoTotalEnviadoCiclo) {
  this.montoTotalEnviadoCiclo = montoTotalEnviadoCiclo;
 }
 public String getTipoTransaccion() {
  return tipoTransaccion;
 }
 public void setTipoTransaccion(String tipo) {
  this.tipoTransaccion = tipo;
 }
 public int getCiclo() {
  return ciclo;
 }
 public void setCiclo(int ciclo) {
  this.ciclo = ciclo;
 }
 public String getEstadoActual() {
  return estadoActual;
 }
 public void setEstadoActual(String estadoActual) {
  this.estadoActual = estadoActual;
 }
 public String getCodigoIdTrx() {
  return codigoIdTrx;
 }
 public void setCodigoIdTrx(String codigoIdTrx) {
  this.codigoIdTrx = codigoIdTrx;
 }
 
 public String getCore() {
	  return core;
	 }
	 public void setCore(String core) {
	  this.core = core;
	 }
	 
	 public String getOrigen() {
		  return origen;
		 }
	public void setOrigen(String origen) {
		  this.origen = origen;
		 }
	 
}
