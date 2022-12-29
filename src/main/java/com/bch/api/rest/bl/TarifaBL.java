package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.TarifaDAL;
import com.bch.api.rest.dto.TarifaDTO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de negocio encargada de operar las tarifas
 * @author 160k
 *
 */
@Component
public class TarifaBL {

 private static final Logger LOGGER = Logger.getLogger(TarifaBL.class);
 
 @Autowired
 private TarifaDAL tarDAL;
 
 /**
  * Lista de tarifas
  * @return lista de tarifas
  */
 public List<TarifaDTO> listarTarifas(int codigoEmisor) throws SQLException 
 {
	 LOGGER.debug("BL listar tarifas");
	 List<TarifaDTO> lista = new ArrayList<TarifaDTO>();
	 lista = tarDAL.listarTarifas(codigoEmisor);
	 return lista;
 }
 
 /**
  * Obtiene los registros formateados de las tarifas de emisores desde la BD para el archivo de interfaz
  * @return lista de registros
  */
 public String[] obtenerRegistrosFormateadosInterfazArchivoTarifas(LocalDate fechaIni, LocalDate fechaFin)
 {
	  return tarDAL.obtenerRegistrosFormateadosInterfazArchivoTarifas(fechaIni, fechaFin);
 }
}
