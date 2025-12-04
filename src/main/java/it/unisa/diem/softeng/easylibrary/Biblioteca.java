package it.unisa.diem.softeng.easylibrary;

import it.unisa.diem.softeng.easylibrary.prestiti.GestorePrestiti;
import it.unisa.diem.softeng.easylibrary.utenti.GestoreUtenti;
import it.unisa.diem.softeng.easylibrary.libri.GestoreLibri;
import it.unisa.diem.softeng.easylibrary.archivio.*;
import it.unisa.diem.softeng.easylibrary.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.libri.Libro;
import it.unisa.diem.softeng.easylibrary.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.prestiti.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.utenti.Utente;


import java.time.LocalDate;
import java.util.List;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;

public class Biblioteca {
    private class Holder<T> {
        public T t;
        
        public Holder(T t) {
            this.t = t;
        }
        
        public <S extends Archivio<Utente> & ArchivioConChiave<Matricola, Utente>> S getArchivioUtenti() {
            return (S)t;
        }
        
        public <S extends Archivio<Libro> & ArchivioConChiave<ISBN, Libro>> S getArchivioLibri() {
            return (S)t;
        }
    }
    
    private final Holder<?> utenti;
    private final Holder<?> libri;
    private final Archivio<Prestito> prestiti;

    public Biblioteca() {
        this.utenti = new Holder<>(new GestoreUtenti());
        this.libri = new Holder<>(new GestoreLibri());
        this.prestiti = new GestorePrestiti();
    }

    public void registraPrestito(ISBN isbn, Matricola matricola, LocalDate scadenzaPrestito) {
        Prestito p = new Prestito(matricola, isbn, StatoPrestito.ATTIVO, scadenzaPrestito);
        
        prestiti.registra(p);
        utenti.getArchivioUtenti().ottieni(matricola).registraPrestito(p);
    }
    
    public List<Prestito> getPrestitiAttivi() {
        return prestiti.filtra((Prestito p) -> p.getStato() == StatoPrestito.ATTIVO);
    }

    public void registraRestituzione(Prestito p) {
        prestiti.rimuovi(p);
    }
    
    public void salvaFile(String filename) {
        
    }
    
    public static void caricaFile(String filename) {
        
    }
}