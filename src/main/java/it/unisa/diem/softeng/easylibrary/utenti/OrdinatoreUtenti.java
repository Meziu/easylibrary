/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.utenti;

import it.unisa.diem.softeng.easylibrary.dati.Persona;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author serenagiannitti
 */
public class OrdinatoreUtenti implements Comparator<Utente>, Serializable {

    @Override
    public int compare(Utente o1, Utente o2) {
        // Ordinamento dei nomi
        int c = ((Persona) o1).compareTo(o2);

        // Se i nomi sono uguali, ordina per matricola
        if (c == 0) {
            return o1.getMatricola().compareTo(o2.getMatricola());
        }

        return c;
    }

}
