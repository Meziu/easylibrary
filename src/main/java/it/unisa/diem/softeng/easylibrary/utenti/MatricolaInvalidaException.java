package it.unisa.diem.softeng.easylibrary.utenti;

/**
 *
 * @brief Eccezione che segnala l'utilizzo o la creazione di una Matricola invalida.
 */
public class MatricolaInvalidaException extends RuntimeException {

    public MatricolaInvalidaException() {
    }

    public MatricolaInvalidaException(String string) {
        super(string);
    }

}
