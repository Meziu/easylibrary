/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.prestiti;

import java.util.Comparator;

/**
 *
 * @author serenagiannitti
 */
public class OrdinatorePrestiti implements Comparator<Prestito> {

    @Override
    public int compare(Prestito p1, Prestito p2) {
        // Ordinamento per data di scadenza
        int c = p1.getDataDiScadenza().compareTo(p2.getDataDiScadenza());

        // Se le date di scadenza sono uguali, ordina per matricola
        if (c == 0) {
            return p1.getMatricola().compareTo(p1.getMatricola());
        }

        return c;
    }

}
