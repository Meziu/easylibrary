/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreGi‡PresenteException;
import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBNInvalidoException;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import java.net.URL;
import java.time.DateTimeException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class AddLibriController implements Initializable {

    @FXML
    private Button confermaButton;
    @FXML
    private Button annullaButton;
    @FXML
    private TextField titoloField;
    @FXML
    private TextField autoriField;
    @FXML
    private TextField isbnField;
    @FXML
    private Spinner<Integer> copieField;
    @FXML
    private TextField dataField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);

        // 2. Associa la Factory allo Spinner
        copieField.setValueFactory(valueFactory);
    }

    @FXML
    private void handleConferma(ActionEvent event) {
        Year annoPubblicazione;
        List<Autore> autoriObjects = new ArrayList<>();

        try {
            // 1. Recupero e pulizia dei valori
            String titolo = titoloField.getText().trim();
            String autoriStr = autoriField.getText().trim();
            String isbnStr = isbnField.getText().trim();
            String annoStr = dataField.getText().trim();
            int copie = copieField.getValue();
            
            // Verifica campi obbligatori
            if (titolo.isEmpty() || autoriStr.isEmpty() || isbnStr.isEmpty() || annoStr.isEmpty()) {
                 new Alert(Alert.AlertType.WARNING, "Tutti i campi sono obbligatori.").show();
                 return;
            }

            // 2. Validazione Anno di Pubblicazione
            try {
                int annoInt = Integer.parseInt(annoStr);
                if (annoInt <= 0) {
                     new Alert(Alert.AlertType.ERROR, "L'anno di pubblicazione deve essere un numero intero positivo.").show();
                     return;
                }
                annoPubblicazione = Year.of(annoInt);
                
            } catch (NumberFormatException | DateTimeException e) {
                new Alert(Alert.AlertType.ERROR, "L'anno di pubblicazione non Ë un formato valido (es. 2023).").show();
                return;
            }
            
            // Verifica che l'anno non sia nel futuro
            if (annoPubblicazione.isAfter(Year.now())) {
                new Alert(Alert.AlertType.ERROR, "L'anno di pubblicazione non puÚ essere nel futuro.").show();
                return;
            }

            // 3. Parsa e crea oggetti Autore
            String[] autoriArray = autoriStr.split("\\s*,\\s*");
            for (String nomeCompleto : autoriArray) {
                if (!nomeCompleto.trim().isEmpty()) {
                    // Tenta di splittare il nome in nome e cognome
                    String[] parti = nomeCompleto.trim().split("\\s+");
                    
                    if (parti.length >= 2) {
                         // Usiamo la prima parola come Nome, il resto come Cognome
                         String nome = parti[0];
                         String cognome = String.join(" ", Arrays.copyOfRange(parti, 1, parti.length));
                         autoriObjects.add(new Autore(nome, cognome));
                    } else {
                         // Se l'utente inserisce solo una parola (es. "Platone")
                         autoriObjects.add(new Autore("", nomeCompleto.trim())); // Nome vuoto, cognome = intero input
                    }
                }
            }
            
            if (autoriObjects.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Devi specificare almeno un autore.").show();
                return;
            }


            // 4. Creazione e Registrazione del Libro
            // Il costruttore Libro gestisce la creazione dell'oggetto ISBN da isbnStr
            Libro nuovoLibro = new Libro(titolo, autoriObjects, annoPubblicazione, isbnStr, copie);

            // Registra nell'archivio persistente
            GenericController.BIBLIOTECA.getArchivioLibri().registra(nuovoLibro); 

            // 5. Aggiorna la tabella (se LIBRI_MODEL Ë ObservableList e accessibile)
            // Sostituisci questo commento con la riga di aggiornamento corretta:
            LibriController.LIBRI_MODEL.setAll(GenericController.BIBLIOTECA.getArchivioLibri().getLista());

            // 6. Chiudi la finestra
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (ValoreGi‡PresenteException e) {
            new Alert(Alert.AlertType.ERROR, "ISBN gi‡ presente nell'archivio.").show();
        } catch (ISBNInvalidoException e) {
            // Sollevata dal costruttore ISBN all'interno del costruttore Libro
            new Alert(Alert.AlertType.ERROR, "Il codice ISBN non Ë valido.").show();
        } catch (Exception e) {
             // Catch generico per altri errori non gestiti (es. ArchivioLibri non trovato)
             new Alert(Alert.AlertType.ERROR, "Errore generico durante l'aggiunta: " + e.getMessage()).show();
             System.err.println("Errore: " + e.getMessage());
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
