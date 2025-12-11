package it.unisa.diem.softeng.easylibrary.dati.libri;

import it.unisa.diem.softeng.easylibrary.archivio.Gestore;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGi�PresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;

/**
 * @brief Gestisce i libri presenti nella biblioteca.
 * 
 * La classe estende Gestore e implemanta ArchivioConChiave e si occupa di registrare,
 * rimuovere, modificare e cercare un libro nella biblioteca.
 * I libri sono identificati univocamente dal loro \ref ISBN.
 * 
 * @see Gestore
 * @see Indicizzabile
 * @see Libro
 * @see ISBN
*/
public class GestoreLibri extends Gestore<Libro> implements Indicizzabile<ISBN, Libro> {

    private final Map<ISBN, Libro> indiceISBN;

    /**
     * @brief Costruttore della classe GestoreLibri.
     * 
     * Inizializza la struttura dati tramite il costruttore della superclasse  e la mappa per l'indice ISBN.
     */
    public GestoreLibri() {
        super();
        
        indiceISBN = new HashMap<>();
    }
    
    
     /**
     * @brief Registra un nuovo libro nel sistema.
     * 
     * Verifica che il libro non sia già presente tramite il suo ISBN.
     * Se non è presente, lo aggiunge alla mappa e alla lista.
     * 
     * @param\[in] l Libro da registrare
     * @throws ValoreGiàPresenteException Se un libro con lo stesso ISBN è già registrato
     */
    @Override
    public void registra(Libro l) {
        //Verifica univocità ISBN
        Libro res = indiceISBN.putIfAbsent(l.getISBN(), l);
        // Se era già presente quel libro
        if (res != null) {
            throw new ValoreGi�PresenteException("TODO MESSAGGIO");
        }
        
        super.registra(l);
    }
    
    
    /**
     * @brief Rimuove un libro dal sistema.
     * 
     * Verifica che il libro sia presente tramite il suo ISBN.
     * Rimuove il libro sia dalla mappa per ISBN che dalla lista.
     * 
     * @param\[in] l Libro da rimuovere
     * @throws ValoreNonPresenteException Se il libro non è presente
     */
    @Override
    public void rimuovi(Libro l) {
        Libro res = indiceISBN.remove(l.getISBN());
        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException(); // TODO
        }
        
        super.rimuovi(l);
    }
    
    
    /**
     * @brief Modifica le informazioni di un libro.
     * 
     * Recupera il libro tramite ISBN e applica le modifiche.
     * 
     * @param\[in] libro Libro da modificare
     * @param\[in] c Funzione che applica le modifiche al libro
     * @throws ValoreNonPresenteException Se il libro non è presente
     */
    @Override
    public void modifica(Libro libro, Consumer<Libro> c) {
        Libro l = ottieni(libro.getISBN());
        if (l == null) {
            throw new ValoreNonPresenteException();
        }
        
        super.modifica(libro, c);
    }

    /**
     * @brief Restituisce il libro associato ad un determinato ISBN.
     * 
     * @param\[in] key ISBN del libro da ottenere
     * @return Libro corrispondente all'ISBN, o null se non presente
     */
    @Override
    public Libro ottieni(ISBN key) {
        return indiceISBN.get(key);
    }

    
    /**
     * @brief Controlla se un libro con un determinato ISBN è presente.
     * 
     * @param\[in] key ISBN da controllare
     * @return true se il libro è presente, false altrimenti
     */
    @Override
    public boolean contiene(ISBN key) {
        return indiceISBN.containsKey(key);
    }
    
}
