# solarmeter
by alekava

Questo semplice programma fornisce una stima abbastanza attendibile di quello che avresti risparmiato installando un accumulo assieme al tuo fotovoltaico.
Il funzionamento si basa sui dati dei quarti orari prelevati da e-distribuzione e simula lo riempimento e lo svuotamento dell'ipotetico accumulo, durante tutto il periodo, utilizzando immissioni e consumi. 

## Istruzioni:
Scegliere un periodo di osservazione (ad esempio un intero anno solare)
Scaricare i files dei consumi da e-distribuzione relativi al periodo e salvarli in una cartella
Scaricare i files delle immissioni da e-distribuzione relativi allo stesso periodo e salvarli in una cartella diversa
Relativamente ai files, si possono tranquillamente scaricare cosi come sono, ovviamente con nomi diversi. Il software provvedera' in ogni caso ad ordinarli in ordine cronologico.
Ovviamente serve cura che i periodi di consumi e immissioni coincidano...

Lanciare il programma che chiede:

- Cartella dei consumi
- Cartella dei prelievi
- Produzione del tuo impianto fotovoltaico relativa allo stesso periodo (in KWh)
- Costo medio del tuo KWh
- Taglio minimo dell'accumulo (Solitamente io parto da 1)
- Taglio massimo dell'accumulo (Non ci sono limiti ma come sara' ovvio, oltre una certa soglia sara' evidente un plateaux)
- Un checkbox che dice se mostrare il log verboso o no (se si', mostra tutti i 96 quarti d'ora di ogni giono del periodo scelto)
  
## Note:
Non vi e' alcuna garanzia di funzionamento. Il sistema e' un semplice calcolo algebrico di immissioni e prelievi e non tiene conto di variazioni di abitudini in funzione della presenza dell'accumulo.


