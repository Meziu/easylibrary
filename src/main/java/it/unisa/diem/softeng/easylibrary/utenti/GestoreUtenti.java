package it.unisa.diem.softeng.easylibrary.utenti;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GestoreUtenti implements ArchivioConChiave<Matricola, Utente> {

    private final List<Utente> utenti;
    private final Map<Matricola, Utente> indiceMatricole;

    public GestoreUtenti() {
        utenti = new ArrayList<>();
        indiceMatricole = new HashMap<>();
    }
    
    @Override
    public List<Utente> getLista() {
        return Collections.unmodifiableList(utenti);
    }

    @Override
    public void registra(Utente u) {
        Utente res = indiceMatricole.putIfAbsent(u.getMatricola(), u);

        // Se era già presente quell'utente
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        int idx = Collections.binarySearch(utenti, u);
        
        utenti.add(Math.abs(idx + 1), u);

    }

    @Override
    public void rimuovi(Utente u) {
        Utente res = indiceMatricole.remove(u.getMatricola());

        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        int idx = Collections.binarySearch(utenti, u);

        // Se l'indice è fuori dalla lista (elemento non presente):
        if (idx < 0 || idx >= utenti.size()) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        utenti.remove(idx);
    }
    
    @Override
    public void modifica(Utente utente, Consumer<Utente> c) {
        Utente u = ottieni(utente.getMatricola());
        if (u == null) {
            throw new ValoreNonPresenteException();
        }
        
        int idx_remove = Collections.binarySearch(utenti, u);
        if (idx_remove < 0 || idx_remove >= utenti.size()) {
            throw new ValoreNonPresenteException();
        }
        
        utenti.remove(idx_remove);
        
        // Applica modifiche.dal consumer.
        c.accept(u);
        
        int idx_insert = Collections.binarySearch(utenti, u);
        utenti.add(Math.abs(idx_insert + 1), u);
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
