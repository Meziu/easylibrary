package it.unisa.diem.softeng.easylibrary.libri;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GestoreLibri implements ArchivioConChiave<ISBN, Libro> {

    private final List<Libro> libri;
    private final Map<ISBN, Libro> indiceISBN;

    public GestoreLibri() {
        libri = new ArrayList<>();
        indiceISBN = new HashMap<>();
    }
    
    @Override
    public List<Libro> getLista() {
        return Collections.unmodifiableList(libri);
    }
    
    @Override
    public void registra(Libro l) {
        //Verifica univocità ISBN
        Libro res = indiceISBN.putIfAbsent(l.getISBN(), l);

        // Se era già presente quel libro
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        int idx = Collections.binarySearch(libri, l);
        libri.add(Math.abs(idx + 1), l);
    }

    @Override
    public void rimuovi(Libro l) {
        Libro res = indiceISBN.remove(l.getISBN());

        // Se non era presente l'utente
        if (res == null) {

            throw new ValoreNonPresenteException(); // TODO
        }

        int idx = Collections.binarySearch(libri, l);

        // Se l'indice è fuori dalla lista (l'elemento non è presente):
        if (idx < 0 || idx >= libri.size()) {
            throw new ValoreNonPresenteException();
        }

        libri.remove(idx);
    }

    @Override
    public Libro ottieni(ISBN key) {
        return indiceISBN.get(key);
    }

    @Override
    public boolean contiene(ISBN key) {
        return indiceISBN.containsKey(key);
    }
    
    @Override
    public void modifica(Libro libro, Consumer<Libro> c) {
        Libro l = ottieni(libro.getISBN());
        if (l == null) {
            throw new ValoreNonPresenteException();
        }
        
        int idx_remove = Collections.binarySearch(libri, l);
        if (idx_remove < 0 || idx_remove >= libri.size()) {
            throw new ValoreNonPresenteException();
        }
        
        libri.remove(idx_remove);
        
        // Applica modifiche.dal consumer.
        c.accept(l);
        
        int idx_insert = Collections.binarySearch(libri, l);
        libri.add(Math.abs(idx_insert + 1), l);
    }
}
