package it.unisa.diem.softeng.easylibrary.archivio;

import it.unisa.diem.softeng.easylibrary.dati.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.Libro;
import it.unisa.diem.softeng.easylibrary.dati.OrdinatoreLibri;
import it.unisa.diem.softeng.easylibrary.interfacce.CollezioneConChiave;
import java.util.HashMap;
import java.util.Map;

public class GestoreLibri extends Archivio<Libro> implements CollezioneConChiave<ISBN, Libro> {

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

        indiceISBN.put(l.getISBN(), l);
        elencoLibri.add(l);
    }

    @Override
    public void rimuovi(Libro l) {
        indiceISBN.remove(l.getISBN());
        elencoLibri.remove(l);
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
