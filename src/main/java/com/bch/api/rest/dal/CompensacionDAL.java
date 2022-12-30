package com.bch.api.rest.dal;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.bch.api.rest.bl.InformarTefBL;
import com.bch.api.rest.bl.ServicioBL;
import com.bch.api.rest.dto.ListaResultadoPaginacionDTO;
import com.bch.api.rest.dto.ResponseReportTransaccionH;
import com.bch.api.rest.dto.ResponseReporteHistoricoDTO;
import com.bch.api.rest.dto.ResponseReporteTrxHistoricoDTO;
import com.bch.api.rest.dto.ResponseTrxCompenHistoriaDTO;
import com.bch.api.rest.dto.TransaccionDTO;
import com.bch.api.rest.entities.Emisor;
import com.bch.api.rest.entities.Reporte;
import com.bch.api.rest.entities.Transaccion;
import com.bch.api.rest.factory.FactoryDAL;
import com.bch.api.rest.utils.FuncionesGenerales;

/**
 * Capa DAL para los Reportes
 * @author 160k
 *
 */
@Repository
@PropertySource({"file:${APPS_PROPS}/Config/BCC-properties.properties"})
@Configuration
public class CompensacionDAL{

@Value("${nombre-persistencia}")
 private String nombrePersistencia;
 
@Autowired
private FactoryDAL facDAL;

@Autowired
private ServicioBL servBL;

@Autowired
private TransaccionDAL tranDAL;

