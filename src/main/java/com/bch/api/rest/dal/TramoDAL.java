package com.bch.api.rest.dal;

import java.sql.SQLException;
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

import com.bch.api.rest.dto.ResponseDTO;
import com.bch.api.rest.dto.ResponseTramo;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.entities.Tramo;

/**
 * Capa DAL para el EMISOR
 * @author 160k
 *
 */
@Repository
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class TramoDAL{


 @Value("${nombre-persistencia}")
 private String nombrePersistencia;
 
 private EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombrePersistencia);
 
 private EntityManager em;
 
 
 private static final Logger LOGGER = Logger.getLogger(TramoDAL.class);


 /***********************************************************
  * Nombre funcion: existeTramoEmisor.......................*
  * Action: Existe tramo en emisores en la base de datos....*
  * Inp:....................................................* 
  * Out::true o false.......................................*
  * @throws SQLException ...................................*
  **********************************************************/
 public boolean existeTramoEmisor(int idTramo) throws SQLException 
 {
  boolean existe = false;
  
  LOGGER.debug("existeTramoEmisor > idTramo: '"+idTramo+"'");
  
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Tramo.sp_Tramo_ExisteUsoEmisor")
        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN).setParameter(1, idTramo)
        .registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);
       
       query.execute();
       
       String existeStr = query.getOutputParameterValue(2).toString();
       LOGGER.debug("tramo > existen tramo en emisores: '"+existeStr+"'");
       
       em.close();
  
       String result = "1";
       
       if(existeStr.equals(result)) 
    {
        existe = true;
    }
    else 
    {
     existe = false;
    }
       
       LOGGER.debug("tramo > existen tramo en emisores: '"+existe+"'");
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error en existeTramoEmisor: '"+ex);
   existe = true;
  }finally {
	  em.close();
  }
       
  return existe;
 }
 
 /**
  * Obtener Tramo por idTramo
  * @return
  * @throws SQLException 
  */
 public ResponseTramo obtenerTramo(int idTramo) throws SQLException 
 {
  LOGGER.debug("obtenerTramo > idTramo: '"+idTramo+"'");
  List<Tramo> tramos = new ArrayList<Tramo>();
  ResponseTramo response = new ResponseTramo();
  Tramo tram = new Tramo();
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Tramo.sp_Tramo_Obtener")
        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN).setParameter(1, idTramo)
        .registerStoredProcedureParameter(2, Emisor.class, ParameterMode.REF_CURSOR);
       
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listTramo = query.getResultList();
   	  
       for(Object[] result : listTramo) 
       {   	   
 
    	   tram.setIdTramo(Integer.parseInt(result[0].toString()));
    	   tram.setNombre(result[1].toString());
    	   tram.setMinimo(Integer.parseInt(result[3].toString()));
    	   tram.setMaximo(Integer.parseInt(result[2].toString()));
    	   tram.setTarifa(Float.parseFloat(result[4].toString()));   
    	   
    	   tramos.add(tram);            
       }
       
       em.close();
  
       LOGGER.debug("tramo > idTramo: '"+tram.getIdTramo()+"'");
       LOGGER.debug("tramo > nombre: '"+tram.getNombre()+"'");
       
       response.setMessage("se han obtenido el tramo " + tram.getNombre());
       response.setStatusCode(200);
       response.setTramos(tramos);
  }
  catch(Exception ex) 
  {
	  tram = null;
	  LOGGER.error("Error al obtener tramo: '"+ex);
	   response.setMessage("Error al obtener tramo: '"+ex);
	   response.setStatusCode(300);
	   return response;
  
  }finally {
	  em.close();
  }
       
  return response;
 }

 /**************************************************
  * Nombre funcion: ingresarTramo.................*
  * Action: Ingresa un nuevo emisor................*
  * Inp:@Emisor....................................* 
  * Out::resultado SP."pkg_Tramo.sp_Tramo_Ins"...*
  *************************************************/
 public ResponseDTO ingresarTramo(Tramo tram) 
 {

  String nombreSP = "pkg_Tramo.sp_Tramo_Ins";
  ResponseDTO responseE = new ResponseDTO();
  String codigo = "";
  String mensaje = "";
  int status = 0;
  try
  {  
		  String nombreE = tram.getNombre();
		  int minimoE = tram.getMinimo();
		  int maximoE = tram.getMaximo();
		  float  tarifaE = tram.getTarifa();
	
		  em = emf.createEntityManager();
		  StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Emisor.class);
  
		  // Parámetros entrada
  
		  query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		  query.setParameter(1,nombreE.substring(0, (nombreE.length() > 50)?50:nombreE.length()));
   		  
		  query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
		  query.setParameter(2,minimoE);

		  query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
		  query.setParameter(3,maximoE);

		  query.registerStoredProcedureParameter(4, Float.class, ParameterMode.IN);
		  query.setParameter(4, tarifaE);
		  
		 
		  //Parámetros salida
		  query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.OUT);
		  
		  //Ejecución SP
   		  query.execute();
     
   		  em.close();
   		  codigo = "000";
   		  mensaje = "Tramo Creado Exitosanmente";
   	      status = 200;
   		  
   		  responseE.setMessage(mensaje);
   		  responseE.setCodigoOperacion(codigo);
   		  responseE.setStatusCode(status);
   		  
   		  return responseE;
	}
	catch(Exception ex) 
	{
 		 
		
		LOGGER.error("Error Insercion:" + ex);
		String error = "Error al ejecutar SP de Insercion: '"+nombreSP+"'";
		LOGGER.info(error);
		LOGGER.error(ex);

		 codigo = "030";
	      status = 300;
		
		responseE.setMessage(error);
   		responseE.setCodigoOperacion(codigo);
   		responseE.setStatusCode(status);
   		  
   		return responseE;
	}finally {
		  em.close();
	  }
 }
 
 /**********************************************************
  * Nombre funcion: updateTramo...........................*
  * Action: Actualiza tramo a la base de datos............*
  * Inp:@tram:Tramo......................................* 
  * Out::resultado SP."pkg_Transaccion.sp_Transaccion_Ins".*
  *********************************************************/
 public ResponseDTO updateTramo(Tramo tram) 
 {
  String nombreSP = "pkg_Tramo.sp_Tramo_Udp";
  
  ResponseDTO responseT = new ResponseDTO();

  try
  { 
	  int idTramoT = tram.getIdTramo();
	  String nombreT = tram.getNombre();
	  int minimoT = tram.getMinimo();
	  int maximoT = tram.getMaximo();
	  float  tarifaT = tram.getTarifa();

	  em = emf.createEntityManager();
	  StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Emisor.class);

	  // Parámetros entrada
	  query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	  query.setParameter(1,idTramoT);

	  query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	  query.setParameter(2,nombreT.substring(0, (nombreT.length() > 50)?50:nombreT.length()));

	  query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
	  query.setParameter(3,minimoT);

	  query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
	  query.setParameter(4,maximoT);

	  query.registerStoredProcedureParameter(5, Float.class, ParameterMode.IN);
	  query.setParameter(5, tarifaT);
	  	 
	  //Parámetros salida
	  query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.OUT);
   
      //Ejecución SP
      query.execute();
   
      em.close();
       
   	  responseT.setMessage("Tramo :" + tram.getNombre() + " actualizado correctamente");
	  responseT.setCodigoOperacion("000");
	  responseT.setStatusCode(200);
	  return responseT;
  }
  catch(Exception ex) 
  {
	  LOGGER.error("Error Actualizacion:" + ex);
	  String error = "Error al ejecutar SP de Actualizacion: '"+nombreSP+"'";
	  LOGGER.info(error);
	  LOGGER.error(ex);
   
	  responseT.setMessage(error);
	  responseT.setCodigoOperacion("030");
	  responseT.setStatusCode(300);
		  
	  return responseT;
  }finally {
	  em.close();
  }
 }
 
 /**********************************************************
  * Nombre funcion: deleteTramo.....................*
  * Action: elimina tramo de la base de datos.............*
  * Inp:@codigo_emisor:int.................................*  
  * Out::resultado SP."pkg_Emisor.sp_Emisor_CambioEstado"..*
  *********************************************************/
 public ResponseDTO deleteTramo(int id_tramo) 
 {
  String nombreSP = "pkg_Tramo.sp_Tramo_Del";
  ResponseDTO responseD = new ResponseDTO();
  try
  { 
	  if(!existeTramoEmisor(id_tramo))
	  {
	  
   em = emf.createEntityManager();
   StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Emisor.class);
    
   // Parámetros entrada
   query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
   query.setParameter(1, id_tramo);
   
   //Ejecución SP
   query.execute();
    
   em.close();
   
   responseD.setMessage("Tramo :" + id_tramo + " se elimino correctamente");
   responseD.setCodigoOperacion("000");
   responseD.setStatusCode(200);
   
	  }else {
		  String warningD = "Tramo no puede ser eliminado ya que esta siendo utilizado en algunos emisores";

		  LOGGER.warn(warningD);
		  
		  responseD.setMessage(warningD);
   		  responseD.setCodigoOperacion("010");
   		  responseD.setStatusCode(100);
   		  
   		  return responseD;
	  }
  }
  
  catch(Exception ex) 
  {
   LOGGER.error("Error eliminacion:" + ex);
   String error = "Error al ejecutar SP de eliminacion: '"+nombreSP+"'";
   LOGGER.info(error);
   LOGGER.error(ex);
   
   responseD.setMessage(error);
	  responseD.setCodigoOperacion("030");
	  responseD.setStatusCode(300);
		  
	  return responseD;
  }finally {
	  em.close();
  }
  
  return responseD;
 }
 
 /**
  * Obtener Tramos
  * @return
  * @throws SQLException 
  */
 @SuppressWarnings("null")
