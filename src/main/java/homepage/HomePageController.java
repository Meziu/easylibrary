/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author ray
 */
public class HomePageController implements Initializable {

    @FXML
    private Button utentiButton;
    @FXML
    private Button libriButton;
    @FXML
    private Button prestitiButton;
    //@FXML
    //private HBox buttonHBox;
    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // --- 1. Rendere i pulsanti ridimensionabili ---
        
        // I pulsanti sono all'interno di una VBox. Vogliamo che la loro larghezza
        // si adatti alla larghezza del contenitore (la VBox contenuta nel BorderPane).
        
        // Colleghiamo (bindiamo) la larghezza preferita dei pulsanti alla larghezza del rootPane
        // (o idealmente al contenitore VBox, ma usare il rootPane è un buon punto di partenza).
        // Sottraiamo un po' per il padding (50.0 a sinistra e 50.0 a destra = 100.0)
        double padding = 100.0; 
        
        utentiButton.prefWidthProperty().bind(rootPane.widthProperty().subtract(padding));
        libriButton.prefWidthProperty().bind(rootPane.widthProperty().subtract(padding));
        prestitiButton.prefWidthProperty().bind(rootPane.widthProperty().subtract(padding));
        
        // --- 2. Ridimensionamento dinamico del font ì ---
        
        if (titleLabel != null) {
            titleLabel.fontProperty().bind(
                Bindings.createObjectBinding(() -> {
                    double newSize = rootPane.getWidth() / 20.0; // Adatta il fattore
                    if (newSize < 20.0) newSize = 20.0; // Min size
                    if (newSize > 40.0) newSize = 40.0; // Max size
                    return Font.font("System Bold", newSize);
                }, rootPane.widthProperty())
            );
        }
        

        // --- 3. Ridimensionamento dell'ImageView ---
        
        imageView.fitWidthProperty().bind(rootPane.widthProperty().divide(5));
        imageView.fitHeightProperty().bind(rootPane.heightProperty().divide(5));
        
    }
    
    private void bindFontSizeToHBoxHeight(Button button, HBox hBox, double scaleFactor) {
        // Controlla che gli elementi siano presenti per evitare NullPointerException
        if (button != null && hBox != null) {
            button.styleProperty().bind(
                hBox.heightProperty()
                    .multiply(scaleFactor) // Fattore di scala (es. 0.35 = 35% dell'altezza dell'HBox)
                    .asString("-fx-font-size: %.0fpx;")
            );
        }
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


    private void loadGenericPage(String type, ActionEvent event) throws Exception {

        // 1) Carica la GenericView
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GenericView.fxml"));
        Parent root = loader.load();

        GenericController controller = loader.getController();

        // 2) Determina i file da caricare
        String fileForm = null;
        
        GenericController specificController;

        switch (type) {
            case "A":
                fileForm = "UtentiViewForm.fxml";
                controller.setPageType(type);
                break;
            case "B":
                fileForm = "LibriViewForm.fxml";
                controller.setPageType(type);
                break;
            case "C":
                fileForm = "PrestitiViewForm.fxml";
                controller.setPageType(type);
                break;
        }

        // 3) Carica form e tabella
        FXMLLoader specificLoader = new FXMLLoader(getClass().getResource(fileForm));
        Parent rootForm = specificLoader.load();
        
        
        specificController = specificLoader.getController();
        specificController.setTableView(controller.getTableView());
        specificController.setupSpecificColumns();
        
        
        // 4) Monta dentro la GenericView
        controller.addLeftNode(rootForm);
        

        // 5) Mostra la scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setStage(stage);
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();
        stage.setScene(new Scene(root));
        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
        stage.show();
    }
   
}
