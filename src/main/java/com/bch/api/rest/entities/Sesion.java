package com.bch.api.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/******************************************************************************************
 * Nombre class: Emisor........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
@Entity
@Table(name="seseiones")
public class Sesion
{
 /**************************************
  *    Atributos
  **************************************/
 @Id
 @Column(name="idSesion")
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private int idSesion;
 
 @Column(name="nombre")
 private String nombre;
 
 @Column(name="accion")
 private String accion;
 
 @Column(name="fechaSesion")
 private String fechaSesion;
 
 
   /**************************************
  *    Setter-Getter
  **************************************/ 
 
 public int getIdSesion() {
	 return idSesion;
 }

 public void setIdSesion(int idSesion) {
	 this.idSesion = idSesion;
 }

 public String getNombre() {
	 return nombre;
 }

 
 public void setNombre(String nombre) {
  this.nombre = nombre;
 }
 
 public String getAccion() {
	 return accion;
 }

 
 public void setAccion(String accion) {
  this.accion = accion;
 }

 public String getFechaSesion() {
  return fechaSesion;
 }

 public void setfechaSesion(String fechaSesion) {
  this.fechaSesion = fechaSesion;
 }
	 

}
