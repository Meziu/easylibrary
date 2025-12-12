package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;


public class UtentiPageController extends DataPageController<Utente>{
    private Indicizzabile<Matricola, Utente> utenti;
    private Indicizzabile<ISBN, Libro> libri;
    
    public UtentiPageController(Indicizzabile<Matricola, Utente> utenti, Indicizzabile<ISBN, Libro> libri){
        this.utenti = utenti;
        this.libri = libri;
    }
    
    
    @Override
    protected void initializeColumns() {

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
        
        
        table.getColumns().addAll(nomeCol, cognomeCol, matrCol, emailCol, prestitiCol);

        
        // RENDIAMO LE COLONNE MODIFICABILI
        nomeCol.setEditable(true);
        cognomeCol.setEditable(true);
        emailCol.setEditable(true);
        
        
        // Carica gli utenti
        setItems(utenti.getLista());
    }
}
