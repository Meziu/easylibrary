package it.unisa.diem.softeng.easylibrary;

import it.unisa.diem.softeng.easylibrary.dati.prestiti.NessunaCopiaDisponibileException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.LimitePrestitiSuperatoException;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.GestorePrestiti;
import it.unisa.diem.softeng.easylibrary.dati.utenti.GestoreUtenti;
import it.unisa.diem.softeng.easylibrary.dati.libri.GestoreLibri;
import it.unisa.diem.softeng.easylibrary.archivio.*;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;

import java.time.LocalDate;
import java.util.List;
import java.io.*;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;


/**
 * @brief Rappresenta una biblioteca che gestisce utenti, libri e prestiti.
 * 
 * La classe fornisce metodi per registrare prestiti e restituzioni, 
 * filtrare i prestiti attivi e salvare/caricare lo stato della biblioteca.
 * Implementa Serializable per consentire la persistenza su file.
 * 
 * @see Archiviabile
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
     * 
     * @post Gli archivi utenti, libri e prestiti sono pronti all'uso
     */
    public Biblioteca() {
        this.archivioUtenti = new GestoreUtenti();
        this.archivioLibri = new GestoreLibri();
        this.archivioPrestiti = new GestorePrestiti(this.archivioUtenti, this.archivioLibri);
    }

    public Indicizzabile<Matricola, Utente> getArchivioUtenti() {
        return archivioUtenti;
    }

    public Indicizzabile<ISBN, Libro> getArchivioLibri() {
        return archivioLibri;
    }

    public Archiviabile<Prestito> getArchivioPrestiti() {
        return archivioPrestiti;
    }
    
    /**
     * @brief Salva lo stato della biblioteca su file.
     * 
     * @param filename Nome del file in cui salvare lo stato
     * 
     * @post Lo stato della biblioteca viene scritto nel file specificato
     */
    public void salvaFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            out.writeObject(this);
        } catch (IOException e) {
            // TODO: gestire questa eccezione correttamente
            e.printStackTrace();
        }
    }

    /**
     * @brief Carica lo stato della biblioteca da file.
     * 
     * @param filename Nome del file da cui leggere lo stato
     * 
     * @pre Il file deve esistere e contenere un oggetto Biblioteca serializzato
     * @return Oggetto Biblioteca caricato, o null in caso di errore
     */
    public static Biblioteca caricaFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(filename))) {
            return (Biblioteca) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
