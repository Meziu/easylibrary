package ClassiPrincipali;

//import Interfacce.Verificabile;

public class IndirizzoEmail{ //implements Verificabile
    private String email;
    
    public IndirizzoEmail(String email){
        this.email = email;
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
    public boolean verifica() {
        if (email == null) return false;

        String e = email.trim().toLowerCase();

        String dominioRichiesto = "@studenti.unisa.it";

        // Deve finire con il dominio richiesto
        if (!e.endsWith(dominioRichiesto)) return false;

        // Parte locale (prima della @)
        String parteLocale = e.substring(0, e.length() - dominioRichiesto.length());

        // Non può essere vuota
        if (parteLocale.isEmpty()) return false;

        // La parte locale deve essere alfanumerica o . _ -
        for (char c : parteLocale.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || c == '.' || c == '_' || c == '-')) {
                return false;
            }
        }

        return true;
    }
}
