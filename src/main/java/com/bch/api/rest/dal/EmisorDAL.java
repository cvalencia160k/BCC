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
import com.bch.api.rest.dto.ResponseEmisor;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.entities.Tramo;

/**
 * Capa DAL para el EMISOR
 * @author 160k
 *
 */
@Repository
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class EmisorDAL{


 @Value("${nombre-persistencia}")
 private String nombrePersistencia;
 
 private EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombrePersistencia);
 
 private EntityManager em;
 
 private static final Logger LOGGER = Logger.getLogger(EmisorDAL.class);

 /***********************************************************
  * Nombre funcion: existeEmisor............................*
  * Action: Existe el emisor en la base de datos............*
  * Inp:....................................................* 
  * Out::true o false.......................................
  * @throws SQLException *
  **********************************************************/
 public boolean existeEmisor(int codigoEmisorE) throws SQLException 
 {
  boolean existeE = false;
  
  LOGGER.debug("existeEmisor > codigoEmisor: '"+codigoEmisorE+"'");

  try
  {
	  em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Emisor.sp_Emisor_Existe")
        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN).setParameter(1, codigoEmisorE)
        .registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);
       
       query.execute();
       
       String existeStrE = query.getOutputParameterValue(2).toString();
       LOGGER.debug("emisor > existeStr: '"+existeStrE+"'");
       
  
       String resultCodeDuplicadaE = "1";
       
       if(existeStrE.equals(resultCodeDuplicadaE)) 
    {
        existeE = true;
    }
    else 
    {
     existeE = false;
    }
       
       LOGGER.debug("emisor > existe: '"+existeE+"'");
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error en existeEmisor: '"+ex);
   existeE = true;
  }finally {
	  em.close();
  }
       
  return existeE;
 }
 
 /**
  * Obtener Emisor por Cod_Emisor
  * @return
  * @throws SQLException 
  */
 public Emisor obtenerEmisor(int codEmisor) throws SQLException 
 {
  LOGGER.debug("obtenerEmisor > codEmisor: '"+codEmisor+"'");
  Emisor emi = new Emisor();
  
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Emisor.sp_Emisor_Obtener")
        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN).setParameter(1, codEmisor)
        .registerStoredProcedureParameter(2, Emisor.class, ParameterMode.REF_CURSOR);
       
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listEmi = query.getResultList();
       
       for(Object[] result : listEmi) 
       {
    	   
    	LOGGER.debug(result[5].toString());
    	LOGGER.debug(result[6].toString());
        emi.setCodigoEmisor(Integer.parseInt(result[0].toString()));
        emi.setEstado(Integer.parseInt(result[1].toString()));
        emi.setIdTramo(Integer.parseInt(result[2].toString()));
        emi.setMontoMaximoTransaccion(result[3].toString());
        emi.setNombreEmisor(result[4].toString());
        emi.setNumeroCuentaCargo(Long.parseLong(result[5].toString()));
        emi.setNumeroCuentaComision(Long.parseLong(result[6].toString()));
        emi.setRutEmisor(result[7].toString());
        emi.setTarifaLBTR(Float.parseFloat(result[8].toString()));              
       }
        
       LOGGER.debug("emisor > codigoEmisor: '"+emi.getCodigoEmisor()+"'");
       LOGGER.debug("emisor > nombre: '"+emi.getNombreEmisor()+"'");
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener el emisor: '"+ex);
   emi = null;
  }finally {
	  em.close();
  }
       
  return emi;
 }

 /**************************************************
  * Nombre funcion: ingresarEmisor.................*
  * Action: Ingresa un nuevo emisor................*
  * Inp:@Emisor....................................* 
  * Out::resultado SP."pkg_emisor.sp_Emisor_Ins"...*
  *************************************************/
 public ResponseDTO ingresarEmisor(Emisor emsor) 
 {

  String nombreSP = "pkg_Emisor.sp_Emisor_Ins";
  ResponseDTO responseE = new ResponseDTO();
  String codigoE = "";
  String mensajeE = "";
  int statusE = 0;
  try
  {  
	  if(!existeEmisor(emsor.getCodigoEmisor()))
	  {
		  int codEmisorE = emsor.getCodigoEmisor();
		  String rutE = emsor.getRutEmisor();
		  String nombreE = emsor.getNombreEmisor();
		  int estadoE = emsor.getEstado();
		  Long numeroCuentaCargoE = emsor.getNumeroCuentaCargo();
		  String maximoMontoTransaccionE = emsor.getMontoMaximoTransaccion();
		  Long numeroCuentaComisionE = emsor.getNumeroCuentaComision();
		  float  tarifaLBTRE = emsor.getTarifaLBTR();
		  int idTramoE = emsor.getIdTramo();

		
		  em = emf.createEntityManager();
		  StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Emisor.class);
  
		  // Parámetros entrada
		  query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		  query.setParameter(1,codEmisorE);
   
		  query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		  query.setParameter(2,rutE.substring(0, (rutE.length() > 10)?10:rutE.length()));
   
		  query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		  query.setParameter(3, nombreE.substring(0, (nombreE.length() > 50)?50:nombreE.length()));
   
		  query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
		  query.setParameter(4,estadoE);

		  query.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN);
		  query.setParameter(5, numeroCuentaCargoE);

		  query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
		  query.setParameter(6,maximoMontoTransaccionE);

		  query.registerStoredProcedureParameter(7, Long.class, ParameterMode.IN);
		  query.setParameter(7, numeroCuentaComisionE);

		  query.registerStoredProcedureParameter(8, Float.class, ParameterMode.IN);
		  query.setParameter(8, tarifaLBTRE);
		  
		  query.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN);
		  query.setParameter(9, idTramoE);
   
		  //Parámetros salida
		  query.registerStoredProcedureParameter(10, Integer.class, ParameterMode.OUT);
		  
		  //Ejecución SP
   		  query.execute();
     
   		  em.close();
   		  
   		  codigoE = "000";
  		  mensajeE = "Emisor Creado Exitosanmente Codigo :" + emsor.getCodigoEmisor();
  	      statusE = 200;
   		  
   		  responseE.setMessage(mensajeE);
   		  responseE.setCodigoOperacion(codigoE);
   		  responseE.setStatusCode(statusE);
   		  
   		  return responseE;
   		  
	  }else {
		  String warningE = emsor.getCodigoEmisor() + " ya existe en la plaforma";

		  LOGGER.warn(warningE);
		  
		  codigoE = "010";
  	      statusE = 100;
		  
		  responseE.setMessage(warningE);
   		  responseE.setCodigoOperacion(codigoE);
   		  responseE.setStatusCode(statusE);
   		  
   		  return responseE;
	  }
	}
	catch(Exception ex) 
	{
		LOGGER.error("Error :" + ex);
		String error = "Error al ejecutar SP: '"+nombreSP+"'";
		LOGGER.info(error);
		LOGGER.error(ex);

		responseE.setMessage(error);
   		responseE.setCodigoOperacion("030");
   		responseE.setStatusCode(300);
   		  
   		return responseE;
	}finally {
		  em.close();
	  }
 }
 
 /**********************************************************
  * Nombre funcion: updateEmisor...........................*
  * Action: Actualiza emisor a la base de datos............*
  * Inp:@emsor:Emisor......................................* 
  * Out::resultado SP."pkg_Transaccion.sp_Transaccion_Ins".*
  *********************************************************/
 public ResponseDTO updateEmisor(Emisor emsor) 
 {
  String nombreSP = "pkg_Emisor.sp_Emisor_Udp";
  
  ResponseDTO responseEm = new ResponseDTO();

  try
  { 
	  int codEmisorEm = emsor.getCodigoEmisor();
	  String rutEm = emsor.getRutEmisor();
	  String nombreEm = emsor.getNombreEmisor();
	  int estadoEm = emsor.getEstado();
	  Long numeroCuentaCargoEm = emsor.getNumeroCuentaCargo();
	  String maximoMontoTransaccionEm = emsor.getMontoMaximoTransaccion();
	  Long numeroCuentaComisionEm = emsor.getNumeroCuentaComision();
	  float  tarifaLBTREm = emsor.getTarifaLBTR();
	  int idTramoEm = emsor.getIdTramo();

	
	  em = emf.createEntityManager();
	  StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Emisor.class);

	  // Parámetros entrada
	  query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	  query.setParameter(1,codEmisorEm);

	  query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	  query.setParameter(2,rutEm.substring(0, (rutEm.length() > 10)?10:rutEm.length()));

	  query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
	  query.setParameter(3, nombreEm.substring(0, (nombreEm.length() > 50)?50:nombreEm.length()));

	  query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
	  query.setParameter(4,estadoEm);

	  query.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN);
	  query.setParameter(5, numeroCuentaCargoEm);

	  query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
	  query.setParameter(6,maximoMontoTransaccionEm);

	  query.registerStoredProcedureParameter(7, Long.class, ParameterMode.IN);
	  query.setParameter(7, numeroCuentaComisionEm);

	  query.registerStoredProcedureParameter(8, Float.class, ParameterMode.IN);
	  query.setParameter(8, tarifaLBTREm);
	  
	  query.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN);
	  query.setParameter(9, idTramoEm);

	  //Parámetros salida
	  query.registerStoredProcedureParameter(10, Integer.class, ParameterMode.OUT);

   
      //Ejecución SP
      query.execute();
   
      em.close();
       
   	  responseEm.setMessage("Emisor :" + emsor.getCodigoEmisor() + " actualizado correctamente");
	  responseEm.setCodigoOperacion("000");
	  responseEm.setStatusCode(200);
	  return responseEm;
  }
  catch(Exception ex) 
  {
	  LOGGER.error("Error :" + ex);
	  String error = "Error al ejecutar SP: '"+nombreSP+"'";
	  LOGGER.info(error);
	  LOGGER.error(ex);
   
	  responseEm.setMessage(error);
	  responseEm.setCodigoOperacion("030");
	  responseEm.setStatusCode(300);
		  
	  return responseEm;
  }finally {
	  em.close();
  }
 }
 
 /**********************************************************
  * Nombre funcion: cambioEstadoEmisor.....................*
  * Action: elimina emisor de la base de datos.............*
  * Inp:@codigo_emisor:int.................................*
  * Inp:@estado_emisor:int.................................*  
  * Out::resultado SP."pkg_Emisor.sp_Emisor_CambioEstado"..*
  *********************************************************/
 public ResponseDTO cambioEstadoEmisor(int codigo_emisor, int estado_emisor) 
 {
  String nombreSP = "pkg_Emisor.sp_Emisor_CambioEstado";
  ResponseDTO responseEc = new ResponseDTO();
  try
  { 
   em = emf.createEntityManager();
   StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Emisor.class);
    
   // Parámetros entrada
   query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
   query.setParameter(1, codigo_emisor);
   query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
   query.setParameter(2, estado_emisor);

  //Parámetros salida
  query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT);

   
   //Ejecución SP
   query.execute();
    
   em.close();
   
   responseEc.setMessage("Emisor :" + codigo_emisor + " cambio de estado exitosamente");
   responseEc.setCodigoOperacion("000");
   responseEc.setStatusCode(200);
  }
  catch(Exception exs) 
  {
   LOGGER.error("Error Sp:" + exs);
   String errorEm = "Error al ejecutar Store P: '"+nombreSP+"'";
   LOGGER.info(errorEm);
   LOGGER.error(exs);
   
   responseEc.setMessage(errorEm);
	  responseEc.setCodigoOperacion("030");
	  responseEc.setStatusCode(300);
		  
	  return responseEc;
  }finally {
	  em.close();
  }
  
  return responseEc;
 }
 
 /**
  * Obtener Emisor por Cod_Emisor
  * @return
  * @throws SQLException 
  */
 @SuppressWarnings("null")