public ResponseTramo obtenerTramos() throws SQLException 
 {
  LOGGER.debug("obtenerTramos ");
  List<Tramo> tramos = new ArrayList<Tramo>();
  ResponseTramo response = new ResponseTramo();
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Tramo.sp_Obtener_TodosTramos")
        .registerStoredProcedureParameter(1, Emisor.class, ParameterMode.REF_CURSOR);
       
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listTramo = query.getResultList();
       
       for(Object[] result : listTramo) 
       {   	   
    	   Tramo tram = new Tramo();
    	   tram.setIdTramo(Integer.parseInt(result[0].toString()));
    	   tram.setNombre(result[1].toString());
    	   tram.setMinimo(Integer.parseInt(result[3].toString()));
    	   tram.setMaximo(Integer.parseInt(result[2].toString()));
    	   tram.setTarifa(Float.parseFloat(result[4].toString()));   
    	   
    	   tramos.add(tram);
       }
       
       em.close();
  
       String mensaje = "se han obtenido " + tramos.size() + " tramos";
       LOGGER.debug(mensaje);
       
       response.setMessage(mensaje);
       response.setStatusCode(200);
       response.setTramos(tramos);
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener tramos: '"+ex);
   response.setMessage("Error al obtener tramos: '"+ex);
   response.setStatusCode(300);
   return response;
   
  }finally {
	  em.close();
  }
       
  return response;
 }
 
}