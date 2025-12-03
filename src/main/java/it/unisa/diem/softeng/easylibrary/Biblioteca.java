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
import java.util.List;

public class Biblioteca {
    private class Holder<T> {
        public T t;
        
        public Holder(T t) {
            this.t = t;
        }
        
        public <S extends Archivio<Utente> & CollezioneConChiave<Matricola, Utente>> S getArchivioUtenti() {
            return (S)t;
        }
        
        public <S extends Archivio<Libro> & CollezioneConChiave<ISBN, Libro>> S getArchivioLibri() {
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
        utenti.getArchivioUtenti().ottieni(matricola).addPrestito(p);
    }

    public void registraRestituzione(Prestito p) {
        prestiti.rimuovi(p);
    }
    
    public void salvaFile(String filename) {
        
    }
    
    public static void caricaFile(String filename) {
        
    } 
    
    
    
    
    
    
    
    
    public List<Prestito> getPrestitiAttivi() {
        return gestorePrestiti.filtra(new Filtro<Prestito>() {
            
            @Override
            public boolean controlla(Prestito p) {
                return p.getStatoPrestito() == StatoPrestito.ATTIVO;
            }
        });
    }
    
}