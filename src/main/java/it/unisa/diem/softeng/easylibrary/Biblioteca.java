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
import java.io.*;

public class Biblioteca implements Serializable {

    // In Java non è possibile mantenere un riferimento ad un oggetto solo attraverso le classi astratte che eredita e le interfacce che implementa.
    // Per far fronte a ciò, senza perdere di flessibilità, imponiamo questi vincoli tramite 2 classi private con la parametrizzazione specifica.
    private class ArchivioUtentiHolder<A extends Archivio<Utente> & ArchivioConChiave<Matricola, Utente>> implements Serializable {

        public Object o;

        public ArchivioUtentiHolder(A t) {
            this.o = t;
        }

        public <A extends Archivio<Utente> & ArchivioConChiave<Matricola, Utente>> A getArchivio() {
            return (A) o;
        }
    }

    private class ArchivioLibriHolder<A extends Archivio<Libro> & ArchivioConChiave<ISBN, Libro>> implements Serializable {

        public Object o;

        public ArchivioLibriHolder(A t) {
            this.o = t;
        }

        public <A extends Archivio<Libro> & ArchivioConChiave<ISBN, Libro>> A getArchivio() {
            return (A) o;
        }
    }

    private final ArchivioUtentiHolder<?> utenti;
    private final ArchivioLibriHolder<?> libri;
    private final Archivio<Prestito> prestiti;

    public Biblioteca() {
        this.utenti = new ArchivioUtentiHolder<>(new GestoreUtenti());
        this.libri = new ArchivioLibriHolder<>(new GestoreLibri());
        this.prestiti = new GestorePrestiti();
    }
    
    public List<Prestito> getPrestitiAttivi() {
        return prestiti.filtra((Prestito p) -> p.getStato() == StatoPrestito.ATTIVO);
    }

    public void registraPrestito(String matricola, String isbn, LocalDate scadenzaPrestito) {
        Utente u = utenti.getArchivio().ottieni(new Matricola(matricola));
        Libro l = libri.getArchivio().ottieni(new ISBN(isbn));
        
        Prestito p = new Prestito(u.getMatricola(), l.getISBN(), StatoPrestito.ATTIVO, scadenzaPrestito);
        
        prestiti.registra(p);
        u.registraPrestito(p);
    }
    
    public void registraRestituzione(Prestito p) {
        prestiti.rimuovi(p);
        utenti.getArchivio().ottieni(p.getMatricola()).rimuoviPrestito(p);
    }

    public void salvaFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream(filename))) {
            out.writeObject(this);
        } catch (IOException e) {
            // TODO: gestire questa eccezione correttamente
            e.printStackTrace();
        }
    }

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
