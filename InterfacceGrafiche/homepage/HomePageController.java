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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    @FXML
    private MenuItem saveButton;
    @FXML
    private MenuItem openButton;
    @FXML
    private HBox buttonHBox;
    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bindFontSizeToHBoxHeight(utentiButton, buttonHBox, 0.23); // Aumentato a 0.35 (35%) per visibilità
        bindFontSizeToHBoxHeight(libriButton, buttonHBox, 0.23);
        bindFontSizeToHBoxHeight(prestitiButton, buttonHBox, 0.23);

        // 2. RIDIMENSIONAMENTO IMMAGINE
        if (rootPane != null && imageView != null) {
            // Aggiungo un ascoltatore per eseguire il binding solo quando l'ImageView è in una Scene.
            // Questo è spesso necessario per i nodi centrali del BorderPane.
            imageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    // Vincola l'altezza/larghezza dell'immagine all'altezza/larghezza 
                    // del BorderPane radice, scalato per lasciare spazio a top/bottom.
                    
                    // L'altezza del BorderPane meno l'altezza di top (MenuBar) e bottom (HBox)
                    // (Devi sottrarre un valore stimato se non puoi accedere all'altezza effettiva)
                    
                    // SOLUZIONE ALTERNATIVA E PIÙ SICURA: Vincola alla dimensione della Scene/Stage
                    
                    // Vincola alla dimensione del rootPane (metodo più semplice)
                    // Usiamo un fattore di scala più aggressivo (es. 0.70)
                    imageView.fitHeightProperty().bind(rootPane.heightProperty().multiply(0.70));
                    imageView.fitWidthProperty().bind(rootPane.widthProperty().multiply(0.70));
                }
            });
            
            // Fai il binding immediato se è già nella scene (dipende da come carichi il FXML)
            if (imageView.getScene() != null) {
                imageView.fitHeightProperty().bind(rootPane.heightProperty().multiply(0.70));
                imageView.fitWidthProperty().bind(rootPane.widthProperty().multiply(0.70));
            }
        }
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

    @FXML
    private void saveFile(ActionEvent event) {
    }

    @FXML
    private void openFile(ActionEvent event) {
    }

    private void loadGenericPage(String type, ActionEvent event) throws Exception {

        // 1) Carica la GenericView
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GenericView.fxml"));
        Parent root = loader.load();

        GenericController controller = loader.getController();

        // 2) Determina i file da caricare
        String fileForm = null;
        String fileTable = null;

        switch (type) {
            case "A":
                fileForm = "UtentiViewForm.fxml";
                fileTable = "UtentiViewTable.fxml";
                break;
            case "B":
                fileForm = "LibriViewForm.fxml";
                fileTable = "LibriViewTable.fxml";
                break;
            case "C":
                fileForm = "PrestitiViewForm.fxml";
                fileTable = "PrestitiViewTable.fxml";
                break;
        }

        // 3) Carica form e tabella
        Parent rootForm = FXMLLoader.load(getClass().getResource(fileForm));
        Parent rootTable = FXMLLoader.load(getClass().getResource(fileTable));

        // 4) Monta dentro la GenericView
        controller.addLeftNode(rootForm);
        controller.addrightNode(rootTable);

        // 5) Mostra la scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
