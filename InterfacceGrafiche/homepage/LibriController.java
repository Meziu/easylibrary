/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import libri.Libro;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class LibriController extends GenericController {

    @FXML
    private VBox vbox;
    @FXML
    private Label labelRicerca;
    @FXML
    private VBox vboxTitolo;
    @FXML
    private Label labelTitolo;
    @FXML
    private TextField textFieldTitolo;
    @FXML
    private VBox vboxAutore;
    @FXML
    private Label labelAutore;
    @FXML
    private TextField textFieldAutore;
    @FXML
    private VBox vboxISBN;
    @FXML
    private Label labelISBN;
    @FXML
    private TextField textFieldISBN;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (vbox != null) {

            double vBoxHeightPercentage = 0.05;
            double fontSizePercentage = 0.03;

            // 1) RIDIMENSIONAMENTO VERTICALE DELLE HBOX (Altezza)
            vboxTitolo.prefHeightProperty().bind(vbox.heightProperty().multiply(vBoxHeightPercentage));
            vboxAutore.prefHeightProperty().bind(vbox.heightProperty().multiply(vBoxHeightPercentage));
            vboxISBN.prefHeightProperty().bind(vbox.heightProperty().multiply(vBoxHeightPercentage));

            // 2) RIDIMENSIONAMENTO DEL FONT con il nuovo metodo
            // Ridimensiona il font della Label principale
            bindFontSize(labelRicerca, vbox.heightProperty(), 0.05);
            labelRicerca.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            // Ridimensiona il font dei campi Titolo
            bindFontSize(labelTitolo, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldTitolo, vbox.heightProperty(), fontSizePercentage);

            // Ridimensiona il font dei campi Autore
            bindFontSize(labelAutore, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldAutore, vbox.heightProperty(), fontSizePercentage);
            
            // Ridimensiona il font dei campi ISBN
            bindFontSize(labelISBN, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldISBN, vbox.heightProperty(), fontSizePercentage);
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
        
        TableView<Libro> bookTableView = (TableView<Libro>) tableView;

        // Chiama il metodo pubblico del GenericController (ereditato)
        try {
            createNewColumn(bookTableView, "Titolo", "titolo");
            createNewColumn(bookTableView, "Autori", "autori");
            createNewColumn(bookTableView, "ISBN", "ISBN");
            createNewColumn(bookTableView, "Data Pubblicazione", "dataPubblicazione");
            createNewColumn(bookTableView, "Copie", "copie");
            
        } catch (RuntimeException e) {
            System.err.println("Errore di configurazione colonne Utenti: " + e.getMessage());
        }
    }
    
}
