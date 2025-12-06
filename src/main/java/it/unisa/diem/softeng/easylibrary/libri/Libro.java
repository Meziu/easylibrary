package it.unisa.diem.softeng.easylibrary.libri;

import java.io.Serializable;
import java.util.List;


/**@class Libro.java
 * @brief Classe che rappresenta un libro presente nel sistema della biblioteca.
 * La classe Libro contiene tutte le informazioni fondamentali di un libro:
 * titolo, autori, anno di pubblicazione, ISBN e numero di copie disponibili.
 * Implementa l'interfaccia Comparable per consentire il confronto tra libri
 * basato sull'ISBN ed è serializzabile.
 * 
 * @see Autore
 * @see ISBN
*/
public class Libro implements Comparable<Libro>, Serializable {

    private String titolo; ///< Titolo del libro
    private List<Autore> autori; ///<  Lista degli autori del libro.
    private int dataPubblicazione; ///< Data di pubblicazione del libro.
    private final ISBN isbn; ///< Codice ISBN del libro.
    private int copieDisponibili; ///< Numero di copie disponibili del libro.

    
    /**
     * @brief Costruttore della classe Libro.
     * 
     * @param titolo Titolo del libro.
     * @param autori Lista degli autori del libro.
     * @param dataPubblicazione Data di pubblicazione del libro.
     * @param isbn Codice ISBN del libro in formato stringa.
     * @param copieDisponibili Numero di copie disponibili.
     */
    public Libro(String titolo, List<Autore> autori, int dataPubblicazione, String isbn, int copieDisponibili) {
        this.titolo = titolo;
        this.autori = autori;
        this.dataPubblicazione = dataPubblicazione;
        this.isbn = new ISBN(isbn);
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
    public int getDataPubblicazione() {
        return dataPubblicazione;
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
     * @param titolo Nuovo titolo del libro.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    
    /**
     * @brief Imposta la lista degli autori del libro.
     * 
     * @param autori Nuova lista di autori.
     */
    public void setAutori(List<Autore> autori) {
        this.autori = autori;
    }

    /**
     * @brief Imposta la data di pubblicazione del libro.
     * 
     * @param dataPubblicazione Nuova data di pubblicazione.
     */
    public void setDataPubblicazione(int dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }
    
    /**
     * @brief Imposta il numero di copie disponibili del libro.
     * 
     * @param copieDisponibili Nuovo numero di copie disponibili.
     */
    public void setCopieDisponibili(int copieDisponibili) {
        this.copieDisponibili = copieDisponibili;
    }

    /**
     * @brief Aggiunge un autore alla lista degli autori del libro.
     * @post L'oggetto di tipo Autore viene aggiunto alla lista.
     * 
     * @param a Autore da aggiungere alla lista.
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
     * @param a L'autore da rimuovere.
     */
    public void rimuoviAutore(Autore a) {
        autori.remove(a);
    }

    
    /**
     * @brief Confronta due libri in base al codice ISBN.
     * 
     * @param l Libro da confrontare con l'istanza corrente.
     * @return Valore negativo, zero o positivo se questo libro è rispettivamente
     * minore, uguale o maggiore del libro passato come parametro.
     */
    @Override
    public int compareTo(Libro l) {
        int c = this.getTitolo().compareTo(l.getTitolo());
        
        if (c == 0) {
            return this.getISBN().compareTo(l.getISBN());
        }
        
        return this.isbn.compareTo(l.isbn);
    }

}
