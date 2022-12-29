package com.bch.api.rest.dto;

public class ResponseTrxCompenHistoriaDTO {

	private int idHis;
	private int idTrx;
	private String fechaTrx;
	private String tipoEvento;
	private String codigoIdTrx;
	private String detalle;
	
	public int getIdHis() {
		return idHis;
	}
	public void setIdHis(int idHis) {
		this.idHis = idHis;
	}
	public int getIdTrx() {
		return idTrx;
	}
	public void setIdTrx(int idTrx) {
		this.idTrx = idTrx;
	}
	public String getFechaTrx() {
		return fechaTrx;
	}
	public void setFechaTrx(String fechaTrx) {
		this.fechaTrx = fechaTrx;
	}
	public String getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public String getCodigoIdTrx() {
		return codigoIdTrx;
	}
	public void setCodigoIdTrx(String codigoIdTrx) {
		this.codigoIdTrx = codigoIdTrx;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
}
