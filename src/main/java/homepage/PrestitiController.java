/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import it.unisa.diem.softeng.easylibrary.archivio.Filtro;
import it.unisa.diem.softeng.easylibrary.dati.Anagrafica;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
    private HBox hboxPrestiti;
    @FXML
    private TextField textFieldUtente;
    @FXML
    private TextField textFieldLibro;

    public static final ObservableList<Prestito> PRESTITI_MODEL = FXCollections.observableArrayList();
    public static PrestitiController INSTANCE;

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
            bindFontSize(textFieldUtente, vbox.heightProperty(), fontSizePercentage);

            // Ridimensiona il font dei campi Matricola
            bindFontSize(labelLibro, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldLibro, vbox.heightProperty(), fontSizePercentage);

            // Ridimensiona il font della Checkbox
            bindFontSize(chkboxLabel, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(chkBox, vbox.heightProperty(), fontSizePercentage);

            chkBox.setSelected(true);
        }
        
        INSTANCE = this;
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
            Prestito prestito = r.getValue();
            if (prestito == null) {
                return new SimpleStringProperty("Dato Prestito Mancante");
            }

            Matricola m = prestito.getMatricola();

            // 1. Cerca l'Utente
            Utente u = BIBLIOTECA.getArchivioUtenti().ottieni(m);

            // 2. GESTIONE MANCANZA UTENTE (EVITA NPE)
            if (u == null) {
                return new SimpleStringProperty("Utente non Trovato (Matricola: " + m.getMatricola() + ")");
            }

            // 3. Estrazione dei dati
            Anagrafica a = u.getAnagrafica(); // Assumiamo che Anagrafica non sia null se Utente non è null
            String nome = a.getNome();
            String cognome = a.getCognome();
            String matricola = m.getMatricola();

            return new SimpleStringProperty(nome + " " + cognome + " <" + matricola + ">");
        });
        loanTableView.getColumns().add(colUtente);

        TableColumn<Prestito, String> colLibro = new TableColumn("Libro");
        colLibro.setCellValueFactory(r -> {
            Prestito prestito = r.getValue();
            if (prestito == null) {
                return new SimpleStringProperty("Dato Prestito Mancante");
            }

            ISBN i = prestito.getISBN();

            // 1. Cerca il Libro
            Libro l = BIBLIOTECA.getArchivioLibri().ottieni(i);

            // 2. GESTIONE MANCANZA LIBRO (EVITA NPE)
            if (l == null) {
                return new SimpleStringProperty("Libro non Trovato (ISBN: " + i.getISBN() + ")");
            }

            // 3. Estrazione dei dati
            String titolo = l.getTitolo();
            String isbn = i.getISBN();

            return new SimpleStringProperty(titolo + " <" + isbn + ">");
        });

        loanTableView.getColumns().add(colLibro);

        TableColumn<Prestito, LocalDate> colData = null;
        TableColumn<Prestito, StatoPrestito> colStato = null;
        try {
            colData = createNewColumn(loanTableView, "Data Scadenza", "dataDiScadenza");
            colStato = createNewColumn(loanTableView, "Stato", "stato");
        } catch (RuntimeException e) {
            System.err.println("Errore di configurazione colonne Utenti: " + e.getMessage());
        }

        colData.setEditable(true);
        colData.setCellFactory(column -> new DatePickerTableCell<Prestito, LocalDate>());
        colData.setOnEditCommit((TableColumn.CellEditEvent<Prestito, LocalDate> e) -> {
            LocalDate nuovaData = e.getNewValue();
            Prestito p = e.getRowValue();

            if (nuovaData != null && !nuovaData.isBefore(LocalDate.now())) {
                // SUCCESS: Aggiorna l'oggetto Prestito e salva l'archivio (OPZIONALE)
                p.setDataDiScadenza(nuovaData);
                // Potresti voler salvare qui l'archivio per la persistenza:
                // GenericController.BIBLIOTECA.salvaArchivio();
            } else {
                // FAILURE: Data non valida (nullo o nel passato)

                String errorMessage = (nuovaData == null)
                        ? "La data selezionata non è valida."
                        : "La data non può essere nel passato.";

                new Alert(Alert.AlertType.ERROR, errorMessage).show();

                // Annulla l'editing e ripristina il vecchio valore
                e.getTableView().edit(-1, null);
                e.getTableView().refresh();
            }
        });

        loanTableView.setItems(PRESTITI_MODEL);
        PRESTITI_MODEL.setAll(GenericController.BIBLIOTECA.getArchivioPrestiti().getLista());
        updateFiltroPrestiti();

        textFieldUtente.textProperty().addListener((obs, oldV, newV) -> updateFiltroPrestiti());
        textFieldLibro.textProperty().addListener((obs, oldV, newV) -> updateFiltroPrestiti());
        chkBox.selectedProperty().addListener((obs, oldV, newV) -> updateFiltroPrestiti());
    }

    public void updateFiltroPrestiti() {

        // 1. Recupero e normalizzazione dei filtri
        final String utenteFilter = textFieldUtente.getText().trim().toLowerCase(Locale.ROOT);
        final String libroFilter = textFieldLibro.getText().trim().toLowerCase(Locale.ROOT);
        // Otteniamo lo stato della checkbox
        final boolean soloAttivi = chkBox.isSelected();

        // 2. Definiamo la LOGICA del filtro (Predicate)
        Filtro<Prestito> filtroCombinato = prestito -> {

            // --- CRITERIO STATO (Checkbox) ---
            if (soloAttivi) {
                // Se la checkbox è spuntata, ignoriamo i prestiti RESTITUITI
                if (prestito.getStato() == StatoPrestito.RESTITUITO) {
                    return false;
                }
            }

            // --- CRITERIO UTENTE (Matricola, Nome, Cognome) ---
            if (!utenteFilter.isEmpty()) {
                // Dobbiamo cercare l'Utente nell'archivio
                Matricola m = prestito.getMatricola();
                Utente u = GenericController.BIBLIOTECA.getArchivioUtenti().ottieni(m);

                // Se l'utente non esiste o il Prestito non ha Utente, non match (e previene NPE)
                if (u == null) {
                    return false;
                }

                String matricolaStr = m.getMatricola().toLowerCase(Locale.ROOT);
                String nome = u.getAnagrafica().getNome().toLowerCase(Locale.ROOT);
                String cognome = u.getAnagrafica().getCognome().toLowerCase(Locale.ROOT);

                // Cerca se l'input match l'inizio di Matricola, Nome o Cognome
                boolean matchesUtente = matricolaStr.startsWith(utenteFilter)
                        || nome.startsWith(utenteFilter)
                        || cognome.startsWith(utenteFilter);

                if (!matchesUtente) {
                    return false;
                }
            }

            // --- CRITERIO LIBRO (Titolo) ---
            if (!libroFilter.isEmpty()) {
                // Dobbiamo cercare il Libro nell'archivio
                ISBN i = prestito.getISBN();
                Libro l = GenericController.BIBLIOTECA.getArchivioLibri().ottieni(i);

                // Se il libro non esiste o il Prestito non ha ISBN, non match
                if (l == null) {
                    return false;
                }

                String titolo = l.getTitolo().toLowerCase(Locale.ROOT);

                // Cerca se l'input match l'inizio del Titolo
                if (!titolo.startsWith(libroFilter)) {
                    return false;
                }
            }

            // Se il prestito ha superato tutti i filtri (Stato + Utente + Libro)
            return true;
        };

        // 3. ESEGUI IL FILTRAGGIO e aggiorna l'ObservableList della tabella
        List<Prestito> listaFiltrata = GenericController.BIBLIOTECA.getArchivioPrestiti().filtra(filtroCombinato);
        PRESTITI_MODEL.setAll(listaFiltrata);
    }

}
