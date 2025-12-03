package it.unisa.diem.softeng.easylibrary.archivio;

import it.unisa.diem.softeng.easylibrary.interfacce.Filtro;
import java.util.ArrayList;
import java.util.List;

public abstract class Archivio<T> {

    List<T> collezione;

    public abstract void registra(T elemento);

    public abstract void rimuovi(T elemento);

    public Archivio() {
        collezione = new ArrayList<>();
    }

    public List<T> getCollezione() {
        return collezione;
    }

    public List<T> filtra(Filtro f) {
        List<T> listaFiltrata = new ArrayList<>();

        for (T el : collezione) {
            if (f.controlla(el)) {
                listaFiltrata.add(el);
            }
        }
        return listaFiltrata;
    }
}
