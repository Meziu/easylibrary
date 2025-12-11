package it.unisa.diem.softeng.easylibrary.archivio;

/**
 *
 * @brief Eccezione che segnala l'inserimento in un Archiviabile di un valore già
 * presente e non sovrascrivibile.
 */
public class ValoreGiaPresenteException extends RuntimeException {

    /**
     * @brief Costruttore.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public ValoreGiaPresenteException() {
    }

    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo ad un valore già presente.
     */
    public ValoreGiaPresenteException(String string) {
        super(string);
    }

}
