package it.unisa.diem.softeng.easylibrary.dati;

import java.time.LocalDate;

public class Prestito implements Comparable<Prestito> {

    private Matricola matricolaUtente;
    private ISBN idLibro;
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

    public StatoPrestito getStatoPrestito() {
        return stato;
    }

    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    public boolean isScaduto() {
        return dataDiScadenza.isBefore(LocalDate.now());
    }

    /*Permette di ggiornare lo stato del prestito*/
    public void setStatoPrestito(StatoPrestito newStato) {
        this.stato = newStato;
    }
    
    @Override
    public int compareTo(Prestito p){
        return this.dataDiScadenza.compareTo(p.dataDiScadenza);
    }
}
