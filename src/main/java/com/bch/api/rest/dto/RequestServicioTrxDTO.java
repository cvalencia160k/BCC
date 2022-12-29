package com.bch.api.rest.dto;

/**
 * Modelo utilizado para el servicio de trx (aceptación, reintento y notificación)
 * @author 160k
 *
 */
public class RequestServicioTrxDTO {
	private String tipoServicio;
	private String arrIdTrx;	//--- Separados por 'coma'
	
	public String getTipoServicio() {
		return tipoServicio;
	}
	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}
	public String getArrIdTrx() {
		return arrIdTrx;
	}
	public void setArrIdTrx(String arr) {
		this.arrIdTrx = arr;
	}
}
