package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmailInvalidoException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.io.IOException;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;


public class UtentiPageController extends DataPageController<Utente, RicercaUtenteController, UtenteAddController> {
    private Indicizzabile<Matricola, Utente> utenti;
    private Indicizzabile<ISBN, Libro> libri;

    public UtentiPageController(VisualizzatorePagine vp, Indicizzabile<Matricola, Utente> utenti, Indicizzabile<ISBN, Libro> libri){
        super(utenti, vp, new RicercaUtenteController(), "Utenti", "/res/RicercaUtente.fxml", new UtenteAddController(utenti));

        this.utenti = utenti;
        this.libri = libri;
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
                        .collect(Collectors.joining("\n "))
                )
        );

        table.getColumns().addAll(matrCol, nomeCol, cognomeCol, emailCol, prestitiCol);


        // RENDIAMO LE COLONNE MODIFICABILI
        nomeCol.setEditable(true);
        cognomeCol.setEditable(true);
        emailCol.setEditable(true);

        //colonna nome effettivamente modificabile
        nomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nomeCol.setOnEditCommit((TableColumn.CellEditEvent<Utente, String> e) -> {
            Utente u = e.getRowValue();
            u.getAnagrafica().setNome(e.getNewValue());
        });

        //colonna cognome effettivamente modificabile
        cognomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cognomeCol.setOnEditCommit((TableColumn.CellEditEvent<Utente, String> e) -> {
            Utente u = e.getRowValue();
            u.getAnagrafica().setCognome(e.getNewValue());
        });

        //modifica l'email
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit((TableColumn.CellEditEvent<Utente, String> e) -> {
            Utente u = e.getRowValue();
            
            IndirizzoEmail nuovaEmail= new IndirizzoEmail(e.getNewValue());
            
            /*try{
                u.setEmail(nuovaEmail);
            }
            catch(IndirizzoEmailInvalidoException ex){
                new Alert(Alert.AlertType.ERROR, "Email non valida"+ ex.getMessage()).show();
            }*/
            
            
        });

        // Carica gli utenti
        setItems(utenti.getLista());
    }
}
