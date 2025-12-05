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

    private final ArchivioConChiave<Matricola, Utente> archivioUtenti;
    private final ArchivioConChiave<ISBN, Libro> archivioLibri;
    private final Archivio<Prestito> archivioPrestiti;

    public Biblioteca() {
        this.archivioUtenti = new GestoreUtenti();
        this.archivioLibri = new GestoreLibri();
        this.archivioPrestiti = new GestorePrestiti();
    }

    public List<Prestito> getPrestitiAttivi() {
        return archivioPrestiti.filtra((Prestito p) -> p.getStato() == StatoPrestito.ATTIVO);
    }

    public void registraPrestito(String matricola, String isbn, LocalDate scadenzaPrestito) {
        Utente u = archivioUtenti.ottieni(new Matricola(matricola));
        Libro l = archivioLibri.ottieni(new ISBN(isbn));

        Prestito p = new Prestito(u.getMatricola(), l.getISBN(), StatoPrestito.ATTIVO, scadenzaPrestito);

        archivioPrestiti.registra(p);
        u.registraPrestito(p);
    }

    public void registraRestituzione(Prestito p) {
        archivioPrestiti.rimuovi(p);
        archivioUtenti.ottieni(p.getMatricola()).rimuoviPrestito(p);
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
