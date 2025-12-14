package it.unisa.diem.softeng.easylibrary.ui.views.pages;

import it.unisa.diem.softeng.easylibrary.ui.views.dataadd.DataAddController;
import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.ui.views.VisualizzatorePagine;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public abstract class DataPageController<T, RC, AC extends DataAddController<?>> implements Initializable {

    @FXML
    protected TableView<T> table;
    @FXML
    protected BorderPane ricercaContent;
    @FXML
    protected Label viewTitle;
    @FXML
    private Button removeButton;

    protected VisualizzatorePagine vp;
    protected RC ricercaController;
    private AC addController;

    private Archiviabile<T> archivio;
    private String titoloVista;
    private String ricercaForm;

    public DataPageController(Archiviabile<T> archivio, VisualizzatorePagine vp, RC ricercaController, String titoloVista, String ricercaForm, AC addController) {
        this.archivio = archivio;
        this.vp = vp;
        this.ricercaController = ricercaController;
        this.titoloVista = titoloVista;
        this.ricercaForm = ricercaForm;
        this.addController = addController;
    }

    /** Implementato dalle sottoclassi */
    protected abstract void initializeColonne();
    private void initializeRicerca() {
        this.viewTitle.setText(titoloVista);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(ricercaForm));
        loader.setController(ricercaController);

        try {
            ricercaContent.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    
    protected abstract void initializeFiltro();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        removeButton.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        
        initializeColonne();
        initializeRicerca();
        initializeFiltro();
    }

    /** Per riempire la tabella */
    public void setItems(java.util.List<T> items) {
        table.getItems().setAll(items);
    }

    @FXML
    private void add(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/DataAddView.fxml"));
        loader.setController(addController);

        try {
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        setItems(archivio.getLista());
    }

    @FXML
    private void remove(ActionEvent event) {
        T selectedItem = table.getSelectionModel().getSelectedItem();

        try {
            archivio.rimuovi(selectedItem);
            setItems(archivio.getLista());
            table.refresh();

        } catch (Exception e) {
            String error = "Errore durante la rimozione del valore: " + e.toString();
            System.err.println(error);
            Alert a = new Alert(Alert.AlertType.ERROR);
            
            a.getDialogPane().setContent(new Label(error));
                    
            a.show();
        }
    }

    @FXML
    public void returnHome(ActionEvent event) {
        vp.visualizzaHome();
    }
}
