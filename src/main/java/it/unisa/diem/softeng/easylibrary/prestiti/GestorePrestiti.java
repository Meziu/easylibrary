package it.unisa.diem.softeng.easylibrary.prestiti;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import it.unisa.diem.softeng.easylibrary.libri.Gestore;
import java.util.Collections;

public class GestorePrestiti extends Gestore<Prestito> {

    public GestorePrestiti() {
        super();
    }

    /*Funzione che segna un prestito come RESTITUITO*/
    @Override
    public void rimuovi(Prestito p) {
        int idx = Collections.binarySearch(lista, p);

        // Se l'indice è fuori dalla lista (cioè non è presente l'elemento):
        if (idx < 0 || idx >= lista.size()) {
            throw new ValoreNonPresenteException();
        }

        lista.get(idx).setStatoPrestito(StatoPrestito.RESTITUITO);
    }
    
}
