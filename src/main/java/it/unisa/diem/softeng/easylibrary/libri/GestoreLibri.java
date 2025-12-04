package it.unisa.diem.softeng.easylibrary.libri;

import static com.sun.org.apache.xml.internal.utils.StringComparable.getComparator;
import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import java.util.function.Consumer;

public class GestoreLibri extends Archivio<Libro> implements ArchivioConChiave<ISBN, Libro> {

    private final Map<ISBN, Libro> indiceISBN;
    private final OrdinatoreLibri ord;

    public GestoreLibri() {
        super();

        indiceISBN = new HashMap<>();
        ord = new OrdinatoreLibri();
    }

    @Override
    public void registra(Libro l) {
        //Verifica univocità ISBN
        Libro res = indiceISBN.putIfAbsent(l.getISBN(), l);

        // Se era già presente quel libro
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        List<Libro> list = getCollezione();
        int idx = Collections.binarySearch(list, l, ord);
        list.add(Math.abs(idx + 1), l);
    }

    @Override
    public void rimuovi(Libro l) {
        Libro res = indiceISBN.remove(l.getISBN());

        // Se non era presente l'utente
        if (res == null) {

            throw new ValoreNonPresenteException(); // TODO
        }

        List<Libro> list = getCollezione();
        int idx = Collections.binarySearch(list, l, ord);

        // Se l'indice è fuori dalla lista (l'elemento non è presente):
        if (idx < 0 || idx >= list.size()) {
            throw new ValoreNonPresenteException();
        }

        list.remove(idx);
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
    public void modifica(ISBN key, Consumer<Libro> c) {
        Libro l = ottieni(key);
        if (l == null) {
            throw new ValoreNonPresenteException();
        }
        
        List<Libro> list = getCollezione();
        int idx_remove = Collections.binarySearch(list, l, ord);
        if (idx_remove < 0 || idx_remove >= list.size()) {
            throw new ValoreNonPresenteException();
        }
        
        // Applica modifiche.dal consumer.
        c.accept(l);
        
        int idx_insert = Collections.binarySearch(list, l, ord);
        list.add(Math.abs(idx_insert + 1), l);
    }
}
