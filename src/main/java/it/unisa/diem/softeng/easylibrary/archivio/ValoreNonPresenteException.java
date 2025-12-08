package it.unisa.diem.softeng.easylibrary.archivio;

/**
 *
 * @brief Eccezione che segnala la richiesta in un Archiviabile di restituzione di
 * un valore non presente.
 */
public class ValoreNonPresenteException extends RuntimeException {

     /**
     * @brief Costruttore.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public ValoreNonPresenteException() {
    }

    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo ad un valore non presente.
     */
    public ValoreNonPresenteException(String string) {
        super(string);
    }

}
