package it.unisa.diem.softeng.easylibrary.archivio;

/**
 *
 * @brief Eccezione che segnala l'inserimento in un Archiviabile di un valore gi√†
 * presente e non sovrascrivibile.
 */
public class ValoreGi‡PresenteException extends RuntimeException {

    /**
     * @brief Costruttore.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public ValoreGi‡PresenteException() {
    }

    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo ad un valore gi√† presente.
     */
    public ValoreGi‡PresenteException(String string) {
        super(string);
    }

}
