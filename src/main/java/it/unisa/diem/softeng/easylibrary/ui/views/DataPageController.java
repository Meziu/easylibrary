package it.unisa.diem.softeng.easylibrary.ui.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public abstract class DataPageController<T, RC, AC extends DataAddController<?>> implements Initializable {

    @FXML
    protected TableView<T> table;
    @FXML
    protected BorderPane ricercaContent;
    @FXML
    protected Label viewTitle;

    protected VisualizzatorePagine vp;
    protected RC ricercaController;
    private AC addController;

    private String titoloVista;
    private String ricercaForm;

    public DataPageController(VisualizzatorePagine vp, RC ricercaController, String titoloVista, String ricercaForm, AC addController) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColonne();
        initializeRicerca();
    }

    /** Per riempire la tabella */
    public void setItems(java.util.List<T> items) {
        table.getItems().setAll(items);
    }

    @FXML
    protected void add(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/DataAddView.fxml"));
        loader.setController(addController);

        try {
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @FXML
    public abstract void remove(ActionEvent event);

    @FXML
    public void returnHome(ActionEvent event) {
        vp.visualizzaHome();
    }
    
    
}
