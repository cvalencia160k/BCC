package com.bch.api.rest;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
 
 /***********************************************************
  * Nombre funcion: configure...............................*
  * Action: Inicializa conf App.............................*
  * Inp: @application : SpringApplicationBuilder => app ini.* 
  * Out: @application : SpringApplicationBuilder => app ini.*
  **********************************************************/
 @Override
 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  return application.sources(ApiRestApplication.class);
 }
}