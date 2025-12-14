package it.unisa.diem.softeng.easylibrary.dati.prestiti;

import java.io.Serializable;

/**
 * @brief Stato del prestito.
 * Ogni \ref Prestito registrato è in uno stato tra ATTIVO, se il libro
 * associato non è stato ancora restituito dall'utente, e RESTITUITO.
 */
public enum StatoPrestito implements Serializable {
    /**
     * @brief \ref Prestito non restituito.
     */
    ATTIVO,
    
    /**
     * @brief \ref Prestito restituito.
     */
    RESTITUITO
}
