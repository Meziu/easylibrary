package it.unisa.diem.softeng.easylibrary.utenti;

import it.unisa.diem.softeng.easylibrary.dati.Persona;
import it.unisa.diem.softeng.easylibrary.prestiti.Prestito;
import java.util.ArrayList;
import java.util.List;

public class Utente extends Persona {

    private final Matricola matricola;
    private IndirizzoEmail email;
    private final List<Prestito> prestitiAttivi;

    public Utente(String nome, String cognome, Matricola matricola, IndirizzoEmail email) {
        super(nome, cognome);
        this.matricola = matricola;
        this.email = email;
        this.prestitiAttivi = new ArrayList<>();
    }

    public Matricola getMatricola() {
        return matricola;
    }

    public IndirizzoEmail getEmail() {
        return email;
    }
    
    public void setEmail(IndirizzoEmail email) {
        this.email = email;
    }

    public List<Prestito> getPrestitiAttivi() {
        return prestitiAttivi;
    }

    public void registraPrestito(Prestito p) {
        prestitiAttivi.add(p);
    }

    public void rimuoviPrestito(Prestito p) {
        prestitiAttivi.remove(p);
    }

    @Override
    public int compareTo(Persona p) {
        // Comparazione solo per matricola

        if (!(p instanceof Utente)) {
            return 1; // Utente sempre dopo Persona
        }

        Utente u = (Utente) p;
        return this.matricola.compareTo(u.matricola);
    }
}
