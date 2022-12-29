package com.bch.api.rest.dto;

import org.apache.log4j.Logger;

/******************************************************************************************
 * Nombre class: ServicioConsultaDatosHeaderDTO...........................................*
 * Actions: Obtiene y Setea la data a consultar en el header del servicio.................*
 *****************************************************************************************/
public class ServicioConsultaDatosDTO {
 
 private static final Logger LOGGER = Logger.getLogger(ServicioConsultaDatosDTO.class);
 
 private String consUsuario;
 private String consPathServices;
 
 private String tranIdTransaccionNegocio;
 private String tranInternalCode;
 private String tranFechaHora;
 private String tranIdCanal;
 private String tranNumeroSucursal;
 private int tranPrioridad;
 
 private String bodyIdTransaccion;
 
 private String bodyDatCabNegRutOperadora;
 private String bodyDatCabNegCajero;
 private String bodyDatCabNegBancoDestino;
 private String bodyDatCabNegBancoOrigen;
 private String bodyDatCabNegRutCliente;
 private String bodyDatCabNegOficinaOrigenTx;
 private String bodyDatCabNegCuiOrigenTx;
 private String bodyDatCabNegOficinaOrigenExternaTx;
 private String bodyDatCabNegCanalOrigenTx;
 private String bodyDatCabNegRutSupervisor;
 private String bodyDatCabNegSupervisor;
 private String bodyDatCabNegFechaContable;
 private String bodyDatCabNegFechaHoraCorrienteTx;
 private String bodyDatCabNegHorario;
 private String bodyDatCabNegTxId;
 private String bodyDatCabNegTxIdParaReversar;
 
 private String bodyDatNegCuenta;
 private String bodyDatNegMoneda;
 private String bodyDatNegMonto;
 private String bodyDatNegNumeroBoleta;
 private String bodyDatNegCodigoProductoFC;
 private String bodyDatNegCodigoTransaccionFC;
 private String bodyDatNegCodigoExtendidoFC;
 private String bodyDatNegLlaveLibretaAhorro;
 private String bodyDatNegSaldoLibreta;
 private String bodyDatNegFlagRespuesta;
 
 private String bodyListaDatCarNombreCampo;
 private String bodyListaDatCarValorCampo;
 
 /**
  * Constructor de la clase
  */ 
 public ServicioConsultaDatosDTO() {
  LOGGER.debug("Inicio: ServicioConsultaDatosDTO");
 }

 public String getConsUsuario() {
  return consUsuario;
 }

 public void setConsUsuario(String consUsuario) {
  this.consUsuario = consUsuario;
 }

 public String getConsPathServices() {
  return consPathServices;
 }

 public void setConsPathServices(String consPathServices) {
  this.consPathServices = consPathServices;
 }

 public String getTranIdTransaccionNegocio() {
  return tranIdTransaccionNegocio;
 }

 public void setTranIdTransaccionNegocio(String tranIdTransaccionNegocio) {
  this.tranIdTransaccionNegocio = tranIdTransaccionNegocio;
 }

 public String getTranInternalCode() {
  return tranInternalCode;
 }

 public void setTranInternalCode(String tranInternalCode) {
  this.tranInternalCode = tranInternalCode;
 }

 public String getTranFechaHora() {
  return tranFechaHora;
 }

 public void setTranFechaHora(String tranFechaHora) {
  this.tranFechaHora = tranFechaHora;
 }

 public String getTranIdCanal() {
  return tranIdCanal;
 }

 public void setTranIdCanal(String tranIdCanal) {
  this.tranIdCanal = tranIdCanal;
 }

 public String getTranNumeroSucursal() {
  return tranNumeroSucursal;
 }

 public void setTranNumeroSucursal(String tranNumeroSucursal) {
  this.tranNumeroSucursal = tranNumeroSucursal;
 }

 public int getTranPrioridad() {
  return tranPrioridad;
 }

 public void setTranPrioridad(int tranPrioridad) {
  this.tranPrioridad = tranPrioridad;
 }

 public String getBodyIdTransaccion() {
  return bodyIdTransaccion;
 }

 public void setBodyIdTransaccion(String bodyIdTransaccion) {
  this.bodyIdTransaccion = bodyIdTransaccion;
 }

 public String getBodyDatCabNegRutOperadora() {
  return bodyDatCabNegRutOperadora;
 }

