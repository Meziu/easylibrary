/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TextField textFieldNome;
    @FXML
    private Label labelLibro;
    @FXML
    private TextField textFieldMatricola;
    @FXML
    private Label chkboxLabel;
    @FXML
    private CheckBox chkBox;
    @FXML
    private VBox vboxUtente;
    @FXML
    private VBox vboxLibro;
    
    private TableView<?> tableView;

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
            bindFontSize(textFieldNome, vbox.heightProperty(), fontSizePercentage);

            // Ridimensiona il font dei campi Matricola
            bindFontSize(labelLibro, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldMatricola, vbox.heightProperty(), fontSizePercentage);
            
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

        // Chiama il metodo pubblico del GenericController (ereditato)
        try {
            createNewColumn(loanTableView, "Utente", "utente");
            createNewColumn(loanTableView, "Libro", "libro");
            createNewColumn(loanTableView, "Data Scadenza", "dataScadenza");
        } catch (RuntimeException e) {
            System.err.println("Errore di configurazione colonne Utenti: " + e.getMessage());
        }
    }
    
}
