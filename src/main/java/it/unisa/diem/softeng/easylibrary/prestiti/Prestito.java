package it.unisa.diem.softeng.easylibrary.prestiti;

import it.unisa.diem.softeng.easylibrary.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.libri.ISBN;
import java.io.Serializable;
import java.time.LocalDate;

public class Prestito implements Comparable<Prestito>, Serializable {

    private final Matricola matricolaUtente;
    private final ISBN idLibro;
    private StatoPrestito stato;
    private LocalDate dataDiScadenza;

    public Prestito(Matricola matricolaUtente, ISBN idLibro, StatoPrestito statoPrestito, LocalDate scadenzaPrestito) {
        this.matricolaUtente = matricolaUtente;
        this.idLibro = idLibro;
        this.stato = statoPrestito;
        this.dataDiScadenza = scadenzaPrestito;
    }

    public Matricola getMatricola() {
        return matricolaUtente;
    }

    public ISBN getISBN() {
        return idLibro;
    }

    public StatoPrestito getStato() {
        return stato;
    }

    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    public boolean isScaduto() {
        return dataDiScadenza.isBefore(LocalDate.now());
    }

    /*Permette di aggiornare lo stato del prestito*/
    public void setStatoPrestito(StatoPrestito newStato) {
        this.stato = newStato;
    }
    
    public void setDataDiScadenza(LocalDate newDataDiScadenza) {
        this.dataDiScadenza = newDataDiScadenza;
    }

    @Override
    public int compareTo(Prestito p) {
        int c1 = this.dataDiScadenza.compareTo(p.dataDiScadenza);

        if (c1 == 0) {
            int c2 = this.matricolaUtente.compareTo(p.matricolaUtente);

            if (c2 == 0) {
                return this.idLibro.compareTo(p.idLibro);
            }

            return c2;
        }

        return c1;
    }
}
