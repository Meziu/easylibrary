/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBNInvalidoException;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.NessunaCopiaDisponibileException;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.LimitePrestitiSuperatoException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.MatricolaInvalidaException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class AddPrestitiController implements Initializable {

    @FXML
    private Button confermaButton;
    @FXML
    private Button annullaButton;
    @FXML
    private TextField utenteField;
    @FXML
    private TextField libroField;
    @FXML
    private DatePicker scadenzaField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        scadenzaField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
    }

    @FXML
    private void handleConferma(ActionEvent event) {
        // Dati di input
        String matricolaStr = utenteField.getText().trim();
        String isbnStr = libroField.getText().trim();

        // Oggetti Parsati
        Matricola matricola;
        ISBN isbn;
        LocalDate scadenza;

        try {
            // 1. Validazione e Parsing Matricola
            if (matricolaStr.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Il campo Utente (Matricola) è obbligatorio.").show();
                return;
            }
            matricola = new Matricola(matricolaStr);

            // 2. Validazione e Parsing ISBN
            if (isbnStr.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Il campo Libro (ISBN) è obbligatorio.").show();
                return;
            }
            isbn = new ISBN(isbnStr);

            // 3. Validazione Data di Scadenza (da DatePicker)
            scadenza = scadenzaField.getValue(); // Prende direttamente il LocalDate

            if (scadenza == null) {
                new Alert(Alert.AlertType.ERROR, "La Data di Scadenza è obbligatoria.").show();
                return;
            }

            // 4. Validazione logica della data: non nel passato
            if (scadenza.isBefore(LocalDate.now())) {
                new Alert(Alert.AlertType.ERROR, "La data di scadenza non può essere nel passato.").show();
                return;
            }

            // 5. Creazione del Prestito e Registrazione
            Prestito nuovoPrestito = new Prestito(matricola, isbn, StatoPrestito.ATTIVO, scadenza);

            // La logica di GestorePrestiti.registra() gestirà:
            // - ValoreNonPresenteException (se Utente/Libro non esistono)
            // - NessunaCopiaDisponibileException
            // - LimitePrestitiSuperatoException
            GenericController.BIBLIOTECA.getArchivioPrestiti().registra(nuovoPrestito);

            // 6. Aggiornamento UI 
            
            try {
                List<Prestito> listaAggiornata = GenericController.BIBLIOTECA.getArchivioPrestiti().getLista();
                PrestitiController.PRESTITI_MODEL.setAll(listaAggiornata);
                PrestitiController.INSTANCE.updateFiltroPrestiti();
            } catch (Exception updateE) {
                System.err.println("WARNING: Impossibile aggiornare la TableView: " + updateE.getMessage());
                // Mostra comunque il successo, ma avvisa del problema UI
            }
            // 7. Successo e Chiusura
            new Alert(Alert.AlertType.INFORMATION, "Prestito registrato con successo.").show();
            handleAnnulla(event); // Chiude la finestra

        } catch (MatricolaInvalidaException | ISBNInvalidoException e) {
            // Cattura eccezioni dovute ai costruttori di Matricola o ISBN
            new Alert(Alert.AlertType.ERROR, "Formato ID non valido: " + e.getMessage()).show();
        } catch (ValoreNonPresenteException e) {
            // Cattura Utente o Libro non esistente
            new Alert(Alert.AlertType.ERROR, "Elemento mancante: " + e.getMessage()).show();
        } catch (NessunaCopiaDisponibileException e) {
            new Alert(Alert.AlertType.WARNING, "Nessuna copia disponibile per il libro richiesto.").show();
        } catch (LimitePrestitiSuperatoException e) {
            new Alert(Alert.AlertType.WARNING, "L'utente ha già raggiunto il limite massimo di prestiti attivi.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Errore sconosciuto: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAnnulla(ActionEvent event) {
        // 1. Ottieni il nodo sorgente dell'evento (il bottone Annulla)
        Node source = (Node) event.getSource();

        // 2. Ottieni l'oggetto Scene che contiene il nodo
        // 3. Ottieni lo Stage (la finestra) che contiene la Scene
        Stage stage = (Stage) source.getScene().getWindow();

        // 4. Chiudi lo Stage
        stage.close();
    }

}
