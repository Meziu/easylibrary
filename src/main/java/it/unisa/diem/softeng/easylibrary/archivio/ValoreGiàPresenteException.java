package it.unisa.diem.softeng.easylibrary.archivio;

/**
 *
 * @brief Eccezione che segnala l'inserimento in un Archiviabile di un valore
 * già presente e non sovrascrivibile.
 */
public class ValoreGiàPresenteException extends RuntimeException {

    /**
     * @brief Costruttore.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public ValoreGiàPresenteException() {
    }

    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo ad un valore già
     * presente.
     */
    public ValoreGiàPresenteException(String string) {
        super(string);
    }

}
