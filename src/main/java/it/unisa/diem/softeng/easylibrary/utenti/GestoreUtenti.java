package it.unisa.diem.softeng.easylibrary.utenti;

import it.unisa.diem.softeng.easylibrary.archivio.Archivio;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import it.unisa.diem.softeng.easylibrary.archivio.Filtro;

public class GestoreUtenti extends Archivio<Utente> implements ArchivioConChiave<Matricola, Utente> {

    private final Map<Matricola, Utente> indiceMatricole;
    private final OrdinatoreUtenti ord;

    public GestoreUtenti() {
        super();
        
        indiceMatricole = new HashMap<>();
        ord = new OrdinatoreUtenti();
    }

    @Override
    public void registra(Utente u) {
        Utente res = indiceMatricole.putIfAbsent(u.getMatricola(), u);

        // Se era già presente quell'utente
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        List<Utente> list = getCollezione();

        int idx = Collections.binarySearch(list, u, ord);
        list.add(idx, u);

    }

    @Override
    public void rimuovi(Utente u) {
        Utente res = indiceMatricole.remove(u.getMatricola());

        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }


        List<Utente> list = getCollezione();
        int idx = Collections.binarySearch(list, u, ord);

        // Se l'indice è fuori dalla lista o se l'elemento trovato dalla binarySearch non è quello giusto.
        if (idx == list.size() || list.get(idx).compareTo(u) != 0) {
            throw new ValoreNonPresenteException("TODO FARE MESSAGGIO BELLO");
        }

        list.remove(idx);
    }


    @Override
    public Utente ottieni(Matricola key) {
        return this.indiceMatricole.get(key);
    }

    @Override
    public boolean contiene(Matricola key) {
        return this.indiceMatricole.containsKey(key);
    }

    @Override
    public void riassegna(Matricola oldKey, Matricola newKey) {
        Utente u = this.indiceMatricole.remove(oldKey);
        
        if (u == null) {
            throw new ValoreNonPresenteException();
        }
        
        // Impostiamo solo la stringa della matricola (e non l'oggetto in se) per non modificare il riferimento nella lista dei prestiti.
        u.getMatricola().setMatricola(newKey.getMatricola());
        
        if (this.indiceMatricole.putIfAbsent(u.getMatricola(), u) != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        } 
    }
}
