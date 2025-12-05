package it.unisa.diem.softeng.easylibrary.prestiti;

import java.io.Serializable;

/**
 * @enum StatoPrestito
 * @brief Rappresenta lo stato di un prestito.
 * 
 * L'enum StatoPrestito definisce i possibili stati in cui può trovarsi
 * un prestito all'interno del sistema.
 * 
 * I valori disponibili sono:
 * - {@link #ATTIVO} : Il prestito è attualmente in corso.
 * - {@link #RESTITUITO} : Il prestito è stato restituito.
 */
public enum StatoPrestito implements Serializable {
    ATTIVO, ///< Indica che il prestito è attualmente attivo
    RESTITUITO ///< Indica che il prestito è stato restituito
}
