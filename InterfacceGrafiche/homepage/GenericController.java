/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class GenericController<T> implements Initializable {

    @FXML
    private Label labelRicerca;
    @FXML
    private VBox leftPane;
    
    private String pageType;
    @FXML
    private TableView<T> table;
    
    public void setPageType(String pageType){
        this.pageType = pageType;
    }


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
    
    @FXML
    private void provaaa(ActionEvent event) { //prova tasto nella menubar
        System.out.println("ciao");
    }
    
    public void addLeftNode(Node n) { // per aggiungere alla parte sinistra
        leftPane.getChildren().add(n);
    }
    
    
    /*public void loadSpecificPage() throws IOException {
        String file = null;

        switch (pageType) {
            case "A": file = "UtentiView.fxml"; break;
            case "B": file = "Libri.fxml"; break;
            case "C": file = "Prestiti.fxml"; break;
        }

        Parent specific = FXMLLoader.load(getClass().getResource(file));
        leftPane.getChildren().setAll(specific);
    }*/
    
}
