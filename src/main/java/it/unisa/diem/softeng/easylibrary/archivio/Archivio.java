/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.archivio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @brief
 */
public interface Archivio<E> extends Serializable {

    List<E> getLista();

    void registra(E elemento);

    void rimuovi(E elemento);
    
    void modifica(E elemento, Consumer<E> c);
    
    default List<E> filtra(Filtro<E> f) {
        List<E> listaFiltrata = new ArrayList<>();

        for (E el : getLista()) {
            if (f.controlla(el)) {
                listaFiltrata.add(el);
            }
        }
        return listaFiltrata;
    }
    
}
