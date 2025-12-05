package it.unisa.diem.softeng.easylibrary.prestiti;

import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import java.util.Collections;
import java.util.List;


/**@class GestorePrestiti.java
 * @brief Classe responsabile della gestione dei prestiti.
 * La classe GestorePrestiti estende Archivio e si occupa della gestione,
 * registrazione e restituzione dei prestiti. I prestiti vengono mantenuti
 * ordinati secondo il criterio definito da OrdinatorePrestiti.
 * 
 * @see Archivio
 * @see Prestito
 * @see OrdinatorePrestiti
*/


public class GestorePrestiti extends Archivio<Prestito> {

    private final OrdinatorePrestiti ord; ///< Oggetto utilizzato per l'ordinamento dei prestiti.

    
    /**
     * @brief Costruttore della classe GestorePrestiti.
     * 
     * Inizializza la struttura dati tramite il costruttore della superclasse
     * e crea l'ordinatore dei prestiti.
     */
    public GestorePrestiti() {
        super();

        ord = new OrdinatorePrestiti();
    }

    
    /**
     * @brief Restituisce lo storico completo dei prestiti.
     * 
     * @return Lista dei prestiti presenti nell'archivio.
     */
    public List<Prestito> getStoricoPrestiti() {
        return getCollezione();
    }

    
    
    /**
    * @brief Registra un nuovo prestito nell'archivio.
    * 
    * Il prestito viene inserito nella posizione corretta in base
    * all'ordinamento definito dall'ordinatore.
    * 
    * @param p Il prestito da registrare.
    */
    @Override
    public void registra(Prestito p) {
        List<Prestito> l = getCollezione();

        int idx = Collections.binarySearch(l, p, ord);
        l.add(idx, p);
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

        List<Prestito> list = getCollezione();
        int idx = Collections.binarySearch(list, p, ord);

        // Se l'indice è fuori dalla lista o se l'elemento trovato dalla binarySearch non è quello giusto.
        if (idx == list.size() || list.get(idx).compareTo(p) != 0) {
            throw new ValoreNonPresenteException();
        }

        list.get(idx).setStatoPrestito(StatoPrestito.RESTITUITO);
    }
}
