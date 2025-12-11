package it.unisa.diem.softeng.easylibrary.dati.utenti;

/**
 *
 * @brief Eccezione lanciata quando un utente tenta di registrare un nuovo prestito
 * ma ha già raggiunto il limite massimo di prestiti attivi consentiti.
 *
 * Viene utilizzata per segnalare errori nella gestione dei prestiti, impedendo
 * di superare il numero massimo di libri presi in prestito da un singolo utente.
 * 
 */
public class LimitePrestitiSuperatoException extends RuntimeException {

    /**
     * @brief Costruttore.
     *
     * Crea una nuova eccezione senza specificare alcun messaggio di errore.
     */
    public LimitePrestitiSuperatoException() {
    }
    
    /**
     * @brief Costruttore con messaggio di errore.
     *
     * @param string messaggio descrittivo dell'errore relativo al limite dei prestiti attivi per quell'utente superato.
     */
    public LimitePrestitiSuperatoException(String string) {
        super(string);
    }
    
}
