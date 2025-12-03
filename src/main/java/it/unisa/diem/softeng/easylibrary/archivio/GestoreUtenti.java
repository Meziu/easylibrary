package it.unisa.diem.softeng.easylibrary.archivio;

import it.unisa.diem.softeng.easylibrary.eccezioni.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.dati.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.Utente;
import it.unisa.diem.softeng.easylibrary.eccezioni.ValoreNonPresenteException;
import it.unisa.diem.softeng.easylibrary.interfacce.CollezioneConChiave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestoreUtenti extends Archivio<Utente> implements CollezioneConChiave<Matricola, Utente> {

    private Map<Matricola, Utente> indiceMatricole;

    public GestoreUtenti() {
        super();
        indiceMatricole = new HashMap<>();
    }

    @Override
    public void registra(Utente u) {
        Utente res = indiceMatricole.putIfAbsent(u.getMatricola(), u);

        // Se era già presente quell'utente
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        List<Utente> l = getCollezione();
        int idx = Collections.binarySearch(l, u);
        l.add(idx, u);

    }

    @Override
    public void rimuovi(Utente u) {
        Utente res = indiceMatricole.remove(u.getMatricola());

        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException(); // TODO
        }

        List<Utente> l = getCollezione();
        int idx = Collections.binarySearch(l, u);

        // Se l'indice è fuori dalla lista o se l'elemento trovato dalla binarySearch non è quello giusto.
        if (idx == l.size() || l.get(idx).compareTo(u) != 0) {
            throw new ValoreNonPresenteException();
        }

        l.remove(idx);
    }

    //matricola è univoca, ha senso restituire una lista?
    public Utente cerca(Matricola matricola) {
        for (Utente u : utenti.values()) {
            if (u.getMatricola().equals(matricola)) {
                return u;
            }
        }
        return null; //O una lista vuota?
    }

    public List<Utente> cerca(String cognome) {
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
    public boolean contiene(Matricola key) {
        return this.indiceMatricole.containsKey(key);
    }

}
