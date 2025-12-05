/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public void addrightNode(Node n) {
        rightNode.setCenter(n);
    }

    public void bindFontSize(Node node, ReadOnlyDoubleProperty containerHeightProperty, double percentage) {
        StringBinding fontSizeBinding = containerHeightProperty
                .multiply(percentage)
                .asString("-fx-font-size: %.0fpx;");

        node.styleProperty().bind(fontSizeBinding);
    }

    //DA USARE NEL CASO IN CUI DECIDIAMO DI AGGIUNGERE LE COLONNE DA CODICE
    /*public <T, S> TableColumn<T, S> createNewColumn(String columnTitle, String propertyName, double prefWidth) throws RuntimeException { 

        if (columnTitle == null || propertyName == null) {
            throw new IllegalArgumentException("Titolo o nome proprietà non possono essere null.");
        }

        // 2. Creazione della nuova colonna generica
        TableColumn<T, S> newColumn = new TableColumn<>(columnTitle);

        // 3. Associazione della colonna alla proprietà del modello
        // Se PropertyValueFactory non riesce a trovare la proprietà, lancia una RuntimeException
        // (che propaghiamo fuori dal metodo).
        try {
            newColumn.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        } catch (Exception e) {
            // Lanciamo una RuntimeException più specifica per l'errore di configurazione
            throw new RuntimeException("Errore di configurazione della colonna: impossibile trovare la proprietà '"
                    + propertyName + "' nel modello. Dettagli: " + e.getMessage(), e);
        }

        return newColumn;
    }*/

}
