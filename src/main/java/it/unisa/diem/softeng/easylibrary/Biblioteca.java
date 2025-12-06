package it.unisa.diem.softeng.easylibrary;

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
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import java.io.*;


/**
 * @brief Rappresenta una biblioteca che gestisce utenti, libri e prestiti.
 * 
 * La classe fornisce metodi per registrare prestiti e restituzioni, 
 * filtrare i prestiti attivi e salvare/caricare lo stato della biblioteca.
 * Implementa Serializable per consentire la persistenza su file.
 * 
 * @see Archivio
 * @see Utente
 * @see Libro
 * @see Prestito
 */
public class Biblioteca implements Serializable {

    private final ArchivioConChiave<Matricola, Utente> archivioUtenti;
    private final ArchivioConChiave<ISBN, Libro> archivioLibri;
    private final Archivio<Prestito> archivioPrestiti;

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
        this.archivioPrestiti = new GestorePrestiti();
    }

    /**
     * @brief Restituisce la lista dei prestiti attivi.
     * 
     * Applica un filtro sugli elementi dell'archivio dei prestiti, selezionando solo
     * quelli con stato ATTIVO.
     * 
     * @see StatoPrestito
     * 
     * @post La lista restituita contiene solo prestiti attivi, senza modificare l'archivio
     * @return Lista dei prestiti attivi
     */
    public List<Prestito> getPrestitiAttivi() {
        return archivioPrestiti.filtra((Prestito p) -> p.getStato() == StatoPrestito.ATTIVO);
    }

    /**
     * @brief Registra un nuovo prestito nella biblioteca.
     * 
     * @param matricola Matricola dell'utente che prende in prestito il libro
     * @param isbn Codice ISBN del libro da prestare
     * @param scadenzaPrestito Data di scadenza del prestito
     * 
     * @pre L'utente con la matricola fornita deve essere presente nell'archivio
     * @pre Il libro con l'ISBN fornito deve essere presente nell'archivio
     * 
     * @post Il prestito viene aggiunto all'archivio dei prestiti.
     * @post Il prestito viene aggiunto alla lista dei prestiti attivi dell'utente.
     * @post Il numero di copie disponibili del libro viene decrementato di 1.
     */
    public void registraPrestito(String matricola, String isbn, LocalDate scadenzaPrestito) {
        Utente u = archivioUtenti.ottieni(new Matricola(matricola));
        Libro l = archivioLibri.ottieni(new ISBN(isbn));

        //LIMITE PRESTITI
        if (u.getPrestitiAttivi().size() >= 3)
            throw new LimitePrestitiSuperatoException("TODO");
        
        //NESSUNA COPIA DISPONIBILE
        if (l.getCopieDisponibili() <= 0)
            throw new NessunaCopiaDisponibileException("TODO");
        
        Prestito p = new Prestito(u.getMatricola(), l.getISBN(), StatoPrestito.ATTIVO, scadenzaPrestito);

        archivioPrestiti.registra(p);
        u.registraPrestito(p);
        l.setCopieDisponibili(l.getCopieDisponibili() - 1);
    }

    /**
     * @brief Registra la restituzione di un prestito.
     * 
     * Aggiorna lo stato del prestito nell'archivio e lo rimuove dalla lista
     * dei prestiti attivi dell'utente.
     * 
     * @pre Il prestito deve esistere nell'archivio dei prestiti.
     * @pre L'utente associato al prestito deve essere presente nell'archivio utenti.
     * @pre Il libro associato al prestito deve esistere nell'archivio libri.
     * 
     * @post Il prestito viene rimosso dall'archivio dei prestiti.
     * @post Il prestito viene rimosso dalla lista dei prestiti attivi dell'utente.
     * @post Il numero di copie disponibili del libro viene incrementato di 1.
     * @param p Prestito da restituire
     */
    public void registraRestituzione(Prestito p) {
        archivioPrestiti.rimuovi(p);
        archivioUtenti.ottieni(p.getMatricola()).rimuoviPrestito(p);
        Libro l = archivioLibri.ottieni(p.getISBN());
        l.setCopieDisponibili(l.getCopieDisponibili() + 1);
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
            // TODO: gestire questa eccezione correttamente
            e.printStackTrace();
            return null;
        }
    }
}
