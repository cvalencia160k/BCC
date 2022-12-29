package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.TramoDAL;
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseTramo;
import com.bch.api.rest.entities.Tramo;
import com.bch.api.rest.utils.FuncionesGenerales;
import java.sql.SQLException;

/**
 * Capa de negocio encargada de operar las tarifas
 * @author 160k
 *
 */
@Component
public class TramoBL {

 private static final Logger LOGGER = Logger.getLogger(TramoBL.class);
 
 @Autowired
 private TramoDAL tramDAL;
 
 /**
  * Ingresar nuevo tramo
  * @return respuesta del registro
  */
 public ResponseDTO ingresarTramo(Tramo tram) throws SQLException 
 {
	LOGGER.debug("BL ingresar tramo");

	//--- Respetar 10 caracteres del nombre de tramo
	tram.setNombre(FuncionesGenerales.limitarTamanoString(tram.getNombre(), 10));
		 
	//--- Clear XSS attack
	tram.setNombre(FuncionesGenerales.cleanXSS(tram.getNombre()));
	   
	return tramDAL.ingresarTramo(tram);
 }
 
 /**
  * Actualizar tramo
  * @return respuesta del registro
  */
 public ResponseDTO updateTramo(Tramo tram) throws SQLException 
 {
	 LOGGER.debug("BL actualizar tramo");

		//--- Respetar 10 caracteres del nombre de tramo
		tram.setNombre(FuncionesGenerales.limitarTamanoString(tram.getNombre(), 10));
			 
		//--- Clear XSS attack
		tram.setNombre(FuncionesGenerales.cleanXSS(tram.getNombre()));
		   
	 return tramDAL.updateTramo(tram);
 }
 
 /**
  * Obtener un tramo por su ID
  * @param idTramo
  * @return data del tramo
  * @throws SQLException
  */
 public ResponseTramo obtenerTramo(int idTramo) throws SQLException 
 {
	 LOGGER.debug("BL listar tramos");
	 return tramDAL.obtenerTramo(idTramo);
 }
 
 /**
  * Obtener lista de tramos
  * @return lista de los tramos
  * @throws SQLException
  */
 public ResponseTramo obtenerTramos() throws SQLException 
 {
	 LOGGER.debug("BL listar tramos");
	 return tramDAL.obtenerTramos();
 }
 
 /**
  * Eliminar un tramo
  * @param id_tramo
  * @return
  */
 public ResponseDTO deleteTramo(int idTramo)
 {
	 LOGGER.debug("BL eliminar tramo");
	 return tramDAL.deleteTramo(idTramo);
 }
}
