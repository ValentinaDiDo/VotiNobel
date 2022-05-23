package it.polito.tdp.nobel.model;

import java.util.*;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami; 
	private Set <Esame> migliore;
	private double mediaMigliore;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.esami = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int m) {
		
		migliore = new HashSet<Esame>();
		mediaMigliore = 0.0;
		Set<Esame> parziale = new HashSet<Esame>();
		//cerca(parziale, 0 , m);
		cerca2(parziale, 0 , m);
		
		return migliore;	
	}

	private void cerca(Set<Esame> parziale , int L, int m) {
		
		//caso terminale
		int sommaCrediti = sommaCrediti(parziale);
		
		if(sommaCrediti > m) //soluzione inammissibile
			return;
		if(sommaCrediti == m) { //soluzione migliore
			
			double mediaVoti = calcolaMedia(parziale);
			
			if(mediaVoti > mediaMigliore) {
				migliore = new HashSet<Esame>(parziale); //NON VOGLIO UN RIFERIMENTO, MA UNA COPIA
				mediaMigliore = mediaVoti;
			}
			return; 
			
		}if(L == esami.size())  //raggiungo il fondo e non ho niente da aggiungere
			return;
		
		//genero sottoproblemi
		for(Esame e : esami) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca(parziale, L+1, m);
				parziale.remove(e); //essendo un set, di questo oggetti ce ne sarà uno solo
			}
		}
		
		//aggiungo esame
		parziale.add(esami.get(L));
		cerca2(parziale, L+1, m);
		parziale.remove(esami.get(L));
		cerca2(parziale, L+1, m);
	}
	
private void cerca2(Set<Esame> parziale , int L, int m) {
		
		//caso terminale
		int sommaCrediti = sommaCrediti(parziale);
		
		if(sommaCrediti > m) //soluzione inammissibile
			return;
		if(sommaCrediti == m) { //soluzione migliore
			
			double mediaVoti = calcolaMedia(parziale);
			
			if(mediaVoti > mediaMigliore) {
				migliore = new HashSet<Esame>(parziale); //NON VOGLIO UN RIFERIMENTO, MA UNA COPIA
				mediaMigliore = mediaVoti;
			}
			return; 
			
		}if(L == esami.size())  //raggiungo il fondo e non ho niente da aggiungere
			return;
		
		//aggiungo esame
		parziale.add(esami.get(L));
		cerca2(parziale, L+1, m);
		parziale.remove(esami.get(L));
		cerca2(parziale, L+1, m);
	}
	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
