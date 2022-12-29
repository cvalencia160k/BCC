package com.bch.api.rest.enums;

/**
 * Estados de transacciones permitidos
 * @author 160k
 *
 */
public enum EstadoTransaccion { 
 
   RECIBIDA("I")
 , PROCESADA("P")
 , RECHAZADA("R")
 , ENVIADA("E")
 , PENDIENTE("N");
 
 private final String value; 
 private EstadoTransaccion(String val) { this.value = val; }
 public String getValue() { return this.value; }
}