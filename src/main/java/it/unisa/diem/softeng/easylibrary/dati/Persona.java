package it.unisa.diem.softeng.easylibrary.dati;

import java.io.Serializable;

/**
 * 
 * @brief Rappresentazione dell'anagrafica di una persona.
 * 
 * La classe è serializzabile e implementa l'interfaccia Comparable per consentire il confronto tra persone
 * basato sul cognome e, a parità di cognome, sul nome.
 */
public abstract class Persona implements Comparable<Persona>, Serializable {

    private String nome;
    private String cognome;

    /**
     * 
     * @brief Costruttore.
     * Costruisce un nuovo oggetto Persona a partire dalle
     * stringhe che ne compongono il nome e il cognome.
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
     * 
     * @return Valore negativo, zero o positivo se la persona corrente è rispettivamente
     * minore, uguale o maggiore del libro passato come parametro.
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
