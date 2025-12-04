package it.unisa.diem.softeng.easylibrary.prestiti;

import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import java.util.Collections;
import java.util.List;

public class GestorePrestiti extends Archivio<Prestito> {
    private final OrdinatorePrestiti ord;

    public GestorePrestiti() {
        super();
        
        ord = new OrdinatorePrestiti();
    }
    
    public List<Prestito> getStoricoPrestiti() {
        return getCollezione();
    }

    /*Funzione che aggiunge un nuovo prestito alla lista dei prestiti*/
    @Override
    public void registra(Prestito p) {
        List<Prestito> l = getCollezione();

        int idx = Collections.binarySearch(l, p, ord);
        l.add(idx, p);
    }

    /*Funzione che segna un prestito come RESTITUITO*/
    @Override
    public void rimuovi(Prestito p) {

        List<Prestito> list = getCollezione();
        int idx = Collections.binarySearch(list, p, ord);

        // Se l'indice è fuori dalla lista o se l'elemento trovato dalla binarySearch non è quello giusto.
        if (idx == list.size() || list.get(idx).compareTo(p) != 0) {
            throw new ValoreNonPresenteException();
        }

        list.get(idx).setStatoPrestito(StatoPrestito.RESTITUITO);
    }
}
