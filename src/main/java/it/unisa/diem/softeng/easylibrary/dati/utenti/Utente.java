package it.unisa.diem.softeng.easylibrary.dati.utenti;

import it.unisa.diem.softeng.easylibrary.dati.Anagrafica;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * @brief Rappresenta un utente della biblioteca.
 *
 * La classe contiene informazioni specifiche degli utenti della biblioteca,
 * quali la sua Anagrafica, la Matricola, l'IndirizzoEmail
 * e la lista dei prestiti attivi.
 *
 * Gli utenti sono ordinati prima per Anagrafica
 * e poi, a parità, per matricola.
 *
 * @see Anagrafica
 * @see Matricola
 * @see IndirizzoEmail
 * @see Prestito
 */
public class Utente implements Comparable<Utente>, Serializable {

    private Anagrafica anagrafica;
    private final Matricola matricola;
    private IndirizzoEmail email;
    private final List<Prestito> prestitiAttivi;

    
    /**
     * @brief Costruisce un nuovo utente.
     *
     * @param\[in] nome Nome dell'utente
     * @param\[in] cognome Cognome dell'utente
     * @param\[in] matricola Matricola univoca
     * @param\[in] email Indirizzo email
     * 
     * @throws IllegalArgumentException Se la matricola e/o l'indirizzo email passato
     * è nullo.
     */
    public Utente(String nome, String cognome, Matricola matricola, IndirizzoEmail email) {
        if(matricola == null){
            throw new IllegalArgumentException("Matricola nulla");
        }
        
        if(email == null){
            throw new IllegalArgumentException("Email nulla");
        }
        
        this.anagrafica = new Anagrafica(nome, cognome);
        this.matricola = matricola;
        this.email = email;
        this.prestitiAttivi = new ArrayList<>();
    }
    
    /**
     * @brief Restituisce l'Anagrafica dell'utente.
     * @return L'anagrafica
     */
    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    /**
     * @brief Restituisce la matricola dell'utente.
     * @return La matricola
     */
    public Matricola getMatricola() {
        return matricola;
    }

    /**
     * @brief Restituisce l'indirizzo email dell'utente.
     * @return L'email
     */
    public IndirizzoEmail getEmail() {
        return email;
    }
    
    /**
     * @brief Restituisce la lista dei prestiti attivi.
     *
     * La lista è restituita come non modificabile.
     * In tal modo le modifiche ai prestiti possono avvenire solo tramite i metodi di Utente.
     * @return Lista non modificabile dei prestiti attivi
     */
    public List<Prestito> getPrestitiAttivi() {
        return Collections.unmodifiableList(prestitiAttivi);
    }
    

    /**
     * @brief Imposta un nuovo indirizzo email.
     * @param\[in] email Nuovo indirizzo email
     * 
     * @throws IllegalArgumentException Se l'indirizzo email passato è nullo.
     */
    public void setEmail(IndirizzoEmail email) {
        if(email == null){
            throw new IllegalArgumentException("Email nulla");
        }
        this.email = email;
    }

    /**
     * @brief Registra un nuovo prestito attivo per l'utente.
     * @param\[in] p Prestito da aggiungere
     * 
     * @throws LimitePrestitiSuperatoException
     * @throws IllegalArgumentException Se il prestito passato
     * è nullo.
    */
    public void registraPrestito(Prestito p) {
        if (p == null) {
            throw new IllegalArgumentException("Prestito nullo");
        }
        if (prestitiAttivi.size() >= 3) {
            throw new LimitePrestitiSuperatoException();
        }
        if (this.prestitiAttivi.size() >= 3) {
            throw new LimitePrestitiSuperatoException();
        }

        this.prestitiAttivi.add(p);
    }

    /**
     * @brief Rimuove un prestito dalla lista dei prestiti attivi dell'utente.
     * @param\[in] p Prestito da rimuovere
     */
    public void rimuoviPrestito(Prestito p) {
        this.prestitiAttivi.remove(p);
    }

    
    /**
     * @brief Confronta l'utente con un altro Utente.
     * 
     * La comparazione è svolta aderendo al contratto di Comparable,
     * tramite il metodo compareTo() nella classe Anagrafica e,
     * a parità di Anagrafica, per matricola.
     *
     * @param\[in] u Utente da confrontare
     * @return Valore negativo, zero o positivo se questo utente è rispettivamente minore,
     *         uguale o maggiore rispetto al'utente passato.
     */
    @Override
    public int compareTo(Utente u) {
        int c = this.getAnagrafica().compareTo(u.getAnagrafica());

        if (c == 0) {
            return this.matricola.compareTo(u.matricola);
        }
        
        return c;
    }
    
    /**
     * 
     * @brief Uguaglianza con un altro Utente.
     * Due utenti sono considerati uguali se hanno la stessa Anagrafica e la stessa Matricola.
     * 
     * @param obj oggetto con cui confrontare.
     * @return true se gli utenti sono uguali, false altrimenti.
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
        
        final Utente other = (Utente) obj;
        if (!Objects.equals(this.anagrafica, other.anagrafica)) {
            return false;
        }
        
        if (!Objects.equals(this.matricola, other.matricola)) {
            return false;
        }
        
        return true;
    }
    
    /**
    * @brief Rappresentazione testuale dell'utente.
    * Restituisce una stringa che identifica univocamente l'utente in forma
    * leggibile, nel formato: nome cognome matricola.
    *
    * @return stringa rappresentativa dell'utente
    */
    @Override
    public String toString() {
        return this.anagrafica.getNome() + " " + this.anagrafica.getCognome() + " <" + this.matricola.getMatricola() + ">";
    }
    
}
