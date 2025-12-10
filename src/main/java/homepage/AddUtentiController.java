/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import static homepage.UtentiController.UTENTI_MODEL;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGi‡PresenteException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.GestoreUtenti;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmailInvalidoException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.MatricolaInvalidaException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class AddUtentiController implements Initializable {

    @FXML
    private Button confermaButton;
    @FXML
    private Button annullaButton;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField matricolaField;
    @FXML
    private TextField mailField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleConferma(ActionEvent event) {
        try {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String matricolaStr = matricolaField.getText();
            String mailStr = mailField.getText();

            Matricola matricola = new Matricola(matricolaStr);
            IndirizzoEmail mail = new IndirizzoEmail(mailStr);
            Utente u = new Utente(nome, cognome, matricola, mail);

            GenericController.BIBLIOTECA.getArchivioUtenti().registra(u);

            // 2. Aggiorna la tabella (live)
            UTENTI_MODEL.setAll(GenericController.BIBLIOTECA.getArchivioUtenti().getLista());

            // 3. Chiudi la finestra (opzionale)
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (ValoreGi‡PresenteException e) {
            new Alert(Alert.AlertType.ERROR, "Matricola gi‡ presente").show();
        } catch (IndirizzoEmailInvalidoException ie) {
            new Alert(Alert.AlertType.ERROR, "Indirizzo email non valido").show();
        } catch (MatricolaInvalidaException me) {
            new Alert(Alert.AlertType.ERROR, "Matricola non valida").show();
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
