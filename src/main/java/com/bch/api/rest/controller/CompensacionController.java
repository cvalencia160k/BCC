package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bch.api.rest.bl.CompensacionBL;
import com.bch.api.rest.bl.InformarTefBL;
import com.bch.api.rest.dto.ListaResultadoPaginacionDTO;
import com.bch.api.rest.dto.ResponseTrxCompenHistoriaDTO;
import com.bch.api.rest.entities.Reporte;
import com.bch.api.rest.utils.FuncionesGenerales;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Controlador Emisor para ejecución de funcionalidades de la API Banco Compensador
 * @author 160k
 */
@RestController
public class CompensacionController {

 private static final Logger LOGGER = Logger.getLogger(CompensacionController.class);
 
 @Autowired
 private CompensacionBL compenBL;
 @Autowired
 private InformarTefBL infoBL;
 /**
  * Obtener listado de reportes de compensación históricos
  * @param fechaIni fecha inicio
  * @param fechaFin fecha fin
  * @param idCodigoInstitucion codigo institucion
  * @param pageNumber número de página del resultado a visualizar
  * @return compenBL.obtenerReportesHistorico
  */
 @ResponseBody
 @RequestMapping(value= "/consulta-repo-compen-historico", method = RequestMethod.GET)
 public ListaResultadoPaginacionDTO lisTrxHistorias(
		 String fechaIni, 
		 String fechaFin,
		 int idCodigoInstitucion, 
		 int pageNumber) 
 {
  try
  {
	 return compenBL.obtenerReportesHistorico(
			 fechaIni,
			 fechaFin,
			 idCodigoInstitucion, 
			 10, 
			 pageNumber);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return new ListaResultadoPaginacionDTO();
  }
 }
 
 /**
  * Obtener listado de trx de compensación históricos
  * @param codigoEmisor int
  * @param codigoInstitucion int
  * @param idTipoTrx String
  * @param idEstadoTrx String
  * @param fechaIni String
  * @param fechaFin String
  * @param pageNumber int 
  * @return ListaResultadoPaginacionDTO
  */
 @ResponseBody
 @RequestMapping(value= "/consulta-trx-compen-historico", method = RequestMethod.GET)
 public ListaResultadoPaginacionDTO obtenerTrxHistoricasCompensacion(		 
		 int codigoEmisor
		 ,int codigoInstitucion
		 ,String idTipoTrx
		 ,String idEstadoTrx
		 ,String fechaIni
		 ,String fechaFin
		 ,int pageNumber) 
 {
  try
  {
	  LOGGER.debug("Inicia obtener.");
	 return compenBL.obtenerTrxHistoricasCompensacion(
			 codigoEmisor
			 ,codigoInstitucion
			 ,idTipoTrx
			 ,idEstadoTrx
			 ,fechaIni
			 ,fechaFin
			 ,pageNumber);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return new ListaResultadoPaginacionDTO();
  }
 }
 
 /*****************************************************************************************************
  * Nombre funcion: obtenerReportesCompensacion24hh...................................................*
  * Action: obtiene los reportes de compensación de las ultimas 24 horas habiles......................*
  * @param pageNumber => Int..........................................................................* 
  * @return lista de resultado + totalRegistros de la consulta........................................* 
  ****************************************************************************************************/
 @ResponseBody
 @RequestMapping(value= "/obtener-repo-compen-24hh", method = RequestMethod.GET)
 public ListaResultadoPaginacionDTO obtenerRepoCompen24hh(int pageNumber) 
 {
  try
  {
	 return compenBL.obtenerRepoCompen24hh(10, pageNumber);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return  new ListaResultadoPaginacionDTO();
  }
 }
 
 /**
  * Listar historias trx de compensación
  * @param idTrx int
  * @return lista de historias de trx de compensación
  */
 @ResponseBody
 @RequestMapping(value= "/consulta-his-trx-compen", method = RequestMethod.GET)
 public List<ResponseTrxCompenHistoriaDTO> listarHistoriasTrxCompen(int idTrx) 
 {
  try
  {
	 return compenBL.listarHistoriasTrxCompen(idTrx);
  }
  catch(Exception ex) 
  {
   LOGGER.error(ex);
   return new ArrayList<ResponseTrxCompenHistoriaDTO>();
  }
 }
 
 /**
  * Listar historias trx de compensación
  * @param nameReport string
  * @param file MultipartFile
  * @return String
  * @throws IOException => Exépcion global
  * @throws NumberFormatException Exépcion fomato numero
  * @throws FileNotFoundException Exépcion input file
  * @throws SQLException Exépcion SQL
  */
 
 @PostMapping("/up-rpt-compensacion")
 public String uploadReporteCompensacion(@RequestParam String nameReport, 
		 @RequestParam MultipartFile file) throws IOException, SQLException {

	 String tipoRpt = nameReport.substring(0,2);
	 String glosaRpt  = nameReport.substring(3,nameReport.length());
	 
	   if(nameReport.length()!=19 || FuncionesGenerales.esNumerico(tipoRpt) || !FuncionesGenerales.esNumerico(glosaRpt) || compenBL.existeReporte(nameReport)) {
	    	return "error con nombre de reporte o reporte duplicado es decir ya se cargo este reporte en el sistema (" + nameReport +  ")";
	    }	 
       
	String Result = "";
	File convFile = new File( file.getOriginalFilename() );
	FileOutputStream fos = new FileOutputStream( convFile );
	fos.write( file.getBytes() );
	fos.close();
	String okStr = "OK";
 	byte[] bytes = file.getBytes();
 	
 	int largo = bytes.length;
 	
 	if(largo == 0) {
 		return "Error no se puede subir un reporte sin datos";
 	}
 	
 	
 	int idReporte = compenBL.upReporteCompensacion(bytes, FuncionesGenerales.cleanXSS(nameReport));
 	
 	try {
 		switch (idReporte) {
 		case -1:
 			return "300 - Error al cargar reporte.";
 		case 0:
 			return "400 - Error al cargar reporte. codigo emisor no existe";
 		default:
 			Result = "200 - Reporte cargado exitosamente | ";
 			Result += compenBL.inicioProcesoCompensacion(infoBL,idReporte,nameReport, okStr,convFile);
 			
 		break;
 	}
 	}catch(NumberFormatException e) {
 		LOGGER.error("error al subir reporte" + e);
		return "500 - Error al cargar reporte." + e.getMessage();
 	}finally{
 		convFile.delete();
 	} 		

 	return Result;
 }
 
 /**
  * Obtener listado de reportes de compensación históricos
  * @param idReporte int
  * @return ResponseEntity<Resource>
  * @throws IOException Exception execpcion Global.
  * @throws Exception Exception execpcion Global.
  */
 @GetMapping("/download-rpt-compensacion")
 public ResponseEntity<Resource>  downReporteCompensacion(@RequestParam int idReporte) throws IOException {

	
	 try {
		 Reporte file = compenBL.downReporteCompensacion(idReporte);
		 InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(file.getFileContent()));
         HttpHeaders headers = new HttpHeaders();
         headers.set("Content-Disposition", String.format("attachment; filename=" + file.getNameFile()));    
         return ResponseEntity.ok()
                 .headers(headers)
                 .contentLength(file.getFileContent().length)
                 .contentType(MediaType.parseMediaType("application/octet-stream"))
                 .body(resource);

     } catch (Exception e) {
    	  LOGGER.error(e);
    	 return ResponseEntity.notFound().build();

     } 

   

 }
 
}
