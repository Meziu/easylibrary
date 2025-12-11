/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import it.unisa.diem.softeng.easylibrary.archivio.Filtro;
import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

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

    private TableView<Libro> bookTableView;
    public static final ObservableList<Libro> LIBRI_MODEL = FXCollections.observableArrayList();

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
        this.bookTableView = (TableView<Libro>) tableView;
    }

    private void apriEditorAutori(Libro libroDaModificare) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homepage/EditorAutoriView.fxml"));
            Parent root = loader.load();

            EditorAutoriController editorController = loader.getController();

            // 1. Passa il Libro al controller EditorAutoriController
            editorController.setLibro(libroDaModificare);

            Stage ownerStage = (Stage) bookTableView.getScene().getWindow();
            Stage popupStage = new Stage();
            popupStage.setTitle("Modifica Autori: " + libroDaModificare.getTitolo());
            popupStage.setScene(new Scene(root));
            popupStage.initOwner(ownerStage);
            popupStage.initModality(Modality.WINDOW_MODAL);

            popupStage.showAndWait();

            // Dopo la chiusura, l'oggetto 'libroDaModificare' è stato aggiornato dall'EditorAutoriController.
            // Forza l'aggiornamento della riga per mostrare la nuova stringa di autori
            bookTableView.refresh();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Impossibile aprire l'editor degli autori.").show();
            e.printStackTrace();
        }
    }

    @Override
    public void setupSpecificColumns() {
        if (bookTableView == null) {
            throw new IllegalStateException("Impossibile configurare: TableView non iniettata.");
        }

        TableColumn<Libro, String> colTitolo = null;
        TableColumn<Libro, ISBN> colISBN = null;
        TableColumn<Libro, Year> colAnno = null;
        TableColumn<Libro, Integer> colCopie = null;
        TableColumn<Libro, String> colAutori = new TableColumn("Autori");

        try {
            colTitolo = createNewColumn(bookTableView, "Titolo", "titolo");
            bookTableView.getColumns().add(colAutori);
            colAutori.setCellValueFactory(data -> {
                // Ottiene la lista di oggetti Autore per il libro corrente
                List<Autore> autoriList = data.getValue().getAutori();

                // Controlla se la lista è vuota per evitare NullPointerException
                if (autoriList == null || autoriList.isEmpty()) {
                    return new SimpleStringProperty("");
                }

                // Trasforma la lista di oggetti Autore in una singola stringa:
                // 1. stream() avvia l'elaborazione della lista
                // 2. map(...) trasforma ogni oggetto Autore nel suo Cognome (getAnagrafica().getCognome())
                // 3. collect(Collectors.joining(", ")) unisce tutti i cognomi separandoli con ", "
                String autoriString = autoriList.stream()
                        .map(autore -> autore.getAnagrafica().getCognome())
                        .collect(Collectors.joining(", "));

                // Restituisce la stringa finale avvolta in una SimpleStringProperty
                return new SimpleStringProperty(autoriString);
            });
            colISBN = createNewColumn(bookTableView, "ISBN", "ISBN");
            colAnno = createNewColumn(bookTableView, "Anno Pubblicazione", "annoPubblicazione");
            colCopie = createNewColumn(bookTableView, "Copie Disponibili", "copieDisponibili");

        } catch (RuntimeException e) {
            System.err.println("Errore di configurazione colonne Utenti: " + e.getMessage());
        }

        // RENDIAMO LE COLONNE MODIFICABILI
        colTitolo.setEditable(true);
        colISBN.setEditable(true);
        colAnno.setEditable(true);
        colCopie.setEditable(true);

        // MODIFICHIAMO EFFETTIVAMENTE I DATI
        colTitolo.setCellFactory(TextFieldTableCell.forTableColumn());
        colTitolo.setOnEditCommit((TableColumn.CellEditEvent<Libro, String> e) -> {
            Libro l = e.getRowValue();

            l.setTitolo(e.getNewValue());
        });

        colAnno.setCellFactory(TextFieldTableCell.forTableColumn(new YearStringConverter()));
        colAnno.setOnEditCommit((TableColumn.CellEditEvent<Libro, Year> e) -> {
            Year nuovoAnno = e.getNewValue();
            Libro l = e.getRowValue();

            if (nuovoAnno != null) {
                // SUCCESS
                l.setAnnoPubblicazione(nuovoAnno);
            } else {
                // FAILURE 
                e.getTableView().edit(-1, null); // Chiude l'editor e ripristina il vecchio valore
                e.getTableView().refresh(); // Forzatura UI per assicurare il ripristino
                new Alert(Alert.AlertType.ERROR, "L'anno inserito non è valido (deve essere un numero intero positivo e non nel futuro).").show();
            }
        });

        colCopie.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colCopie.setOnEditCommit((TableColumn.CellEditEvent<Libro, Integer> e) -> {

            Integer newValue = e.getNewValue();
            Libro libro = e.getRowValue();

            if (newValue != null && newValue >= 0) {
                libro.setCopieDisponibili(newValue);

            } else {
                // Il valore non è valido (es. l'utente ha provato a digitare del testo o un numero negativo)

                // 1. Annulla la modifica a livello UI per ripristinare il vecchio valore
                e.getTableView().edit(-1, null);
                e.getTableView().refresh(); // Forzatura UI per assicurare il ripristino

                new Alert(Alert.AlertType.ERROR, "Il numero di copie deve essere un intero positivo o zero.").show();
            }
        });

        colAutori.setEditable(false); // Non si modifica direttamente

        bookTableView.setRowFactory(tv -> {
            TableRow<Libro> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Libro selectedLibro = row.getItem();
                    apriEditorAutori(selectedLibro); // Apre il popup
                }
            });
            return row;
        });

        // ISBN NON MODIFICABILE
        /*colISBN.setCellFactory(TextFieldTableCell.forTableColumn(new ISBNConverter()));
        colISBN.setOnEditCommit((TableColumn.CellEditEvent<Libro, ISBN> e) -> {
            ISBN nuovoISBN = e.getNewValue();
            Libro l = e.getRowValue();

            if (nuovoISBN != null) {
                // SUCCESS: L'oggetto IndirizzoEmail è valido e viene salvato
                l.setISBN(nuovoISBN);
            } else {
                // FAILURE: La validazione è fallita nel Convertitore (ha restituito null)

                // 1. Annulla la modifica a livello UI per ripristinare il vecchio valore
                // Questo è essenziale per forzare la cella a uscire dalla modalità di editing.
                e.getTableView().edit(-1, null);
                e.getTableView().refresh();
                new Alert(Alert.AlertType.ERROR, "Email non valida").show();
            }
        });*/
        bookTableView.setItems(LIBRI_MODEL);

        LIBRI_MODEL.setAll(GenericController.BIBLIOTECA.getArchivioLibri().getLista());

        textFieldTitolo.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFiltroLibri();
        });

        textFieldAutore.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFiltroLibri();
        });

        textFieldISBN.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFiltroLibri();
        });
    }

    private void updateFiltroLibri() {

        // 1. Recupero e normalizzazione dei filtri
        // Normalizziamo in minuscolo per una ricerca case-insensitive
        final String titoloFilter = textFieldTitolo.getText().trim().toLowerCase(Locale.ROOT);
        final String autoreFilter = textFieldAutore.getText().trim().toLowerCase(Locale.ROOT);
        final String isbnFilter = textFieldISBN.getText().trim().toLowerCase(Locale.ROOT);

        // 2. Definiamo la LOGICA del filtro (Predicate)
        Filtro<Libro> filtroCombinato = libro -> {

            // CASO BASE: Se tutti i campi di ricerca sono vuoti, mostra il libro (true)
            if (titoloFilter.isEmpty() && autoreFilter.isEmpty() && isbnFilter.isEmpty()) {
                return true;
            }

            boolean matchesTitolo = true;
            boolean matchesAutore = true;
            boolean matchesISBN = true;

            // CRITERIO TITOLO (Ricerca per Prefisso)
            if (!titoloFilter.isEmpty()) {
                String titolo = libro.getTitolo().toLowerCase(Locale.ROOT);
                // Il filtro è soddisfatto se il titolo INIZIA con il testo cercato
                matchesTitolo = titolo.startsWith(titoloFilter);
            }

            // CRITERIO AUTORE (Ricerca nella Lista di Autori)
            if (!autoreFilter.isEmpty()) {
                // Il filtro è soddisfatto se ALMENO UN autore soddisfa il criterio
                matchesAutore = libro.getAutori().stream().anyMatch(autore -> {
                    String nome = autore.getAnagrafica().getNome().toLowerCase(Locale.ROOT);
                    String cognome = autore.getAnagrafica().getCognome().toLowerCase(Locale.ROOT);

                    // L'input deve matchare l'INIZIO del Nome OPPURE l'INIZIO del Cognome
                    return nome.startsWith(autoreFilter) || cognome.startsWith(autoreFilter);
                });
            }

            // CRITERIO ISBN (Ricerca per Prefisso)
            if (!isbnFilter.isEmpty()) {
                String isbnStr = libro.getISBN().getISBN().toLowerCase(Locale.ROOT);
                matchesISBN = isbnStr.startsWith(isbnFilter);
            }

            // 3. Condizione finale: Il libro deve soddisfare TUTTI i criteri attivi (AND logico)
            return matchesTitolo && matchesAutore && matchesISBN;
        };

        // 4. ESEGUI IL FILTRAGGIO e aggiorna l'ObservableList della tabella
        List<Libro> listaFiltrata = GenericController.BIBLIOTECA.getArchivioLibri().filtra(filtroCombinato);
        LIBRI_MODEL.setAll(listaFiltrata);
    }

}
