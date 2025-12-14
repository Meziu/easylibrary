package it.unisa.diem.softeng.easylibrary.dati.utenti;

import java.io.Serializable;

/**
 * @brief  Rappresenta la matricola di un utente della biblioteca.
 * Ogni \ref Utente è associato univocamente ad una matricola.
 * Le matricole sono composte da esattamente 10 cifre numeriche.
 * 
 */
public final class Matricola implements Comparable<Matricola>, Serializable {

    private final String matricola;

    /**
     * @brief Costruttore.
     * Costruisce un nuovo oggetto Matricola a partire dalla
     * stringa di caratteri che la compongono, verificandone la validità.
     * 
     * @param\[in] matricola Stringa di caratteri della matricola.
     * 
     * @throws MatricolaInvalidaException Se la matricola non aderisce
     * allo standard di validità.
     * 
     * \see verifica()
     * 
     */
    public Matricola(String matricola) {
        if (verifica(matricola)) {
            this.matricola = matricola;
        } else {
            throw new MatricolaInvalidaException();
        }
    }

    /**
     * @brief Restituisce la stringa della matricola.
     * 
     * @return La stringa di caratteri che compongono la Matricola.
     */
    public String getMatricola() {
        return matricola;
    }
    
    /**
     * @brief Verifica della matricola.
     * Verifica se la stringa passata come argomento sia o meno una matricola valida.
     * Una matricola è valida se è composta esattamente da 10 cifre numeriche
     * 
     * @param\[in] matricola Stringa di caratteri da verificare.
     * @return true se la stringa è una matricola valida, false altrimenti.
     */
    public static boolean verifica(String matricola) {
        if (matricola == null) {
            return false;
        }

        // lunghezza standard
        if (matricola.length() != 10) {
            return false;
        }

        //deve essere numerica
        for (char c : matricola.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * @brief Comparazione con un'altra Matricola.
     * La comparazione è svolta aderendo al contratto di Comparable,
     * dove una Matricola è ordinata rispetto ad un'altra in ordine
     * lessicografico della stringa di caratteri associata.
     * 
     * @param\[in] matricola da confrontare
     * @return Valore negativo, zero o positivo se questa matricola è rispettivamente minore,
     *         uguale o maggiore rispetto alla matricola passata.
     */
    @Override
    public int compareTo(Matricola matricola) {
        return this.matricola.compareTo(matricola.matricola);
    }

    /**
     * @brief HashCode associato alla Matricola.
     * Il calcolo è svolto aderendo al contratto di Object.hashCode().
     * 
     * @return valore hash coerente con equals().
     */
    @Override
    public int hashCode() {
        return this.matricola.hashCode();
    }

    /**
     * @brief Criterio di uguaglianza.
     * Il criterio aderisce al contratto di Object.equals(),
     * dove una Matricola è considerata uguale ad un'altra solo se
     * la stringa di caratteri associata è uguale.
     * 
     * @param obj oggetto con cui confrontare.
     * @return true se le matricola sono uguali, false altrimenti.
     */
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