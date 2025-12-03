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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void visualizzaUtenti(ActionEvent event) throws Exception {
        loadGenericPage("A", event);
    }

    @FXML
    private void visualizzaLibri(ActionEvent event) throws Exception {
        loadGenericPage("B", event);
    }

    @FXML
    private void visualizzaPrestiti(ActionEvent event) throws Exception {
        loadGenericPage("C", event);
    }

    @FXML
    private void saveFile(ActionEvent event) {
    }

    @FXML
    private void openFile(ActionEvent event) {
    }

    private void loadGenericPage(String type, ActionEvent event) throws Exception {

        GenericController controller;

        switch (type) {
            case "A":
                controller = new UtentiController();
                break;
            case "B":
                controller = new UtentiController(); //da cambiare
                break;
            case "C":
                controller = new UtentiController();  //da cambiare
                break;
            default:
                throw new IllegalArgumentException("Invalid type");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GenericView.fxml"));
        loader.setController(controller); 
        Parent root = loader.load();

        // carica la parte specifica
        
        String file = null;
                
         switch (type) {
            case "A" :
                file = "UtentiView.fxml"; break;
            case "B" :
                file = "LibriView.fxml"; break; //bisogna creare il file
            case "C" :
                file = "PrestitiView.fxml"; break; // bisogna creare il file
            default :
                break;
        };

        FXMLLoader loaderSpecifico = new FXMLLoader(getClass().getResource(file));
        Parent rootSpecifico = loaderSpecifico.load();

        controller.addLeftNode(rootSpecifico);  // aggiunge la parte sinistra della pagina (RICERCA)
        
        // Va aggiunta anche la parte della tabella

        // mostra la scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
