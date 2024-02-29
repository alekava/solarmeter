package com.alekava.meters.objects;

import java.util.Date;
import java.util.HashMap;

public class Giorno {

	private Date date;
	private HashMap<String, QuartoOrario> quarti;
	
	
	public Giorno () {
		quarti = new HashMap<>();
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public HashMap<String, QuartoOrario> getQuarti() {
		return quarti;
	}
	public void setQuarti(HashMap<String, QuartoOrario> quarti) {
		this.quarti = quarti;
	}

	
	
	
}
