package com.bch.api.rest.dal;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseSesion;
import com.bch.api.rest.entities.Sesion;


/**
 * Capa DAL para el EMISOR
 * @author 160k
 *
 */
@Repository
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class SesionDAL{


 @Value("${nombre-persistencia}")
 private String nombrePersistencia;
 
 private EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombrePersistencia);
 
 private EntityManager em;
 
 private static final Logger LOGGER = Logger.getLogger(SesionDAL.class);


 /**************************************************
  * Nombre funcion: ingresarAccionSession..........*
  * Action: Ingresa un accion session..............*
  * Inp:@Sesion....................................* 
  * Out::resultado SP."pkg_sesion.sp_Sesion_Ins"...*
  *************************************************/
 public ResponseDTO ingresarAccionSession(Sesion sesion) 
 {

  String nombreSP = "pkg_sesion.sp_Sesion_Ins";
  ResponseDTO responseS = new ResponseDTO();

  try
  {  

		  String nombre = sesion.getNombre();
		  String accion = sesion.getAccion();
		  String codigoS = "";
		  String mensajeS = "";
		  int statusS = 0;
		  String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				  
		  em = emf.createEntityManager();
		  StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Sesion.class);
  
		  // Parámetros entrada
		  query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		  query.setParameter(1,nombre);
   
		  query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		  query.setParameter(2,accion);
		  
		  query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		  query.setParameter(3,fecha);
   
		  		 		  
		  //Ejecución SP
   		  query.execute();
     
   		  em.close();
   		  
   		  codigoS = "000";
  		  mensajeS = "Accion Session Creada Exitosanmente Codigo ";
  	      statusS = 200;
   		  
   		  responseS.setMessage(mensajeS);
   		  responseS.setCodigoOperacion(codigoS);
   		  responseS.setStatusCode(statusS);
   		  
   		  return responseS;
   		  
	}
	catch(Exception exS) 
	{
		LOGGER.error("Error :" + exS);
		String errorS = "Error al ejecutar SP: '"+nombreSP+"'";
		LOGGER.info(errorS);
		LOGGER.error(exS);

		responseS.setMessage(errorS);
   		responseS.setCodigoOperacion("030");
   		responseS.setStatusCode(300);
   		  
   		return responseS;
	}finally {
		  em.close();
	  }
 }
 
 
 /**
  * Obtener Emisor por Cod_Emisor
  * @return
  * @throws SQLException 
  */
 @SuppressWarnings("null")
public ResponseSesion obtenerSesiones(String fechaIni, String fechaFin) throws SQLException 
 {

  List<Sesion> sesiones = new ArrayList<Sesion>();
  ResponseSesion response = new ResponseSesion();
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_sesion.sp_Obtener_sesiones")
        .registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, fechaIni)
        .registerStoredProcedureParameter(2, String.class, ParameterMode.IN).setParameter(2, fechaFin)
        .registerStoredProcedureParameter(3, Sesion.class, ParameterMode.REF_CURSOR);
       
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listSesiones = query.getResultList();
       
       for(Object[] result : listSesiones) 
       {
    	 Sesion ses = new Sesion();
    	 
    	 
    	 ses.setIdSesion(Integer.parseInt(result[0].toString()));
    	 ses.setAccion(result[1].toString());
    	 ses.setNombre(result[2].toString());
    	 ses.setfechaSesion(result[3].toString());
    	 
    	 sesiones.add(ses);
       }
       
       em.close();
  
       LOGGER.debug("se han obteneido" + sesiones.size() + " sesiones");
       
       response.setMessage("se han obteneido" + sesiones.size() + " emisores");
       response.setStatusCode(200);
       response.setSesiones(sesiones);
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener sesiones: '"+ex);
   response.setMessage("Error al obtener sesiones: '"+ex);
   response.setStatusCode(300);
   return response;
   
  }finally {
	  em.close();
  }
       
  return response;
 }
 
}
