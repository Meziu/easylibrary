package it.unisa.diem.softeng.easylibrary.dati;

import it.unisa.diem.softeng.easylibrary.eccezioni.MatricolaInvalidaException;

public class Matricola implements Comparable<Matricola> {

    private String matricola;

    public Matricola(String matricola) {
        if (verifica(matricola)) {
            this.matricola = matricola;
        } else {
            throw new MatricolaInvalidaException(); // TODO
        }
    }

    public String getMatricola() {
        return matricola;
    }

    /*Funzione che verifica la validità della matricola.
    La funzione che ne verifica l'unicità deve essere implementata in Gestore Utenti*/
 /*
     * Verifica che la matricola sia valida:
     * - lunga esattamente 8 caratteri
     * - solo numeri o lettere
     */
    public static boolean verifica(String mat) {
        if (mat == null) {
            return false;
        }

        // rimuovi spazi eventuali
        String m = mat.trim();

        // lunghezza standard
        if (m.length() != 8) {
            return false;
        }

        //deve essere alfanumerica
        for (char c : m.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int compareTo(Matricola m) {
        return this.matricola.compareTo(m.matricola);
    }
}
