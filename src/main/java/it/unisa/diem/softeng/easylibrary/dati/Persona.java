package it.unisa.diem.softeng.easylibrary.dati;

import java.io.Serializable;

/**
 * 
 * @brief Rappresentazione dell'anagrafica di una persona.
 */
public abstract class Persona implements Comparable<Persona>, Serializable {

    private String nome;
    private String cognome;

    /**
     * 
     * @brief Costruttore.
     * Costruisce un nuovo oggetto Persona a partire dalle
     * stringhe di caratteri che ne compongono il nome e il cognome.
     * 
     * @param\[in] nome Stringa di caratteri del nome.
     * @param\[in] cognome Stringa di caratteri del cognome.
     */
    public Persona(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * @brief Getter della stringa del nome.
     * 
     * @return La stringa di caratteri che compongono il nome della Persona.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @brief Getter della stringa del cognome.
     * 
     * @return La stringa di caratteri che compongono il cognome della Persona.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @brief Setter della stringa del nome.
     * 
     * @param\[in] nome La stringa di caratteri con cui sostituire il nome della Persona.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @brief Setter della stringa del cognome.
     * 
     * @param\[in] cognome La stringa di caratteri con cui sostituire il cognome della Persona.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * 
     * @brief Comparazione con un'altra Persona.
     * La comparazione è svolta aderendo al contratto di Comparable,
     * dove una Persona è ordinata rispetto ad un'altra in ordine
     * lessicografico prima del cognome e poi, a parità di
     * cognome, del nome.
     */
    @Override
    public int compareTo(Persona p) {
        int c = this.cognome.compareTo(p.cognome);

        if (c == 0) {
            return this.nome.compareTo(p.nome);
        }

        return c;
    }

}
