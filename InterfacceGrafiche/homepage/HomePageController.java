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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

/**
 *
 * @author ray
 */
public class HomePageController implements Initializable {
    
    private Label label;
    @FXML
    private Button utentiButton;
    @FXML
    private Button libriButton;
    @FXML
    private Button prestitiButton;
    @FXML
    private MenuItem saveButton;
    @FXML
    private MenuItem openButton;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void visualizzaUtenti(ActionEvent event) {
    }

    @FXML
    private void visualizzaLibri(ActionEvent event) {
    }

    @FXML
    private void visualizzaPrestiti(ActionEvent event) {
    }

    @FXML
    private void saveFile(ActionEvent event) {
    }

    @FXML
    private void openFile(ActionEvent event) {
    }

    
}
