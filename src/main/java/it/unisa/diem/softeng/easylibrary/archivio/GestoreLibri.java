package it.unisa.diem.softeng.easylibrary.archivio;

import it.unisa.diem.softeng.easylibrary.dati.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.Libro;
import it.unisa.diem.softeng.easylibrary.eccezioni.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.eccezioni.ValoreNonPresenteException;
import it.unisa.diem.softeng.easylibrary.interfacce.CollezioneConChiave;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestoreLibri extends Archivio<Libro> implements CollezioneConChiave<ISBN, Libro> {

    private Map<ISBN, Libro> indiceISBN;

    public GestoreLibri() {
        super();
        indiceISBN = new HashMap<>();
    }

    @Override
    public void registra(Libro l) {
        Libro res = indiceISBN.putIfAbsent(l.getISBN(), l);
        
        // Se era già presente quel libro
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        List<Libro> list = getCollezione();
        int idx = Collections.binarySearch(list, l);
        list.add(idx, l);
    }
    
    
    

    @Override
    public void rimuovi(Libro l) {
        Libro res = indiceISBN.remove(l.getISBN());

        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        List<Libro> list = getCollezione();
        int idx = Collections.binarySearch(list, l);

        // Se l'indice è fuori dalla lista o se l'elemento trovato dalla binarySearch non è quello giusto.
        if (idx == list.size() || list.get(idx).compareTo(l) != 0) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
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
}
