package it.unisa.diem.softeng.easylibrary.utenti;

import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import java.util.function.Consumer;

public class GestoreUtenti extends Archivio<Utente> implements ArchivioConChiave<Matricola, Utente> {

    private final Map<Matricola, Utente> indiceMatricole;
    private final OrdinatoreUtenti ord;

    public GestoreUtenti() {
        super();

        indiceMatricole = new HashMap<>();
        ord = new OrdinatoreUtenti();
    }

    @Override
    public void registra(Utente u) {
        Utente res = indiceMatricole.putIfAbsent(u.getMatricola(), u);

        // Se era già presente quell'utente
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        int idx = Collections.binarySearch(lista, u, ord);
        
        lista.add(Math.abs(idx + 1), u);

    }

    @Override
    public void rimuovi(Utente u) {
        Utente res = indiceMatricole.remove(u.getMatricola());

        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        int idx = Collections.binarySearch(lista, u, ord);

        // Se l'indice è fuori dalla lista (elemento non presente):
        if (idx < 0 || idx >= lista.size()) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        lista.remove(idx);
    }

    @Override
    public Utente ottieni(Matricola key) {
        return this.indiceMatricole.get(key);
    }

    @Override
    public boolean contiene(Matricola key) {
        return this.indiceMatricole.containsKey(key);
    }
    
    @Override
    public void modifica(Matricola key, Consumer<Utente> c) {
        Utente u = ottieni(key);
        if (u == null) {
            throw new ValoreNonPresenteException();
        }
        
        int idx_remove = Collections.binarySearch(lista, u, ord);
        if (idx_remove < 0 || idx_remove >= lista.size()) {
            throw new ValoreNonPresenteException();
        }
        
        // Applica modifiche.dal consumer.
        c.accept(u);
        
        int idx_insert = Collections.binarySearch(lista, u, ord);
        lista.add(Math.abs(idx_insert + 1), u);
    }
}
