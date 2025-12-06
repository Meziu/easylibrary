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
        
        // Nota: se la VBox centrale avesse un fx:id, potresti bindare alla sua larghezza.
        
        // Esempio di binding alla larghezza del rootPane, per garantire che si espandano:
        double padding = 100.0; // 50.0 left + 50.0 right
        
        utentiButton.prefWidthProperty().bind(rootPane.widthProperty().subtract(padding));
        libriButton.prefWidthProperty().bind(rootPane.widthProperty().subtract(padding));
        prestitiButton.prefWidthProperty().bind(rootPane.widthProperty().subtract(padding));
        
        // --- 2. Ridimensionamento dinamico del font (Opzionale ma molto utile) ---
        
        // Se vuoi anche che la dimensione del font si adatti (in un certo range), 
        // puoi bindarla alla larghezza o all'altezza del rootPane.
        // Questo è più complesso e richiede una logica personalizzata,
        // ma si può fare, ad esempio, per il titolo:
        
        //Label titleLabel = (Label) rootPane.getCenter().lookup(".title-text");
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
        

        // --- 3. Ridimensionamento dell'ImageView (Placeholder Logo) ---
        // Se l'ImageView è definita con un fx:id, potresti bindare le sue 
        // proprietà fitWidth e fitHeight per mantenere la proporzione
        // e farla scalare con la finestra.
        
        imageView.fitWidthProperty().bind(rootPane.widthProperty().divide(5));
        imageView.fitHeightProperty().bind(rootPane.heightProperty().divide(5));
        
        
        // Per il VBox al centro, il BorderPane e l'allineamento 'CENTER' 
        // gestiscono già molto bene il suo posizionamento e il ridimensionamento.
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
        stage.setScene(new Scene(root));
        stage.show();
    }

}
