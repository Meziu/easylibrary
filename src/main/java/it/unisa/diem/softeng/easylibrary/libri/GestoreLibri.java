package it.unisa.diem.softeng.easylibrary.libri;

import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;

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
        list.add(idx, l);
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

        // Se l'indice è fuori dalla lista o se l'elemento trovato dalla binarySearch non è quello giusto.
        if (idx == list.size() || list.get(idx).compareTo(l) != 0) {
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
    public void riassegna(ISBN oldKey, ISBN newKey) {
        Libro l = this.indiceISBN.remove(oldKey);

        if (l == null) {
            throw new ValoreNonPresenteException();
        }

        // Impostiamo solo la stringa dell'ISBN (e non l'oggetto in se) per non modificare il riferimento nella lista dei Prestiti.
        l.getISBN().setISBN(newKey.getISBN());

        if (this.indiceISBN.putIfAbsent(l.getISBN(), l) != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }
    }
}
