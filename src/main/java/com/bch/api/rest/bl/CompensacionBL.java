package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bch.api.rest.dal.CompensacionDAL;
import com.bch.api.rest.dal.InstitucionDAL;
import com.bch.api.rest.dto.ListaResultadoPaginacionDTO;
import com.bch.api.rest.dto.ResponseTrxCompenHistoriaDTO;
import com.bch.api.rest.entities.Reporte;
import com.bch.api.rest.utils.FuncionesGenerales;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de negocio encargada de operar las transacciones
 * @author 160k
 *
 */
@Component
public class CompensacionBL {

 private static final Logger LOGGER = Logger.getLogger(CompensacionBL.class);
 
 @Autowired
 private CompensacionDAL compenDAL;
 
 private String header = "";
 
 private int countTrx = 0;
 private int countRinstitucion = 0;
 private String codigoInsitutcion = "";
 private String tipoTrx = "";
 private String line;
 private String monto = "";
 private String numeroCredito = "";
 private static String errorJCR= "Error carga JCR detalle ";
 private static String errorRPT= "Error obtenerReportesHistorico: ";
 /**
  * Verifica si existe un emisor por su codigo
  * @return
  * @throws SQLException 
  */
 public boolean existeReporte(String nombreReporte) throws SQLException  
 {
 
	return compenDAL.existeReporte(nombreReporte);
 }
 
 
 
 
 /**
  * Consulta los reportes que se han realizado a lo largo del tiempo
  * @param fechaIni fecha inicial (dd-mm-yyyy)
  * @param fechaFin fecha final (dd-mm-yyyy)
  * @param idCodigoInstitucion codigo de institución
  * @param pageSize tamaño de la paginación
  * @param pageNumber numero de página a consultar
  * @return
  */
 public ListaResultadoPaginacionDTO obtenerReportesHistorico(
		 String fechaIni, 
		 String fechaFin,
		 int idCodigoInstitucion, 
		 int pageSize, 
		 int pageNumber) 
 {
	 //--- Limitar tamaños string
	 fechaIni = FuncionesGenerales.limitarTamanoString(fechaIni, 10);	//-- dd-mm-yyyy
	 fechaFin = FuncionesGenerales.limitarTamanoString(fechaFin, 10);	//-- dd-mm-yyyy
	 
	 //--- Clear XSS
	 fechaIni = FuncionesGenerales.cleanXSS(fechaIni);
	 fechaFin = FuncionesGenerales.cleanXSS(fechaFin);
	 
	 fechaIni = (!fechaIni.isEmpty())?FuncionesGenerales.fecbaStandard(fechaIni):"";
	 fechaFin = (!fechaFin.isEmpty())?FuncionesGenerales.fecbaStandard(fechaFin):"";
	 
	 ListaResultadoPaginacionDTO objRes = new ListaResultadoPaginacionDTO();
	 try {
		 objRes = compenDAL.obtenerReportesHistorico(
				 fechaIni,
				 fechaFin,
				 idCodigoInstitucion, 
				 pageSize, 
				 pageNumber);
	} catch (SQLException e) {
		LOGGER.debug(errorRPT+e);
	}
	 return objRes;
 }
 
 /**
  * obtenerRepoCompen24hh Obtener Reportes de compensación de as ultimas 24hh
  * @param numRegistrosPagina cantidad de registros por página
   * @param pageNumber página actual
  * @return lista de reportes de compensación de las ultimas 24 Horas
  */
 public ListaResultadoPaginacionDTO obtenerRepoCompen24hh(int numRegistrosPagina, int pageNumber) 
 {
  try
  {
	 return compenDAL.obtenerRepoCompen24hh(numRegistrosPagina, pageNumber);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return  new ListaResultadoPaginacionDTO();
  }
 }
 
