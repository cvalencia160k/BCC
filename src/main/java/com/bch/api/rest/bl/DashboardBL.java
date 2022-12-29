package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bch.api.rest.dal.DashboardDAL;
import com.bch.api.rest.dto.CuantificadoresOpcMenuDTO;
import com.bch.api.rest.dto.DashboardDTO;
import com.bch.api.rest.utils.FuncionesGenerales;

import java.sql.SQLException;


/**
 * Capa de negocio encargada de generar el dashboard inicial
 * @author 160k
 *
 */
@Component
public class DashboardBL {

 private static final Logger LOGGER = Logger.getLogger(DashboardBL.class);
 
 @Autowired
 private DashboardDAL dashDAL;
 
 /**
  * Obtener data para el dashboard inicial
  * @return datos de resultado
  */
 public DashboardDTO obtenerDashboardInicial() 
 {
	 DashboardDTO dash = new DashboardDTO();
	 try {
		 int horasNoHabiles = FuncionesGenerales.horasHabiles();
		 dash = dashDAL.obtenerDashboardInicial(horasNoHabiles);
	} catch (SQLException e) {
		LOGGER.debug("Error obtenerDashboardInicial: "+e);
	}
	 return dash;
 }
 
 /**
  * Obtiene los cuantifiadores definidos para las opc de menú
  * @return
  */
 public CuantificadoresOpcMenuDTO cuantificadoresOpcMenu() 
 {
	 int horasNoHabiles = FuncionesGenerales.horasHabiles();
	 return dashDAL.cuantificadoresOpcMenu(horasNoHabiles);
 }
}
