/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generic;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class GenericController implements Initializable {

    @FXML
    private Label labelRicerca;
    @FXML
    private VBox leftPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        labelRicerca.styleProperty().bind(
            leftPane.heightProperty().multiply(0.05).asString("-fx-font-size: %.0fpx;")
        );
    }    
    
}
