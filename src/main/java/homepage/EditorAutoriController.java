package homepage;

import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class EditorAutoriController implements Initializable {

    @FXML
    private TableView<Autore> autoriTableView;
    @FXML
    private TableColumn<Autore, String> nomeColumn;
    @FXML
    private TableColumn<Autore, String> cognomeColumn;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;

    private Libro libroCorrente; // Riferimento al Libro da modificare in LibriController
    private ObservableList<Autore> autoriModel; // Modello temporaneo per l'editing

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomeColumn.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAnagrafica().getNome()));
        cognomeColumn.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAnagrafica().getCognome()));

        nomeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cognomeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Salva la modifica del Nome
        nomeColumn.setOnEditCommit((TableColumn.CellEditEvent<Autore, String> e) -> {
            Autore autore = e.getRowValue();
            String nuovoNome = e.getNewValue().trim();

            if (!nuovoNome.isEmpty()) {
                // Modifica l'oggetto Anagrafica all'interno dell'Autore
                autore.getAnagrafica().setNome(nuovoNome);
            } else {
                new Alert(Alert.AlertType.ERROR, "Il nome non può essere vuoto.").show();
                // Forza l'annullamento della modifica per ripristinare il vecchio valore
                e.getTableView().refresh();
            }
        });

        // Salva la modifica del Cognome
        cognomeColumn.setOnEditCommit((TableColumn.CellEditEvent<Autore, String> e) -> {
            Autore autore = e.getRowValue();
            String nuovoCognome = e.getNewValue().trim();

            if (!nuovoCognome.isEmpty()) {
                // Modifica l'oggetto Anagrafica all'interno dell'Autore
                autore.getAnagrafica().setCognome(nuovoCognome);
            } else {
                new Alert(Alert.AlertType.ERROR, "Il cognome non può essere vuoto.").show();
                e.getTableView().refresh();
            }
        });
    }

    public void setLibro(Libro libro) {
        this.libroCorrente = libro;

        // Clonazione DEEP COPY (nuovi oggetti Autore)
        List<Autore> autoriClonati = new ArrayList<>();
        for (Autore autoreOriginale : libro.getAutori()) {
            // Creiamo un nuovo oggetto Autore con nuove Anagrafiche
            Autore nuovoAutore = new Autore(
                    autoreOriginale.getAnagrafica().getNome(),
                    autoreOriginale.getAnagrafica().getCognome()
            );
            autoriClonati.add(nuovoAutore);
        }

        this.autoriModel = FXCollections.observableArrayList(autoriClonati);
        autoriTableView.setItems(autoriModel);
    }

    @FXML
    private void handleAddAutore(ActionEvent event) {
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();

        if (!nome.isEmpty() && !cognome.isEmpty()) {
            Autore nuovoAutore = new Autore(nome, cognome);
            autoriModel.add(nuovoAutore);

            nomeField.clear();
            cognomeField.clear();
        } else {
            new Alert(Alert.AlertType.WARNING, "Nome e Cognome sono obbligatori.").show();
        }
    }

    @FXML
    private void handleRimuoviAutore(ActionEvent event) {
        Autore selected = autoriTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            autoriModel.remove(selected);
        }
    }

    @FXML
    private void handleConferma(ActionEvent event) {
        // Salva la lista modificata nel Libro originale
        if (this.libroCorrente != null) {
            this.libroCorrente.setAutori(new ArrayList<>(autoriModel));
            // setAutori(List<Autore>) modifica l'oggetto Libro
        }
        // Chiudi il popup
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void handleAnnulla(ActionEvent event) {
        // Chiudi senza salvare le modifiche nel LibroCorrente
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