public ResponseEmisor obtenerEmisores(int estado_emisor) throws SQLException 
 {
  LOGGER.debug("obtenerEmisores > estado_emisor: '"+estado_emisor+"'");
  List<Emisor> emisores = new ArrayList<Emisor>();
  ResponseEmisor response = new ResponseEmisor();
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Emisor.sp_Obtener_TodosEmisores")
        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN).setParameter(1, estado_emisor)
        .registerStoredProcedureParameter(2, Emisor.class, ParameterMode.REF_CURSOR);
       
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listEmi = query.getResultList();
       
       for(Object[] result : listEmi) 
       {
    	 Emisor emi = new Emisor();
    	 Tramo tram = new Tramo();
    	 
    	 emi.setCodigoEmisor(Integer.parseInt(result[0].toString()));
    	 emi.setEstado(Integer.parseInt(result[1].toString()));
    	 emi.setIdTramo(Integer.parseInt(result[2].toString()));
    	 emi.setMontoMaximoTransaccion(result[3].toString());
    	 emi.setNombreEmisor(result[4].toString());
    	 emi.setNumeroCuentaCargo(Long.parseLong(result[5].toString()));
    	 emi.setNumeroCuentaComision(Long.parseLong(result[6].toString()));
    	 emi.setRutEmisor(result[7].toString());
    	 emi.setTarifaLBTR(Float.parseFloat(result[8].toString()));
    	 emi.setNEstado(result[13].toString());
    	 tram.setIdTramo(Integer.parseInt(result[2].toString()));
    	 tram.setNombre(result[9].toString());
    	 tram.setMinimo(Integer.parseInt(result[10].toString()));
    	 tram.setMaximo(Integer.parseInt(result[11].toString()));
    	 tram.setTarifa(Float.parseFloat(result[12].toString()));
    	 emi.setTramo(tram);
    	 emisores.add(emi);
       }
       
       em.close();
  
       LOGGER.debug("se han obteneido" + emisores.size() + " emisores");
       
       response.setMessage("se han obteneido" + emisores.size() + " emisores");
       response.setStatusCode(200);
       response.setEmisores(emisores);
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener emisores: '"+ex);
   response.setMessage("Error al obtener emisores: '"+ex);
   response.setStatusCode(300);
   return response;
   
  }finally {
	  em.close();
  }
       
  return response;
 }
 
}
