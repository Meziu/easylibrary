package it.unisa.diem.softeng.easylibrary.dati.libri;

import it.unisa.diem.softeng.easylibrary.dati.Anagrafica;
import java.io.Serializable;
import java.util.Objects;

/**
 * @brief Classe che rappresenta un autore di libri presenti nel sistema della
 * biglioteca. La classe Autore contiene il riferimento ad un oggetto di classe
 * Anagrafica e rappresenta un autore associato a uno o più libri.
 *
 * @see Anagrafica
 * @see Libro
 */
public class Autore implements Serializable {

    private Anagrafica anagrafica;

    /**
     * @brief Costruttore. Costruisce un nuovo oggetto Autore a partire dalle
     * stringhe di caratteri che ne compongono il nome e il cognome.
     *
     * @param\[in] nome Stringa di caratteri del nome dell'autore.
     * @param\[in] cognome Stringa di caratteri del cognome dell'autore.
     */
    public Autore(String nome, String cognome) {
        this.anagrafica = new Anagrafica(nome, cognome);
    }

    /**
     * @brief Restituisce l'Anagrafica associata all'Utente.
     *
     * @return l'Anagrafica associata all'Utente.
     */
    public Anagrafica getAnagrafica() {
        return this.anagrafica;
    }

    /**
     *
     * @brief Uguaglianza con un'altro Autore.
     *
     * @return true se i due Autore hanno nome e cognome uguale (per
     * String.equals), false altrimenti.
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
        final Autore other = (Autore) obj;
        if (!Objects.equals(this.anagrafica, other.anagrafica)) {
            return false;
        }

        return true;
    }
}
