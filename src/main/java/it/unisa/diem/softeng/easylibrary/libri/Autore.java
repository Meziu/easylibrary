package it.unisa.diem.softeng.easylibrary.libri;

import it.unisa.diem.softeng.easylibrary.dati.Persona;


/**@file Autore.java
 * @brief Classe che rappresenta un autore di libri presenti nel sistema della biglioteca.
 * La classe Autore estende la classe {@link Persona} e rappresenta un autore associato a uno o più libri.
 * 
 * @see Persona
*/
public class Autore extends Persona {

    /**
     * @brief Costruttore della classe Autore.
     * 
     * @param nome Nome dell'autore.
     * @param cognome Cognome dell'autore.
     */
    public Autore(String nome, String cognome) {
        super(nome, cognome);
    }
}
