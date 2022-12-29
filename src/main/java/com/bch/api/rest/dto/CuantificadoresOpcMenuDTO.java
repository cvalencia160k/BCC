package com.bch.api.rest.dto;

public class CuantificadoresOpcMenuDTO {

	private int cantidadTrxCCAAceptadas;
	private int cantidadTrxCCARechazadas;
	private int cantidadTrxCompen;
	private int cantidadRepoCompen;
	
	public int getCantidadTrxCCAAceptadas() {
		return cantidadTrxCCAAceptadas;
	}
	public void setCantidadTrxCCAAceptadas(int cantidadTrxCCAAceptadas) {
		this.cantidadTrxCCAAceptadas = cantidadTrxCCAAceptadas;
	}
	public int getCantidadTrxCCARechazadas() {
		return cantidadTrxCCARechazadas;
	}
	public void setCantidadTrxCCARechazadas(int cantidadTrxCCARechazadas) {
		this.cantidadTrxCCARechazadas = cantidadTrxCCARechazadas;
	}
	public int getCantidadTrxCompen() {
		return cantidadTrxCompen;
	}
	public void setCantidadTrxCompen(int cantidadTrxCompen) {
		this.cantidadTrxCompen = cantidadTrxCompen;
	}
	public int getCantidadRepoCompen() {
		return cantidadRepoCompen;
	}
	public void setCantidadRepoCompen(int cantidadRepoCompen) {
		this.cantidadRepoCompen = cantidadRepoCompen;
	}
}
