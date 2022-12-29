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
import com.bch.api.rest.dto.ListaResultadoPaginacionDTO;
import com.bch.api.rest.dto.ResponseReportTransaccionH;
import com.bch.api.rest.dto.ResponseTransaccionHistoriaDTO;
import com.bch.api.rest.dto.TransaccionDTO;
import com.bch.api.rest.dto.ctrm.TransaccionControlM;
import com.bch.api.rest.dto.filtrobusq.ConsultaTransaccion;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.entities.TransaccionHistoria;
import com.bch.api.rest.utils.FuncionesGenerales;

/**
 * Capa DAL para las Transacciones
 * @author 160k
 *
 */
@Repository
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
public class TransaccionDAL{

@Value("${nombre-persistencia}")
 private String nombrePersistencia;
 
 private EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombrePersistencia);
 
 private EntityManager em;

 private static final Logger LOGGER = Logger.getLogger(TransaccionDAL.class);
 
 /************************************************************
  * Nombre funcion: ingresarTransaccion......................*
  * Action: Ingresa la transacción a la base de datos........*
  * Inp:@tran:Transaccion....................................* 
  * Out::resultado SP."pkg_Transaccion.sp_Transaccion_Ins"...*
  ************************************************************/
 public String ingresarTransaccion(Transaccion tran) 
 {
  String nombreSP = "pkg_Transaccion.sp_Transaccion_Ins";
  em = emf.createEntityManager();
  try
  { 
 
   StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Transaccion.class);
   
   String codInstitucion = tran.getCodInstitucion();
   String fechaTrx = tran.getFechaTransaccion();
   String codTrx = tran.getCodigoTransaccion();
   String montoEnviado = tran.getMontoTotalEnviadoCiclo();
   
   String origenCCA = "CCA";
      
   // Parámetros entrada
   query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
   query.setParameter(1, codInstitucion.substring(0, (codInstitucion.length() > 4)?4:codInstitucion.length()));
   
   query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
   query.setParameter(2, fechaTrx.substring(0, (fechaTrx.length() > 20)?20:fechaTrx.length()));
   
   query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
   query.setParameter(3, codTrx.substring(0, (codTrx.length() > 100)?100:codTrx.length()));
   
   query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
   query.setParameter(4, montoEnviado.substring(0, (montoEnviado.length() > 14)?14:montoEnviado.length()));

   query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
   query.setParameter(5, tran.getTipoTransaccion());

   query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);
   query.setParameter(6, tran.getCiclo());

   query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
   query.setParameter(7, tran.getEstadoActual());
   
   query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
   query.setParameter(8, tran.getCore());
   
   query.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
   query.setParameter(9, tran.getOrigen());

   //Parámetros salida
   query.registerStoredProcedureParameter(10, Integer.class, ParameterMode.OUT);
   
   //Ejecución SP
   query.execute();
   
   String idTrx = query.getOutputParameterValue(10).toString();
   
   em.close();
   
   return idTrx;
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error :" + ex);
   String error = "Error al ejecutar SP: '"+nombreSP+"'";
   LOGGER.info(error);
   LOGGER.error(ex);
   
   return error;
  }finally {
	  em.close();
  }
 }
 
 /******************************************************************************************
  * Nombre funcion: actualizarEstadoTransaccion............................................*
  * Action: Actualizar el estado actual de la transacción..................................*
  * Inp:...................................................................................* 
  * Out::resultado SP."pkg_Transaccion.sp_Transaccion_Estado_Upd" lista de transacciones...*
  *****************************************************************************************/
 public void actualizarEstadoTransaccion(Transaccion tran) 
 {
  String nombreSP = "pkg_Transaccion.sp_Transaccion_Estado_Upd";
  
  try
  {
   em = emf.createEntityManager();
   StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Transaccion.class);
   
   int idTransaccion = tran.getIdTransaccion();
   String estadoActual = tran.getEstadoActual();
   
   // Parámetros entrada
   query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
   query.setParameter(1, idTransaccion);
   
   query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
   query.setParameter(2, estadoActual);

   // Ejecución SP
   query.execute();
   
   em.close();
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al actualizar estado trx: " + ex);
  }finally {
	  em.close();
  }
 }
 
 /**
  * Actualiza el codigo id trx de la transacción
  * @param tran
  */
 public void actualizarCodigoIdTrx(Transaccion tran)
 {
  String nombreSP = "pkg_Transaccion.sp_Transaccion_CodigoIdTrx_Upd";
  
  try
  {
   em = emf.createEntityManager();
   StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, Transaccion.class);
   
   int idTransaccion = tran.getIdTransaccion();
   String codigoIdTrxActual = tran.getCodigoIdTrx();
   
   // Parámetros entrada
   query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
   query.setParameter(1, idTransaccion);
   
   query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
   query.setParameter(2, codigoIdTrxActual);

   // Ejecución SP
   query.execute();
   
   em.close();
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al actualizar codigo id trx:" + ex);
  }finally {
	  em.close();
  }
 }
 
 /******************************************************************************************
  * Nombre funcion: ingresarHistoriaTransaccion............................................*
  * Action: Registrar Historia en la transaccion...........................................*
  * Inp:@tran:Transaccion..................................................................* 
  * Out::resultado SP."pkg_Transaccion.sp_TrnsaccionHistoria_Ins" lista de transacciones...*
  *****************************************************************************************/
 public String ingresarHistoriaTransaccion(TransaccionHistoria tranHisBD)
    {
  String nombreSP = "pkg_Transaccion.sp_TrnsaccionHistoria_Ins";
  
  try
  {
   //---- En caso de que el codigo venga NULL
   if(tranHisBD.getCodigoIdTrx() == null) 
   {
    tranHisBD.setCodigoIdTrx("-");
   }
   
   em = emf.createEntityManager();
   StoredProcedureQuery query = em.createStoredProcedureQuery(nombreSP, TransaccionHistoria.class);
   
   // Parámetros entrada
   query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
   query.setParameter(1, tranHisBD.getIdTransaccion());
   
   query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
   query.setParameter(2, tranHisBD.getCodigoOperacion());
   
   query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
   query.setParameter(3, tranHisBD.getTipoEvento());
   
   query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
   query.setParameter(4, tranHisBD.getDetalle());

   query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
   query.setParameter(5, tranHisBD.getEstadoActual());

   query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
   query.setParameter(6, tranHisBD.getCodigoIdTrx());

   // Parámetros salida
   query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT);
   
   // Ejecución SP
   query.execute();
   
   String idHisTrx = query.getOutputParameterValue(7).toString();
   
   em.close();
   
   return idHisTrx;
  }
  catch(Exception ex) 
  {
   String error = "Error al ejecutar SP: '"+nombreSP+"'";
   LOGGER.error(error+ " " + ex);
   
   return error;
  }finally {
	  em.close();
  }
 }

 /******************************************************************************************
  * Nombre funcion: consultarTrx...........................................................*
  * Action:Obtener todas las transacciones según filtro de búsqueda........................*
  * Inp:@ctran:ConsultaTransaccion.........................................................* 
  * Out::resultado SP."pkg_Transaccion.sp_Transaccion_Sel" lista de transacciones..........*
  *****************************************************************************************/
 public List<TransaccionControlM> consultarTrx(ConsultaTransaccion ctran) throws SQLException 
 {
  LOGGER.debug("consultarTrx()" + ctran.getDateDesde() + "-" + ctran.getDateHasta()+ "-" + ctran.getCodigoEmisors() + "-" +  ctran.getEstadoTrxs() + "-" +  ctran.getTipoTrxs());
  
  List<TransaccionControlM> lista = new ArrayList<TransaccionControlM>();
  
  try
  {
   em = emf.createEntityManager();
        StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Transaccion.sp_Transaccion_Sel");
        		
        if(ctran.getDateDesde()== null) {
        	ctran.setDateDesde("");
        	ctran.setDateHasta("");
        }
        
        if(ctran.getCodigoEmisors()== null) {
        	ctran.setCodigoEmisors("");
        }
        
        if(ctran.getEstadoTrxs()== null) {
        	ctran.setEstadoTrxs("");
        }
        
        if(ctran.getTipoTrxs() == null) {
        	ctran.setTipoTrxs("");
        }
        
        LOGGER.debug("consultarTrx() Final" + ctran.getDateDesde() + "-" + ctran.getDateHasta()+ "-" + ctran.getCodigoEmisors() + "-" +  ctran.getEstadoTrxs() + "-" +  ctran.getTipoTrxs());

        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, ctran.getDateDesde());
    	query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN).setParameter(2, ctran.getDateHasta());
    	query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN).setParameter(3, ctran.getCodigoEmisors());
    	query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN).setParameter(4, ctran.getEstadoTrxs());
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN).setParameter(5, ctran.getTipoTrxs());
    	query.registerStoredProcedureParameter(6, TransaccionControlM.class, ParameterMode.REF_CURSOR);
        
        query.execute();
        LOGGER.debug("query ejecuto ok");
        @SuppressWarnings("unchecked")
        List<Object[]> listRes = query.getResultList();
        
        for(Object[] result : listRes) 
        {
         TransaccionControlM t = new TransaccionControlM();
         
         LOGGER.debug("idTransaccion: '"+result[0].toString()+"' | montoCargo: "+result[6].toString());
         
         t.setIdTransaccion(Integer.parseInt(result[0].toString()));
         t.setCodigoTrx(result[1].toString());
         t.setCodigoInstitucion(result[2].toString());
         t.setRutEmisor(result[3].toString());
         t.setNumeroCuenta(Long.parseLong(result[4].toString(),12));
         t.setMontoTotalEnviadoCiclo(result[5].toString());
         t.setMontoCargo(Double.parseDouble(result[6].toString()));
         t.setTipoTrx(result[7].toString());         
         t.setEstadoActual(result[8].toString());
         t.setOrigen(result[9].toString());
         t.setCore(result[10].toString());
         lista.add(t);
        }
        LOGGER.debug("Total de trx rechazadas:" + lista.size());
        em.close();
  }
  catch(Exception ex)
  {
   LOGGER.error("Error al consultar transacción :" + ex);
   lista = null; 
  }finally {
	  em.close();
  }
  
  return lista;
 }
 
 /******************************************************************************************
  * Nombre funcion: obtenerTransaccion.....................................................*
  * Action: Obtener todas las transacciones según filtro de búsqueda.......................*
  * Inp:@idTrx:int.........................................................................* 
  * Out::resultado SP."pkg_Transaccion.ssp_Transaccion_Obt" lista de transacciones.........*
  *****************************************************************************************/
 public Transaccion obtenerTransaccion(int idTrx) throws SQLException 
 {
  LOGGER.debug("obtenerTransaccion > idTrx: '"+idTrx+"'");
  Transaccion trx = new Transaccion();
  
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Transaccion.sp_Transaccion_Obt")
        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN).setParameter(1, idTrx)
        .registerStoredProcedureParameter(2, Transaccion.class, ParameterMode.REF_CURSOR);
       
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listRes = query.getResultList();
       
       for(Object[] result : listRes) 
       {
        trx.setIdTransaccion(Integer.parseInt(result[0].toString()));
        trx.setCodInstitucion(result[1].toString());
        trx.setFechaTransaccion(result[2].toString());
        trx.setCodigoTransaccion(result[3].toString());
        trx.setMontoTotalEnviadoCiclo(result[4].toString());
        trx.setTipoTransaccion(result[5].toString());
        trx.setCiclo(Integer.parseInt(result[6].toString()));
        trx.setEstadoActual(result[7].toString());
        trx.setCodigoIdTrx((result[8] == null)?null:result[8].toString());
        trx.setOrigen(result[9].toString());
        trx.setCore(result[10].toString());
       }
       
       em.close();
  
       LOGGER.debug("trx > idTrx: '"+trx.getIdTransaccion()+"'");
       LOGGER.debug("trx > codIdTrx: '"+trx.getCodigoIdTrx()+"'");
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener la transacción: '"+ex);
   trx = null;
  }finally {
	  em.close();
  }
       
  return trx;
 }
 
 /**************************************************************************************************************
  * Nombre funcion: verificarDuplicidadTransaccionCiclo........................................................*
  * Action: Verificar si la transacción ya se registró el día de hoy (ciclo y codigo transacción)..............*
  * Inp:@codigoInstitucion:String..............................................................................* 
  * Out::resultado SP."pkg_Transaccion.sp_Transaccion_ExisteCicloHoy"  true/false si se registró o no..........*
  *****************************************************************************************/
 public boolean verificarDuplicidadTransaccionCiclo(Transaccion tranBD) 
 {
  boolean estaDuplicada = false;
  
  LOGGER.debug("verificar Duplicidad Transaccion Ciclo > idTrx: ");
  LOGGER.debug("verificar Duplicidad Transaccion Ciclo > codigoInstitucion:");
  String cod_trx = tranBD.getCodigoTransaccion();
  try
  {
   em = emf.createEntityManager();

   StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Transaccion.sp_Transaccion_ExisteCicloHoy")
        .registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, cod_trx)
        .registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

   query.execute();
       
   String duplicStr = query.getOutputParameterValue(2).toString();
   LOGGER.debug("trx > duplicStr: '"+duplicStr+"'");
       
   em.close();
  
   String resultCodeDuplicada = "1";
   
   if(duplicStr.equals(resultCodeDuplicada)) 
   {
    estaDuplicada = true;
   }
   else 
   {
    estaDuplicada = false;
   }
       
   LOGGER.debug("estaDuplicada: '"+estaDuplicada+"'");
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error en verificarDuplicidadTransaccionCiclo: '"+ex);
   estaDuplicada = true;
  }finally {
	  em.close();
  }
       
  return estaDuplicada;
 }
 
 /**
  * Obtener la secuencia de servicio de consulta
  * @return
  * @throws SQLException 
  */
 public int getSequence() throws SQLException 
 {
  int secVal = 0;
  try
  {
   em = emf.createEntityManager();
   StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Transaccion.sp_GetValorSecuencia",
     TransaccionHistoria.class);
   
   // Parámetros salida
   query.registerStoredProcedureParameter(1, Long.class, ParameterMode.OUT);

   // Ejecución SP
   query.execute();
   
   secVal = Integer.parseInt(query.getOutputParameterValue(1).toString());
   LOGGER.debug("secVal: '"+secVal+"'");
   
   em.close();
   
   return secVal;
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error getSequence" + ex);
   return -1;
  }finally {
	  em.close();
  }
 }
 
 /**
  * Obtiene la secuencia mas actual de la transacción
  * @return último valor de la secuencia
  * @throws SQLException 
  */
 public int obtenerSeqTrx() throws SQLException 
 {
  return getSequence();
 }

 /******************************************************************************************
  * Nombre funcion: obtenerTransaccionHistoricas...........................................*
  * Action: Obtener todas las transacciones historicas según filtro de búsqueda............*
  * Inp:@idTrx:int.........................................................................* 
  * Out::resultado SP."pkg_Transaccion.ssp_Transaccion_Obt" lista de transacciones.........*
  *****************************************************************************************/
 public ListaResultadoPaginacionDTO obtenerTransaccionHistoricas(
		 String fechaIniT, 
		 String fechaFinT,
		 int idCodigoInstitucionT, 
		 String estadoTrxT, 
		 int pageSizeT, 
		 int pageNumberT) throws SQLException 
 {
  LOGGER.debug("obtenerTransaccionHistoricas > fechaIni: '"+fechaIniT+"' | fechaFin: '"+fechaFinT+
		  "' | idCodigoInstitucion: '"+idCodigoInstitucionT+"' | estadoTrx: '"+estadoTrxT+"' | pageSize: '"+pageSizeT
		  +"' | pageNumber: '"+pageNumberT+"'");
  
  ListaResultadoPaginacionDTO objRes = new ListaResultadoPaginacionDTO();
  
  try
  {
	  List<ResponseReportTransaccionH> historiadeTramsacciones = new ArrayList<ResponseReportTransaccionH>();
	  
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Transaccion.sp_obtenerTransaccionesHistoricas");
        
       
       if(fechaIniT== null) {
    	   fechaIniT ="";
       }
       if(fechaFinT== null) {
    	   fechaFinT ="";
       }
       if(estadoTrxT== null) {
    	   estadoTrxT ="";
       }
       
       
       
      
       query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
	   query.setParameter(1,fechaIniT);

       query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	   query.setParameter(2,fechaFinT);

       query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
	   query.setParameter(3,idCodigoInstitucionT);

       query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
	   query.setParameter(4,estadoTrxT);
		  
	   query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
	   query.setParameter(5,pageNumberT);
		  
	   query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);
	   query.setParameter(6,pageSizeT);
	   
	   query.registerStoredProcedureParameter(7, ResponseReportTransaccionH.class, ParameterMode.REF_CURSOR);
		 
	   query.registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT);
		 
       query.execute();
       
       objRes.setTotalRegistros(Integer.parseInt(query.getOutputParameterValue(8).toString()));
       
       @SuppressWarnings("unchecked")
       List<Object[]> listResT = query.getResultList();
       
       for(Object[] resultT : listResT) 
       {
    	ResponseReportTransaccionH trxh = new ResponseReportTransaccionH();
    	trxh.setIdTrx(Integer.parseInt(resultT[1].toString()));
    	trxh.setCodigoTrx(resultT[2].toString());
    	trxh.setCodigoInsitucion((resultT[3].toString()));
    	trxh.setNombreInsitucion(resultT[4].toString());
    	trxh.setFechaTrx((resultT[5].toString()));    	
    	trxh.setMontototalenviadociclio(resultT[6].toString());
    	trxh.setCiclo(Integer.parseInt(resultT[7].toString()));
    	trxh.setEstadoCiclo(resultT[8].toString());
    	trxh.setCodigoIdTrx((resultT[9] == null)?"":resultT[9].toString());
    	trxh.setOrigen(resultT[10].toString());
    	if(resultT[11]==null) {
    		trxh.setIntentos(1);
 	   }else {
 		  trxh.setIntentos(Integer.parseInt(resultT[11].toString()));   
 	   }
    	trxh.setCore(resultT[12].toString());
        historiadeTramsacciones.add(trxh);
       }
       
       objRes.setListaResultado(historiadeTramsacciones);
       
       em.close();
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener la historia transacción: '"+ex);
   return objRes;
  }finally {
	  em.close();
  }
       
  return objRes;
 }
 
 /******************************************************************************************
  * Nombre funcion: obtenerHistoriasTransaccion...........................................*
  * Action: Obtener todas las transacciones historicas según filtro de búsqueda............*
  * Inp:@idTrx:int.........................................................................* 
  * Out::resultado SP."pkg_Transaccion.ssp_Transaccion_Obt" lista de transacciones.........*
  *****************************************************************************************/
 public ListaResultadoPaginacionDTO obtenerHistoriasTransaccion(int idTrx) throws SQLException 
 {
  LOGGER.debug("obtenerHistoriasTransaccion > idTrx: "+idTrx);
  
  ListaResultadoPaginacionDTO objRes = new ListaResultadoPaginacionDTO();
  
  try
  {
	  List<ResponseTransaccionHistoriaDTO> listaHistoriasTrx = new ArrayList<ResponseTransaccionHistoriaDTO>();
	  
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Transaccion.sp_obtenerHistoriasTransaccion");
        
      
       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	   query.setParameter(1,idTrx);
	   
	   query.registerStoredProcedureParameter(2, ResponseTransaccionHistoriaDTO.class, ParameterMode.REF_CURSOR);
		 
       query.execute();
       
       objRes.setTotalRegistros(0);
       
       @SuppressWarnings("unchecked")
       List<Object[]> listRes = query.getResultList();
       
       for(Object[] result : listRes) 
       {
    	   ResponseTransaccionHistoriaDTO htrx = new ResponseTransaccionHistoriaDTO();
    	   htrx.setIdHis(Integer.parseInt(result[0].toString()));
    	   htrx.setIdTrx(Integer.parseInt(result[1].toString()));
    	   htrx.setCodigoOperacion(Integer.parseInt(result[2].toString()));
    	   htrx.setFechaTrx(result[3].toString());	
    	   htrx.setTipoEvento(result[4].toString());
    	   htrx.setDetalle(result[5].toString());
    	   htrx.setEstado(result[6].toString());
    	   htrx.setCodigoIdTrx((result[7] == null)?"":result[7].toString());
 

    	   listaHistoriasTrx.add(htrx);
       }
       
       objRes.setListaResultado(listaHistoriasTrx);
       
       em.close();
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener las historias de la transacción: '"+ex);
   return objRes;
  }finally {
	  em.close();
  }
       
  return objRes;
 }
 
 /***********************************************************************************************
  * Nombre funcion: obtenerTransaccionRA........................................................*
  * Action: Obtener todas las transacciones rechazadas o aceptadas en las ultimas 24 HH Habiles.*
  * Inp:@estadoT:String..............................................................................* 
  * Out::resultado SP."pkg_Transaccion.sp_Transaccion_Sel24HH" lista de transacciones..............*
  ***********************************************************************************************/
 public ListaResultadoPaginacionDTO obtenerTransaccionesRA(String estadoT, int numRegPaginaT
		 , int numeroPaginaT) throws SQLException 
 {
  LOGGER.debug("obtenerTransaccionRA > estadoT: "+estadoT);
  
  ListaResultadoPaginacionDTO objRes = new ListaResultadoPaginacionDTO();
  
  try
  {	  
	   List<TransaccionDTO> transacciones = new ArrayList<TransaccionDTO>();
	  
	   int horasNoHabiles = FuncionesGenerales.horasHabiles();
	  	   
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Transaccion.sp_Transaccion_Sel24HH");
        
       query.registerStoredProcedureParameter(1, int.class, ParameterMode.IN);
	   query.setParameter(1,horasNoHabiles);

       query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	   query.setParameter(2,estadoT);

       query.registerStoredProcedureParameter(3, int.class, ParameterMode.IN);
	   query.setParameter(3,numRegPaginaT);
       
       query.registerStoredProcedureParameter(4, int.class, ParameterMode.IN);
	   query.setParameter(4,numeroPaginaT);
	   
	   query.registerStoredProcedureParameter(5, TransaccionDTO.class, ParameterMode.REF_CURSOR);
	
	   query.registerStoredProcedureParameter(6, int.class, ParameterMode.OUT);
	   
       query.execute();
             
       objRes.setTotalRegistros(Integer.parseInt(query.getOutputParameterValue(6).toString()));
       
       @SuppressWarnings("unchecked")
       List<Object[]> listResT = query.getResultList();
       
       for(Object[] resultT : listResT) 
       {
    	   TransaccionDTO trx = new TransaccionDTO();
    	   
    	   trx.setIdTrx(resultT[1].toString());
    	   trx.setCodTrx(resultT[2].toString());
    	   trx.setCodInstitucion(resultT[3].toString());
    	   trx.setRutEmisor(resultT[4].toString());	
    	   trx.setNumeroCuentaCargo(resultT[5].toString());
    	   trx.setMontoTotalEnviadoCiclo(resultT[6].toString());
    	   trx.setMontoTotalEnviadoCicloLong((long)Double.parseDouble(resultT[7].toString()));
    	   trx.setTipoTrx(resultT[8].toString());
    	   trx.setEstadoActual(resultT[9].toString());
    	   trx.setFechaHoraTrx(resultT[10].toString());
    	   trx.setCodigoIdTransaccion((resultT[11] == null)?"":resultT[11].toString());
    	   trx.setNombreInsitucion(resultT[12].toString());
    	   trx.setCiclo(Integer.parseInt(resultT[13].toString()));
    	   trx.setOrigen(resultT[15].toString());
    	   if(resultT[14]==null) {
    		   trx.setIntentos(1);
    	   }else {
    		   trx.setIntentos(Integer.parseInt(resultT[14].toString()));   
    	   }
    	   trx.setCore(resultT[16].toString());

    	   
    	   transacciones.add(trx);
       }
       
       objRes.setListaResultado(transacciones);
       
       em.close();
       
       return objRes;
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener transacciones RA: '"+ex);
   return objRes;  
  }finally {
	  em.close();
  }
  
 }
 
 /******************************************************************************************
  * Nombre funcion: obtenerTransaccion.....................................................*
  * Action: Obtener todas las transacciones según filtro de búsqueda.......................*
  * Inp:@idTrx:int.........................................................................* 
  * Out::resultado SP."pkg_Transaccion.ssp_Transaccion_Obt" lista de transacciones.........*
  *****************************************************************************************/
 public String obtenerUltimaTransaccionId() throws SQLException 
 {

  String trx = "";
  
  try
  {

       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Transaccion.sp_Transaccion_UlObt");
   
       query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
       
       query.execute();
       
       trx = query.getOutputParameterValue(1).toString();
       
       em.close();
  
       LOGGER.debug("trx > codIdTrx: '"+trx);
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener la transacción: '"+ex);
   trx = null;
  }finally {
	  em.close();
  }
       
  return trx;
 }
 
 
}