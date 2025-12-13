/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author marco
 */
public class AutoriPageController {
    @FXML
    private TableView<Autore> autoriTable;
    @FXML
    private TableColumn<Autore, String> nomeColumn;
    @FXML
    private TableColumn<Autore, String> cognomeColumn;
    
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    
    @FXML
    public void aggiungiAutore(ActionEvent event) {
    }
    
    @FXML
    public void rimuoviAutore(ActionEvent event) {
    }
    
    @FXML
    public void conferma(ActionEvent event) {
    }
    
    @FXML
    public void annulla(ActionEvent event) {
    }
}
