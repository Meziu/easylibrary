/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.libri;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author serenagiannitti
 */
public class OrdinatoreLibri implements Comparator<Libro>, Serializable {

    @Override
    public int compare(Libro l1, Libro l2) {
        // Ordinamento per titolo
        int c = l1.getTitolo().compareTo(l2.getTitolo());

        // Se i titoli sono uguali, ordina per ISBN
        if (c == 0) {
            return l1.getISBN().compareTo(l2.getISBN());
        }

        return c;
    }

}
