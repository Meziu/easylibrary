package it.unisa.diem.softeng.easylibrary.libri;

import it.unisa.diem.softeng.easylibrary.dati.Persona;


/**
 * @brief Classe che rappresenta un autore di libri presenti nel sistema della biglioteca.
 * La classe Autore estende la classe Persona e rappresenta un autore associato a uno o più libri.
 * 
 * @see Persona
 * @see Libro
*/
public class Autore extends Persona {

    /**
     * @brief Costruttore.
     * Costruisce un nuovo oggetto Autore a partire dalle
     * stringhe di caratteri che ne compongono il nome e il cognome.
     * 
     *  @param\[in] nome Stringa di caratteri del nome dell'autore.
     *  @param\[in] cognome Stringa di caratteri del cognome dell'autore.
     */
    public Autore(String nome, String cognome) {
        super(nome, cognome);
    }
}
