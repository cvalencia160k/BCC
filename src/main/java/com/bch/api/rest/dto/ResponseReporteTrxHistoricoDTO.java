package com.bch.api.rest.dto;

public class ResponseReporteTrxHistoricoDTO {

	private int idTrx;
	private int idReporte;
	private String codIdTrx;
	private String codInstitucionTrx;
	private String nombreInstitucionTrx;
	private String tipoTrx;
	private String fechaTrx;
	private int numeroTrx;
	private long montoTotalTrx;
	private String estadoTrx;
	private String nombreArchivoTrx;
	
	public int getIdTrx() {
		return idTrx;
	}
	public void setIdTrx(int idTrx) {
		this.idTrx = idTrx;
	}
	public int getIdReporte() {
		return idReporte;
	}
	public void setIdReporte(int idReporte) {
		this.idReporte = idReporte;
	}
	public String getCodIdTrx() {
		return codIdTrx;
	}
	public void setCodIdTrx(String codIdTrx) {
		this.codIdTrx = codIdTrx;
	}
	public String getCodInstitucionTrx() {
		return codInstitucionTrx;
	}
	public void setCodInstitucionTrx(String codInstitucionTrx) {
		this.codInstitucionTrx = codInstitucionTrx;
	}
	public String getNombreInstitucionTrx() {
		return nombreInstitucionTrx;
	}
	public void setNombreInstitucionTrx(String nombreInstitucionTrx) {
		this.nombreInstitucionTrx = nombreInstitucionTrx;
	}
	public String getTipoTrx() {
		return tipoTrx;
	}
	public void setTipoTrx(String tipoTrx) {
		this.tipoTrx = tipoTrx;
	}
	public String getFechaTrx() {
		return fechaTrx;
	}
	public void setFechaTrx(String fechaTrx) {
		this.fechaTrx = fechaTrx;
	}
	public int getNumeroTrx() {
		return numeroTrx;
	}
	public void setNumeroTrx(int numeroTrx) {
		this.numeroTrx = numeroTrx;
	}
	public long getMontoTotalTrx() {
		return montoTotalTrx;
	}
	public void setMontoTotalTrx(long montoTotalTrx) {
		this.montoTotalTrx = montoTotalTrx;
	}
	public String getEstadoTrx() {
		return estadoTrx;
	}
	public void setEstadoTrx(String estadoTrx) {
		this.estadoTrx = estadoTrx;
	}
	public String getNombreArchivoTrx() {
		return nombreArchivoTrx;
	}
	public void setNombreArchivoTrx(String nombreArchivoTrx) {
		this.nombreArchivoTrx = nombreArchivoTrx;
	}
}
