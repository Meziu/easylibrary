package it.unisa.diem.softeng.easylibrary.utenti;

import it.unisa.diem.softeng.easylibrary.dati.Persona;
import it.unisa.diem.softeng.easylibrary.prestiti.Prestito;
import java.util.ArrayList;
import java.util.Collections;
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
    
    public List<Prestito> getPrestitiAttivi() {
        return Collections.unmodifiableList(prestitiAttivi);
    }
    
    public void setEmail(IndirizzoEmail email) {
        Utente.this.email = email;
    }

    public void registraPrestito(Prestito p) {
        Utente.this.prestitiAttivi.add(p);
    }

    public void rimuoviPrestito(Prestito p) {
        Utente.this.prestitiAttivi.remove(p);
    }

    @Override
    public int compareTo(Persona p) {
        int c = super.compareTo(p);

        if (c != 0 || !(p instanceof Utente)) {
            return c;
        }

        Utente u = (Utente) p;
        return this.matricola.compareTo(u.matricola);
    }
}