 public void setBodyDatCabNegRutOperadora(String bodyDatCabNegRutOperadora) {
  this.bodyDatCabNegRutOperadora = bodyDatCabNegRutOperadora;
 }

 public String getBodyDatCabNegCajero() {
  return bodyDatCabNegCajero;
 }

 public void setBodyDatCabNegCajero(String bodyDatCabNegCajero) {
  this.bodyDatCabNegCajero = bodyDatCabNegCajero;
 }

 public String getBodyDatCabNegBancoDestino() {
  return bodyDatCabNegBancoDestino;
 }

 public void setBodyDatCabNegBancoDestino(String bodyDatCabNegBancoDestino) {
  this.bodyDatCabNegBancoDestino = bodyDatCabNegBancoDestino;
 }

 public String getBodyDatCabNegBancoOrigen() {
  return bodyDatCabNegBancoOrigen;
 }

 public void setBodyDatCabNegBancoOrigen(String bodyDatCabNegBancoOrigen) {
  this.bodyDatCabNegBancoOrigen = bodyDatCabNegBancoOrigen;
 }

 public String getBodyDatCabNegRutCliente() {
  return bodyDatCabNegRutCliente;
 }

 public void setBodyDatCabNegRutCliente(String bodyDatCabNegRutCliente) {
  this.bodyDatCabNegRutCliente = bodyDatCabNegRutCliente;
 }

 public String getBodyDatCabNegOficinaOrigenTx() {
  return bodyDatCabNegOficinaOrigenTx;
 }

 public void setBodyDatCabNegOficinaOrigenTx(String bodyDatCabNegOficinaOrigenTx) {
  this.bodyDatCabNegOficinaOrigenTx = bodyDatCabNegOficinaOrigenTx;
 }

 public String getBodyDatCabNegCuiOrigenTx() {
  return bodyDatCabNegCuiOrigenTx;
 }

 public void setBodyDatCabNegCuiOrigenTx(String bodyDatCabNegCuiOrigenTx) {
  this.bodyDatCabNegCuiOrigenTx = bodyDatCabNegCuiOrigenTx;
 }

 public String getBodyDatCabNegOficinaOrigenExternaTx() {
  return bodyDatCabNegOficinaOrigenExternaTx;
 }

 public void setBodyDatCabNegOficinaOrigenExternaTx(String bodyDatCabNegOficinaOrigenExternaTx) {
  this.bodyDatCabNegOficinaOrigenExternaTx = bodyDatCabNegOficinaOrigenExternaTx;
 }

 public String getBodyDatCabNegCanalOrigenTx() {
  return bodyDatCabNegCanalOrigenTx;
 }

 public void setBodyDatCabNegCanalOrigenTx(String bodyDatCabNegCanalOrigenTx) {
  this.bodyDatCabNegCanalOrigenTx = bodyDatCabNegCanalOrigenTx;
 }

 public String getBodyDatCabNegRutSupervisor() {
  return bodyDatCabNegRutSupervisor;
 }

 public void setBodyDatCabNegRutSupervisor(String bodyDatCabNegRutSupervisor) {
  this.bodyDatCabNegRutSupervisor = bodyDatCabNegRutSupervisor;
 }

 public String getBodyDatCabNegSupervisor() {
  return bodyDatCabNegSupervisor;
 }

 public void setBodyDatCabNegSupervisor(String bodyDatCabNegSupervisor) {
  this.bodyDatCabNegSupervisor = bodyDatCabNegSupervisor;
 }

 public String getBodyDatCabNegFechaContable() {
  return bodyDatCabNegFechaContable;
 }

 public void setBodyDatCabNegFechaContable(String bodyDatCabNegFechaContable) {
  this.bodyDatCabNegFechaContable = bodyDatCabNegFechaContable;
 }

 public String getBodyDatCabNegFechaHoraCorrienteTx() {
  return bodyDatCabNegFechaHoraCorrienteTx;
 }

 public void setBodyDatCabNegFechaHoraCorrienteTx(String bodyDatCabNegFechaHoraCorrienteTx) {
  this.bodyDatCabNegFechaHoraCorrienteTx = bodyDatCabNegFechaHoraCorrienteTx;
 }

 public String getBodyDatCabNegHorario() {
  return bodyDatCabNegHorario;
 }

