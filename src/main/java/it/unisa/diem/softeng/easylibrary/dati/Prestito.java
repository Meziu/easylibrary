package ClassiPrincipali;

import java.time.LocalDate;
import java.time.Month;

public class Prestito {
    private Matricola matricolaUtente;
    private ISBN idLibro;
    private StatoPrestito statoPrestito;
    private LocalDate scadenzaPrestito;

    public Prestito(Matricola matricolaUtente, ISBN idLibro, StatoPrestito statoPrestito, LocalDate scadenzaPrestito){
        this.matricolaUtente = matricolaUtente;
        this.idLibro = idLibro;
        this.statoPrestito = statoPrestito;
        this.scadenzaPrestito = scadenzaPrestito;
    }
    
    public Matricola getMatricola(){
        return matricolaUtente;
    }
    
    public ISBN getISBN(){
        return idLibro;
    }
    
    public StatoPrestito getStatoPrestito() {
        return statoPrestito;
    }

    public LocalDate getScadenzaPrestito() {
        return scadenzaPrestito;
    }
    
    
    public boolean isScaduto(){
        return scadenzaPrestito.isBefore(LocalDate.now());
    }
    
    /*Permette di ggiornare lo stato del prestito*/
    public void setStatoPrestito(StatoPrestito newStato){
        this.statoPrestito = newStato;
    }
    
    
    /*
    @Override
    public int compareTo(){
        return 
    }
    */
}
