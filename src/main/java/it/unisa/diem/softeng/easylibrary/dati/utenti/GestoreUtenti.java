package it.unisa.diem.softeng.easylibrary.dati.utenti;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreGi�PresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.Gestore;
import java.util.function.Consumer;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;


/**
 * @brief Gestisce gli utenti nel sistema della biblioteca.
 * 
 * La classe estende Gestore e implemanta ArchivioConChiave e si occupa di registrare,
 * rimuovere, modificare e cercare un utente nel sistema della biblioteca.
 * Gli utenti sono identificati univocamente dalla loro \ref Matricola.
 * 
 * @see Gestore
 * @see Indicizzabile
 * @see Utente
 * @see Matricola
*/
public class GestoreUtenti extends Gestore<Utente> implements Indicizzabile<Matricola, Utente> {

    private final Map<Matricola, Utente> indiceMatricole;

    /**
     * @brief Costruttore della classe GestoreUtenti.
     * 
     * Inizializza la struttura dati tramite il costruttore della superclasse  e la mappa per la matricola.
     */
    public GestoreUtenti() {
        super();
        
        indiceMatricole = new HashMap<>();
    }

    
    /**
     * @brief Registra un nuovo utente nel sistema.
     * 
     * Verifica che l'utente non sia già presente tramite la sua matricola.
     * Se non è presente, lo aggiunge alla mappa e alla lista.
     * 
     * @param\[in] u Utente da registrare
     * @throws ValoreGiàPresenteException Se un utente con la stessa matricola è già registrato
     */
    @Override
    public void registra(Utente u) {
        Utente res = indiceMatricole.putIfAbsent(u.getMatricola(), u);

        // Se era già presente quell'utente
        if (res != null) {
            throw new ValoreGi�PresenteException("TODO FARE MESSAGGIO BELLO");
        }

        super.registra(u);

    }

    /**
     * @brief Rimuove un utente dal sistema.
     * 
     * Verifica che l'utente sia presente tramite la sua matricola.
     * Rimuove l'utente sia dalla mappa per matricola che dalla lista.
     * 
     * @param\[in] u Utente da rimuovere
     * @throws ValoreNonPresenteException Se l'utente non è presente
     */
    @Override
    public void rimuovi(Utente u) {
        Utente res = indiceMatricole.remove(u.getMatricola());

        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        super.rimuovi(u);
    }
    
    
    /**
     * @brief Modifica le informazioni di un utente.
     * 
     * Recupera l'utente tramite matricola e applica le modifiche.
     * 
     * @param\[in] utente Utente da modificare
     * @param\[in] c Funzione che applica le modifiche all'utente
     * @throws ValoreNonPresenteException Se l'utente non è presente
     */
    @Override
    public void modifica(Utente utente, Consumer<Utente> c) {
        Utente u = ottieni(utente.getMatricola());
        if (u == null) {
            throw new ValoreNonPresenteException();
        }
        
        super.modifica(utente, c);
    }

    
    /**
     * @brief Restituisce l'utente associato ad una determinata matricola.
     * 
     * @param\[in] key Matricola del libro da ottenere
     * @return utente corrispondente alla matricola, o null se non presente
     */
    @Override
    public Utente ottieni(Matricola key) {
        return this.indiceMatricole.get(key);
    }

    /**
     * @brief Controlla se un utente con un determinata matricola è presente.
     * 
     * @param\[in] key Matricola da controllare
     * @return true se la matricola è presente, false altrimenti
     */
    @Override
    public boolean contiene(Matricola key) {
        return this.indiceMatricole.containsKey(key);
    }
}
