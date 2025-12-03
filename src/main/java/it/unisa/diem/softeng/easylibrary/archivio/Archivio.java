package it.unisa.diem.softeng.easylibrary.archivio;

import it.unisa.diem.softeng.easylibrary.interfacce.Filtro;
import java.util.ArrayList;
import java.util.List;

public abstract class Archivio<E> {

    List<E> collezione;

    public abstract void registra(E elemento);

    public abstract void rimuovi(E elemento);

    public Archivio() {
        collezione = new ArrayList<>();
    }

    public List<E> getCollezione() {
        return collezione;
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
