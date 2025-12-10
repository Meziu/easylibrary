package it.unisa.diem.softeng.easylibrary.dati.prestiti;

/**
 * @brief Eccezione lanciata quando non sono disponibili copie di un libro richiesto in prestito.
 *
 * Viene utilizzata per segnalare che non ci sono copie di un libro attualmente disponibili,
 * impedendo di registrare un nuovo prestito.
 */
public class NessunaCopiaDisponibileException extends RuntimeException {

    /**
     * @brief Costruttore.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public NessunaCopiaDisponibileException() {
    }
    
    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo alla non disponibilità di un libro.
     */
    public NessunaCopiaDisponibileException(String string) {
        super(string);
    }
}
