package com.alekava.meters;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JTextArea;

import com.alekava.meters.objects.Batteria;
import com.alekava.meters.objects.Giorno;
import com.alekava.meters.objects.Periodo;
import com.alekava.meters.objects.QuartoOrario;

public class Elaboratore {

	private static  final String NEWLINE = "\n";
	public static final int PERIODI_GIORNO = 96;
	public static final int TIPO_CONSUMI = 1;
	public static final int TIPO_IMMISSIONI = 2;
	public static final int TIPO_PRODUZIONE = 3;

	
	public static Periodo periodo = new Periodo();
	
	public static double totaleConsumiSenzaBatteria = .0;
	public static double totaleImmissioniSenzaBatteria = .0;
	
	public static double totaleConsumiConBatteria = .0;
	public static double totaleImmissioniConBatteria = .0;
	
	
	public static void go(File folderPrelievi, File folderImmissioni, JTextArea log, int produzione, int batteryMin, int batteryMax, double costoKWh, boolean bVerboseLog ) throws FileNotFoundException, ParseException {
	
		totaleImmissioniSenzaBatteria = .0;
		totaleConsumiSenzaBatteria = .0;
		
		try {
			readAllFiles(folderPrelievi, TIPO_CONSUMI);
			readAllFiles(folderImmissioni, TIPO_IMMISSIONI);
		} catch (Exception e) {
			log.append("ASSICURATI DI AVER SELEZIONATO LA CARTELLA PRELIEVI E IMMISSIONI"+ NEWLINE);
		} 
		
		log.append("Calcolo consumi"+ NEWLINE);
		

		senzaBatteria();
		

		log.append("======================="+ NEWLINE);
		log.append("Senza Batteria" + NEWLINE);
		log.append("Totale Produzione  +~> KWh " + String.format("%.2f",Double.valueOf(produzione)) + NEWLINE);
		log.append("Totale Consumi     +~> KWh " + String.format("%.2f",totaleConsumiSenzaBatteria) + NEWLINE);
		log.append("Totale Immissioni  +~> KWh " + String.format("%.2f",totaleImmissioniSenzaBatteria) + NEWLINE);
		log.append("Totale Autoconsumo +~> KWh " + String.format("%.2f",produzione-totaleImmissioniSenzaBatteria) +
   	    		            " - EUR " + String.format("%.2f",costoKWh*(produzione-totaleImmissioniSenzaBatteria)) + NEWLINE);
		
		
		for (int k = batteryMin; k< batteryMax+1; k++) {
			totaleConsumiConBatteria = 0;
			totaleImmissioniConBatteria = 0;
			Batteria batteria = new Batteria();
			batteria.setCapacita(k);
			
			log.append("======================="+ NEWLINE);
			log.append("Inizio Simulazione con Batteria da " + batteria.getCapacita() + " KWh"+ NEWLINE);
			log.append("======================="+ NEWLINE);
			log.append(NEWLINE);
			conBatteria(batteria, log, bVerboseLog);
			log.append(NEWLINE);
			log.append("======================="+ NEWLINE);
			log.append("Fine simulazione con Batteria da " + batteria.getCapacita() + " KWh"+ NEWLINE);
			log.append("Totale Consumi     +~> KWh " + String.format("%.2f",totaleConsumiConBatteria) + NEWLINE);
			log.append("Totale Immissioni  +~> KWh " + String.format("%.2f",totaleImmissioniConBatteria) + NEWLINE);
			log.append("Risparmio Con Batt +~> KWh " + String.format("%.2f",(totaleConsumiSenzaBatteria - totaleConsumiConBatteria)) + NEWLINE);
			log.append("Risparmio Con Batt +~> EUR " + String.format("%.2f",(totaleConsumiSenzaBatteria - totaleConsumiConBatteria)*costoKWh) + NEWLINE);
			log.append("======================="+ NEWLINE);
			log.append(NEWLINE);
			
		}  
	}


