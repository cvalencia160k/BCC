package com.bch.api.rest.dto;

public class ResponseReporteHistoricoDTO {

	private int idReporte;
	private String idCodTrxRepo;
	private int codEmisor;
	private String nombreEmisor;
	private String fechaRecepcion;
	private String estado;
	private int ciclo;
	private String nombreArchivo;
	
	public int getIdReporte() {
		return idReporte;
	}
	public void setIdReporte(int idReporte) {
		this.idReporte = idReporte;
	}
	public String getIdCodTrxRepo() {
		return idCodTrxRepo;
	}
	public void setIdCodTrxRepo(String idCodTrxRepo) {
		this.idCodTrxRepo = idCodTrxRepo;
	}
	public int getCodEmisor() {
		return codEmisor;
	}
	public void setCodEmisor(int codEmisor) {
		this.codEmisor = codEmisor;
	}
	public String getNombreEmisor() {
		return nombreEmisor;
	}
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}
	public String getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(String fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getCiclo() {
		return ciclo;
	}
	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
}
