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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private TextField copieField;

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
    }
    
}
