package it.unisa.diem.softeng.easylibrary.dati.utenti;

import it.unisa.diem.softeng.easylibrary.dati.Anagrafica;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @brief Rappresenta un utente della biblioteca.
 *
 * La classe contiene informazioni specifiche
 * degli utenti della biblioteca, quali la sua Anagrafica, la Matricola, l'IndirizzoEmail
 * e la lista dei prestiti attivi.
 *
 * Gli utenti sono ordinati prima per Anagrafica
 * e poi, a parità, per matricola.
 *
 * @see Anagrafica
 * @see Matricola
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
     */
    public Utente(String nome, String cognome, Matricola matricola, IndirizzoEmail email) {
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
    
    // ------ get necessariiiii ------
    /**
     * @brief Restituisce il nome dell'utente.
     * @return Il nome
     */
    public String getNome(){
        return anagrafica.getNome();
    }
    
    /**
     * @brief Restituisce il cognome dell'utente.
     * @return Il cognome
     */
    public String getCognome(){
        return anagrafica.getCognome();
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
     * @return Lista non modificabile dei prestiti attivi
     */
    public List<Prestito> getPrestitiAttivi() {
        return Collections.unmodifiableList(prestitiAttivi);
    }
    
    
    /**
     * @brief Imposta un nuovo indirizzo email.
     * @param\[in] email Nuovo indirizzo email
     */
    public void setEmail(IndirizzoEmail email) {
        Utente.this.email = email;
    }

    /**
     * @brief Registra un nuovo prestito attivo per l'utente.
     * @param\[in] p Prestito da aggiungere
     */
    public void registraPrestito(Prestito p) {
        Utente.this.prestitiAttivi.add(p);
    }

    /**
     * @brief Rimuove un prestito dalla lista dei prestiti attivi dell'utente.
     * @param\[in] p Prestito da rimuovere
     */
    public void rimuoviPrestito(Prestito p) {
        Utente.this.prestitiAttivi.remove(p);
    }

    
    /**
     * @brief Confronta l'utente con un'altro Utente.
     * La comparazione è svolta aderendo al contratto di Comparable,
     * tramite il metodo compareTo() nella classe Anagrafica e,
     * a parità di cognome e nome, per matricola.
     *
     * @param\[in] u Utente da confrontare
     * @return Negativo, zero o positivo se questo utente è rispettivamente minore,
     *         uguale o maggiore rispetto alla persona passata
     */
    @Override
    public int compareTo(Utente u) {
        int c = this.getAnagrafica().compareTo(u.getAnagrafica());

        if (c == 0) {
            return this.matricola.compareTo(u.matricola);
        }
        
        return c;
    }
}
