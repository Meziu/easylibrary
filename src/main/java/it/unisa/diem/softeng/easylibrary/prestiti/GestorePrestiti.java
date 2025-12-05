package it.unisa.diem.softeng.easylibrary.prestiti;

import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class GestorePrestiti implements Archivio<Prestito> {

    private final List<Prestito> prestiti;

    public GestorePrestiti() {
        prestiti = new ArrayList<>();
    }
    
    @Override
    public List<Prestito> getLista() {
        return Collections.unmodifiableList(prestiti);
    }

    /*Funzione che aggiunge un nuovo prestito alla lista dei prestiti*/
    @Override
    public void registra(Prestito p) {
        int idx = Collections.binarySearch(prestiti, p);
        
        // BinarySearch ritorna valori positivi se trova il valore, o (- posizione di inserimento - 1)
        // se non lo trova. In ogni caso, noi vogliamo inserire immediatamente dopo un prestito trovato
        // o nel punto di inserimento restituito.
        prestiti.add(Math.abs(idx + 1), p);
    }

    /*Funzione che segna un prestito come RESTITUITO*/
    @Override
    public void rimuovi(Prestito p) {
        int idx = Collections.binarySearch(prestiti, p);

        // Se l'indice è fuori dalla lista (cioè non è presente l'elemento):
        if (idx < 0 || idx >= prestiti.size()) {
            throw new ValoreNonPresenteException();
        }

        prestiti.get(idx).setStatoPrestito(StatoPrestito.RESTITUITO);
    }
    
    @Override
    public void modifica(Prestito prestito, Consumer<Prestito> c) {
        int idx_remove = Collections.binarySearch(prestiti, prestito);
        if (idx_remove < 0 || idx_remove >= prestiti.size()) {
            throw new ValoreNonPresenteException();
        }
        
        Prestito p = prestiti.get(idx_remove);
        
        prestiti.remove(idx_remove);
        
        // Applica modifiche.dal consumer.
        c.accept(p);
        
        int idx_insert = Collections.binarySearch(prestiti, p);
        prestiti.add(Math.abs(idx_insert + 1), p);
    }
}
