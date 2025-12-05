package it.unisa.diem.softeng.easylibrary.prestiti;

import java.io.Serializable;

/**
 *
 * @brief Stato del prestito.
 * Ogni \ref Prestito registrato è in uno stato tra "attivo", se il libro
 * associato non è stato ancora restituito dall'utente, e "restituito".
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
