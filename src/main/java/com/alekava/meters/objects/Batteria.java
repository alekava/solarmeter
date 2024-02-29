package com.alekava.meters.objects;

public class Batteria {
	
	private double stato;
	private double capacita;
	
	public Batteria() {}

	/**
	 * Ritorna il valore di eccedenza nel caso in cui sia piena
	 * @param valore
	 * @return
	 */
	public double carica(double valore) {
		if(this.stato + valore > capacita) {
			this.stato = capacita;
			return this.stato + valore - capacita;
		}else {
			this.stato = this.stato + valore;
		}
		return 0;
	}
	
	/**
	 * Ritorna il valore di difetto nel caso in cui il consumo sia maggiore della capacita residua
	 * @param valore
	 * @return
	 */
	public double usa (double valore) {
		if(this.stato < valore) {
			this.stato = 0;
			return valore - this.stato;
		}else {
			this.stato = this.stato - valore;
		}
		return 0;
	}
	
	public double getStato() {
		return stato;
	}

	public void setStato(double stato) {
		this.stato = stato;
	}

	public double getCapacita() {
		return capacita;
	}

	public void setCapacita(double capacita) {
		this.capacita = capacita;
	}
	
	
}
