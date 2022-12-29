package com.bch.api.rest.dto;

public class ResponseReportTransaccionH {

	private int idTrx;
    private String codigoIdTrx;
    private String codigoInsitucion;
    private String fechaTrx;
    private String nombreInsitucion;
    private String montototalenviadociclio;
    private int ciclo;
    private int intentos;
    private String estadoCiclo;
    private String codigoTrx;
    private String origen;
    private String core;
    private int totalRegistros;
    
public int getIdTrx() {
   return idTrx;
}

public void setIdTrx(int idTrx) {
   this.idTrx = idTrx;
}

public String getCore() {
	   return core;
	}

	public void setCore(String core) {
	   this.core = core;
	}

public String getOrigen() {
	   return origen;
	}

	public void setOrigen(String origen) {
	   this.origen = origen;
	}

public String getCodigoIdTrx() {
  return codigoIdTrx;
 }
 
 public void setCodigoIdTrx(String codigoIdTrx) {
	 this.codigoIdTrx = codigoIdTrx;
 }

 public String getCodigoInsitucion() {
	 return codigoInsitucion;
 }

 public void setCodigoInsitucion(String codigoInsitucion) {
	 this.codigoInsitucion = codigoInsitucion;
 }

 public String getFechaTrx() {
     return fechaTrx;
 }

 public void setFechaTrx(String fechaTrx) {
     this.fechaTrx = fechaTrx;
 }

 public String getNombreInsitucion() {
	return nombreInsitucion;
 }

 public void setNombreInsitucion(String nombreInsitucion) {
	 this.nombreInsitucion = nombreInsitucion;
 }	 


 public String getMontototalenviadociclio() {
	 return montototalenviadociclio;
 }

 public void setMontototalenviadociclio(String montototalenviadociclio) {
	 this.montototalenviadociclio = montototalenviadociclio;
 }	 
	
 public String getEstadoCiclo() {
	 return estadoCiclo;
 }

	public void setEstadoCiclo(String estadoCiclo) {
		 this.estadoCiclo = estadoCiclo;
	}	 
	
	public String getCodigoTrx() {
		return codigoTrx;
	}

	public void setCodigoTrx(String codigoTrx) {
		 this.codigoTrx = codigoTrx;
	}
	
	public int getCiclo() {
		return ciclo;
	}

	public void setCiclo(int ciclo) {
		 this.ciclo = ciclo;
	}
	
	public int getIntentos() {
		return intentos;
	}

	public void setIntentos(int intentos) {
		 this.intentos = intentos;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	
}
