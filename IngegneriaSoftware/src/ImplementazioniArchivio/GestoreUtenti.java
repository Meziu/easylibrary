package ImplementazioniArchivio;

import archivi.ValoreGiàPresenteException;
import archivi.Archivio;
import ClassiPrincipali.Matricola;
import ClassiPrincipali.Utente;
import Interfacce.CollezioneConChiave;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestoreUtenti extends Archivio<Utente> implements CollezioneConChiave<Matricola, Utente> {
    private Map<Matricola, Utente> indiceMatricole;
    private Map<IndirizzoEmail, Utente> indiceEmail;
    
    public GestoreUtenti(){
        super();
        indiceMatricole = new HashMap<>();
        indiceEmail = new HashMap<>();
    }
    
    @Override
    public void registra(Utente u){
        boolean matricolaPresente = indiceMatricole.containsKey(u.getMatricola());
        boolean emailPresente = indiceEmail.containsKey(u.getIndirizzoEmail());
        
        if(matricolaPresente || emailPresente) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }
        
        indiceMatricole.put(u.getMatricola(), u);
        indiceEmail.put(u.getIndirizzoEmail(), u);
        
        List<Utente> l = getCollezione();
        int idx = Collections.binarySearch(l, u);
        l.add(idx, u);
        
    }
    
    @Override
    public void rimuovi(Utente u){
        Utente utentePerMatricola = indiceMatricole.get(u.getMatricola());
        Utente utentePerEmail = indiceEmail.get(u.getIndirizzoEmail());
        
        if (utentePerMatricola.compareTo(utentePerEmail) != 0) {
            throw new ValoreNonPresenteException(); // TODO
        }
        
        indiceMatricole.remove(u.getMatricola());
        indiceEmail.remove(u.getIndirizzoEmail());
        
        List<Utente> l = getCollezione();
        int idx = Collections.binarySearch(l, u);
        
        // Se l'indice è fuori dalla lista o se l'elemento trovato dalla binarySearch non è quello giusto.
        if (idx == l.size() || l.get(idx).compareTo(u) != 0) {
            return;
        }
        
        l.remove(idx);
    }
    
    
    
    
    
    
    
    
    
    
    //matricola è univoca, ha senso restituire una lista?
    public Utente cerca(Matricola matricola){
        for (Utente u : utenti.values()) {
            if (u.getMatricola().equals(matricola) ) {
                return u;
            }
        }
        return null; //O una lista vuota?
    }
    
    
    
    
    public List<Utente> cerca(String cognome){
       List<Utente> risultati = new ArrayList<>();
       
        for (Utente u : utenti.values()) {
            if (u.getCognome().equals(cognome)) {
                risultati.add(u);
            }
        }
        return risultati;
    }
    

    @Override
    public Utente ottieni(Matricola key) {
        return this.indiceMatricole.get(key);
    }

    @Override
    public boolean chiavePresente(Matricola key) {
        return this.indiceMatricole.containsKey(key);
    }
    
}
