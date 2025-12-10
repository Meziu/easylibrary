/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleConferma(ActionEvent event) {
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