 private EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombrePersistencia);
 
 private EntityManager em;

 String tipoTrxEnviado="E";
 
 String dateStringE = "yyyy-MM-dd HH:mm:ss";
 
 String dateStringI = "yyyyMMddHHmmss";
 
 String strBCOCOMPEN = "BCOCOMPEN";
 
 private static final Logger LOGGER = Logger.getLogger(CompensacionDAL.class);
 
 
 
 public boolean existeReporte(String nombreReporte) throws SQLException 
 {
  boolean existeE = false;
  
  LOGGER.debug("existeReporte > Nombre Reporte: '"+nombreReporte+"'");

  try
  {
	  em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Compensacion.sp_Reporte_Existe")
        .registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, nombreReporte)
        .registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);
       
       query.execute();
       
       String existeStrE = query.getOutputParameterValue(2).toString();
       LOGGER.debug("reporte > existeStr: '"+existeStrE+"'");
       
  
       String resultCodeDuplicadaE = "1";
       
       if(existeStrE.equals(resultCodeDuplicadaE)) 
    {
        existeE = true;
    }
    else 
    {
     existeE = false;
    }
       
       LOGGER.debug("reporte > existe: '"+existeE+"'");
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error en existeReporteComp: '"+ex);
   existeE = true;
  }finally {
	  em.close();
  }
       
  return existeE;
 }
 
 
 
 /******************************************************************************************
  * Nombre funcion: obtenerReportesHistorico...............................................*
  * Action: Obtener los reportes historicos según filtro de búsqueda.......................*
  * Inp:@idTrx:int.........................................................................* 
  * Out::resultado SP."pkg_Reporte.sp_ObtenerReportesHistorico" lista de reportes..........*
  *****************************************************************************************/
 public ListaResultadoPaginacionDTO obtenerReportesHistorico(
		 String fechaIni, 
		 String fechaFin,
		 int codigoEmisor, 
		 int pageSize, 
		 int pageNumber) throws SQLException 
 {
  LOGGER.debug("obtenerReportesHistorico > fechaIni: '"+fechaIni+"' | fechaFin: '"+fechaFin+
		  "' | codigoEmisor: '"+codigoEmisor+"' | pageSize: '"+pageSize+"' | pageNumber: '"+pageNumber+"'");
  
  ListaResultadoPaginacionDTO objResC = new ListaResultadoPaginacionDTO();
  
  try
  {
	  List<ResponseReporteHistoricoDTO> listaRepoHisC = new ArrayList<ResponseReporteHistoricoDTO>();
	  
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Compensacion.sp_ObtenerReportesCompensacion");
        
      
       query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
	   query.setParameter(1,fechaIni);

       query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	   query.setParameter(2,fechaFin);

       query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
	   query.setParameter(3,codigoEmisor);
		  
	   query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
	   query.setParameter(4,pageNumber);
		  
	   query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
	   query.setParameter(5,pageSize);
	   
	   query.registerStoredProcedureParameter(6, ResponseReportTransaccionH.class, ParameterMode.REF_CURSOR);
		 
	   query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT);
		 
       query.execute();
       
       objResC.setTotalRegistros(Integer.parseInt(query.getOutputParameterValue(7).toString()));
       
       @SuppressWarnings("unchecked")
       List<Object[]> listResC = query.getResultList();
       
       for(Object[] resultC : listResC) 
       {
           ResponseReporteHistoricoDTO hisRepo = new ResponseReporteHistoricoDTO();
           hisRepo.setIdReporte(Integer.parseInt(resultC[1].toString()));
           hisRepo.setIdCodTrxRepo((resultC[2] == null)?"":resultC[2].toString());
           hisRepo.setCodEmisor(Integer.parseInt(resultC[3].toString()));
           hisRepo.setNombreEmisor(resultC[4].toString());
           hisRepo.setFechaRecepcion((resultC[5].toString()));    	
           hisRepo.setEstado(resultC[6].toString());
           hisRepo.setCiclo(Integer.parseInt(resultC[7].toString()));
           hisRepo.setNombreArchivo(resultC[8].toString());
        
           listaRepoHisC.add(hisRepo);
       }
       
       objResC.setListaResultado(listaRepoHisC);
       
       em.close();
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener reportes históricos: '"+ex);
   return objResC;
  }finally {
	  em.close();
  }
       
  return objResC;
 }
 
 /***********************************************************************************************
  * Nombre funcion: obtenerRepoCompen24hh........................................................*
  * Action: Obtener reportes de compensación en las ultimas 24 HH Habiles.*
  * Inp:@numRegPagina:int..............................................................................* 
  * Inp:@numeroPagina:int..............................................................................* 
  * Out::resultado SP."pkg_Transaccion.sp_Transaccion_Sel24HH" lista de transacciones..............*
  ***********************************************************************************************/
 public ListaResultadoPaginacionDTO obtenerRepoCompen24hh(int numRegPagina, int numeroPagina) throws SQLException 
 {
  LOGGER.debug("obtenerRepoCompen24hh");
  
  ListaResultadoPaginacionDTO objResCo = new ListaResultadoPaginacionDTO();
  
  try
  {
	  int horasNoHabiles = FuncionesGenerales.horasHabiles();
	   
	   List<ResponseReporteHistoricoDTO> listaRepoHisCo = new ArrayList<ResponseReporteHistoricoDTO>();
	  
	   
	   
       em = emf.createEntityManager();
       StoredProcedureQuery query = 
    		   em.createStoredProcedureQuery("pkg_Compensacion.sp_ObtenerReportesCompensacion24HH");
        
       query.registerStoredProcedureParameter(1, int.class, ParameterMode.IN);
	   query.setParameter(1,horasNoHabiles);

       query.registerStoredProcedureParameter(2, int.class, ParameterMode.IN);
	   query.setParameter(2,numRegPagina);
       
       query.registerStoredProcedureParameter(3, int.class, ParameterMode.IN);
	   query.setParameter(3,numeroPagina);
	   
	   query.registerStoredProcedureParameter(4, TransaccionDTO.class, ParameterMode.REF_CURSOR);
	
	   query.registerStoredProcedureParameter(5, int.class, ParameterMode.OUT);
	   
       query.execute();
             
       objResCo.setTotalRegistros(Integer.parseInt(query.getOutputParameterValue(5).toString()));
       
       @SuppressWarnings("unchecked")
       List<Object[]> listResCo = query.getResultList();
       
       for(Object[] resultCo : listResCo) 
       {
    	   ResponseReporteHistoricoDTO hisRepoCo = new ResponseReporteHistoricoDTO();
    	   hisRepoCo.setIdReporte(Integer.parseInt(resultCo[1].toString()));
    	   hisRepoCo.setIdCodTrxRepo((resultCo[2] == null)?"":resultCo[2].toString());
    	   hisRepoCo.setCodEmisor(Integer.parseInt(resultCo[3].toString()));
    	   hisRepoCo.setNombreEmisor(resultCo[4].toString());
    	   hisRepoCo.setFechaRecepcion((resultCo[5].toString()));    	
    	   hisRepoCo.setEstado(resultCo[6].toString());
    	   hisRepoCo.setCiclo(Integer.parseInt(resultCo[7].toString()));
           hisRepoCo.setNombreArchivo(resultCo[8].toString());
        
           listaRepoHisCo.add(hisRepoCo);
       }
       
       objResCo.setListaResultado(listaRepoHisCo);
       
       em.close();
       
       return objResCo;
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener transacciones RA: '"+ex);
   return objResCo;  
  }finally {
	  em.close();
  }
  
 }
 
 /******************************************************************************************
  * Nombre funcion: obtenerTrxHistoricasCompensacion.......................................*
  * Action: Obtener trx historicas de compensación.........................................*
  * Inp: @idTrx:int........................................................................* 
  * Out: lista de trx históricas de compensación...........................................*
  *****************************************************************************************/
 public ListaResultadoPaginacionDTO obtenerTrxHistoricasCompensacion(
		 int codigoEmisor
		 ,int codigoInstitucion
		 ,String idTipoTrx
		 ,String idEstadoTrx
		 ,String fechaIni
		 ,String fechaFin
		 ,int pageNumber) throws SQLException 
 {
  
  ListaResultadoPaginacionDTO objRes = new ListaResultadoPaginacionDTO();
  
  try
  {
	  List<ResponseReporteTrxHistoricoDTO> listaRepoHis = new ArrayList<ResponseReporteTrxHistoricoDTO>();
	  
       em = emf.createEntityManager();
       StoredProcedureQuery query = 
    		   em.createStoredProcedureQuery("pkg_Compensacion.sp_ObtenerTrxHistoricasCompensacion");
        
       
       if(idTipoTrx== null) {
    	   idTipoTrx ="";
       }
       if(idEstadoTrx== null) {
    	   idEstadoTrx ="";
       }
       if(fechaIni== null) {
    	   fechaIni ="";
       }
       if(fechaFin== null) {
    	   fechaFin ="";
       }

       LOGGER.debug("sp_ObtenerTrxHistoricasCompensacion > fechaIni: '"+fechaIni+"' | fechaFin: '"+fechaFin+
    			  "' | idCodigoInstitucion: '"+codigoInstitucion+
    			  "' | estadoTrx: '"+idEstadoTrx+"'|  idTipoTrx: '"+idTipoTrx+"'"  + "| pageNumber: '"+pageNumber+"'");
       
       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	   query.setParameter(1,codigoEmisor);

       query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
	   query.setParameter(2,codigoInstitucion);

       query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
	   query.setParameter(3,idTipoTrx);

       query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
	   query.setParameter(4,idEstadoTrx);

       query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
	   query.setParameter(5,fechaIni);

       query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
	   query.setParameter(6,fechaFin);

	   query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.IN);
	   query.setParameter(7,pageNumber);
		  
	   query.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN);
	   query.setParameter(8,10);
	   
	   query.registerStoredProcedureParameter(9, ResponseReportTransaccionH.class, ParameterMode.REF_CURSOR);
		 
	   query.registerStoredProcedureParameter(10, Integer.class, ParameterMode.OUT);
		 
       query.execute();
       
       objRes.setTotalRegistros(Integer.parseInt(query.getOutputParameterValue(10).toString()));
       
       @SuppressWarnings("unchecked")
       List<Object[]> listRes = query.getResultList();
       
       for(Object[] result : listRes) 
       {
    	   ResponseReporteTrxHistoricoDTO hisRepo = new ResponseReporteTrxHistoricoDTO();
           hisRepo.setIdTrx(Integer.parseInt(result[1].toString()));
           hisRepo.setIdReporte(Integer.parseInt(result[2].toString()));
           hisRepo.setCodIdTrx((result[3] == null)?"":result[3].toString());
           hisRepo.setCodInstitucionTrx(result[4].toString());
           hisRepo.setNombreInstitucionTrx(result[5].toString());
           hisRepo.setTipoTrx(result[6].toString());
           hisRepo.setFechaTrx((result[7].toString()));    	
           hisRepo.setNumeroTrx(Integer.parseInt(result[8].toString()));    	
           hisRepo.setMontoTotalTrx(Long.parseLong(result[9].toString()));    	
           hisRepo.setEstadoTrx(result[10].toString());
           hisRepo.setNombreArchivoTrx(result[11].toString());
        
           listaRepoHis.add(hisRepo);
       }
       
       objRes.setListaResultado(listaRepoHis);
       
       em.close();
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al obtener reportes trx históricos: '"+ex);
   return objRes;
  }finally {
	  em.close();
  }
       
  return objRes;
 }
 
 /**
  * Listar historias trx de compensación
  * @param idTrx
  * @return lista de historias de trx de compensación
  */
 public List<ResponseTrxCompenHistoriaDTO> listarHistoriasTrxCompen(int idTrx)
 {
     List<ResponseTrxCompenHistoriaDTO> lista = new ArrayList<ResponseTrxCompenHistoriaDTO>();
  try
  {
       em = emf.createEntityManager();
       StoredProcedureQuery query = 
    		   em.createStoredProcedureQuery("pkg_Compensacion.sp_ObtenerHistoriasTrxCompensacion");
        
       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	   query.setParameter(1,idTrx);
	   
	   query.registerStoredProcedureParameter(2, ResponseTrxCompenHistoriaDTO.class, ParameterMode.REF_CURSOR);
		 
       query.execute();
       
       @SuppressWarnings("unchecked")
       List<Object[]> listRes = query.getResultList();
       
       for(Object[] result : listRes) 
       {
    	   ResponseTrxCompenHistoriaDTO hisTrx = new ResponseTrxCompenHistoriaDTO();
    	   hisTrx.setIdHis(Integer.parseInt(result[0].toString()));
    	   hisTrx.setIdTrx(Integer.parseInt(result[1].toString()));
    	   hisTrx.setFechaTrx(result[2].toString());
    	   hisTrx.setTipoEvento(result[3].toString());
    	   hisTrx.setCodigoIdTrx(result[4].toString());
    	   hisTrx.setDetalle(result[5].toString());
    	   
    	   lista.add(hisTrx);
       }
       
       em.close();
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al listar Historias Trx Compen: '"+ex);
   return lista;
  }finally {
	  em.close();
  }
       
  return lista;
 }
 
 /**
  * subida reporte compensacion
  * @param idTrx
  * @return lista de historias de trx de compensación
  */
 public int upReporteCompensacion(byte[] reporte, String nameReport) 
 {
	 int idRptTrx = -1;
  try
  {
	  
	String codigoEmisor = nameReport.substring(4, 7);
	int idEmisor = Integer.parseInt(codigoEmisor);
	EmisorDAL emisor = new EmisorDAL();
	if(!emisor.existeEmisor(idEmisor)) {
		return 0;
	}
	
	
	String codigoCiclo = nameReport.substring(2, 4);
	String fecha = nameReport.substring(7,9) + "-" + nameReport.substring(9,11) + "-" + nameReport.substring(11,13) +
			" " + nameReport.substring(13,15) + ":" + nameReport.substring(15,17)
			+ ":" + nameReport.substring(17,19); 
	;
	int ciclo = 0;
	
	switch(codigoCiclo) {
	case "10":
		ciclo = 1;
		break;
	case "15":
		ciclo = 2;
		break;
	default:
		ciclo = 0;
	}
	
	Blob reportB = new javax.sql.rowset.serial.SerialBlob(reporte);
	
	em = emf.createEntityManager();
    StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Compensacion.sp_upl_reporteCompensacion");
     
   
    query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	query.setParameter(1,idEmisor);

    query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	query.setParameter(2,nameReport);

	query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
	query.setParameter(3,ciclo);
	
	query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
	query.setParameter(4,fecha);
	
	query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
	query.setParameter(5,"PROCESADO");
	
    query.registerStoredProcedureParameter(6, Blob.class, ParameterMode.IN);
	query.setParameter(6,reportB);
	
	//Parámetros salida
	 query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT);
	
   query.execute();
	  
   idRptTrx = Integer.parseInt(query.getOutputParameterValue(7).toString());
	   
  }
  catch(Exception ex) 
  {
     LOGGER.debug("Error upReporteCompensacion: "+ex);
     return idRptTrx;
  }finally {
	  em.close();
	  
  }
  return idRptTrx;
 }
 
 /** descarga reporte compensacion
 * @param idTrx
 * @return lista de historias de trx de compensación
 */
