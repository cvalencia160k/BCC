package com.bch.api.rest.factory;

import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.EmisorDAL;
import com.bch.api.rest.dal.TransaccionDAL;

@Component
public class FactoryDAL {

 private EmisorDAL emiDAL;
 private TransaccionDAL tranDAL;
 
 public FactoryDAL(EmisorDAL emiDAL, TransaccionDAL tranDAL) 
 {
  this.emiDAL = emiDAL;
  this.tranDAL = tranDAL;
 }

 public EmisorDAL getEmiDAL() {
  return emiDAL;
 }

 public void setEmiDAL(EmisorDAL emiDAL) {
  this.emiDAL = emiDAL;
 }

 public TransaccionDAL getTranDAL() {
  return tranDAL;
 }

 public void setTranDAL(TransaccionDAL tranDAL) {
  this.tranDAL = tranDAL;
 }
 
}
