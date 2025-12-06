package it.unisa.diem.softeng.easylibrary.utenti;

/**
 * 
 * @brief Eccezione lanciata quando una matricola risulta non valida.
 *
 * Questa eccezione viene utilizzata per segnalare errori legati alla
 * creazione, validazione o utilizzo di una matricola non conforme
 * allo standard previsto.
 * 
 * @see Matricola
 */
public class MatricolaInvalidaException extends RuntimeException {

    /**
     * @brief Costruttore di default.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public MatricolaInvalidaException() {
    }

    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo alla matricola non valida.
     */
    public MatricolaInvalidaException(String string) {
        super(string);
    }

}
