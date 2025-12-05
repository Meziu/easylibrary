package it.unisa.diem.softeng.easylibrary.libri;

/**
 *
 * @brief Eccezione che segnala l'utilizzo o la creazione di un codice ISBN invalido.
 */
public class ISBNInvalidoException extends RuntimeException {

    public ISBNInvalidoException() {
    }

    public ISBNInvalidoException(String string) {
        super(string);
    }

}
