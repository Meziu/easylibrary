package it.unisa.diem.softeng.easylibrary.ui.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;


public abstract class DataPageController<T, RC> implements Initializable {

    @FXML
    protected TableView<T> table;
    @FXML
    protected BorderPane ricercaContent;
    @FXML
    protected Label viewTitle;

    protected VisualizzatorePagine vp;
    protected RC rc;

    private String titoloVista;
    private String ricercaForm;

    public DataPageController(VisualizzatorePagine vp, RC rc, String titoloVista, String ricercaForm) {
        this.vp = vp;
        this.rc = rc;
        this.titoloVista = titoloVista;
        this.ricercaForm = ricercaForm;
    }

    /** Implementato dalle sottoclassi */
    protected abstract void initializeColonne();
    private void initializeRicerca() {
        this.viewTitle.setText(titoloVista);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(ricercaForm));
        loader.setController(rc);

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
    public abstract void add(ActionEvent event);

    @FXML
    public abstract void remove(ActionEvent event);

    @FXML
    public void returnHome(ActionEvent event) {
        vp.visualizzaHome();
    }
}
