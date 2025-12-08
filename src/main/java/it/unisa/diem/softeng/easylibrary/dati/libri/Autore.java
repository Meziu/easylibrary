package it.unisa.diem.softeng.easylibrary.dati.libri;

import it.unisa.diem.softeng.easylibrary.dati.Anagrafica;
import java.io.Serializable;


/**
 * @brief Classe che rappresenta un autore di libri presenti nel sistema della biglioteca.
 * La classe Autore contiene il riferimento ad un oggetto di classe Anagrafica e
 * rappresenta un autore associato a uno o più libri.
 * 
 * @see Anagrafica
 * @see Libro
*/
public class Autore implements Comparable<Autore>, Serializable {
    private Anagrafica anagrafica;
    
    /**
     * @brief Costruttore.
     * Costruisce un nuovo oggetto Autore a partire dalle
     * stringhe di caratteri che ne compongono il nome e il cognome.
     * 
     *  @param\[in] nome Stringa di caratteri del nome dell'autore.
     *  @param\[in] cognome Stringa di caratteri del cognome dell'autore.
     */
    public Autore(String nome, String cognome) {
        this.anagrafica = new Anagrafica(nome, cognome);
    }
    
    public Anagrafica getAnagrafica() {
        return this.anagrafica;
    }
    
    @Override
    public int compareTo(Autore a) {
        return this.anagrafica.compareTo(a.getAnagrafica());
    }
}
