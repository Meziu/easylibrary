package ClassiPrincipali;

public class Matricola implements Comparable<Matricola> {
    private String matricola;
    
    public Matricola(String matricola){
        this.matricola = matricola;
    }
    
    public String getMatricola() {
        return matricola;
    }

    
    
    /*Funzione che verifica la validità della matrocola.
    La funzione che ne verifica l'unicità deve essere implementata in Gestore Utenti*/
    /*
     * Verifica che la matricola sia valida:
     * - lunga esattamente 8 caratteri
     * - solo numeri o lettere
    */
    public boolean verifica() {
        if (matricola == null) return false;

        // rimuovi spazi eventuali
        String m = matricola.trim();

        // lunghezza standard
        if (m.length() != 8) return false;

        //deve essere alfanumerica
        for (char c : m.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }

        return true;
    }

    @Override
    public int compareTo(Matricola m) {
        return this.matricola.compareTo(m.matricola);
    }
}
