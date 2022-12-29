package com.bch.api.rest.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/******************************************************************************************
 * Nombre class: TransaccionHistoria........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
@Entity
@Table(name="transaccion_historia")
public class TransaccionHistoria {
 
 /**************************************
  *    Atributos
  **************************************/
 @Id
 @Column(name="id_historia")
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private int idHistoria;
 
 @Column(name="id_transaccion")
    private int idTransaccion;

 @Column(name="codigo_operacion")
    private int codigoOperacion;
 
 @Column(name="fecha")
    private Date fechaRegistro;
 
 @Column(name="tipo_evento")
    private String tipoEvento;

 @Column(name="detalle")
    private String detalle;
 
 @Column(name="estado_actual")
 private String estadoActual;

 @Column(name="codigo_id_transaccion")
    private String codigoIdTrx;
    
 /**************************************
  *    Setter-Getter
  **************************************/
    
 public int getIdHistoria() {
  return idHistoria;
 }

 public void setIdHistoria(int idHistoria) {
  this.idHistoria = idHistoria;
 }

 public int getIdTransaccion() {
  return idTransaccion;
 }

 public void setIdTransaccion(int idTransaccion) {
  this.idTransaccion = idTransaccion;
 }

 public int getCodigoOperacion() {
  return codigoOperacion;
 }

 public void setCodigoOperacion(int codigo) {
  this.codigoOperacion = codigo;
 }

 public Date getFechaRegistro() {
  return fechaRegistro;
 }

 public void setFechaRegistro(Date fechaRegistro) {
  this.fechaRegistro = fechaRegistro;
 }

 public String getTipoEvento() {
  return tipoEvento;
 }

 public void setTipoEvento(String tipoEvento) {
  this.tipoEvento = tipoEvento;
 }

 public String getDetalle() {
  return detalle;
 }

 public void setDetalle(String detalle) {
  this.detalle = detalle;
 }
 
 public String getEstadoActual() {
  return estadoActual;
 }

 public void setEstadoActual(String estdao) {
  this.estadoActual = estdao;
 }

 public String getCodigoIdTrx() {
  return codigoIdTrx;
 }

 public void setCodigoIdTrx(String codigoIdTrx) {
  this.codigoIdTrx = codigoIdTrx;
 }
 
}
