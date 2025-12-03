package ImplementazioniArchivio;

import archivi.Archivio;
import ClassiPrincipali.ISBN;
import ClassiPrincipali.Libro;
import Interfacce.Ricercabile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GestoreLibri implements Archivio<Libro>{
    private Map<ISBN, Libro> libri;
    private List<Libro> elencoLibri;
    
    public GestoreLibri(){
        libri = new HashMap<>();
        elencoLibri = new ArrayList<>();
    }
    
    
    @Override
    public void aggiungi(Libro l){
        //Verifica univocità ISBN
                
        libri.put(l.getISBN(), l);
        elencoLibri.add(l);
    }
    
    @Override
    public void rimuovi(Libro l){
        libri.remove(l.getISBN());
        elencoLibri.remove(l);
    }
    
    
    
    public List<Libro> getElencoLibri(){
        return elencoLibri;
    }

    
    //ISBN è univoco, ha senso restituire una lista?
    public Libro cerca(ISBN isbn){
        for (Libro l : libri.values()) {
            if (l.getISBN().equals(isbn) ) {
                return l;
            }
        }
        return null; //O una lista vuota?
    }
}
