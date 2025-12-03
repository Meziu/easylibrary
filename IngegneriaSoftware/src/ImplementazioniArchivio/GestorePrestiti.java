package ImplementazioniArchivio;

import Archivi.Archivio;
import ClassiPrincipali.Prestito;
import ClassiPrincipali.StatoPrestito;
import java.util.ArrayList;
import java.util.List;

public class GestorePrestiti extends Archivio<Prestito>{ //implements Ricercabile<StatoPrestito, Prestito>
    private List<Prestito> storicoPrestiti;

    public GestorePrestiti() {
        this.storicoPrestiti = new ArrayList<>();
    }
    
    
    /*Funzione che aggiunge un nuovo prestito alla lista dei prestiti*/
    @Override
    public void registra(Prestito p){
        storicoPrestiti.add(p);
    }
    
    /*Funzione che segna un prestito come RESTITUITO*/
    @Override
    public void rimuovi(Prestito p){
        p.setStatoPrestito(StatoPrestito.RESTITUITO);
    }
    
    
    public List<Prestito> getStoricoPrestiti(){
        return storicoPrestiti;
    }
    
    public List<Prestito> getPrestitiAttivi(){
        List<Prestito> attivi = getStoricoPrestiti();
        ///IMPLEMENTARE FILTRO
        //filtra(StatoPrestito.ATTIVO);
        return attivi;
    }
    
    
    
    
    
    //Funzione che cerca i prestiti attivi/restituiti
    public  List<Prestito> cerca(StatoPrestito s){
        List<Prestito> prestiti = new ArrayList<>();
        
        for (Prestito p : storicoPrestiti){
            if(p.getStatoPrestito() == s){
                prestiti.add(p);
            }
        }
        return prestiti;
    }
    
    //Cerca un determinato prestito
    public  Prestito cerca(Prestito p){ 
        for (Prestito pr : storicoPrestiti){
            if(pr.equals(p)){
                return pr;
            }
        }
        return null;
    }
}
