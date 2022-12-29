package com.bch.api.rest.dal;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.bch.api.rest.dto.CuantificadoresOpcMenuDTO;
import com.bch.api.rest.dto.DashboardDTO;
import com.bch.api.rest.entities.Institucion;

/**
 * Capa DAL para los Reportes
 * @author 160k
 *
 */
@Repository
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class DashboardDAL{

@Value("${nombre-persistencia}")
 private String nombrePersistencia;
 
 private EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombrePersistencia);
 
 private EntityManager em;

 private static final Logger LOGGER = Logger.getLogger(DashboardDAL.class);
 
 /**
  * Obtiene los datos para el dashboard inicial
  * @return datos para el dashboard inicial
  * @throws SQLException exception de SQL
  */
 public DashboardDTO obtenerDashboardInicial(int horasNoHabilesD) throws SQLException 
 {
  LOGGER.debug("DAL obtenerDashboardInicial()");
  
  DashboardDTO dash = new DashboardDTO();
  
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Dashboard.sp_DashboardInicial");
       
       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
       query.setParameter(1, horasNoHabilesD);
       
       query.registerStoredProcedureParameter(2, Institucion.class, ParameterMode.REF_CURSOR);
		 
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listResD = query.getResultList();
       
       for(Object[] resultD : listResD) 
       {
    	   //--- Mes
    	   dash.setTotalTrxCCAAceptadasMes(Integer.parseInt(resultD[0].toString()));
    	   dash.setTotalTrxCCARechazadasMes(Integer.parseInt(resultD[1].toString()));
    	   dash.setTotalTrxCompenAceptadasMes(Integer.parseInt(resultD[2].toString()));
    	   dash.setTotalTrxCompenRehazadasMes(Integer.parseInt(resultD[3].toString()));
    	   
    	   //--- Día
    	   dash.setTotalTrxCCAAceptadasDia(Integer.parseInt(resultD[4].toString()));
    	   dash.setTotalTrxCCARechazadasDia(Integer.parseInt(resultD[5].toString()));
    	   dash.setTotalTrxCompenAceptadasDia(Integer.parseInt(resultD[6].toString()));
    	   dash.setTotalTrxCompenRehazadasDia(Integer.parseInt(resultD[7].toString()));
       }
       
       em.close();
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener obtenerDashboardInicial: '"+ex);
  }finally {
	  em.close();
  }
       
  return dash;
 }
 
 /**
  * Obtiene los cuantificadores para las opciojes de menu definidas
  * @return datos de cuantificadores de opc de menu
  * @throws SQLException exception de SQL
  */
 public CuantificadoresOpcMenuDTO cuantificadoresOpcMenu(int horasNoHabilesDa) 
 {
	 LOGGER.debug("DAL cuantificadoresOpcMenu()");
	  
	 CuantificadoresOpcMenuDTO cuantiDa = new CuantificadoresOpcMenuDTO();
	  
	  try
	  {
	       em = emf.createEntityManager();
	       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Dashboard.sp_CuantificadoresMenu");
	       
	       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		   query.setParameter(1,horasNoHabilesDa);
		   
		   query.registerStoredProcedureParameter(2, Institucion.class, ParameterMode.REF_CURSOR);
			 
	       query.execute();
	       
	       @SuppressWarnings("unchecked")
	       List<Object[]> listResDa = query.getResultList();
	       
	       for(Object[] resultDa : listResDa) 
	       {
	    	   cuantiDa.setCantidadTrxCCAAceptadas(Integer.parseInt(resultDa[0].toString()));
	    	   cuantiDa.setCantidadTrxCCARechazadas(Integer.parseInt(resultDa[1].toString()));
	    	   cuantiDa.setCantidadTrxCompen(Integer.parseInt(resultDa[2].toString()));
	    	   cuantiDa.setCantidadRepoCompen(Integer.parseInt(resultDa[3].toString()));
	       }
	       
	       em.close();
	  
	  }
	  catch(Exception ex) 
	  {
	   LOGGER.error("Error al obtener cuantificadoresOpcMenu: '"+ex);
	  }finally {
		  em.close();
	  }
	       
	  return cuantiDa;
	 }
}