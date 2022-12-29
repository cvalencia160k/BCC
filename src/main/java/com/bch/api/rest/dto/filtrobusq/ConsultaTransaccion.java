package com.bch.api.rest.dto.filtrobusq;

/******************************************************************************************
 * Nombre class: ConsultaTransaccion........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class ConsultaTransaccion {

 
 private String fechaDesde;
 private String fechaHasta;
 private String codigoEmisor;
 private String tipoTrx;
 private String estadoTrx;
 
 public String getDateDesde() {
  return fechaDesde;
 }
 public void setDateDesde(String fechasDesde) {
  this.fechaDesde = fechasDesde;
 }
 public String getDateHasta() {
  return fechaHasta;
 }
 public void setDateHasta(String fechasHasta) {
  this.fechaHasta = fechasHasta;
 }
 public String getCodigoEmisors() {
  return codigoEmisor;
 }
 public void setCodigoEmisors(String codigoEmisore) {
  this.codigoEmisor = codigoEmisore;
 }
 public String getTipoTrxs() {
  return tipoTrx;
 }
 public void setTipoTrxs(String tipoTrxn) {
  this.tipoTrx = tipoTrxn;
 }
 public String getEstadoTrxs() {
  return estadoTrx;
 }
 public void setEstadoTrxs(String estadoTrxn) {
  this.estadoTrx = estadoTrxn;
 }
 
}