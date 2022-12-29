package com.bch.api.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/******************************************************************************************
 * Nombre class: Tramo........................................................*
 * Actions: traer todos atributos........................................................*
 *****************************************************************************************/
@Entity
@Table(name="tramos")
public class Tramo {

	 /**************************************
	  *    Atributos
	  **************************************/
	 @Id
	 @Column(name="id_tramo")
	 private int idTramo;
	 
	 @Column(name="nombre")
	 private String nombre;
	 
	 @Column(name="minimo")
	 private int minimo;
	 
	 @Column(name="maximo")
	 private int maximo;
	 
	 @Column(name="tarifa")
	 private float tarifa;
	 /**************************************
	  *    Setter-Getter
	  **************************************/ 
	 
	 public int getIdTramo() {
	  return idTramo;
	 }

	 public String getNombre() {
		  return nombre;
	 }
	 
	 public int getMinimo() {
		  return minimo;
	 }
	 
	 public int getMaximo() {
		  return maximo;
	 }
	 
	 public float getTarifa() {
		  return tarifa;
	 }
	 
	 public void setIdTramo(int idTramo) {
		  this.idTramo = idTramo;
	 }

	public void setNombre(String nombre) {
		 this.nombre = nombre;
	 }
		 
	public void setMinimo(int minimo) {
		this.minimo = minimo;
	}
		 
	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}
		 
	public void setTarifa(float tarifa) {
	this.tarifa = tarifa;
	}
		 
	
}
