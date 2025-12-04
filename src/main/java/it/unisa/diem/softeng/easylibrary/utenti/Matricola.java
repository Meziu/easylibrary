package it.unisa.diem.softeng.easylibrary.utenti;

import java.io.Serializable;

public final class Matricola implements Comparable<Matricola>, Serializable {

    private String matricola;

    public Matricola(String matricola) {
        setMatricola(matricola);
    }

    public String getMatricola() {
        return matricola;
    }

    protected void setMatricola(String nuovaMatricola) {
        if (verifica(nuovaMatricola)) {
            this.matricola = nuovaMatricola;
        } else {
            throw new MatricolaInvalidaException(); // TODO
        }
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

    @Override
    public int hashCode() {
        return this.matricola.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Matricola other = (Matricola) obj;
        if (!this.matricola.equals(other.matricola)) {
            return false;
        }
        return true;
    }
}
