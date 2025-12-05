package it.unisa.diem.softeng.easylibrary.archivio;

/**
 *
 * @brief Eccezione che segnala l'inserimento in un Archivio di un valore già
 * presente e non sovrascrivibile.
 */
public class ValoreGiàPresenteException extends RuntimeException {

    public ValoreGiàPresenteException() {
    }

    public ValoreGiàPresenteException(String string) {
        super(string);
    }

}
