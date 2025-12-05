package it.unisa.diem.softeng.easylibrary.utenti;

/**
 *
 * @brief Eccezione che segnala l'utilizzo di un IndirizzoEmail invalido.
 */
public class IndirizzoEmailInvalidoException extends RuntimeException {

    public IndirizzoEmailInvalidoException(String string) {
        super(string);
    }

    public IndirizzoEmailInvalidoException() {
    }

}
