package com.bch.api.rest.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.SesionDAL;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseSesion;
import com.bch.api.rest.entities.Sesion;

import java.sql.SQLException;

/**
 * Capa de negocio encargada de operar las transacciones
 * @author 160k
 *
 */
@Component
public class SesionBL {
 
 @Autowired
 private SesionDAL sesDAL;
 
 
 
 /**
  * Obtiene lista de emisores segúne estado
  * @param estado_emisor
  * @return lista emisores
  * @throws SQLException
  */
 public ResponseSesion obtenerSesiones(String fechaIni, String fechaFin) throws SQLException 
 {
   return sesDAL.obtenerSesiones(fechaIni, fechaFin);
 }
 
 /**
  * Ingresa un nuevo emisor
  * @param emi dato del emisor
  * @return objeto response
  * @throws SQLException
  */
 public ResponseDTO ingresarSesion(Sesion ses) throws SQLException
 {
	 return sesDAL.ingresarAccionSession(ses);
 }
 
 
}