 /**
  * Obtener listado de trx de compensación históricos
  * @param fechaIni fecha inicio
  * @param fechaFin fecha fin
  * @param idCodigoInstitucion codigo institucion
  * @param pageNumber número de página del resultado a visualizar
  * @return
  */
 public ListaResultadoPaginacionDTO obtenerTrxHistoricasCompensacion(
		 int codigoEmisor
		 ,int codigoInstitucion
		 ,String idTipoTrx
		 ,String idEstadoTrx
		 ,String fechaIni
		 ,String fechaFin
		 ,int pageNumber) 
 {
	//--- Limitar tamaños string
	 idTipoTrx = FuncionesGenerales.limitarTamanoString(idTipoTrx, 1);	//-- R,P,A, etc

	 idEstadoTrx = FuncionesGenerales.limitarTamanoString(idEstadoTrx, 1);	//-- R,P,A, etc

	 fechaIni = FuncionesGenerales.limitarTamanoString(fechaIni, 10);	//-- dd-mm-yyyy

	 fechaFin = FuncionesGenerales.limitarTamanoString(fechaFin, 10);	//-- dd-mm-yyyy

	 
	 //--- Clear XSS
	 idTipoTrx = FuncionesGenerales.cleanXSS(idTipoTrx);
	
	 idEstadoTrx = FuncionesGenerales.cleanXSS(idEstadoTrx);

	 fechaIni = FuncionesGenerales.cleanXSS(fechaIni);

	 fechaFin = FuncionesGenerales.cleanXSS(fechaFin);

	 fechaIni = (!fechaIni.isBlank())?FuncionesGenerales.fecbaStandard(fechaIni):"";

	 fechaFin = (!fechaFin.isEmpty())?FuncionesGenerales.fecbaStandard(fechaFin):"";

	 
	 ListaResultadoPaginacionDTO objRes = new ListaResultadoPaginacionDTO();
	 try {
		 objRes = compenDAL.obtenerTrxHistoricasCompensacion(
				 codigoEmisor
				 ,codigoInstitucion
				 ,idTipoTrx
				 ,idEstadoTrx
				 ,fechaIni
				 ,fechaFin
				 ,pageNumber);
	} catch (SQLException e) {
		LOGGER.debug(errorRPT+e);
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
  try
  {
	 return compenDAL.listarHistoriasTrxCompen(idTrx);
  }
  catch(Exception ex) 
  {
     LOGGER.debug(errorRPT+ex);
     return new ArrayList<ResponseTrxCompenHistoriaDTO>();
  }
 }
 
 /**
  * subida reporte compensacion
  * @param idTrx
  * @return lista de historias de trx de compensación
  */
 public int upReporteCompensacion(byte[] reporte, String nameReport) 
 {
  try
  {
	 return compenDAL.upReporteCompensacion(reporte, nameReport);
  }
  catch(Exception ex) 
  {
     LOGGER.debug("Error upReporteCompensacion: "+ex);
     return 0;
  }
 }
 
 /**
  * descarga reporte compensacion
  * @param idTrx
  * @return lista de historias de trx de compensación
  */
 public Reporte downReporteCompensacion(int idReporte) 
 {
  try
  {
	 return compenDAL.downReporteCompensacion(idReporte);
  }
  catch(Exception ex) 
  {
     LOGGER.debug("Error downReporteCompensacion: "+ex);
     Reporte report = new Reporte();
     return report;
     
  }
  	
 }
 
 /**
  * carga archivo reporte compensacion
  * @param idTrx
  * @return lista de historias de trx de compensación
 * @throws FileNotFoundException 
 * @throws SQLException 
 * @throws NumberFormatException 
 * @throws IOException 
  */
  public String cargaArchivo(File file, int idReporte) throws FileNotFoundException  {

	  	 header = "";
	  	 countTrx = 0;
	  	 countRinstitucion = 0;
	  	 codigoInsitutcion = "";
	     tipoTrx = "";
	     monto = "";
	     numeroCredito = "";
	     line = "";
	     InstitucionDAL institucion = new InstitucionDAL();
	     BufferedReader br = new BufferedReader(new FileReader(file));

	     try  {
	    	 while ((line = br.readLine()) != null) {
	    		 if (!line.isEmpty()) {
	    			 String[] resultadoSplit = line.split(":");
	    			 iniciaLectura(resultadoSplit,idReporte,institucion);
	    		 }
	    	 }
	     }catch(IOException | SQLException ex) {
	    	 LOGGER.debug("Error carga trx reporte compensacion: "+ex);
	     }finally {
	    	 try {
				br.close();
			} catch (IOException e) {
				LOGGER.debug("Error carga trx reporte compensacion al cerrar buffer reporte: "+e);
			}
	     }
	  return "Se procesaron "  + countRinstitucion + " Instituciones con un total de "
	     + countTrx + " registros " + header;	    
  }
  
  public void iniciaLectura(String[] resultadoSplit, int idReporte, InstitucionDAL institucion) throws SQLException {
	  if(resultadoSplit.length > 1) {
			 String opcion = resultadoSplit[0];
			 opcion = setOpcion(opcion);
			 seteaOpcion(opcion,resultadoSplit);
			 validaInsertTrxComp(institucion,idReporte);
		 }
  }
  
  public void seteaOpcion(String opcion,String[] resultadoSplit) {
	  switch(opcion) {
		case"ID":
			codigoInsitutcion = resultadoSplit[1].replaceAll(" ","");
			break;
		case"ITEMS RCVD":
			setItemRecibidos(resultadoSplit);
			break;
		case"ITEMS ENVS":
			setItemEnviados(resultadoSplit);
			break;
		default:
			break;
	 }
  } 
  
  public void setItemEnviados(String[] resultadoSplit) {
	  tipoTrx = "E";
	  monto=resultadoSplit[1].substring(51,71).replaceAll(" ","").replace("(", "").replace(")", "");
	  numeroCredito = resultadoSplit[1].substring(12,23).replaceAll(" ","");
	  
  }
  
  public void setItemRecibidos(String[] resultadoSplit) {
	  tipoTrx = "R";
	
	  monto=resultadoSplit[1].substring(51,71).replaceAll(" ","").replace("(", "").replace(")", "");
	  numeroCredito = resultadoSplit[1].substring(12,23).replaceAll(" ","");
  }
  
  public String setOpcion(String opcion) {
	  if(opcion.indexOf("   ID")>-1) {
			 opcion = "ID";
		 }else if(opcion.indexOf("ITEMS RCVD")>-1) {
			 opcion = "ITEMS RCVD";
		 }else if(opcion.indexOf("ITEMS ENVS")>-1) {
			 opcion = "ITEMS ENVS";
		 }
		 
		return opcion;
  }
  
  public void validaInsertTrxComp(InstitucionDAL institucion, int idReporte) throws SQLException {

	  if(!codigoInsitutcion.isEmpty() && !tipoTrx.isEmpty() && !monto.isEmpty()) {
		  if(institucion.existeInstitucion(Integer.parseInt(codigoInsitutcion.substring(0,4)))) {			 	 
				 compenDAL.insertTrx(codigoInsitutcion,tipoTrx,monto,numeroCredito,idReporte);
				 
				 seteaVariables();
				
			 }else {
				 if(header.isEmpty()) {
					 header += " | Alterta códigos instituciones no existen " + codigoInsitutcion;
				 }else {
					 header += "," + codigoInsitutcion;
				 }
				 codigoInsitutcion="";
			 }
	  }
		 
  }
    
  public void seteaVariables() {

		 switch(tipoTrx) {
			case"E":
				codigoInsitutcion="";
				countTrx ++;
				countRinstitucion++;
				break;
			case"R":
				countTrx ++;
				break;
			default:
				break;
		 }
		 
		 tipoTrx = "";
		  numeroCredito = "";
		  monto = "";
  }
  
  public String setRegularizacion(int idReporte, String emisor, InformarTefBL infoBL) {
	  
	 try {
		return compenDAL.inicioRegularizacion(idReporte, Integer.parseInt(emisor),"E",infoBL);
	} catch (NumberFormatException | SQLException e) {
		
		LOGGER.error("Error regularizacion "+ e);
		return "Error regularizacion "+ e;
	}
  }
  
  public String setTotalesReporteCompensacion(int idReporte,String tipoTrx ,String codigoTrx,
		  String idEmisor, InformarTefBL infoBL) {
	  
	  try {
			return compenDAL.setTotalReporteCompen(idReporte, tipoTrx,codigoTrx,idEmisor,infoBL );
		} catch (NumberFormatException | SQLException e) {
			
			LOGGER.error(errorJCR  +tipoTrx +": "+ e);
			return "Error carga total JCR "  +tipoTrx +": "+ e;
		}

  }

  public String setDetalleReporteCompensacion(int idReporte,String tipoTrx , String codigoTrx, String idEmisor) {
	  
	try {
		return compenDAL.setDetalleReporteCompen(idReporte, tipoTrx,codigoTrx, idEmisor );
	} catch (NumberFormatException | SQLException e) {
		
		LOGGER.error(errorJCR  +tipoTrx +":"+ e);
		return errorJCR  +tipoTrx +":"+ e;
	}
	
  }

  public String inicioProcesoCompensacion( InformarTefBL infoBL, int idReporte, String nameReport,
		String okStr, File convFile) throws IOException {
	  String Result = "";
	try {
		String codigoTrxCargo = "";
			
			Result += cargaArchivo(convFile,idReporte);
			codigoTrxCargo = setRegularizacion(idReporte, nameReport.substring(4, 7),infoBL);
			String[] resultadoSplit = codigoTrxCargo.split(":");
			String ResultQ = resultadoSplit[0];
			if(ResultQ.equals(okStr)){
	 			String codigoTrx =resultadoSplit[1]; 
				setTotalesReporteCompensacion(idReporte,"E",codigoTrx,nameReport.substring(4, 7),infoBL );
				setDetalleReporteCompensacion(idReporte,"E",codigoTrx,nameReport.substring(4, 7));
	 			String result = setTotalesReporteCompensacion(idReporte,"R",codigoTrx,
	 					nameReport.substring(4, 7),infoBL);
	 			if((result.split(":")[0]).equals(okStr)) {
	 				codigoTrx=result.split(":")[1];
	 	 			setDetalleReporteCompensacion(idReporte,"R",codigoTrx,nameReport.substring(4, 7));	

	 			}else {
	 			
	 				Result += "500 - Error al cargar reporte." + result;
	 			}
			}else {
				Result += "500 - Error al cargar reporte." + codigoTrxCargo;
			}
	} catch (NumberFormatException e) {
		
		LOGGER.error(errorJCR  +tipoTrx +":"+ e);
		return Result;
	}
	
	
	return Result;
  }

	 /**
* Obtiene los registros formateados de trx de compensación desde la BD para el archivo de interfaz
* @return lista de registros
*/
public String[] obtenerRegistrosFormateadosInterfazArchivoTrxCompen(LocalDate fechaIni, LocalDate fechaFin)
{
return compenDAL.obtenerRegistrosFormateadosInterfazArchivoTrxCompen(fechaIni, fechaFin);
}
  
}
