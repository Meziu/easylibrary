/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import it.unisa.diem.softeng.easylibrary.dati.Anagrafica;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author marco
 */
public class PrestitiController extends GenericController {

    @FXML
    private VBox vbox;
    @FXML
    private Label labelRicerca;
    @FXML
    private Label labelUtente;
    @FXML
    private Label labelLibro;
    @FXML
    private Label chkboxLabel;
    @FXML
    private CheckBox chkBox;
    @FXML
    private VBox vboxUtente;
    @FXML
    private VBox vboxLibro;
    
    private TableView<?> tableView;
    @FXML
    private ComboBox<?> comboUtente;
    @FXML
    private HBox hboxPrestiti;
    @FXML
    private ComboBox<?> comboLibro;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (vbox != null) {

            double vBoxHeightPercentage = 0.05;
            double fontSizePercentage = 0.03;

            // 1) RIDIMENSIONAMENTO VERTICALE DELLE HBOX (Altezza)
            vboxUtente.prefHeightProperty().bind(vbox.heightProperty().multiply(vBoxHeightPercentage));
            vboxLibro.prefHeightProperty().bind(vbox.heightProperty().multiply(vBoxHeightPercentage));

            // 2) RIDIMENSIONAMENTO DEL FONT con il nuovo metodo
            // Ridimensiona il font della Label principale
            bindFontSize(labelRicerca, vbox.heightProperty(), 0.05);
            labelRicerca.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            // Ridimensiona il font dei campi Nome
            bindFontSize(labelUtente, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(comboUtente, vbox.heightProperty(), fontSizePercentage);

            // Ridimensiona il font dei campi Matricola
            bindFontSize(labelLibro, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(comboLibro, vbox.heightProperty(), fontSizePercentage);
            
            // Ridimensiona il font della Checkbox
            bindFontSize(chkboxLabel, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(chkBox, vbox.heightProperty(), fontSizePercentage);
        }
    }
    
    @Override
    public void setTableView(TableView<?> tableView) {
        this.tableView = tableView;
    }
    
    
    @Override
    public void setupSpecificColumns() {
        if (tableView == null) {
            throw new IllegalStateException("Impossibile configurare: TableView non iniettata.");
        }
        
        TableView<Prestito> loanTableView = (TableView<Prestito>) tableView;

        TableColumn<Prestito, String> colUtente = new TableColumn("Utente");
        colUtente.setCellValueFactory(r -> {
            Matricola m = r.getValue().getMatricola();
            Utente u = BIBLIOTECA.getArchivioUtenti().ottieni(m);
            
            Anagrafica a = u.getAnagrafica();
            String nome = a.getNome();
            String cognome = a.getCognome();
            String matricola = m.getMatricola();
            
            return new SimpleStringProperty(nome + " " + cognome + " <" + matricola +">");
        });
        
        TableColumn<Prestito, String> colLibro = new TableColumn("Libro");
        colLibro.setCellValueFactory(r -> {
            ISBN i = r.getValue().getISBN();
            Libro l = BIBLIOTECA.getArchivioLibri().ottieni(i);
            
            String titolo = l.getTitolo();
            String isbn = i.getISBN();
            
            return new SimpleStringProperty(titolo + " <" + isbn +">");
        });
        
        loanTableView.getColumns().add(colUtente);
        loanTableView.getColumns().add(colLibro);

        Prestito nuovoPrestito= new Prestito(new Matricola("0612708887"),new ISBN("1111111111"),StatoPrestito.ATTIVO,LocalDate.of(2005,Month.MAY,30));
        loanTableView.getItems().add(nuovoPrestito);
        try {
            createNewColumn(loanTableView, "Data Scadenza", "dataScadenza");
        } catch (RuntimeException e) {
            System.err.println("Errore di configurazione colonne Utenti: " + e.getMessage());
        }
    }
    
}
