package it.unisa.diem.softeng.easylibrary;

import it.unisa.diem.softeng.easylibrary.dati.prestiti.GestorePrestiti;
import it.unisa.diem.softeng.easylibrary.dati.utenti.GestoreUtenti;
import it.unisa.diem.softeng.easylibrary.dati.libri.GestoreLibri;
import it.unisa.diem.softeng.easylibrary.archivio.*;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;

import java.io.*;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;

/**
 * @brief Rappresenta una biblioteca che gestisce utenti, libri e prestiti.
 *
 * La classe fornisce metodi per registrare prestiti e restituzioni, filtrare i
 * prestiti attivi e salvare/caricare lo stato della biblioteca. Implementa
 * Serializable per consentire la persistenza su file.
 *
 * @see GestorePrestiti
 * @see GestoreUtenti
 * @see GestoreLibri
 * @see Utente
 * @see Libro
 * @see Prestito
 */
public class Biblioteca implements Serializable {

    private final Indicizzabile<Matricola, Utente> archivioUtenti;
    private final Indicizzabile<ISBN, Libro> archivioLibri;
    private final Archiviabile<Prestito> archivioPrestiti;

    /**
     * @brief Costruttore.
     *
     * Inizializza gli archivi con le rispettive classi gestore.
     * @post archivioUtenti != null
     * @post archivioLibri != null
     * @post archivioPrestiti != null
     */
    public Biblioteca() {
        this.archivioUtenti = new GestoreUtenti();
        this.archivioLibri = new GestoreLibri();
        this.archivioPrestiti = new GestorePrestiti(this.archivioUtenti, this.archivioLibri);
    }

    /**
     * @brief Getter dell'archivio utenti.
     *
     * @return L'archivio indicizzabile degli utenti registrati nella
     * biblioteca.
     */
    public Indicizzabile<Matricola, Utente> getArchivioUtenti() {
        return archivioUtenti;
    }

    /**
     * @brief Getter dell'archivio libri.
     *
     * @return L'archivio indicizzabile dei libri registrati nella biblioteca.
     */
    public Indicizzabile<ISBN, Libro> getArchivioLibri() {
        return archivioLibri;
    }

    /**
     * @brief Getter dell'archivio prestiti.
     *
     * @return L'archivio indicizzabile dei prestiti registrati nella
     * biblioteca.
     */
    public Archiviabile<Prestito> getArchivioPrestiti() {
        return archivioPrestiti;
    }

    /**
     * @brief Salva lo stato della biblioteca su file.
     *
     * @param\[in] filename Nome del file in cui salvare lo stato
     *
     * @post Lo stato della biblioteca viene scritto nel file specificato
     *
     * @throws IOException nel caso in cui il percorso specificato non sia
     * accessibile.
     */
    public void salvaFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            out.writeObject(this);
        }
    }

    /**
     * @brief Carica lo stato della biblioteca da file.
     *
     * @param\[in] filename Nome del file da cui leggere lo stato
     *
     * @pre Il file deve esistere e contenere un oggetto Biblioteca
     * serializzato.
     * @return Oggetto Biblioteca caricato, o null in caso di errore nella
     * deserializzazione delle classi.
     *
     * @throws IOException nel caso in cui il file non esista o non sia
     * accessibile.
     */
    public static Biblioteca caricaFile(String filename) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(filename)))) {
            return (Biblioteca) in.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
