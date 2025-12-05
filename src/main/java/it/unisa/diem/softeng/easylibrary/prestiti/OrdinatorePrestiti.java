package it.unisa.diem.softeng.easylibrary.prestiti;

import java.io.Serializable;
import java.util.Comparator;

/**@class OrdinatorePrestiti.java
 * @brief Classe che definisce il criterio di ordinamento dei prestiti.
 * 
 * I prestiti vengono ordinati principalmente per data di scadenza.
 * In caso di uguaglianza della data di scadenza, l'ordinamento avviene
 * in base alla matricola dell'utente associato al prestito.
 * 
 * La classe implementa l'interfaccia Comparator ed è serializzabile.
 * 
 * @see Prestito
 * 
 * @author serenagiannitti
 */
public class OrdinatorePrestiti implements Comparator<Prestito>, Serializable {

    
    /**
     * @brief Confronta due prestiti per gestire il loro ordinamento.
     * 
     * Il confronto avviene:
     * - prima in base alla data di scadenza;
     * - in caso di uguaglianza, in base alla matricola.
     * 
     * @param p1 Primo prestito da confrontare.
     * @param p2 Secondo prestito da confrontare.
     * 
     * @return Valore negativo, zero o positivo se il primo prestito è
     * rispettivamente minore, uguale o maggiore del secondo.
    */
    @Override
    public int compare(Prestito p1, Prestito p2) {
        // Ordinamento per data di scadenza
        int c = p1.getDataDiScadenza().compareTo(p2.getDataDiScadenza());

        // Se le date di scadenza sono uguali, ordina per matricola
        if (c == 0) {
            return p1.getMatricola().compareTo(p2.getMatricola());
        }

        return c;
    }

}
