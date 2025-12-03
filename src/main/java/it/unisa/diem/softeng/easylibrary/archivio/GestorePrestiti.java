package it.unisa.diem.softeng.easylibrary.archivio;

import it.unisa.diem.softeng.easylibrary.dati.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.eccezioni.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.eccezioni.ValoreNonPresenteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorePrestiti extends Archivio<Prestito> {

    public GestorePrestiti() {
        super();
    }
    
    public List<Prestito> getStoricoPrestiti() {
        return getCollezione();
    }

    
    
    /*Funzione che aggiunge un nuovo prestito alla lista dei prestiti*/
    @Override
    public void registra(Prestito p) {
        List<Prestito> l = getCollezione();
        if (l.contains(p)){
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }
        int idx = Collections.binarySearch(l, p);
        l.add(idx, p);
    }

    
    
    /*Funzione che segna un prestito come RESTITUITO*/
    @Override
    public void rimuovi(Prestito p) {
        List<Prestito> l = getCollezione();
        if (!l.contains(p)){
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }
        
        p.setStatoPrestito(StatoPrestito.RESTITUITO);
    }

    
    
    





    
    //Cerca un determinato prestito
    public Prestito cerca(Prestito p) {
        List<Prestito> prestiti = new ArrayList<>();
        for (Prestito pr : prestiti) {
            if (pr.equals(p)) {
                return pr;
            }
        }
        return null;
    }
}
