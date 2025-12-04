package it.unisa.diem.softeng.easylibrary.archivio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Archivio<E> implements Serializable {

    private final List<E> collezione;
    
    public Archivio() {
        collezione = new ArrayList<>();
    }

    public abstract void registra(E elemento);
    
    public abstract void rimuovi(E elemento);
    
    public List<E> getCollezione() {
        // Lista read-only.
        return Collections.unmodifiableList(collezione);
    }

    public List<E> filtra(Filtro<E> f) {
        List<E> listaFiltrata = new ArrayList<>();

        for (E el : collezione) {
            if (f.controlla(el)) {
                listaFiltrata.add(el);
            }
        }
        return listaFiltrata;
    }
}
