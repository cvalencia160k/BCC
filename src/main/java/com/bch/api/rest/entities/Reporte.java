package com.bch.api.rest.entities;

public class Reporte {
	
	private byte[] fileContent;
	
	private String nameFile;
	
	public void setFileContent(byte[] content) {
		this.fileContent = content;
	}
	
	public void  setNameFile(String name) {
		this.nameFile = name;
	}
	
	public byte[] getFileContent() {
		return fileContent;
	}
	
	public String getNameFile() {
		return nameFile;
	}
	
}
