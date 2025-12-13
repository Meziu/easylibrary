/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author marco
 */
class LibroAddForm implements Initializable {

    @FXML
    public TextField titoloField;
    @FXML
    public TextField isbnField;
    @FXML
    public Spinner<Integer> annoField;
    @FXML
    public Spinner<Integer> copieField;
    
    public List<Autore> listaAutori;
    
    public LibroAddForm() {
        listaAutori = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        copieField.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0)
        );

        int currentYear = Year.now().getValue();
        annoField.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, currentYear, currentYear)
        );
    }

    @FXML
    public void inserisciAutori() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/EditorAutoriView.fxml"));
        loader.setController(new AutoriPageController(listaAutori));

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

}
