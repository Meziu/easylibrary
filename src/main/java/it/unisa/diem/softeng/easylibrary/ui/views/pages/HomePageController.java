package it.unisa.diem.softeng.easylibrary.ui.views.pages;

import it.unisa.diem.softeng.easylibrary.ui.views.VisualizzatorePagine;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HomePageController implements Initializable {

    private VisualizzatorePagine vsPagine;

    public HomePageController(VisualizzatorePagine vsPagine) {
        this.vsPagine = vsPagine;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void visualizzaUtenti(ActionEvent event) {
        vsPagine.visualizzaUtenti();
    }

    @FXML
    private void visualizzaLibri(ActionEvent event) {
        vsPagine.visualizzaLibri();
    }

    @FXML
    private void visualizzaPrestiti(ActionEvent event) {
        vsPagine.visualizzaPrestiti();
    }
}
