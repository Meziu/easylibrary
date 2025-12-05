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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class GenericController implements Initializable {
    
    @FXML
    private VBox leftNode;
    @FXML
    private BorderPane rightNode;
    @FXML
    private SplitPane splitPane;

    private String pageType;
    

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void provaaa(ActionEvent event) { //prova tasto nella menubar
        System.out.println("ciao");
    }
    
    @FXML
    private void returnHome(ActionEvent event) throws Exception { //prova tasto nella menubar
        Parent root = FXMLLoader.load(getClass().getResource("HomePageView.fxml"));
        
        
        Scene scene = new Scene(root);
        
        HomePageController controller = new HomePageController();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Biblioteca");
        stage.show();
    }

    public void addLeftNode(Node n) { // per aggiungere alla parte sinistra
        VBox.setVgrow(n, Priority.ALWAYS);
        leftNode.getChildren().add(n);
    }
    
    public void addrightNode(Node n){
        rightNode.setCenter(n);
    }

    
}
