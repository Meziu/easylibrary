/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.archivio;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;

/**
 *
 * @brief Gestore generico di archivio.
 * 
 * @param <E> Classe degli elementi archiviati.
 */
public abstract class Gestore<E extends Comparable<? super E>> implements Archiviabile<E> {
    
    protected final List<E> lista;

    public Gestore() {
        lista = new ArrayList<>();
    }

    @Override
    public List<E> getLista() {
        return Collections.unmodifiableList(lista);
    }

    @Override
    public void registra(E el) {
        int idx = Collections.binarySearch(lista, el);
        
        // BinarySearch ritorna valori positivi se trova il valore, o (- posizione di inserimento - 1)
        // se non lo trova. In ogni caso, noi vogliamo inserire immediatamente dopo un prestito trovato
        // o nel punto di inserimento restituito.
        lista.add(Math.abs(idx + 1), el);
    }

    @Override
    public void rimuovi(E el) {
        int idx = Collections.binarySearch(lista, el);
        // Se l'indice è fuori dalla lista (l'elemento non è presente):
        if (idx < 0 || idx >= lista.size()) {
            throw new ValoreNonPresenteException();
        }
        lista.remove(idx);
    }

    @Override
    public void modifica(E elemento, Consumer<E> c) {
        int idx_remove = Collections.binarySearch(lista, elemento);
        if (idx_remove < 0 || idx_remove >= lista.size()) {
            throw new ValoreNonPresenteException();
        }
        
        E el = lista.get(idx_remove);
        
        lista.remove(idx_remove);
        
        // Applica modifiche dal consumer.
        c.accept(el);
        
        int idx_insert = Collections.binarySearch(lista, el);
        lista.add(Math.abs(idx_insert + 1), el);
    }
    
}
