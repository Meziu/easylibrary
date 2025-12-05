package it.unisa.diem.softeng.easylibrary.libri;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;

import java.util.HashMap;
import java.util.Map;
import it.unisa.diem.softeng.easylibrary.archivio.ArchivioConChiave;
import java.util.function.Consumer;

public class GestoreLibri extends Gestore<Libro> implements ArchivioConChiave<ISBN, Libro> {

    private final Map<ISBN, Libro> indiceISBN;

    public GestoreLibri() {
        super();
        
        indiceISBN = new HashMap<>();
    }
    
    @Override
    public void registra(Libro l) {
        //Verifica univocità ISBN
        Libro res = indiceISBN.putIfAbsent(l.getISBN(), l);
        // Se era già presente quel libro
        if (res != null) {
            throw new ValoreGiàPresenteException("TODO FARE MESSAGGIO BELLO");
        }
        
        super.registra(l);
    }
    
    @Override
    public void rimuovi(Libro l) {
        Libro res = indiceISBN.remove(l.getISBN());
        // Se non era presente l'utente
        if (res == null) {
            throw new ValoreNonPresenteException(); // TODO
        }
        
        super.rimuovi(l);
    }
    
    @Override
    public void modifica(Libro libro, Consumer<Libro> c) {
        Libro l = ottieni(libro.getISBN());
        if (l == null) {
            throw new ValoreNonPresenteException();
        }
        
        super.modifica(libro, c);
    }

    @Override
    public Libro ottieni(ISBN key) {
        return indiceISBN.get(key);
    }

    @Override
    public boolean contiene(ISBN key) {
        return indiceISBN.containsKey(key);
    }
    
}
