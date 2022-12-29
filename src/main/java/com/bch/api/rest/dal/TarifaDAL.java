package com.bch.api.rest.dal;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import com.bch.api.rest.dto.TarifaDTO;
import com.bch.api.rest.entities.Emisor;

/**
 * Capa DAL para las tarifas
 * @author 160k
 *
 */
@Repository
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class TarifaDAL{


 @Value("${nombre-persistencia}")
 private String nombrePersistencia;
 
 private EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombrePersistencia);
 
 private EntityManager em;
  
 private static final Logger LOGGER = Logger.getLogger(TarifaDAL.class);

/**
  * Obtener Tramos
  * @return
  * @throws SQLException 
  */
 public List<TarifaDTO> listarTarifas(int codigoEmisor) 
 {
  List<TarifaDTO> lista = new ArrayList<TarifaDTO>();
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Tarifa.sp_TarifasEmisor");
       
       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
       query.setParameter(1, codigoEmisor);
       
       query.registerStoredProcedureParameter(2, Emisor.class, ParameterMode.REF_CURSOR);
       
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listRes = query.getResultList();
       
       for(Object[] result : listRes) 
       {   	   
    	   TarifaDTO tar = new TarifaDTO();
    	   tar.setIdTarifa(Integer.parseInt(result[0].toString()));
    	   tar.setCodigoEmisor(result[1].toString());
    	   tar.setNombreEmisor(result[2].toString());
    	   tar.setRutEmisor(result[3].toString());
    	   tar.setCuentaTarifa(Long.parseLong(result[4].toString()));
    	   tar.setTarifaLBTR(Float.parseFloat(result[5].toString()));
    	   tar.setTarifaTramo(Float.parseFloat(result[6].toString()));   
    	   
    	   lista.add(tar);
       }
       
      
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener tarifas: '"+ex);
  }finally{
	  em.close();
  }
       
  return lista;
 }
 
 @SuppressWarnings("unused")
 public String[] obtenerRegistrosFormateadosInterfazArchivoTarifas(LocalDate fechaIniT, LocalDate fechaFinT)
 {
	
	 try
	  {
	       em = emf.createEntityManager();
	       StoredProcedureQuery query = 
	    		   em.createStoredProcedureQuery("pkg_Tarifa.sp_obtenerRegistrosFormateadosInterfazArchivoTarifas");
	        
	       query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
	       query.setParameter(1, fechaIniT.toString());
	       
	       query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	       query.setParameter(2, fechaFinT.toString());
	       
	       query.registerStoredProcedureParameter(3, String.class, ParameterMode.REF_CURSOR);
			
	       query.execute();
	       
	       @SuppressWarnings("unchecked")
	       List<String> listResTD = query.getResultList();
	       String[] listaTD = new String[listResTD.size()];
	       int iT = 0;
	       for(String resultTD : listResTD) 
	       {
	    	   listaTD[iT] = resultTD;
	    	   iT++;
	       }
	       return listaTD;
	  }
	  catch(Exception ex) 
	  {
	   LOGGER.error("Error al obtener Registros Formateados Interfaz Archivo Tarifas: '"+ex);
	   String[] listResTD = {"Error al obtener Registros Formateados Interfaz Archivo Tarifas: '"+ex};
	   return  listResTD;

	  }finally {
		  em.close();
	  }
 }
	
 
}