package com.bch.api.rest.dto;

/******************************************************************************************
 * Nombre class: TarifaDTO..................................................*
 *****************************************************************************************/
public class TarifaDTO {
	private int idTarifa;
	private String codigoEmisor;
    private String nombreEmisor;
    private String rutEmisor;
    private long cuentaTarifa;
    private float tarifaLBTR;
    private float tarifaTramo;
    
	public int getIdTarifa() {
		return idTarifa;
	}
	public void setIdTarifa(int idTarifa) {
		this.idTarifa = idTarifa;
	}
	public String getCodigoEmisor() {
		return codigoEmisor;
	}
	public void setCodigoEmisor(String codigoEmisor) {
		this.codigoEmisor = codigoEmisor;
	}
	public String getNombreEmisor() {
		return nombreEmisor;
	}
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}
	public String getRutEmisor() {
		return rutEmisor;
	}
	public void setRutEmisor(String rutEmisor) {
		this.rutEmisor = rutEmisor;
	}
	public long getCuentaTarifa() {
		return cuentaTarifa;
	}
	public void setCuentaTarifa(long cuentaTarifa) {
		this.cuentaTarifa = cuentaTarifa;
	}
	public float getTarifaLBTR() {
		return tarifaLBTR;
	}
	public void setTarifaLBTR(float tarifaLBTR) {
		this.tarifaLBTR = tarifaLBTR;
	}
	public float getTarifaTramo() {
		return tarifaTramo;
	}
	public void setTarifaTramo(float tarifaTramo) {
		this.tarifaTramo = tarifaTramo;
	}
}