package com.bch.api.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import com.bch.api.rest.bl.DashboardBL;
import com.bch.api.rest.dto.CuantificadoresOpcMenuDTO;
import com.bch.api.rest.dto.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controlador para Dashboard inicial
 * @author 160k
 */
@RestController
public class DashboardController {

 @Autowired
 private DashboardBL dashBL;
 
 /*****************************************************************************************************
  * Nombre funcion: listarTrxRechazadas...............................................................*
  * Action: obtiene los datos iniciales del dashboard.................................................*
  * Inp: .............................................................................................* 
  * @return datos para el dashboard...................................................................*
  *****************************************************************************************************/
 @RequestMapping(value= "/dashboard-inicial", method = RequestMethod.GET, produces = "application/json")
 public DashboardDTO listarTrxRechazadas() 
 {
	 return dashBL.obtenerDashboardInicial();
 }
 
 /*****************************************************************************************************
  * Nombre funcion: cuantificadoresOpcMenu............................................................*
  * Action: obtiene los cuantifiadores definidos para las opc de menú.................................*
  * Inp: .............................................................................................* 
  * @return datos de los cuantificadoes de las opc de menú definidas...................................*
  *****************************************************************************************************/
 @RequestMapping(value= "/cuantificadores-menu", method = RequestMethod.GET, produces = "application/json")
 public CuantificadoresOpcMenuDTO cuantificadoresOpcMenu() 
 {
	 return dashBL.cuantificadoresOpcMenu();
 }
}
