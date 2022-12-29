package com.bch.api.rest.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Capa de negocio encargada de trabajar con interfaces de archivo de texto plano
 * @author 160k
 *
 */
@Component
public class InterfazArchivoBL {

 @Autowired
 private CompensacionBL compenBL;
 @Autowired
 private TarifaBL tariBL;
	
 private static final Logger LOGGER = Logger.getLogger(InterfazArchivoBL.class);
 
 /**
  * Procesa un tipo de interfaz de archivo definido
  * @param tipoInterfaz "trx" o "tarifa"
  * @return
  */
 public ResponseEntity<InputStreamResource> procesarInterfazArchivo(String tipoInterfaz)
 {
	 String tarifaStr="tarifas";
	 String trxcompenStr="trx-compen";
	 
	 try
	    {
	 if(!tipoInterfaz.equals(trxcompenStr) && !tipoInterfaz.equals(tarifaStr)) 
	 {
		 LOGGER.debug("ERROR al seleccionar tipo de interfaz: Se debe indicar 'trx-compen' o 'tarifas' unicamente");
		 return null;
	 }
	 
	 //--- Obtener fechas actuales de consulta
	 LocalDate initial = LocalDate.now();
	 LocalDate start = initial.withDayOfMonth(1);
	 LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));
	 
	 //--- Directorio final + nombreArchivo	 
	 String directorioArchivo = "interfaces-archivos\\";
	 String nombreArchivo = "";
	 if(tipoInterfaz.equals(trxcompenStr)) 
	 {
		 
		 this.limpiarDirectorioPrevio(directorioArchivo,"interfaz-trx\\");
		 
		 nombreArchivo = "BCC_TRANSACCIONES.txt";
		 directorioArchivo+="interfaz-trx\\" + nombreArchivo;
	 }
	 if(tipoInterfaz.equals(tarifaStr)) 
	 {
		 
		 this.limpiarDirectorioPrevio(directorioArchivo,"interfaz-tarifas\\");
		 
		 nombreArchivo = "BCC_TARIFAS.txt";
		 directorioArchivo+= "interfaz-tarifas\\" + nombreArchivo;
	 }	 
	 
	 //---- Obtener la data desde la BD
	 String[] lineasObt = this.obtenerLineasArchivo(tipoInterfaz, start, end);
	 
	 
	 if(lineasObt!=null) {
		 desFile(lineasObt,directorioArchivo);
		    //---- Descargar el archivo automáticamente
		    return this.descargarArchivo(directorioArchivo, nombreArchivo);		    
	    
	    
 	}
	    }
	    catch (IOException ex)
	    {
	    	LOGGER.debug("Error al generar el archivo para la interfaz de archivo: "+ex.getMessage());
	    	LOGGER.debug("Error al generar el archivo para la interfaz de archivo: "+ex);

	    	return null;
	    }
	 
	 return null;
 }
 
 /**
  * Obtiene las lineas formateadas según tipo de interfaz
  * @param tipoInterfaz
  * @param fechaIni fecha inicial
  * @param fechaFin fecha final
  * @return
  */
 private String[] obtenerLineasArchivo(String tipoInterfaz, LocalDate fechaIni, LocalDate fechaFin)
 {
	 String[] tarif = {""}; 
	 String trxCompen="trx-compen";
	 String tarifas="tarifas";
	 
	 if(tipoInterfaz.equals(trxCompen)) 
	 {
		 return compenBL.obtenerRegistrosFormateadosInterfazArchivoTrxCompen(fechaIni, fechaFin);
	 }
	 if(tipoInterfaz.equals(tarifas)) 
	 {
		 return tariBL.obtenerRegistrosFormateadosInterfazArchivoTarifas(fechaIni, fechaFin);
	 }
	 
	 return tarif;
 }
 
 /**
  * Elimina el directorio y lo vuelve a crear
  * @param dir directorio a eliminar
  */
 public void limpiarDirectorioPrevio(String dir, String dirRPT) 
 {
	 
	 
	 File index = new File(dir);
	 if(!index.exists()) {
		 index.mkdir();
	 }
	 
	 File indexRpt = new File(dir + dirRPT);
	 if(!indexRpt.exists()) {
		 indexRpt.mkdir();
	 }

	 String[]entries = indexRpt.list();
	 for(String s: entries)
	 {
	     File currentFile = new File(indexRpt.getPath(),s);
	     currentFile.delete();
	 }
 }
  
 /**
  * Descargar el archivo fisico
  * @param rutaArchivo ruta del archivo a descargar
  * @param nombreArchivo nombre del archivo que se descargará
  * @return
 * @throws IOException 
  */
 public ResponseEntity<InputStreamResource> descargarArchivo(String rutaArchivo, 
		 String nombreArchivo) throws IOException
 {
	//---- Descargar el archivo automáticamente
	    byte[] bytes = Files.readAllBytes(Paths.get(rutaArchivo));
	    InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Disposition", String.format("attachment; filename=" + nombreArchivo));    
      return ResponseEntity.ok()
              .headers(headers)
              .contentLength(bytes.length)
              .contentType(MediaType.parseMediaType("application/octet-stream"))
              .body(resource);
 }
 
 
 public void desFile(String[] lineasObt, String directorioArchivo){
	 
	 int size = lineasObt.length;
	 String[] lineasArchivo = new String[size+2];
	 int ln = 0;
	 
	 String fechaHeader = new SimpleDateFormat("yyyyMMdd").format(new Date());
	 
	 lineasArchivo[ln] = "HEADER"+fechaHeader;
	 for(ln=1;ln<=size;ln++) 
	 {
		 lineasArchivo[ln] = lineasObt[ln-1].replace(".", "").replace(".", "");
		 LOGGER.debug("Linea " + lineasObt[ln-1].replace(".", "").replace(".", ""));
	 }
	 lineasArchivo[lineasArchivo.length-1] = "TRAILER"+String.format("%06d",lineasObt.length);
	 
	 //---- Generar el archivo

	    String encoding = "UTF-8";
	    
		    try (PrintWriter writer = new PrintWriter(directorioArchivo, encoding)) 
		    {
				for(ln=0;ln<lineasArchivo.length;ln++)
				{
					writer.println(lineasArchivo[ln]);
				}
				writer.close();				
			} catch (FileNotFoundException e) {
				LOGGER.debug("Error al generar el archivo para la interfaz de archivo: "+e.getMessage());
		    	LOGGER.debug("Error al generar el archivo para la interfaz de archivo: "+e);
			} catch (UnsupportedEncodingException e) {
				LOGGER.debug("Error al generar el archivo para la interfaz de archivo: "+e.getMessage());
		    	LOGGER.debug("Error al generar el archivo para la interfaz de archivo: "+e);
			}
		    
		    LOGGER.debug("OK descargando archivo de interfaz: "+directorioArchivo);
		    
	 
 }
 
}
