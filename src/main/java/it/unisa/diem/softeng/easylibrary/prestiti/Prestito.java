package it.unisa.diem.softeng.easylibrary.prestiti;

import it.unisa.diem.softeng.easylibrary.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.libri.ISBN;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * @brief Rappresenta un prestito di un libro da parte di un utente.
 *
 * La classe memorizza l'associazione tra l'utente (tramite la matricola)
 * e il libro (tramite ISBN), insieme allo stato del prestito e alla data di scadenza del prestito.
 *
 * La classe è serializzabile e implementa Comparable per consentire l'ordinamento dei prestiti
 * prima per data di scadenza, poi per matricola dell'utente e infine per ISBN del libro.
 */
public class Prestito implements Comparable<Prestito>, Serializable {

    private final Matricola matricolaUtente;
    private final ISBN isbn;
    private StatoPrestito statoPrestito;
    private LocalDate dataDiScadenza;

    
    /**
     * @brief Costruttore di un nuovo prestito.
     *
     * @param\[in] matricolaUtente Matricola dell'utente che prende in prestito il libro
     * @param\[in] isbn ISBN del libro preso in prestito
     * @param\[in] statoPrestito Stato iniziale del prestito
     * @param\[in] scadenzaPrestito Data di scadenza del prestito
     */
    public Prestito(Matricola matricolaUtente, ISBN isbn, StatoPrestito statoPrestito, LocalDate scadenzaPrestito) {
        this.matricolaUtente = matricolaUtente;
        this.isbn = isbn;
        this.statoPrestito = statoPrestito;
        this.dataDiScadenza = scadenzaPrestito;
    }

    /**
     * @brief Restituisce la matricola dell'utente associato al prestito.
     * @return La matricola dell'utente
     */
    public Matricola getMatricola() {
        return matricolaUtente;
    }

    /**
     * @brief Restituisce l'ISBN del libro preso in prestito.
     * @return L'ISBN del libro
     */
    public ISBN getISBN() {
        return isbn;
    }

    /**
     * @brief Restituisce lo stato corrente del prestito.
     * @return Lo stato del prestito
     */
    public StatoPrestito getStato() {
        return statoPrestito;
    }

    /**
     * @brief Restituisce la data di scadenza del prestito.
     * @return Data di scadenza
     */
    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    /**
     * @brief Verifica se il prestito è scaduto.
     * @return true se la data di scadenza è antecedente alla data odierna, false altrimenti
     */
    public boolean isScaduto() {
        return dataDiScadenza.isBefore(LocalDate.now());
    }

    /**
     * @brief Aggiorna lo stato del prestito.
     * @param\[in] nuovoStato Nuovo stato da assegnare al prestito
     */
    public void setStatoPrestito(StatoPrestito nuovoStato) {
        this.statoPrestito = nuovoStato;
    }
    
    /**
     * @brief Aggiorna la data di scadenza del prestito.
     * @param\[in] nuovaDataDiScadenza Nuova data di scadenza
     */
    public void setDataDiScadenza(LocalDate nuovaDataDiScadenza) {
        this.dataDiScadenza = nuovaDataDiScadenza;
    }

    
    /**
     * @brief Comparazione con un altro prestito.
     *
     * La comparazione è svolta aderendo al contratto di Comparable,
     * dove un prestito è ordinato rispetto ad un altro per:
     * - Data di scadenza (prima i prestiti più prossimi alla scadenza)
     * - Matricola dell'utente
     * - ISBN del libro
     *
     * @param\[in] p Prestito da confrontare
     * @return Negativo, zero o positivo se questo prestito è rispettivamente
     *         minore, uguale o maggiore rispetto a quello passato
     */
    @Override
    public int compareTo(Prestito p) {
        int c1 = this.dataDiScadenza.compareTo(p.dataDiScadenza);

        if (c1 == 0) {
            int c2 = this.matricolaUtente.compareTo(p.matricolaUtente);

            if (c2 == 0) {
                return this.isbn.compareTo(p.isbn);
            }

            return c2;
        }

        return c1;
    }
}
