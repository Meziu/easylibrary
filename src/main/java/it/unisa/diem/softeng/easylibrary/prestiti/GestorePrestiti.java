package it.unisa.diem.softeng.easylibrary.prestiti;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import it.unisa.diem.softeng.easylibrary.libri.Gestore;
import java.util.Collections;

/**
 * @class GestorePrestiti.java
 * @brief Classe responsabile della gestione dei prestiti.
 * La classe GestorePrestiti estende Archivio e si occupa della gestione,
 * registrazione e restituzione dei prestiti. I prestiti vengono mantenuti
 * ordinati secondo il criterio definito da OrdinatorePrestiti.
 * 
 * @see Archivio
 * @see Prestito
 * @see OrdinatorePrestiti
*/
public class GestorePrestiti extends Gestore<Prestito> {

    /**
     * @brief Costruttore della classe GestorePrestiti.
     * 
     * Inizializza la struttura dati tramite il costruttore della superclasse
     * e crea l'ordinatore dei prestiti.
     */
    public GestorePrestiti() {
        super();
    }

    /**
     * @brief Segna un prestito come restituito.
     * 
     * Cerca il prestito nella lista e ne aggiorna lo stato in
     * {@link StatoPrestito#RESTITUITO}.
     * 
     * @param p Il prestito da rimuovere (restituire).
     * 
     * @throws ValoreNonPresenteException Se il prestito non è presente
     * nell'archivio.
    */
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
