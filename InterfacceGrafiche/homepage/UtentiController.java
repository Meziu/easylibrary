/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utenti.Utente;

/**
 *
 * @author marco
 */
public class UtentiController extends GenericController {

    @FXML
    private Label labelRicerca;
    @FXML
    private VBox vbox;
    @FXML
    private Label labelNome;
    @FXML
    private TextField textFieldNome;
    @FXML
    private Label labelMatricola;
    @FXML
    private TextField textFieldMatricola;
    @FXML
    private VBox vboxNome;
    @FXML
    private VBox vboxMatricola;
    
    private TableView<?> tableView;

    public void initialize(URL url, ResourceBundle rb) {
        if (vbox != null) {

            double vBoxHeightPercentage = 0.05;
            double fontSizePercentage = 0.03;

            // 1) RIDIMENSIONAMENTO VERTICALE DELLE HBOX (Altezza)
            vboxNome.prefHeightProperty().bind(vbox.heightProperty().multiply(vBoxHeightPercentage));
            vboxMatricola.prefHeightProperty().bind(vbox.heightProperty().multiply(vBoxHeightPercentage));

            // 2) RIDIMENSIONAMENTO DEL FONT con il nuovo metodo
            // Ridimensiona il font della Label principale
            bindFontSize(labelRicerca, vbox.heightProperty(), 0.05);
            labelRicerca.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            // Ridimensiona il font dei campi Nome
            bindFontSize(labelNome, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldNome, vbox.heightProperty(), fontSizePercentage);

            // Ridimensiona il font dei campi Matricola
            bindFontSize(labelMatricola, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldMatricola, vbox.heightProperty(), fontSizePercentage);
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
        
        TableView<Utente> userTableView = (TableView<Utente>) tableView;

        // Chiama il metodo pubblico del GenericController (ereditato)
        try {
            createNewColumn(userTableView, "Nome", "nome");
            createNewColumn(userTableView, "Cognome", "cognome");
            createNewColumn(userTableView, "Matricola", "matricola");
            createNewColumn(userTableView, "Mail", "mail");
            createNewColumn(userTableView, "Prestiti", "prestiti");
        } catch (RuntimeException e) {
            System.err.println("Errore di configurazione colonne Utenti: " + e.getMessage());
        }
    }
    
    

    
}
