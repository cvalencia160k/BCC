package com.bch.api.rest.dto.ctrm;

import java.util.List;


/******************************************************************************************
 * Nombre class: ResponseControlM........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
public class ResponseControlM {

 private int statusCode;
 private List<TransaccionControlM> listaTrxRechazadas;
 
 public int getStatusCode() {
  return statusCode;
 }
 public void setStatusCode(int statusCode) {
  this.statusCode = statusCode;
 }
 public List<TransaccionControlM> getListaTrxRechazadas() {
  return listaTrxRechazadas;
 }
 public void setListaTrxRechazadas(List<TransaccionControlM> listaTrxRechazadas) {
  this.listaTrxRechazadas = listaTrxRechazadas;
 }
 
}

