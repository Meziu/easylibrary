/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

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

    }

}
