package com.bch.api.rest.dto;

/**
 * Modelo usado para mostrar las historias de una transacción
 * @author 160k
 *
 */
public class ResponseTransaccionHistoriaDTO {

	private int idHis;
    private int idTrx;
    private int codigoOperacion;
    private String fechaTrx;
    private String tipoEvento;
    private String detalle;
    private String estado;
    private String codigoIdTrx;
    
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
	public int getCodigoOperacion() {
		return codigoOperacion;
	}
	public void setCodigoOperacion(int codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
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
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCodigoIdTrx() {
		return codigoIdTrx;
	}
	public void setCodigoIdTrx(String codigoIdTrx) {
		this.codigoIdTrx = codigoIdTrx;
	}
	
}