 public void setBodyDatCabNegHorario(String bodyDatCabNegHorario) {
  this.bodyDatCabNegHorario = bodyDatCabNegHorario;
 }

 public String getBodyDatCabNegTxId() {
  return bodyDatCabNegTxId;
 }

 public void setBodyDatCabNegTxId(String bodyDatCabNegTxId) {
  this.bodyDatCabNegTxId = bodyDatCabNegTxId;
 }

 public String getBodyDatCabNegTxIdParaReversar() {
  return bodyDatCabNegTxIdParaReversar;
 }

 public void setBodyDatCabNegTxIdParaReversar(String bodyDatCabNegTxIdParaReversar) {
  this.bodyDatCabNegTxIdParaReversar = bodyDatCabNegTxIdParaReversar;
 }

 public String getBodyDatNegCuenta() {
  return bodyDatNegCuenta;
 }

 public void setBodyDatNegCuenta(String bodyDatNegCuenta) {
  this.bodyDatNegCuenta = bodyDatNegCuenta;
 }

 public String getBodyDatNegMoneda() {
  return bodyDatNegMoneda;
 }

 public void setBodyDatNegMoneda(String bodyDatNegMoneda) {
  this.bodyDatNegMoneda = bodyDatNegMoneda;
 }

 public String getBodyDatNegMonto() {
  return bodyDatNegMonto;
 }

 public void setBodyDatNegMonto(String bodyDatNegMonto) {
  this.bodyDatNegMonto = bodyDatNegMonto;
 }

 public String getBodyDatNegNumeroBoleta() {
  return bodyDatNegNumeroBoleta;
 }

 public void setBodyDatNegNumeroBoleta(String bodyDatNegNumeroBoleta) {
  this.bodyDatNegNumeroBoleta = bodyDatNegNumeroBoleta;
 }

 public String getBodyDatNegCodigoProductoFC() {
  return bodyDatNegCodigoProductoFC;
 }

 public void setBodyDatNegCodigoProductoFC(String bodyDatNegCodigoProductoFC) {
  this.bodyDatNegCodigoProductoFC = bodyDatNegCodigoProductoFC;
 }

 public String getBodyDatNegCodigoTransaccionFC() {
  return bodyDatNegCodigoTransaccionFC;
 }

 public void setBodyDatNegCodigoTransaccionFC(String bodyDatNegCodigoTransaccionFC) {
  this.bodyDatNegCodigoTransaccionFC = bodyDatNegCodigoTransaccionFC;
 }

 public String getBodyDatNegCodigoExtendidoFC() {
  return bodyDatNegCodigoExtendidoFC;
 }

 public void setBodyDatNegCodigoExtendidoFC(String bodyDatNegCodigoExtendidoFC) {
  this.bodyDatNegCodigoExtendidoFC = bodyDatNegCodigoExtendidoFC;
 }

 public String getBodyDatNegLlaveLibretaAhorro() {
  return bodyDatNegLlaveLibretaAhorro;
 }

 public void setBodyDatNegLlaveLibretaAhorro(String bodyDatNegLlaveLibretaAhorro) {
  this.bodyDatNegLlaveLibretaAhorro = bodyDatNegLlaveLibretaAhorro;
 }

 public String getBodyDatNegSaldoLibreta() {
  return bodyDatNegSaldoLibreta;
 }

 public void setBodyDatNegSaldoLibreta(String bodyDatNegSaldoLibreta) {
  this.bodyDatNegSaldoLibreta = bodyDatNegSaldoLibreta;
 }

 public String getBodyDatNegFlagRespuesta() {
  return bodyDatNegFlagRespuesta;
 }

 public void setBodyDatNegFlagRespuesta(String bodyDatNegFlagRespuesta) {
  this.bodyDatNegFlagRespuesta = bodyDatNegFlagRespuesta;
 }

 public String getBodyListaDatCarNombreCampo() {
  return bodyListaDatCarNombreCampo;
 }

 public void setBodyListaDatCarNombreCampo(String bodyListaDatCarNombreCampo) {
  this.bodyListaDatCarNombreCampo = bodyListaDatCarNombreCampo;
 }

 public String getBodyListaDatCarValorCampo() {
  return bodyListaDatCarValorCampo;
 }

 public void setBodyListaDatCarValorCampo(String bodyListaDatCarValorCampo) {
  this.bodyListaDatCarValorCampo = bodyListaDatCarValorCampo;
 }
 
}
