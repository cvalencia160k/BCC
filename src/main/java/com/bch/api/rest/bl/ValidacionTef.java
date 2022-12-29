package com.bch.api.rest.bl;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.utils.FuncionesGenerales;

public class ValidacionTef {

 private static final Logger LOGGER = Logger.getLogger(ValidacionTef.class);
 
 private TransaccionBL tranBL;
 private EmisorBL emiBL;
 
 private String tipoEvento;
 private String strCodigosOperacion;
 private String respValidacion;
 private String codigoOperacionValidacion; //-- Trx con error en campos de entrada
 private String codigoOperacionIteracion;
 private String codigoValidacionErrorExistencia; //--- Codigo CMF No identificado

 /**
  * Constructor de la clase
  */
 public ValidacionTef(TransaccionBL tranBL, EmisorBL emiBL) 
 {
  this.tranBL = tranBL;
  this.emiBL = emiBL;
  
  this.tipoEvento = "";
  this.strCodigosOperacion = "";
  this.respValidacion = "";
  this.codigoOperacionValidacion = "010"; //-- Trx con error en campos de entrada
  this.codigoOperacionIteracion = "000";
  this.codigoValidacionErrorExistencia = "011"; //--- Codigo CMF No identificado
 }
 
 public TransaccionBL getTranBL() {
  return tranBL;
 }

 public EmisorBL getEmiBL() {
  return emiBL;
 }

 public String getTipoEvento() {
  return tipoEvento;
 }

 public void setTipoEvento(String tipoEvento) {
  this.tipoEvento = tipoEvento;
 }

 public String getStrCodigosOperacion() {
  return strCodigosOperacion;
 }

 public void setStrCodigosOperacion(String strCodigosOperacion) {
  this.strCodigosOperacion = strCodigosOperacion;
 }

 public String getRespValidacion() {
  return respValidacion;
 }

 public void setRespValidacion(String respValidacion) {
  this.respValidacion = respValidacion;
 }

 public String getCodigoOperacionValidacion() {
  return codigoOperacionValidacion;
 }

 public void setCodigoOperacionValidacion(String codigoOperacionValidacion) {
  this.codigoOperacionValidacion = codigoOperacionValidacion;
 }

 public String getCodigoOperacionIteracion() {
  return codigoOperacionIteracion;
 }

 public void setCodigoOperacionIteracion(String codigoOperacionIteracion) {
  this.codigoOperacionIteracion = codigoOperacionIteracion;
 }

 public String getCodigoValidacionErrorExistencia() {
  return codigoValidacionErrorExistencia;
 }

 public void setCodigoValidacionErrorExistencia(String codigoValidacionErrorExistencia) {
  this.codigoValidacionErrorExistencia = codigoValidacionErrorExistencia;
 }

 /**
  * Validar codigo institución (númerico, 4 digitos) y existencia de Institución
  */
 public void validacionCodigoInstitucion(Transaccion tranBD) 
 {
  //--- 1. Validar codigo institución (númerico, 4 digitos)
  String codigoInstitucion = tranBD.getCodInstitucion();
  if(!StringUtils.isNumeric(codigoInstitucion) || codigoInstitucion.length() != 4) 
  {
   codigoOperacionIteracion = codigoOperacionValidacion;
     
   String mensaje = "Código institución '"+codigoInstitucion+"' debe ser un número de 4 cifras";
     
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, Integer.parseInt(codigoOperacionIteracion), mensaje);
     
   strCodigosOperacion += codigoOperacionIteracion+"|";
   respValidacion += mensaje+"\n";
  }
  else 
  {
   //---- Validar existencia del codigo de institución en la BD
   boolean existeEmisor = false;
   try 
   {
    existeEmisor = emiBL.existeEmisor(Integer.parseInt(codigoInstitucion));
   } 
   catch (Exception e) 
   {
    LOGGER.info(e);
    LOGGER.debug("Error al validar la existencia del codigo instiución: "+e.getMessage());
    
    existeEmisor = false;
   }

   if(!existeEmisor) 
   {
    codigoOperacionIteracion = codigoValidacionErrorExistencia; //--- Codigo CMF No identificado
    String mensaje = "Código institución '"+codigoInstitucion+"' no está registrado";
      
    tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, Integer.parseInt(codigoOperacionIteracion), mensaje);
      
    strCodigosOperacion += codigoOperacionIteracion+"|";
    respValidacion += mensaje+"\n";
   }
  }
 }
 
 /**
  * Validar fecha trnsacción (fecha válida yyyy-MM-dd HH:mm:ss)
  */
 public void validarFechaTrx(Transaccion tranBD) 
 {
  String fechaTrx = tranBD.getFechaTransaccion();
  Date fecha = FuncionesGenerales.parseDate(fechaTrx);
  if(fecha == null) 
  {
   codigoOperacionIteracion = codigoOperacionValidacion;
   
   String mensaje = "Fecha de transacción '"+fechaTrx+"' debe ser una fecha válida yyyy-MM-dd HH:mm:ss";
   
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, Integer.parseInt(codigoOperacionIteracion), mensaje);
   
   strCodigosOperacion += codigoOperacionIteracion+"|";
   respValidacion += mensaje+"\n";
  }
 }
 
 /**
  * Validar codigo trnsacción (texto hexadecimal, letras y números)
  * @param tranBD
  */
 public void validarCodigoTrx(Transaccion tranBD)
 {
  String codigoTrx = tranBD.getCodigoTransaccion();
  boolean esAlfaNumerico = FuncionesGenerales.esAlfaNumericoGuion(codigoTrx);
  if(!esAlfaNumerico)
  {
   codigoOperacionIteracion = codigoOperacionValidacion;
   
   String mensaje = "Código de transacción '"+codigoTrx+"' debe ser texto alfa-numérico";
   
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento,Integer.parseInt(codigoOperacionIteracion), mensaje);
   
   strCodigosOperacion += codigoOperacionIteracion+"|";
   respValidacion += mensaje+"\n";
  }
 }
 
 /**
  * Validar monto de transacción (numérico, hasta 14 cifras)
  * @param tranBD
  */
 public void validarMontoTrx(Transaccion tranBD)
 {
  String montoEnviado = tranBD.getMontoTotalEnviadoCiclo();
  if(!StringUtils.isNumeric(montoEnviado) || montoEnviado.length() != 14) 
  {
   codigoOperacionIteracion = codigoOperacionValidacion;
   
   String mensaje = "Monto Total Enviado '"+montoEnviado+"' debe ser un número de 14 cifras";
     
   tranBL.generarHistoriaTransaccion(tranBD, tipoEvento, Integer.parseInt(codigoOperacionIteracion), mensaje);
   
   strCodigosOperacion += codigoOperacionIteracion+"|";
   respValidacion += mensaje+"\n";
  }
 }
}
