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
        
        // BinarySearch ritorna valori positivi se trova il valore, o (- posizione di inserimento - 1)
        // se non lo trova. In ogni caso, noi vogliamo inserire immediatamente dopo un prestito trovato
        // o nel punto di inserimento restituito.
        l.add(Math.abs(idx + 1), p);
    }

    /*Funzione che segna un prestito come RESTITUITO*/
    @Override
    public void rimuovi(Prestito p) {

        List<Prestito> list = getCollezione();
        int idx = Collections.binarySearch(list, p, ord);

        // Se l'indice è fuori dalla lista (cioè non è presente l'elemento):
        if (idx < 0 || idx >= list.size()) {
            throw new ValoreNonPresenteException();
        }

        list.get(idx).setStatoPrestito(StatoPrestito.RESTITUITO);
    }
}