	private static void conBatteria(Batteria batteria, JTextArea log, boolean bVerboseLog) {

		  Map<Date, Giorno> m1 = new TreeMap<Date, Giorno>( periodo.getGiorni());
		  
		  m1.forEach((key, value) -> {
		    //   System.out.println("Key=" + key + ", Value=" + value);
		        
		        HashMap<String, QuartoOrario> quarti = value.getQuarti();
		        Map<String, QuartoOrario> m2 = new TreeMap<String, QuartoOrario>(quarti);
		        
		        LocalDate localDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(key) );
		        
		        m2.forEach((k, v) -> {
		        
		        	 double risultato = gestioneBatteria(v, batteria);
		        	 
		        	 if(risultato>0) {
		        		 totaleImmissioniConBatteria = totaleImmissioniConBatteria + risultato;
		        	 }else {
		        		 totaleConsumiConBatteria = totaleConsumiConBatteria - risultato; 	
		        	 }
		        	
		        	 if(bVerboseLog) {
		        		 log.append("Giorno => " + localDate.toString()        +" Fascia => " + k
				        	 		+ ", PR = " + String.format("%.3f",v.getPrelevata()) 
				        	 		+ ", IM = " + String.format("%.3f",v.getImmessa()) 
				        	 		+ ", BTT = " + String.format("%.3f", batteria.getStato()));
			        	 log.append(NEWLINE);
				    		 
		        	 }
		        });  
		    });

	}
	
	private static void senzaBatteria() {

		Map<Date, Giorno> m1 = new TreeMap<Date, Giorno>(periodo.getGiorni());

		m1.forEach((key, value) -> {

			HashMap<String, QuartoOrario> quarti = value.getQuarti();
			Map<String, QuartoOrario> m2 = new TreeMap<String, QuartoOrario>(quarti);
			m2.forEach((k, v) -> {
				totaleConsumiSenzaBatteria = totaleConsumiSenzaBatteria + v.getPrelevata();
				totaleImmissioniSenzaBatteria = totaleImmissioniSenzaBatteria + v.getImmessa();
			});
		});
	}

	/**
	 * Se positivo genera una immissione<br>
	 * Se negativo genera un prelievo
	 */
	public static double gestioneBatteria(QuartoOrario quarto, Batteria batteria) {
		if (quarto.getImmessa() > 0 && quarto.getPrelevata() == .0) {
			// carico la batteria
			return batteria.carica(quarto.getImmessa());
		} else if (quarto.getImmessa() == .0 && quarto.getPrelevata() > 0) {
			// uso la batteria segno meno
			return -batteria.usa(quarto.getPrelevata());
		} else if (quarto.getImmessa() > 0 && quarto.getPrelevata() > 0) {
			// nello stesso quarto, abbiamo avuto prelievi e immissioni.
			// In mancanza di altri dati, andiamo per delta
			if (quarto.getImmessa() > quarto.getPrelevata()) {
				return batteria.carica(quarto.getImmessa() - quarto.getPrelevata());
			} else {
				return -batteria.usa(quarto.getPrelevata() - quarto.getImmessa());
			}
		}
		return 0;
	}
	
	public static void readAllFiles(final File folder, final int tipo) throws FileNotFoundException, ParseException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.getName().indexOf(".csv") > 0) {
				readFile(fileEntry, tipo);
				//System.out.println(fileEntry.getName());
			}
		}
	}


	private static void readFile(File file, final int tipo) throws FileNotFoundException, ParseException {
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				getRecordFromLine(scanner.nextLine(), tipo);
			}
		}

	}
	
	
	private static void getRecordFromLine(String line, final int tipo) throws ParseException {

		Giorno giorno = new Giorno();
		boolean isGiornoEsistente = false;
	

		boolean isHeader = false;
	    try (Scanner rowScanner = new Scanner(line)) {
	    	int counter = 0;
	        rowScanner.useDelimiter(";");
	        while (rowScanner.hasNext()) {
	        	String value = rowScanner.next().replaceAll("\"", "");
	        	// header
	        	if(counter == 0 && value.equalsIgnoreCase("giorno")) {
	        		isHeader = true;
	        	} else {
		        	if(isHeader) {
		        		periodo.getFasceOrarie().add(value);
		        	} else {		
		        		 if(counter == 0 ) {
		 	        		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 	        		Date date = dateFormat.parse(value);
		 	        		
		 	        		Giorno giornoEsistente = periodo.getGiorni().get(date);
				 	   		if(giornoEsistente!=null) {
				 	   			giorno = giornoEsistente;
				 	   			isGiornoEsistente = true;
				 	   		}
		 	        		giorno.setDate(date);
		 	        		
		 	        	} else {
		 	        		// ora una data ce l'ho
		 	        		QuartoOrario quarto = new QuartoOrario();
		 	      
		 	        		String fasciaOraria = periodo.getFasceOrarie().get(counter-1);

		 	        		if (isGiornoEsistente) {
		 	        			if(giorno.getQuarti().containsKey(fasciaOraria)) {
		 	        				quarto = giorno.getQuarti().get(fasciaOraria);
		 	        			}
		 	        		}
		 	        		
		 	        		quarto.setOrario(fasciaOraria);
		 	        		value = value.replaceAll(",", ".");
		 	        		if(tipo == TIPO_CONSUMI) {
		 	        			quarto.setPrelevata(Double.parseDouble(value));
		 	        			quarto.incrementaCompletezza();
		 	        		}
		 	        		else if (tipo == TIPO_IMMISSIONI) {
		 	        			quarto.setImmessa(Double.parseDouble(value));
		 	        			quarto.incrementaCompletezza();
		 	        		}
		 	        		giorno.getQuarti().putIfAbsent(fasciaOraria, quarto);
		 	        		
		 	        	}
		        	}
	        	}
	           	counter ++;
	        }

	    }
        if(!isGiornoEsistente && giorno.getDate()!= null) {
	    	periodo.getGiorni().put(giorno.getDate(), giorno);
        }

	}
}
