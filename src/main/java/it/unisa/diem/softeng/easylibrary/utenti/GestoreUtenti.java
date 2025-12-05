package it.unisa.diem.softeng.easylibrary.utenti;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import it.unisa.diem.softeng.easylibrary.libri.Gestore;
import java.util.function.Consumer;

public class GestoreUtenti extends Gestore<Utente> implements ArchivioConChiave<Matricola, Utente> {

    private final Map<Matricola, Utente> indiceMatricole;

    public GestoreUtenti() {
        super();
        
        indiceMatricole = new HashMap<>();
    }

    @Override
    public void registra(Utente u) {
        Utente res = indiceMatricole.putIfAbsent(u.getMatricola(), u);

        // Se era già presente quell'utente
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        super.registra(u);

    }

    @Override
    public void rimuovi(Utente u) {
        Utente res = indiceMatricole.remove(u.getMatricola());

        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        super.rimuovi(u);
    }
    
    @Override
    public void modifica(Utente utente, Consumer<Utente> c) {
        Utente u = ottieni(utente.getMatricola());
        if (u == null) {
            throw new ValoreNonPresenteException();
        }
        
        super.modifica(utente, c);
    }

    @Override
    public Utente ottieni(Matricola key) {
        return this.indiceMatricole.get(key);
    }

    @Override
    public boolean contiene(Matricola key) {
        return this.indiceMatricole.containsKey(key);
    }
}
