package com.bch.api.rest.dto;

/******************************************************************************************
 * Nombre class: TransaccionDTO..................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class TransaccionDTO {
	private String idTrx;
	private String codInstitucion;
    private String fechaHoraTrx;
    private String codTrx;
    private long montoTotalEnviadoCicloLong;
    private String montoTotalEnviadoCiclo;
    private String rutEmisor;
    private String tipoTrx;
    private String estadoActual;
    private String numeroCuentaCargo;
    private String codigoIdTransaccion;
    private String nombreInsitucion;
    private int ciclo;
    private int intentos;
    private String origen;
    private String core;
    
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
    	 
    	 
	public String getNombreInsitucion() {
  	  return nombreInsitucion;
  	 }
  	 public void setNombreInsitucion(String nombreInsitucion) {
  	  this.nombreInsitucion = nombreInsitucion;
  	 }
  	 public int getCiclo() {
  	  return ciclo;
  	 }
  	 public void setCiclo(int ciclo) {
  	  this.ciclo = ciclo;
  	 }
    
    
    public String getIdTrx() {
    	  return idTrx;
    	 }
    	 public void setIdTrx(String idTrx) {
    	  this.idTrx = idTrx;
    	 }
    	 public String getRutEmisor() {
    	  return rutEmisor;
    	 }
    	 public void setRutEmisor(String rutEmisor) {
    	  this.rutEmisor = rutEmisor;
    	 }
    	 public String getTipoTrx() {
       	  return tipoTrx;
       	 }
       	 public void setTipoTrx(String tipo) {
       	  this.tipoTrx = tipo;
       	 }
    	 public String getEstadoActual() {
    	  return estadoActual;
    	 }
    	 public void setEstadoActual(String estadoActual) {
    	  this.estadoActual = estadoActual;
    	 }
    	 public String getNumeroCuentaCargo() {
    	  return numeroCuentaCargo;
    	 }
    	 public void setNumeroCuentaCargo(String numeroCuentaCargo) {
    	  this.numeroCuentaCargo = numeroCuentaCargo;
    	 }
    
    	 public String getCodigoIdTransaccion() {
       	  return codigoIdTransaccion;
       	 }
       	 public void setCodigoIdTransaccion(String codigoIdTransaccion) {
       	  this.codigoIdTransaccion = codigoIdTransaccion;
       	 }
    	 
 public String getCodInstitucion() {
  return codInstitucion;
 }
 public void setCodInstitucion(String codInstitucion) {
  this.codInstitucion = codInstitucion;
 }
 public String getFechaHoraTrx() {
  return fechaHoraTrx;
 }
 public void setFechaHoraTrx(String fechaHoraTrx) {
  this.fechaHoraTrx = fechaHoraTrx;
 }
 public String getCodTrx() {
  return codTrx;
 }
 public void setCodTrx(String codTrx) {
  this.codTrx = codTrx;
 }
 public long getMontoTotalEnviadoCicloLong() {
	  return montoTotalEnviadoCicloLong;
	 }
	 public void setMontoTotalEnviadoCicloLong(long montoTotalEnviadoCicloLong) {
	  this.montoTotalEnviadoCicloLong = montoTotalEnviadoCicloLong;
	 }
 public String getMontoTotalEnviadoCiclo() {
  return montoTotalEnviadoCiclo;
 }
 public void setMontoTotalEnviadoCiclo(String montoTotalEnviadoCiclo) {
  this.montoTotalEnviadoCiclo = montoTotalEnviadoCiclo;
 }

 public int getIntentos() {
		return intentos;
	}
	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}
}