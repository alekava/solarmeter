package com.alekava.meters.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Periodo {

	HashMap<Date, Giorno> giorni;
	
	ArrayList<String> fasceOrarie = new ArrayList<>();
	
	public Periodo() {
		giorni = new HashMap<Date, Giorno>(); 
	}
	
	public HashMap<Date, Giorno> getGiorni() {
		return giorni;
	}
	
	public ArrayList<String> getFasceOrarie(){
		return fasceOrarie; 
	}
	
	
}
