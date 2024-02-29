package com.alekava.meters.objects;

public class QuartoOrario {

	private String orario;
	
	// l'immessa con segno positivo 
	private double immessa;
	
	// la prelevata con segno negativo
	private double prelevata;
	
	// il bilancio e' un numero 
	private double bilancio;

	private int completezza;
	
	public int getCompletezza() {
		return completezza;
	}
	
	public void incrementaCompletezza() {
		completezza++;
	}
	
	public boolean isCompleto() {
		return completezza == 2;
	}
	
	
	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public double getImmessa() {
		return immessa;
	}

	public void setImmessa(double immessa) {
		this.immessa = immessa;
	}

	public double getPrelevata() {
		return prelevata;
	}

	public void setPrelevata(double prelevata) {
		this.prelevata = prelevata;
	}

	public double getBilancio() {
		return bilancio;
	}

	public void setBilancio(double bilancio) {
		this.bilancio = bilancio;
	}
	
	
	
}
