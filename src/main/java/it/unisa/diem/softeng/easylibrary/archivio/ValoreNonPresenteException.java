package it.unisa.diem.softeng.easylibrary.archivio;

/**
 *
 * @brief Eccezione che segnala la richiesta in un Archivio di restituzione di
 * un valore non presente.
 */
public class ValoreNonPresenteException extends RuntimeException {

    public ValoreNonPresenteException() {
    }

    public ValoreNonPresenteException(String string) {
        super(string);
    }

}
