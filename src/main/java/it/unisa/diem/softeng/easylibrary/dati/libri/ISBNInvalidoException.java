package it.unisa.diem.softeng.easylibrary.dati.libri;

/**
 *
 * @brief Eccezione lanciata quando un codice ISBN risulta non valido.
 *
 * Questa eccezione viene utilizzata per segnalare errori legati alla creazione,
 * validazione o utilizzo di un codice ISBN non conforme agli standard previsti.
 *
 * @see ISBN
 */
public class ISBNInvalidoException extends RuntimeException {

    /**
     * @brief Costruttore.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public ISBNInvalidoException() {
    }

    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo all'ISBN non
     * valido.
     */
    public ISBNInvalidoException(String string) {
        super(string);
    }

}
