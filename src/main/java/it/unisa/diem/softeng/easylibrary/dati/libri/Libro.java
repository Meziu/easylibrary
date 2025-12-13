package it.unisa.diem.softeng.easylibrary.dati.libri;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @brief Classe che rappresenta un libro presente nel sistema della biblioteca.
 * La classe Libro contiene le seguenti informazioni relative ad un libro:
 * titolo, autori, anno di pubblicazione, ISBN e numero di copie disponibili.
 * 
 * La classe è serializzabile e implementa l'interfaccia Comparable per consentire il confronto tra libri
 * basato sul titolo e, a parità di titolo, sull'ISBN.
 * 
 * @see Autore
 * @see ISBN
*/
public class Libro implements Comparable<Libro>, Serializable {

    private String titolo; ///< Titolo del libro
    private List<Autore> autori; ///<  Lista degli autori del libro.
    private Year annoPubblicazione; ///< Anno di pubblicazione del libro.
    private final ISBN isbn; ///< Codice ISBN del libro.
    private int copieDisponibili; ///< Numero di copie disponibili del libro.

    
    /**
     * @brief Costruttore della classe Libro.
     * 
     * @param\[in] titolo Stringa di caratteri del titolo del libro.
     * @param\[in] autori Lista degli autori del libro.
     * @param\[in] annoPubblicazione  Anno di pubblicazione del libro.
     * @param\[in] isbn Codice ISBN.
     * @param\[in] copieDisponibili Interi che indica il numero di copie disponibili.
     */
    public Libro(String titolo, List<Autore> autori, int annoPubblicazione, ISBN isbn, int copieDisponibili) {
        this.titolo = titolo;
        this.autori = new ArrayList<>();
        this.autori.addAll(autori);
        this.annoPubblicazione = Year.of(annoPubblicazione);
        this.isbn = isbn;
        this.copieDisponibili = copieDisponibili;
    }

    
    /**
     * @brief Restituisce il titolo del libro.
     * 
     * @return Titolo del libro.
     */
    public String getTitolo() {
        return titolo;
    }

    
    /**
     * @brief Restituisce la lista degli autori del libro.
     * 
     * @return Lista degli autori.
     */
    public List<Autore> getAutori() {
        return autori;
    }

    
    /**
     * @brief Restituisce l'anno di pubblicazione del libro.
     * 
     * @return Anno di pubblicazione.
     */
    public int getAnnoPubblicazione() {
        return annoPubblicazione.getValue();
    }

    
    /**
     * @brief Restituisce il codice ISBN del libro.
     * 
     * @return ISBN del libro.
     */
    public ISBN getISBN() {
        return isbn;
    }

    
    /**
     * @brief Restituisce il numero di copie disponibili.
     * 
     * @return Numero di copie disponibili.
     */
    public int getCopieDisponibili() {
        return copieDisponibili;
    }
    
    /**
     * @brief Imposta il titolo del libro.
     * 
     * @param\[in] titolo Nuovo titolo del libro.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    
    /**
     * @brief Imposta la lista degli autori del libro.
     * 
     * @param\[in] autori Nuova lista di autori.
     */
    public void setAutori(List<Autore> autori) {
        this.autori = autori;
    }

    /**
     * @brief Imposta la data di pubblicazione del libro.
     * 
     * @param\[in] annoPubblicazione Nuovo anno di pubblicazione.
     */
    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = Year.of(annoPubblicazione);
    }
    
    /**
     * @brief Imposta il numero di copie disponibili del libro.
     * 
     * @param\[in] copieDisponibili Nuovo numero di copie disponibili.
     */
    public void setCopieDisponibili(int copieDisponibili) {
        this.copieDisponibili = copieDisponibili;
    }

    /**
     * @brief Aggiunge un autore alla lista degli autori del libro.
     * 
     * @post L'oggetto di tipo Autore viene aggiunto alla lista.
     * 
     * @param\[in] a Autore da aggiungere alla lista.
     * 
     */
    public void aggiungiAutore(Autore a) {
        autori.add(a);
    }

     /**
     * @brief Rimuove un autore dalla lista degli autori del libro.
     * 
     * @post L'oggetto di tipo Autore viene rimosso dalla lista.
     * 
     * @param[in] a L'autore da rimuovere.
     */
    public void rimuoviAutore(Autore a) {
        autori.remove(a);
    }

    
    /**
     * @brief Comparazione con un altro libro.
     * 
     * La comparazione è svolta aderendo al contratto di Comparable,
     * dove un libro è ordinato rispetto ad un altro in ordine
     * lessicografico prima del titolo e poi, a parità di
     * titolo, del codice ISBN.
     * 
     * @param\[in] l Libro da confrontare con l'istanza corrente.
     * 
     * @return Valore negativo, zero o positivo se il libro corrente è rispettivamente
     * minore, uguale o maggiore del libro passato come parametro.
     */
    @Override
    public int compareTo(Libro l) {
        int c = this.getTitolo().compareTo(l.getTitolo());
        
        if (c == 0) {
            return this.getISBN().compareTo(l.getISBN());
        }
        
        return c;
    }
    
    /**
     * 
     * @brief Uguaglianza con un'altro Libro.
     * 
     * @return true se i due Libro hanno titolo e ISBN uguale, false altrimenti.
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
        final Libro other = (Libro) obj;
        if (!Objects.equals(this.titolo, other.titolo)) {
            return false;
        }
        
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        
        return true;
    }

}
