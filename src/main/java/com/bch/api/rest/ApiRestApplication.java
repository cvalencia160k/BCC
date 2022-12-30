package com.bch.api.rest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
@EnableAsync
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class ApiRestApplication extends SpringBootServletInitializer implements WebApplicationInitializer{


 static final Logger logger = Logger.getLogger(ApiRestApplication.class);
 static final String LOG_PROPERTIES_FILE = System.getProperty("APPS_PROPS") + "/Config/log4j.properties";

 /***********************************************************
  * Nombre funcion: main....................................*
  * Action: Inicializa API Rest.............................*
  * Inp: @args : String[] => parametros iniciales de apiRest* 
  * Out : void..............................................*
  **********************************************************/
 public static void main(String[] args)
 {
  new ApiRestApplication();
  SpringApplication.run(ApiRestApplication.class, args);
 }
 
 
 @Bean
 public WebMvcConfigurer corsConfigurer() {
	  return new WebMvcConfigurer() {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**").allowedMethods("GET","PUT","POST","DELETE").allowedOrigins("*").allowedHeaders("*");
		}
	  };
 }
 
 public ApiRestApplication() {
     initializeLogger();
     // sample info log message
     logger.info("Log4jDemo - leaving the constructor ...");
 }
 
 private void initializeLogger() {
     Properties logProperties = new Properties();
     FileInputStream fstream = null;
     DataInputStream in =null;
     BufferedReader br =null;
     try {
         // load log4j properties configuration file
         logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
         PropertyConfigurator.configure(logProperties);
         logger.info("Logging initialized.");
     } catch (IOException e) {
         logger.error("Unable to load logging property :", e);
     }
     try {
         fstream = new FileInputStream(LOG_PROPERTIES_FILE);
         in = new DataInputStream(fstream);
         br = new BufferedReader(new InputStreamReader(in));
         String strLine;
         while ((strLine = br.readLine()) != null) {
        	 logger.trace(strLine);
         }
        
     } catch (FileNotFoundException fe) {
         logger.error("File Not Found", fe);
         logger.warn("This is a warning message");
         logger.trace("This message will not be logged since log level is set as DEBUG");
     } catch (IOException e) {
         logger.error("IOEXception occured e:", e);
     }finally {
    	 try {
    		 if(fstream!=null) {
    			 fstream.close();
    		 }
			
		} catch (IOException e) {
	         logger.error("IOEXception occured: ", e);

		}
    	 try {
    		 if(in!=null) {
    			 in.close();
    		 }
			
		} catch (IOException e) {
	         logger.error("IOEXception occurede: ", e);

		}
    	 try {
    		 if(br!=null) {
    			 br.close();
    		 }
			
		} catch (IOException e) {
	         logger.error("IOEXception occuredd : ", e);

		}
     }
 }
 
}
