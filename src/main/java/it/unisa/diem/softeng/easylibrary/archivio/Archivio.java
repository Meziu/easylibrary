package it.unisa.diem.softeng.easylibrary.archivio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Archivio<E> implements Serializable {

    protected final List<E> lista;
    
    public Archivio() {
        lista = new ArrayList<>();
    }

    public abstract void registra(E elemento);
    
    public abstract void rimuovi(E elemento);
    
    public List<E> getLista() {
        // Lista read-only.
        return Collections.unmodifiableList(lista);
    }

    public List<E> filtra(Filtro<E> f) {
        List<E> listaFiltrata = new ArrayList<>();

        for (E el : lista) {
            if (f.controlla(el)) {
                listaFiltrata.add(el);
            }
        }
        return listaFiltrata;
    }
}