public Reporte  downReporteCompensacion(int id_reporte) throws IOException {
	Blob blob = null;
	String nameReport = "";
	Reporte report = new Reporte();
	try
	  {
	       em = emf.createEntityManager();
	       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Compensacion.sp_dow_reporteCompensacion");
	        
	       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		   query.setParameter(1,id_reporte);
		   
		   query.registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);
			 
	       query.execute();
	       
	       @SuppressWarnings("unchecked")
	       List<Object[]> listRes = query.getResultList();
	       
	       for(Object[] result : listRes) 
	       {
	    	   nameReport = result[0].toString();
	    	   blob = (Blob) result[1];
	       }
	       
	       if(blob != null) {
	    	   int myblobLength = (int) blob.length();  
		       byte[] file = blob.getBytes(1, myblobLength);
		       blob.free();
		       
		      report.setFileContent(file);
		      report.setNameFile(nameReport);   
	       }
	       
	       
		  return report;
	  }
	  catch(Exception ex) 
	  {
	   LOGGER.error("Error al listar Historias Trx Compen: '"+ex);
	   return report;

	  }finally {
		    em.close();

	  }
	
	}

 /** 
  * insert trx reporte compensacion
  * @param idTrx
  * @return lista de historias de trx de compensación
  */
 public void insertTrx(String codigoInsitutcion, String tipoTrx, String monto, String numeroCredito, int idReporte) {
		int idRptTrx = 0;
		try
		  {

			em = emf.createEntityManager();
		    StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Compensacion.sp_ins_trxCompensacion");
		     
		    String codigoInst = codigoInsitutcion.substring(0,4);
		    
		    query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
			query.setParameter(1,idReporte);

		    query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.setParameter(2,codigoInst);

			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.setParameter(3,tipoTrx);
			
			query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
			query.setParameter(4,Integer.parseInt(numeroCredito));
			
			query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
			query.setParameter(5,Integer.parseInt(monto.replaceAll(",", "")));
			
		    query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
			query.setParameter(6,"P");
			
			//Parámetros salida
			query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT);
			 
		    query.execute();
			   
		    idRptTrx = Integer.parseInt(query.getOutputParameterValue(7).toString());
			
		  }
		  catch(Exception ex) 
		  {
		     LOGGER.error("Error insertTrx: "+ex);
		     
		  }finally {
			  em.close();
			  insertTrxHistoria(idRptTrx, "Creación", "SINCODIGO", "Creacion de trx a partir de reporte de compensación");

		  }
		 
	
	}
 
 /** 
  * insert trx reporte compensacion historia
  * @param idTrx
  * @return lista de historias de trx de compensación
  */
 public void insertTrxHistoria(int idRptTrx, String tipoEvento, String codidoRptTrx, String detalle) {
		try
		  {
			em = emf.createEntityManager();
		    StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Compensacion.sp_ins_trxCompensacionHist");
		     
		    query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
			query.setParameter(1,idRptTrx);

		    query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.setParameter(2,tipoEvento);

			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.setParameter(3,codidoRptTrx);
			
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.setParameter(4,detalle);
			
			//Parámetros salida
			query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.OUT);
			 
		    query.execute();
			   			   
		  }
		  catch(Exception ex) 
		  {
		     LOGGER.debug("Error upReporteCompensacion: "+ex);
		     
		  }finally {
			  em.close();
			  
		  }
	}
 
 /**
  * Obtener los registros formateados de las trx-compen para el archivo de interfaz
  * @param fechaIni fecha inicial
  * @param fechaFin fecha final
  * @return lista de datos formateados para interfaces
  */
 @SuppressWarnings("unused")
 public String[] obtenerRegistrosFormateadosInterfazArchivoTrxCompen(LocalDate fechaIniC, LocalDate fechaFinC)
 {
	 try
	  {
	       em = emf.createEntityManager();
	       StoredProcedureQuery query = 
	    		   em.createStoredProcedureQuery("pkg_Compensacion.sp_obtenerRegistrosFormateadosInterfazArchivoTrxCompen");

	       query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
	       query.setParameter(1, fechaIniC.toString());
	       
	       query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	       query.setParameter(2, fechaFinC.toString());
	       
	       query.registerStoredProcedureParameter(3, String.class, ParameterMode.REF_CURSOR);
			
	       query.execute();
	       
	       @SuppressWarnings("unchecked")
	       List<String> listResCom = query.getResultList();
	       String[] listaCom = new String[listResCom.size()];
	       int i = 0;
	       for(String resultCom : listResCom) 
	       {
	    	   listaCom[i] = resultCom;
	    	   i++;
	       }
	       return listaCom;
	  }
	  catch(Exception ex) 
	  {
	   LOGGER.error("Error al obtener Registros Formateados Interfaz Archivo Trx Compen: '"+ex);
	   String[] listaCom = {"Error al obtener Registros Formateados Interfaz Archivo Trx Compen: '"+ex};
	   return listaCom;
	  }finally {
		  em.close();
	  }
 }
	
 /******************************************************************************
  * Nombre funcion: inicioRegularizacion.......................................*
  * Action: Inicia proceso de regularizacion...................................*
  * Inp: @idTrx:int............................................................* 
  * Out: lista de trx históricas de compensación...............................*
  ******************************************************************************/
 public String inicioRegularizacion(
		 int idReporte
		 ,int idEmisor
		 ,String tipoTrx
		 ,InformarTefBL infoBL
) throws SQLException 
 {
	 String codigoTrx = "";
  try
  {
	  String fechaInicio = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +  " 00:00:00";
	  String fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 23:59:59";
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Regularizacion.sp_obt_regularizacion");
       LOGGER.debug("Inicio Regularizacion  "); 
       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	   query.setParameter(1, idReporte);
	   
	   query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
	   query.setParameter(2, idEmisor);
	   
       query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
	   query.setParameter(3,tipoTrx);

       query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
	   query.setParameter(4,fechaInicio);

       query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
	   query.setParameter(5,fechaFin);

	   query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.OUT);
	   
	   query.registerStoredProcedureParameter(7, String.class, ParameterMode.OUT);

       query.execute();   
       String codigoidTrx =query.getOutputParameterValue(7).toString();
       LOGGER.debug("DIF :" + query.getOutputParameterValue(6));
       String dif = query.getOutputParameterValue(6).toString()==null?"0":query.getOutputParameterValue(6).toString().replace("-", "");
      
       int difInt = Integer.parseInt(query.getOutputParameterValue(6).toString());

      
      
       String emisorId = Integer.toString(idEmisor);
       LOGGER.debug("CODIGO CCA:" + codigoidTrx + "tiene una diferencia entre el reporte y el cargo en linea (CAA-REPORTE) de " + difInt + ", para el emisor" + emisorId);
       
       for(int i=dif.length(); i<14; i++) {
    	   dif = "0" + dif;
       }
       
       for(int i=emisorId.length(); i<4; i++) {
    	   emisorId = "0" + emisorId;
       }
       
    	   TransaccionDTO tran = new TransaccionDTO();
    	   String codigotrx = strBCOCOMPEN + new SimpleDateFormat(dateStringI).format(new Date()) + idReporte;
    	   String fechaHoy = new SimpleDateFormat(dateStringE).format(new Date());
    	   tran.setMontoTotalEnviadoCiclo(dif);
		   tran.setCodInstitucion(emisorId);
		   tran.setFechaHoraTrx(fechaHoy);
		  
		   tran.setCodTrx(codigotrx);
		   
    	   if(difInt>0) {
    		   infoBL.informarTEFAbono(tran, "Abono","Regularizacion");
    	   }else if(difInt<0) {
    		   infoBL.informarTEF(tran, "Cargo","Regularizacion",true);
    	   }
    	   Thread.sleep(10000);
    	   String cargoSinCCA = "SINCARGOCCA";
    	   if(codigoidTrx.equals(cargoSinCCA)) {
    		   String codigoTrxRegularizacion = tranDAL.obtenerUltimaTransaccionId();
    		   codigoTrx = codigoTrxRegularizacion;
    	   }else {
    		   codigoTrx = codigoidTrx;   
    	   }
    	   
    	   
       
       
       em.close();
  
  }
  catch(InterruptedException ex) 
  {

	  
	  
	  LOGGER.error("Error al regularizar: '"+ex);
   return "Error al regularizar: '"+ex;
  }finally {
	  em.close();
  }
       
  return "OK:" + codigoTrx;
 }
 
 /******************************************************************************
  * Nombre funcion: inicioRegularizacion.......................................*
  * Action: Inicia proceso de regularizacion...................................*
  * Inp: @idTrx:int............................................................* 
  * Out: lista de trx históricas de compensación...............................*
  ******************************************************************************/
 public String setTotalReporteCompen(
		 int idReporte
		 ,String tipoTrx
		 ,String codigoTrx
		 ,String idEmisor
		 ,InformarTefBL infoBL
) throws SQLException 
 {
  
  try
  {
	  
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Regularizacion.sp_obt_total");
        
       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	   query.setParameter(1, idReporte);
	   
       query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	   query.setParameter(2,tipoTrx);

	   query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT);

       query.execute();
          
       String result = "";
    
       String monto = query.getOutputParameterValue(3).toString();
       for(int i=monto.length(); i<14; i++) {
    	   monto = "0" + monto;
       }
       
       for(int i=idEmisor.length(); i<4; i++) {
    	   idEmisor = "0" + idEmisor;
       }
       
    	   TransaccionDTO tran = new TransaccionDTO();
    	   String fechaHoy = new SimpleDateFormat(dateStringE).format(new Date());
		   
    	   tran.setCodInstitucion(idEmisor);
		   tran.setFechaHoraTrx(fechaHoy);
	
		   tran.setMontoTotalEnviadoCiclo(monto);
		   
		   String codigotrx = strBCOCOMPEN + new SimpleDateFormat(dateStringI).format(new Date()) + idReporte + idEmisor;
		   tran.setCodTrx(codigotrx);
		   
		   Emisor emi = facDAL.getEmiDAL().obtenerEmisor(Integer.parseInt(idEmisor));

		   if(tipoTrx.equals(tipoTrxEnviado)) {
			   tran.setCodigoIdTransaccion(codigotrx);
			   tran.setCodTrx(codigoTrx);
			   servBL.ejecutarJCR(tran,emi,"BCOCOMPENCAR", emi.getNombreEmisor());
		   }else {			  			   
			   infoBL.informarTEFAbono(tran, "Abono","Compensacion");
			   Thread.sleep(10000);
			   result = tranDAL.obtenerUltimaTransaccionId();
			   tran.setCodTrx(result);
			   tran.setCodigoIdTransaccion(codigotrx);
			   servBL.ejecutarJCR(tran,emi,"BCOCOMPENABO",emi.getNombreEmisor());   
		   }
	       
       em.close();
       
       return "OK:" + result ;
  
  }
  catch(InterruptedException ex) 
  {
	  LOGGER.error("Error al ejecutar JCR total: '"+ex);
   	  return "Error al ejecutar JCR total: '"+ex;
  }finally {
	  em.close();
  }
       
  
 }
 
 /******************************************************************************
  * Nombre funcion: inicioRegularizacion.......................................*
  * Action: Inicia proceso de regularizacion...................................*
  * Inp: @idTrx:int............................................................* 
  * Out: lista de trx históricas de compensación...............................*
  ******************************************************************************/
 public String setDetalleReporteCompen(
		 int idReporte
		 ,String tipoTrx
		 ,String codigoTrx
		 ,String codigoEmisor
) throws SQLException 
 {
    
  try
  {	  
       em = emf.createEntityManager();
       StoredProcedureQuery query = em.createStoredProcedureQuery("pkg_Regularizacion.sp_obt_detComp");
        
       query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
	   query.setParameter(1, idReporte);
	   
       query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
	   query.setParameter(2,tipoTrx);

	   query.registerStoredProcedureParameter(3, Transaccion.class, ParameterMode.REF_CURSOR);

       query.execute();
              
       @SuppressWarnings("unchecked")
       List<Object[]> listRegCm = query.getResultList();
       int indice = 0;
       for(Object[] resultRegCm : listRegCm) 
       {
    	   TransaccionDTO tran = new TransaccionDTO();
    	   String fechaHoy = new SimpleDateFormat(dateStringE).format(new Date());
    	   tran.setMontoTotalEnviadoCiclo(resultRegCm[6].toString());
		   tran.setCodInstitucion(resultRegCm[2].toString());
		   tran.setFechaHoraTrx(fechaHoy);

		   
		   Emisor emi = facDAL.getEmiDAL().obtenerEmisor(Integer.parseInt(codigoEmisor));
		   String codigotrx = strBCOCOMPEN + new SimpleDateFormat(dateStringI).format(new Date()) + idReporte + indice;
		   tran.setCodTrx(codigoTrx);
		   tran.setCodigoIdTransaccion(codigotrx);
    	   String nomInstitucion = resultRegCm[8].toString();
    	   if(tipoTrx.equals(tipoTrxEnviado)) {
			  
    		   servBL.ejecutarJCR(tran,emi,"BCOCOMPENABO",nomInstitucion);
		   }else {
			   
			   servBL.ejecutarJCR(tran,emi,"BCOCOMPENCAR",nomInstitucion);
		   }
    	   
    	   indice++;
       }
       
       em.close();
  
  }
  catch(Exception ex) 
  {
   LOGGER.error("Error al cargar detalee JCR: '"+ex);
   return "Error al cargar detalee JCR: '"+ex;
  }finally {
	  em.close();
  }
       
  return "OK";
 }
 
 
 
}