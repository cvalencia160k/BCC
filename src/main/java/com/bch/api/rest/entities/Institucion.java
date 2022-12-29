package com.bch.api.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/******************************************************************************************
 * Nombre class: Emisor........................................................*
 * Actions: traer topdos atributos........................................................*
 *****************************************************************************************/
@Entity
@Table(name="emisores")
public class Institucion
{
 /**************************************
  *    Atributos
  **************************************/
 @Id
 @Column(name="codigo_institucion")
 private int codigoInstitucion;
 
 @Column(name="nombre_institucion")
 private String nombreInstitucion;

public int getCodigoInstitucion() {
	return codigoInstitucion;
}

public void setCodigoInstitucion(int codigoInstitucion) {
	this.codigoInstitucion = codigoInstitucion;
}

public String getNombreInstitucion() {
	return nombreInstitucion;
}

public void setNombreInstitucion(String nombreInstitucion) {
	this.nombreInstitucion = nombreInstitucion;
}
 
}
