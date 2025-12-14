package it.unisa.diem.softeng.easylibrary.dati.prestiti;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.Gestore;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.util.Collections;

/**
 * @brief Gestisce i prestiti dei libri.
 * 
 * La classe GestorePrestiti estende Gestore e si occupa di registrare le restituzioni dei prestiti.
 * 
 * @see Gestore
 * @see Prestito
*/
public class GestorePrestiti extends Gestore<Prestito> {
    private final Indicizzabile<Matricola, Utente> archivioUtenti;
    private final Indicizzabile<ISBN, Libro> archivioLibri;

    /**
     * @brief Costruttore della classe GestorePrestiti.
     * 
     * Inizializza la struttura dati tramite il costruttore della superclasse, prendendo per riferimento
     * gli archivi da utilizzare come appoggio per i prestiti da registrare.
     * 
     * @param\[in] archivioUtenti Riferimento ad un archivio indicizzabile di utenti.
     * @param\[in] archivioLibri Riferimento ad un archivio indicizzabile di libri.
     */
    public GestorePrestiti(Indicizzabile<Matricola, Utente> archivioUtenti, Indicizzabile<ISBN, Libro> archivioLibri) {
        super();
        
        this.archivioUtenti = archivioUtenti;
        this.archivioLibri = archivioLibri;
    }

    /**
     * @brief Registra un nuovo prestito nell'archivio.
     * 
     * Aggiunge un nuovo prestito all'archivio, controllando che i suoi dati siano coerenti
     * con l'archivio di utenti e libri specificati nel costruttore.
     * 
     * @param\[in] p Il prestito da registrare.
     * 
     * @throws ValoreNonPresenteException Se ISBN o Matricola specificati nel prestito
     * non sono presenti negli archivi di utenti e libri.
     * 
     * @pre L'utente con la matricola fornita deve essere presente nell'archivio
     * @pre Il libro con l'ISBN fornito deve essere presente nell'archivio
     * 
     * @post Il prestito viene aggiunto all'archivio dei prestiti.
     * @post Il prestito viene aggiunto alla lista dei prestiti attivi dell'utente.
     * @post Il numero di copie disponibili del libro viene decrementato di 1.
    */
    @Override
    public void registra(Prestito p) {
        // Se matricola o isbn non sono presenti negli archivi di libri e utenti
        if (!archivioUtenti.contiene(p.getMatricola())) {
            throw new ValoreNonPresenteException("Matricola \"" + p.getMatricola() + "\" non presente nell'archivio degli utenti");
        }
        
        if (!archivioLibri.contiene(p.getISBN())) {
            throw new ValoreNonPresenteException("ISBN \"" + p.getISBN() + "\" non presente nell'archivio dei libri");
        }
        
        Libro l = archivioLibri.ottieni(p.getISBN());
        
        // Se il libro non possiede alcuna copia disponibile
        if (l.getCopieDisponibili() <= 0) {
            throw new NessunaCopiaDisponibileException("Nessuna copia disponibile per il libro \"" + p.getISBN() + "\"");
        }
        
        archivioUtenti.ottieni(p.getMatricola()).registraPrestito(p);
        
        super.registra(p);
        l.setCopieDisponibili(l.getCopieDisponibili() - 1);
    }
    
    

    /**
     * @brief Segna un prestito come restituito.
     * 
     * Cerca il prestito nella lista e ne aggiorna lo stato in "RESTITUITO".
     * Il prestito viene rimosso dalla lista di prestiti dell'Utente e la copia
     * segnata come utilizzabile nel Libro.
     * 
     * @param\[in] p Il prestito da rimuovere (restituire).
     * 
     * @throws ValoreNonPresenteException Se il prestito non è presente
     * nell'archivio.
     * 
     * @pre Il prestito deve esistere nell'archivio dei prestiti.
     * @pre L'utente associato al prestito deve essere presente nell'archivio utenti.
     * @pre Il libro associato al prestito deve esistere nell'archivio libri.
     * 
     * @post Il prestito viene rimosso dall'archivio dei prestiti.
     * @post Il prestito viene rimosso dalla lista dei prestiti attivi dell'utente.
     * @post Il numero di copie disponibili del libro viene incrementato di 1.
    */
    @Override
    public void rimuovi(Prestito p) {
        int idx = Collections.binarySearch(this.getLista(), p);

        // Se l'indice è fuori dalla lista (cioè non è presente l'elemento):
        if (idx < 0 || idx >= this.getLista().size()) {
            throw new ValoreNonPresenteException();
        }

        this.getLista().get(idx).setStato(StatoPrestito.RESTITUITO);
        
        // Restore strutture di utente e libro per ritornare allo stato prima del prestito.
        archivioUtenti.ottieni(p.getMatricola()).rimuoviPrestito(p);
        Libro l = archivioLibri.ottieni(p.getISBN());
        l.setCopieDisponibili(l.getCopieDisponibili() + 1);
    }
    
}
