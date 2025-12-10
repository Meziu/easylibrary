package it.unisa.diem.softeng.easylibrary.dati.utenti;

import java.io.Serializable;

/**
 * 
 * @brief Indirizzo di posta elettronica (email).
 * Gli indirizzi email sono composti (per standard
 * internazionale RFC 5322) da un campo di username e un nome di dominio.
 * Ogni utente è associato ad un IndirizzoEmail, che richiede che il nome
 * di dominio deve corrispondere a "studenti.unisa.it".
 * 
 */
public class IndirizzoEmail implements Serializable {

    private String email;

    /**
     * 
     * @brief Costruttore.
     * Costruisce un nuovo oggetto IndirizzoEmail a partire dalla
     * stringa di caratteri che lo compongono, verificandone la validità.
     * 
     * @param\[in] email Stringa di caratteri dell'indirizzo email.
     * 
     * @throws IndirizzoEmailInvalidoException Se l'indirizzo inserito
     * non aderisce allo standard di validità.
     * 
     * \see verifica()
     * 
     */
    public IndirizzoEmail(String email) {
        if (verifica(email)) {
            this.email = email;
        } else {
            throw new IndirizzoEmailInvalidoException(); // TODO
        }
    }

    /**
     * @brief Getter della stringa dell'indirizzo email.
     * 
     * @return La stringa di caratteri che compongono l'indirizzo email.
     */
    public String getIndirizzoEmail() {
        return email;
    }
    
    /**
     * @brief Imposta un nuovo indirizzo email.
     * @param\[in] email Nuovo indirizzo email
     */
    public void setIndirizzoEmail(String email) {
        if (verifica(email)) {
            this.email = email;
        } else {
            throw new IndirizzoEmailInvalidoException(); // TODO
        }
    }

    /*
     * Verifica che l'email sia valida:
     * - Contiene una sola @
     * - Il dominio è esattamente @studenti.unisa.it
     * - La parte locale (prima della @) contiene solo lettere, numeri, punti,
     *   trattini e underscore, e non può essere vuota.
     */
    /**
     * @brief Verifica dell'indirizzo email.
     * Verifica se la stringa passata come argomento sia o meno un indirizzo email
     * valido da usare nella biblioteca a seconda degli standard
     * definiti dal documento RFC 5322 e se il campo del nome di dominio corrisponde
     * a "studenti.unisa.it".
     * 
     * @param\[in] indirizzo Stringa di caratteri da verificare.
     * 
     * @return true se la stringa è un indirizzo email valido (per la biblioteca), false altrimenti.
     */
    public static boolean verifica(String indirizzo) {
        if (indirizzo == null) {
            return false;
        }
        
        String dominioRichiesto = "@studenti.unisa.it";

        // Deve finire con il dominio richiesto
        if (!indirizzo.endsWith(dominioRichiesto)) {
            return false;
        }

        // Recupero solo la parte locale (prima della @)
        String parteLocale = indirizzo.substring(0, indirizzo.length() - dominioRichiesto.length());

        // Non può essere vuota
        if (parteLocale.isEmpty()) {
            return false;
        }

        // La parte locale deve essere alfanumerica o . _ -
        for (char c : parteLocale.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || c == '.' || c == '_' || c == '-')) {
                return false;
            }
        }

        return true;
    }
    
    @Override
    public String toString(){
        return email;
    }
}
