package it.unisa.diem.softeng.easylibrary.dati.utenti;

import java.io.Serializable;

/**
 *
 * @brief Indirizzo di posta elettronica (email). Gli indirizzi email sono
 * composti (secondo una versione semplificata dello standard internazionale RFC
 * 5322) da un campo di username e un nome di dominio. Ogni utente è associato
 * ad un IndirizzoEmail, che richiede che il nome di dominio deve corrispondere
 * a "@studenti.unisa.it".
 *
 */
public class IndirizzoEmail implements Serializable {

    private String indirizzo;

    /**
     *
     * @brief Costruttore. Costruisce un nuovo oggetto IndirizzoEmail a partire
     * dalla stringa di caratteri che lo compongono, verificandone la validità.
     *
     * @param\[in] email Stringa di caratteri dell'indirizzo email.
     *
     * @throws IndirizzoEmailInvalidoException Se l'indirizzo inserito non
     * aderisce allo standard di validità.
     *
     * \see verifica()
     *
     */
    public IndirizzoEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IndirizzoEmailInvalidoException("Email nulla o vuota");
        }

        if (verifica(email)) {
            this.indirizzo = email;
        } else {
            throw new IndirizzoEmailInvalidoException();
        }
    }

    /**
     * @brief Getter della stringa dell'indirizzo email.
     *
     * @return La stringa di caratteri che compongono l'indirizzo email.
     */
    public String getIndirizzoEmail() {
        return indirizzo;
    }

    /**
     * @brief Imposta un nuovo indirizzo email.
     * @param\[in] email Nuovo indirizzo email
     *
     * @throws IndirizzoEmailInvalidoException Se l'indirizzo inserito è nullo o
     * vuoto.
     */
    public void setIndirizzoEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IndirizzoEmailInvalidoException("Email nulla o vuota");
        }

        if (verifica(email)) {
            this.indirizzo = email;
        } else {
            throw new IndirizzoEmailInvalidoException();
        }
    }

    /**
     * @brief Verifica che l'email sia valida: - Contiene una sola @ - Il
     * dominio è esattamente @studenti.unisa.it - La parte locale (prima della
     * @) contiene solo lettere, numeri, punti, trattini e underscore, e non può
     * essere vuota.
     *
     * @param\[in] indirizzo Stringa di caratteri da verificare.
     *
     * @return true se la stringa è un indirizzo email valido (per la
     * biblioteca), false altrimenti.
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
}
