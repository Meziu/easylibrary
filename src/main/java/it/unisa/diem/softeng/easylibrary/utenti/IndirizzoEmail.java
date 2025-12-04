package it.unisa.diem.softeng.easylibrary.utenti;

public class IndirizzoEmail {

    private String email;

    public IndirizzoEmail(String email) {
        if (verifica(email)) {
            this.email = email;
        } else {
            throw new IndirizzoEmailInvalidoException(); // TODO
        }
    }

    public String getIndirizzoEmail() {
        return email;
    }

    /*
     * Verifica che l'email sia valida:
     * - Contiene una sola @
     * - Il dominio è esattamente @studenti.unisa.it
     * - La parte locale (prima della @) contiene solo lettere, numeri, punti,
     *   trattini e underscore, e non può essere vuota.
     */
    public static boolean verifica(String indirizzo) {
        if (indirizzo == null) {
            return false;
        }

        String e = indirizzo.trim().toLowerCase();

        String dominioRichiesto = "@studenti.unisa.it";

        // Deve finire con il dominio richiesto
        if (!e.endsWith(dominioRichiesto)) {
            return false;
        }

        // Parte locale (prima della @)
        String parteLocale = e.substring(0, e.length() - dominioRichiesto.length());

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
