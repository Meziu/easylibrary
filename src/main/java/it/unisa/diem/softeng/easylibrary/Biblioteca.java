package it.unisa.diem.softeng.easylibrary;

import it.unisa.diem.softeng.easylibrary.archivio.*;
import it.unisa.diem.softeng.easylibrary.dati.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.Libro;
import it.unisa.diem.softeng.easylibrary.dati.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.Utente;
import it.unisa.diem.softeng.easylibrary.interfacce.CollezioneConChiave;

import java.time.LocalDate;

public class Biblioteca {
    private class HolderArchivioUtenti<T extends Archivio<Utente> & CollezioneConChiave<Matricola, Utente>> {
        public T archivio;
        
        public HolderArchivioUtenti(T archivio) {
            this.archivio = archivio;
        }
    }
    private class HolderArchivioLibri<T extends Archivio<Libro> & CollezioneConChiave<ISBN, Libro>> {
        public T archivio;
        
        public HolderArchivioLibri(T archivio) {
            this.archivio = archivio;
        }
    }
    private class HolderArchivioPrestiti<T extends Archivio<Prestito>> {
        public T archivio;
        
        public HolderArchivioPrestiti(T archivio) {
            this.archivio = archivio;
        }
    }
    
    private final HolderArchivioUtenti utenti;
    private final HolderArchivioLibri libri;
    private final HolderArchivioPrestiti prestiti;

    public Biblioteca() {
        this.utenti = new HolderArchivioUtenti(new GestoreUtenti());
        this.libri = new HolderArchivioLibri(new GestoreLibri());
        this.prestiti = new HolderArchivioPrestiti(new GestorePrestiti());
    }
    
    public Archivio<Utente> getArchivioUtenti() {
        return utenti.archivio;
    }
    
    public Archivio<Utente> getArchivioLibri() {
        return libri.archivio;
    }
    
    public Archivio<Utente> getArchivioPrestiti() {
        return prestiti.archivio;
    }

    public void registraPrestito(ISBN isbn, Matricola matricola, LocalDate scadenzaPrestito) {
        Prestito p = new Prestito(matricola, isbn, StatoPrestito.ATTIVO, scadenzaPrestito);
        
        prestiti.archivio.registra(p);
        utenti.archivio.ottieni(matricola).addPrestito(p);
    }

    public void registraRestituzione(Libro l, Utente u) {
        archivioPrestiti.cerca(new Prestito(l, u, StatoPrestito.ATTIVO, scadenzaPrestito)).setStatoPrestito(StatoPrestito.RESTITUITO);
    }
    
    public void salvaFile(String filename) {
        
    }
    
    public static void caricaFile(String filename) {
        
    }
}
