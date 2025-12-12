package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;


public class UtentiPageController extends DataPageController<Utente, RicercaUtenteController> {
    private Indicizzabile<Matricola, Utente> utenti;
    private Indicizzabile<ISBN, Libro> libri;
    private VisualizzatoreHomePage hp;
    
    public UtentiPageController(VisualizzatoreHomePage hp, Indicizzabile<Matricola, Utente> utenti, Indicizzabile<ISBN, Libro> libri){
        super(hp, new RicercaUtenteController(), "Utenti", "/res/RicercaUtente.fxml");
        
        this.utenti = utenti;
        this.libri = libri;
    }

    @Override
    public void add(ActionEvent event) {
        
    }

    @Override
    public void remove(ActionEvent event) {
        
    }
    
    @Override
    protected void initializeColonne() {

        TableColumn<Utente, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getAnagrafica().getNome())
        );

        TableColumn<Utente, String> cognomeCol = new TableColumn<>("Cognome");
        cognomeCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getAnagrafica().getCognome())
        );

        TableColumn<Utente, String> matrCol = new TableColumn<>("Matricola");
        matrCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getMatricola().getMatricola())
        );

        TableColumn<Utente, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEmail().getIndirizzoEmail())
        );

        TableColumn<Utente, String> prestitiCol = new TableColumn<>("Prestiti attivi");
        prestitiCol.setCellValueFactory(c ->
                new SimpleStringProperty( c.getValue().getPrestitiAttivi()
                        .stream()
                        .map(a -> libri.ottieni(a.getISBN()))
                        .map(l -> l.getTitolo())
                        .collect(Collectors.joining(", "))
                )
        );
        
        table.getColumns().addAll(matrCol, nomeCol, cognomeCol, emailCol, prestitiCol);

        
        // RENDIAMO LE COLONNE MODIFICABILI
        nomeCol.setEditable(true);
        cognomeCol.setEditable(true);
        emailCol.setEditable(true);
        
        
        // Carica gli utenti
        setItems(utenti.getLista());
    }
}
