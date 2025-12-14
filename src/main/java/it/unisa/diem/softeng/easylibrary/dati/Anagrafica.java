package it.unisa.diem.softeng.easylibrary.dati;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @brief Rappresentazione dell'anagrafica di una persona.
 * 
 * La classe è serializzabile e implementa l'interfaccia Comparable per consentire il confronto tra persone
 * basato sul cognome e, a parità di cognome, sul nome.
 */
public class Anagrafica implements Comparable<Anagrafica>, Serializable {

    private String nome;
    private String cognome;

    /**
     * 
     * @brief Costruttore.
     * Costruisce un nuovo oggetto Anagrafica a partire dalle
     * stringhe che ne compongono il nome e il cognome.
     * 
     * @param\[in] nome Stringa di caratteri del nome.
     * @param\[in] cognome Stringa di caratteri del cognome.
     */
    public Anagrafica(String nome, String cognome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome nullo o vuoto");
        }
        if (cognome == null || cognome.isEmpty()) {
            throw new IllegalArgumentException("Cognome nullo o  vuoto");
        }
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * @brief Getter della stringa del nome.
     * 
     * @return La stringa di caratteri che compongono il nome della Anagrafica.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @brief Getter della stringa del cognome.
     * 
     * @return La stringa di caratteri che compongono il cognome della Anagrafica.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @brief Setter della stringa del nome.
     * 
     * @param\[in] nome La stringa di caratteri con cui sostituire il nome della Anagrafica.
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome nullo o vuoto");
        }
        this.nome = nome;
    }

    /**
     * @brief Setter della stringa del cognome.
     * 
     * @param\[in] cognome La stringa di caratteri con cui sostituire il cognome della Anagrafica.
     */
    public void setCognome(String cognome) {
        if (cognome == null || cognome.trim().isEmpty()) {
            throw new IllegalArgumentException("Cognome nullo o vuoto");
        }
        this.cognome = cognome;
    }

    /**
     * 
     * @brief Comparazione con un'altra Anagrafica.
     * La comparazione è svolta aderendo al contratto di Comparable,
     * dove una Anagrafica è ordinata rispetto ad un'altra in ordine
     * lessicografico prima del cognome e poi, a parità di
     * cognome, del nome.
     * 
     * @return Valore negativo, zero o positivo se l'anagrafica corrente è rispettivamente
     * minore, uguale o maggiore dell'anagrafica passato come parametro.
     */
    @Override
    public int compareTo(Anagrafica a) {
        int c = this.cognome.compareToIgnoreCase(a.cognome);

        if (c == 0) {
            return this.nome.compareToIgnoreCase(a.nome);
        }

        return c;
    }

    /**
     * 
     * @brief Uguaglianza con un'altra Anagrafica.
     * 
     * @return true se le due Anagrafica hanno nome e cognome uguale (per String.equals), false altrimenti.
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
        final Anagrafica other = (Anagrafica) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cognome, other.cognome)) {
            return false;
        }
        return true;
    }

}
