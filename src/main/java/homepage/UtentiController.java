/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import it.unisa.diem.softeng.easylibrary.archivio.Filtro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.GestoreUtenti;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private Label labelMatricola;
    @FXML
    private TextField textFieldCognome;
    @FXML
    private TextField textFieldMatricola;
    @FXML
    private VBox vboxNome;
    @FXML
    private VBox vboxMatricola;

    private TableView<?> tableView;
    public static final ObservableList<Utente> UTENTI_MODEL = FXCollections.observableArrayList();

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
            bindFontSize(textFieldCognome, vbox.heightProperty(), fontSizePercentage);

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

        TableColumn<Utente, String> colNome = new TableColumn("Nome");
        userTableView.getColumns().add(colNome);
        TableColumn<Utente, String> colCognome = new TableColumn("Cognome");
        userTableView.getColumns().add(colCognome);
        TableColumn<Utente, ?> colMatricola = null;
        TableColumn<Utente, IndirizzoEmail> colMail = null;
        TableColumn<Utente, ?> colPrestiti = null;

        try {
            colNome.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAnagrafica().getNome()));
            colCognome.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAnagrafica().getCognome()));
            colMatricola = createNewColumn(userTableView, "Matricola", "matricola");
            colMail = createNewColumn(userTableView, "Mail", "email");
            colPrestiti = createNewColumn(userTableView, "N. Prestiti", "prestiti");

        } catch (RuntimeException e) {
            System.err.println("Errore di configurazione colonne Utenti: " + e.getMessage());
        }

        // RENDIAMO LE COLONNE MODIFICABILI
        colNome.setEditable(true);
        colCognome.setEditable(true);
        colMail.setEditable(true);

        // MODIFICHIAMO EFFETTIVAMENTE I DATI
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit((TableColumn.CellEditEvent<Utente, String> e) -> {
            Utente u = e.getRowValue();

            u.getAnagrafica().setNome(e.getNewValue());
        });

        colCognome.setCellFactory(TextFieldTableCell.forTableColumn());
        colCognome.setOnEditCommit((TableColumn.CellEditEvent<Utente, String> e) -> {
            Utente u = e.getRowValue();

            u.getAnagrafica().setCognome(e.getNewValue());
        });

        colMail.setCellFactory(TextFieldTableCell.forTableColumn(new IndirizzoEmailConverter()));
        colMail.setOnEditCommit((TableColumn.CellEditEvent<Utente, IndirizzoEmail> e) -> {
            IndirizzoEmail nuovaMail = e.getNewValue();
            Utente u = e.getRowValue();

            if (nuovaMail != null) {
                // SUCCESS: L'oggetto IndirizzoEmail è valido e viene salvato
                u.setEmail(nuovaMail);
            } else {
                // FAILURE: La validazione è fallita nel Convertitore (ha restituito null)

                // 1. Annulla la modifica a livello UI per ripristinare il vecchio valore
                // Questo è essenziale per forzare la cella a uscire dalla modalità di editing.
                e.getTableView().edit(-1, null);
                e.getTableView().refresh();
                new Alert(Alert.AlertType.ERROR, "Email non valida").show();
            }
        });

        userTableView.setItems(UTENTI_MODEL);

        UTENTI_MODEL.setAll(GenericController.BIBLIOTECA.getArchivioUtenti().getLista());

        textFieldCognome.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFiltroUtenti();
        });
        textFieldMatricola.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFiltroUtenti();
        });

    }

    private void updateFiltroUtenti() {
        // I filtri devono essere finali per essere usati nella lambda
        final String cognomeFilter = textFieldCognome.getText().trim().toLowerCase(Locale.ROOT);
        final String matricolaFilter = textFieldMatricola.getText().trim().toLowerCase(Locale.ROOT);

        // 1. Definiamo la LOGICA del filtro implementando l'interfaccia Filtro<Utente> tramite lambda
        Filtro<Utente> filtroCombinato = utente -> {

            // CASO BASE: MOSTRA TUTTO
            if (cognomeFilter.isEmpty() && matricolaFilter.isEmpty()) {
                return true;
            }

            boolean matchesCognome = true;
            boolean matchesMatricola = true;

            // CRITERIO NOME/COGNOME (RICERCA PER PREFISSO)
            if (!cognomeFilter.isEmpty()) {
                String cognome = utente.getAnagrafica().getCognome().toLowerCase(Locale.ROOT);

                // Requisito: Il filtro deve essere l'INIZIO del Nome OPPURE l'INIZIO del Cognome
                matchesCognome = cognome.startsWith(cognomeFilter);
            }

            // CRITERIO MATRICOLA (RICERCA PER PREFISSO)
            if (!matricolaFilter.isEmpty()) {
                String matricolaStr = utente.getMatricola().toString().toLowerCase(Locale.ROOT);

                // Requisito: Il filtro deve essere l'INIZIO della Matricola
                matchesMatricola = matricolaStr.startsWith(matricolaFilter);
            }

            // AND logico: l'utente passa il filtro se soddisfa TUTTI i criteri attivi
            return matchesCognome && matchesMatricola;
        };

        // 2. ESEGUI IL FILTRAGGIO SULL'ARCHIVIO COMPLETO
        List<Utente> listaFiltrata = GenericController.BIBLIOTECA.getArchivioUtenti().filtra(filtroCombinato);

        // 3. AGGIORNA LA TABLEVIEW
        UTENTI_MODEL.setAll(listaFiltrata);
    }
}
