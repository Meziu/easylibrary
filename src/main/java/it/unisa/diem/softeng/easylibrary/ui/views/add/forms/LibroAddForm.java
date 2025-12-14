/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views.add.forms;

import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import it.unisa.diem.softeng.easylibrary.ui.views.AutoriPageController;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 *
 * @author marco
 */
public class LibroAddForm implements Initializable {

    @FXML
    public TextField titoloField;
    @FXML
    public TextField isbnField;
    @FXML
    public Spinner<Integer> annoField;
    @FXML
    public Spinner<Integer> copieField;
    
    public ObservableList<Autore> listaAutori;
    
    public LibroAddForm() {
        listaAutori = FXCollections.observableArrayList();
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
        AutoriPageController.mostraPerLista(titoloField.getScene().getWindow(), listaAutori);
    }

}
