package it.unisa.diem.softeng.easylibrary;

import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.dati.Libro;
import it.unisa.diem.softeng.easylibrary.dati.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.Utente;

import java.time.LocalDate;

public class Biblioteca {
    
    private Archivio<Libro> gestoreLibri;
    private Archivio<Utente> gestoreUtenti;
    private Archivio<Prestito> gestorePrestiti;

    public Biblioteca(Archivio<Libro> gestoreLibri, Archivio<Utente> gestoreUtenti, Archivio<Prestito> gestorePrestiti) {
        this.gestoreLibri = gestoreLibri;
        this.gestoreUtenti = gestoreUtenti;
        this.gestorePrestiti = gestorePrestiti;
    }

    public void registraPrestito(Libro l, Utente u, LocalDate scadenzaPrestito) {
        Prestito p = new Prestito(u.getMatricola(), l.getISBN(), StatoPrestito.ATTIVO, scadenzaPrestito);
        gestorePrestiti.registra(p);
        gestoreUtenti.cerca(u.getMatricola()).addPrestito(p);
    }

    public void rimuovi(Prestito p) {
        gestorePrestiti.getStatoPrestito(p);
    }

    public void registraRestituzione(Libro l, Utente u) {
        gestorePrestiti.cerca(new Prestito(l, u, StatoPrestito.ATTIVO, scadenzaPrestito)).setStatoPrestito(StatoPrestito.RESTITUITO);

    }
}
