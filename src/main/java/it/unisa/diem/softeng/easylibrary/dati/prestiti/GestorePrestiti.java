package it.unisa.diem.softeng.easylibrary.dati.prestiti;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import it.unisa.diem.softeng.easylibrary.dati.libri.Gestore;
import java.util.Collections;

/**
 * @brief Gestisce i prestiti dei libri.
 * 
 * La classe GestorePrestiti estende Gestore e si occupa di registrare le restituzioni dei prestiti.
 * 
 * @see Gestore
 * @see Prestito
*/
public class GestorePrestiti extends Gestore<Prestito> {

    /**
     * @brief Costruttore della classe GestorePrestiti.
     * 
     * Inizializza la struttura dati tramite il costruttore della superclasse.
     */
    public GestorePrestiti() {
        super();
    }

    /**
     * @brief Segna un prestito come restituito.
     * 
     * Cerca il prestito nella lista e ne aggiorna lo stato in "RESTITUITO".
     * 
     * @param\[in] p Il prestito da rimuovere (restituire).
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

        lista.get(idx).setStato(StatoPrestito.RESTITUITO);
    }
    
}
