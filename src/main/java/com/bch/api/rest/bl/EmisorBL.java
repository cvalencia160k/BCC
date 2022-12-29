package com.bch.api.rest.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.EmisorDAL;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseEmisor;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.utils.FuncionesGenerales;

import java.sql.SQLException;

/**
 * Capa de negocio encargada de operar las transacciones
 * @author 160k
 *
 */
@Component
public class EmisorBL {
 
 @Autowired
 private EmisorDAL emiDAL;
 
 /**
  * Verifica si existe un emisor por su codigo
  * @return
  * @throws SQLException 
  */
 public boolean existeEmisor(int codEmisor) throws SQLException 
 {
  return emiDAL.existeEmisor(codEmisor);
 }
 
 /**
  * Obtiene un emisor por su codigo
  * @return
  * @throws SQLException 
  */
 public Emisor obtenerEmisor(int codEmisor) throws SQLException
 {
  return emiDAL.obtenerEmisor(codEmisor);
 }
 
 /**
  * Obtiene lista de emisores segúne estado
  * @param estado_emisor
  * @return lista emisores
  * @throws SQLException
  */
 public ResponseEmisor obtenerEmisores(int estado_emisor) throws SQLException 
 {
   return emiDAL.obtenerEmisores(estado_emisor);
 }
 
 /**
  * Ingresa un nuevo emisor
  * @param emi dato del emisor
  * @return objeto response
  * @throws SQLException
  */
 public ResponseDTO ingresarEmisor(Emisor emi) throws SQLException
 {
	 //--- Safe string XSS
     emi.setRutEmisor(FuncionesGenerales.cleanXSS(emi.getRutEmisor()));
     emi.setNombreEmisor(FuncionesGenerales.cleanXSS(emi.getNombreEmisor()));
    
    
     //--- Fin Safe string XSS
	   
	 return emiDAL.ingresarEmisor(emi);
 }
 
 /**
  * Actualizar un emisor
  * @param emi
  * @return
  */
 public ResponseDTO updateEmisor(Emisor emi) 
 {
	 //--- Safe string XSS
     emi.setRutEmisor(FuncionesGenerales.cleanXSS(emi.getRutEmisor()));
     emi.setNombreEmisor(FuncionesGenerales.cleanXSS(emi.getNombreEmisor()));
 
     //--- Fin Safe string XSS
	   
	 return emiDAL.updateEmisor(emi);
 }
 
 /**
  * Cambia el estado de un emisor
  * @param codigo_emisor codigo del emisor
  * @param estado_emisor estado nuevo
  * @return
  * @throws SQLException
  */
 public ResponseDTO cambioEstadoEmisor(int codigo_emisor, int estado_emisor) throws SQLException
 {
	 return emiDAL.cambioEstadoEmisor(codigo_emisor, estado_emisor);
 }
}