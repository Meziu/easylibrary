package it.unisa.diem.softeng.easylibrary.dati.utenti;

/**
 *
 * @brief Eccezione che segnala l'utilizzo o la creazione di un IndirizzoEmail invalido.
 * 
 * Questa eccezione viene utilizzata per segnalare errori legati alla
 * creazione, validazione o utilizzo di un indirizzo email non conforme
 * agli standard previsti.
 * 
 * @see IndirizzoEmail
 */
public class IndirizzoEmailInvalidoException extends RuntimeException {

    /**
     * @brief Costruttore.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public IndirizzoEmailInvalidoException(String string) {
        super(string);
    }

    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo all'indirizzo email non valido.
     */
    public IndirizzoEmailInvalidoException() {
    }

}
