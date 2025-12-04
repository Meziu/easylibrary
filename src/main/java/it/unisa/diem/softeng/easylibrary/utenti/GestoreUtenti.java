package it.unisa.diem.softeng.easylibrary.utenti;

import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import it.unisa.diem.softeng.easylibrary.libri.Libro;
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

        List<Utente> list = getCollezione();

        int idx = Collections.binarySearch(list, u, ord);
        
        list.add(Math.abs(idx + 1), u);

    }

    @Override
    public void rimuovi(Utente u) {
        Utente res = indiceMatricole.remove(u.getMatricola());

        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        List<Utente> list = getCollezione();
        int idx = Collections.binarySearch(list, u, ord);

        // Se l'indice è fuori dalla lista (elemento non presente):
        if (idx < 0 || idx >= list.size()) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        list.remove(idx);
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
        
        List<Utente> list = getCollezione();
        int idx_remove = Collections.binarySearch(list, u, ord);
        if (idx_remove < 0 || idx_remove >= list.size()) {
            throw new ValoreNonPresenteException();
        }
        
        // Applica modifiche.dal consumer.
        c.accept(u);
        
        int idx_insert = Collections.binarySearch(list, u, ord);
        list.add(Math.abs(idx_insert + 1), u);
    }
}
