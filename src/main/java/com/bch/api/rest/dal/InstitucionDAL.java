package com.bch.api.rest.dal;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.bch.api.rest.entities.Institucion;

/**
 * Capa DAL para los Reportes
 * @author 160k
 *
 */
@Repository
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class InstitucionDAL{

@Value("${nombre-persistencia}")
 private String nombrePersistencia;
 
 private EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombrePersistencia);
 
 private EntityManager em;

 private static final Logger LOGGER = Logger.getLogger(InstitucionDAL.class);
 
 
 /***********************************************************
  * Nombre funcion: existeInstitucion............................*
  * Action: Existe la Institucion en la base de datos............*
  * Inp:....................................................* 
  * Out::true o false.......................................
  * @throws SQLException *
  **********************************************************/
 public boolean existeInstitucion(int codigoInstitucion) throws SQLException 
 {
  boolean existeI = false;
  
  LOGGER.debug("existeInstitucion > codigoInstitucion: '"+codigoInstitucion+"'");

  try
  {
	  em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Institucion.sp_Institucion_Existe")
        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN).setParameter(1, codigoInstitucion)
        .registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);
       
       query.execute();
       
       String existeStrI = query.getOutputParameterValue(2).toString();
       LOGGER.debug("institucion > existeStr: '"+existeStrI+"'");
       
  
       String resultCodeDuplicadaI = "1";
       
       if(existeStrI.equals(resultCodeDuplicadaI)) 
    {
        existeI = true;
    }
    else 
    {
     existeI = false;
    }
       
       LOGGER.debug("emisor > existe: '"+existeI+"'");
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error en existeInstitucion: '"+ex);
   existeI = true;
  }finally {
	  em.close();
  }
       
  return existeI;
 }
 
 /******************************************************************************************
  * Nombre funcion: listarInstituciones....................................................*
  * Action: Obtener lista de instituciones.................................................*
  * Out::resultado pkg_Institucion.sp_ConsultarInstituciones - lista de instituciones......*
  *****************************************************************************************/
 public List<Institucion> listarInstituciones() throws SQLException 
 {
  LOGGER.debug("DAL listarInstituciones()");
  
  List<Institucion> lista = new ArrayList<Institucion>();
  
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Institucion.sp_ConsultarInstituciones");
       
	   query.registerStoredProcedureParameter(1, Institucion.class, ParameterMode.REF_CURSOR);
		 
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listRes = query.getResultList();
       
       for(Object[] result : listRes) 
       {
           Institucion ins = new Institucion();
           ins.setCodigoInstitucion(Integer.parseInt(result[0].toString()));
           ins.setNombreInstitucion(result[1].toString());
           
           lista.add(ins);
       }
       
       em.close();
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener listar Instituciones: '"+ex);
   return lista;
  }finally {
	  em.close();
  }
       
  return lista;
 }
 
}