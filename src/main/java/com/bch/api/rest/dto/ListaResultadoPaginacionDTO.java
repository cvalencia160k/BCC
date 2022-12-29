package com.bch.api.rest.dto;

/**
 * Modelo para ser usando en la paginación de tablas en general
 * @author 160k
 *
 */
public class ListaResultadoPaginacionDTO {

	private int totalRegistrosPaginacion;
	private Object listaResultado;
	
	public int getTotalRegistros() {
		return totalRegistrosPaginacion;
	}
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistrosPaginacion = totalRegistros;
	}
	public Object getListaResultado() {
		return listaResultado;
	}
	public void setListaResultado(Object listaResultado) {
		this.listaResultado = listaResultado;
	}
}
