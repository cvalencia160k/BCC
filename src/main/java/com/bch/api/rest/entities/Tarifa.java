package com.bch.api.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/******************************************************************************************
 * Nombre class: Tarifa...................................................................*
 * Actions: Obtiene la información de tarifas de emisores.................................*
 ******************************************************************************************/
@Entity
@Table(name="tarifas")
public class Tarifa {

	 /**************************************
	  *    Atributos
	  **************************************/
	 @Id
	 @Column(name="id_tarifa")
	 private int idTarifaBD;
	 
	 @Column(name="codigo_emisor")
	 private String codigoEmisorBD;
	 
	 @Column(name="cuenta_tarifa")
	 private long cuentaTarifaBD;
	 
	 @Column(name="tarifa_lbtr")
	 private float tarifaLBTRBD;
	 
	 @Column(name="tarifa_tramo")
	 private float tarifaTramoBD;
	 
	 /**************************************
	  *    Setter-Getter
	  **************************************/
	public int getIdTarifaBD() {
		return idTarifaBD;
	}

	public void setIdTarifaBD(int idTarifa) {
		this.idTarifaBD = idTarifa;
	}

	public String getCodigoEmisorBD() {
		return codigoEmisorBD;
	}

	public void setCodigoEmisorBD(String codigoEmisor) {
		this.codigoEmisorBD = codigoEmisor;
	}

	public long getCuentaTarifaBD() {
		return cuentaTarifaBD;
	}

	public void setCuentaTarifaBD(long cuentaTarifa) {
		this.cuentaTarifaBD = cuentaTarifa;
	}

	public float getTarifaLBTRBD() {
		return tarifaLBTRBD;
	}

	public void setTarifaLBTR(float tarifaLBTR) {
		this.tarifaLBTRBD = tarifaLBTR;
	}

	public float getTarifaTramo() {
		return tarifaTramoBD;
	}

	public void setTarifaTramo(float tarifaTramo) {
		this.tarifaTramoBD = tarifaTramo;
	} 
}
