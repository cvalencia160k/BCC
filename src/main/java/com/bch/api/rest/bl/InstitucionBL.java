package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.InstitucionDAL;
import com.bch.api.rest.entities.Institucion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de negocio encargada de operar las transacciones
 * @author 160k
 *
 */
@Component
public class InstitucionBL {

 private static final Logger LOGGER = Logger.getLogger(InstitucionBL.class);
 
 @Autowired
 private InstitucionDAL insDAL;
 
 /**
  * Lista de instituciones
  * @return lista de instituciones
  */
 public List<Institucion> listarInstituciones() 
 {
	 List<Institucion> lista = new ArrayList<Institucion>();
	 try {
		 lista = insDAL.listarInstituciones();
	} catch (SQLException e) {
		LOGGER.debug("Error listarInstituciones: "+e);
	}
	 return lista;
 }
}
