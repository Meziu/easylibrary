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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    @FXML
    private MenuItem prova;
    @FXML
    protected TableView<?> tableView;
    
    private String pageType;
    @FXML
    private Button addButton;

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
        
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();
        
        stage.setScene(scene);
        stage.setTitle("Biblioteca");
        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
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

    public <T, S> TableColumn<T, S> createNewColumn(TableView<T> tableView, String columnTitle, String propertyName) throws RuntimeException { 

        if (columnTitle == null || propertyName == null) {
            throw new IllegalArgumentException("Titolo o nome proprietà non possono essere null.");
        }

        // 2. Creazione della nuova colonna generica
        TableColumn<T, S> newColumn = new TableColumn<>(columnTitle);

        // 3. Associazione della colonna alla proprietà del modello
        // Se PropertyValueFactory non riesce a trovare la proprietà, lancia una RuntimeException
        try {
            newColumn.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        } catch (Exception e) {
            // Lanciamo una RuntimeException più specifica per l'errore di configurazione
            throw new RuntimeException("Errore di configurazione della colonna: impossibile trovare la proprietà '"
                    + propertyName + "' nel modello. Dettagli: " + e.getMessage(), e);
        }
        
        tableView.getColumns().add(newColumn);
 
        return newColumn;
    }

    public TableView<?> getTableView() {
        return tableView;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }
    
    public void setTableView(TableView<?> userTableView) {
    }
    
    public void setupSpecificColumns(){
    }

    @FXML
    private void add(ActionEvent event) {
        if (pageType == null) {
            System.err.println("Errore: Tipo di pagina (pageType) non è stato impostato. Impossibile aprire il popup.");
            return;
        }

        String fileView;
        String title;

        switch (pageType) {
            case "A":
                fileView = "AddUtentiView.fxml";
                title = "Aggiungi Nuovo Utente";
                break;
            case "B":
                fileView = "AddLibriView.fxml";
                title = "Aggiungi Nuovo Libro";
                break;
            case "C":
                fileView = "AddPrestitiView.fxml";
                title = "Registra Nuovo Prestito";
                break;
            default:
                System.err.println("Tipo di pagina non riconosciuto: " + pageType);
                return;
        }

        // 3. Logica di apertura della finestra modale
        try {
            // Carica il file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileView));
            Parent root = loader.load();

            // Ottieni lo Stage (finestra) padre dall'evento
            Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Crea il nuovo Stage (la finestra popup)
            Stage popupStage = new Stage();
            popupStage.setTitle(title);
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);

            // Imposta la Modalità Modale
            popupStage.initOwner(ownerStage);
            popupStage.initModality(Modality.WINDOW_MODAL); 
            
            // Mostra la finestra e attende che venga chiusa
            popupStage.showAndWait();

        } catch (IOException e) {
            System.err.println("Errore I/O nel caricare il popup FXML: " + fileView);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore generico nell'apertura del popup: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    

}
